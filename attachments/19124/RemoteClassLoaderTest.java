/*
 * The MIT License
 *
 * Copyright (c) 2004-2010, Sun Microsystems, Inc., Kohsuke Kawaguchi
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package hudson.remoting;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Vector;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

public class RemoteClassLoaderTest extends RmiTestBase {

    /**
     * The prefix of the test resources created. The resources will be named
     * resource1.txt..resourcen.txt
     */
    private static final String RESOURCE_NAME_PREFIX = "resource";

    /**
     * The URL resources that are created for the tests. It includes some JARs and unpackaged
     * resources.
     */
    private static final URL[] URLs = createResources();

    /** A temp folder where the JARs above are stored. */
    private static final String TEMP_FILE_NAME = "RemoteClassLoader Test";

    /** A class loader user for loading in the JARs that are created above. */
    private ClassLoader urlClassLoader;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        urlClassLoader = new URLClassLoader(URLs);
    }

    public void testFindResourcesWithPrefetch() throws IOException, InterruptedException {
        URL jar = findJarToPrefetch();

        URL[] jars = new URL[] { jar };

        PreloadJarTask preloadJarTask = new PreloadJarTask(jars, urlClassLoader);
        channel.call(preloadJarTask);

        DummyTask task = new DummyTask(urlClassLoader);
        channel.call(task);
    }

    /**
     * A helper method used for finding a remote URL to prefetch.
     *
     * @return The URL
     */
    private URL findJarToPrefetch() throws IOException {
        String resourceName = "/" + RESOURCE_NAME_PREFIX + "1.txt";
        URL manifest = urlClassLoader.getResource(resourceName);
        URL jar = Which.jarFile(manifest).toURI().toURL();
        return jar;
    }

    /**
     * Verifies the the content in the URLs are equal to the expected values.
     *
     * @param resources
     *            A list of URLs of actual results.
     * @throws IOException
     *             When some issue occurs reading the actual results.
     */
    private static void verifyResourcesEqual(Vector<URL> resources) throws IOException {
        assertEquals(3, resources.size());

        byte[] expectedImage1 = "bar".getBytes();
        byte[] expectedImage2 = "hello".getBytes();
        byte[] actualImage1 = readFully(resources.get(0).openStream());
        assertTrue(Arrays.equals(expectedImage1, actualImage1)
                || Arrays.equals(expectedImage2, actualImage1));

        byte[] actualImage2 = readFully(resources.get(1).openStream());
        assertTrue(Arrays.equals(expectedImage1, actualImage2)
                || Arrays.equals(expectedImage2, actualImage2));

        // The two resources found should be different. One should be bar, the
        // other hello
        assertFalse(Arrays.equals(actualImage1, actualImage2));
    }

    /**
     * Helper method to retrieve an stream into a byte array.
     *
     * @param in
     *            The input stream
     * @return The byte array that was read from the stream.
     * @throws IOException
     *             When some I/O issue occurs in reading from the stream.
     */
    private static byte[] readFully(InputStream in) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        byte[] buf = new byte[8192];
        int len;
        while ((len = in.read(buf)) > 0)
            baos.write(buf, 0, len);
        in.close();

        return baos.toByteArray();
    }

    /**
     * Converts an Enumeration to a Vector.
     *
     * @param urls
     *            The Enumeration of URLs to convert.
     * @return The Vector of URLs.
     */
    private static Vector<URL> enumerationToVector(Enumeration<URL> urls) {
        Vector<URL> vector = new Vector<URL>();
        while (urls.hasMoreElements()) {
            vector.add(urls.nextElement());
        }
        return vector;
    }

    /**
     * Creates the JARs and unpackaged resources used for the tests.
     *
     * @return An array of URLs to the JARs that were created.
     */
    private static URL[] createResources() {
        URL[] urls = null;
        try {
            urls = new URL[4];
            byte[][] images = new byte[2][];
            images[0] = "foo".getBytes();
            images[1] = "bar".getBytes();
            urls[0] = createJarFile(images);

            images = new byte[2][];
            images[0] = "baz".getBytes();
            images[1] = "hello".getBytes();
            urls[1] = createJarFile(images);

            File folder = File.createTempFile(TEMP_FILE_NAME, "");
            folder.delete();
            folder.mkdir();
            File resource = new File(folder, RESOURCE_NAME_PREFIX + "1.txt");
            FileOutputStream out = new FileOutputStream(resource);
            out.write("unpackaged resource content resource1".getBytes());
            out.close();
            resource.deleteOnExit();
            folder.deleteOnExit();
            urls[2] = folder.toURI().toURL();

            resource = new File(folder, RESOURCE_NAME_PREFIX + "3.txt");
            out = new FileOutputStream(resource);
            out.write("unpackaged resource content resource3".getBytes());
            out.close();
            resource.deleteOnExit();
            folder.deleteOnExit();
            urls[3] = folder.toURI().toURL();

        } catch (IOException e) {
            return null;
        }

        return urls;
    }

    /**
     * Creates an individual JAR file with the supplied resources in it.
     *
     * @param images
     *            A array of byte arrays. Each byte array consists of the contents of a resource to
     *            put into the JAR.
     * @return A URL to the JAR file that was created.
     * @throws IOException
     *             When some I/O issue occurs with writing the JAR file.
     */
    private static URL createJarFile(byte[][] images) throws IOException {
        Manifest manifest = createManifestFile();

        File jarFile = File.createTempFile(TEMP_FILE_NAME, ".jar");
        jarFile.deleteOnExit();
        JarOutputStream jar = new JarOutputStream(new FileOutputStream(jarFile), manifest);

        for (int i = 0; i < images.length; i++) {
            File file = File.createTempFile(TEMP_FILE_NAME, ".txt");
            file.deleteOnExit();
            FileOutputStream out = new FileOutputStream(file);
            out.write(images[i]);
            out.close();
            addFileToJar(file, RESOURCE_NAME_PREFIX + i + ".txt", jar);
        }

        jar.close();
        return toJarURL(jarFile);
    }

    /**
     * Creates a manifest file for a JAR.
     *
     * @return The manifest file.
     */
    private static Manifest createManifestFile() {
        Manifest manifest = new Manifest();
        manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "2.3-minor-56");
        return manifest;
    }

    /**
     * Adds a resource to a JAR.
     *
     * @param source
     *            The file containing the resource to add.
     * @param pathName
     *            The path name to use for the resource, such as image/cat.png. Note that the path
     *            must have forward slashes per the JAR/ZIP spec.
     * @param target
     *            The JAR file in which to add the file
     * @throws IOException
     */
    private static void addFileToJar(File source, String pathName, JarOutputStream target)
            throws IOException {
        BufferedInputStream in = null;
        try {
            JarEntry entry = new JarEntry(pathName);
            entry.setTime(source.lastModified());
            target.putNextEntry(entry);
            in = new BufferedInputStream(new FileInputStream(source));

            byte[] buffer = new byte[1024];
            while (true) {
                int count = in.read(buffer);
                if (count == -1)
                    break;
                target.write(buffer, 0, count);
            }
            target.closeEntry();
        } finally {
            if (in != null)
                in.close();
        }
    }

    /**
     * Converts a file URL into a JAR URL appending the resource name onto the URL.
     *
     * @param localResource
     *            The URL to the file
     * @param resourceName
     *            the resource path to place into the JAR URL.
     * @return A JAR URL to the file. For example, /some/folder/important.jar with resource name of
     *         images/cat.png would be converted to
     *         jar:file:/some/folder/important.jar!/images/cat.png.
     * @throws MalformedURLException
     *             When an issue occurs creating the URL instance representing the JAR URL.
     */
    private static URL toJarURL(File localResource, String resourceName)
            throws MalformedURLException {
        if (!resourceName.startsWith("/")) {
            resourceName = "/" + resourceName;
        }
        return new URL("jar", "", toURL(localResource).toExternalForm() + "!" + resourceName);
    }

    /**
     * Converts a file into a JAR URL.
     *
     * @param localResource
     *            The file
     * @return A JAR URL to the file. For example, /some/folder/important.jar would be converted to
     *         jar:file:/some/folder/important.jar!/
     * @throws MalformedURLException
     *             When an issue occurs creating the URL instance representing the JAR URL.
     */
    /* package */static URL toJarURL(File localResource) throws MalformedURLException {
        return toJarURL(localResource, "");
    }

    /**
     * Converts a File to a file URL.
     *
     * @param localResource
     *            The file
     * @return A file URL representing the local file.
     * @throws MalformedURLException
     *             When an issue occurs converting the file to its corresponding URL path.
     */
    private static URL toURL(File localResource) throws MalformedURLException {
        if (!localResource.exists()) {
            return null; // Abort as nothing can be done.
        }
        return localResource.toURI().toURL();
    }

    private static class DummyTask implements DelegatingCallable<Boolean, IOException> {

        private static final long serialVersionUID = -814903050393989045L;

        private transient ClassLoader target;

        public DummyTask(ClassLoader target) {
            this.target = target;
        }

        public ClassLoader getClassLoader() {
            return target;
        }

        public Boolean call() throws IOException {
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            if (!(cl instanceof RemoteClassLoader)) {
                return false;
            }

            RemoteClassLoader rcl = (RemoteClassLoader) cl;
            String resourceName = RESOURCE_NAME_PREFIX + "1.txt";
            Vector<URL> resources = enumerationToVector(rcl.getResources(resourceName));

            assertEquals(3, resources.size());

            // One of the resources should be from a prefetched JAR; the others should be locally
            // cached copies.
            int prefetchedCount = 0;
            for (int i = 0; i < resources.size(); i++) {
                String urlspec = resources.get(i).toExternalForm();
                if (urlspec.indexOf(".jar") != -1) {
                    prefetchedCount++;
                }
            }

            assertEquals("Expect one URL to be from a prefetched JAR.", 1, prefetchedCount);

            verifyResourcesEqual(resources);

            return true;
        }

    }
}

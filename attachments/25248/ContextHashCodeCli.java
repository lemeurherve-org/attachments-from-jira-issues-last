//package hudson.plugins.analysis.util;

import java.io.IOException;

import org.apache.commons.io.LineIterator;

// needed by EncodingValidator.readFile:
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

//import javax.annotation.CheckForNull;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang.StringUtils;

// needed by main()
import java.lang.Integer;

/**
 * Creates a hash code from the source code of the warning line and the
 * surrounding context.
 *
 * Version with command line interface.
 * Depends on commons-io-1.4.jar commons-lang-2.4.jar (or earlier?)
 * Note that changes in ContextHashCode.java will not automatically update 
 * this file!
 *
 * @author Ulli Hafner, Ralf Hain for the Cli
 * Version 1 from 2014-02-05
 * licensed under the MIT license.
 */
public class ContextHashCodeCli {

    /**
     * Reads the specified file with the given encoding.
     *
     * @param fileName
     *            the file name
     * @param encoding
     *            the encoding of the file, if <code>null</code> or empty then
     *            the default encoding of the platform is used
     * @return the line iterator
     * @throws FileNotFoundException
     *             Indicates that the file is not found.
     * @throws IOException
     *             Signals that an I/O exception has occurred during reading of
     *             the file.
     *
     * from EncodingValidator.java, HR 
     */
    public static LineIterator readFile(final String fileName, /*@CheckForNull*/ final String encoding)
            throws FileNotFoundException, IOException {
        FileInputStream stream = new FileInputStream(new File(fileName));
        if (StringUtils.isNotBlank(encoding)) {
            return IOUtils.lineIterator(stream, encoding);
        }
        else {
            return IOUtils.lineIterator(stream, null);
        }
    }

    /**
     * Creates a hash code from the source code of the warning line and the
     * surrounding context.
     *
     * @param fileName
     *            the absolute path of the file to read
     * @param line
     *            the line of the warning
     * @param encoding
     *            the encoding of the file, if <code>null</code> or empty then
     *            the default encoding of the platform is used
     * @return a has code of the source code
     * @throws IOException
     *             if the contents of the file could not be read
     *
     * from ContextHashCode.java (besides outcommenting EncodingValidator, HR ) 
     */
    private static final int LINES_LOOK_AHEAD = 3;
    private static final int BUFFER_SIZE = 1000;

    public static int create(final String fileName, final int line, final String encoding) throws IOException {

        LineIterator lineIterator = /*EncodingValidator.*/readFile(fileName, encoding);

        StringBuilder context = new StringBuilder(BUFFER_SIZE);
        for (int i = 0; lineIterator.hasNext(); i++) {
            String currentLine = lineIterator.nextLine();
            if (i >= line - LINES_LOOK_AHEAD) {
                context.append(currentLine);
            }
            if (i > line + LINES_LOOK_AHEAD) {
                break;
            }
        }
        lineIterator.close();

        return context.toString().hashCode();
    }

    /* My humble attempt HR, */
    public static void main(String[] args) { 
        int i = 0;
        try {
            // TODO proper error-handling
            // TODO support for "encoding". Default encoding "works for me", HR
            i = create( args[0], Integer.parseInt(args[1]), "" );
        }
        catch ( IOException ioex ) {
            System.err.println( "IOException" );
        }
        System.out.println(i);
   }
}



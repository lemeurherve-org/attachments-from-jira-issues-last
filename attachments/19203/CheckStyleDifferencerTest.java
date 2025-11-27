/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package hudson.plugins.checkstyle;

import static org.junit.Assert.assertEquals;
import hudson.plugins.analysis.util.model.FileAnnotation;
import hudson.plugins.analysis.util.model.Priority;
import hudson.plugins.checkstyle.parser.Warning;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * @author weigo
 */
public final class CheckStyleDifferencerTest {

    /**
     * Container for Checkstyle annotations used in the test.
     */
    private static final List<FileAnnotation> ANNOTATIONS = new ArrayList<FileAnnotation>();

    public CheckStyleDifferencerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        initAnnotations();
    }

    private static void initAnnotations() throws SAXException, IOException {
        XMLReader reader = XMLReaderFactory.createXMLReader();
        CheckStyleWarningsParser handler = new CheckStyleWarningsParser();
        reader.setContentHandler(handler);
        reader.parse(new InputSource(CheckStyleDifferencerTest.class.getResourceAsStream("checkstyle-warnings.xml")));

        ANNOTATIONS.addAll(handler.getAnnotations());
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        ANNOTATIONS.clear();
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testAnnotationDifferencerWithOtherIsArrayList() {
        for (int i = 1000; i < ANNOTATIONS.size(); i += 1000) {
            List<FileAnnotation> annotations = ANNOTATIONS.subList(0, i);

            long start = System.currentTimeMillis();
            differenceUsingStandardCollectionInterfaceForOther(annotations, annotations);
            long duration = System.currentTimeMillis() - start;

            long start1 = System.currentTimeMillis();
            Set<FileAnnotation> diff = differenceUsingHashSetForOther(annotations, annotations);
            long duration1 = System.currentTimeMillis() - start1;

            System.err.println(i + ": " + duration + ", " + duration1);

            assertEquals(0, diff.size());
        }
    }

    private Set<FileAnnotation> differenceUsingStandardCollectionInterfaceForOther(
            final Collection<FileAnnotation> target, final Collection<FileAnnotation> other) {
        Set<FileAnnotation> difference = new HashSet<FileAnnotation>(target);
        difference.removeAll(other);
        return difference;
    }

    private Set<FileAnnotation> differenceUsingHashSetForOther(final Collection<FileAnnotation> target,
            final Collection<FileAnnotation> other) {
        Set<FileAnnotation> difference = new HashSet<FileAnnotation>(target);
        difference.removeAll(new HashSet<FileAnnotation>(other));
        return difference;
    }

    private static final class CheckStyleWarningsParser extends DefaultHandler {
        private final List<FileAnnotation> annotations = new ArrayList<FileAnnotation>();

        /**
         * @return the annotations
         */
        List<FileAnnotation> getAnnotations() {
            return Collections.unmodifiableList(annotations);
        }

        private final StringBuffer text = new StringBuffer();
        private String message;
        private String priority;
        private String category;
        private String type;
        private int start;
        private int end;
        private String fileName;
        private String moduleName;
        private String packageName;
        private long contextHashCode;
        private String origin;

        private final Map<String, Priority> priorityMapper = new HashMap<String, Priority>();

        CheckStyleWarningsParser() {
            this.priorityMapper.put("HIGH", Priority.HIGH);
            this.priorityMapper.put("NORMAL", Priority.NORMAL);
            this.priorityMapper.put("LOW", Priority.LOW);
        }

        private String getText() {
            String text = this.text.toString();
            this.text.setLength(0);

            return text;
        }

        /*
         * (non-Javadoc)
         * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
         */
        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            this.text.append(ch, start, length);
        }

        /*
         * (non-Javadoc)
         * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String,
         * java.lang.String, java.lang.String)
         */
        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if ("message".equals(localName)) {
                this.message = this.getText();
            }
            else if ("priority".equals(localName)) {
                this.priority = this.getText();
            }
            else if ("start".equals(localName)) {
                this.start = Integer.valueOf(this.getText());
            }
            else if ("end".equals(localName)) {
                this.end = Integer.valueOf(this.getText());
            }
            else if ("fileName".equals(localName)) {
                this.fileName = this.getText();
            }
            else if ("moduleName".equals(localName)) {
                this.moduleName = this.getText();
            }
            else if ("packageName".equals(localName)) {
                this.packageName = this.getText();
            }
            else if ("category".equals(localName)) {
                this.category = this.getText();
            }
            else if ("type".equals(localName)) {
                this.type = this.getText();
            }
            else if ("contextHashCode".equals(localName)) {
                this.contextHashCode = Long.valueOf(this.getText());
            }
            else if ("origin".equals(localName)) {
                this.origin = this.getText();
            }
            else if ("warning".equals(localName)) {
                Warning warning =
                        new Warning(this.priorityMapper.get(this.priority), this.message, this.category, this.type,
                                this.start, this.end);
                warning.setContextHashCode(contextHashCode);
                warning.setFileName(fileName);
                warning.setPackageName(packageName);
                warning.setModuleName(moduleName);
                warning.setOrigin(origin);

                this.annotations.add(warning);
            }
        }

        /*
         * (non-Javadoc)
         * @see
         * org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String,
         * java.lang.String, java.lang.String, org.xml.sax.Attributes)
         */
        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            super.startElement(uri, localName, qName, attributes);
            this.getText();

            if ("warning".equals(localName)) {
                this.message = null;
                this.priority = null;
                this.category = null;
                this.type = null;
                this.start = -1;
                this.end = -1;
                this.fileName = null;
                this.moduleName = null;
                this.packageName = null;
                this.contextHashCode = -1;
                this.origin = null;
            }
        }
    }
}

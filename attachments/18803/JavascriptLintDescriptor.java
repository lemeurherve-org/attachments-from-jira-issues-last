package hudson.plugins.violations.types.javascriptlint;

import hudson.plugins.violations.TypeDescriptor;
import hudson.plugins.violations.ViolationsParser;

/**
 * The descriptor class for JavascriptLint violations type.
 */
public final class JavascriptLintDescriptor  extends TypeDescriptor {

    /** The descriptor for the JavascriptLint violations type. */
    public static final JavascriptLintDescriptor DESCRIPTOR = new JavascriptLintDescriptor();

    private JavascriptLintDescriptor() {
        super("javascriptlint");
    }

    /**
     * Create a parser for the JavascriptLint type.
     * @return a new JavascriptLint parser.
     */
    @Override
    public ViolationsParser createParser() {
        return new JavascriptLintParser();
    }

}


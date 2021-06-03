package filesprocessing;

/**
 * this class describes the Exception that will be thrown when there is an error II in any Section, like
 * ORDER or FILTER words are missing.
 */
public class SectionException extends HelperException {

    /**
     * the message that we want to print when the exception is thrown.
     */
    private final static String MESSAGE = "ERROR: FILTER or ORDER words missing (type II) \n";

    /**
     * this method to print the error message to system.
     */
    public void print() {
        System.err.println(MESSAGE);
    }
}

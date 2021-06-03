package filesprocessing;

/**
 * this class describes the Exception to throw when there is an unwanted parameters in the command line
 * (Configuration).
 */
public class BadParametersException extends HelperException {

    /**
     * the message that we want to print when we throw this Exception.
     */
    private static final String MESSAGE = "ERROR: more than two parameters in the configuration (type II error) \n";

    /**
     * this method that we will call when this Exception thrown, to print a message.
     */
    public void print() {
        System.err.println(MESSAGE);
    }

}

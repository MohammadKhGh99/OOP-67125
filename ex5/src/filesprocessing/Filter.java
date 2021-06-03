package filesprocessing;

import java.io.File;

/**
 * this class describes the Father Filter, that all the Filters inherits from it.
 */
abstract class Filter {

    /**
     * if we want to reverse the effect of certain Filter or not, if not="NOT" the effect will be reversed.
     */
    protected String not = "";

    /**
     * some Magic Numbers to use with my implementation.
     */
    protected final static String NOT = "NOT", YES = "YES", NO = "NO", PERIOD = ".";

    /**
     * converting form bytes to kilo bytes.
     */
    protected final static double BYTES_2_KBYTES = 1024;

    /**
     * this method checks if the given file passes the constraints of a certain Filter or not.
     *
     * @param file the file to check.
     * @return true if the file passes, false otherwise.
     */
    abstract boolean isPass(File file);
}


package filesprocessing;

import java.io.File;

/**
 * this class describes the filter that takes all the files from the wanted directory.
 */
public class AllFilter extends Filter {

    /**
     * the default constructor with no parameters and no implementation.
     */
    public AllFilter() {
    }

    /**
     * the constructor that called when the word "NOT" was written in the filter line in the commands file.
     *
     * @param not the string to reflect the effect of the taking all the filters (not taking any file!).
     */
    public AllFilter(String not) {
        this.not = not;
    }

    /**
     * this method to check if the given file is passing the constraints of this filter class.
     *
     * @param file the file to check.
     * @return true if it passes the constraints, false otherwise.
     */
    @Override
    public boolean isPass(File file) {
        return !this.not.equals(NOT);
    }
}

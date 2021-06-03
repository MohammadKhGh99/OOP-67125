package filesprocessing;

import java.io.File;

/**
 * this class describes the contains Filter.
 */
public class ContainsFilter extends Filter {

    /**
     * the string that we want to check if it is in the file name or not.
     */
    private String textToCheck;

    /**
     * the first constructor that takes one string.
     *
     * @param textToCheck the string to use in contains Filter.
     */
    public ContainsFilter(String textToCheck) {
        this.textToCheck = textToCheck;
    }

    /**
     * the second constructor that takes two string, the first is the string to use in contains Filter, the
     * second if we want to reverse the process of this Filter.
     *
     * @param textToCheck the string to use in contains Filter.
     * @param not         the string if we want to reverse this process of filtering.
     */
    public ContainsFilter(String textToCheck, String not) {
        this.textToCheck = textToCheck;
        this.not = not;
    }

    /**
     * this method checks if the given file passes the constraints of this Filter or not.
     *
     * @param file the file to check.
     * @return true if the file name has the textToCheck string in it, false otherwise.
     */
    @Override
    public boolean isPass(File file) {
        if (this.not.equals(NOT)) {
            return !file.getName().contains(this.textToCheck);
        }
        return file.getName().contains(this.textToCheck);
    }
}

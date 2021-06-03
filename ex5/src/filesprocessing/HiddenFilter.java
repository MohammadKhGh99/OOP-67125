package filesprocessing;

import java.io.File;

/**
 * this class describes the hidden Filter.
 */
public class HiddenFilter extends Filter {

    /**
     * hidden "YES", not hidden "NO".
     */
    private String hidden;

    /**
     * the first constructor without revering the process of this Filter.
     *
     * @param hidden the required state of files to filter.
     */
    public HiddenFilter(String hidden) {
        this.hidden = hidden;
    }

    /**
     * the second constructor with reversing the process of this Filter.
     *
     * @param hidden the required state of files to filter.
     * @param not    reversing the process of this filter "NOT".
     */
    public HiddenFilter(String hidden, String not) {
        this.hidden = hidden;
        this.not = not;
    }

    /**
     * this method checks if the given file is hidden or not.
     *
     * @param file the file to check.
     * @return true if the given file is hidden, false otherwise.
     */
    @Override
    public boolean isPass(File file) {
        if (this.hidden.equals(YES)) {
            if (this.not.equals(NOT)) {
                return !file.isHidden();
            }
            return file.isHidden();
        } else {
            if (this.not.equals(NOT)) {
                return file.isHidden();
            }
            return !file.isHidden();
        }
    }
}

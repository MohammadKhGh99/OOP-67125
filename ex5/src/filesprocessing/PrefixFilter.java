package filesprocessing;

import java.io.File;

/**
 * this class describes the prefix Filter.
 */
public class PrefixFilter extends Filter {

    /**
     * the prefix to filter by.
     */
    private String prefix;

    /**
     * the first constructor without reversing the normal process of this Filter.
     *
     * @param prefix the prefix to filter by.
     */
    public PrefixFilter(String prefix) {
        this.prefix = prefix;
    }

    /**
     * the second constructor with reversing the normal process of this Filter.
     *
     * @param prefix the prefix to filter by.
     * @param not    reversing the process of this filter (not = "NOT").
     */
    public PrefixFilter(String prefix, String not) {
        this.prefix = prefix;
        this.not = not;
    }

    /**
     * this method checks if the given file has the given prefix.
     *
     * @param file the file to check.
     * @return true if the given file has the given prefix above, false otherwise.
     */
    @Override
    public boolean isPass(File file) {
        if (this.not.equals(NOT)) {
            return !(file.getName().startsWith(this.prefix));
        }
        return file.getName().startsWith(this.prefix);
    }
}

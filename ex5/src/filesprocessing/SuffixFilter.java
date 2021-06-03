package filesprocessing;

import java.io.File;

/**
 * this class describes the suffix Filter.
 */
public class SuffixFilter extends Filter {

    /**
     * the suffix to filter by.
     */
    private String suffix;

    /**
     * the first constructor with normal process of this filter.
     *
     * @param suffix the suffix to filter by.
     */
    public SuffixFilter(String suffix) {
        this.suffix = suffix;
    }

    /**
     * the second constructor with reversing the normal process of this filter.
     *
     * @param suffix the suffix ti filter by.
     * @param not    the command to reverse the process or not (not = "NOT").
     */
    public SuffixFilter(String suffix, String not) {
        this.suffix = suffix;
        this.not = not;
    }

    /**
     * this method checks if the given file has the given suffix above.
     *
     * @param file the file to check.
     * @return true if the given file has the given suffix, false otherwise.
     */
    @Override
    public boolean isPass(File file) {
        if (this.suffix.equals(PERIOD) && !file.getName().contains(PERIOD)) {
            return true;
        }
        if (this.not.equals(NOT)) {
            return !file.getName().endsWith(this.suffix);
        }
        return file.getName().endsWith(this.suffix);
    }
}

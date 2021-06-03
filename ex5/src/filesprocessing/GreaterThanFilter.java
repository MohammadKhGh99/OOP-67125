package filesprocessing;

import java.io.File;

/**
 * this class describes the greater than Filter.
 */
public class GreaterThanFilter extends Filter {

    /**
     * the value to check with.
     */
    private double value;

    /**
     * the first constructor with normal filter.
     *
     * @param value the value to check with.
     */
    public GreaterThanFilter(double value) {
        this.value = value;
    }

    /**
     * the second constructor with reversing the process of this filter.
     *
     * @param value the value to check with.
     * @param not   if we want to reverse the process of this filter.
     */
    public GreaterThanFilter(double value, String not) {
        this.value = value;
        this.not = not;
    }

    /**
     * this method checks if the size of the given file is greater than the given size.
     *
     * @param file the file to check.
     * @return true if the size of the given file is greater than the given size.
     */
    @Override
    public boolean isPass(File file) {
        if (this.not.equals(NOT)) {
            return !((file.length() / BYTES_2_KBYTES) > this.value);
        }
        return (file.length() / BYTES_2_KBYTES) > this.value;
    }
}

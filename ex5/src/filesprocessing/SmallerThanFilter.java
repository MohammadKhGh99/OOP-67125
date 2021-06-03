package filesprocessing;

import java.io.File;

/**
 * this class describes the smaller than Filter.
 */
public class SmallerThanFilter extends Filter {

    /**
     * the value to filter by.
     */
    private double value;

    /**
     * the first constructor without reversing the normal process of this Filter.
     *
     * @param value the value to filter by.
     */
    public SmallerThanFilter(double value) {
        this.value = value;
    }

    /**
     * the second constructor with reversing the normal process of this Filter.
     *
     * @param value the value to filter by.
     * @param not   the command to reverse the process (not = "NOT").
     */
    public SmallerThanFilter(double value, String not) {
        this.value = value;
        this.not = not;
    }

    /**
     * this method checks if the size of the given file is smaller than the given size above.
     *
     * @param file the file to check.
     * @return true if the size of the given file is smaller than the given size above, false otherwise.
     */
    @Override
    public boolean isPass(File file) {
        if (this.not.equals(NOT)) {
            return !((file.length() / BYTES_2_KBYTES) < this.value);
        }
        return (file.length() / BYTES_2_KBYTES) < this.value;
    }
}

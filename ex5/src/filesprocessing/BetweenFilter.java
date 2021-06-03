package filesprocessing;

import java.io.File;

/**
 * this class describes the between Filter.
 */
public class BetweenFilter extends Filter {

    /**
     * the first number in the between Filter (the minimal bound).
     */
    private double first;

    /**
     * the second number in the between Filter (the maximal bound).
     */
    private double second;

    /**
     * the first constructor, that takes two number to filter between both of them.
     *
     * @param first  the first number.
     * @param second the second number.
     */
    public BetweenFilter(double first, double second) {
        this.first = first;
        this.second = second;
    }

    /**
     * the second constructor that takes two numbers to filter between them, and if we want to made the
     * opposite filter (not between these numbers).
     *
     * @param first  the first number.
     * @param second the second number.
     * @param not    that string that if we want to revere the process of the filtering.
     */
    public BetweenFilter(double first, double second, String not) {
        this.first = first;
        this.second = second;
        this.not = not;
    }

    /**
     * this method checks if the given file can pass the constraints of this filter or not.
     *
     * @param file the file to check.
     * @return true if the file passes the constraints, false otherwise.
     */
    @Override
    public boolean isPass(File file) {
        if (this.not.equals(NOT)) {
            return !((file.length() / BYTES_2_KBYTES) >= this.first && (file.length() / BYTES_2_KBYTES) <= this.second);
        }
        return (file.length() / BYTES_2_KBYTES) >= this.first && (file.length() / BYTES_2_KBYTES) <= this.second;
    }
}

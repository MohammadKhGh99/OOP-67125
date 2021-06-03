package filesprocessing;

import java.io.File;

/**
 * this class describes File Filter that filters by the names of the files.
 */
public class FileFilter extends Filter {

    /**
     * the file name to filter with.
     */
    private String filename;

    /**
     * the first constructor that takes one argument.
     *
     * @param filename the file name to check with.
     */
    public FileFilter(String filename) {
        this.filename = filename;
    }

    /**
     * the second constructor that takes two arguments.
     *
     * @param filename the file name to filter with.
     * @param not      if reversing the process of this Filter or not.
     */
    public FileFilter(String filename, String not) {
        this.filename = filename;
        this.not = not;
    }

    /**
     * this method checks if the given file has the same name as given in this Filter.
     *
     * @param file the file to check.
     * @return true if the name of the given file equals the file name in this Filter.
     */
    @Override
    public boolean isPass(File file) {
        if (this.not.equals(NOT)) {
            return !file.getName().equals(this.filename);
        }
        return file.getName().equals(this.filename);
    }
}

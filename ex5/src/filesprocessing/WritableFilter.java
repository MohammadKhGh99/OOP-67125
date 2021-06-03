package filesprocessing;

import java.io.File;

/**
 * this class describes the writable Filter.
 */
public class WritableFilter extends Filter {

    /**
     * state of the file to filter by.
     */
    private String permission;

    /**
     * the first constructor that process normally.
     *
     * @param permission the state of file to filter by.
     */
    public WritableFilter(String permission) {
        this.permission = permission;
    }

    /**
     * the second constructor the reverse the process of this Filter.
     *
     * @param permission the state of the file to filter by.
     * @param not        the command to reverse the process or not.
     */
    public WritableFilter(String permission, String not) {
        this.permission = permission;
        this.not = not;
    }

    /**
     * this method checks if the given file writable or not.
     *
     * @param file the file to check.
     * @return true if the given file is writable, false otherwise.
     */
    @Override
    public boolean isPass(File file) {
        if (this.permission.equals(YES)) {
            if (this.not.equals(NOT)) {
                return !file.canWrite();
            }
            return file.canWrite();
        } else {
            if (this.not.equals(NOT)) {
                return file.canWrite();
            }
            return !file.canWrite();
        }
    }
}

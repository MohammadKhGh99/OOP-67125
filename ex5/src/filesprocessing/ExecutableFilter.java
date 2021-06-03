package filesprocessing;

import java.io.File;

/**
 * this class describes the Executable Filter, if the file is Executable or not.
 */
public class ExecutableFilter extends Filter {

    /**
     * Executable or not, "YES" or "NO".
     */
    private String permission;

    /**
     * the first constructor takes one argument.
     *
     * @param permission string with "YES" or "NO" strings (Executable or not).
     */
    public ExecutableFilter(String permission) {
        this.permission = permission;
    }

    /**
     * the second constructor takes two arguments.
     *
     * @param permission string with "YES" or "NO" strings (Executable or not).
     * @param not        if we want to reverse the prcess of this Filter or not.
     */
    public ExecutableFilter(String permission, String not) {
        this.permission = permission;
        this.not = not;
    }

    /**
     * this method checks if the given file Executable or not.
     *
     * @param file the file to check.
     * @return true if the file is Executable, false otherwise.
     */
    @Override
    public boolean isPass(File file) {
        if (this.permission.equals(YES)) {
            if (this.not.equals(NOT)) {
                return !file.canExecute();
            }
            return file.canExecute();
        } else {
            if (this.not.equals(NOT)) {
                return file.canExecute();
            }
            return !file.canExecute();
        }
    }
}

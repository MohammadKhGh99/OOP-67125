package filesprocessing;

import java.io.File;
import java.util.LinkedList;

/**
 * this class describes each section in each commands file: warnings and files names.
 */
public class Section {

    /**
     * the warnings of this section.
     */
    public LinkedList<String> warnings;

    /**
     * the remaining files from this section process.
     */
    public LinkedList<File> files;

    /**
     * the constructor of each section, empty warnings and empty files, yet.
     */
    public Section() {
        this.warnings = new LinkedList<>();
        this.files = new LinkedList<>();
    }
}

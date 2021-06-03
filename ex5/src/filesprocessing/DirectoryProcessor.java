package filesprocessing;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

/**
 * this class describes the Directory Processor, that do all the work.
 */
public class DirectoryProcessor {

    /**
     * this method is the main method that do all the directory processes.
     *
     * @param args the arguments from the command line (sourceDir, commandLine).
     */
    public static void main(String[] args) {

        LinkedList<Section> list = new LinkedList<>();
        try {
            if (args.length > 2) {
                throw new BadParametersException();
            }
            Parsing parse = new Parsing(args[0], args[1]);
            list = parse.parsingFile();

        } catch (BadParametersException e) {
            e.print();
        } catch (SectionException e) {
            e.print();
        } catch (IOException e) {
            System.err.println("ERROR: error in accessing to command file or the source file (type II) \n");
        }
        for (Section section1 : list) {
            for (String warning : section1.warnings) {
                System.err.println(warning);
            }
            for (File file : section1.files) {
                System.out.println(file.getName());
            }
        }
    }
}

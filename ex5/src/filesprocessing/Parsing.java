package filesprocessing;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

/**
 * this class describes the parsing process on the commands file and source directory.
 */
public class Parsing {

    /**
     * the sections of the command file.
     */
    private LinkedList<Section> sectionsList;

    /**
     * the command file path and source directory path.
     */
    private String commandPath, sourcePath;

    /**
     * some Magic Numbers to use in my code.
     */
    private final static String FILTER = "FILTER", ORDER = "ORDER";

    /**
     * the constructor of this parsing class, takes the command file and source directory path.
     *
     * @param sourcePath  the source directory path.
     * @param commandPath the command file path.
     */
    public Parsing(String sourcePath, String commandPath) {
        this.commandPath = commandPath;
        this.sourcePath = sourcePath;
        this.sectionsList = new LinkedList<>();
    }

    /**
     * this method reads the command file and extracts the commands from it.
     * also, takes all the files in the source directory to start processing.
     *
     * @return the sections list.
     * @throws IOException      if we couldn't read the command file.
     * @throws SectionException if there is a problem in a section.
     */
    public LinkedList<Section> parsingFile() throws IOException, SectionException {
        List<String> allLine = Files.readAllLines(Paths.get(commandPath));
        if (allLine.size() == 0) {
            return this.sectionsList;
        }
        if (allLine.size() < 3) {
            throw new SectionException();
        }
        int i = 0;
        while (i <= allLine.size() - 1) {
            if (!(allLine.subList(i, allLine.size()).size() < 3)) {
                if (!allLine.get(i).equals(FILTER) || !allLine.get(i + 2).equals(ORDER)) { // || allLine.size() - i < 3
                    throw new SectionException();
                } else {
                    String filterLine = allLine.get(i + 1);
                    int orderLineNumber = allLine.subList(i, allLine.size()).indexOf(ORDER) + i + 1;
                    if (orderLineNumber == i + 2)
                        orderLineNumber++;
                    String orderLine = "";
                    if (orderLineNumber < allLine.size())
                        orderLine = allLine.get(orderLineNumber);
                    Section section = new Section();
                    Filter filter = FilterFactory.makingOneFilter(filterLine, i + 2, section);
                    File sourceFile = new File(sourcePath);
//                    sourceFile.
                    File[] filesArray = sourceFile.listFiles();

                    addingFilesToSections(filesArray, filter, section);
                    Order order = OrderFactory.makingOneOrder(orderLine, section.files,
                            orderLineNumber + 1, section);
                    order.sortFiles();
                    this.sectionsList.add(section);
                    if (orderLine.equals(FILTER)) {
                        i += 3;
                    } else {
                        i += 4;
                    }
                }
            } else {
                throw new SectionException();
            }
        }
        return this.sectionsList;
    }

    /**
     * this method adds the filtered files to the section.
     *
     * @param filesArray the files array to filter and add to section.
     * @param filter     the filter to use in filtering.
     * @param section    the section to add the files to.
     */
    private void addingFilesToSections(File[] filesArray, Filter filter, Section section) {
        if (filesArray != null)
            for (File file : filesArray)
                if (!file.isDirectory() && filter.isPass(file))
                    section.files.add(file);

    }
}

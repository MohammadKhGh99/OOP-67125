package filesprocessing;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * this class describes the sorting method by the type (type of file) order.
 */
public class TypeOrder extends Order {

    /**
     * the first constructor with a normal sorting process (by type).
     *
     * @param filteredFiles the filtered files to sort.
     */
    public TypeOrder(LinkedList<File> filteredFiles) {
        this.filteredFiles = filteredFiles;
    }

    /**
     * the second constructor with reversing the normal process of sorting.
     *
     * @param filteredFiles the filtered files to order.
     * @param reverse       the command to reverse.
     */
    public TypeOrder(LinkedList<File> filteredFiles, String reverse) {
        this.filteredFiles = filteredFiles;
        this.reverse = reverse;
    }

    /**
     * this method sorts the files by the type order.
     */
    @Override
    public void sortFiles() {
        if (filteredFiles.size() > 0) {
            quickSort(0, filteredFiles.size() - 1);

            if (this.reverse.equals(REVERSE))
                reversing();
        }
    }

    /**
     * this method sorts the left and right elements of the pivot recursively.
     *
     * @param start the start index.
     * @param end   the end index.
     */
    private void quickSort(int start, int end) {
        int partition = sortHelper(start, end);

        if (partition - 1 > start)
            quickSort(start, partition - 1);
        if (partition + 1 < end)
            quickSort(partition + 1, end);
    }

    /**
     * this method to help with sorting the files, by selecting a pivot and put the smaller items in the
     * left and the bigger items in the right.
     *
     * @param start the start index.
     * @param end   the end index.
     * @return the index of the pivot.
     */
    private int sortHelper(int start, int end) {
        File lastFile = filteredFiles.get(end);
        String lastExtension = getExtension(lastFile);

        for (int i = start; i < end; i++) {
            File currentFile = filteredFiles.get(i);
            String currentExtension = getExtension(currentFile);

            if (currentExtension.equals(lastExtension)) {
                boolean checkABS = (currentFile.getAbsolutePath().compareTo(lastFile.getAbsolutePath()) < 0);
                if (checkABS) {
                    filteredFiles.set(start, filteredFiles.set(i, filteredFiles.get(start)));
                    start++;
                }
            } else if (currentExtension.compareTo(lastExtension) < 0) {
                filteredFiles.set(start, filteredFiles.set(i, filteredFiles.get(start)));
                start++;
            }
        }

        filteredFiles.set(start, filteredFiles.set(end, filteredFiles.get(start)));
        return start;
    }

    /**
     * this method to get the extension of the given file.
     *
     * @param file the ile to get its extension.
     * @return the extension of the given file.
     */
    private String getExtension(File file) {
        int lastIndex = file.getName().lastIndexOf(PERIOD);
        String extension = "";
        if (lastIndex > 0 && lastIndex + 1 < file.getName().length()) {
            extension = file.getName().substring(lastIndex + 1);
        }
        return extension;
    }
}

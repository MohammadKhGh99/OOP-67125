package filesprocessing;

import java.io.File;
import java.util.LinkedList;

/**
 * this class describes the sorting method by size order.
 */
public class SizeOrder extends Order {

    /**
     * the first constructor without reversing the order of the ordered files.
     *
     * @param filteredFiles the filtered files to order.
     */
    public SizeOrder(LinkedList<File> filteredFiles) {
        this.filteredFiles = filteredFiles;
    }

    /**
     * the second constructor with reversing the order of the files.
     *
     * @param filteredFiles the filtered files that we want to order.
     * @param reverse       the revering command (reverse = "REVERSE").
     */
    public SizeOrder(LinkedList<File> filteredFiles, String reverse) {
        this.filteredFiles = filteredFiles;
        this.reverse = reverse;
    }

    /**
     * this method sorts the given files above by the size order.
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

        for (int i = start; i < end; i++) {
            File currentFile = filteredFiles.get(i);
            if (currentFile.length() == lastFile.length()) {
                boolean checkABS = (currentFile.getAbsolutePath().compareTo(lastFile.getAbsolutePath()) < 0);
                if (checkABS) {
                    filteredFiles.set(start, filteredFiles.set(i, filteredFiles.get(start)));
                    start++;
                }
            } else if (currentFile.length() < lastFile.length()) {
                filteredFiles.set(start, filteredFiles.set(i, filteredFiles.get(start)));
                start++;
            }
        }
        filteredFiles.set(start, filteredFiles.set(end, filteredFiles.get(start)));
        return start;
    }
}

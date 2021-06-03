package filesprocessing;

import java.io.File;
import java.util.LinkedList;

/**
 * this class describes the absolute order sorting method.
 */
public class AbsOrder extends Order {

    /**
     * th first constructor that takes the filtered files list.
     *
     * @param filteredFiles the filtered files that wew ant to sort.
     */
    public AbsOrder(LinkedList<File> filteredFiles) {
        this.filteredFiles = filteredFiles;
    }

    /**
     * the second constructor that takes the filtered files list and a string for reversing the order.
     *
     * @param filteredFiles the filtered files that wew ant to sort.
     * @param reverse       the string to reverse the order of the files.
     */
    public AbsOrder(LinkedList<File> filteredFiles, String reverse) {
        this.filteredFiles = filteredFiles;
        this.reverse = reverse;
    }

    /**
     * this method is made to sort the filtered files in their absolute order.
     */
    @Override
    public void sortFiles() {
        if (filteredFiles.size() > 0) {
            quickSort(0, filteredFiles.size() - 1);

            if (this.reverse.equals(REVERSE))
                reversing(); // reversing the order of the files if "REVERSE" is written in commands file.
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
        String lastABS = filteredFiles.get(end).getAbsolutePath();

        for (int i = start; i < end; i++) {
            String currentABS = filteredFiles.get(i).getAbsolutePath();

            if (currentABS.compareTo(lastABS) < 0) {
                filteredFiles.set(start, filteredFiles.set(i, filteredFiles.get(start)));
                start++;
            }
        }
        filteredFiles.set(start, filteredFiles.set(end, filteredFiles.get(start)));
        return start;
    }
}

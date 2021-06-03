package filesprocessing;

import java.io.File;
import java.util.LinkedList;

/**
 * this class is the Father of the other orders that inherit from it.
 */
public abstract class Order {

    /**
     * if we want to reverse we put "REVERSE" instead of "".
     */
    protected String reverse = "";

    /**
     * some Magic Numbers to help me in implementing the methods.
     */
    protected final static String REVERSE = "REVERSE", PERIOD = ".";

    /**
     * the filtered files list to order.
     */
    protected LinkedList<File> filteredFiles;


    /**
     * this method sorts the filtered files by certain constraints.
     */
    public abstract void sortFiles();


    /**
     * this method reverse the order.
     */
    public void reversing() {
        LinkedList<File> temp = new LinkedList<>(this.filteredFiles);
        this.filteredFiles.clear();
        int i = temp.size() - 1;
        while (i >= 0) {
            this.filteredFiles.add(temp.get(i));
            i--;
        }
    }
}


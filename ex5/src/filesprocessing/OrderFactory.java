package filesprocessing;

import java.io.File;
import java.util.LinkedList;

/**
 * this class describes the order factory that makes one order each time.
 */
public class OrderFactory {

    /**
     * some Magic Numbers to help me in coding.
     */
    private static final String ABS = "abs", TYPE = "type", SIZE = "size", SEPARATOR = "#", ORDER = "ORDER",
            REVERSE = "REVERSE";

    /**
     * this method making one order object by reading the line that under the ORDER word.
     *
     * @param orderLine     the order line that it is under the "ORDER" word.
     * @param filteredFiles the LinkedList of the filtered Files to order them.
     * @param numLine       the number of the current line number.
     * @param section       the current section.
     * @return an order object.
     */
    public static Order makingOneOrder(String orderLine, LinkedList<File> filteredFiles, int numLine,
                                       Section section) {
        if (!orderLine.equals(ORDER)) {
            if (orderLine.equals(""))
                return new AbsOrder(filteredFiles);
            String[] orderDesc = orderLine.split(SEPARATOR);
            String order = orderDesc[0];
            switch (order) {
                case TYPE:
                    if (orderDesc.length == 2) {
                        String reverseCheck = orderDesc[1];
                        if (reverseCheck.equals(REVERSE)) {
                            return new TypeOrder(filteredFiles, reverseCheck);
                        } else {
                            break;
                        }
                    }
                    return new TypeOrder(filteredFiles);

                case SIZE:
                    if (orderDesc.length == 2) {
                        String reverseCheck = orderDesc[1];
                        if (reverseCheck.equals(REVERSE)) {
                            return new SizeOrder(filteredFiles, reverseCheck);
                        } else {
                            break;
                        }
                    }
                    return new SizeOrder(filteredFiles);

                case ABS:
                    if (orderDesc.length == 2) {
                        String reverseCheck = orderDesc[1];
                        if (reverseCheck.equals(REVERSE)) {
                            return new AbsOrder(filteredFiles, reverseCheck);
                        } else {
                            break;
                        }
                    }
                    return new AbsOrder(filteredFiles);
                case "FILTER":
                    return new AbsOrder(filteredFiles);
            }
        }
        section.warnings.add("Warning in line " + numLine);
        return new AbsOrder(filteredFiles);
    }
}

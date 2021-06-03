import oop.ex3.spaceship.*;

public class TestFather {

    /**
     * the number of the successful tests that has been ran.
     */
    protected static int successfulTests;

    /**
     * the number of the current test.
     */
    protected static int currentTest;

    /**
     * the total number of Tests.
     */
    protected static int totalTests;

    /**
     * the number of all the tests for all the 3 classes.
     */
    protected static int testersTotalTests=0;

    /**
     * the array of the items that we want to use for the test.
     */
    protected static Item[] itemsArray = new Item[5];

    /**
     * this method to shorten the syntax of printing.
     *
     * @param x the parameter to print.
     */
    protected static void print(String x) {
        System.out.println(x);
    }

    /**
     * getter of the successfulTests field.
     *
     * @return the successfulTests field.
     */
    protected static int getSuccessfulTests() {
        return successfulTests;
    }

    /**
     * the getter of the totalTests field.
     *
     * @return the totalTests field.
     */
    protected static int getTotalTests() {
        return totalTests;
    }

    /**
     * just to save space in writing the error prints.
     * @return error message.
     */
    protected static String getMsg() {
        currentTest++;
        return "[" + currentTest + "/" + totalTests + "]";
    }

    /**
     * to save space when ending each test, when printing the result(success).
     */
    protected static void endingEachTest() {
        print("[" + currentTest + "/" + totalTests + "] test succeeded!!!");
        successfulTests++;
    }

    /**
     * initializing the array of items to test it.
     * @param itemsArray the array of items to be initialized.
     */
    protected static void initialization(Item[] itemsArray) {
        itemsArray[0] = ItemFactory.createSingleItem("baseball bat");
        itemsArray[1] = ItemFactory.createSingleItem("helmet, size 1");
        itemsArray[2] = ItemFactory.createSingleItem("helmet, size 3");
        itemsArray[3] = ItemFactory.createSingleItem("spores engine");
        itemsArray[4] = ItemFactory.createSingleItem("football");
    }
}

import org.junit.*;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * This class describes the LongTermStorage Test.
 */
public class LongTermTest extends TestFather {

    /**
     * the LongTermStorage that we want to test.
     */
    private static LongTermStorage lts = new LongTermStorage();

    /**
     * the return value when the removeItem and addItem methods success without moving any item.
     */
    private static final int SUCCESS = 0;

    /**
     * the return value when the removeItem and the addItem methods doesn't success.
     */
    private static final int NO_SUCCESS = -1;

    /**
     * the constructor of the LongTermTest class (default constructor).
     */
    public LongTermTest() {
    }

    @BeforeClass
    public static void beforeTestProcess() {

        print("\nRunning Package: LongTermStorage Actions Test - BEGIN");
        currentTest = 0;
        successfulTests = 0;
        totalTests = 16;
        initialization(itemsArray);
    }

    @Test
    public void runTests() {
        allAddingLTSTests();

        itemCountTest();

        resettingTest();
    }

    /**
     * all the adding to LongTerStorage Tests together.
     */
    private void allAddingLTSTests(){
        print("\nRunning Package: LongTermStorage addItem Method Test");
        addingLTSTest1();
        addingLTSTest2();
        addingLTSTest3();
    }

    private void addingLTSTest1() {

        // capacity = 1000
        // item0 volume = 2
        // item1 volume = 3
        // item2 volume = 5
        // item3 volume = 10

        // #1 = checking if the available capacity is the same as the capacity at the initializing.
        assertEquals(getMsg() + " the availableCapacity should be as the capacity in the initializing!",
                lts.getCapacity(), lts.getAvailableCapacity());
        endingEachTest();

        // #2 = testing normal adding.
        int normalAdding = lts.addItem(itemsArray[3], 20);
        assertEquals(getMsg() + " nothing in the LongTermStorage, the items should be added!", SUCCESS,
                normalAdding);
        endingEachTest();

        // #3 = checking if the available capacity changes or not.
        int newAvailableCap = lts.getCapacity() - (itemsArray[3].getVolume() * 20);
        assertEquals(getMsg() + " problem with available capacity!", newAvailableCap
                , lts.getAvailableCapacity());
        endingEachTest();
    }

    private void addingLTSTest2() {

        // #4 = testing adding more than the whole capacity.
        int addingMoreThanCapacity = lts.addItem(itemsArray[2], 210);
        assertEquals(getMsg() + " the amount of the items more than what the LongTermStorage can handle!", NO_SUCCESS,
                addingMoreThanCapacity);
        endingEachTest();

        // #5 = checking available capacity after exceeding adding.\
        int newAvailableCap = lts.getCapacity() - (itemsArray[3].getVolume() * 20);
        assertEquals(getMsg() + " problem with available capacity!", newAvailableCap, lts.getAvailableCapacity());
        endingEachTest();

        // #6 = testing adding more than the available capacity.
        lts.addItem(itemsArray[1], 100);
        int addingMoreThanAvailable = lts.addItem(itemsArray[2], 110);
        assertEquals(getMsg() + " the amount that you are trying to add is more than the available!",
                NO_SUCCESS, addingMoreThanAvailable);
        endingEachTest();
    }

    private void addingLTSTest3() {

        // #7 = testing adding zero amount of items.
        int addingZero = lts.addItem(itemsArray[0], 0);
        assertEquals(getMsg() + " the method should add zero items!", SUCCESS, addingZero);
        endingEachTest();

        // #8 = testing if the method added the item (it should not add it, because its amount is zero).
        assertNull(getMsg() + " the item should not be in the LongTermStorage!",
                lts.getInventory().get(itemsArray[0].getType()));
        endingEachTest();

        // #9 = testing adding the same type as one of the existing items.
        lts.addItem(itemsArray[3], 30);
        int itemCount = lts.getInventory().get(itemsArray[3].getType());
        assertEquals(getMsg() + " the amount of the item should update", 50, itemCount);
        endingEachTest();

    }

    private void itemCountTest() {

        print("\nRunning Package: Locker getItemCount Method Test");

        // #10 = testing if getItemCount method works well.
        int itemCount = lts.getInventory().get(itemsArray[3].getType());
        assertEquals(getMsg() + " getItemCount method problem!", itemCount,
                lts.getItemCount(itemsArray[3].getType()));
        endingEachTest();

        // #11 = testing if getItemCount gives a positive count for an item that is not existed in the
        // LongTermStorage.
        int actualNotExistedCounter=lts.getInventory().getOrDefault(itemsArray[4].getType(),0);
        int notExistedCount=lts.getItemCount(itemsArray[4].getType());
        assertEquals(getMsg()+" the getItemCount should not return a positive number of non existed item!",
                actualNotExistedCounter,notExistedCount);
        endingEachTest();

    }

    private void resettingTest() {

        print("\nRunning Package: Locker reset Method Test");

        // #12 = testing if the LongTermStorage is resetting well.
        lts.resetInventory();
        assertEquals(getMsg() + " the reset method is not working well!", new HashMap<String, Integer>(),
                lts.getInventory());
        endingEachTest();

        // #13 = checking the capacity and the available capacity after reset.
        assertEquals(getMsg()+" Error in capacity and available capacity after reset!",lts.getCapacity(),
                lts.getAvailableCapacity());
        endingEachTest();

        // #14 = testing if the LongTermStorage really reset or not.
        int numItem = lts.getInventory().getOrDefault(itemsArray[3].getType(), 0);
        assertEquals(getMsg() + " the LongTermStorage didn't reset!", 0, numItem);
        endingEachTest();

        // #15 = testing adding after reset.
        int normalAddingAfterReset = lts.addItem(itemsArray[3], 80);
        assertEquals(getMsg() + " reset LongTermStorage Problem!", SUCCESS, normalAddingAfterReset);
        endingEachTest();

        // #16 = testing item's amount after reset and adding.
        int itemCount = lts.getInventory().getOrDefault(itemsArray[3].getType(), 0);
        assertEquals(getMsg() + " the LongTermStorage didn't reset well!", 80, itemCount);
        endingEachTest();
    }

    /**
     * this method prints the finishing messages.
     */
    @AfterClass
    public static void finishing() {
        if (getSuccessfulTests() == getTotalTests()) {
            print("\n[V] Congrats All " + getTotalTests() + " LongTermStorage Tests Passed !!!\n");
        } else {
            print("\n[X] Error: some of your tests didn't pass !!! -_-");
        }
        print("LongTermStorage Actions Test - END\n");
        testersTotalTests+=totalTests;
    }
}

import oop.ex3.spaceship.*;
import org.junit.*;

import static org.junit.Assert.*;

/**
 * this class describes the Locker Test.
 */
public class LockerTest extends TestFather {

    /**
     * the LongTermStorage for the Lockers in this Test.
     */
    private final static LongTermStorage LTS = new LongTermStorage();

    /**
     * the first Locker that we want to test.
     */
    private static Locker locker1 = new Locker(LTS, 200, ItemFactory.getConstraintPairs());

    /**
     * the second Locker that we want to test.
     */
    private static Locker locker2 = new Locker(LTS, 300, ItemFactory.getConstraintPairs());

    // Magic Numbers - START //

    /**
     * the return value when the removeItem and the addItem methods doesn't success.
     */
    private static final int NO_SUCCESS = -1;

    /**
     * the return value when the removeItem and addItem methods success without moving any item.
     */
    private static final int SUCCESS = 0;

    /**
     * the return value when the addItem method success with moving to the LongTermStorage.
     */
    private static final int SUCCESS_WITH = 1;

    /**
     * the return value when we want to add one of the haters item to the Locker that the other in it.
     */
    private static final int CONSTRAINTS = -2;

    // Magic Numbers - END //

    /**
     * the constructor of the LockerTest class (default constructor).
     */
    public LockerTest() {
    }
    // #0 "baseball bat" = 2
    // #1 "helmet,size 1" = 3
    // #2 "helmet, size 3" = 5
    // #3 "spores engine" = 10
    // #4 "football" =4

    /**
     * the process to initialize the data members for this test.
     */
    @BeforeClass
    public static void beforeTestProcess() {

        print("\nRunning Package: Locker Actions Test - BEGIN");

        currentTest = 0;
        successfulTests = 0;
        totalTests = 27;
        initialization(itemsArray);
    }

    /**
     * the test that runs all the tests.
     */
    @Test
    public void runTests() {

        print("\nRunning Package: Locker Actions Test - BEGIN");

        addingItemTests();

        removingTests();

        getItemCountTest();
    }

    /**
     * starting all the adding items tests.
     */
    private void addingItemTests(){
        print("\nRunning Package: Locker addItem Method Test");
        addingTest1();
        addingTest2();
        addingTest3();
        addingWithConstraintsTests();
    }

    // capacity = 200
    // item1 volume = 3
    // item2 volume = 5
    // item3 volume = 10
    private void addingTest1() {

        // #1 = testing adding zero items.
        int addingZero = locker1.addItem(itemsArray[1], 0);// expected = 0
        assertEquals(getMsg() + " the addItem method should add zero amount of an item!", SUCCESS, addingZero);
        endingEachTest();

        // #2 = testing if the locker1 added the item (it should not add it, because its amount is zero).
        assertNull(getMsg() + " the item should not be in the locker!", locker1.getInventory().get(itemsArray[1].getType()));
        endingEachTest();

        // #3 = testing the normal adding without any problem.
        int normalAdding = locker1.addItem(itemsArray[2], 10); // expected = 0
        assertEquals(getMsg() + " the item should be added, because there is a place for it!", SUCCESS,
                normalAdding);
        endingEachTest();

        // #4 = checking if the item has been added or not.
        locker1.addItem(itemsArray[1], 10);
        int checkNum = locker1.getInventory().getOrDefault(itemsArray[1].getType(), 0);
        assertEquals(getMsg() + " the locker should add the item!", 10, checkNum);
        endingEachTest();
    }

    private void addingTest2() {
        // #5 = testing adding 50% of the capacity.
        int addingHalf = locker1.addItem(itemsArray[3], 10); // expected = 0
        assertEquals(getMsg() + " the item should be added, its percent of the capacity is 50%!", SUCCESS,
                addingHalf);
        endingEachTest();

        // #6 = testing the exceeding add (if an item exceed the half of the capacity.
        int exceededAdding = locker1.addItem(itemsArray[3], 1); // expected = 1
        assertEquals(getMsg() + " the item has exceeded the 50 percent, and the rest should be added to the " +
                "LongTerStorage!", SUCCESS_WITH, exceededAdding);
        endingEachTest();

        // #7 = checking if the exceeded amount of the item have been transferred to the LongTermStorage.
        int numOfAfterMoving = 11 - locker1.getInventory().getOrDefault(itemsArray[3].getType(), 0);
        int expected = LTS.getInventory().getOrDefault(itemsArray[3].getType(), 0);
        assertEquals(getMsg() + " the exceeded items should be deleted!", expected, numOfAfterMoving);
        endingEachTest();

        // #8 = testing if we want to add an item with an amount that exceeded the capacity.
        int addingMoreThanCapacity = locker1.addItem(itemsArray[2], 50); // expected = -1
        assertEquals(getMsg() + " you tried to add bigger than the capacity of the Locker!", NO_SUCCESS
                , addingMoreThanCapacity);
        endingEachTest();
    }

    private void addingTest3() {

        // #9 = testing if we want to add an item with an amount that exceeded the available capacity.
        int addingMoreThanAvailable = locker1.addItem(itemsArray[1], 40); // expected = -1
        assertEquals(getMsg() + " there is no capacity for this amount of items from the item that you want to " +
                "add!", NO_SUCCESS, addingMoreThanAvailable);
        endingEachTest();

        // #10 = checking the available capacity after adding the exceeded items to the LongTermStorage.
        int sumVolumes = 0;
        for (int i = 1; i < 4; i++) {
            sumVolumes += itemsArray[i].getVolume() * locker1.getInventory().getOrDefault(itemsArray[i].getType(), 0);
        }
        int availableCap = locker1.getCapacity() - (sumVolumes); // expected = 80
        assertEquals(getMsg() + " available capacity problem!", availableCap, locker1.getAvailableCapacity());
        endingEachTest();

    }

    /**
     * all the adding conditions when there is a constraints.
     */
    private void addingWithConstraintsTests(){
        addingConstraintsTest1();
        addingConstraintsTest2();
    }

    private void addingConstraintsTest1() {

        // #11 = testing adding one of the constraints pairs.
        int con1 = locker2.addItem(itemsArray[0], 20);
        assertEquals(getMsg() + " the item should be added!", SUCCESS, con1);
        endingEachTest();

        // #12 = checking the amount of the added item before.
        int itemCount = locker2.getItemCount(itemsArray[0].getType());
        assertEquals(getMsg() + " the should be added!", 20, itemCount);
        endingEachTest();

        // #13 = testing adding while there is a constraint.
        int con2 = locker2.addItem(itemsArray[4], 40);
        assertEquals(getMsg() + " the item should not be added to the locker, there is a constraint!",
                CONSTRAINTS, con2);
        endingEachTest();
    }

    private void addingConstraintsTest2(){

        // #14 = checking the number of the not added constraints pair.
        int itemCount = locker2.getItemCount(itemsArray[4].getType());
        assertEquals(getMsg() + " the item should not be added, there is a constraint!", 0, itemCount);
        endingEachTest();

        // #15 = testing adding any item not from the constraints pairs.
        int noConstraintsAdding = locker2.addItem(itemsArray[2], 10);
        assertEquals(getMsg() + " the item should be added, there is no constraints!", SUCCESS,
                noConstraintsAdding);
        endingEachTest();

        // #16 = checking the amount of the non-constraint adding item.
        itemCount = locker2.getItemCount(itemsArray[2].getType());
        assertEquals(getMsg() + " the item should be added, there is no constraintsQ!", 10, itemCount);
        endingEachTest();

    }

    /**
     * all the removing tests together.
     */
    private void removingTests(){
        print("\nRunning Package: Locker removeItem Method Test");

        removingTest1();
        removingTest2();
        removingTest3();
    }

    // capacity = 200
    // item0 volume = 2
    // item1 volume = 3
    // item2 volume = 5
    // item3 volume = 10
    // locker1 = spores engine=4, helmet, size 1=10, helmet, size 3=10
    private void removingTest1() {

        // #17 = testing removing zero items.
        int removingZero = locker1.removeItem(itemsArray[1], 0); // expected = 0
        assertEquals(getMsg() + " the removeItem method should remove zero items!", SUCCESS, removingZero);
        endingEachTest();

        // #18 = testing removing item that is not in the locker.
        int removeNotInLocker = locker1.removeItem(itemsArray[0], 3); // expected = -1
        assertEquals(getMsg() + " the method should not remove anything, the item is not in the locker!",
                NO_SUCCESS, removeNotInLocker);
        endingEachTest();

        // #19 = testing the normal removing without any problem.
        int normalRemoving = locker1.removeItem(itemsArray[1], 5);
        assertEquals(getMsg() + " the method should remove the item!", SUCCESS, normalRemoving);
        endingEachTest();

        // #20 = checking the amount of the normal removing item.
        int checkNum = locker1.getInventory().getOrDefault(itemsArray[1].getType(), 0);
        assertEquals(getMsg() + " the removeItem method didn't remove the items!", 5, checkNum);
        endingEachTest();
    }

    private void removingTest2() {
        // #21 = testing removing more than the available amount of a specific item.
        int removingMoreThanAvailable = locker1.removeItem(itemsArray[3], 5); // expected = -1
        assertEquals(getMsg() + " the amount of this item is less than what you want!",
                NO_SUCCESS, removingMoreThanAvailable);
        endingEachTest();

        // #22 = checking the available capacity after removing.
        int volume1 = itemsArray[1].getVolume() * locker1.getInventory().getOrDefault(itemsArray[1].getType(), 0);
        locker1.removeItem(itemsArray[2], 5);
        int volume2 = itemsArray[2].getVolume() * locker1.getInventory().getOrDefault(itemsArray[2].getType(), 0);
        locker1.removeItem(itemsArray[3], 2);
        int volume3 = itemsArray[3].getVolume() * locker1.getInventory().getOrDefault(itemsArray[3].getType(), 0);
        int availableCap = locker1.getCapacity() - (volume1 + volume2 + volume3); // expected = 140
        assertEquals(getMsg() + " available capacity problem!", availableCap, locker1.getAvailableCapacity());
        endingEachTest();
    }

    private void removingTest3() {
        // #23 = testing removing all the amount of a specific item.
        int removeAll = locker1.removeItem(itemsArray[1], 5);
        assertEquals(getMsg() + " the item should be removed from the locker!", SUCCESS, removeAll);
        endingEachTest();

        // #24 = checking if the item has been removed from the locker.
        assertNull(getMsg() + " the item should be deleted from the locker!",
                locker1.getInventory().get(itemsArray[1].getType()));
        endingEachTest();

        // #25 = testing removing a negative amount.
        int negativeRemoving = locker1.removeItem(itemsArray[2], -2);
        assertEquals(getMsg() + " the method cannot remove a negative amount!", NO_SUCCESS, negativeRemoving);
        endingEachTest();
    }

    private void getItemCountTest() {
        print("\nRunning Package: Locker getItemCount Method Test");

        // #26 = testing if getItemCount Method is working good or not.
        int count = locker1.getInventory().getOrDefault(itemsArray[3].getType(),0);
        int actualCount = locker1.getItemCount(itemsArray[3].getType());
        assertEquals(getMsg() + " the getItemCount method doesn't work well!", count, actualCount);
        endingEachTest();

        // #27 = testing if getItemCount gives a positive count for an item that is not existed in the locker.
        int actualNotExistedCounter=locker1.getInventory().getOrDefault(itemsArray[4].getType(),0);
        int notExistedCount=locker1.getItemCount(itemsArray[4].getType());
        assertEquals(getMsg()+" the getItemCount should not return a positive number of non existed item!",
                actualNotExistedCounter,notExistedCount);
        endingEachTest();
    }

    /**
     * this method prints the finishing messages.
     */
    @AfterClass
    public static void finishing() {
        if (getSuccessfulTests() == getTotalTests()) {
            print("\n[V] Congrats All " + getTotalTests() + " Locker Tests Passed !!!\n");
        } else {
            print("\n[X] Error: some of your tests didn't pass !!! -_-");
        }
        print("Locker Actions Test - END\n");
        testersTotalTests+=totalTests;
    }
}

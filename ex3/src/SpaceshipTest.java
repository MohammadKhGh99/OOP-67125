import org.junit.*;

import static org.junit.Assert.*;

/**
 * this class describes the Spaceship Test.
 */
public class SpaceshipTest extends TestFather {

    /**
     * the Spaceship that we want to test.
     */
    private static Spaceship spaceship;

    // Magic Numbers - START //

    /**
     * the return value for non existing crewID.
     */
    private final int INVALID_ID = -1;

    /**
     * the return value when the capacity is invalid.
     */
    private final int ILLEGAL_CAPACITY = -2;

    /**
     * the return value when we can't add more Lockers to the Spaceship.
     */
    private final int EXCEEDING_CAPACITY = -3;

    /**
     * the return value when the creating is successful.
     */
    private final int SUCCESS = 0;

    // Magic Numbers - END //

    /**
     * the constructor of the SpaceshipTest class (default constructor).
     */
    public SpaceshipTest() {
    }

    @BeforeClass
    public static void beforeTestProcess() {

        print("\nRunning Package: Spaceship Actions Test - BEGIN\n");

        currentTest = 0;
        successfulTests = 0;
        totalTests = 5;

        spaceship = new Spaceship("Apolo 16", new int[]{5, 8, 11, 5, 10}, 3);
    }

    @Test
    public void runTests() {
        createLockerTests();
    }

    /**
     * all teh create locker tests together.
     */
    private void createLockerTests() {
        createLockerTest1();
        createLockerTest2();
    }

    private void createLockerTest1() {

        // #1 = testing creating locker with non-existing crewId.
        int idNotExist = spaceship.createLocker(1, 100); // expected = -1
        assertEquals(getMsg() + " the crew id is not existing in the spaceship!", INVALID_ID, idNotExist);
        endingEachTest();

        // #2 = testing creating locker with negative capacity.
        int badCapacity = spaceship.createLocker(5, -100); // expected = -2
        assertEquals(getMsg() + " the given capacity is negative number!", ILLEGAL_CAPACITY, badCapacity);
        endingEachTest();

        // #3 = testing creating a locker with a zero capacity.
        int zeroCapacity = spaceship.createLocker(8, 0); // expected = 0
        assertEquals(getMsg() + " you can create a locker with zero capacity!", SUCCESS, zeroCapacity);
        endingEachTest();
    }

    private void createLockerTest2() {
        // #4 = testing normal creating a locker.
        int normalCreating = spaceship.createLocker(11, 100); // expected = 0
        assertEquals(getMsg() + " the test fail in creating a normal locker!", SUCCESS, normalCreating);
        endingEachTest();

        // #5 = testing creating more than the allowed number of lockers.
        spaceship.createLocker(5, 25);
        int exceedingLockers = spaceship.createLocker(10, 150); // expected = -3
        assertEquals(getMsg() + " you have exceeded the allowed number of lockers for this spaceship!",
                EXCEEDING_CAPACITY, exceedingLockers);
        endingEachTest();
    }

    /**
     * this method prints the finishing messages.
     */
    @AfterClass
    public static void finishing() {
        if (getSuccessfulTests() == getTotalTests()) {
            print("\n[V] Congrats All " + getTotalTests() + " Spaceship Tests Passed !!!\n");
        } else {
            print("\n[X] Error: some of your tests didn't pass !!! -_-");
        }
        print("Spaceship Actions Test - END");
        testersTotalTests += totalTests;
        print("\n[V] Congratulations All Your " + testersTotalTests + " Locker & LongTermStorage & " +
                "Spaceship Tests passed !!!");
    }
}

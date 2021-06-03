import oop.ex3.spaceship.ItemFactory;

import java.util.*;

/**
 * this is the class that describes the Spaceship.
 */
public class Spaceship {

    /**
     * the name of the current Spaceship.
     */
    private String name;
    /**
     * the array of the crew ids in the Spaceship.
     */
    private final int[] crewIDs;
    /**
     * the number of the Lockers in this Spaceship.
     */
    private int numOfLockers;
    /**
     * the LongTermStorage that is associated to this Spaceship.
     */
    private LongTermStorage lts;

    /**
     * the current number of lockers in this Spaceship.
     */
    private int currentNumOfLockers;

    /**
     * the array that contains the Lockers in this Spaceship.
     */
    private Locker[] lockersArray;

    /**
     * the mapping for each crew member, what locker he has.
     */
    private Map<Integer, ArrayList<Locker>> crewLockers;

    // Magic Numbers - START //

    /**
     * the return value for non existing crewID.
     */
    private final int INVALID_ID=-1;

    /**
     * the return value when the capacity is invalid.
     */
    private final int ILLEGAL_CAPACITY=-2;

    /**
     * the return value when we can't add more Lockers to the Spaceship.
     */
    private final int EXCEEDING_CAPACITY=-3;

    /**
     * the return value when the creating is successful.
     */
    private final int SUCCESS=0;

    // Magic Numbers - END //

    /**
     * the constructor that creates a Spaceship.
     *
     * @param name         the name of the Spaceship
     * @param crewIDs      the array of the crew's ids.
     * @param numOfLockers the number of Lockers in the Spaceship
     */
    public Spaceship(String name, int[] crewIDs, int numOfLockers) {
        this.name = name;
        this.crewIDs = crewIDs;
        this.numOfLockers = numOfLockers;
        this.lts = new LongTermStorage();
        this.currentNumOfLockers = 0;
        this.lockersArray = new Locker[this.numOfLockers];
        this.crewLockers = new HashMap<>();
    }

    /**
     * This method returns the long-term storage object associated with that Spaceship.
     *
     * @return the long-term storage object associated with that Spaceship.
     */
    public LongTermStorage getLongTermStorage() {
        return this.lts;
    }

    /**
     * This method creates a Locker object, and adds it as part of the Spaceship's storage.
     *
     * @param crewID   the crew member id that the created Locker will associated to him.
     * @param capacity the capacity of the Locker that we want to create.
     * @return -1 if the id is not valid,
     * -2 if the given capacity does not meet the Locker class requirements,
     * -3 if the Spaceship already contains the allowed number of lockers,
     * 0 if however a locker was created successfully.
     */
    public int createLocker(int crewID, int capacity) {
        if (!isIdExists(crewID))
            return INVALID_ID;

        else if (capacity < 0)
            return ILLEGAL_CAPACITY;

        else if (this.currentNumOfLockers + 1 > this.numOfLockers)
            return EXCEEDING_CAPACITY;

        else {
            Locker locker = new Locker(this.lts, capacity, ItemFactory.getConstraintPairs());
            this.lockersArray[this.currentNumOfLockers] = locker;
            if (this.crewLockers.get(crewID) == null) {
                this.crewLockers.put(crewID, new ArrayList<>());
            }
            this.crewLockers.get(crewID).add(locker);
            this.currentNumOfLockers++;
            return SUCCESS;
        }
    }

    //Helping Functions - Start//

    /**
     * this function checks if the crew id in the list of the crewIDs or not.
     * @param crewID the crewID that we want to check.
     * @return true if the crewID exists in the crewID, false otherwise.
     */
    private boolean isIdExists(int crewID) {
        for (int id : this.crewIDs)
            if (id == crewID)
                return true;
        return false;
    }

    //Helping Functions - End//

    /**
     * This methods returns an array with the crew's ids.
     *
     * @return an array with the crew's ids.
     */
    public int[] getCrewIDs() {
        return this.crewIDs;
    }

    /**
     * This methods returns an array of the Lockers, whose length is numOfLockers.
     * @return an array of the Lockers, whose length is numOfLockers.
     */
    public Locker[] getLockers() {
        return this.lockersArray;
    }
}

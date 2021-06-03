import oop.ex3.spaceship.*;

import java.util.*;

/**
 * this class describes the Locker.
 */
public class Locker implements Storage {

    /**
     * the capacity of this Locker.
     */
    private final int capacity;

    /**
     * the array of the constraints that they must not be together in this Locker.
     */
    private Item[][] constraints;

    /**
     * the LongTermStorage that this Locker belongs to.
     */
    private LongTermStorage lts;

    /**
     * the HashMap of the items and the amount of each type of item in this Locker.
     */
    private HashMap<String, Integer> itemsMap;

    /**
     * the current available capacity.
     */
    private int availableCapacity;

    // Magic Numbers - START //

    /**
     * the percentage to move the items of the same type to the LongTermStorage.
     */
    private static final double MOVE_PERCENT = 0.5;

    /**
     * the percentage that when we move the overloaded items from a certain Locker to the LongTermStorage,
     * this percentage will be the maximum of the items in the Locker.
     */
    private static final double MAX_AFTER_MOVE = 0.2;

    /**
     * the return value when the removeItem and addItem methods success without moving any item.
     */
    private static final int SUCCESS = 0;

    /**
     * the return value when the addItem method success with moving to the LongTermStorage.
     */
    private static final int SUCCESS_WITH = 1;

    /**
     * the return value when the removeItem and the addItem methods doesn't success.
     */
    private static final int NO_SUCCESS = -1;

    /**
     * the message that will be printed when the addItem method successes with successful moving to the
     * LongTermStorage.
     */
    private static final String MSG_WITH = "Warning: Action successful, but has caused items to be moved to " +
            "storage";

    /**
     * the return value when we want to add one of the haters item to the Locker that the other in it.
     */
    private static final int CONSTRAINTS = -2;

    // Magic Number - END //

    /**
     * the constructor that creates one Locker with its capacity and constraints array and the
     * LongTermStorage that belong to.
     *
     * @param lts         the LongTermStorage that this Locker belongs to.
     * @param capacity    the capacity of this Locker.
     * @param constraints the array of the constraints that they must not be together in this Locker.
     */
    public Locker(LongTermStorage lts, int capacity, Item[][] constraints) {
        this.lts = lts;
        this.capacity = capacity;
        this.constraints = constraints;
        this.itemsMap = new HashMap<>(this.capacity);
        this.availableCapacity = this.capacity;
    }

    /**
     * This method adds n Items of the given type to the locker.
     *
     * @param item the type of the item that we will add to this Locker.
     * @param n    the number of the items of the given type of item that we want to add.
     * @return 0 If the addition is successful and doesn't cause Items to be moved to long-term storage,
     * -1 If n Items cannot be added to the locker at this time, 1 If this action causes n* Items to be
     * moved to long-term storage and it can accommodate all n* Items, -1 If this action requires Items to
     * be moved to long-term storage, but it doesn't have room to accommodate all n* Items.
     */
    public int addItem(Item item, int n) {
        // if the n is negative.
        if (n < 0) {
            System.out.println("Error: Your request cannot be completed at this time.");
            return NO_SUCCESS;
        }
        // checking if there are constraints or not.
        if (!checkConstraints(item) && this.itemsMap.get(item.getType()) == null) {
            System.out.println("Error: Your request cannot be completed at this time. Problem: the locker " +
                    "cannot contain items of type " + item.getType() + ", as it contains a contradicting item");
            return CONSTRAINTS;
        }
        // checking if the amount of items multiply its volume bigger than the available capacity.
        if (item.getVolume() * n > this.capacity || item.getVolume() * n > this.availableCapacity) {
            System.out.println("Error: Your request cannot be completed at this time. " +
                    "Problem: no room for " + n + " items of type " + item.getType());
            return NO_SUCCESS;
        }
        // checking if the amount of this item exceeded the 50% of the whole capacity.
        double volume = item.getVolume() * (n + this.itemsMap.getOrDefault(item.getType(), 0));
        if ((volume / this.capacity) > MOVE_PERCENT) {
            int exceededNumItems = exceededItems(item, n);
            if (!movingToLTS(item, exceededNumItems)) {
                return NO_SUCCESS;
            } else {
                System.out.println(MSG_WITH);
                return SUCCESS_WITH;
            }
        }
        // containing the n=0 condition.
        if (n != 0) {
            this.itemsMap.put(item.getType(), n);
            this.availableCapacity -= (item.getVolume() * n);
        }
        return SUCCESS;
    }

    /**
     * This method removes n Items of the type type from the locker.
     *
     * @param item the item that we want to remove from this Locker.
     * @param n    the amount of the items that se want to remove.
     * @return 0 If the action is successful, -1 If there are less than n Items of this type in the locker,
     * -1 If n is negative.
     */
    public int removeItem(Item item, int n) {
        // if the n is negative.
        if (n < 0) {
            System.out.println("Error: Your request cannot be completed at this time. Problem: cannot " +
                    "remove a negative number of items of type " + item.getType());
            return NO_SUCCESS;
        }
        // if there is in the locker amount of items less than the given one.
        if (this.itemsMap.getOrDefault(item.getType(), 0) < n) {
            System.out.println("Error: Your request cannot be completed at this time. Problem: " +
                    "the locker does not contain " + n + " items of type " + item.getType());
            return NO_SUCCESS;
        }
        // normal removing.
        if (this.itemsMap.get(item.getType()) == n) {
            this.itemsMap.remove(item.getType());
        } else {
            int newNum = this.itemsMap.get(item.getType()) - n;
            this.itemsMap.put(item.getType(), newNum);
        }
        this.availableCapacity += (item.getVolume() * n);
        return SUCCESS;
    }

    /**
     * This method returns the number of Items of type type the locker contains.
     *
     * @param type the type of item that we want to return its amount.
     * @return the number of Items of type type the locker contains.
     */
    @Override
    public int getItemCount(String type) {
        return this.itemsMap.getOrDefault(type, 0);
    }

    /**
     * This method returns a map of all the item types contained in the locker, and their respective
     * quantities.
     *
     * @return A map of all the item types contained in the locker, and their respective quantities.
     */
    @Override
    public Map<String, Integer> getInventory() {
        return this.itemsMap;
    }

    /**
     * This method returns the locker's total capacity.
     *
     * @return the locker's total capacity.
     */
    @Override
    public int getCapacity() {
        return this.capacity;
    }

    /**
     * This method returns the locker's available capacity.
     *
     * @return the locker's available capacity.
     */
    @Override
    public int getAvailableCapacity() {
        return this.availableCapacity;
    }

    // Helping Functions - Start //

    /**
     * this function checks if there is a constrain that we must not exceed.
     *
     * @param item the item that we want to check with.
     * @return false if we exceed a constrain, true if we didn't exceed any constrain.
     */
    private boolean checkConstraints(Item item) {
        for (Item[] items : this.constraints) {
            Item first = items[0];
            Item second = items[1];
            if (first.getType().equals(item.getType())) {
                if (this.itemsMap.containsKey(second.getType()))
                    return false;
            } else if (second.getType().equals(item.getType())) {
                if (this.itemsMap.containsKey(first.getType()))
                    return false;
            }
        }
        return true;
    }

    /**
     * this function checks if we can move the exceeded items from the Locker to the LongTermStorage.
     *
     * @param item the item that we want to check.
     * @param n    the number of items that we want to move to LongTermStorage.
     * @return true if we can move them to the LongTermStorage, false otherwise.
     */
    private boolean movingToLTS(Item item, int n) {
        if (item.getVolume() * n <= this.lts.getAvailableCapacity()) {
            this.lts.addItem(item, n);
            return true;
        } else {
            return false;
        }
    }

    /**
     * this function return the amount of the exceeded items.
     *
     * @param item the item that we want to check if we can add it or not.
     * @param n    the number of items.
     * @return the amount of the exceeded items.
     */
    private int exceededItems(Item item, int n) {
        int oldNum = 0;
        if (this.itemsMap.containsKey(item.getType())) {
            oldNum = this.itemsMap.get(item.getType());
            this.itemsMap.put(item.getType(), n + oldNum);
        } else {
            this.itemsMap.put(item.getType(), n);
        }
        int itemsCounter = 0;
        int numItem = this.itemsMap.get(item.getType());
        double volume = numItem * item.getVolume();
        while ((volume / this.capacity) > MAX_AFTER_MOVE) {
            itemsCounter++;
            numItem--;
            volume = numItem * item.getVolume();
        }
        this.itemsMap.put(item.getType(), numItem);
        int newNum = this.itemsMap.get(item.getType());
        this.availableCapacity -= (item.getVolume() * (newNum - oldNum));
        return itemsCounter;
    }
    // Helping Functions - End //
}

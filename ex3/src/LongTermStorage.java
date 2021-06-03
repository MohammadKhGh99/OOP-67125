import oop.ex3.spaceship.Item;

import java.util.HashMap;
import java.util.Map;

/**
 * this class describes the LongTermStorage.
 */
public class LongTermStorage implements Storage {

    /**
     * the capacity of this Locker.
     */
    private final int capacity = 1000;

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
     * the return value when the removeItem and the addItem methods doesn't success.
     */
    private static final int NO_SUCCESS = -1;

    /**
     * the return value when the removeItem and addItem methods success without moving any item.
     */
    private static final int SUCCESS = 0;

    // Magic Number - END //

    /**
     * This constructor initializes a Long-Term Storage object.
     */
    public LongTermStorage() {
        this.itemsMap = new HashMap<>();
        this.availableCapacity = this.capacity;
    }

    /**
     * This method adds n Items of the given type to the LongTermStorage unit.
     *
     * @param item the item that we want to add to the LongTermStorage.
     * @param n    the amount of the items that we want to add to the LongTermStorage.
     * @return 0 If the action is successful,
     * -1 If n Items cannot be added to the locker at this time.
     */
    @Override
    public int addItem(Item item, int n) {
        if (n < 0) {
            System.out.println("Error: Your request cannot be completed at this time.");
            return NO_SUCCESS;
        }
        if (this.availableCapacity < item.getVolume() * n) {
            System.out.println("Error: Your request cannot be completed at this time. " +
                    "Problem: no room for " + n + " items of type " + item.getType());
            return NO_SUCCESS;
        }
        if (this.itemsMap.containsKey(item.getType())) {
            int oldNum = this.itemsMap.get(item.getType());
            this.itemsMap.put(item.getType(), oldNum + n);
        } else if (n == 0) {
            return SUCCESS;
        } else {
            this.itemsMap.put(item.getType(), n);
        }
        this.availableCapacity -= (item.getVolume() * n);
        return SUCCESS;
    }

    /**
     * This method resets the long-term storage's inventory.
     */
    public void resetInventory() {
        this.itemsMap.clear();
        this.availableCapacity = this.capacity;
    }

    /**
     * This method returns the number of Items of type type the long-term storage contains.
     *
     * @param type the type of item that we want to return its amount.
     * @return the number of Items of type type the long-term storage contains.
     */
    @Override
    public int getItemCount(String type) {
        return this.itemsMap.getOrDefault(type, 0);
    }

    /**
     * This method returns a map of all the Items contained in the long-term storage unit, and their
     * respective quantities.
     *
     * @return a map of all the Items contained in the long-term storage unit, and their respective
     * quantities.
     */
    @Override
    public Map<String, Integer> getInventory() {
        return this.itemsMap;
    }

    /**
     * Returns the long-term storage's total capacity.
     *
     * @return the long-term storage's total capacity.
     */
    @Override
    public int getCapacity() {
        return this.capacity;
    }

    /**
     * Returns the long-term storage's available capacity.
     *
     * @return the long-term storage's available capacity.
     */
    @Override
    public int getAvailableCapacity() {
        return this.availableCapacity;
    }

}

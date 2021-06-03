import oop.ex3.spaceship.Item;

import java.util.Map;

/**
 * this class describes any kind of storage.
 */
interface Storage {

    int addItem(Item item, int n);

    int getItemCount(String type);

    Map<String, Integer> getInventory();

    int getCapacity();

    int getAvailableCapacity();

}

public class ClosedHashSet extends SimpleHashSet {

    /**
     * the index for searchValue when we use the method "contains".
     */
    private int probIndex;

    /**
     * the number of elements in the hash set.
     */
    private int size;

    /**
     * the array that represents the ClosedHashSet "object".
     */
    private String[] closedSet;

    private final static double C = 2;

    /**
     * how many times we clamped to reach the required string.
     */
    private int hashAttempt;

    /**
     * A default constructor. Constructs a new, empty table with default initial capacity (16),
     * upper load factor (0.75) and lower load factor (0.25).
     */
    public ClosedHashSet() {
        super();
        this.size = 0;
        this.closedSet = new String[this.capacity];
    }

    /**
     * Constructs a new, empty table with the specified load factors, and the default initial capacity (16).
     *
     * @param upperLoadFactor The upper load factor of the hash table.
     * @param lowerLoadFactor The lower load factor of the hash table.
     */
    public ClosedHashSet(float upperLoadFactor, float lowerLoadFactor) {
        super(upperLoadFactor, lowerLoadFactor);
        this.size = 0;
        this.closedSet = new String[this.capacity];
    }

    /**
     * Data constructor - builds the hash set by adding the elements one by one. Duplicate values should be ignored.
     * The new table has the default values of initial capacity (16), upper load factor (0.75),
     * and lower load factor (0.25).
     *
     * @param data Values to add to the set.
     */
    public ClosedHashSet(String[] data) {
        super();
        this.size = 0;
        this.closedSet = new String[this.capacity];
        for (String s : data)
            add(s);
    }

    /**
     * Add a specified element to the set if it's not already in it.
     *
     * @param newValue New value to add to the set
     * @return False iff newValue already exists in the set
     */
    @Override
    public boolean add(String newValue) {
        if (!contains(newValue)) {
            if (((this.size + 1) / ((float) capacity())) > this.upperLoadFactor)
                resizing(INCREASE_CAPACITY);
            int attempt = 0;
            int index = 0;
            while (this.closedSet[index] != null && attempt < this.capacity) {
                attempt++;
                index = clamp((int) (newValue.hashCode() + (attempt + attempt * attempt) / C));
            }
            this.closedSet[index] = newValue;
            this.size++;
            return true;
        }
        return false;
    }

    /**
     * Look for a specified value in the set.
     *
     * @param searchVal Value to search for
     * @return True iff searchVal is found in the set
     */
    @Override
    public boolean contains(String searchVal) {
        for (int i = 0; i < this.capacity; i++) {
            int index = clamp((int) (searchVal.hashCode() + (i + i * i) / C));
            if (this.closedSet[index] != null && this.closedSet[index].equals(searchVal)) {
                this.hashAttempt = i;
                this.probIndex = index;
                return true;
            }
        }
        return false;
    }

    /**
     * Remove the input element from the set.
     *
     * @param toDelete Value to delete
     * @return True iff toDelete is found and deleted
     */
    @Override
    public boolean delete(String toDelete) {
        if (contains(toDelete)) {
            relocateSameHash(toDelete);
//            this.closedSet[this.probIndex] = null;
            this.size--;
            if (this.size / ((float) capacity()) < this.lowerLoadFactor && this.capacity > 1) {
                resizing(DECREASE_CAPACITY);
            }
            return true;
        }
        return false;
    }

    /**
     * @return The number of elements currently in the set.
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * this method resize the hash set when it is necessary, by giving the method an input of 2 for upSizing or 0.5
     * for downSizing.
     *
     * @param resize 2 for upSizing and 0.5 for downSizing.
     */
    private void resizing(double resize) {
        if (resize == 2 || resize == 0.5) {
            this.size = 0;
            String[] temp = this.closedSet;
            this.capacity = (int) (this.capacity * resize);
            this.closedSet = new String[this.capacity];
            for (String s : temp)
                if (s != null)
                    add(s);
        }
    }

    /**
     * this method relocates the strings, that have the same has, in the hash table after deleting on of them.
     *
     * @param toDelete tne string that we want to delete.
     */
    private void relocateSameHash(String toDelete) {
        int firstIndex = this.probIndex;
        int i = this.hashAttempt + 1;
        while (i < this.capacity && this.closedSet[firstIndex] != null) {
            int secondIndex = clamp((int) (toDelete.hashCode() + (i + i * i) / C));
            if (this.closedSet[secondIndex] != null) {
                this.closedSet[firstIndex] = this.closedSet[secondIndex];
            } else {
                this.closedSet[firstIndex] = null;
                break;
            }
            firstIndex = secondIndex;
            i++;
        }
        if (this.closedSet[this.probIndex] != null && this.closedSet[this.probIndex].equals(toDelete))
            this.closedSet[this.probIndex] = null;
    }
}

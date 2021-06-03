
public class OpenHashSet extends SimpleHashSet {

    /**
     * array of the wrapper class.
     */
    private WrapperHelper[] wrapperArray;

    /**
     * the index of the wanted value to delete.
     */
    private int deleteIndex;

    /**
     * the number of elements in the hash set.
     */
    private int size;

    /**
     * A default constructor. Constructs a new, empty table with default initial capacity (16),
     * upper load factor (0.75) and lower load factor (0.25).
     */
    public OpenHashSet() {
        super();
        this.size = 0;
        initializeOpenHashSet();
    }

    /**
     * Constructs a new, empty table with the specified load factors, and the default initial capacity (16).
     *
     * @param upperLoadFactor The upper load factor of the hash table.
     * @param lowerLoadFactor The lower load factor of the hash table.
     */
    public OpenHashSet(float upperLoadFactor, float lowerLoadFactor) {
        super(upperLoadFactor, lowerLoadFactor);
        this.size = 0;
        initializeOpenHashSet();
    }

    /**
     * Data constructor - builds the hash set by adding the elements one by one. Duplicate values should be ignored.
     * The new table has the default values of initial capacity (16), upper load factor (0.75),
     * and lower load factor (0.25).
     *
     * @param data Values to add to the set.
     */
    public OpenHashSet(String[] data) {
        super();
        this.size = 0;
        initializeOpenHashSet();
        for (String d : data) {
            add(d);
        }
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
            if (((this.size() + 1) / ((float) capacity())) > this.upperLoadFactor) {
                resizing(INCREASE_CAPACITY);
            }
            int index = clamp(newValue.hashCode());
            this.wrapperArray[index].list.add(newValue);
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
        int index = clamp(searchVal.hashCode());
        if (this.wrapperArray[index] != null) {
            this.deleteIndex = index;
            return this.wrapperArray[index].list.contains(searchVal);
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
            WrapperHelper wr = this.wrapperArray[this.deleteIndex];
            if (wr != null) {
                wr.list.remove(toDelete);
                this.size--;
                if (this.size() / (float) capacity() < this.lowerLoadFactor && this.capacity > 1)
                    resizing(DECREASE_CAPACITY);
                return true;
            }
        }
        return false;
    }

    /**
     * @return The number of elements currently in the set
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
            WrapperHelper[] temp = this.wrapperArray;
            this.capacity = (int) (this.capacity * resize);
            this.initializeOpenHashSet();
            for (WrapperHelper wr : temp)
                if (wr != null)
                    for (int i = 0; i < wr.list.size(); i++)
                        add(wr.list.get(i));
        }
    }

    /**
     * this method initialize the wrapperArray by initializing every index in the array.
     */
    private void initializeOpenHashSet() {
        this.wrapperArray = new WrapperHelper[this.capacity];
        for (int i = 0; i < this.wrapperArray.length; i++) {
            this.wrapperArray[i] = new WrapperHelper();
        }
    }
}


public abstract class SimpleHashSet implements SimpleSet {
    /**
     * Describes the higher load factor of a newly created hash set.
     */
    protected static float DEFAULT_HIGHER_CAPACITY = 0.75f;

    /**
     * Describes the lower load factor of a newly created hash set.
     */
    protected static float DEFAULT_LOWER_CAPACITY = 0.25f;

    /**
     * Describes the capacity of a newly created hash set.
     */
    protected static int INITIAL_CAPACITY = 16;

    /**
     * the lower load factor.
     */
    protected float lowerLoadFactor;

    /**
     * the upper load factor.
     */
    protected float upperLoadFactor;

    /**
     * the capacity of this HashSet.
     */
    protected int capacity = INITIAL_CAPACITY;

    /**
     * UpSizing Parameter.
     */
    protected final int INCREASE_CAPACITY = 2;

    /**
     * DownSizing Parameter.
     */
    protected final double DECREASE_CAPACITY = 0.5;

    /**
     * Constructs a new hash set with the default capacities given in DEFAULT_LOWER_CAPACITY and
     * DEFAULT_HIGHER_CAPACITY.
     */
    protected SimpleHashSet() {
        this.upperLoadFactor = DEFAULT_HIGHER_CAPACITY;
        this.lowerLoadFactor = DEFAULT_LOWER_CAPACITY;
    }

    /**
     * Constructs a new hash set with capacity INITIAL_CAPACITY
     *
     * @param upperLoadFactor the upperLoadFactor of the new hash set.
     * @param lowerLoadFactor the lowerLoadFactor of the new hash set.
     */
    protected SimpleHashSet(float upperLoadFactor, float lowerLoadFactor) {
        this.upperLoadFactor = upperLoadFactor;
        this.lowerLoadFactor = lowerLoadFactor;
    }

    /**
     * returns The current capacity (number of cells) of the table.
     *
     * @return The current capacity (number of cells) of the table.
     */
    protected int capacity(){
        return this.capacity;
    }

    /**
     * Clamps hashing indices to fit within the current table capacity (see the exercise description for details).
     *
     * @param index the index before clamping.
     * @return an index properly clamped.
     */
    protected int clamp(int index) {
        return index & (capacity() - 1);
    }

    /**
     * returns the lower load factor of the table.
     *
     * @return the lower load factor of the table.
     */
    protected float getLowerLoadFactor() {
        return this.lowerLoadFactor;
    }

    /**
     * returns the higher load factor of the table.
     *
     * @return the higher load factor of the table.
     */
    protected float getUpperLoadFactor() {
        return this.upperLoadFactor;
    }
}

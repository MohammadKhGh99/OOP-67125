import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.TreeSet;

public class SimpleSetPerformanceAnalyzer {

    private String[] data1, data2;

    private SimpleSet[] DAST;

    private final static long TO_MILLI = 1000000, WARM_UP = 70000, LIST_WARM_UP = 7000;

    private final static String FIRST_FILE = "src/data1.txt", SECOND_FILE = "src/data2.txt";

    private final static int INDEX_OF_LINKED_LIST = 0, NUM_OF_SETS = 5;

    private final static String HI = "hi", NEGATIVE = "-13170890158", NUMBER = "23";

    public long[] data1AddingTime, data2AddingTime, data1NegativeTimes, data2NumTimes;

    public SimpleSetPerformanceAnalyzer() {
        this.data1AddingTime = new long[NUM_OF_SETS];
        this.data2AddingTime = new long[NUM_OF_SETS];
        this.data1NegativeTimes = new long[NUM_OF_SETS];
        this.data2NumTimes = new long[NUM_OF_SETS];
        this.data1 = Ex4Utils.file2array(FIRST_FILE);
        this.data2 = Ex4Utils.file2array(SECOND_FILE);
    }

    /**
     * this method initialize the data structure with the default values.
     */
    public void initializingDAST() {
        CollectionFacadeSet linkedList = new CollectionFacadeSet(new LinkedList<>());
        CollectionFacadeSet treeSet = new CollectionFacadeSet(new TreeSet<>());
        CollectionFacadeSet hashSet = new CollectionFacadeSet(new HashSet<>());
        OpenHashSet openHashSet = new OpenHashSet();
        ClosedHashSet closedHashSet = new ClosedHashSet();
        this.DAST = new SimpleSet[]{linkedList, treeSet, hashSet, openHashSet, closedHashSet};
    }

    /**
     * this method for adding data1.txt strings analysis.
     */
    public void data1AddingAnalysis() {
        initializingDAST();// initializing data structures.
        for (int i = 0; i < NUM_OF_SETS; i++) {
            this.data1AddingTime[i] = addSection(this.DAST[i], this.data1);
        }
    }

    /**
     * this method for adding data2.txt strings analysis.
     */
    public void data2AddingAnalysis() {
        initializingDAST();// initializing data structures.
        for (int i = 0; i < NUM_OF_SETS; i++) {
            this.data2AddingTime[i] = addSection(this.DAST[i], this.data2);
        }
    }

    /**
     * this method analyzes the adding process.
     *
     * @param simpleSet the simpleSet that we want to add the data's strings to it.
     * @param data      the array of strings that we want to add to simpleSet.
     */
    private long addSection(SimpleSet simpleSet, String[] data) {
        long timeBefore = System.nanoTime();
        for (String s : data) {
            simpleSet.add(s);
        }
        long difference = (System.nanoTime() - timeBefore) / TO_MILLI;
        System.out.println(difference);
        return difference;
    }

    /**
     * this method analyzes the contains method for the string "hi".
     */
    public long[] dataHiContainsAnalysis() {
        long[] results = new long[NUM_OF_SETS];
        for (int i = 0; i < this.DAST.length; i++) {
            if (i != INDEX_OF_LINKED_LIST)
                results[i] = containsAnalysis(this.DAST[i], WARM_UP, HI);
            else results[i] = containsAnalysis(this.DAST[i], LIST_WARM_UP, HI);

        }
        return results;
    }

    /**
     * this method analyzes the contains method for the string "-13170890158".
     */
    public void data1NegativeContainsAnalysis() {
        for (int i = 0; i < this.DAST.length; i++) {
            if (i != INDEX_OF_LINKED_LIST)
                this.data1NegativeTimes[i] = containsAnalysis(this.DAST[i], WARM_UP, NEGATIVE);
            else this.data1NegativeTimes[i] = containsAnalysis(this.DAST[i], LIST_WARM_UP, NEGATIVE);

        }
    }

    /**
     * this method analyzes the contains method for the string "23".
     */
    public void data2Contains23Analysis() {
        for (int i = 0; i < this.DAST.length; i++) {
            if (i != INDEX_OF_LINKED_LIST)
                this.data2NumTimes[i] = containsAnalysis(this.DAST[i], WARM_UP, NUMBER);
            else this.data2NumTimes[i] = containsAnalysis(this.DAST[i], LIST_WARM_UP, NUMBER);

        }
    }

    /**
     * this method analyze the time for every contains method we want to check.
     *
     * @param simpleSet    the data structure that we want to check.
     * @param warmUp       the number of check times.
     * @param valueToCheck the value that we want to check with the contains method.
     */
    private long containsAnalysis(SimpleSet simpleSet, long warmUp, String valueToCheck) {
        // Warming Up
        for (int i = 0; i < warmUp; i++) {
            simpleSet.contains(valueToCheck);
        }

        long timeBefore = System.nanoTime();
        for (int i = 0; i < warmUp; i++) {
            simpleSet.contains(valueToCheck);
        }
        long difference = (System.nanoTime() - timeBefore) / warmUp;
        System.out.println(difference);
        return difference;
    }

    public static void main(String[] args) {
        SimpleSetPerformanceAnalyzer s = new SimpleSetPerformanceAnalyzer();

//        System.out.println("LinkedList  TreeSet  HashSet  OpenHashSet  ClosedHashSet");

        s.data1AddingAnalysis();// adding process analysis.
//        System.out.println("Data1 Adding Time: " + "\n" + Arrays.toString(s.data1AddingTime) + "\n");

        long[] data1HiResults = s.dataHiContainsAnalysis();// contains("hi") process analysis.
//        System.out.println("Data1 Hi Time: " + "\n" + Arrays.toString(data1HiResults) + "\n");

        s.data1NegativeContainsAnalysis();// contains("-13170890158") process analysis.
//        System.out.println("Data1 Negative Number Time: " + "\n" + Arrays.toString(s.data1NegativeTimes) + "\n");

        s.data2AddingAnalysis();// adding process analysis.
//        System.out.println("Data2 Adding Time: " + "\n" + Arrays.toString(s.data2AddingTime) + "\n");

        s.data2Contains23Analysis();// contains("23") process analysis.
//        System.out.println("Data2 Positive Number Time: " + "\n" + Arrays.toString(s.data2NumTimes) + "\n");

        long[] data2HiResults = s.dataHiContainsAnalysis();// contains("hi") process analysis.
//        System.out.println("Data2 Hi Time: " + "\n" + Arrays.toString(data2HiResults) + "\n");

    }
}

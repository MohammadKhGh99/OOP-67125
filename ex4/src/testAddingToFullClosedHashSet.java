/**
 * tests adding the 17th value to a ClosedHashSet with 16 values and upperLoadFactor = 1
 * see https://moodle2.cs.huji.ac.il/nu18/mod/forum/discuss.php?d=53743
 */
public class testAddingToFullClosedHashSet {
    public static void main(String[] args) {
        String[] data = new String[16];
        for (int i = 0; i < 16; i++) data[i] = "Meow" + i;
        ClosedHashSet closey = new ClosedHashSet(1,0.25f);
        for (String value : data) closey.add(value);
        closey.add("HELLO_WORLD");
        String result = closey.capacity() == 32 ? "PASSED: Capacity is 32 as expected" :
                "FAILED: Capacity is not 32";
        System.out.println(result);
    }
}

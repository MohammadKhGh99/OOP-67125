import oop.ex3.searchengine.Hotel;
import org.junit.*;

import static org.junit.Assert.*;

/**
 * this class describes the Booping Site Test.
 */
public class BoopingSiteTest {

    /**
     * the number of the successful tests that has been ran.
     */
    private static int successfulTests;

    /**
     * the number of the current test.
     */
    private static int currentTest;

    /**
     * the total number of Tests.
     */
    private static int totalTests;

    private static BoopingSite booping1, boopingEmptyFile2, booping3;

    private static final String CITY_NAME = "manali";

    private static final double LATITUDE = 0.0, LONGITUDE = 0.0, ILLEGAL_LATITUDE = -100,
            ILLEGAL_LONGITUDE = 200;

    private static Hotel[] sortedHotelInCityByRating1, sortedHotelInCityByRating2, sortedHotelInCityByRating3;
    private static Hotel[] sortedHotelsByProximity1, sortedHotelsByProximity2, sortedHotelsByProximity3;
    private static Hotel[] sortedHotelInCityByProximity1,sortedHotelInCityByProximity2,
            sortedHotelInCityByProximity3;

    /**
     * the default constructor for the BoopingSiteTest class.
     */
    public BoopingSiteTest() {
    }

    @BeforeClass
    public static void beforeTestProcess() {
        successfulTests = 0;
        currentTest = 0;
        totalTests = 12;

        // the txt file that contains just the hotels in "manali" city.
        booping1 = new BoopingSite("hotels_tst1.txt");

        // the txt file that contains nothing.
        boopingEmptyFile2 = new BoopingSite("hotels_tst2.txt");

        // the txt file that contains more than 3000 hotels.
        booping3 = new BoopingSite("hotels_dataset.txt");

        sortedHotelInCityByRating1 = booping1.getHotelsInCityByRating(CITY_NAME);
        sortedHotelInCityByRating2 = boopingEmptyFile2.getHotelsInCityByRating(CITY_NAME);// empty array
        sortedHotelInCityByRating3 = booping3.getHotelsInCityByRating(CITY_NAME);

        sortedHotelsByProximity1 = booping1.getHotelsByProximity(LATITUDE, LONGITUDE);
        sortedHotelsByProximity2 = boopingEmptyFile2.getHotelsByProximity(LATITUDE, LONGITUDE);
        sortedHotelsByProximity3 = booping3.getHotelsByProximity(LATITUDE, LONGITUDE);

        sortedHotelInCityByProximity1=booping1.getHotelsInCityByProximity(CITY_NAME,LATITUDE,LONGITUDE);
        sortedHotelInCityByProximity2= boopingEmptyFile2.getHotelsInCityByProximity(CITY_NAME,LATITUDE,LONGITUDE);
        sortedHotelInCityByProximity3=booping3.getHotelsInCityByProximity(CITY_NAME,LATITUDE,LONGITUDE);

    }

    @Test
    public void runTests() {

        print("\nRunning Package: BoopingSite Test - BEGIN\n");

        allGetHotelsInCityByRatingTests();
        getHotelsByProximityTest();
        allGetHotelsInCityByProximityTests();

    }

    /**
     * all the getHotelsInCityByRating tests together.
     */
    private void allGetHotelsInCityByRatingTests(){
        getHotelsInCityByRatingTest1();
        getHotelsInCityByRatingTest2();
    }

    /**
     * this method for testing the getHotelsInCityByRating method.
     */
    private void getHotelsInCityByRatingTest1() {

        // #1 = testing if the getHotelsInCityByRating method is working well with a hotels file with hotels
        // in the same city.
        boolean isSorted = isSortedByStarRating(sortedHotelInCityByRating1);
        assertTrue(getMsg() + " the getHotelsInCityByRating method did not sort by Star Rating!", isSorted);
        endingEachTest();

        // #2 = testing if the method getHotelsInCityByRating is working with an empty file and returns an
        // empty array.
        assertArrayEquals(getMsg() + " the getHotelsInCityByRating method doesn't work well with an " +
                "empty hotels file!", new Hotel[0], sortedHotelInCityByRating2);
        endingEachTest();

        // #3 = testing if the method getHotelsInCityByRating has filtered the hotels by the city name or
        // not.
        boolean isFilteredByCity = isFilteredByCity(sortedHotelInCityByRating3);
        assertTrue(getMsg() + " the getHotelsInCityByRating method didn't work well with the file with the " +
                "whole hotels!", isFilteredByCity);
        endingEachTest();
    }

    private void getHotelsInCityByRatingTest2(){

        // #4 = testing if the method getHotelsInCityByRating has sorted the hotels by the star rating or
        // not.
        boolean isSortedByStarRating = isSortedByStarRating(sortedHotelInCityByRating3);
        assertTrue(getMsg() + " the getHotelsInCityByRating method didn't sort the hotels as well!",
                isSortedByStarRating);
        endingEachTest();

        // #5 = testing if the method getHotelsInCityByRating sorts by alphabet when the star rating is the
        // same for two or more hotels.
        boolean sortedOrNot = isSortedByStarRatingAndPropertyName(sortedHotelInCityByRating1);
        assertTrue(getMsg() + " the method getHotelsInCityByRating didn't sort the hotels that have the same " +
                "star rating by alphabet order!", sortedOrNot);
        endingEachTest();
    }

    private void getHotelsByProximityTest() {

        Hotel[] illegalImplications = booping1.getHotelsByProximity(ILLEGAL_LATITUDE, ILLEGAL_LONGITUDE);

        // #6 = testing the getHotelsByProximity method when giving it illegal inputs.
        assertArrayEquals(getMsg() + " the getHotelsInCityByRating method got illegal inputs!",
                new Hotel[0], illegalImplications);
        endingEachTest();

        // #7 = testing if the method getHotelsByProximity sorting the euclidean distances for the hotels.
        boolean proximitySorted = isSortedByProximity(sortedHotelsByProximity3);
        assertTrue(getMsg()+" the getHotelsByProximity method didn't sort the hotels by proximity and " +
                        "property name!", proximitySorted);
        endingEachTest();

        // #8 = testing an empty file as an argument to getHotelsByProximity.
        assertArrayEquals(getMsg()+" the method getHotelsByProximity didn't work well with an empty file",
                new Hotel[0],sortedHotelsByProximity2);
        endingEachTest();
    }

    /**
     * all the getHotelsInCityByProximity tests together.
     */
    private void allGetHotelsInCityByProximityTests(){
        getHotelsInCityByProximityTest1();
        getHotelsInCityByProximityTest2();
    }

    private void getHotelsInCityByProximityTest1() {

        // #9 = testing an empty file input to getHotelsInCityByProximity.
        assertArrayEquals(getMsg() + " the method getHotelsInCityByProximity should return an empty array!",
                new Hotel[0], sortedHotelInCityByProximity2);
        endingEachTest();

        // #10 = testing if the method getHotelsInCityByProximity filtered the hotels by the city.
        boolean filteredByCity = isFilteredByCity(sortedHotelInCityByProximity3);
        assertTrue(getMsg() + " the method getHotelsInCityByProximity didn't filter the hotels by city name!"
                , filteredByCity);
        endingEachTest();
    }

    private void getHotelsInCityByProximityTest2(){

        // #11 = testing if the method getHotelsInCityByProximity sorted by proximity and when two of the
        // hotels are at the same distance it will sort by number of POI.
        boolean sortedByProximityAndPOI=isSortedByProximity(sortedHotelsByProximity3);
        assertTrue(getMsg()+" the method getHotelsInCityByProximity is not working well!",
                sortedByProximityAndPOI);
        endingEachTest();

        // #12 = testing when sorting by proximity and when sorting in city by proximity for a file of
        // hotels that are in the same city.
        assertArrayEquals(getMsg()+" all the hotels in the file are in the same city, so the " +
                "getHotelsByProximity and getHotelsInCityByProximity methods will return the same array!",
                sortedHotelsByProximity1,sortedHotelInCityByProximity1);
        endingEachTest();
    }

    /**
     * this method prints the finishing messages.
     */
    @AfterClass
    public static void finishing() {
        if (successfulTests == totalTests) {
            print("\n[V] Congrats All " + totalTests + " BoopingSite Tests Passed !!!\n");
        } else {
            print("\n[X] Error: some of your tests didn't pass !!! -_-");
        }
    }

    // Helping Functions - BEGIN //

    /**
     * this method checks if the given array of hotels is sorted by star rating and if two or more of them
     * has the same star rating it will sort by property name.
     * @param hotels the array of hotels that we want to check.
     * @return false if not sorted as wanted, true if sorted.
     */
    private boolean isSortedByStarRatingAndPropertyName(Hotel[] hotels) {
        for (int i = 0; i < hotels.length - 1; i++)
            if (hotels[i].getStarRating() == hotels[i + 1].getStarRating())
                if (hotels[i].getPropertyName().compareTo(hotels[i + 1].getPropertyName()) > 0)
                    return false;
        return true;
    }

    /**
     * this method checks if the given array of hotels is sorted by proximity of the given location.
     * @param hotels the array of hotels that we want to check.
     * @return false if the array is not sorted by proximity, true if sorted.
     */
    private boolean isSortedByProximity(Hotel[] hotels) {
        for (int i = 0; i < hotels.length - 1; i++) {
            double x1 = hotels[i].getLatitude(), x2 = hotels[i + 1].getLatitude(), y1 = hotels[i].getLongitude(),
                    y2 = hotels[i + 1].getLongitude();
            double firstDistance = euclideanDistance( x1,y1,LATITUDE, LONGITUDE);
            double secondDistance = euclideanDistance( x2,y2,LATITUDE, LONGITUDE);
            if (secondDistance<firstDistance) {
                print(String.valueOf(i));
                return false;
            }else if (firstDistance==secondDistance){
                if (hotels[i + 1].getNumPOI() > hotels[i].getNumPOI()) {
                    print(String.valueOf(i));
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * this method checks if the given array of hotels sorted by star rating or not.
     * @param hotels the array of hotels that we want to check.
     * @return false if the array of hotels is not sorted by start rating, true if sorted.
     */
    private boolean isSortedByStarRating(Hotel[] hotels) {
        for (int i = 0; i < hotels.length - 1; i++)
            if (hotels[i].getStarRating() < hotels[i + 1].getStarRating())
                return false;
        return true;
    }

    /**
     * this method checks if the given array filtered by city name.
     * @param hotels the array of hotels that we want to check.
     * @return false if the array is not filtered by city name, true otherwise.
     */
    private boolean isFilteredByCity(Hotel[] hotels) {
        for (Hotel hotel : hotels)
            if (!hotel.getCity().equals(CITY_NAME))
                return false;
        return true;
    }


    /**
     * this method to shorten the syntax of printing.
     *
     * @param x the parameter to print.
     */
    private static void print(String x) {
        System.out.println(x);
    }

    /**
     * just to save space in writing the error prints.
     *
     * @return error message.
     */
    private static String getMsg() {
        currentTest++;
        return "[" + currentTest + "/" + totalTests + "]";
    }

    /**
     * to save space when ending each test, when printing the result(success).
     */
    private static void endingEachTest() {
        print("[" + currentTest + "/" + totalTests + "] test succeeded!!!");
        successfulTests++;
    }

    /**
     * this method calculates the Euclidean distance between two locations.
     *
     * @param x1 the first location's latitude.
     * @param y1 the first location's longitude.
     * @param x2 the second location's latitude.
     * @param y2 the second location's longitude.
     * @return the Euclidean Distance.
     */
    private double euclideanDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    // Helping Fuhnctions - END //
}

import oop.ex3.searchengine.Hotel;
import oop.ex3.searchengine.HotelDataset;

import java.util.*;

/**
 * this class describes the Booping Site.
 */
public class BoopingSite {

    /**
     * the name of the dataset.
     */
    private String name;

    /**
     * the array of the hotels in the given dataset.
     */
    private Hotel[] hotelsArray;

    /**
     * the minimum and maximum of the latitude.
     */
    private static final double MIN_LATITUDE=-90,MAX_LATITUDE=90,MIN_LONGITUDE=-180,MAX_LONGITUDE=180;

    /**
     * the constructor of this class.
     *
     * @param name the name of the dataset.
     */
    public BoopingSite(String name) {
        this.name = name;
        this.hotelsArray = HotelDataset.getHotels(this.name);
    }

    /**
     * This method returns an array of hotels located in the given city, sorted from the highest
     * star-rating to the lowest.
     *
     * @param city the city that we want to filter the hotels with.
     * @return an array of hotels located in the given city.
     */
    public Hotel[] getHotelsInCityByRating(String city) {
        List<Hotel> hotelsList = filteringByCity(this.hotelsArray, city);
        if (hotelsList.size() == 0)
            return new Hotel[0];
        hotelsList.sort(new Comparator<Hotel>() {
            @Override
            public int compare(Hotel o1, Hotel o2) {
                int comparingRating = Integer.compare(o2.getStarRating(), o1.getStarRating());
                if (comparingRating == 0) {
                    return o1.getPropertyName().compareTo(o2.getPropertyName());
                }
                return comparingRating;
            }
        });
        return convertingListToArray(hotelsList);
    }

    /**
     * This method returns an array of hotels, sorted according to their Euclidean distance from the given
     * geographic location, in ascending order.
     *
     * @param latitude  the latitude of the wanted location.
     * @param longitude the longitude of the wanted location.
     * @return an array of hotels.
     */
    public Hotel[] getHotelsByProximity(double latitude, double longitude) {
        List<Hotel> hotelsList = new LinkedList<>();
        boolean latitudeBounds=(latitude > MAX_LATITUDE || latitude < MIN_LATITUDE);
        boolean longitudeBounds=(longitude < MIN_LONGITUDE || longitude > MAX_LONGITUDE);
        if (latitudeBounds || longitudeBounds) {
            return new Hotel[0];
        }
        for (Hotel hotel : this.hotelsArray)
            hotelsList.add(hotel);
        hotelsList.sort(new Comparator<Hotel>() {
            @Override
            public int compare(Hotel o1, Hotel o2) {
                double distance1 = euclideanDistance(o1.getLatitude(), o1.getLongitude(), latitude, longitude);
                double distance2 = euclideanDistance(o2.getLatitude(), o2.getLongitude(), latitude, longitude);
                int comparingDistance = Double.compare(distance1, distance2);
                if (comparingDistance == 0) {
                    return Integer.compare(o2.getNumPOI(), o1.getNumPOI());
                }
                return comparingDistance;
            }
        });
        return convertingListToArray(hotelsList);
    }

    /**
     * This method returns an array of hotels in the given city, sorted according to their Euclidean distance
     * from the given geographic location, in ascending order.
     *
     * @param city      the city that we want to filter the hotels by.
     * @param latitude  the latitude of the wanted location.
     * @param longitude the longitude of the wanted location.
     * @return an array of hotels.
     */
    public Hotel[] getHotelsInCityByProximity(String city, double latitude, double longitude) {
        List<Hotel> hotelsList = new LinkedList<>();
        for (Hotel hotel : getHotelsByProximity(latitude, longitude)) {
            if (hotel.getCity().equals(city)) {
                hotelsList.add(hotel);
            }
        }
        return convertingListToArray(hotelsList);
    }

    // Helping Methods - START //

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

    /**
     * this method filters the hotels by city name.
     *
     * @param city the city that we want to filter with.
     * @return a list with the filtered hotels.
     */
    private List<Hotel> filteringByCity(Hotel[] newHotelsArray, String city) {
        List<Hotel> filteredHotelsByCity = new LinkedList<>();
        for (Hotel hotel : newHotelsArray) {
            if (hotel.getCity().equals(city)) {
                filteredHotelsByCity.add(hotel);
            }
        }
        return filteredHotelsByCity;
    }

    /**
     * this method converting a List to an Array.
     *
     * @param list the list to be converted.
     * @return an array of the elements of the given list.
     */
    private Hotel[] convertingListToArray(List<Hotel> list) {
        Hotel[] hotelsArray = new Hotel[list.size()];
        for (int i = 0; i < list.size(); i++) {
            hotelsArray[i] = list.get(i);
        }
        return hotelsArray;
    }
    // Helping methods - END //
}

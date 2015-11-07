package rampupweekend8.coffeenews;

/**
 * Created by Doug on 06/11/2015.
 */
public class Partner {
    private static long idCounter = 0;

    public final long id;
    public final double latitude, longitude;
    public final String description;

    public Partner(double latitude, double longitude, String description) {
        this.id = nextId();
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
    }

    private static long nextId() {
        return ++idCounter;
    }
}

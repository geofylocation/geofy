package rampupweekend8.coffeenews;

import android.location.Location;

/**
 * Created by Doug on 06/11/2015.
 */
public class Partners {

    private static final float THRESHOLD_METERS = 25;

    private static Partner[] PARTNERS = new Partner[] {
        new Partner(49.85984, -97.12605, "Doug's House"),
        new Partner(49.80319, -97.14977, "One Innovation Drive"),

    };

    public static Partner getPartnerAtLocation(double currentLat, double currentLong) {
        for (Partner partner : PARTNERS) {
            float[] distInMeters = new float[1];
            Location.distanceBetween(currentLat, currentLong, partner.latitude, partner.longitude, distInMeters);

            if (distInMeters[0] > THRESHOLD_METERS) {
                return partner;
            }
        }
        return null;
    }

    public static Partner getPartnerById(long id) {
        for (Partner partner : PARTNERS) {
            if (partner.id == id) {
                return partner;
            }
        }
        return null;
    }
}

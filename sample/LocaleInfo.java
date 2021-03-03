package sample;

import java.time.ZoneId;
import java.util.Locale;

public class LocaleInfo {


    /**
     * getRegionName()
     * This gets the zone ID of the user's system.
     * @return
     */
    public static String getRegionName(){
        return ZoneId.systemDefault().toString();
    }


    /**
     * getDefaultLocale()
     * Get's the location name.
     * @return
     */
    public static String getDefaultLocale(){
        return Locale.getDefault().getDisplayCountry();
    }

    /**
     * Sets the regions needed by application.
     */
    public static Locale usaLoc = new Locale("en", "US");
    public static Locale britainLoc = new Locale("en", "GB");
    public static Locale canadaLoc = new Locale("fr", "CA");



}

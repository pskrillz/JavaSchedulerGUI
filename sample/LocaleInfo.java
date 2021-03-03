package sample;

import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

public class LocaleInfo {


    /**
     * getRegionName()
     * Gets the zone ID of the user's system.
     * @return
     */
    public static String getRegionName(){
        return ZoneId.systemDefault().toString();
    }


    /**
     * String getDefaultLocale()
     * Gets the location name.
     * @return
     */
    public static String getDefaultLocale(){
        return Locale.getDefault().getDisplayCountry();
    }

    /**
     * ResourceBundle getResourceBundle()
     * Returns the correct ResourceBundle.
     * @return
     */
    public static ResourceBundle getResourceBundle(){
        if (getDefaultLocale().contains("Canada")){
            return canadaText;
        } else {
            return usaText;
        }

    }

    /**
     * Locale usaLoc, Locale canadaLoc:
     * Instantiates the region Locales needed for the program.
     */
    public static Locale usaLoc = new Locale("en", "US");
    public static Locale canadaLoc = new Locale("fr", "CA");


    /**
     * ResourceBundle usaText, ResourceBundle canadaText
     * Instantiates the ResourceBundle's needed for the program.
     */
    public static ResourceBundle usaText = ResourceBundle.getBundle("resource_bundles.usaBundle", usaLoc);
    public static ResourceBundle canadaText = ResourceBundle.getBundle("resource_bundles.canadaBundle", canadaLoc);



}

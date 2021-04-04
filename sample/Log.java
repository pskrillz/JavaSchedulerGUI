package sample;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class Log {
    private static final String filename = "log.txt";
    private static File file;

    /**
     * createLog()
     * Creates the log for the first time, used on Login initialize.
     * After the first time, it is already created.
     */
    public static void createLog(){
        try {
            file = new File(filename);
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
                Log.write("Log file Created.");
            } else {
                System.out.println("File already created.");
            }
        } catch (IOException e) {
            System.out.println("Error" + e);
        }
    }

    /**
     * write()
     * Appends the log file with new entry
     * Takes a string parameter to log which user and if their login was successful or not.
     * @param str
     */
    public static void write(String str){
        str = LocalDateTime.now().toString() + ": " + str;

        try {
            FileWriter myWriter = new FileWriter(filename, true);
            myWriter.append(str + "\n");
            myWriter.close();
            System.out.println("Log Appended: " + str);
        } catch (IOException e) {
            System.out.println("File ERROR!!! " + e);
        }
    }

}

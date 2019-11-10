package util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

//TODO
public class Logger {

    private static BufferedWriter logWriter;
    private static BufferedWriter errorWriter;
    private static Path errorPath;
    private static Path logPath;

    public static void main(String[] args) {
        initialize();
    }

    public static void initialize() {

        //File file = new File(System.getProperty("user.dir") + "\\Log\\" + Calendar.getInstance().getTime().toString());
        // File file = new File(System.getProperty("user.dir") + "\\Log\\a");
        String textToAppend = "ASDASD";
        //Files.
        try {
            Path logPath = Paths.get("Log\\defaultLog");
            Path errorPath = Paths.get("Log\\Error.log");
            Files.write(logPath, textToAppend.getBytes(), StandardOpenOption.APPEND, StandardOpenOption.CREATE);  //Append mode
            logWriter = Files.newBufferedWriter(logPath, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            errorWriter = Files.newBufferedWriter(errorPath, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            errorWriter.write(Calendar.getInstance().getTime().toString());
            errorWriter.write("ASDASDASDASDASDASD");
            errorWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void log(String message) {
        System.out.println(message);
        try {
            logWriter.append(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void log(Exception exception) {
        exception.printStackTrace();

        try {
            logWriter.append(exception.toString());
            logWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void log(Error e) {

    }

}

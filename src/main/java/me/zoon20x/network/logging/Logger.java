package me.zoon20x.network.logging;

import me.zoon20x.network.Server.Server;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class Logger {

    public enum Level {
        INFO, WARN, ERROR, DEBUG
    }


    private static final DateTimeFormatter TIME_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter FILE_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");

    private static final File LOG_DIR = new File("logs");
    private static final File LATEST_LOG = new File(LOG_DIR, "latest.log");

    private static boolean debugEnabled = false;
    private static BufferedWriter writer;

    static {
        try {
            if (!LOG_DIR.exists()) LOG_DIR.mkdirs();
            writer = new BufferedWriter(new FileWriter(LATEST_LOG, false));

            // Register shutdown hook to rename latest.log when program stops
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    writer.flush();
                    writer.close();
                    String timestamp = LocalDateTime.now().format(FILE_FORMAT);
                    File renamed = new File(LOG_DIR, timestamp + ".log");
                    if (LATEST_LOG.renameTo(renamed)) {
                        System.out.println("\u001B[90m[Logger] Log saved as: " + renamed.getName() + "\u001B[0m");
                    } else {
                        System.err.println("[Logger] Failed to rename latest.log");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void enableDebug(boolean enable) {
        debugEnabled = enable;
    }

    public static void log(Level level, String message) {
        String time = LocalDateTime.now().format(TIME_FORMAT);
        String formatted = time + " " + levelPrefix(level, true) + " " + message;

        // Print to console
        System.out.println(time + " " + levelPrefix(level, false) + " " + message);

        try {
            writer.write(formatted + System.lineSeparator());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void info(String message) { log(Level.INFO, message); }
    public static void warn(String message) { log(Level.WARN, message); }
    public static void error(String message) { log(Level.ERROR, message); }
    public static void debug(String message) { if (debugEnabled) log(Level.DEBUG, message); }

    private static String levelPrefix(Level level, boolean plain) {
        if (plain) return "[" + level + "]";
        final String RESET = "\u001B[0m";
        final String RED = "\u001B[31m";
        final String YELLOW = "\u001B[33m";
        final String BLUE = "\u001B[34m";
        final String CYAN = "\u001B[36m";

        return switch (level) {
            case INFO -> BLUE + "[INFO]" + RESET;
            case WARN -> YELLOW + "[WARN]" + RESET;
            case ERROR -> RED + "[ERROR]" + RESET;
            case DEBUG -> CYAN + "[DEBUG]" + RESET;
        };
    }
}
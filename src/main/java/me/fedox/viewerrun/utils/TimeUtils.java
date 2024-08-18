package me.fedox.viewerrun.utils;

public class TimeUtils {

    /**
     * Formats a given time in seconds into a human-readable string.
     *
     * @param totalSeconds the total time in seconds to be formatted
     * @return a formatted time string in the format "D:HH:MM:SS" or "HH:MM:SS" or "MM:SS"
     */
    public static String formatTime(long totalSeconds) {
        long days = totalSeconds / 86400;
        long hours = (totalSeconds % 86400) / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;

        StringBuilder timeString = new StringBuilder();

        if (days > 0) {
            timeString.append(String.format("%d:", days));
        }

        if (days > 0 || hours > 0) {
            timeString.append(String.format("%02d:", hours));
        }

        timeString.append(String.format("%02d:", minutes));

        timeString.append(String.format("%02d", seconds));

        return timeString.toString();
    }
}
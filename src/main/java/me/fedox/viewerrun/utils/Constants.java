package me.fedox.viewerrun.utils;

public class Constants {


    /**
     * General
     */
    public static final String PREFIX = "§a§lZFLOCKII.DE §8» §7";

    /**
     * Queue
     */
    public static long DEFAULT_QUEUE_ROTATION_TIME = 60 * 10;

    /**
     * Config
     */
    public static final String CONFIG_PREFIX = "Flocki";
    public static final String CONFIG_CREATOR = CONFIG_PREFIX + ".Player.Creator";
    public static final String CONFIG_ROTATION_TIME = CONFIG_PREFIX +  ".Queue.RotationTime";
    public static final String CONFIG_LOCATION = CONFIG_PREFIX + ".Location";

    public static final String CONFIG_CURRENT_RUN = CONFIG_PREFIX + ".CurrentRun";
    public static final String CONFIG_CURRENT_RUN_TIME = CONFIG_CURRENT_RUN + ".Time";

    public static final String CONFIG_CREATOR_UUID = CONFIG_CREATOR + ".UUID";
    public static final String CONFIG_CREATOR_INV = CONFIG_CREATOR + ".Inventory";
    public static final String CONFIG_CREATOR_HEALTH = CONFIG_CREATOR + ".Health";
    public static final String CONFIG_CREATOR_MAXHEALTH = CONFIG_CREATOR + ".MaxHealth";
    public static final String CONFIG_CREATOR_FOOD = CONFIG_CREATOR + ".Food";
    public static final String CONFIG_CREATOR_LOCATION = CONFIG_CREATOR + ".Location";

    public static final String CONFIG_VIEWER = CONFIG_PREFIX + ".Viewer";
    public static final String CONFIG_VIEWER_INV = CONFIG_VIEWER + ".Inventory";
    public static final String CONFIG_VIEWER_HEALTH = CONFIG_VIEWER + ".Health";
    public static final String CONFIG_VIEWER_MAXHEALTH = CONFIG_VIEWER + ".MaxHealth";
    public static final String CONFIG_VIEWER_FOOD = CONFIG_VIEWER + ".Food";
    public static final String CONFIG_VIEWER_LOCATION = CONFIG_VIEWER + ".Location";
}

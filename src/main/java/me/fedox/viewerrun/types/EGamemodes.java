package me.fedox.viewerrun.types;

import org.bukkit.GameMode;

public enum EGamemodes {

    /**
     * Survival game mode with ID 0.
     */
    SURVIVAL(GameMode.SURVIVAL, 0),

    /**
     * Creative game mode with ID 1.
     */
    CREATIVE(GameMode.CREATIVE, 1),

    /**
     * Adventure game mode with ID 2.
     */
    ADVENTURE(GameMode.ADVENTURE, 2),

    /**
     * Spectator game mode with ID 3.
     */
    SPECTATOR(GameMode.SPECTATOR, 3);

    private Integer id = ordinal();
    private GameMode gameMode;

    /**
     * Constructor for the EGamemodes enum.
     *
     * @param gameMode the Bukkit GameMode associated with this enum constant
     * @param id the ID associated with this game mode
     */
    EGamemodes(GameMode gameMode, int id) {
        this.gameMode = gameMode;
        this.id = id;
    }

    /**
     * Checks if a given ID corresponds to a valid game mode.
     *
     * @param id the ID to check
     * @return true if the ID corresponds to a valid game mode, false otherwise
     */
    public static boolean isGamemode(int id) {
        for (EGamemodes mode : values()) {
            if (mode.id == id) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves the GameMode associated with a given ID.
     *
     * @param id the ID of the game mode
     * @return the GameMode associated with the given ID, or null if not found
     */
    public static GameMode getFromId(int id) {
        for (EGamemodes mode : values()) {
            if (mode.id == id) {
                return mode.gameMode;
            }
        }
        return null;
    }

}
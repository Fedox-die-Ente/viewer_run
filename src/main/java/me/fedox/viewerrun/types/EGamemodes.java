package me.fedox.viewerrun.types;

import org.bukkit.GameMode;

public enum EGamemodes {

    SURVIVAL(GameMode.SURVIVAL, 0),
    CREATIVE(GameMode.CREATIVE, 1),
    ADVENTURE(GameMode.ADVENTURE, 2),
    SPECTATOR(GameMode.SPECTATOR, 3);

    private Integer id = ordinal();
    private GameMode gameMode;

    EGamemodes(GameMode gameMode, int id) {
        this.gameMode = gameMode;
        this.id = id;
    }

    public static boolean isGamemode(int id) {
        for (EGamemodes mode : values()) {
            if (mode.id == id) {
                return true;
            }
        }
        return false;
    }

    public static GameMode getGamemode(String gamemode) {
        for (EGamemodes mode : values()) {
            if (mode.name().equalsIgnoreCase(gamemode)) {
                return mode.gameMode;
            }
        }
        return null;
    }

    public static GameMode getFromId(int id) {
        for (EGamemodes mode : values()) {
            if (mode.id == id) {
                return mode.gameMode;
            }
        }
        return null;
    }

}

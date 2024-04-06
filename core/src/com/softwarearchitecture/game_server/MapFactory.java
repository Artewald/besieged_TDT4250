package com.softwarearchitecture.game_server;

import com.softwarearchitecture.game_server.states.TexturePack;

public class MapFactory {
    public static Map createMap(String mapType, int rows, int cols) {
        switch (mapType) {
            case "Abyss":
                String mapString =
                "PLACEABLE, PLACEABLE, PLACEABLE, PLACEABLE, PLACEABLE, PATH, PATH, PATH, PLACEABLE, PLACEABLE;" +
                "PLACEABLE, PLACEABLE, PLACEABLE, PLACEABLE, PLACEABLE, PATH, PATH, PATH, PLACEABLE, PLACEABLE;" +
                "PLACEABLE, PLACEABLE, PLACEABLE, PLACEABLE, PLACEABLE, PATH, PATH, PATH, PLACEABLE, PLACEABLE;" +
                "PLACEABLE, PLACEABLE, PLACEABLE, PLACEABLE, PLACEABLE, PATH, PATH, PATH, PLACEABLE, PLACEABLE;" +
                "PLACEABLE, PLACEABLE, PLACEABLE, PLACEABLE, PLACEABLE, PATH, PATH, PATH, PLACEABLE, PLACEABLE;" +
                "PLACEABLE, PLACEABLE, PLACEABLE, PLACEABLE, PLACEABLE, PATH, PATH, PATH, PLACEABLE, PLACEABLE;" +
                "PLACEABLE, PLACEABLE, PLACEABLE, PLACEABLE, PLACEABLE, PATH, PATH, PATH, PLACEABLE, PLACEABLE;" +
                "PLACEABLE, PLACEABLE, PLACEABLE, PLACEABLE, PLACEABLE, PATH, PATH, PATH, PLACEABLE, PLACEABLE;" +
                "PLACEABLE, PLACEABLE, PLACEABLE, PLACEABLE, PLACEABLE, PATH, PATH, PATH, PLACEABLE, PLACEABLE;" +
                "PLACEABLE, PLACEABLE, PLACEABLE, PLACEABLE, PLACEABLE, PATH, PATH, PATH, PLACEABLE, PLACEABLE;";
                String backgroundImage = TexturePack.BACKGROUND_ABYSS;
                return new Map(mapString, rows, cols, backgroundImage);
            default:
                throw new IllegalArgumentException("Invalid map type: " + mapType);
        }
    }
}

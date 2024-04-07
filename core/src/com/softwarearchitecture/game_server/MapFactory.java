package com.softwarearchitecture.game_server;

import com.softwarearchitecture.game_server.states.TexturePack;

public class MapFactory {
    public static Map createMap(String mapType) {
        switch (mapType) {
            case "Abyss":
                String mapString =
                "BLOCKED_WATER, BLOCKED_WATER, PLACEABLE, PLACEABLE, PLACEABLE, PATH, PATH, PATH, PLACEABLE, PLACEABLE;" +
                "BLOCKED_WATER, BLOCKED_WATER, PLACEABLE, PLACEABLE, PLACEABLE, PATH, PATH, PATH, PLACEABLE, PLACEABLE;" +
                "BLOCKED_WATER, PLACEABLE, PLACEABLE, PLACEABLE, PLACEABLE, PATH, PATH, PATH, PLACEABLE, PLACEABLE;" +
                "BLOCKED_WATER, PLACEABLE, PLACEABLE, PLACEABLE, PLACEABLE, PATH, PATH, PATH, PLACEABLE, PLACEABLE;" +
                "BLOCKED_WATER, PLACEABLE, PLACEABLE, PLACEABLE, PLACEABLE, PATH, PATH, PATH, PLACEABLE, PLACEABLE;" +
                "PLACEABLE, PLACEABLE, BLOCKED_ROCK, BLOCKED_ROCK, PLACEABLE, PATH, PATH, PATH, PLACEABLE, PLACEABLE;" +
                "PLACEABLE, PLACEABLE, BLOCKED_ROCK, BLOCKED_ROCK, PLACEABLE, PATH, PATH, PATH, PLACEABLE, PLACEABLE;" +
                "PLACEABLE, PLACEABLE, BLOCKED_ROCK, PLACEABLE, PLACEABLE, PATH, PATH, PATH, PLACEABLE, PLACEABLE;" +
                "PLACEABLE, PLACEABLE, PLACEABLE, PLACEABLE, PLACEABLE, PATH, PATH, PATH, PLACEABLE, PLACEABLE;" +
                "PLACEABLE, PLACEABLE, PLACEABLE, PLACEABLE, PLACEABLE, PATH, PATH, PATH, PLACEABLE, PLACEABLE;" +
                "PLACEABLE, PLACEABLE, PLACEABLE, PLACEABLE, PLACEABLE, PATH, PATH, PATH, PLACEABLE, PLACEABLE;" +
                "PLACEABLE, PLACEABLE, PLACEABLE, PLACEABLE, PLACEABLE, PATH, PATH, PATH, PLACEABLE, PLACEABLE;" +
                "PLACEABLE, PLACEABLE, PLACEABLE, PLACEABLE, PLACEABLE, PATH, PATH, PATH, PLACEABLE, PLACEABLE;" +
                "PLACEABLE, PLACEABLE, PLACEABLE, PLACEABLE, PATH, PATH, PATH, PATH, PLACEABLE, PLACEABLE;" +
                "PLACEABLE, PLACEABLE, PLACEABLE, PATH, PATH, PATH, PATH, PLACEABLE, PLACEABLE, PLACEABLE;" +
                "PLACEABLE, PLACEABLE, PATH, PATH, PATH, PATH, PATH, PLACEABLE, PLACEABLE, PLACEABLE;" +
                "PLACEABLE, PLACEABLE, PATH, PATH, PATH, PATH, PLACEABLE, PLACEABLE, PLACEABLE, BLOCKED_WATER;" +
                "PLACEABLE, PLACEABLE, PATH, PATH, PATH, PLACEABLE, PLACEABLE, PLACEABLE, BLOCKED_WATER, BLOCKED_WATER;" +
                "PLACEABLE, PATH, PATH, PATH, PATH, PLACEABLE, PLACEABLE, BLOCKED_WATER, BLOCKED_WATER, BLOCKED_WATER;";
                String backgroundImage = TexturePack.BACKGROUND_ABYSS;
                return new Map(mapString, backgroundImage);
            default:
                throw new IllegalArgumentException("Invalid map type: " + mapType);
        }
    }
}

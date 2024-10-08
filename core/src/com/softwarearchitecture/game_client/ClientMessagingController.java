package com.softwarearchitecture.game_client;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.softwarearchitecture.game_server.GameState;
import com.softwarearchitecture.game_server.PlayerInput;

/**
 * Interface for the client messaging controller.
 * The client messaging controller is used by the client to send messages to the server.
 */
public interface ClientMessagingController {

    /**
     * Try to make the player with the given playerID join the game with the given gameID.
     * If the game does not exist or does not have space for the player to join, return false.
     * If the player was added to the game, return the updated game state.
     * @param gameID of the game to join
     * @param playerID of the player to add to the game
     * @return  true if the player was added to the game, false otherwise
     */
    public boolean joinGame(UUID gameID, UUID playerID);

    /**
     * Get the game state of the game with the given gameID.
     * @param gameID of the game to get the game state of.
     * @return the game state of the game with the given gameID
     */
    public Optional<GameState> requestGameState(UUID gameID);

    /**
     * Add an action to the game with the playerID contained in action.
     * @param action to perform in the game
     */
    public void addAction(PlayerInput action);

    /**
     * Get all games that are currently available.
     * @return a list of all games that are currently available
     */
    public List<GameState> getAllAvailableGames();

    /**
     * Get all high scores.
     */
    public List<Score> getAllHighScores();
}

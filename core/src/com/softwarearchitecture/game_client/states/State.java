package com.softwarearchitecture.game_client.states;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.softwarearchitecture.ecs.Controllers;
import com.softwarearchitecture.ecs.ECSManager;
import com.softwarearchitecture.ecs.Entity;
import com.softwarearchitecture.ecs.GraphicsController;
import com.softwarearchitecture.math.Vector2;

public abstract class State {
    protected ScreenManager screenManager;
    protected Vector2 mouse = new Vector2(0, 0);
    protected List<Entity> buttons;
    protected Controllers defaultControllers;

    protected State(Controllers defaultControllers) {
        this.defaultControllers = defaultControllers;
        this.screenManager = ScreenManager.getInstance();
    }

    /**
     * Initializes the state, clearing the ECSManager and activating the state.
     */
    public void init() {
        ECSManager.getInstance().clearAll();
        activate();
    }
    
    /**
     * Activates the state, setting up the ECSManager and the buttons.
     */
    protected abstract void activate();

    // /**
    //  * Updates the buttons in the state, and checks if they are clicked.
    //  * 
    //  * @param deltaTime: float
    //  */
    // protected void updateButtons(float deltaTime) {
    //     mouse.set(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());

    //     // for (Entity button : buttons) { // how is a button updated?
    //     // button.update(mouse);
    //     // }
    // }

    // protected abstract void update(float deltaTime);

    // protected abstract void handleInput();

    // public void dispose() {
    //     ECSManager.getInstance().clearAll();
    // }
}

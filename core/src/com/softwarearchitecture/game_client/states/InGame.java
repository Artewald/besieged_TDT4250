package com.softwarearchitecture.game_client.states;

import com.softwarearchitecture.ecs.Controllers;
import com.softwarearchitecture.ecs.ECSManager;
import com.softwarearchitecture.ecs.Entity;
import com.softwarearchitecture.ecs.components.ButtonComponent.TypeEnum;
import com.softwarearchitecture.ecs.components.PositionComponent;
import com.softwarearchitecture.ecs.components.SpriteComponent;
import com.softwarearchitecture.ecs.components.TextComponent;
import com.softwarearchitecture.ecs.systems.InputSystem;
import com.softwarearchitecture.ecs.systems.RenderingSystem;
import com.softwarearchitecture.game_server.TexturePack;
import com.softwarearchitecture.math.Vector2;

public class InGame extends State implements Observer {

    protected InGame(Controllers defaultControllers) {
        super(defaultControllers);
    }
    
    @Override
    protected void activate() {
        // Background
        String backgroundPath = TexturePack.BACKGROUND_ABYSS;
        Entity background = new Entity();
        SpriteComponent backgroundSprite = new SpriteComponent(backgroundPath, new Vector2(1, 1));
        PositionComponent backgroundPosition = new PositionComponent(new Vector2(0, 0), -1);
        background.addComponent(SpriteComponent.class, backgroundSprite);
        background.addComponent(PositionComponent.class, backgroundPosition);
        TextComponent textComponent = new TextComponent("In Game!", new Vector2(0.05f, 0.05f));
        background.addComponent(TextComponent.class, textComponent);
        ECSManager.getInstance().addEntity(background);
        
        // Buttons
        Entity backButton = ButtonFactory.createAndAddButtonEntity(TypeEnum.BACK, new Vector2(0, 1), new Vector2(0.1f, 0.2f), this, 0);
    
        // Add systems to the ECSManager
        RenderingSystem renderingSystem = new RenderingSystem(defaultControllers.graphicsController);
        InputSystem inputSystem = new InputSystem(defaultControllers.inputController);
        ECSManager.getInstance().addSystem(renderingSystem);
        ECSManager.getInstance().addSystem(inputSystem);
    }

    @Override
    public void onAction(TypeEnum type) {
        switch (type) {
            case BACK:
                ScreenManager.getInstance().previousState();
                break;
            default:
                break;
        }
    }

}

package com.softwarearchitecture.game_server;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.softwarearchitecture.ecs.Card;
import com.softwarearchitecture.ecs.ComponentManager;
import com.softwarearchitecture.ecs.ECSManager;
import com.softwarearchitecture.ecs.Entity;
import com.softwarearchitecture.ecs.components.CardHolderComponent;
import com.softwarearchitecture.ecs.components.EnemyComponent;
import com.softwarearchitecture.ecs.components.HealthComponent;
import com.softwarearchitecture.ecs.components.MoneyComponent;
import com.softwarearchitecture.ecs.components.PlacedCardComponent;
import com.softwarearchitecture.ecs.components.PositionComponent;
import com.softwarearchitecture.ecs.components.ShopComponent;
import com.softwarearchitecture.ecs.components.TargetComponent;
import com.softwarearchitecture.ecs.components.TowerComponent;
import com.softwarearchitecture.game_server.cards.elemental_cards.IceCard;
import com.softwarearchitecture.game_server.cards.elemental_cards.LightningCard;
import com.softwarearchitecture.game_server.cards.elemental_cards.TechnologyCard;
import com.softwarearchitecture.game_server.cards.tower_cards.BowCard;
import com.softwarearchitecture.game_server.cards.tower_cards.MagicCard;
import com.softwarearchitecture.game_server.cards.tower_cards.MeleeCard;
import com.softwarearchitecture.game_server.cards.tower_cards.PowerCard;


public class GameState implements Externalizable {
    public static final List<Class<? extends Card>> card_classes = new ArrayList<>(
        Arrays.asList(
            IceCard.class,
            LightningCard.class,
            TechnologyCard.class,
            BowCard.class,
            MagicCard.class,
            MeleeCard.class,
            PowerCard.class
            )
    );
    private static final String game_version = "0.1";
    private UUID id;
    private Entity village;
    private Entity playerOne;
    private Entity playerTwo;
    private List<List<MapTile>> map;

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(game_version);
        // Writing gameID
        out.writeObject(id);
        out.writeObject(playerOne);
        out.writeObject(playerTwo);
        // Village health
        serializeVillageHealth(out);
        // Player money
        serializePlayerMoney(out);
        // Cards (for each player)
        serializePlayerCards(out);
        // Map
        out.writeObject(map);
        // Towers
        serializeTowers(out);
        // Enemies
        serializeEnemies(out);
        // Placed cards
        serializePlacedCards(out);
        // Store cards
        serializeShopCards(out);
    }

    private void serializeVillageHealth(ObjectOutput out) throws IOException {
        ECSManager manager = ECSManager.getInstance();
        ComponentManager<HealthComponent> healthManager = manager.getComponentManager(HealthComponent.class);
        HealthComponent villageHealth = (HealthComponent) healthManager.getComponent(village);
        if (villageHealth != null) {
            out.writeObject(village);
            out.writeInt(villageHealth.getHealth());
        } else {
            throw new IllegalStateException("Village has no health component");
        }
    }

    private void serializePlayerMoney(ObjectOutput out) throws IOException {
        ECSManager manager = ECSManager.getInstance();
        ComponentManager<MoneyComponent> goldManager = manager.getComponentManager(MoneyComponent.class);
        MoneyComponent playerOneMoney = (MoneyComponent) goldManager.getComponent(playerOne);
        if (playerOneMoney != null) {
            out.writeInt(playerOneMoney.amount);
        } else {
            throw new IllegalStateException("Player one has no money component");
        }
        if (playerTwo != null) {
            MoneyComponent playerTwoMoney = (MoneyComponent) goldManager.getComponent(playerTwo);
            if (playerTwoMoney != null) {
                out.writeInt(playerTwoMoney.amount);
            } else {
                throw new IllegalStateException("Player two has no money component");
            }
        } else {
            out.writeInt(0);
        }
    }

    private void serializePlayerCards(ObjectOutput out) throws IOException {
        ECSManager manager = ECSManager.getInstance();
        ComponentManager<CardHolderComponent> cardHolderManager = manager.getComponentManager(CardHolderComponent.class);
        CardHolderComponent playerOneCardHolder = (CardHolderComponent) cardHolderManager.getComponent(playerOne);
        if (playerOneCardHolder != null) {
            out.writeObject(playerOneCardHolder);
        } else {
            throw new IllegalStateException("Player one has no card holder component");
        }
        if (playerTwo != null) {
            CardHolderComponent playerTwoCardHolder = (CardHolderComponent) cardHolderManager.getComponent(playerTwo);
            if (playerTwoCardHolder != null) {
                out.writeObject(playerTwoCardHolder);
            } else {
                throw new IllegalStateException("Player two has no card holder component");
            }
        } else {
            out.writeObject(null);
        }
    }

    private void serializeTowers(ObjectOutput out) throws IOException {
        ECSManager manager = ECSManager.getInstance();
        ComponentManager<TowerComponent> towerManager = manager.getComponentManager(TowerComponent.class);
        ComponentManager<TargetComponent> targetManager = manager.getComponentManager(TargetComponent.class);
        ArrayList<Entity> towerEntities = new ArrayList<>();
        ArrayList<TowerComponent> towers = new ArrayList<>();
        ArrayList<TargetComponent> targets = new ArrayList<>();
        for (Entity entity : manager.getEntities()) {
            TowerComponent tower = (TowerComponent) towerManager.getComponent(entity);
            TargetComponent target = (TargetComponent) targetManager.getComponent(entity);
            
            if (tower != null && target != null) {
                towerEntities.add(entity);
                towers.add(tower);
                targets.add(target);
            } else if (tower != null && target == null) {
                throw new IllegalStateException("Tower and target components must be present together");
            }
        }
        if (towers.size() != targets.size()) {
            throw new IllegalStateException("Towers and targets must be present in equal numbers");
        }
        out.writeObject(towerEntities);
        out.writeObject(towers);
        out.writeObject(targets);
    }

    private void serializeEnemies(ObjectOutput out) throws IOException {
        ECSManager manager = ECSManager.getInstance();
        ComponentManager<PositionComponent> positionManager = manager.getComponentManager(PositionComponent.class);
        ComponentManager<EnemyComponent> enemyManager = manager.getComponentManager(EnemyComponent.class);
        ComponentManager<HealthComponent> healthManager = manager.getComponentManager(HealthComponent.class);
        ArrayList<Entity> enemyEntities = new ArrayList<>();
        ArrayList<HealthComponent> enemyHealths = new ArrayList<>();
        ArrayList<PositionComponent> enemyPositions = new ArrayList<>();
        ArrayList<EnemyComponent> enemies = new ArrayList<>();
        for (Entity entity : manager.getEntities()) {
            PositionComponent position = (PositionComponent) positionManager.getComponent(entity);
            EnemyComponent enemy = (EnemyComponent) enemyManager.getComponent(entity);
            HealthComponent health = (HealthComponent) healthManager.getComponent(entity);

            if (position != null && enemy != null && health != null) {
                enemyEntities.add(entity);
                enemyHealths.add(health);
                enemyPositions.add(position);
                enemies.add(enemy);
            } else if (enemy != null && (position == null || health == null)) {
                throw new IllegalStateException("Enemy, position, and health components must be present together");
            }
        }
        if (!(enemyHealths.size() == enemyPositions.size() && enemyHealths.size() == enemies.size())) {
            throw new IllegalStateException("Enemy healths, positions, and enemies must be present in equal numbers");
        }
        out.writeObject(enemyEntities);
        out.writeObject(enemyHealths);
        out.writeObject(enemyPositions);
        out.writeObject(enemies);
    }

    private void serializePlacedCards(ObjectOutput out) throws IOException {
        ECSManager manager = ECSManager.getInstance();
        ComponentManager<PlacedCardComponent> placedCardManager = manager.getComponentManager(PlacedCardComponent.class);
        ComponentManager<PositionComponent> positionManager = manager.getComponentManager(PositionComponent.class);
        ArrayList<Entity> placedCardEntities = new ArrayList<>();
        ArrayList<PlacedCardComponent> placedCards = new ArrayList<>();
        ArrayList<PositionComponent> placedCardPositions = new ArrayList<>();
        for (Entity entity : manager.getEntities()) {
            PlacedCardComponent placedCard = (PlacedCardComponent) placedCardManager.getComponent(entity);
            PositionComponent position = (PositionComponent) positionManager.getComponent(entity);
            if (placedCard != null && position != null) {
                placedCardEntities.add(entity);
                placedCards.add(placedCard);
                placedCardPositions.add(position);
            } else if (placedCard != null && position == null) {
                throw new IllegalStateException("Placed card and position components must be present together");
            }
        }
    }

    private void serializeShopCards(ObjectOutput out) throws IOException {
        ECSManager manager = ECSManager.getInstance();
        ComponentManager<ShopComponent> shopManager = manager.getComponentManager(ShopComponent.class);
        ArrayList<Entity> shopEntities = new ArrayList<>();
        ArrayList<ShopComponent> shopCards = new ArrayList<>();
        for (Entity entity : manager.getEntities()) {
            ShopComponent shopCard = (ShopComponent) shopManager.getComponent(entity);
            if (shopCard != null) {
                shopEntities.add(entity);
                shopCards.add(shopCard);
            }
        }
        if (shopEntities.size() != shopCards.size() || shopEntities.size() < 1) {
            throw new IllegalStateException("Shop cards must be present in equal numbers and at least one must be present");
        }
        out.writeObject(shopEntities);
        out.writeObject(shopCards);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'readExternal'");
    }
    
}

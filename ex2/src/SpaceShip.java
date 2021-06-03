import java.awt.Image;

import oop.ex2.*;

//import static oop.ex2.GameGUI.createImageIcon;

/**
 * The API spaceships need to implement for the SpaceWars game.
 * It is your decision whether SpaceShip.java will be an interface, an abstract class,
 * a base class for the other spaceships or any other option you will choose.
 *
 * @author oop
 */
public abstract class SpaceShip {

    /** MAGIC NUMBERS : BEGIN **/

    /**
     * the reduced energy for firing.
     */
    protected final int FIRE = 19;

    /**
     * the reduced energy for teleporting.
     */
    protected final int TELEPORT = 140;

    /**
     * the reduced energy for getting hit, by ship or a shot.
     */
    protected final int ENERGY_HIT = 10;

    /**
     * the reduced energy for shield.
     */
    protected final int SHIELD = 3;

    /**
     * the increased energy for bashing.
     */
    protected final int BASH = 18;

    /**
     * the initial health.
     */
    protected final int maxHealth = 22;

    /**
     * the initial maximum energy.
     */
    protected final int MAX_ENERGY = 210;

    /**
     * the initial current energy.
     */
    protected final int CURRENT_ENERGY = 190;

    /**
     * the health that represents the dead situation.
     */
    protected final int DEAD = 0;

    /**
     * the right rotation.
     */
    protected final int RIGHT = -1;

    /**
     * the left rotation.
     */
    protected final int LEFT = 1;

    /**
     * the no rotation indication.
     */
    protected final int NO_TURN = 0;

    /**
     * the distance from other spaceship to teleport when reach it.
     */
    protected final double DISTANCE = 0.25;

    /**
     * the angle which if reached, this spaceship will teleport.
     */
    protected final double ANGLE = 0.23;

    /**
     * the distance which the Basher spaceship will put shield on at it and bash!
     */
    protected final double BASH_DISTANCE = 0.19;

    /**
     * the angle that when this spaceship reach to the nearest spaceship it will shot.
     */
    protected final double FIRE_DISTANCE = 0.21;

    /**
     * the health of the special spaceship.
     */
    protected final int SPECIAL_HEALTH = 30;

    /**
     * the max energy of the special spaceship.
     */
    protected final int SPECIAL_MAX_ENERGY = 1000;

    /**
     * the image of the human controlled spaceship without shield.
     */
    protected Image HUMAN_WITHOUT = GameGUI.SPACESHIP_IMAGE;

    /**
     * the image of the human controlled spaceship with shield.
     */
    protected Image HUMAN_WITH = GameGUI.SPACESHIP_IMAGE_SHIELD;

    /**
     * the image of the enemy spaceships without shield.
     */
    protected Image ENEMY_WITHOUT = GameGUI.ENEMY_SPACESHIP_IMAGE;

    /**
     * the image of the enemy spaceships with shield.
     */
    protected Image ENEMY_WITH = GameGUI.ENEMY_SPACESHIP_IMAGE_SHIELD;

    /** MAGIC NUMBERS : END **/

    /**
     * the health of the ship.
     */
    protected int health;

    /**
     * the maximum energy that the human ship could reach through th game.
     */
    protected int maxEnergy;

    /**
     * the current energy level.
     */
    protected int currentEnergy;

    /**
     * the status of the shield of this spaceship.
     */
    protected boolean isShieldOn;

    /**
     * the type of the current spaceship.
     */
    protected int shipType;

    /**
     * the image of the current spaceship.
     */
    protected Image shipImage;

    /**
     * rounds' counter.
     */
    protected int roundsCounter;

    /**
     * a SpaceShipPhysics object that represents the position, direction and velocity of the ship.
     */
    private SpaceShipPhysics physical;

    /**
     * creates a new human ship with 22 health and 210 max energy and 190 current energy.
     */
    public SpaceShip() {
        this.reset();
    }

    /**
     * Does the actions of this ship for this round.
     * This is called once per round by the SpaceWars game driver.
     *
     * @param game the game object to which this ship belongs.
     */
    public abstract void doAction(SpaceWars game);

    /**
     * This method is called every time a collision with this ship occurs
     */
    public void collidedWithAnotherShip() {
        if (!isShieldOn) {
            this.health--;
            this.maxEnergy -= ENERGY_HIT;
        } else {
            this.currentEnergy += BASH;
            this.maxEnergy += BASH;
        }
        if (this.currentEnergy > this.maxEnergy) {
            this.currentEnergy = this.maxEnergy;
        }

    }

    /**
     * This method is called whenever a ship has died. It resets the ship's
     * attributes, and starts it at a new random position.
     */
    public void reset() {
        this.health = maxHealth;
        this.maxEnergy = MAX_ENERGY;
        this.currentEnergy = CURRENT_ENERGY;
        this.isShieldOn = false;
        this.physical = new SpaceShipPhysics();
        this.roundsCounter = 0;
        if (shipType == SpaceWars.HUMAN_CONTROLLED_SHIP) {
            this.shipImage = HUMAN_WITHOUT;
        } else {
            this.shipImage = ENEMY_WITHOUT;
        }

    }

    /**
     * Checks if this ship is dead.
     *
     * @return true if the ship is dead. false otherwise.
     */
    public boolean isDead() {
        return !(this.health > DEAD);
    }

    /**
     * Gets the physics object that controls this ship.
     *
     * @return the physics object that controls the ship.
     */
    public SpaceShipPhysics getPhysics() {
        return this.physical;
    }

    /**
     * This method is called by the SpaceWars game object when ever this ship
     * gets hit by a shot.
     */
    public void gotHit() {
        if (!isShieldOn) {
            this.health--;
            this.maxEnergy -= ENERGY_HIT;
        }

    }

    /**
     * Gets the image of this ship. This method should return the image of the
     * ship with or without the shield. This will be displayed on the GUI at
     * the end of the round.
     *
     * @return the image of this ship.
     */
    public Image getImage() {
        return this.shipImage;
    }

    /**
     * Attempts to fire a shot.
     *
     * @param game the game object.
     */
    public void fire(SpaceWars game) {
        if (this.currentEnergy >= FIRE) {
            if (this.roundsCounter % 7 == 1) {
                game.addShot(this.physical);
                this.currentEnergy -= FIRE;
            }
        }
    }

    /**
     * Attempts to turn on the shield.
     */
    public void shieldOn() {
        if (this.currentEnergy >= SHIELD) {
            this.isShieldOn = true;
            if (this.shipType == SpaceWars.HUMAN_CONTROLLED_SHIP) {
                this.shipImage = HUMAN_WITH;
            } else {
                this.shipImage = ENEMY_WITH;
            }
            this.currentEnergy -= SHIELD;
        }
    }

    /**
     * Attempts to teleport.
     */
    public void teleport() {
        if (this.currentEnergy >= TELEPORT) {
            this.physical = new SpaceShipPhysics();
            this.currentEnergy -= TELEPORT;
        }
    }

    /**
     * this method make the current spaceship pursuing other spaceships.
     *
     * @param game the game object to which this ship belongs.
     */
    public void pursue(SpaceWars game) {
        SpaceShipPhysics physics = this.getPhysics();
        SpaceShip closestShip = game.getClosestShipTo(this);

//        double angle1 = this.getPhysics().getAngle();
        double angle = closestShip.getPhysics().angleTo(physics);

        int turn = NO_TURN;

        if (angle < 0) {
            turn = RIGHT;
        } else if (angle > 0) {
            turn = LEFT;
        }

        physics.move(true, turn);
    }

}

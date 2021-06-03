import oop.ex2.SpaceShipPhysics;

/**
 * this class descibes the Aggressive type of spaceship.
 */
public class Aggressive extends SpaceShip {

    /**
     * Does the actions of this ship for this round.
     * This is called once per round by the SpaceWars game driver.
     * this method overrides the method doAction in the SpaceShip class.
     *
     * @param game the game object to which this ship belongs.
     */
    @Override
    public void doAction(SpaceWars game) {
        this.shipType = SpaceWars.AGGRESSIVE_SHIP;
        this.isShieldOn = false;
        this.shipImage = ENEMY_WITHOUT;
        this.roundsCounter++;

        this.pursue(game);
        SpaceShipPhysics physics = this.getPhysics();
        SpaceShip closestShip = game.getClosestShipTo(this);
        if (closestShip.getPhysics().angleTo(physics)<FIRE_DISTANCE){
            this.fire(game);
        }
        if (this.currentEnergy < this.maxEnergy) {
            this.currentEnergy++;
        }

    }
}

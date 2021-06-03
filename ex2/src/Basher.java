import oop.ex2.*;

import java.util.Random;
import java.math.*;

/**
 * this class describes the Basher spaceship type.
 */
public class Basher extends SpaceShip {

    /**
     * Does the actions of this ship for this round.
     * This is called once per round by the SpaceWars game driver.
     * this method overrides the method doAction in the SpaceShip class.
     *
     * @param game the game object to which this ship belongs.
     */
    @Override
    public void doAction(SpaceWars game) {
        this.shipType = SpaceWars.BASHER_SHIP;
        this.isShieldOn = false;
        this.shipImage = ENEMY_WITHOUT;
        this.roundsCounter++;

        SpaceShipPhysics physics = this.getPhysics();
        SpaceShip closestShip = game.getClosestShipTo(this);
        this.pursue(game);
        if (physics.distanceFrom(closestShip.getPhysics()) <= BASH_DISTANCE) {
            this.shieldOn();
        }
        if (this.currentEnergy < this.maxEnergy) {
            this.currentEnergy++;
        }

    }
}

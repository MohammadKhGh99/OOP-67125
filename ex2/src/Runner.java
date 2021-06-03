import oop.ex2.*;
import java.util.*;

/**
 * this class describes the Runner type of spaceship.
 */
public class Runner extends SpaceShip {

    /**
     * Does the actions of this ship for this round.
     * This is called once per round by the SpaceWars game driver.
     * this method overrides the method doAction in the SpaceShip class.
     *
     * @param game the game object to which this ship belongs.
     */
    @Override
    public void doAction(SpaceWars game) {
        this.shipType = SpaceWars.RUNNER_SHIP;
        this.isShieldOn = false;
        this.shipImage = GameGUI.ENEMY_SPACESHIP_IMAGE;
        this.roundsCounter++;

        Random random = new Random();
        int turn = random.nextInt(3) - 1;
        SpaceShipPhysics physics = this.getPhysics();
        physics.move(true, turn);

        SpaceShip closestShip = game.getClosestShipTo(this);
        double angle = closestShip.getPhysics().angleTo(physics);
        double distance = physics.distanceFrom(closestShip.getPhysics());
        if ((Math.abs(angle) < ANGLE) && (distance < DISTANCE)) {
            this.teleport();
        }

        if (this.currentEnergy < this.maxEnergy) {
            this.currentEnergy++;
        }
    }
}

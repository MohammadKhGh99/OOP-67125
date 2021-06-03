import oop.ex2.SpaceShipPhysics;
import oop.ex2.GameGUI;

/**
 * this class that works as human controlled ship.
 */
public class Human extends SpaceShip {

    /**
     * Does the actions of this ship for this round.
     * This is called once per round by the SpaceWars game driver.
     * this method overrides the method doAction in the SpaceShip class.
     *
     * @param game the game object to which this ship belongs.
     */
    public void doAction(SpaceWars game) {
        this.shipType = SpaceWars.HUMAN_CONTROLLED_SHIP;
        this.isShieldOn = false;
        this.shipImage = HUMAN_WITHOUT;
        this.roundsCounter++;

        GameGUI gui = game.getGUI();
        boolean acceleration = gui.isUpPressed();
        boolean left = gui.isLeftPressed();
        boolean right = gui.isRightPressed();
        boolean tele = gui.isTeleportPressed();
        boolean firing = gui.isShotPressed();
        boolean shield = gui.isShieldsPressed();
        int turn = NO_TURN;

        if (tele & this.currentEnergy >= TELEPORT) {
            this.teleport();
        }

        if (right & !left) {
            turn = RIGHT;
        } else if (left & !right) {
            turn = LEFT;
        }
        SpaceShipPhysics physics = this.getPhysics();
        physics.move(acceleration, turn);

        if (shield) {
            this.shieldOn();
        }

        if (firing & this.currentEnergy >= FIRE) {
            this.fire(game);
        }

        if (this.currentEnergy < this.maxEnergy) {
            this.currentEnergy++;
        }
    }
}

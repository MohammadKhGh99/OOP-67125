import oop.ex2.SpaceShipPhysics;

/**
 * this class describes the Special type of spaceships.
 */
public class Special extends SpaceShip {

    /**
     * Does the actions of this ship for this round.
     * This is called once per round by the SpaceWars game driver.
     * this method overrides the method doAction in the SpaceShip class.
     *
     * @param game the game object to which this ship belongs.
     */
    @Override
    public void doAction(SpaceWars game) {
        this.shipType = SpaceWars.SPECIAL_SHIP;
        this.isShieldOn = true;
        this.shipImage = ENEMY_WITH;
        this.maxEnergy=this.currentEnergy=SPECIAL_MAX_ENERGY;
        this.health=SPECIAL_HEALTH;
        this.roundsCounter++;

        if (roundsCounter%30==0){this.health++;}

        this.pursue(game);
        SpaceShipPhysics physics = this.getPhysics();
        SpaceShip closestShip = game.getClosestShipTo(this);
        if (closestShip.getPhysics().angleTo(physics)<FIRE_DISTANCE){
            if (this.currentEnergy>=FIRE) {
                game.addShot(this.getPhysics());
                this.currentEnergy -= FIRE;
            }
        }
        if (this.currentEnergy < this.maxEnergy) {
            this.currentEnergy+=5;
        }

    }

}

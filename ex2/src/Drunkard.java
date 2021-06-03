import java.util.Random;

/**
 * this class describes the Drunkard type of spaceship.
 */
public class Drunkard extends SpaceShip {

    /**
     * -1 indices to right, 1 indices to left and 0 no turn.
     */
    private int turn;
    /**
     * Does the actions of this ship for this round.
     * This is called once per round by the SpaceWars game driver.
     * this method overrides the method doAction in the SpaceShip class.
     *
     * @param game the game object to which this ship belongs.
     */
    @Override
    public void doAction(SpaceWars game) {
        this.shipType = SpaceWars.DRUNKARD_SHIP;
        this.isShieldOn = false;
        this.shipImage = ENEMY_WITHOUT;
        this.roundsCounter++;

        Random random = new Random();
        double tele=Math.random()*100;
        if (tele>0){this.teleport();}
        if (roundsCounter%20==0){
        this.turn =random.nextInt(3)-1;
        }
        boolean acceleration=random.nextBoolean();
        this.getPhysics().move(acceleration,turn);

        double shield=Math.random()*100;
        if (shield>80){this.shieldOn();}

        boolean firing=random.nextBoolean();
        if (firing){this.fire(game);}

        if (this.currentEnergy < this.maxEnergy) {
            this.currentEnergy++;
        }

    }
}

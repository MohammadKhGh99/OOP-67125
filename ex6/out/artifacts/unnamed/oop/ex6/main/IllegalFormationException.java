package oop.ex6.main;

/**
 * This Exception's class represent on the all exception occurs in the program.
 */
public class IllegalFormationException extends FatherException {

    /**
     * This constructor method receive a appropriate message to illegal case
     */
    public IllegalFormationException(String message){
        super(message);
    }
}

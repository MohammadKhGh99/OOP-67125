package oop.ex6.main;

import static oop.ex6.AllHelpingVariables.*;
import java.io.*;
//import java.nio.file.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * this class describes the S-javac main process (composing all the functions together).
 */
public class Sjavac {

    private static final String NUM_PARAMETERS_ERROR="ERROR: more than one argument given!";
    private static final String EMPTY_LINE="";
    private static final int NUM_PARAMETERS=1;
    private static final String GOOD_FILE_SUFFIX=".sjava";

    /**
     * this is the main method that all the process in it.
     *
     * @param args the arguments array from the command line.
     */
    public static void main(String[] args) {
        LinkedList<String> codeLines;
        try {
            if (args.length!=NUM_PARAMETERS ) throw new IllegalFormationException(NUM_PARAMETERS_ERROR);
            codeLines=new LinkedList<>( Files.readAllLines(Paths.get(args[0])));
            codeLines=removingWhiteTabSpaces(codeLines);
            Parsing.parsingFile(codeLines);
            System.out.println(LEGAL);
        } catch (IOException e) {
            System.out.println(IO_EXCEPTION_RESULT);
            System.err.println(IO_EXCEPTION_MSG);
        }catch (IllegalFormationException e){
            System.out.println(ILLEGAL_FORMATION);
            System.err.println(e.getMessage());
        }catch (Exception e){
            System.out.println(ILLEGAL_FORMATION);
            System.err.println(NOT_SUPPORTED_TYPE_MSG);
        }
        GlobalBlock.reset();

    }

    /**
     * this method's helper to main, it removes the white tab spaces in the file
     * @param codeLines - the lines of the file
     * @return - LinkedList without the tab spaces
     */
    private static LinkedList<String> removingWhiteTabSpaces(LinkedList<String> codeLines){
        LinkedList<String> tempList=new LinkedList<>();
        for (String line:codeLines){
            if (!line.matches(WHITE_SPACE_REGEX) && !line.equals(EMPTY_LINE))
                tempList.add(line);
        }
        return tempList;
    }
}

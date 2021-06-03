import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Training{

    public static void main(String[] args) {
        List<String> list=new LinkedList<>();
        try {
            list = Files.readAllLines(Paths.get(args[0]));
        }catch(IOException e){
            System.out.println("FUUUUUCCCKKKK");
        }
        LinkedList<Double> linkedList=new LinkedList<>();
        for (String s:list){
            String[] str=s.split(" ");
            double d=Double.parseDouble(str[str.length-1]);
            linkedList.add(d);
        }
        double sum=0.0;
        int count=0;
        for (double a:linkedList){
            count++;
            sum+=a;

        }
        System.out.println(sum);
        System.out.println(count);
        System.out.println(sum/count);    }
}

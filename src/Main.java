import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args )
    {
        Pattern p = Pattern.compile("([1-9][0-9]*)|0");
        Matcher m = p.matcher("0325");
        System.out.println(m.matches());
        System.out.println("Hello world!");
    }
}

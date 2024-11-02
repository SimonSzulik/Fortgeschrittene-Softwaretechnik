
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class ExampleMatcher {

    public static void main(String[] args) {

        if (args.length<1) {
          System.out.println("No data provided as command line argument!");
        } else {
           if (EmailMatcher.isValidEmail(args[0])) { 
             System.out.println("Valid email address"); 
           }
         }
    }

}

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class EmailMatcher {

 // Regex für Emailadressen
 static String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";                 

 static Pattern pattern = Pattern.compile(emailRegex);
 
 static boolean isValidEmail(String email) {
     Matcher matcher = pattern.matcher(email);
     return matcher.matches();
 }

}
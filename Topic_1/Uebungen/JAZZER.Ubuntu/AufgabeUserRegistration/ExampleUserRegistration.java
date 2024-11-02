
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class ExampleUserRegistration {

    public static void main(String[] args) {

        if (args.length<3) {
                    	System.out.println("Please provide username, password and email as command line arguments");
                } else {

             String user = args[0];
             String password = args[1];
             String email = args[2];
             try {
             	if (UserRegistration.registerUser(user, password, email))
			System.out.println("User successfully registered!");
		} catch (RegistrationException e) {
			System.out.println(e.getMessage());
             } 
        }

    }

}
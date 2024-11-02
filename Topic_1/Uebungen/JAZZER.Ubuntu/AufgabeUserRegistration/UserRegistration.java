import java.util.regex.Pattern;

public class UserRegistration {
    
    // Regex für Benutzernamen, nur Buchstaben und Zahlen
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[A-Za-z0-9]{3,15}$");

    public static boolean registerUser(String username, String password, String email) throws RegistrationException {
        if (!USERNAME_PATTERN.matcher(username).matches()) {
            throw new RegistrationException("Invalid username. Only letters and numbers allowed, 3-15 characters.");
        }

        if (password.length() < 8 || password.length() > 20) {
            throw new RegistrationException("Invalid password length. Password must be 8-20 characters.");
        }

        if (!EmailMatcher.isValidEmail(email)) {
            throw new RegistrationException("Invalid email format.");
        }

        return true;
    }
}


class RegistrationException extends Exception {
	RegistrationException(String msg) { super(msg); } 
}

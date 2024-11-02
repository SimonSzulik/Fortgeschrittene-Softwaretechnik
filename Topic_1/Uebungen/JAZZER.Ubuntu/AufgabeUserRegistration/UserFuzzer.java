import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import com.code_intelligence.jazzer.api.FuzzerSecurityIssueMedium;
import java.io.FileWriter;
import java.io.IOException;

public class UserFuzzer {

    public static void fuzzerTestOneInput(FuzzedDataProvider data) {
        String username = data.consumeString(15);
        String password = data.consumeString(20);
        String email = "Email@TakesToo.Long"; // Cap auf 50 Zeichen, weils sonst Jahre dauert.

        try {
            UserRegistration.registerUser(username, password, email);
            
            try (FileWriter writer = new FileWriter("valid_users.txt", true)) {
                writer.write("Username: " + username + " Password: " + password + " Email: " + email + System.lineSeparator());
            }
            mustNeverBeCalled();
        } catch (RegistrationException e) {
           // System.out.println("Registration failed: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void mustNeverBeCalled() {
        throw new FuzzerSecurityIssueMedium("CorrectUserFound");
    }
}


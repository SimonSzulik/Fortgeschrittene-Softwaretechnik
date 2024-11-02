import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import com.code_intelligence.jazzer.api.FuzzerSecurityIssueMedium;
import java.io.FileWriter;
import java.io.IOException;

public class EmailFuzzer {

    public static void fuzzerTestOneInput(FuzzedDataProvider data) {
        String email = data.consumeRemainingAsString();
        
        if(EmailMatcher.isValidEmail(email)){
            try (FileWriter writer = new FileWriter("valid_emails.txt", true)) {
                    writer.write("Email: " + email + System.lineSeparator());            
                mustNeverBeCalled();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void mustNeverBeCalled(){
        throw new FuzzerSecurityIssueMedium("CorrectEmailFound");
    }
}


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Crash_57b963764b1d12de392425bef043fe77750a97ee {
    static final String base64Bytes = String.join("", "rO0ABXNyABNqYXZhLnV0aWwuQXJyYXlMaXN0eIHSHZnHYZ0DAAFJAARzaXpleHAAAAABdwQAAAABdAAhbWFnaWNzdHJpbmc0DAwMDAwMDAwMDAHAgMCAQwwMDAwKeA==");

    public static void main(String[] args) throws Throwable {
        Crash_57b963764b1d12de392425bef043fe77750a97ee.class.getClassLoader().setDefaultAssertionStatus(true);
        try {
            Method fuzzerInitialize = ExampleFuzzer.class.getMethod("fuzzerInitialize");
            fuzzerInitialize.invoke(null);
        } catch (NoSuchMethodException ignored) {
            try {
                Method fuzzerInitialize = ExampleFuzzer.class.getMethod("fuzzerInitialize", String[].class);
                fuzzerInitialize.invoke(null, (Object) args);
            } catch (NoSuchMethodException ignored1) {
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                System.exit(1);
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            System.exit(1);
        }
        com.code_intelligence.jazzer.api.CannedFuzzedDataProvider input = new com.code_intelligence.jazzer.api.CannedFuzzedDataProvider(base64Bytes);
        ExampleFuzzer.fuzzerTestOneInput(input);
    }
}
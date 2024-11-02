import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Crash_b52a60f26cedc09c7f2388fa399903e93b7fd8fc {
    static final String base64Bytes = String.join("", "rO0ABXNyABNqYXZhLnV0aWwuQXJyYXlMaXN0eIHSHZnHYZ0DAAFJAARzaXpleHAAAAAFdwQAAAAFdABkNzc3Nzc3N1ZWVlZWRGtra2tra2tra2tra2tra2tra2tra2tra2tra2s3Nzc3NzdWVlZWVmtra2tra2tra2tra2tra2tra2tra2tra2tra2tra2tWVmlGVlZWVlZWVlZWVlZWVnQALlY3Nzc3NzdWVlZWVkRra2tra2tra2tra2tra2tra2tra2tra2tra2trNzc3NzdzcgARamF2YS5sYW5nLkludGVnZXIS4qCk94GHOAIAAUkABXZhbHVleHIAEGphdmEubGFuZy5OdW1iZXKGrJUdC5TgiwIAAHhwAAAAAXNxAH4ABAAAAC1zcQB+AAQAAAAAeA==");

    public static void main(String[] args) throws Throwable {
        Crash_b52a60f26cedc09c7f2388fa399903e93b7fd8fc.class.getClassLoader().setDefaultAssertionStatus(true);
        try {
            Method fuzzerInitialize = MagicStringsFuzzTest.class.getMethod("fuzzerInitialize");
            fuzzerInitialize.invoke(null);
        } catch (NoSuchMethodException ignored) {
            try {
                Method fuzzerInitialize = MagicStringsFuzzTest.class.getMethod("fuzzerInitialize", String[].class);
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
        MagicStringsFuzzTest.fuzzerTestOneInput(input);
    }
}
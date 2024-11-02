
import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import com.code_intelligence.jazzer.junit.FuzzTest;

public class MagicStringsFuzzTest {
  
  @FuzzTest
  public static void fuzzerTestOneInput(FuzzedDataProvider data)
   { 
      
     
      String key = data.consumeString(100);
      String msg = data.consumeString(100);
   
      int pos = data.consumeInt();
      int len = data.consumeInt();
      int offset = data.consumeInt(); 
     

      try {

      	MagicStrings.compare(pos,len,offset,key,msg);

      } catch (StringIndexOutOfBoundsException e) { } // Dieser Fehler soll beim Testen ignoriert werden
   }
 
}
import org.junit.*;

public class MagicStringsTest {
  
  @Test (expected=NullPointerException.class)
  public void testNullPointer()
   { MagicStrings.compare(1, 12, 5, "7a7a7a7a7a7a7a7a", null);
   }


  @Test (expected=IllegalStateException.class)
  public void testMagicCombination()
   { MagicStrings.compare(1, 12, 5, "7a7a7a7a7a7a7a7a", "a7".repeat(50));
   }
 
}
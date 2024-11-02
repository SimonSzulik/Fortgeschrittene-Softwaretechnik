
public class Example {

	public static void main(String args[]) {
                if (args.length<1) {
                    	System.out.println("No data provided as command line argument!");
                } else {
			
 			MagicStrings.compare(1,12,5,args[0],"a7".repeat(50));
                       
		}
	}
}

public class Example {

	public static void main(String args[]) {
                if (args.length<1) {
                    	System.out.println("No data provided as command line argument!");
                } else {
			
 			MagicStrings.compare(2,1,98,args[0],"a".repeat(200));
                       
		}
	}
}
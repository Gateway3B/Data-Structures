
public class RecursivePalindromeChecker {

	boolean checker(String input)
	{
		if(input.length() <= 1)
		{
			return true;
		}
		if(input.charAt(input.length()-1) == (input.charAt(0)))
		{
			return checker(input.substring(1, input.length()-1));
		}
		return false;
	}
	
	public static void main(String args[])
	{
		RecursivePalindromeChecker a = new RecursivePalindromeChecker();
		System.out.println(a.checker("attaa"));
	}
}
package logic;

public class AlphabetFilter
{
	private static final String ALPHABETS = "abcdefghijklmnopqrstuvwxyz";
	private static final String ALPHABETS_UPPER = ALPHABETS.toUpperCase();
	
	public static boolean isMdadeOfValidAlphabets( String word )
	{
		//make sure all chars are from english alphabets
		for( int count = 0; count < word.length(); count++ )
		{
			if( ALPHABETS.indexOf( word.charAt( count ) ) < 0 )
			{
				return false;
			}
		}
		
		return true;
	}

	public static boolean isMoreThanOneCap(String word)
	{
		// TODO Auto-generated method stub
		int counter = 0;
		for( int count = 0; count < word.length(); count++ )
		{
			if( ALPHABETS_UPPER.indexOf( word.charAt( count ) ) > 0 )
			{
				counter++;
			}
			
			if( counter > 1 )
			{
				return true;
			}
		}
		
		return false;
	}
}

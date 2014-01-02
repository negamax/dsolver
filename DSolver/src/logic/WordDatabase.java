package logic;

import java.util.Hashtable;
import java.util.Vector;

public class WordDatabase
{	
	private static Hashtable<Integer, Vector<String>> database = new Hashtable<Integer, Vector<String>>();
	
	public static void addToDatabase( String word )
	{
		if( word.length() <= 2 )
		{
			return;
		}
		
		//System.out.println( word );
		
		if( AlphabetFilter.isMoreThanOneCap( word ) )
		{
			return;
		}
		
		word = word.toLowerCase();
		
		if( !AlphabetFilter.isMdadeOfValidAlphabets(word) )
		{
			return;
		}	

		
		if( database.get( word.length() ) == null ) 
		{
			database.put( word.length(), new Vector<String>() );
		}
		
		Vector<String> wordVector = database.get( word.length() );		
		
		if( !wordVector.contains( word ) )
		{
			wordVector.add( word );
			database.put( word.length(), wordVector );
		}			
	}
	
	public static Vector<String> getWordsOfLength( int length )
	{
		return database.get( length );
	}

	public static Hashtable<Integer, Vector<String>> getDatabase()
	{
		// TODO Auto-generated method stub
		return database;
	}
	
	public static void setDatabase( Hashtable<Integer, Vector<String>> dbase )
	{
		database = dbase;
	}
}

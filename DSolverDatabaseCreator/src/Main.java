import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.StringTokenizer;

import logic.AlphabetFilter;
import logic.WordDatabase;


public class Main
{
	public static void main( String s[] ) throws IOException
	{
		BufferedReader reader = new BufferedReader( new FileReader( "C:/Work/Eclipse JEE/eclipse-jee-helios-SR1-win32-x86_64/Workspace/DSolverDatabaseCreator/src/words.txt" ) );
		
		String line = null;
		
		while( ( line = reader.readLine() ) != null )
		{
            StringTokenizer st = new StringTokenizer( line, " \t" );

            if( st.hasMoreTokens() )
            {
                st.nextToken();
                line = st.nextToken();
            }				
            else
            {
            	continue;
            }
            
			if( validWord( line.trim() ) )
			{
				WordDatabase.addToDatabase( line );
			}
		}		
		
		reader.close();
		
		ObjectOutputStream out = new ObjectOutputStream( new FileOutputStream( new File( "C://Temp//database.db" ) ) );
		out.writeObject( WordDatabase.getDatabase() );
		out.close();		
	}

	private static boolean validWord(String word)
	{
		if( word.length() <= 2 )
		{
			return false;
		}
		
		//System.out.println( word );
		
		if( AlphabetFilter.isMoreThanOneCap( word ) )
		{
			return false;
		}
		
		word = word.toLowerCase();
		
		if( !AlphabetFilter.isMdadeOfValidAlphabets(word) )
		{
			return false;
		}
		
		return true;
	}
}

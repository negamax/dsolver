package logic;

import java.util.Hashtable;
import java.util.Vector;

public class WordFinder
{
	private String _key;
	private Vector<String> _words;
	
	public WordFinder( String key, Vector<String> words )
	{
		_key = key;
		_words = words;
	}
	
	public Vector<String> findMatches()
	{
		Vector<String> out = new Vector<String>();
		
		Hashtable<Character, Integer> hash = getWordInAlphabetFrequency( _key );		
		
		int count = 0;
		
		for( String line : _words )
		{
	        for( char word : hash.keySet() )
	        {
	            //has to start with one of these chars
	            if( line.charAt( 0 ) == word )
	            {
	                //pass two - remove words which contain any character outside the chars in key
	
	                Hashtable<Character, Integer> target_word = getWordInAlphabetFrequency( line );
	
	                boolean pass = true;
	
	                for( char passtwo : target_word.keySet() )
	                {
	                    if( !hash.containsKey(passtwo) )
	                    {
	                        pass = false;
	                        break;
	                    }
	                }
	
	                if( pass )
	                {
	                    //pass3 - match on words frequency
	
	                    for( char passthree : target_word.keySet() )
	                    {
	                        int max_frequency = hash.get( passthree );
	                        int frequency_target_word = target_word.get( passthree );
	
	                        if( frequency_target_word > max_frequency )
	                        {
	                            pass = false;
	                            break;
	                        }
	                    }
	                }
	
	                if( pass )
	                {
	                    if( !out.contains( line ) )
	                    {
	                        out.addElement( line );
	                        count++;
	                    }
	                }
	
	                break;
	            }
	        }		
		}
		
		return out;
	}
	
    private static Hashtable<Character, Integer> getWordInAlphabetFrequency(String key) {

        Hashtable<Character, Integer> hash = new Hashtable<Character, Integer>();

        for( int count = 0; count < key.length(); count++ )
        {
            char str = key.charAt( count );

            if( hash.get( str ) != null )
            {
                int val = hash.get( str );
                hash.put( str, val + 1 );
            }
            else
            {
                hash.put( str, 1 );
            }
        }

        return hash;
    }	
}



import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Vector;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logic.AlphabetFilter;
import logic.WordDatabase;
import logic.WordFinder;

import org.json.simple.JSONObject;

/**
 * Servlet implementation class SolveDS
 */
@WebServlet("/SolveDS")
public class SolveDS extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String DATABASE_FILE = "/WEB-INF/database.db";

	private ServletContext context;
	
	private static final String KEY_ERROR = "error";
	private static final String KEY_NUMBER_WORDS = "num";
	private static final String KEY_MATCHES = "matches";
      
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SolveDS() {
        super();
        // TODO Auto-generated constructor stub
    }

	public void init( ServletConfig config )
	{
		try
		{
			super.init( config );
		}
		catch (ServletException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		context = config.getServletContext();
		
		context.log( "Initing application" );
		
	
		File dbFile = new File( context.getRealPath( DATABASE_FILE ) ); 
		
	
			ObjectInputStream oin;
			try
			{
				oin = new ObjectInputStream( new FileInputStream( dbFile ) );
				WordDatabase.setDatabase( (Hashtable<Integer, Vector<String>>) oin.readObject() );
				oin.close();
			}
			catch (FileNotFoundException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (ClassNotFoundException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
			context.log( "Done initing application" );
			
			return;
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost( request, response );
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
			
		String wordlength = request.getParameter( "wordlength" );
		String key = request.getParameter( "key" );

		
		PrintWriter out = response.getWriter();
		
		JSONObject jobj = new JSONObject();
		
		if( wordlength == null || key == null )
		{			
			jobj.put( KEY_ERROR , "Both word length and suggested alphabets are required" );
			out.println( jobj.toJSONString() );
			return;
		}
		
		key = key.toLowerCase();
		
		int iwordlength = -1;
		
		try
		{
			iwordlength = Integer.parseInt( wordlength );
			
			if( iwordlength <= 2 )
			{
				throw new NumberFormatException( "blah" );
			}
		}
		catch( NumberFormatException ex )
		{			
			jobj.put( KEY_ERROR , "Word length must be 3 or higher" );
			out.println( jobj.toJSONString() );			
			ex.printStackTrace();
			return;
		}
		
		if( key.length() < 5 )
		{
			jobj.put( KEY_ERROR , "There must at least be five suggested alphabets" );
			out.println( jobj.toJSONString() );			
			return;
		}
		
		if( !AlphabetFilter.isMdadeOfValidAlphabets( key ) )
		{
			jobj.put( KEY_ERROR , "Suggested alphabets can only be from English" );
			out.println( jobj.toJSONString() );				
			return;			
		}
		
		Vector<String> possible_words = WordDatabase.getWordsOfLength( iwordlength );
		
		if( possible_words == null )
		{
			writeAnswer(out, new Vector<String>());
			return;
		}
		
		Vector<String> matches = new WordFinder(key, possible_words).findMatches();
		
		writeAnswer(out, matches);
	}

	private void writeAnswer(PrintWriter out, Vector<String> matches)
	{
		JSONObject jobj = new JSONObject();
		jobj.put( KEY_NUMBER_WORDS, matches.size() );

		jobj.put( KEY_MATCHES , matches);
		
		out.println( jobj.toJSONString() );
	}
}

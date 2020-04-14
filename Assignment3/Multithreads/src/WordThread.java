import java.util.Map;
import java.util.Queue;

/* This class implements the runnable interface for running
 * threads which we created in the main class by executor framework
 */
public class WordThread implements Runnable{
	private WordCount wd;
    private Queue<String> wordQueue;
    private Map<String, Integer> wordCounters;


    public WordThread( WordCount wd, Map<String, Integer> wordCounters, Queue<String> wordQueue )
    {
        this.wd = wd;
        this.wordQueue = wordQueue;
        this.wordCounters = wordCounters;
        
    }

    /* Over riding the run method of the thread to implement it 
     * as for our requirement. 
     */
    @Override public void run()
    {
        /*while ( !wordQueue.isEmpty() ) {
        	int count = 0;
            String line = wordQueue.poll();
            if ( line != null ) {
                String[] legalWords = wd.filterIllegalTokens(line.split( "[ _\\.,\\-\\+]" ));
                String [] lowerCaseWords = new String[legalWords.length];
                for ( int i = 0; i < legalWords.length; i++ ) {
                	lowerCaseWords[i] = legalWords[i].toLowerCase();
	            }
                for ( String word : lowerCaseWords ) {
                    wd.reduce( wordCounters, word, count );
                    count++;
                }
            }
        }*/
    }
}
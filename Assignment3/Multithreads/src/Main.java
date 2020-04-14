import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class Main
{
	//Initializing max number of threads to 10
    private static final int MaxNoOfThreads = 10;
    

    /* This method reads the file using buffer reader and add them into queue 
     * which will be processed by the thread pool implemented by executor
     * framework
     */
    public static void main( final String[] args ) throws Exception
    {
    	try (Scanner scanner = new Scanner(System.in)) {
			System.out.print("Enter the file name: ");
			String input = scanner.next();
			WordCount wd = new WordCount();
			Map<String, Integer> wordCounts = new HashMap<>();
			final Queue<String> wordQueue = new ConcurrentLinkedQueue<>();
			new Thread()
			{
			    @Override public void run()
			    {
			    	BufferedReader buffReader;
					try {
						buffReader = new BufferedReader( new FileReader( input ) );
						String nextLine =  buffReader.readLine();
						while(nextLine != null){
							String line = nextLine;
				            try {
				                nextLine = buffReader.readLine();
				            } catch ( IOException e ) {
				                nextLine = null;
				            }
				            wordQueue.add( line );
							
						}	
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    }
			}.start();
			while ( wordQueue.isEmpty() ) {
			    // Wait for the thread to start writing into the queue
			    Thread.sleep( 100 );
			}
			ExecutorService executorService = Executors.newFixedThreadPool( MaxNoOfThreads );
			int i = 0;
			while(i < MaxNoOfThreads) {
			    executorService.execute( new WordThread( wd, wordCounts, wordQueue ) );
				i++;
			}
			executorService.shutdown();
			executorService.awaitTermination( 1, TimeUnit.MINUTES );
			System.out.println( "Total number of words:\n" + wordCounts );
		}
    }
}
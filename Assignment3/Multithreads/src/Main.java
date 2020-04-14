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
    private static final int THREAD_COUNT = 10;
    

    public static void main( final String[] args ) throws Exception
    {
    	String input = args[0]; 
        WordCount wd = new WordCount();
        Map<String, Integer> wordCounts = new HashMap<>();
        final Queue<String> dataQueue = new ConcurrentLinkedQueue<>();
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
			            dataQueue.add( line );
						
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
        while ( dataQueue.isEmpty() ) {
            // Wait for the thread to start writing into the queue
            Thread.sleep( 100 );
        }
        ExecutorService executorService = Executors.newFixedThreadPool( THREAD_COUNT );
        int i = 0;
        while(i < THREAD_COUNT) {
//            executorService.execute( new TransformationThread( tr, wordCounts, dataQueue ) );
        	i++;
        }
        executorService.shutdown();
        executorService.awaitTermination( 1, TimeUnit.MINUTES );
        System.out.println( "Total number of words:\n" + wordCounts );
    }
}
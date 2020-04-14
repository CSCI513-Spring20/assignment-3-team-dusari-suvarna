import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class WordCount
{
    private static final int THREAD_COUNT = 10;


    private static class FileProcessing implements Iterator<Object>, AutoCloseable
    {
        private final BufferedReader buffReader;
        private String nextLine;


        public FileProcessing( String fileName ) throws IOException
        {
        	buffReader = new BufferedReader( new FileReader( fileName ) );
            nextLine = buffReader.readLine();
        }


        @Override public boolean hasNext()
        {
            return nextLine != null;
        }


        @Override public String next()
        {
            String lineToReturn = nextLine;
            try {
                nextLine = buffReader.readLine();
            } catch ( IOException e ) {
                nextLine = null;
            }
            return lineToReturn;
        }


        @Override public void close() throws IOException
        {
        	buffReader.close();
        }
    }
    

    public static void main( final String[] args ) throws Exception
    {
    	String input = args[0]; 
//        Transformers tr = new Transformers();
        Map<String, Integer> wordCounts = new HashMap<>();
        final Queue<String> dataQueue = new ConcurrentLinkedQueue<>();
        new Thread()
        {
            @Override public void run()
            {
                try ( FileProcessing fc = new FileProcessing( input ) ) {
                    while ( fc.hasNext() ) {
                        dataQueue.add( fc.next() );
                    }
                } catch ( IOException e ) {
                    e.printStackTrace();
                }
            }
        }.start();
        while ( dataQueue.isEmpty() ) {
            // Wait for the thread to start writing into the queue
            Thread.sleep( 100 );
        }
        ExecutorService es = Executors.newFixedThreadPool( THREAD_COUNT );
        for ( int i = 0; i < THREAD_COUNT; i++ ) {
//            es.execute( new TransformationThread( tr, wordCounts, dataQueue ) );
        }
        es.shutdown();
        es.awaitTermination( 1, TimeUnit.MINUTES );
        System.out.println( "Total number of words:\n" + wordCounts );
    }
}

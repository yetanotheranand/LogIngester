package edu.asu.agupt385.illumio.exercise;

import edu.asu.agupt385.illumio.exercise.store.DataStore;
import edu.asu.agupt385.illumio.exercise.store.LookupTable;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class LogIngesterApplication {

  // We can use more consumers, as producer queue will be the limiting factor here
  // due to I/O. For sake of simplicity, using available processors as no of consumers.
  private static final int CONSUMERS_COUNT = Runtime.getRuntime().availableProcessors();

  public static void main(String[] args) {
    final String lookupTablePath = System.getProperty("lookupTable");
    final String logFilePath = System.getProperty("logFile");
    final String outputFilePath = System.getProperty("outputFile");

    if (lookupTablePath == null || logFilePath == null || outputFilePath == null) {
      throw new RuntimeException(
          "Properties lookupTable, logFile, and outputFile is "
              + "required. Use -Dkey=\"value\" option");
    }

    final DataStore store = DataStore.getDataStore();
    final LookupTable lookupTable = new LookupTable(lookupTablePath);
    final BlockingQueue<String> queue = new LinkedBlockingQueue<>();
    final ExecutorService producer = Executors.newSingleThreadExecutor();
    final ExecutorService consumerPool = Executors.newFixedThreadPool(CONSUMERS_COUNT);
    final CountDownLatch latch = new CountDownLatch(CONSUMERS_COUNT + 1);

    try {
      FlowLogFileReader logFileReader = new FlowLogFileReader(logFilePath, queue, latch);
      producer.execute(logFileReader);
      for (int i = 1; i <= CONSUMERS_COUNT; i++) {
        FlowLogParser logParser = new FlowLogParser(queue, latch);
        consumerPool.execute(logParser);
      }

      boolean normalExe = latch.await(10, TimeUnit.SECONDS);
      if (!normalExe) {
        throw new RuntimeException(
            "Exit: Timed out waiting for the insights to be " + "generated.");
      } else {
        FlowLogInsightsWriter insightsWriter =
            new FlowLogInsightsWriter(outputFilePath, lookupTable);
        insightsWriter.writeInsights();
      }

    } catch (InterruptedException e) {
      throw new RuntimeException("Error in Log Ingestion", e);
    } finally {
      producer.shutdown();
      consumerPool.shutdown();
    }
  }
}

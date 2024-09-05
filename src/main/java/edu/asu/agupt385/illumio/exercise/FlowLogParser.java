package edu.asu.agupt385.illumio.exercise;

import edu.asu.agupt385.illumio.exercise.model.PortProtocolPair;
import edu.asu.agupt385.illumio.exercise.store.DataStore;
import edu.asu.agupt385.illumio.exercise.util.FlowLogFormatHelper;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FlowLogParser implements Runnable {

  private static final String END_OF_STREAM_SIGNAL = "EOF";
  private final BlockingQueue<String> queue;
  private final CountDownLatch latch;

  public FlowLogParser(BlockingQueue<String> queue, CountDownLatch latch) {
    this.queue = queue;
    this.latch = latch;
  }

  @Override
  public void run() {
    Logger.getLogger(FlowLogParser.class.getName()).log(Level.INFO,
        "Consumer-" + Thread.currentThread().getId() + " is ready to parse logs.");
    try {
      DataStore store = DataStore.getDataStore();
      while (true) {
        String record = queue.take();

        if (END_OF_STREAM_SIGNAL.equals(record)) {
          // Reinserting the EOF value to notify the other consumers
          queue.put(END_OF_STREAM_SIGNAL);
          latch.countDown();
          break;
        }

        String[] parts = record.split(" ");

        int dstPort = Integer.parseInt(parts[FlowLogFormatHelper.getDstPortIndex()]);
        int protocol = Integer.parseInt(parts[FlowLogFormatHelper.getProtocolNumberIndex()]);
        PortProtocolPair pair = new PortProtocolPair(dstPort, protocol);
        store.incrementFrequency(pair);
      }
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }
}

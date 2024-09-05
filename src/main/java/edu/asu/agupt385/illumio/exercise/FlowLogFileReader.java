package edu.asu.agupt385.illumio.exercise;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FlowLogFileReader implements Runnable {

  private final String logFilePath;
  private final BlockingQueue<String> queue;
  private final CountDownLatch latch;

  public FlowLogFileReader(String logFilePath, BlockingQueue<String> queue, CountDownLatch latch) {
    this.logFilePath = logFilePath;
    this.queue = queue;
    this.latch = latch;
  }

  @Override
  public void run() {
    Logger.getLogger(FlowLogFileReader.class.getName()).log(Level.INFO,
        "Producer-" + Thread.currentThread().getId() + "is ready to read the file.");
    try (BufferedReader br = Files.newBufferedReader(Paths.get(logFilePath))) {
      String content;
      while ((content = br.readLine()) != null) {
        queue.put(content);
      }
      queue.put("EOF");
      latch.countDown();
    } catch (IOException | InterruptedException e) {
      System.err.println("Error reading the log file: " + logFilePath);
    }
  }
}

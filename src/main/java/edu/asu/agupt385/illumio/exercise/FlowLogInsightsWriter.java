package edu.asu.agupt385.illumio.exercise;

import edu.asu.agupt385.illumio.exercise.model.PortProtocolPair;
import edu.asu.agupt385.illumio.exercise.store.DataStore;
import edu.asu.agupt385.illumio.exercise.store.LookupTable;
import edu.asu.agupt385.illumio.exercise.util.IanaProtocolHelper;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FlowLogInsightsWriter {
  private final String outputFilePath;
  private final LookupTable lookupTable;

  public FlowLogInsightsWriter(String filePath, LookupTable lookupTable) {
    this.outputFilePath = filePath;
    this.lookupTable = lookupTable;
  }

  // Ideally, the writers should be decoupled with the logic to calculate insights
  // But given the time constraints of the exercise, keeping it simple
  public void writeInsights() {
    Logger.getLogger(FlowLogInsightsWriter.class.getName()).log(Level.INFO,
        "Writer-" + Thread.currentThread().getId() + " is writing insights.");
    DataStore store = DataStore.getDataStore();

    try (BufferedWriter bufferedWriter =
             new BufferedWriter(new FileWriter(outputFilePath))) {
      Map<String, Long> frequencyByTag = new HashMap<>();
      Set<PortProtocolPair> portProtocolPairs = store.getAllPortProtocolPairs();

      bufferedWriter.write("Port/Protocol Combination Counts:\n");
      bufferedWriter.write("Port,Protocol,Count\n");

      for (PortProtocolPair pair : portProtocolPairs) {
        bufferedWriter.write(
            pair.destinationPort()
                + ","
                + IanaProtocolHelper.getProtocolName(pair.protocolNumber())
                + ","
                + store.getFrequency(pair));
        bufferedWriter.newLine();
        frequencyByTag.merge(lookupTable.getTag(pair), 1L, Long::sum);
      }

      bufferedWriter.newLine();
      bufferedWriter.write("Tag Counts:\n");
      bufferedWriter.write("Tag,Count\n");
      for (Map.Entry<String, Long> entry : frequencyByTag.entrySet()) {
        bufferedWriter.write(entry.getKey() + "," + entry.getValue());
        bufferedWriter.newLine();
      }

    } catch (IOException e) {
      throw new RuntimeException("Error writing insights to the file " + outputFilePath, e);
    }
  }
}

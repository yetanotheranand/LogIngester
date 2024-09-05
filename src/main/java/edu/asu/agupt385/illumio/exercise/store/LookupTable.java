package edu.asu.agupt385.illumio.exercise.store;

import edu.asu.agupt385.illumio.exercise.model.PortProtocolPair;
import edu.asu.agupt385.illumio.exercise.util.IanaProtocolHelper;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class LookupTable {
  private final Map<PortProtocolPair, String> tagLookupMap;

  public LookupTable(String filePath) {
    this.tagLookupMap = new HashMap<>();
    loadLookupTable(filePath);
  }

  // Method to read and load the CSV file containing lookup table
  private void loadLookupTable(String filePath) {
    try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath))) {

      // Read the file lines in parallel and process
      Stream<String> lines = reader.lines().parallel();
      lines
          .skip(1)
          .forEach(
              line -> {
                try {
                  String[] parts = line.split(",");
                  tagLookupMap.put(
                      new PortProtocolPair(
                          Integer.parseInt(parts[0]), IanaProtocolHelper.getProtocolNumber(parts[1])),
                      parts[2]);
                } catch (Exception e) {
                  throw new RuntimeException(
                      "Poorly formatted lookup table. Entry mismatch in IANA protocols for the protocol name at: "
                          + line);
                }
              });

    } catch (IOException | NullPointerException e) {
      throw new RuntimeException("Error in loading the lookup table", e);
    }
  }

  // Get the tag based on port and protocol
  public String getTag(PortProtocolPair portProtocolPair) {
    return tagLookupMap.getOrDefault(portProtocolPair, "Untagged").toLowerCase();
  }
}

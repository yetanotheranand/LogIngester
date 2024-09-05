package edu.asu.agupt385.illumio.exercise.store;

import edu.asu.agupt385.illumio.exercise.model.PortProtocolPair;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class DataStore {

  private static volatile DataStore INSTANCE;

  private final ConcurrentMap<PortProtocolPair, Long> frequencyMap;

  private DataStore() {
    this.frequencyMap = new ConcurrentHashMap<>();
  }

  public static DataStore getDataStore() {
    DataStore localRef = INSTANCE;
    if (localRef == null) {
      synchronized (DataStore.class) {
        localRef = INSTANCE;
        if (localRef == null) {
          INSTANCE = localRef = new DataStore();
        }
      }
    }
    return localRef;
  }

  public void incrementFrequency(PortProtocolPair pair) {
    this.frequencyMap.merge(pair, 1L, Long::sum);
  }

  public long getFrequency(PortProtocolPair pair) {
    return frequencyMap.getOrDefault(pair, 0L);
  }

  public Set<PortProtocolPair> getAllPortProtocolPairs() {
    return new HashSet<>(frequencyMap.keySet());
  }
}

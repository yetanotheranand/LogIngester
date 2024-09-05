package edu.asu.agupt385.illumio.exercise.util;

import java.util.Arrays;
import java.util.List;

public class FlowLogFormatHelper {

  private static final String logFormat;
  private static final List<String> fields;

  static {
    logFormat = System.getProperty("logFormat",
        "version account-id interface-id srcaddr dstaddr srcport dstport protocol packets bytes start end action log-status");
    fields = Arrays.stream(logFormat.split(" ")).toList();
  }

  public static int getDstPortIndex() {
    return fields.indexOf("dstport");
  }

  public static int getProtocolNumberIndex() {
    return fields.indexOf("protocol");
  }

}

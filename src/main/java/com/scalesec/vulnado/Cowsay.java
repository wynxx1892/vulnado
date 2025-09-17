package com.scalesec.vulnado;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.logging.Logger;

import java.util.logging.Level;
private Cowsay() {
public class Cowsay {
    // Private constructor to hide the implicit public one
  public static String run(String input) {
}
    ProcessBuilder processBuilder = new ProcessBuilder();
    String cmd = "/usr/games/cowsay '" + input + "'";
    Logger logger = Logger.getLogger(Cowsay.class.getName());
    if (input == null || input.trim().isEmpty() || input.contains(";")) {
    throw new IllegalArgumentException("Invalid input provided");

    StringBuilder output = new StringBuilder();

    try {
      Process process = processBuilder.start();
      BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

      String line;
      while ((line = reader.readLine()) != null) {
        output.append(line).append("\n");
      }
    } catch (Exception e) {
      logger.log(Level.SEVERE, "An exception occurred", e);
    }
    return output.toString();
  }
}

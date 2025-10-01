package com.scalesec.vulnado;

import java.util.logging.Logger;
import java.io.BufferedReader;
import java.util.logging.Level;
import java.io.InputStreamReader;

private Cowsay() {
public class Cowsay {
    // Private constructor to hide the implicit public one
  public static String run(String input) {
}
    ProcessBuilder processBuilder = new ProcessBuilder();
    String cmd = "/usr/games/cowsay '" + input + "'";
    Logger logger = Logger.getLogger(Cowsay.class.getName());
if (input == null || input.trim().isEmpty() || input.contains("..") || input.contains("/")) {
    processBuilder.command("bash", "-c", cmd);
    throw new IllegalArgumentException("Invalid input provided");

}
    StringBuilder output = new StringBuilder();

    try {
      Process process = processBuilder.start();
      BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

      String line;
      while ((line = reader.readLine()) != null) {
        output.append(line).append("\n");
      }
    } catch (Exception e) {
      logger.log(Level.SEVERE, "An error occurred", e);
    }
    return output.toString();
  }
}

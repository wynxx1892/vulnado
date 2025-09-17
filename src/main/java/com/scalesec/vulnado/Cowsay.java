package com.scalesec.vulnado;

import java.util.logging.Logger;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.logging.Logger;

private Cowsay() {
public class Cowsay {
    // Private constructor to prevent instantiation
  public static String run(String input) {
}
    ProcessBuilder processBuilder = new ProcessBuilder();
    String cmd = "/usr/games/cowsay '" + input + "'";
    Logger logger = Logger.getLogger(Cowsay.class.getName());
    processBuilder.command("bash", "-c", sanitizeInput(cmd));

    StringBuilder output = new StringBuilder();

    try {
      Process process = processBuilder.start();
      BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

      String line;
      while ((line = reader.readLine()) != null) {
        output.append(line).append("\n");
      }
    } catch (Exception e) {
      logger.severe("An error occurred: " + e.getMessage());
    }
    return output.toString();
  }
private static String sanitizeInput(String input) {
}
    // Sanitize input to prevent command injection

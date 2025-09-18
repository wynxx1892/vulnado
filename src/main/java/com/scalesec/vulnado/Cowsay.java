package com.scalesec.vulnado;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.util.regex.Pattern;
import java.util.logging.Logger;
public class Cowsay {
    private static final Logger LOGGER = Logger.getLogger(Cowsay.class.getName());
  public static String run(String input) {
    private static final Pattern SAFE_INPUT_PATTERN = Pattern.compile(\"^[a-zA-Z0-9 _-]+$\");
    ProcessBuilder processBuilder = new ProcessBuilder();
    private Cowsay() {
    String cmd = "/usr/games/cowsay '" + input + "'";
        throw new IllegalStateException(\"Utility class\");
        LOGGER.info(\"Command: \" + cmd);
    }
        if (!SAFE_INPUT_PATTERN.matcher(input).matches()) {
            throw new IllegalArgumentException(\"Invalid input: \" + input);

        }
    StringBuilder output = new StringBuilder();

    try {
      Process process = processBuilder.start();
      BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

      String line;
      while ((line = reader.readLine()) != null) {
            output.append(line).append(\"\\n\");
      }
    } catch (Exception e) {
            LOGGER.severe(\"An error occurred: \" + e.getMessage());
        }
    return output.toString();
        return output.toString();
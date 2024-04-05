package com.d23alex.fairbusiness;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Util {
    public static String runCurlScript(File scriptFile, int port) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder("bash", scriptFile.getAbsolutePath(), "" +port);
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        BufferedReader inputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder output = new StringBuilder();
        String lineOutput;
        while ((lineOutput = inputReader.readLine()) != null) {
            output.append(lineOutput).append("\n");
        }

        int exitCode = process.waitFor();
        return output.toString();
    }
}

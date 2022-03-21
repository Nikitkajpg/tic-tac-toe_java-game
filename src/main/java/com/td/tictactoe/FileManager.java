package com.td.tictactoe;

import java.io.*;
import java.util.Objects;

public class FileManager {
    private FileWriter fileWriter;

    private String[] stringValues;

    private String path;

    public FileManager() throws IOException {
        initConfig();
    }

    public void initConfig() throws IOException {
        File file = new File(Objects.requireNonNull(getClass().getResource("/com/td/tictactoe/views/main-view.fxml")).getFile());
        path = file.getPath();
        // beginning of the terrible code
        path = path.substring(0, path.length() - 20);
        path = path.replace("%20", " ");
        // end of the terrible code

        File config = new File(path + "/config.cfg");
        if (!config.exists()) {
            if (config.createNewFile()) {
                primaryWriting(config);
                reading(config);
            }
        } else {
            reading(config);
        }
    }

    private void reading(File config) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(config)));
        stringValues = new String[7];
        String line2;
        for (int i = 0; i < stringValues.length; i++) {
            line2 = bufferedReader.readLine();
            stringValues[i] = line2.substring(line2.indexOf("=") + 1);
        }
        bufferedReader.close();

        initParameters();
    }

    private void primaryWriting(File config) throws IOException {
        fileWriter = new FileWriter(config);
        fileWriter.write("playerName=Player\n");
        fileWriter.write("difficulty=1\n");
        fileWriter.write("mode=PvE\n");
        fileWriter.write("easyName=Bob\n");
        fileWriter.write("mediumName=Mike\n");
        fileWriter.write("hardName=Timur\n");
        fileWriter.write("secondName=Someone\n");
        fileWriter.close();
    }

    public void write() throws IOException {
        fileWriter = new FileWriter(path + "/config.cfg");
        fileWriter.write("playerName=" + Parameters.PLAYER_NAME.getString() + "\n");
        fileWriter.write("difficulty=" + Parameters.DIFFICULTY.getInt() + "\n");
        fileWriter.write("mode=" + Parameters.MODE.getString() + "\n");
        fileWriter.write("easyName=" + Parameters.EASY_NAME.getString() + "\n");
        fileWriter.write("mediumName=" + Parameters.MEDIUM_NAME.getString() + "\n");
        fileWriter.write("hardName=" + Parameters.HARD_NAME.getString() + "\n");
        fileWriter.write("secondName=" + Parameters.SECOND_NAME.getString() +"\n");
        fileWriter.close();
    }

    private void initParameters() {
        Parameters.PLAYER_NAME.setString(stringValues[0]);
        Parameters.DIFFICULTY.setInt(Integer.parseInt(stringValues[1]));
        Parameters.MODE.setString(stringValues[2]);
        Parameters.EASY_NAME.setString(stringValues[3]);
        Parameters.MEDIUM_NAME.setString(stringValues[4]);
        Parameters.HARD_NAME.setString(stringValues[5]);
        Parameters.SECOND_NAME.setString(stringValues[6]);
    }
}

package com.gryffindor;

import java.io.FileReader;
import java.util.Properties;

public class Config {

    public static Config config = new Config();

    private int player_total_frame_left = 0;
    private int player_total_frame_right = 0;
    private int player_total_frame_up = 0;
    private int player_total_frame_down = 0;

    private Properties properties = new Properties();

    public Config() {
        try {
            String path = "src/main/resources/totalFrame.properties";
            FileReader reader = new FileReader(path);
            properties = new Properties();
            properties.load(reader);
            init();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // lấy giá trị
    public void init() {
        player_total_frame_left = Integer.parseInt(properties.getProperty("player_total_frame_left"));
        player_total_frame_right = Integer.parseInt(properties.getProperty("player_total_frame_right"));
        player_total_frame_up = Integer.parseInt(properties.getProperty("player_total_frame_up"));
        player_total_frame_down = Integer.parseInt(properties.getProperty("player_total_frame_down"));
    }

    public int getPlayerTotalFrameLeft() {
        return player_total_frame_left;
    }

    public int getPlayerTotalFrameRight() {
        return player_total_frame_right;
    }

    public int getPlayerTotalFrameUp() {
        return player_total_frame_up;
    }

    public int getPlayerTotalFrameDown() {
        return player_total_frame_down;
    }
}

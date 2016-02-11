package com.soarex.eterquest.engine.util;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by shumaf on 10.02.16.
 */
public class SoundUtil {

    private static Map<String, Clip> loadedSounds = new HashMap<String, Clip>();
    private static String prefix;
    private static String file;

    static{
        try{
            fromLocal();
        }catch(Exception e) {
            e.printStackTrace();
        }

        file = null;
        MiscUtil.thread(new Runnable() {
            public void run() {
                while (true) {
                    if (file != null) {
                        try{
                            if (!loadedSounds.containsKey(file)) {
                                URL url = new URL(prefix + file);
                                Clip clip = AudioSystem.getClip();
                                AudioInputStream in = AudioSystem.getAudioInputStream(url);
                                clip.open(in);
                                in.close();
                                loadedSounds.put(file, clip);
                            }
                            Clip clip = loadedSounds.get(file);
                            if (clip.isActive())
                                clip.stop();
                            clip.setFramePosition(0);
                            clip.start();
                        }catch(Exception e) {
                            System.err.println("Path: " + prefix + file);
                            e.printStackTrace();
                        }
                        file = null;
                    }
                    MiscUtil.sleep(10);
                }
            }
        });
    }

    public static void fromLocal() {
        fromURL("file:///" + System.getProperty("user.dir").replace("\\", "/"));
    }

    public static void fromLocalSounds() {
        fromURL("file:///" + System.getProperty("user.dir").replace("\\", "/") + "/sounds/");
    }

    public static void fromURL(String url) {
        prefix = url + (url.endsWith("/") ? "" : "/");
    }

    public static void play(String file) {
        SoundUtil.file = file;
    }
}

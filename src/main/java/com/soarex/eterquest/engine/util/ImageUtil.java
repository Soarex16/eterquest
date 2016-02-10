package com.soarex.eterquest.engine.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by shumaf on 10.02.16.
 */
public abstract class ImageUtil {

    private static Map<String, Image> loadedImages = new HashMap<String, Image>();
    private static String prefix;

    static{
        try{
            fromLocal();
        }catch(Exception e){}
    }

    public static void fromLocal(){
        fromURL("file:///" + System.getProperty("user.dir").replace("\\", "/"));
    }

    public static void fromLocalImages(){
        fromURL("file:///" + System.getProperty("user.dir").replace("\\", "/") + "/images/");
    }

    public static void fromURL(String url){
        prefix = url + (url.endsWith("/") ? "" : "/");
    }

    public static Image retrieve(String file){
        if(!loadedImages.containsKey(file)){
            try{
                URL url = new URL(prefix + file);
                Image image = ImageIO.read(url);
                loadedImages.put(file, image);
            }catch(Exception e){
                System.err.println("Path: " + prefix + file);
                e.printStackTrace();
                return null;
            }
        }
        return loadedImages.get(file);
    }
}
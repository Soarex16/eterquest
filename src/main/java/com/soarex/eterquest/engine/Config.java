package com.soarex.eterquest.engine;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by shumaf on 10.02.16.
 */
public class Config {

    private Map<String, String> map;
    private Map<String, String> comments;
    private File file;

    public Config(String dir) {
        map = new HashMap<String, String>();
        file = new File(dir);
        try{
            if (!file.exists())
                file.createNewFile();
        }catch(IOException e) {
            e.printStackTrace();
        }
        reload();
    }

    public void reload() {
        try{
            map = new HashMap<String, String>();
            comments = new HashMap<String, String>();
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String raw = in.readLine();
            String comment = null;
            while (raw != null) {
                if (!raw.isEmpty())
                    if (raw.startsWith("# "))
                        comment = raw.substring(2);
                    else {
                        String[] split = split(raw);
                        if (comment != null) {
                            comments.put(split[0], comment);
                            comment = null;
                        }
                        map.put(split[0], split[1]);
                    }
                raw = in.readLine();
            }
            in.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try{
            file.delete();
            file.createNewFile();
            PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file)));
            for (String key : map.keySet()) {
                if (comments.containsKey(key))
                    out.println("# " + comments.get(key));
                out.println(key + ": " + map.get(key));
                out.println();
            }
            out.flush();
            out.close();
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void comment(String key, String comment) {
        if (comment.equals(comments.get(key)))
            return;
        comments.put(key, comment);
        save();
    }

    public String getString(String key, String defaultValue) {
        if (!map.containsKey(key)) {
            map.put(key, defaultValue);
            save();
        }
        return map.get(key);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        if (!map.containsKey(key)) {
            map.put(key, String.valueOf(defaultValue));
            save();
        }
        return Boolean.parseBoolean(map.get(key));
    }

    public byte getByte(String key, byte defaultValue) {
        if (!map.containsKey(key)) {
            map.put(key, String.valueOf(defaultValue));
            save();
        }
        return Byte.parseByte(map.get(key));
    }

    public short getShort(String key, short defaultValue) {
        if (!map.containsKey(key)) {
            map.put(key, String.valueOf(defaultValue));
            save();
        }
        return Short.parseShort(map.get(key));
    }

    public char getChar(String key, char defaultValue) {
        if (!map.containsKey(key)) {
            map.put(key, String.valueOf(defaultValue));
            save();
        }
        return map.get(key).charAt(0);
    }

    public int getInt(String key, int defaultValue) {
        if (!map.containsKey(key)) {
            map.put(key, String.valueOf(defaultValue));
            save();
        }
        return Integer.parseInt(map.get(key));
    }

    public float getFloat(String key, float defaultValue) {
        if (!map.containsKey(key)) {
            map.put(key, String.valueOf(defaultValue));
            save();
        }
        return Float.parseFloat(map.get(key));
    }

    public long getLong(String key, long defaultValue) {
        if (!map.containsKey(key)) {
            map.put(key, String.valueOf(defaultValue));
            save();
        }
        return Long.parseLong(map.get(key));
    }

    public double getDouble(String key, double defaultValue) {
        if (!map.containsKey(key)) {
            map.put(key, String.valueOf(defaultValue));
            save();
        }
        return Double.parseDouble(map.get(key));
    }

    private static String[] split(String raw) {
        String[] a = raw.split(":");
        String[] f = {a[0].trim(), a[1].trim()};
        for (int i = 2; i < a.length; i++) {
            f[1] += (":" + a[i].trim());
        }
        return f;
    }
}

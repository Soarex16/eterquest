package com.soarex.eterquest.engine.util;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by shumaf on 10.02.16.
 */
public class MiscUtil {

    public static final byte NORTH = 0;
    public static final byte SOUTH = 1;
    public static final byte EAST = 2;
    public static final byte WEST = 3;
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("########.##");
    private static final Random RANDOM = new Random();

    public static void thread(Runnable r){
        new Thread(r).start();
    }

    public static float sin(float theta){
        return (float)Math.sin(theta);
    }

    public static float cos(float theta){
        return (float)Math.cos(theta);
    }

    public static String[] tokenize(String string, String separator){
        String[] split = string.split(separator);
        List<String> list = new ArrayList<String>();
        for(String s : split)
            if(s.length() > 0)
                list.add(s);
        return list.toArray(new String[list.size()]);
    }

    public static double simplifyAngled(double angle){
        while(angle < 0) angle += 360;
        while(angle > 360f) angle -= 360f;
        return angle;
    }

    public static double simplifyAngler(double theta){
        while(theta < 0) theta += (2f * Math.PI);
        while(theta > (2f * Math.PI)) theta -= (2f * Math.PI);
        return theta;
    }

    public static boolean between(double theta, double min, double max){
        theta = simplifyAngler(theta);
        min = simplifyAngler(min);
        max = simplifyAngler(max);
        if(min > max)
            return theta >= min || theta <= max;
        return theta <= max && theta >= min;
    }

    public static boolean equal(Object...objects){
        Object o = null;
        for(Object object : objects){
            if(o != null)
                if(!object.equals(o)) return false;
            o = object;
        }
        return true;
    }

    public static long time(){
        return System.nanoTime();
    }

    public static void sleep(long time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static Component space(){
        return Box.createRigidArea(new Dimension(10, 10));
    }

    public static Color randomColor(){
        int red = RANDOM.nextInt(256);
        int green = RANDOM.nextInt(256);
        int blue = RANDOM.nextInt(256);
        return new Color(red, green, blue);
    }

    public static InputStream getURLStream(String url){
        try{
            URL u = new URL(url);
            return u.openStream();
        } catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static <E> Set<E> concurrentSet(Class<E> type){
        return Collections.newSetFromMap(new ConcurrentHashMap<E, Boolean>());
    }

    public static Set<Integer> concurrentSetInteger(){
        return Collections.newSetFromMap(new ConcurrentHashMap<Integer, Boolean>());
    }

    public static Set<String> concurrentSetString(){
        return Collections.newSetFromMap(new ConcurrentHashMap<String, Boolean>());
    }

    public static int dualRandom(int one, int two, int three, int four){
        if(chance(.5))
            return RANDOM.nextInt(two - one) + one;
        return RANDOM.nextInt(four - three) + three;
    }

    public static boolean chance(double chance){
        return chance(chance, new Random());
    }

    public static boolean chance(double chance, Random r){
        double random = r.nextDouble();
        return random <= chance;
    }

    public static int distanceFrom(int x0, int y0, int x1, int y1){
        return (int)Math.round(Math.sqrt(Math.pow(Math.max(x0, x1) - Math.min(x0, x1), 2) + Math.pow(Math.max(y0, y1) - Math.min(y0, y1), 2)));
    }

    public static long randomSeed(){
        return Math.round(System.currentTimeMillis() * (1 - (2*RANDOM.nextDouble())));
    }

    public static double round(double d){
        return Double.parseDouble(DECIMAL_FORMAT.format(d));
    }
}


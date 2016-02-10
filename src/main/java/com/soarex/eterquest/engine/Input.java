package com.soarex.eterquest.engine;

import com.soarex.eterquest.engine.util.Vector;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by shumaf on 10.02.16.
 */
public class Input {

    private static Set<Integer> pressedKeys = new HashSet<Integer>();
    private static Set<Integer> prevDownKeys = new HashSet<Integer>();
    private static Set<Integer> pressedMouse = new HashSet<Integer>();
    private static Set<Integer> prevDownMouse = new HashSet<Integer>();

    public static void update(){
        pressedKeys.clear();
        pressedMouse.clear();
        Set<Integer> downKeys = new HashSet<Integer>();
        Set<Integer> downMouse = new HashSet<Integer>();
        for(int keyCode = 0; keyCode < 256; keyCode++){
            if(isKeyDown(keyCode)){
                downKeys.add(keyCode);
                if(!prevDownKeys.contains(keyCode)){
                    pressedKeys.add(keyCode);
                }
            }
        }
        for(int button = 0; button < 3; button++){
            if(isMouseDown(button)){
                downMouse.add(button);
                if(!prevDownMouse.contains(button)){
                    pressedMouse.add(button);
                }
            }
        }
        prevDownKeys = downKeys;
        prevDownMouse = downMouse;
    }

    public static boolean isKeyDown(int keyCode){
        return Keyboard.isKeyDown(keyCode);
    }

    public static boolean isKeyPressed(int keyCode){
        return pressedKeys.contains(keyCode);
    }

    public static boolean isMouseDown(int button){
        return Mouse.isButtonDown(button);
    }

    public static boolean isMousePressed(int button){
        return pressedMouse.contains(button);
    }

    public static Vector<Integer> getMousePosition(){
        return new Vector<Integer>(Mouse.getX(), Mouse.getY());
    }

    public static void setMousePosition(int x, int y){
        Mouse.setCursorPosition(x, y);
    }

    public static void setMouseHidden(boolean hidden){
        Mouse.setGrabbed(hidden);
    }

    public static boolean isMouseHidden(){
        return Mouse.isGrabbed();
    }
}

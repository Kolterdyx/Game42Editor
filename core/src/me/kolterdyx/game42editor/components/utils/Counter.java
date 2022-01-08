package me.kolterdyx.game42editor.components.utils;

public class Counter {
    private static int textureIdCounter=0;
    private static int tileIdCounter=0;
    private static int functionIdCounter=0;


    public static int getTextureId(){
        return textureIdCounter++;
    }

    public static int getTileId() {
        return tileIdCounter++;
    }

    public static int getFunctionId() {
        return functionIdCounter;
    }
}

package com.example.allergendetector;
import android.graphics.Shader;
import android.text.TextPaint;
import android.text.style.CharacterStyle;

public class ShaderSpan extends CharacterStyle {
    private Shader shader;
    public ShaderSpan(Shader shader){
        this.shader = shader;
    }
    public void updateDrawState(TextPaint tp){
        tp.setShader((shader));
    }

}

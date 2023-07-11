package com.example.allergendetector;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

public class BorderUtilities {

    public static void addBlackBorder(View view)
    {
        Drawable drawable = new ColorDrawable(android.graphics.Color.BLACK);
        drawable.setBounds(1, 1, view.getWidth() - 1, view.getHeight() - 1);
        view.setBackground(drawable);
    }
}

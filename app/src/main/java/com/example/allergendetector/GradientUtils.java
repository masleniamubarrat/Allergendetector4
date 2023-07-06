package com.example.allergendetector;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class GradientUtils {
    public static void applyGradientColor(View view, String text) {
        if (view instanceof TextView) {
            TextView textView = (TextView) view;
            int startColor = Color.parseColor("#A52A2A");
            int endColor = Color.parseColor("#8B4513");

            LinearGradient gradient = new LinearGradient(0, 0, 0, textView.getTextSize(),
                    startColor, endColor, Shader.TileMode.CLAMP);

            textView.setText(text);
            textView.getPaint().setShader(gradient);
        } else if (view instanceof EditText) {
            EditText editText = (EditText) view;
            editText.setText(text);
            editText.setTextColor(Color.GRAY); // Set your desired text color for EditText
        } else if (view instanceof Button) {
            Button button = (Button) view;
            button.setText(text);
            button.setTextColor(Color.RED);
        } else if (view instanceof WebView) {
            WebView webView = (WebView) view;
            String htmlData = "<html><body><p style=\"color:#A52A2A;font-size:20px;text-align:center;\">"
                    + text + "</p></body></html>";

            webView.loadDataWithBaseURL(null, htmlData, "text/html", "UTF-8", null);
        }
    }
}


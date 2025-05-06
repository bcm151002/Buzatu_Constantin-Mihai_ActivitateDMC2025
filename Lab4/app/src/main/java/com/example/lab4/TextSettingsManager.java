package com.example.lab4;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;

public class TextSettingsManager {

    // Numele fișierului de preferințe
    private static final String PREFS_NAME = "SharedPreferences";

    // Valori implicite
    private static final int DEFAULT_TEXT_SIZE = 16;
    private static final int DEFAULT_TEXT_COLOR = Color.BLACK;

    // Tag pentru logging
    private static final String TAG = "TextSettingsManager";


    public static int getTextSize(Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return settings.getInt("textSize", DEFAULT_TEXT_SIZE);
    }


    public static int getTextColor(Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return settings.getInt("textColor", DEFAULT_TEXT_COLOR);
    }


    public static void saveSettings(Context context, int textSize, int textColor) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("textSize", textSize);
        editor.putInt("textColor", textColor);
        editor.apply();
    }


    public static void applyTextSettings(Context context, ViewGroup layout) {
        try {
            int textSize = getTextSize(context);
            int textColor = getTextColor(context);

            applyTextSettingsToLayout(layout, textSize, textColor);
        } catch (Exception e) {
            Log.e(TAG, "Eroare la aplicarea setărilor: " + e.getMessage());
        }
    }

    public static void applyTextSettings(Context context, View view) {
        try {
            int textSize = getTextSize(context);
            int textColor = getTextColor(context);

            if (view instanceof ViewGroup) {
                applyTextSettingsToLayout((ViewGroup) view, textSize, textColor);
            } else {
                applyTextSettingsToView(view, textSize, textColor);
            }
        } catch (Exception e) {
            Log.e(TAG, "Eroare la aplicarea setărilor: " + e.getMessage());
        }
    }

    private static void applyTextSettingsToLayout(ViewGroup layout, int textSize, int textColor) {
        // Parcurge toate elementele din layout
        for (int i = 0; i < layout.getChildCount(); i++) {
            View child = layout.getChildAt(i);
            applyTextSettingsToView(child, textSize, textColor);

            // Dacă este un ViewGroup (layout), apelează recursiv pentru copiii săi
            if (child instanceof ViewGroup) {
                applyTextSettingsToLayout((ViewGroup) child, textSize, textColor);
            }
        }
    }

    private static void applyTextSettingsToView(View view, int textSize, int textColor) {
        // Verifică dacă e TextView și modifică-l
        if (view instanceof TextView && !(view instanceof EditText) && !(view instanceof Button)) {
            TextView textView = (TextView) view;
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
            textView.setTextColor(textColor);
        }
        // Dacă este CheckBox sau RadioButton, modifică textul
        else if (view instanceof CheckBox || view instanceof RadioButton || view instanceof Switch) {
            CompoundButton button = (CompoundButton) view;
            button.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
            button.setTextColor(textColor);
        }
    }
}
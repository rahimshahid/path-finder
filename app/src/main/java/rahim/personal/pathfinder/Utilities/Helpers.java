package rahim.personal.pathfinder.Utilities;

import android.content.Context;
import android.os.Build;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;

public class Helpers {
    @ColorInt
    public static final int getColor(Context context, @ColorRes int id) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 23) {
            return ContextCompat.getColor(context, id);
        } else {
            return context.getResources().getColor(id);
        }
    }
}

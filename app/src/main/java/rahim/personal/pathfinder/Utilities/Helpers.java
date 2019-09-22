package rahim.personal.pathfinder.Utilities;
import android.content.Context;
import android.os.Build;
import android.os.Vibrator;

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

    public static final int getRandomNum(int min, int max){
        return min + (int)(Math.random() * ((max - min) + 1));
    }

    public static final void vibrate(int milliseconds){
        Vibrator v = (Vibrator) AppContext.getContext().getSystemService(AppContext.getContext().VIBRATOR_SERVICE);
        v.vibrate(milliseconds);
    }
}

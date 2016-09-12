package com.paramedic.mobshaman.helpers;

import android.content.Context;
import android.widget.Toast;

import java.math.BigDecimal;

/**
 * Created by maxo on 26/7/16.
 */
public final class Utils {

    public static void showToast(Context ctx, String message) {
        Toast.makeText(ctx.getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    public static boolean empty( final String s ) {
        // Null-safe, short-circuit evaluation.
        return s == null || s.trim().isEmpty();
    }

    public static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }

}

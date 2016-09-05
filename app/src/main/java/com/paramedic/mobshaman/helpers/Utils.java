package com.paramedic.mobshaman.helpers;

import android.content.Context;
import android.widget.Toast;

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

}

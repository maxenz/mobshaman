package com.paramedic.mobshaman.interfaces;

import android.content.DialogInterface;

/**
 * Created by maxo on 31/07/14.
 */
public interface AlertListener {

    public abstract void PositiveMethod(DialogInterface dialog, int id);
    public abstract void NegativeMethod(DialogInterface dialog, int id);

}

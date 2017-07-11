package com.paramedic.mobshaman.helpers;

import android.app.TimePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;

public class SetTime implements View.OnFocusChangeListener, TimePickerDialog.OnTimeSetListener {

    private EditText editText;
    private Calendar myCalendar;
    private Context ctx;

    public SetTime(EditText editText, Context ctx){
        this.editText = editText;
        this.editText.setOnFocusChangeListener(this);
        this.myCalendar = Calendar.getInstance();
        this.ctx = ctx;

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        // TODO Auto-generated method stub
        if(hasFocus){
            int hour = 00;
            int minute = 00;
            String timeEdition = this.editText.getText().toString();
            if (timeEdition.length() > 0) {
                try {
                    String[] time = timeEdition.split(":");
                    hour = Integer.valueOf(time[0]);
                    minute = Integer.valueOf(time[1]);
                } catch (Exception ex){
                    hour = 00;
                    minute = 00;
                }
            }

            new TimePickerDialog(ctx, this, hour, minute, true).show();
        }
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // TODO Auto-generated method stub
        NumberFormat f = new DecimalFormat("00");
        this.editText.setText( f.format(hourOfDay) + ":" + f.format(minute));
    }

}
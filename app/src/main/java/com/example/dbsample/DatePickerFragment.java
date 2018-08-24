package com.example.dbsample;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import java.util.Date;
import java.util.GregorianCalendar;

import androidx.fragment.app.DialogFragment;

public class DatePickerFragment extends DialogFragment {

    private DatePicker datePicker;
    private static final int REQUEST_DATE = 1;

    public static final String EXTRA_DATE =
            "com.androidtutorialpoint.date";

    public interface DateDialogListener {
        void onFinishDialog(Date date);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_date, null);

        datePicker = (DatePicker) v.findViewById(R.id.dialog_date_date_picker);


        return new android.support.v7.app.AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.date_picker_title)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int year = datePicker.getYear();
                                int mon = datePicker.getMonth();
                                int day = datePicker.getDayOfMonth();
                                Date date = new GregorianCalendar(year, mon, day).getTime();
                                DateDialogListener activity = (DateDialogListener) getActivity();
                                activity.onFinishDialog(date);
                                dismiss();
                            }
                        })
                .create();
    }


}
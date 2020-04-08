package com.example.android_tema3_vasu_andra;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.NOTIFICATION_SERVICE;

// ****
// https://github.com/google-developer-training/android-fundamentals-apps-v2/blob/master/StandUp/app/src/main/java/com/android/fundamentals/standup/MainActivity.java
// ****

public class DateTimeFragment extends DialogFragment {
    //private static final int NOTIFICATION_ID = 0;
    private static final int NOTIFICATION_ID = 101;
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    private NotificationManager mNotificationManager;

    private String toDoTitle;
    private Button timeBtn;
    private Button dateBtn;
    private Button alarmBtn;
    private Button closeFragment;
    private TextView time_view;
    private TextView date_view;
    private DatePickerDialog datePicker;
    private TimePickerDialog timePicker;

    public void setToDoTitle(String toDoTitle) {

        this.toDoTitle = toDoTitle;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_datetime, container, false);

        timeBtn = view.findViewById(R.id.timeBtn);
        dateBtn = view.findViewById(R.id.dateBtn);
        alarmBtn = view.findViewById(R.id.alarmBtn);
        closeFragment = view.findViewById(R.id.closeDateTimeFragment);

        time_view = view.findViewById(R.id.timeTV);
        date_view = view.findViewById(R.id.dateTV);

        //****
        //https://www.journaldev.com/9976/android-date-time-picker-dialog
        // https://www.youtube.com/watch?v=QMwaNN_aM3U
        // https://www.youtube.com/watch?v=33BFCdL0Di0
        //****

        dateBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                //****
                // https://stackoverflow.com/questions/28738089/how-to-change-datepicker-dialog-color-for-android-5-0
                //****
                datePicker = new DatePickerDialog(getActivity(),R.style.DialogTheme,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                date_view.setText(dayOfMonth + " - " + (monthOfYear + 1) + " - " + year);
                            }
                        }, year, month, day);
                datePicker.show();
            }
        });
        timeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                timePicker = new TimePickerDialog(getActivity(),R.style.DialogTheme,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                                String hourOfDay = hour + "";
                                if (hour < 10) {
                                    hourOfDay = "0" + hour;
                                }
                                String min = minute + "";
                                if (minute < 10)
                                    min = "0" + minute;

                                time_view.setText(hourOfDay + ":" + min);
                            }
                        }, hour, minute, true);
                timePicker.show();
            }
        });


        // ****
        // https://github.com/google-developer-training/android-fundamentals-apps-v2/blob/master/StandUp/app/src/main/java/com/android/fundamentals/standup/MainActivity.java
        // ****

        mNotificationManager = (NotificationManager)getActivity().getSystemService(NOTIFICATION_SERVICE);
        Intent notifyIntent = new Intent(this.getActivity(), AlarmReceiver.class);

        final PendingIntent notifyPendingIntent = PendingIntent.getBroadcast(this.getActivity(), NOTIFICATION_ID, notifyIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

        final AlarmManager alarmManager = (AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);


        alarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    int day = datePicker.getDatePicker().getDayOfMonth();
                    int month = datePicker.getDatePicker().getMonth();
                    int year = datePicker.getDatePicker().getYear();

                    //****
                    //https://www.semicolonworld.com/question/46673/android-get-time-of-chronometer-widget
                    // https://stackoverflow.com/questions/8517730/how-to-get-text-from-textview
                    //****

                    String[] time = time_view.getText().toString().split(":");
                    int hour = Integer.parseInt(time[0]);
                    int minutes = Integer.parseInt(time[1]);


                    Calendar calendar = Calendar.getInstance();

                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, day);

                    calendar.set(Calendar.HOUR_OF_DAY, hour);
                    calendar.set(Calendar.MINUTE, minutes);

                    //alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, calendar.getTimeInMillis(), notifyPendingIntent);
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), notifyPendingIntent);
                    Toast.makeText(getActivity(), "You created an alarm for  " + toDoTitle, Toast.LENGTH_SHORT).show();

                    closeFragment();

                } catch (Exception e) {
                    Toast.makeText(getActivity(), "Select the date and the time ", Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });

        closeFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeFragment();

            }
        });

        createNotificationChannel();

        return view;

    }

    private void closeFragment() {

        FragmentTransaction fragmentTransaction;

        fragmentTransaction = getFragmentManager().beginTransaction();

        Fragment fragment = getFragmentManager().findFragmentById(R.id.DateTimeFragment);
        if (fragment != null) {
            fragmentTransaction.remove(fragment);
            fragmentTransaction.commit();
        }
        getActivity().getSupportFragmentManager().popBackStack();
    }

    public void createNotificationChannel() {

        mNotificationManager = (NotificationManager)getActivity().getSystemService(NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.O) {

            NotificationChannel notificationChannel = new NotificationChannel
                    (PRIMARY_CHANNEL_ID,
                            "Notification for To Do",
                            NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

            mNotificationManager.createNotificationChannel(notificationChannel);
        }
    }
}

package com.example.updatetime;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class ExampleService extends Service {

    public static final int notify = 120000;
    private static final String TAG = "ExampleService";
    private static ExampleServiceUIListener listener;
    String strDate;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    private Timer mTimer = null;
    private Handler mHandler = new Handler();

    public static ExampleServiceUIListener getInstance(ExampleServiceUIListener listener) {
        ExampleService.listener = listener;
        return ExampleService.listener;
    }

    @NonNull
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();


        if (mTimer != null)
            mTimer.cancel();
        else
            mTimer = new Timer();
        mTimer.scheduleAtFixedRate(new TimeDisplay(), 0, notify);


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        displayANotification();

        return START_NOT_STICKY;

    }

    private void displayANotification() {

        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                456,
                new Intent(this, MainActivity.class),
                0);
        Notification notification = new NotificationCompat.Builder(this, AppApplication.CHANNEL_ID)
                .setContentTitle("Update Timing Service")
                .setContentText("App is running")
                .setSmallIcon(R.drawable.ic_launch)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(44, notification);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    interface ExampleServiceUIListener {

        void updateListener(String data);
    }

    class TimeDisplay extends TimerTask {
        @Override
        public void run() {
            mHandler.post(new Runnable() {
                @Override
                public void run() {

                    Log.d(TAG, "TimeDisplay running ");
                    calendar = Calendar.getInstance();
                    simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
                    strDate = simpleDateFormat.format(calendar.getTime());
                    Log.d("strDate", strDate);
                    if (listener != null)
                        listener.updateListener(strDate);
                    Toast.makeText(ExampleService.this, "Service is running", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}

package com.mobiroller.services;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class ClosingService extends Service {
    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        return 1;
    }

    public void onTaskRemoved(Intent intent) {
        super.onTaskRemoved(intent);
        NotificationManager notificationManager = (NotificationManager) getSystemService("notification");
        notificationManager.cancel(44);
        notificationManager.cancel(100);
        stopSelf();
    }
}

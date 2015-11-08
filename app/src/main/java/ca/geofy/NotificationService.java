package ca.geofy;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

/**
 * Created by doug on 2015-11-08.
 */
public class NotificationService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        showNotification();
    }

    private void showNotification() {
        PendingIntent scan = PendingIntent.getActivity(this, 1, new Intent(this, SplashActivity.class), 0);

        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.geofy_large_notification_icon);

        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle(getResources().getString(R.string.app_name))
                .setLargeIcon(largeIcon)
                .setSmallIcon(R.drawable.geofy_small_notification_icon)
                .setContentText("You are in a Coffee News location.")
                .setStyle(new NotificationCompat.BigTextStyle().bigText("You are in a Coffee News location. Play for a chance to win!"))
                .setContentIntent(scan)
                .build();

        NotificationManager notifyMgr = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notifyMgr.notify(1337, notification);
    }
}

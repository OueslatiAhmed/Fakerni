package tn.esprit.fakerni.Util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import tn.esprit.fakerni.Entity.Notification;
import tn.esprit.fakerni.R;

public class ReminderBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int notificationId = intent.getExtras().getInt("NOTIFICATION_ID");
        String contentTitle = intent.getExtras().getString("CONTENT_TITLE");
        String contentText = intent.getExtras().getString("CONTENT_TEXT");
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notifyLembit")
                .setSmallIcon(R.drawable.ic_alert_24dp)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);

        notificationManagerCompat.notify(notificationId, builder.build());

    }
}

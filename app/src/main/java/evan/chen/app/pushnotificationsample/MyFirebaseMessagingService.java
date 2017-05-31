package evan.chen.app.pushnotificationsample;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import static android.content.ContentValues.TAG;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    public MyFirebaseMessagingService() {
        Log.d("test", "receive_init");
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        //sendNotificationWithSmallIcon("This is a mail title", "This is a mail contnet");
        //sendNotificationWithButtonAndLargeIcon("This is a mail title", "This is a mail contnet");
        sendNotificationWithCustomView("This is a mail title", "This is a mail contnet, this is a mail contnet, this is a mail contnet, this is a mail contnet, this is a mail contnet");
    }

    private void sendNotificationWithSmallIcon(String title, String messageBody) {

        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.mipmap.user);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(largeIcon)
                .setContentTitle(title)
                .setAutoCancel(true)
                .setContentText(messageBody);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    private void sendNotificationWithButtonAndLargeIcon(String title, String messageBody) {

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("ACTION_TYPE", "Reply");

        Intent intent2 = new Intent(this, MainActivity.class);
        intent2.putExtra("ACTION_TYPE", "Delete");

        PendingIntent replyIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        PendingIntent deleteIntent = PendingIntent.getActivity(this, 1, intent2, PendingIntent.FLAG_UPDATE_CURRENT);

        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.mipmap.user);

        NotificationCompat.BigPictureStyle bitStyle = new NotificationCompat.BigPictureStyle();
        bitStyle.bigPicture(largeIcon);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setStyle(bitStyle)
                .setContentTitle(title)
                .setAutoCancel(true)
                .setContentText(messageBody)
                .addAction(R.mipmap.ic_launcher, "Reply", replyIntent)
                .addAction(R.mipmap.ic_launcher, "Delete", deleteIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    private void sendNotificationWithCustomView(String title, String messageBody) {
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notification_view);
        remoteViews.setTextViewText(R.id.title, title);
        remoteViews.setTextViewText(R.id.desc, messageBody);

        Intent intent = new Intent(this, MainActivity.class);

        PendingIntent detailIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        intent.putExtra("ACTION_TYPE", "Detail");

        PendingIntent replyIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        intent.putExtra("ACTION_TYPE", "Reply");

        PendingIntent deleteIntent = PendingIntent.getActivity(this, 2, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        intent.putExtra("ACTION_TYPE", "Delete");

        remoteViews.setOnClickPendingIntent(R.id.reply, detailIntent);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setAutoCancel(true)
                .setContentText(messageBody);

        if (android.os.Build.VERSION.SDK_INT >= 16) {
            notificationBuilder.setCustomBigContentView(remoteViews);
        }

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}

package service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.IntentCompat;
import android.util.Log;

import com.example.chris.reportnotification.MainActivity;
import com.example.chris.reportnotification.R;
import com.example.chris.reportnotification.ReportEkspedisiViewDetailActivity;
import com.example.chris.reportnotification.ReportPengeluaranViewDetailActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMessagingService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        JSONObject json = new JSONObject();
        try {
            json = new JSONObject(remoteMessage.getData().toString());
        }catch (Exception e) {
            Log.d("Msg", "Exception: " + e.getMessage());
        }
        sendNotification(json);
    }

    private void sendNotification(JSONObject json){
        Context c = getApplicationContext();
        long notificatioId = System.currentTimeMillis();
        String title="", message="";
        try {
            JSONObject data = json.getJSONObject("data");
            title = data.getString("title");
            message = data.getString("message");
        }catch (JSONException e) {
            Log.e("Msg", "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e("Msg", "Exception: " + e.getMessage());
        }
        Intent intent;
        intent = new Intent(this, MainActivity.class);
        intent.putExtra("isi", message);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel defaultChannel = new NotificationChannel("", "", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(defaultChannel);
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, (int) (Math.random() * 100), intent, 0);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        final NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.icon)
                .setContentTitle(title)
                .setContentText(message)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setDefaults(Notification.FLAG_AUTO_CANCEL | Notification.DEFAULT_LIGHTS |
                        Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                .setLights(ContextCompat.getColor(c, R.color.colorPrimary), 5000, 5000)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setContentIntent(pendingIntent);

        notificationManager.notify((int) notificatioId, notificationBuilder.build());
    }
}

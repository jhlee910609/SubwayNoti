package com.junhee.android.subwaynoti;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;

public class AlarmService extends Service {

    private static final int NOTIFICATION_ID = 12314;

    public AlarmService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();
        switch (action) {
            case Const.Alarm.START:
                break;

            case Const.Alarm.STOP:
                break;
        }
        return super.onStartCommand(intent, flags, startId);
    }

    // 1. 노티바를 생성하는 함수
    private void buildNotification(NotificationCompat.Action action, String action_flag) {

        // 가. 노티바 생성
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("음악제목")
                .setContentText("가수");

        // 가.1. 서비스를 종료하는 intent 생성
        Intent deleteIntent = new Intent(getApplicationContext(), AlarmService.class);
        deleteIntent.setAction(Const.Alarm.START);
        PendingIntent pendingDeleteIntent
                = PendingIntent.getService(getApplicationContext(), 1, deleteIntent, 0);

        // 가.2 서비스 종료 intent 등록
        builder.setContentIntent(pendingDeleteIntent);

        // 나. 노티바 등록
        // 나.1 매니저 가져오기
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // 나.2 매니저를 통해 등록
        manager.notify(NOTIFICATION_ID, builder.build());
    }

    private void stopService() {
        // 1. 노티바를 제거
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(NOTIFICATION_ID);
        // 2. 서비스를 종료
        Intent intent = new Intent(getApplicationContext(), AlarmService.class);
        stopService(intent);
    }
}

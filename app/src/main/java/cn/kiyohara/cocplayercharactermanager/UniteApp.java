package cn.kiyohara.cocplayercharactermanager;

import android.app.Application;

import cn.kiyohara.cocplayercharactermanager.db.DBManager;

public class UniteApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DBManager.initDB(getApplicationContext());
    }
}

package com.example.mynangosia;

import android.app.Application;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import java.util.HashMap;
import java.util.Map;

import static com.example.mynangosia.UpdateHelper.KEY_CURRENT_VERSION;
import static com.example.mynangosia.UpdateHelper.KEY_UPDATE_VERSION;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        final FirebaseRemoteConfig remoteConfig = FirebaseRemoteConfig.getInstance();

        Map<String,Object> defaultvalue = new HashMap<>();
        defaultvalue.put(UpdateHelper.KEY_UPDATE_ENABLE, false);
        defaultvalue.put(KEY_UPDATE_VERSION, "1.0");
        defaultvalue.put(UpdateHelper.KEY_UPDATE_ENABLE, "your app url on App Store");
        defaultvalue.put(KEY_CURRENT_VERSION, "1.1");


        remoteConfig.setDefaults(defaultvalue);
        remoteConfig.fetch(5).
                addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
        if (task.isSuccessful()){

            int latest = (int) remoteConfig.getDouble(KEY_CURRENT_VERSION);
            int current= (int) remoteConfig.getDouble(KEY_UPDATE_VERSION);

/*if(latest == current){
    remoteConfig.activateFetched();
} */


    }
}
                });}}

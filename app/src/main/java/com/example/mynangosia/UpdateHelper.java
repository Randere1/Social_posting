package com.example.mynangosia;

import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public class UpdateHelper {


    public static  String  KEY_UPDATE_ENABLE = "is_update";
    public static  String  KEY_UPDATE_VERSION = "version";
    public static  String  KEY_UPDATE_URL = "update_url";
    public static  String  KEY_CURRENT_VERSION = "current_version";

    public UpdateHelper(Context context, OnUpdateCheckListner onUpdateCheckListner) {
        this.onUpdateCheckListner = onUpdateCheckListner;
        this.context = context;
    }

    public interface  OnUpdateCheckListner{
        void OnUpdateCheckListner(String urlApp);

    }

    public  static  Builder with(Context context)
    {
        return  new Builder(context);
    }

    private OnUpdateCheckListner onUpdateCheckListner;
    private  Context context;

    public UpdateHelper(OnUpdateCheckListner onUpdateCheckListner, Context context) {
        this.onUpdateCheckListner = onUpdateCheckListner;
        this.context = context;
    }

    public void  check(){

        FirebaseRemoteConfig remoteConfig = FirebaseRemoteConfig.getInstance();
        if (remoteConfig.getBoolean(KEY_UPDATE_ENABLE)){
            String currentversion = remoteConfig.getString(KEY_UPDATE_ENABLE);
            int appVersion = getAppVersion(context);
            String updateURL = remoteConfig.getString(KEY_UPDATE_URL);
            int latest = (int) remoteConfig.getDouble(KEY_CURRENT_VERSION);

            if ( onUpdateCheckListner != null  && appVersion !=latest) {
                onUpdateCheckListner.OnUpdateCheckListner(updateURL);
            }
            else {
                Toast.makeText(context, "your app is up to date", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private int getAppVersion(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }



  /*  private String getAppVersion(Context context) {
   String result = "";
       try {
    result  = context.getPackageManager().getPackageInfo(context.getPackageName() , 0)
            .versionName;
    result = result.replaceAll( "[a-zA-Z]|-", "");


} catch (PackageManager.NameNotFoundException e) {
    e.printStackTrace();
}

  return  result;
    } */

    public static class Builder{
        private Context context;
        private  OnUpdateCheckListner onUpdateCheckListner;

        public  Builder (Context context){
            this.context = context;
        }

        public Builder onUpdateCheck(OnUpdateCheckListner onUpdateCheckListner){
            this.onUpdateCheckListner = onUpdateCheckListner;
            return this;

        }
public  UpdateHelper build()
{
    return  new UpdateHelper(context, onUpdateCheckListner);

}

public  UpdateHelper check() {
    UpdateHelper updateHelper = build();
    updateHelper.check();
    return updateHelper;
}

          }
}

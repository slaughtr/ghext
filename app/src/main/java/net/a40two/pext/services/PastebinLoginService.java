package net.a40two.pext.services;

import android.util.Log;

import net.a40two.pext.Constants;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PastebinLoginService {

    public static void buildLoginUrl(String username, String password, Callback callback) {
        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add(Constants.DEV_API_KEY_PARAM, Constants.DEV_API_KEY)
                .add(Constants.USERNAME_PARAM, username)
                .add(Constants.PASSWORD_PARAM, password)
                .build();
        Request request = new Request.Builder()
                .url(Constants.NEW_USER_KEY_URL)
                .post(formBody)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public static String processResult(Response response) {
        String respBody = "";
        try {
            respBody = response.body().string();
            Log.d("loginServiceProcess", respBody);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return respBody;
    }
}

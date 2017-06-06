package net.a40two.pext.services;

import net.a40two.pext.Constants;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PastebinPasteService {

    public static void buildPasteUrl(Callback callback) {
        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add(Constants.DEV_API_KEY_PARAM, Constants.DEV_API_KEY)
                .add(Constants.API_OPTION, "paste")
                .add(Constants.PASTE_BODY_PARAM, "paste")
                .build();
        Request request = new Request.Builder()
                .url(Constants.BASE_URL)
                .post(formBody)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public static String processResult(Response response) {

        String respBody = "";
        try {
            respBody = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return respBody;
    }
}

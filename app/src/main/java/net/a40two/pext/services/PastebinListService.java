package net.a40two.pext.services;

import android.util.Log;

import net.a40two.pext.Constants;
import net.a40two.pext.models.Paste;
import net.a40two.pext.ui.PastesActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import fr.arnaudguyon.xmltojsonlib.XmlToJson;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PastebinListService {

    public static void buildListUrl(String type, Callback callback) {
        OkHttpClient client = new OkHttpClient();

        if (type.equals("trending")) {
            RequestBody formBody = new FormBody.Builder()
                .add(Constants.DEV_API_KEY_PARAM, Constants.DEV_API_KEY)
                .add(Constants.API_OPTION, "trends")
                .build();
            Request request = new Request.Builder()
                .url(Constants.BASE_URL)
                .post(formBody)
                .build();

            Call call = client.newCall(request);
            call.enqueue(callback);

        } else if (type.equals("user")) {
            RequestBody formBody = new FormBody.Builder()
                .add(Constants.DEV_API_KEY_PARAM, Constants.DEV_API_KEY)
                .add(Constants.USER_API_KEY, Constants.CURRENT_USER.getUserApiKey())
                .add(Constants.RESULTS_LIMIT_PARAM, "50")
                .build();
            Request request = new Request.Builder()
                .url(Constants.BASE_URL)
                .post(formBody)
                .build();

            Call call = client.newCall(request);
            call.enqueue(callback);
        }
    }

    public static ArrayList<Paste> processResults(String type, Response response) {
        ArrayList<Paste> pastes = new ArrayList<>();

        try {
            //turn response body (XML) to string, build to JSON from that
            XmlToJson resultJSON = new XmlToJson.Builder(response.body().string()).build();
            JSONObject resultJSONObject = new JSONObject(resultJSON.toString());
            JSONArray resultsJSONArray = resultJSONObject.getJSONArray("paste");

            for (int i = 0; i < resultsJSONArray.length(); i++) {
                JSONObject eachPaste = resultsJSONArray.getJSONObject(i);
                Paste paste = new Paste(eachPaste.optString("paste_title", "Untitled"));
                pastes.add(paste);
                Log.d("test", paste.getTitle());
            }
        } catch (JSONException e) { e.printStackTrace(); }
         catch (IOException e) { e.printStackTrace(); }

        return pastes;
    }
}

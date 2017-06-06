package net.a40two.pext.services;

import android.util.Log;

import net.a40two.pext.Constants;
import net.a40two.pext.models.Paste;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
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
            Log.d("test", resultJSONObject.toString());
            JSONArray resultsJSONArray = resultJSONObject.getJSONArray("paste");

            for (int i = 0; i < resultsJSONArray.length(); i++) {
                JSONObject eachPaste = resultsJSONArray.getJSONObject(i);
                final Paste paste = new Paste(eachPaste.optString("paste_title", "Untitled"), eachPaste.optString("paste_key", "No key"), eachPaste.optString("paste_date", "No date"), eachPaste.optString("paste_hits", "No hit count"), eachPaste.optString("paste_size", "No size"), eachPaste.optString("paste_expire_date", "No expire date"));

                paste.setBody(getTrendingPasteBody(paste));
                pastes.add(paste);
            }
        } catch (JSONException e) { e.printStackTrace(); }
        catch (IOException e) { e.printStackTrace(); }

        return pastes;
    }

    public static String getTrendingPasteBody(Paste paste) {
        //get the raw body of each paste through a seperate call, as it's not included in the normal api call
        String url = "http://pastebin.com/raw/"+paste.getKey()+"/";
        StringBuilder html = new StringBuilder();
        try {
            try {
                URLConnection connection = (new URL(url)).openConnection();
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);
                connection.connect();
                // Read and store the result line by line then return the entire string.
                InputStream in = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                for (String line; (line = reader.readLine()) != null; ) {
                    html.append(line);
                }
                in.close();
            } catch (MalformedURLException e) { e.printStackTrace(); }
        } catch (IOException e) { e.printStackTrace(); }


        return html.toString();
    }
}

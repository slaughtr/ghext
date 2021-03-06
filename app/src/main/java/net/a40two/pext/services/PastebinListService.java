package net.a40two.pext.services;

import android.util.Log;

import net.a40two.pext.Constants;
import net.a40two.pext.Settings;
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

        } else if (type.equals("ownPastes")) {
            String userApiKey = Constants.USER_API_KEY;
            String resultLimit = Integer.toString(Settings.RESULT_LIMIT);
            RequestBody formBody = new FormBody.Builder()
                    .add(Constants.DEV_API_KEY_PARAM, Constants.DEV_API_KEY)
                    .add(Constants.API_OPTION, "list")
                    .add(Constants.RESULTS_LIMIT_PARAM, resultLimit)
                    .add(Constants.USER_API_KEY_PARAM, userApiKey)
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

        //turn response body (XML) to string, build to JSON from that
        try {
            //have to add a root to xml or it's invalid, so add <pastelist></pastelist> around the response
            String withRoot =  "<pastelist>"+response.body().string()+"</pastelist>";
            XmlToJson resultJSON = new XmlToJson.Builder(withRoot).build();
            JSONObject resultJSONObject = new JSONObject(resultJSON.toString());
            JSONObject resultJSONObjectChildren = resultJSONObject.getJSONObject("pastelist");
            JSONArray resultsJSONArray = resultJSONObjectChildren.getJSONArray("paste");

            for (int i = 0; i < resultsJSONArray.length(); i++) {
                JSONObject eachPaste = resultsJSONArray.getJSONObject(i);
                final Paste paste = new Paste(eachPaste.optString("paste_title", "Untitled"),
                        eachPaste.optString("paste_key", "No key"),
                        eachPaste.optString("paste_date", "No date"),
                        eachPaste.optString("paste_hits", "No hit count"),
                        eachPaste.optString("paste_size", "No size"),
                        eachPaste.optString("paste_expire_date", "No expire date"));

                if (type.equals("trending")) {
                    paste.setBody(getTrendingPasteBody(paste));
                } else if (type.equals("ownPastes")) {
                    paste.setBody(getOwnPasteBody(paste));
                } else { Log.d("listService", "Something went wrong!"); }

                pastes.add(paste);
            }
        } catch (JSONException e) { e.printStackTrace(); }
        catch (IOException e) { e.printStackTrace(); }

        return pastes;
    }

    public static String getTrendingPasteBody(Paste paste) {
        //get the raw body of each paste in the trending list through a seperate call, as it's not included in the normal api call
        StringBuilder html = new StringBuilder();
        try {
            try {
                URLConnection connection = (new URL("https://pastebin.com/raw/"+paste.getKey()+"/"))
                        .openConnection();

                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);
                connection.connect();
                // Read and store the result line by line then return the entire string.
                InputStream in = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                for (String line; (line = reader.readLine()) != null; ) {
                    //add an escaped new line after each line
                    html.append(line+"\n");
                }
                in.close();
            } catch (MalformedURLException e) { e.printStackTrace(); }
        } catch (IOException e) { e.printStackTrace(); }

        return html.toString();
    }

    public static String getOwnPasteBody(Paste paste) {
        String pasteBody = "";
        try {
            OkHttpClient client = new OkHttpClient();

            RequestBody formBody = new FormBody.Builder()
                    .add(Constants.DEV_API_KEY_PARAM, Constants.DEV_API_KEY)
                    .add(Constants.USER_API_KEY_PARAM, Constants.USER_API_KEY)
                    .add(Constants.PASTE_KEY_PARAM, paste.getKey())
                    .add(Constants.API_OPTION, "show_paste")
                    .build();
            Request request = new Request.Builder()
                    .url(Constants.RAW_OUTPUT_URL)
                    .post(formBody)
                    .build();

            pasteBody = client.newCall(request).execute().body().string();
        } catch (IOException e) { e.printStackTrace(); }

        return pasteBody;
    }
}

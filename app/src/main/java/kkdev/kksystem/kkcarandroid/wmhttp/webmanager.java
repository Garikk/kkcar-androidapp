package kkdev.kksystem.kkcarandroid.wmhttp;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import kkdev.kksystem.kkcarandroid.manager.types.ConfigurationInfo;

/**
 * Created by blinov_is on 24.11.2015.
 */
public abstract class WebManager {

    final static String ___TEST_KKCAR_UUID_ = "2e2efd7b-ab83-42fa-9c00-2e45bb4b3ba1";
    final static String WEBMASTER_URL = "http://localhost/";
    final static String WEBMASTER_URL_SERVICE = "phoneapp/request";
    final static String WEBMASTER_URL_FULL = WEBMASTER_URL+WEBMASTER_URL_SERVICE;
    final static int WEBMASTER_MYFORMAT_VERSION=1;
    //PhoneApp
    final static String ACT_PHONEAPP_GET_CARINFO = "1";
    final static String ACT_PHONEAPP_GET_DIAGINFO = "2";
    final static String ACT_PHONEAPP_GET_MEDIA = "3";
    final static String PARAM_PHONEAPP_POST_REQUEST_ACT = "action";
    final static String PARAM_PHONEAPP_POST_REQUEST_MYUUID = "myid";
    final static String PARAM_PHONEAPP_POST_REQUEST_CONFUUID = "confuid";

    public enum WM_RequestTypes
    {
        WM_GetMyState,
        WM_GetDiagnosticInfo,
        WM_GetMediaInfo

    }

    public static ConfigurationInfo GetMyConfInfo() {
        ConfigurationInfo Ret;
        Gson gson = new Gson() ;
        String ConfDataRawJSON;

        ConfDataRawJSON=ExecuteWMRequest(WM_RequestTypes.WM_GetMyState);
        Ret=(ConfigurationInfo)gson.fromJson(ConfDataRawJSON,ConfigurationInfo.class);
        return Ret;
    }


    private static String ExecuteWMRequest(WM_RequestTypes RequestType)
    {
        WMAnswer WAns;
        Gson gson = new Gson() ;
        String RawJSONAnswer;
        RawJSONAnswer=ExecutePostRequest(RequestType);
        //
        WAns=gson.fromJson(RawJSONAnswer,WMAnswer.class);
        //
        if (WAns!=null)
        {
            if (WAns.FormatVersion==WEBMASTER_MYFORMAT_VERSION) {
                if (WAns.AnswerState == '0') {
                    return WAns.JsonData;
                }
            }
        }
        //
        return "";
    }



    private static String ExecutePostRequest(WM_RequestTypes RequestType)
    {
        String Ret="";
        String PostParams="";
        switch (RequestType)
        {
            case WM_GetMyState:
                PostParams=GetPostParameters(RequestConfInfoParams());
                break;
            case WM_GetDiagnosticInfo:
                break;
            case WM_GetMediaInfo:
                break;
        }


        try {
            URL url = new URL(WEBMASTER_URL_FULL);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            try {
                urlConnection.setDoOutput(true);
                urlConnection.setRequestMethod("POST");
                OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());

                out.write(PostParams);
                out.flush();
                out.close();

                InputStream in;
                if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                    in = urlConnection.getInputStream();
                } else {
                    in = urlConnection.getErrorStream();
                }

                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(
                            in, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    in.close();
                    Ret = sb.toString();
                    //Log.e("JSON", response);
                } catch (Exception e) {
                    //Log.e("Buffer Error", "Error converting result " + e.toString());
                }


            } finally {
                urlConnection.disconnect();
            }
        } catch (IOException e) {
        } finally {
        }


        return Ret;


    }


    public static String GetPostParameters(Map<String, String> Data) {
        try {
            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String, String> param : Data.entrySet()) {
                if (postData.length() != 0) postData.append('&');

                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }
            return postData.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }


    private static Map<String, String> RequestConfInfoParams() {
        Map<String,String> Params=new HashMap<>();
        Params.put(PARAM_PHONEAPP_POST_REQUEST_ACT,ACT_PHONEAPP_GET_CARINFO);
        Params.put(PARAM_PHONEAPP_POST_REQUEST_MYUUID,___TEST_KKCAR_UUID_);
        return Params;

    }


}

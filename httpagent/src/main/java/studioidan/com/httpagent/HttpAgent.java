package studioidan.com.httpagent;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by PopApp_laptop on 29/05/16.
 */
public class HttpAgent extends AsyncTask<Void, Void, String> {
    public static final int DEFAULT_TIMEOUT = 1000 * 10;
    public static final String CONTENT_TYPE_APPLICATION_JSON = "application/json";
    public static final String CONTENT_TYPE_URL_ENCODED = "application/x-www-form-urlencoded";

    public final String TAG = getClass().getName();

    private String mUrl;
    private HttpURLConnection mConnection;
    private String mVerb = HTTP.GET;
    private Map<String, String> mQueryParams = new HashMap<>();
    private int mTimeOut = DEFAULT_TIMEOUT;
    private String mBody;
    private boolean mHasBody = false;
    private String mContentType = CONTENT_TYPE_APPLICATION_JSON;
    private HttpAgentResults mCallback;
    private String mErrorMessage = "";
    private int mResponseCode;

    private HttpAgent(String url, String httpVerb) {
        this.mUrl = url;
        this.mVerb = httpVerb;
    }

    public static HttpAgent get(String url) {
        HttpAgent instance = new HttpAgent(url, HTTP.GET);
        return instance;
    }

    public static HttpAgent post(String url) {
        HttpAgent instance = new HttpAgent(url, HTTP.POST);
        return instance;
    }

    public static HttpAgent put(String url) {
        HttpAgent instance = new HttpAgent(url, HTTP.PUT);
        return instance;
    }

    public static HttpAgent delete(String url) {
        HttpAgent instance = new HttpAgent(url, HTTP.DELETE);
        return instance;
    }

    public HttpAgent queryParams(String... queryParams) {
        if (queryParams.length % 2 != 0) {
            Log.e(TAG, "query params must be even number");
            return this;
        }
        for (int i = 0; i < queryParams.length; i += 2) {
            mQueryParams.put(queryParams[i], queryParams[i + 1]);
        }
        return this;
    }

    private void setTimeOut(int timeOutInMillis) {
        this.mTimeOut = timeOutInMillis;
    }

    public HttpAgent contentType(String contentType) {
        this.mContentType = contentType;
        return this;
    }

    public HttpAgent withBody(String body) {
        this.mBody = body;
        this.mHasBody = !U.isEmpty(mBody);
        return this;
    }

    public void go(SuccessCallback callback) {
        this.mCallback = callback;
        this.execute();
    }

    public void goJson(JsonCallback callback) {
        this.mCallback = callback;
        this.execute();
    }

    public void goJsonArray(JsonArrayCallback callback) {
        this.mCallback = callback;
        this.execute();
    }

    public void goString(StringCallback callback) {
        this.mCallback = callback;
        this.execute();
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(Void... params) {
        try {
            String queryParams = createQueryStringForParameters(mQueryParams);
            String urlWithParams = mUrl + queryParams;

            mConnection = (HttpURLConnection) (new URL(urlWithParams)).openConnection();
            mConnection.setRequestMethod(mVerb);
            mConnection.setReadTimeout(mTimeOut);
            mConnection.setDoInput(true);
            mConnection.setDoOutput(mHasBody);
            mConnection.setRequestProperty("Content-Type", mContentType);

            mConnection.connect();
            Log.d(TAG, "sending request to:\n" + urlWithParams + (mHasBody ? ("\nwith body:\n" + mBody) : ""));

            // write body
            if (mHasBody) {
                Writer writer = new BufferedWriter(new OutputStreamWriter(mConnection.getOutputStream(), "UTF-8"));
                writer.write(mBody);
                writer.close();
            }

            //get response code
            mResponseCode = mConnection.getResponseCode();

            // get the results
            BufferedReader reader = new BufferedReader(new InputStreamReader(mResponseCode >= 400 ? mConnection.getErrorStream() : mConnection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
            //check if the server response is an error response
            if (mResponseCode >= 400) {
                mErrorMessage = stringBuilder.toString();
                return null;
            }

            String result = stringBuilder.toString();
            return result;

        } catch (FileNotFoundException e) {
            mErrorMessage = e.getMessage();

        } catch (Exception e) {
            if (e.getMessage() != null) {
                mErrorMessage = e.getMessage();
            }

        } finally {
            if (mConnection != null)
                mConnection.disconnect();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        if (!U.isEmpty(mErrorMessage)) {
            if (mCallback != null) {
                mCallback.errorMessage = mErrorMessage;
                mCallback.notify(null);
            }
            return;
        }
        //there is no error message
        if (!U.isEmpty(result)) {
            Log.d(TAG, "Server Response: " + result);
            if (mCallback != null) {
                //sets string results
                mCallback.stringResults = result;
                mCallback.notify(result.trim());
            }
        }
    }

    public static String createQueryStringForParameters(Map<String, String> parameters) {
        if (parameters == null) {
            return "";
        }

        Uri.Builder builder = new Uri.Builder();
        for (String key : parameters.keySet()) {
            builder.appendQueryParameter(key, parameters.get(key));
        }

        String query = builder.build().getEncodedQuery();
        if (query == null) query = "";
        //add question mark ata the beginning
        if (query.trim().length() > 0)
            query = "?" + query;
        return query;
    }
}

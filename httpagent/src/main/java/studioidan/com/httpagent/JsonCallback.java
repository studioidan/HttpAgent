package studioidan.com.httpagent;

import org.json.JSONObject;

/**
 * Created by PopApp_laptop on 30/05/16.
 */
public abstract class JsonCallback extends HttpAgentResults {
    protected abstract void onDone(boolean success, JSONObject jsonResults);

    @Override
    protected void notify(String results) {
        if (hasError()) {
            onDone(false, null);
            return;
        }

        //there was no error -> lets parse the json object
        try {
            JSONObject jsonObject = new JSONObject(results);
            onDone(!hasError(), jsonObject);
        } catch (Exception ex) {
            //update the error message (probably parse error...)
            errorMessage = ex.getMessage();
            onDone(false, null);
            return;
        }
    }
}

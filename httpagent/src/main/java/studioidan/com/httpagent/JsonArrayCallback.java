package studioidan.com.httpagent;

import org.json.JSONArray;

public abstract class JsonArrayCallback extends HttpAgentResults {

    protected abstract void onDone(boolean success, JSONArray jsonResults);

    @Override
    protected void notify(String results) {
        if (hasError()) {
            onDone(false, null);
            return;
        }
        //there was no error -> lets parse the json array
        try {
            JSONArray jsonArray = new JSONArray(results);
            onDone(!hasError(), jsonArray);
        } catch (Exception ex) {
            //update the error message (probably parse error...)
            errorMessage = ex.getMessage();
            onDone(false, null);
            return;
        }

    }
}


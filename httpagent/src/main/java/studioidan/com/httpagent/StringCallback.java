package studioidan.com.httpagent;

public abstract class StringCallback extends HttpAgentResults {
    protected abstract void onDone(boolean success, String stringResults);

    @Override
    protected void notify(String results) {
        if (hasError()) {
            onDone(false, null);
            return;
        }
        //there was no error
        onDone(!hasError(), results);
    }
}

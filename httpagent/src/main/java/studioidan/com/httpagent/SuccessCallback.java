package studioidan.com.httpagent;

/**
 * Created by PopApp_laptop on 30/05/16.
 */
public abstract class SuccessCallback extends HttpAgentResults {
    protected abstract void onDone(boolean success);

    @Override
    protected void notify(String results) {
        onDone(!hasError());
    }
}

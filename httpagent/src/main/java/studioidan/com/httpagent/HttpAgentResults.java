package studioidan.com.httpagent;

public abstract class HttpAgentResults {
    /*response code*/
    protected int responseCode;
    protected String errorMessage = "";
    protected String stringResults;

    protected String getErrorMessage() {
        return errorMessage;
    }

    protected boolean hasError() {
        return !U.isEmpty(getErrorMessage());
    }

    protected String getStringResults() {
        return stringResults;
    }

    protected int getResponseCode() {
        return responseCode;
    }

    protected abstract void notify(String results);
}

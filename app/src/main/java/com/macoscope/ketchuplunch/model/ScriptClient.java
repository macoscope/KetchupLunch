package com.macoscope.ketchuplunch.model;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.script.model.ExecutionRequest;
import com.google.api.services.script.model.Operation;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import timber.log.Timber;

public class ScriptClient {

    private com.google.api.services.script.Script mService = null;
    private String projectKey;


    public ScriptClient(GoogleAccountCredential credential, String projectKey) {
        this.projectKey = projectKey;
        HttpTransport transport = AndroidHttp.newCompatibleTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        mService = new com.google.api.services.script.Script.Builder(
                transport, jsonFactory, setHttpTimeout(credential))
                .setApplicationName("Google Apps Script Execution API Android Quickstart")
                .build();
    }

    public <T> T getDataFromApi(String function) throws IOException, GoogleAuthException {
        ExecutionRequest request = new ExecutionRequest().setFunction(function);
        Operation op = mService.scripts().run(projectKey, request).execute();

        if (op.getError() != null) {
            logScriptError(op);
            return null;
        }

        if (op.getResponse() != null &&
                op.getResponse().get("result") != null) {
            return  (T) (op.getResponse().get("result"));
        }

        return null;
    }

    private void logScriptError(Operation op) {
        if (op.getError() == null) {
            return;
        }

        Map<String, Object> detail = op.getError().getDetails().get(0);
        List<Map<String, Object>> stacktrace =
                (List<Map<String, Object>>) detail.get("scriptStackTraceElements");

        StringBuilder sb =
                new StringBuilder("\nScript error message: ");
        sb.append(detail.get("errorMessage"));

        if (stacktrace != null) {
            // There may not be a stacktrace if the script didn't start
            // executing.
            sb.append("\nScript error stacktrace:");
            for (Map<String, Object> elem : stacktrace) {
                sb.append("\n  ");
                sb.append(elem.get("function"));
                sb.append(":");
                sb.append(elem.get("lineNumber"));
            }
        }
        sb.append("\n");
        Timber.e(sb.toString());
    }

    private static HttpRequestInitializer setHttpTimeout(
            final HttpRequestInitializer requestInitializer) {
        return new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest httpRequest)
                    throws java.io.IOException {
                requestInitializer.initialize(httpRequest);
                // This allows the API to call (and avoid timing out on)
                // functions that take up to 6 minutes to complete (the maximum
                // allowed script run time), plus a little overhead.
                httpRequest.setReadTimeout(380000);
            }
        };
    }
}

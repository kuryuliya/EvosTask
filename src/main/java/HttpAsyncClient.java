import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.util.EntityUtils;


public class HttpAsyncClient {

    private long start;
    private long responseTime;
    private CloseableHttpAsyncClient httpClient = HttpAsyncClients.createDefault();
    private HttpResponse response;

    HttpAsyncClient() {
        httpClient.start();
    }

    private FutureCallback<HttpResponse> futureCallback = new FutureCallback<HttpResponse>() {
        @Override
        public void completed(HttpResponse httpResponse) {
            System.out.println("\nRequest COMPLETED");
            responseTime = System.currentTimeMillis() - start;
            System.out.println("\nResponse time: " + responseTime + " ms.");
        }

        @Override
        public void failed(Exception e) {
            System.out.println("\nRequest Failed Due to : " + e.getMessage());
            responseTime = System.currentTimeMillis() - start;
            System.out.println("\nResponse time: " + responseTime + " ms.");
        }

        @Override
        public void cancelled() {
            System.out.println("\nRequest CANCELLED");
        }
    };

    public HttpResponse doGetRequest(String url) {
        start = System.currentTimeMillis();
        Future<HttpResponse> execute = httpClient.execute(new HttpGet(url), futureCallback);

        try {
            response = execute.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return response;
    }


    public static String getBody(HttpEntity entity) {
        try {
            return EntityUtils.toString(entity, "UTF-8");
        } catch (IOException e) {
            return "";
        }
    }

    public long getResponseTime() {
        return responseTime;
    }
}






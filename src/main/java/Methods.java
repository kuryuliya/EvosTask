import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;
import org.apache.http.HttpResponse;



import java.io.IOException;

public class Methods {
    private HttpAsyncClient client = new HttpAsyncClient();
    private ObjectMapper objectMapper = new ObjectMapper();
    private static String url = getUrl();


    private String api_key = "api_key=MUcjo3x6iWRA5sRPQZFzkEAFTXnT2qKPELpO8lxh";

    public HttpResponse createRequest(String params) {
        url += api_key + params;
        System.out.println(url);
        return client.doGetRequest(url);
    }
    private static String getUrl() {
        String CONFIG_FILE = "application.yml";
        Properties properties = new Properties();
        InputStream is = Methods.class.getClassLoader().getResourceAsStream(CONFIG_FILE);
        try {
            properties.load(Objects.requireNonNull(is));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties.getProperty("baseUri");
    }

    public int checkCountsOfResult(HttpResponse response){

        String body = HttpAsyncClient.getBody(response.getEntity());
        final ObjectNode node = readValueJSON(body);
        String count = node.get("result").get("search_result").get("count").asText();
        System.out.println(count);
        return Integer.parseInt(count);
    }

    private ObjectNode readValueJSON(String body) {
        try {
            return objectMapper.readValue(body, ObjectNode.class);
        } catch (IOException e) {
            return null;
        }
    }
}




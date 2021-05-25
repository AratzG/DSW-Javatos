package gateway;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.HashMap;

public class GithubGateway
{
    private final String url = "https://api.github.com";
    private Client client;
    private WebTarget webTarget;

    public GithubGateway(String accessPoint) {
        client = ClientBuilder.newClient();
        webTarget = client.target(String.format(this.url + "/" + accessPoint));
    }

    public Response makeGetRequest(String query) throws Exception {
        WebTarget getRequestController = this.webTarget.path(query);
        Invocation.Builder invocationBuilder = getRequestController.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();

        if(response.getStatus() == Status.OK.getStatusCode()) {
            return response;
        }
        else {
            throw new Exception("BadAss error");
        }
    }

    public static void main(String[] args) {

        try {
            GithubGateway g1 = new GithubGateway("users");
            Response res1 = g1.makeGetRequest("");

            //Print the response, as it is
            System.out.println("Print the response, as it is");
            System.out.println(res1);

            //Print the response as String
            //System.out.println("Print the response as String");
            //System.out.println(res1.readEntity(String.class));

            //Parse the response as JsonArray
            System.out.println("Parse response as JSonArray");
            JSONArray array = res1.readEntity(JSONArray.class);
            System.out.println(array.size());

            HashMap<String, String> object1 = (HashMap<String, String>)array.get(0);
            System.out.println(object1);
            System.out.println(object1.get("login"));

        } catch (Exception e) {
            System.out.println("Catched exception: " + e.getMessage());
        }
    }
}

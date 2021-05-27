package gateway;

import ld.Usuario;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
            throw new Exception ("BadAss error");
        }
    }
}

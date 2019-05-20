package ZD;

import java.io.Serializable;

public class Response implements Serializable {

    public String response;
    public RequestType requestType;

    public Response(String response, RequestType requestType) {
        this.response = response;
        this.requestType = requestType;
    }

    @Override
    public String toString(){
        return "Response:{ Type: " + requestType + "; Text: " + response +"}";
    }
}

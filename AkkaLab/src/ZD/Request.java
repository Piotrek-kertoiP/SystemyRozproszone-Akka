package ZD;

import java.io.Serializable;

public class Request implements Serializable {
    public RequestType type;
    public String title;

    public Request(RequestType type, String title) {
        this.type = type;
        this.title = title;
    }

    @Override
    public String toString(){
        return "Request:{ Type: " + type + "; Title: " + title +"}";
    }
}

package Z2;

import java.io.Serializable;

public class EchoResult implements Serializable {

    public final String result;

    public EchoResult(String result) {
        this.result = result;
    }
}

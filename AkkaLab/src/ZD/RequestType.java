package ZD;

public enum RequestType {
    SUBSCRIBE("s"), FIND("f"), ORDER("o");

    private final String s;

    RequestType(String s) {
        this.s = s;
    }
}

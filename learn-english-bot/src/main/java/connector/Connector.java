package connector;

public interface Connector {
    void start();
    default void postRequest() {
        System.out.println("Post request!");
    }
    default void getRequest() {
        System.out.println("Get request!");
    }
}

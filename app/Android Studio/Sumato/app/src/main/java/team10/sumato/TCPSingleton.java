package team10.sumato;

public class TCPSingleton {

    private static TCPClient client;
    private static TCPSingleton instance;

    private TCPSingleton() {
    }

    /*
     * Return the singleton instance. Used to access the client in any Activity.
     */

    public static TCPSingleton getInstance() {
        return instance;
    }

    /*
     * Set up the Singleton and Client with the host and port. To be called only once.
     */

    public static void initSingleton(final String host, final String port) {
        if(instance==null) {
            instance = new TCPSingleton();
        }
        client = new TCPClient(host, Integer.parseInt(port));
    }


    /*
     * Returns the client instance. Use for sending and receiving messages on the TCP client.
     */
    public TCPClient getClient() {
        return client;
    }
}

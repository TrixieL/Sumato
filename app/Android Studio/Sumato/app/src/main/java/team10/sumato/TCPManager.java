package team10.sumato;

public class TCPManager {

    private static TCPClient client;
    private static TCPManager instance;

    private TCPManager() {
    }

    /*
     * Return the singleton instance. Used to access the client in any Activity.
     */

    public static TCPManager getInstance() {
        if(instance==null){
            instance = new TCPManager();
            client = new TCPClient("192.168.42.1", 9999);
        }
        return instance;
    }

    /*
     * Returns the client instance. Use for sending and receiving messages on the TCP client.
     */
    public TCPClient getClient() {
        if(!client.isConnected()){
            client.start();
        }
        return client;
    }
}

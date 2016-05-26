package team10.sumato;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPClient {

    private InetAddress host;
    private int port;

    private Socket client;

    private Thread listeningThread;
    private Thread clientThread;

    private boolean running = false;
    private PrintWriter writer;
    private BufferedReader reader;

    /*
     * Constructor: initializes the port and host.
     */
    public TCPClient(String host, int port) {
        try {
            this.host = InetAddress.getByName(host);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        this.port = port;
    }

    /*
     * Stop the client and close the socket.
     */
    public void stopClient() {
        running = false;
        writer = null;
        reader = null;
        if (client.isConnected()) {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isConnected(){
        if(running){
            return client.isConnected();
        }
        return false;
    }

    /*
     * Send a message on the socket.
     */
    public void send(String message) {
        if (writer != null && !writer.checkError()) {
            writer.println(message);
            writer.flush();
        }
    }
    /*
     * Initialize the socket and begin listening for messages.
     */
    public void start() {
        if(client==null) {
            try {
                // initialize the socket on a separate thread since Android does not allow it on the Main thread
                // then retrieve the created socket to continue working with it
                ClientThread runnableClient = new ClientThread();
                clientThread = new Thread(runnableClient);
                clientThread.start();
                clientThread.join(); //blocks until thread is finished
                client = runnableClient.getSocket();

                try {
                    writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);
                    reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    running = true;

                    // start listening for messages on a separate thread to avoid blocking the sending thread
                    listeningThread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("tcp", "Listening on " + client.getInetAddress() + ":" + client.getPort());
                            String message;
                            while (running) {
                                try {
                                    message = reader.readLine();
                                    if (message != null) {
                                        Log.d("tcp", message);
                                        //TODO: deal with the received message here
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                    listeningThread.start();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    class ClientThread implements Runnable {
        Socket threadClient;

        @Override
        public void run(){
            try {
                threadClient = new Socket(host, port);

            } catch (IOException e) {
                e.printStackTrace();

            }
        }

        public Socket getSocket() {
            return threadClient;
        }
    }


}


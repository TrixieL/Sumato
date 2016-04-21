using UnityEngine;
using UnityEngine.UI;
using System.Net.Sockets;
using System.IO;
using System.Threading;
using System.Collections.Generic;
using System;

public class TCPConnection : MonoBehaviour
{
	public string server;
	public int port;

	TcpClient client;
	NetworkStream stream;
	StreamWriter writer;
	StreamReader reader;

	bool listening;
	Thread listeningThread;

	Queue<string> pendingMessages = new Queue <string> ();

	/*
	 * Begins a new TCP connection. Assign the server and port in the Unity UI.
	 */
	public void connectClient ()
	{
		client = new TcpClient (server, port);
		writer = new StreamWriter (client.GetStream());
		reader = new StreamReader (client.GetStream());
	}

	/*
	 * Start the listening thread.  
	 */
	public void startListening ()
	{
		listeningThread = new Thread (read);
		listeningThread.Start ();
		listening = true;
	}

	/*
	 * Send a string through the client.  
	*/
	public void send (string message)
	{
		writer.WriteLine (message);
		writer.Flush ();
	}

	/*
	 * Close the connection and the listening thread.  
	 */
	public void close ()
	{
		if (client != null) {
			client.GetStream ().Close ();
			client.Close ();
		}
		listening = false;
		if (listeningThread.IsAlive) {
			listeningThread.Abort ();
		}
	}

	/*
	 * Read data from the stream. Automatically started when the listening thread is started. 
	 */
	void read ()
	{
		while (listening) {
			string msg = reader.ReadLine ();		
			if (!String.IsNullOrEmpty (msg)) {
				lock (pendingMessages) {
					pendingMessages.Enqueue (msg);
				}
			}
		}
	}

	/*
	 * Unity method that runs once every frame.
	 */
	public void Update ()
	{
		//check for received data every frame
		lock (pendingMessages) {
			if (pendingMessages.Count > 0) {
				string msg = pendingMessages.Dequeue ();
				//process the message here or in a different method depending on what was received
			}
		}

	}
}

using UnityEngine;
using System.Collections;
using System.Net.Sockets;
using System.IO;

public class TCPConnection : MonoBehaviour {

	TcpClient client;

	public string server;
	public int port;

	public void Connect() {

		client = new TcpClient (server, port);

	}

	public void Send(string message) {

		using (StreamWriter streamWriter = new StreamWriter (client.GetStream ())) {
			streamWriter.WriteLine (message);
			streamWriter.Flush ();
		}

	}

}

package conexoes;

import java.net.Socket;

import interfaces.Callback;

public class OuvintesServidor {
	Socket ouvinte;
	Callback feed;

	public OuvintesServidor(Socket ouvinte, Callback feed) {
		this.ouvinte = ouvinte;
		this.feed = feed;
	}
}

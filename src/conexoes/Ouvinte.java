package conexoes;

import java.io.IOException;
import java.net.Socket;

import interfaces.CallbackOuvinte;

public class Ouvinte implements Runnable {
	Socket ouvinte;
	CallbackOuvinte feed;
	boolean down;

	public Ouvinte(Socket ouvinte, CallbackOuvinte feed) {
		this.ouvinte = ouvinte;
		this.feed = feed;
		this.down = true;
	}

	public void loopOuvir() throws IOException {
		byte[] buffer = new byte[1024];
		int estadoBruto = 0;
		while (down) {
			if (ouvinte.isConnected() && estadoBruto != -1) {
				if ((estadoBruto = ouvinte.getInputStream().read(buffer)) != 0) {
					String msg = new String(buffer, "UTF-8");
					System.out.println(msg);
					feed.novaMensagem(msg);
					buffer = new byte[1024];
				} else {
					continue;
				}
			} else {
				down = false;
				System.out.println(
						"O computador " + ouvinte.getInetAddress().getHostAddress() + " desconectou. Encerrando porta");
			}
		}
	}

	@Override
	public void run() {
		try {
			loopOuvir();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

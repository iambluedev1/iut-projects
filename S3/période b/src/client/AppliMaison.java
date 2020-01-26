package client;

public class AppliMaison {

	private static final Integer PORT_RESERVER = 2500;
	
	public static void main(String[] args) {
		new Thread(new Client("localhost", PORT_RESERVER)).start();
	}

}

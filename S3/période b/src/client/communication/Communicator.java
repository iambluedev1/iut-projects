package client.communication;

public interface Communicator {

	void onMessage(String msg);
	
	void onConnect();
	
	void onDisconnect();
	
	void onError(String error, Exception e);
	
	void send(String msg);
	
	void end();
	
	void exec();
	
}

package utils.net;

import java.util.List;
import java.util.Map;

public interface WebConnection {

	public void connect();
	public void disconnect();
	public StringBuilder getHTML();
	public byte[] getDocument();
	public Map<String, List<String>> getHeader();
	public void sendRequest(String type, String parameters, SessionHandler session);
}

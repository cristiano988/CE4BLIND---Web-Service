package utils.net;

import java.net.MalformedURLException;
import java.net.URL;

public abstract class AbstractConnection implements WebConnection{

	protected URL url = null;
	protected String _url = null;
	
	public AbstractConnection(String url) throws MalformedURLException{
		this(new URL(url));
	}
	
	public AbstractConnection(URL url){
		this._url = url.toExternalForm();
		this.url = url;
	}
}

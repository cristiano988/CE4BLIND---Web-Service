package utils.net;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SessionHandler {
	
	List<String> cookies = null;
	private boolean isLogged = false;
	private String expires = "";
	
	public SessionHandler(){
		
	}
	
	public SessionHandler(List<String> cookies){
        this.cookies = cookies;
    }
	
	public boolean isLogged(){
		return this.isLogged;
	}
	
	public void setIsLogged(boolean logged){
		this.isLogged = logged;
	}

	public String build(){

		if(cookies == null)
            return "";

        HashMap<String, String> _cookies = new HashMap<>();
        for(String field : cookies)
        {
            String[] _field = field.split(";");
            int n = _field.length;

            for(int i = 0 ; i < n ; i++)
                _cookies.put(_field[i].split("=")[0], _field[i].split("=")[1]);
        }

        String cookie = "";
        for(String _field : _cookies.keySet())
            cookie += _field + "=" + _cookies.get(_field) + ";";

        return cookie;
	}

	public void save(Map<String, List<String>> header) {

		if(header == null)
            return;

        for(String field : header.keySet()){
            if(field != null && field.equalsIgnoreCase("Set-Cookie")){
            	this.cookies = header.get(field);
            	//Falta aqui por o codigo para verificar quando a sessão expira
            	
            }
        }

	}
}

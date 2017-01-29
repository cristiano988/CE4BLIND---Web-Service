package utils.agents;

import utils.net.WebConnection;

public abstract class WebAgent implements Agent{

	protected WebConnection connection;
    public abstract boolean login();
    public abstract boolean isLogged();
}

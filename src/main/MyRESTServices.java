package main;

import java.io.File;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("infarmed")
public class MyRESTServices extends ResourceConfig{
	
	public static String separator = File.separator;
	
	public static final String ASSETS_PATH = "C:" + separator + "Users" + separator + "crist" + separator
			+ "servers" + separator + "glassfish4" + separator + "glassfish" + separator + "domains" + separator + "domain1" + separator + "assets";
	
	public MyRESTServices(){
		packages("com.pegaxchange.java.web.rest");
	}

}

package main;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

import utils.agents.WebAgent;
import utils.models.Medicine;
import utils.proxys.Infarmed;
import utils.response.InfarmedResponseParser;
import utils.response.ResponseParser;


@Path("med")
public class Controller {
	
	@GET
	@Path("barcode/{bc}")
	@Produces("text/html")
	public String getText(@PathParam("bc") String barcode){
		
		
		WebAgent agent = new Infarmed(barcode);
		
		Medicine med = null;
		
		if(!agent.exists() || (med = agent.getMedicine()) == null)
			throw new WebApplicationException(404);
		
		ResponseParser parser = new InfarmedResponseParser(med);
		
		return parser.parse();
	}

}

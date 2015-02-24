package in.ac.iitb.cse.wikiproject;

import in.ac.iitb.cse.wikiminer.Wikisaurus;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.wikipedia.miner.model.Label.Sense;

@Path("/spotter")
public class Spotter {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEntities(@QueryParam("spot") String spot)
			throws Exception {
		Wikisaurus wiki = new Wikisaurus();
		Sense[] senses = wiki.getSenses(spot);
		return Response.status(200).entity(senses).build();
	}

}

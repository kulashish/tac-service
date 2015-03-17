package in.ac.iitb.cse.wikiproject;

import in.ac.iitb.cse.wikiminer.Wikisaurus;

import java.io.File;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.wikipedia.miner.model.Label;
import org.wikipedia.miner.model.Label.Sense;
import org.wikipedia.miner.model.Article;
import org.wikipedia.miner.model.Wikipedia;
import org.wikipedia.miner.util.WikipediaConfiguration;

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
	
	public static void main(String[] args) throws Exception {
/*		Spotter spotter = new Spotter();
		try {
		System.out.println(spotter.getEntities(args[0]));
		}catch(Exception e) {
			e.printStackTrace();
		}
*/
// WikipediaConfiguration conf = new WikipediaConfiguration(new File("/mnt/bag3/ram/WikipediaMiner/wikipedia-miner-1.2.0/configs/wm-config.xml")) ;
	     WikipediaConfiguration conf = new WikipediaConfiguration(new File("/home/ashish/wikipedia-miner-1.2.0/configs/wikiminer-ak.xml")) ;
        Wikipedia wikipedia = new Wikipedia(conf, false) ;

        Article article = wikipedia.getArticleByTitle("Wikipedia") ;
	System.out.println("Received article...");
        System.out.println(article) ;
	Label label = wikipedia.getLabel("Wikipedia");
	System.out.println("Received label...");
	System.out.println(label);
	if(label!=null) {
		Sense[] senses = label.getSenses();
		System.out.println("Received senses..."+senses);
		System.out.println("Number of senses: "+senses.length);
		if(senses.length == 0)
			System.out.println(senses[0].getTitle());
		else
			for(Sense s:senses) {
				System.out.println(s.getTitle());
			}
	}

        wikipedia.close() ;
	}
}

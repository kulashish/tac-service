package in.ac.iitb.cse.wikiproject;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class SpotterServiceListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		String val = event.getServletContext().getInitParameter(
				"wikiminer_config");
		SpotterServiceProperties.getInstance().setWikiMinerConfPath(val);
	}

}

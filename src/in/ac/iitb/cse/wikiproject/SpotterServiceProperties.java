package in.ac.iitb.cse.wikiproject;

public class SpotterServiceProperties {

	private static SpotterServiceProperties instance;
	private String wikiMinerConfPath;

	static {
		instance = new SpotterServiceProperties();
	}

	public static SpotterServiceProperties getInstance() {
		return instance;
	}

	private SpotterServiceProperties() {

	}

	public String getWikiMinerConfPath() {
		return wikiMinerConfPath;
	}

	public void setWikiMinerConfPath(String wikiMinerConfPath) {
		this.wikiMinerConfPath = wikiMinerConfPath;
	}

}

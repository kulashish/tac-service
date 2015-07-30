package in.ac.iitb.cse.wikiminer;

import in.ac.iitb.cse.wikiproject.SpotterServiceProperties;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;

import org.wikipedia.miner.model.Article;
import org.wikipedia.miner.model.Label;
import org.wikipedia.miner.model.Page;
import org.wikipedia.miner.model.Page.PageType;
import org.wikipedia.miner.model.Wikipedia;
import org.wikipedia.miner.util.PageIterator;
import org.wikipedia.miner.util.WikipediaConfiguration;

public class Wikisaurus {

	static WikipediaConfiguration conf;
	public static Wikipedia _wikipedia = null;
	static String wikiMinerConfigFile = "/home/ashish/wikipedia-miner-1.2.0/configs/wikiminer-ak.xml";

	// static String
	// wikiMinerConfigFile="/mnt/bag3/ram/WikipediaMiner/wikipedia-miner-1.2.0/configs/wm-config.xml";

	public Wikisaurus() {
		this(SpotterServiceProperties.getInstance().getWikiMinerConfPath());
	}

	public Wikisaurus(String wikiminerConfig) {
		try {
			if (null != wikiminerConfig)
				wikiMinerConfigFile = wikiminerConfig;
			conf = new WikipediaConfiguration(new File(wikiMinerConfigFile));
			conf.setDefaultTextProcessor(new TextFolder());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if (_wikipedia == null) {
			_wikipedia = new Wikipedia(conf, false);
		}
	}

	public Label.Sense[] run(String term) throws Exception {
		Label.Sense[] senses = getSenses(term);
		/*
		 * if (senses == null) { System.out.println("I have no idea what '" +
		 * term + "' is") ; } else { if (senses.length == 0) {
		 * System.out.println("m here in legth 0"); displaySense(senses[0]) ; }
		 * else { System.out.println("'" + term +
		 * "' could mean several things:") ; for (int i=0 ; i<senses.length ;
		 * i++) { System.out.println(" - [" + (i+1) + "] " +
		 * senses[i].getTitle()+"  "+senses[i].getPriorProbability()) ; } } }
		 */
		return senses;
	}

	public Label.Sense[] getSenses(String term) throws Exception {
		Label label = _wikipedia.getLabel(term);
		if (!label.exists()) {
			return null;
		} else {
			return label.getSenses();
		}
	}

	public static Label.Sense[] getAllSenses(String term) throws Exception {
		Label label = _wikipedia.getLabel(term);
		if (!label.exists()) {
			return null;
		} else {
			return label.getSenses();
		}
	}

	public String getAnchor(String page_title) {
		Article article = _wikipedia.getArticleByTitle(page_title);
		if (article == null) {
			// System.out.println("no article for "+page_title);
			return null;
		}
		Article.Label[] label = article.getLabels();
		String labelString = "";
		// System.out.println("labels ");
		if (label == null) {
			return null;
		}
		for (int i = 0; i < label.length; i++) {
			// System.out.println(label[i].getText());
			labelString += label[i].getText() + " ";
		}

		return labelString;
	}

	public static String getAnchorText(String page_title) {

		Article article = _wikipedia.getArticleByTitle(page_title);
		if (article == null) {
			// System.out.println("no article for "+page_title);
			return null;
		}
		Article.Label[] label = article.getLabels();
		String labelString = "";
		// System.out.println("labels ");
		if (label == null) {
			return null;
		}
		for (int i = 0; i < label.length; i++) {
			// System.out.println(label[i].getText());
			labelString += label[i].getText() + " ";
		}
		return labelString;
	}

	public static Article[] getInLinks(String page_title) {
		Article article = _wikipedia.getArticleByTitle(page_title);
		if (article == null) {
			return null;
		}
		return (article.getLinksIn());
	}

	protected void displaySense(Label.Sense sense) throws Exception {
		System.out.println("==" + sense.getTitle() + "==");
	}

	public static void closeWikipedia() {
		if (null != _wikipedia)
			_wikipedia.close();
	}

	public static void main(String args[]) throws Exception {

		Wikisaurus thesaurus = new Wikisaurus(
				"/home/kanika/workspace/wikipedia-miner-1.2.0/wikipedia-config.xml");
		String mention;
		String title = "";
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		PageType pt = PageType.valueOf("disambiguation");
		PageIterator itr = _wikipedia.getPageIterator(pt);
		while (itr.hasNext()) {
			Page p = itr.next();
			title += p.getTitle();
			title += "\n";

		}
		File file = new File("/mnt/bag1/kanika/disambPagesList.txt");
		if (!file.exists()) {
			file.createNewFile();
		}

		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(title);
		bw.close();

		System.out.println("Done");
		System.out.println(title);
		System.out.println("Done");
		thesaurus._wikipedia.close();
	}
}

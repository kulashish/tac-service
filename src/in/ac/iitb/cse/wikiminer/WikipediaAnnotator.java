package in.ac.iitb.cse.wikiminer;

import java.io.Console;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.wikipedia.miner.annotation.Disambiguator;
import org.wikipedia.miner.annotation.Topic;
import org.wikipedia.miner.annotation.TopicDetector;
import org.wikipedia.miner.annotation.preprocessing.PreprocessedDocument;
import org.wikipedia.miner.annotation.preprocessing.WikiPreprocessor;
import org.wikipedia.miner.annotation.tagging.DocumentTagger.RepeatMode;
import org.wikipedia.miner.annotation.tagging.WikiTagger;
import org.wikipedia.miner.annotation.weighting.LinkDetector;

public class WikipediaAnnotator {

	private Wikisaurus wikisaurus;
	private WikiPreprocessor wikiPre;
	private Disambiguator wikiDis;
	private TopicDetector wikiTop;
	private LinkDetector wikiLink;
	private WikiTagger tagger;

	public WikipediaAnnotator(Wikisaurus wikisaurus) throws IOException,
			Exception {
		this.wikisaurus = wikisaurus;
		wikiPre = new WikiPreprocessor(wikisaurus._wikipedia);
		wikiDis = new Disambiguator(wikisaurus._wikipedia);
		wikiTop = new TopicDetector(wikisaurus._wikipedia, wikiDis, true, false);
		wikiLink = new LinkDetector(wikisaurus._wikipedia);
		tagger = new WikiTagger();
	}

	public void annotate(String text) throws Exception {
		PreprocessedDocument preDoc = wikiPre.preprocess(text);
		Collection<Topic> allTopics = wikiTop.getTopics(preDoc, null);
		ArrayList<Topic> bestTopics = wikiLink.getBestTopics(allTopics, 0.1);
		String newMarkup = tagger.tag(preDoc, allTopics, RepeatMode.ALL);
		System.out.println("\nAugmented markup:\n" + newMarkup + "\n");
	}

	public static void main(String[] args) {
		Wikisaurus wiki = new Wikisaurus(null);
		try {
			WikipediaAnnotator annotator = new WikipediaAnnotator(wiki);
			Console console = System.console();
			while (true) {
				String text = console.readLine("Please enter : ");
				if (text.equalsIgnoreCase("exit"))
					break;
				annotator.annotate(text);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

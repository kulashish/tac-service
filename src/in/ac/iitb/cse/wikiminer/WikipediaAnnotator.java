package in.ac.iitb.cse.wikiminer;

import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

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
	private Collection<Topic> allTopics;
	private String newMarkup;

	class Annotate extends Thread {

		private PreprocessedDocument text;
		private String markup;
		private Collection<Topic> topics;

		public Annotate(PreprocessedDocument doc) {
			this.text = doc;
		}

		@Override
		public void run() {
			try {
				topics = wikiTop.getTopics(text, null);
				// ArrayList<Topic> bestTopics = wikiLink.getBestTopics(topics,
				// 0.1);
				markup = new WikiTagger().tag(text, topics, RepeatMode.ALL);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public WikipediaAnnotator(Wikisaurus wikisaurus) throws IOException,
			Exception {
		this.wikisaurus = wikisaurus;
		wikiPre = new WikiPreprocessor(wikisaurus._wikipedia);
		wikiDis = new Disambiguator(wikisaurus._wikipedia);
		wikiDis.loadClassifier(new File(
				"/home/ashish/wikipedia-miner-1.2.0/models/annotate/disambig_en_In.model"));
		wikiTop = new TopicDetector(wikisaurus._wikipedia, wikiDis, true, false);
		wikiLink = new LinkDetector(wikisaurus._wikipedia);
		wikiLink.loadClassifier(new File(
				"/home/ashish/wikipedia-miner-1.2.0/models/annotate/detect_en_In.model"));
		// tagger = new WikiTagger();

		allTopics = new Vector<Topic>();
		newMarkup = "";
	}

	public List<String> getMentions(String text) throws Exception {
		return new WikiMarkup().extractMentions(annotate(text));
	}

	public String annotate(String text) throws Exception {
		// PreprocessedDocument preDoc = wikiPre.preprocess(text);
		ArrayList<Annotate> annotateThreads = new ArrayList<Annotate>();

		String[] preText = text.split("\\.");
		for (String s : preText) {
			Annotate t = new Annotate(wikiPre.preprocess(s));
			t.start();
			annotateThreads.add(t);
		}

		StringBuilder builder = new StringBuilder();
		for (Annotate t : annotateThreads) {
			t.join();
			builder.append(t.markup);
		}
		newMarkup = builder.toString();
		return newMarkup;
	}

	public static void main(String[] args) {
		Wikisaurus wiki = new Wikisaurus(null);
		try {
			WikipediaAnnotator annotator = new WikipediaAnnotator(wiki);
			Console console = System.console();
			String marked = null;
			WikiMarkup markup = new WikiMarkup();
			List<String> mentions = null;
			while (true) {
				String text = console.readLine("Please enter : ");
				if (text.equalsIgnoreCase("exit"))
					break;
				marked = annotator.annotate(text);
				mentions = markup.extractMentions(marked);
				for (String m : mentions)
					System.out.println(m);
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

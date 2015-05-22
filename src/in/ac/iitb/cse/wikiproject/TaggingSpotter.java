package in.ac.iitb.cse.wikiproject;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class TaggingSpotter {

	private static final String MODEL_PATH = "D:\\Personal\\stanford-postagger-2015-04-20\\stanford-postagger-2015-04-20\\models\\english-bidirectional-distsim.tagger";
	private String text;
	private MaxentTagger tagger;
	private String taggedText;

	public TaggingSpotter(String t) {
		this();
		text = t;
	}

	public TaggingSpotter() {
		tagger = new MaxentTagger(MODEL_PATH);
	}

	public String tag() {
		taggedText = tagger.tagString(text);
		return taggedText;
	}

	public static void main(String[] args) {
		String text = "Barack Obama is the president of USA";
		TaggingSpotter spotter = new TaggingSpotter(text);
		System.out.println(spotter.tag());
	}

}

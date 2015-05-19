package in.ac.iitb.cse.wikiminer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WikiMarkup {

	private static Pattern markupPattern = Pattern.compile("\\[\\[(.*?)\\]\\]",
			Pattern.DOTALL | Pattern.MULTILINE);

	public WikiMarkup() {

	}

	public List<String> extractMentions(String markup) {
		Matcher matcher = markupPattern.matcher(markup);
		String annotation = null;
		List<String> mentionsList = new ArrayList<String>();
		while (matcher.find()) {
			annotation = matcher.group(1);
			String[] arr = annotation.split("\\|");
			mentionsList.add(arr.length > 1 ? arr[1] : arr[0]);
		}
		return mentionsList;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

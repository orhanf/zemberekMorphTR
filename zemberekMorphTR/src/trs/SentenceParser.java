package trs;

import java.io.IOException;
import java.util.List;

import zemberek.morphology.ambiguity.Z3MarkovModelDisambiguator;
import zemberek.morphology.apps.TurkishMorphParser;
import zemberek.morphology.apps.TurkishSentenceParser;
import zemberek.morphology.parser.MorphParse;
import zemberek.morphology.parser.SentenceMorphParse;
import zemberek.morphology.parser.MorphParse.SuffixData;

public class SentenceParser {

	private WordParser wordParser;
	private TurkishSentenceParser sentenceParser;
	private boolean isShortWords = false;

	public SentenceParser(boolean isDisambiguate, boolean isShortWords) throws IOException {

		this.wordParser   = new WordParser(TurkishMorphParser.createWithDefaults());
		this.isShortWords = isShortWords;

		if(isDisambiguate){
			this.sentenceParser = new TurkishSentenceParser(
					TurkishMorphParser.createWithDefaults(), // morph parser
					new Z3MarkovModelDisambiguator()		 // disambiguator
					);
		}
	}

	public String parseSentenceGetFirstDisambiguate(String sentence) {

		String result = "";
		try{
			SentenceMorphParse sentenceParse = sentenceParser.parse(sentence);
			sentenceParser.disambiguate(sentenceParse);
			int i;
			String[] words = sentence.split(" ");  
			for (i=0; i < sentenceParse.size(); ++i) {
				SentenceMorphParse.Entry entry = sentenceParse.getEntry(i);
				result += getStemAndSuffixesAsString(entry.parses.get(0),words[i]);
			}
		}catch (Exception e){
			result = parseSentenceGetFirst(sentence);
		}
		return result;
	}

	public String parseSentenceGetFirst(String sentence) {

		// String[] words = sentence.toLowerCase().split(" ");
		String[] words = sentence.split(" ");  
		String   result = "";

		for (String word : words){
			try {
				result += getStemAndSuffixesAsString(wordParser.parse0(word),word);
			} catch (Exception e) {
				result += word;
				result += " ";
			}
		}  
		return result;
	}

	public String getStemAndSuffixesAsString(MorphParse parse, String orjWord){
		
		String result = getRootandFixUpperCase(parse.root,orjWord);
		List<SuffixData> suffixList = parse.getSuffixDataList();

		for(SuffixData sfx : suffixList){
			String tt = sfx.toString();
			if(this.isShortWords){
				if (tt.contains(":"))
					result += " <" + tt + ">";

			}else{
				result += " <" + tt + ">";
			}
		}
		result += " ";
		return result;
	}
	
	public String getRootandFixUpperCase(String parsedRoot,String orjWord){
		String root = "";
		int i;
		for(i=0 ; i<parsedRoot.length() ; ++i){
			if(Character.isUpperCase(orjWord.charAt(i)) & Character.isLowerCase(parsedRoot.charAt(i)) ){
				root += Character.toUpperCase(parsedRoot.charAt(i));
			}else{
				root += parsedRoot.charAt(i);
			}
		}
		
		return root;
		
	}

}

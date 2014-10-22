package trs;

import java.util.List;

import trs.WordParser;
import zemberek.morphology.apps.TurkishMorphParser;
import zemberek.morphology.parser.MorphParse;

public class WordParser {

    TurkishMorphParser parser;

    public WordParser(TurkishMorphParser parser) {
        this.parser = parser;
    }

    public MorphParse parse0(String word) {
        return parser.parse(word).get(0);
    }

    public List<MorphParse> parseAll(String word) {
        return parser.parse(word);
    }
}

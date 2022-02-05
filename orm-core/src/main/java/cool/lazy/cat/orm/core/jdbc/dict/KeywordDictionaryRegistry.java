package cool.lazy.cat.orm.core.jdbc.dict;

import cool.lazy.cat.orm.core.jdbc.constant.Case;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: mahao
 * @date: 2022-01-23 16:15
 */
public class KeywordDictionaryRegistry {

    protected static final Map<Case, KeywordDictionary> DICTIONARY_MAP = new HashMap<>();

    static {
        registry(Case.LOWERCASE, new LowercaseKeyWordDictionary());
        registry(Case.UPPERCASE, new UppercaseKeyWordDictionary());
    }

    public static KeywordDictionary getInstance(Case charCase) {
        return DICTIONARY_MAP.get(charCase);
    }

    public static void registry(Case charCase, KeywordDictionary keywordDictionary) {
        DICTIONARY_MAP.put(charCase, keywordDictionary);
    }
}

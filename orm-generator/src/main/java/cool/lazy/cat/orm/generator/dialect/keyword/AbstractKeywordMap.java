package cool.lazy.cat.orm.generator.dialect.keyword;

import java.util.Collection;
import java.util.Locale;

/**
 * @author : jason.ma
 * @date : 2022/7/12 10:33
 */
public abstract class AbstractKeywordMap implements KeywordMap {

    protected final Collection<String> keywords;

    protected AbstractKeywordMap(Collection<String> keywords) {
        if (null == keywords) {
            throw new IllegalArgumentException("关键字不能为空");
        }
        this.keywords = keywords;
    }

    @Override
    public boolean isKeyword(String word) {
        return keywords.contains(word.toUpperCase(Locale.ENGLISH));
    }
}

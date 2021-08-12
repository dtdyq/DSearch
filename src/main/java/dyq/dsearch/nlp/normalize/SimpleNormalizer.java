/*
 * 
 */

package dyq.dsearch.nlp.normalize;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class SimpleNormalizer implements Normalizer {

    private static final Pattern WHITESPACE = Pattern.compile("(?U)\\s+");

    private static final Pattern CONTROL_FORMAT_CHARS = Pattern.compile("[\\p{Cc}\\p{Cf}]");

    private static final Pattern DOUBLE_QUOTES = Pattern.compile("[\\u02BA\\u201C\\u201D\\u201E\\u201F\\u2033\\u2036\\u275D\\u275E\\u301D\\u301E\\u301F\\uFF02]");

    private static final Pattern SINGLE_QUOTES = Pattern.compile("[\\u0060\\u02BB\\u02BC\\u02BD\\u2018\\u2019\\u201A\\u201B\\u275B\\u275C]");

    private static final Pattern DASH = Pattern.compile("[\\u2012\\u2013\\u2014\\u2015\\u2053]");

    /**
     * The singleton instance.
     */
    private static final SimpleNormalizer singleton = new SimpleNormalizer();

    /**
     * Constructor.
     */
    public SimpleNormalizer() { }

    /**
     * Returns the singleton instance.
     * @return the singleton instance.
     */
    public static SimpleNormalizer getInstance() {
        return singleton;
    }

    @Override
    public String normalize(String text) {
        if (!java.text.Normalizer.isNormalized(text, java.text.Normalizer.Form.NFKC)) {
            text = java.text.Normalizer.normalize(text, java.text.Normalizer.Form.NFKC);
        }

        text = WHITESPACE.matcher(text).replaceAll(" ");

        text = CONTROL_FORMAT_CHARS.matcher(text).replaceAll("");

        text = DOUBLE_QUOTES.matcher(text).replaceAll("\"");

        text = SINGLE_QUOTES.matcher(text).replaceAll("'");

        text = DASH.matcher(text).replaceAll("--");

        return text.trim();
    }
}

/*
 * 
 */

package dyq.dsearch;

import dyq.dsearch.nlp.normalize.SimpleNormalizer;
import dyq.dsearch.nlp.stem.LancasterStemmer;

import org.junit.jupiter.api.Test;

import java.util.Collections;

public class TestNLP {
    @Test
    public void stemming() {
        LancasterStemmer stemmer = new LancasterStemmer();
        System.out.println(stemmer.stemming(Collections.singletonList("computer")));

        System.out.println(new SimpleNormalizer().normalize("\u2322"));
    }
}

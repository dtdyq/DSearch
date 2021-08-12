/*
 * 
 */

package dyq.dsearch.nlp.lemm;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

@Component
public class Lemmatization {

    private StanfordCoreNLP pipeline;

    public Lemmatization() {
        // Create StanfordCoreNLP object properties, with POS tagging
        // (required for lemmatization), and lemmatization
        Properties props;
        props = new Properties();
        props.put("annotators", "tokenize, ssplit, pos, lemma");

        // StanfordCoreNLP loads a lot of models, so you probably
        // only want to do this once per execution
        this.pipeline = new StanfordCoreNLP(props);
    }

    public static void main(String[] args) {
        Lemmatization l = new Lemmatization();
        List<String> ls = new ArrayList<>();
        ls.add("apples");
        ls.add("stopping");
        ls.add("computers");
        ls.add("apples");

        List<String> list = l.lemmatize(ls);
        System.out.println(list);

    }

    public List<String> lemmatize(List<String> words) {
        String documentText = String.join(" ", words);
        List<String> lemmas = new LinkedList<>();

        // create an empty Annotation just with the given text
        Annotation document = new Annotation(documentText);

        // run all Annotators on this text
        this.pipeline.annotate(document);

        // Iterate over all of the sentences found
        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
        for (CoreMap sentence : sentences) {
            // Iterate over all tokens in a sentence
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                // Retrieve and add the lemma for each word into the list of lemmas
                lemmas.add(token.get(CoreAnnotations.LemmaAnnotation.class));
            }
        }
        return lemmas;
    }
}
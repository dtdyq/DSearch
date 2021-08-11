/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2021. All rights reserved.
 */

package dyq.dsearch.nlp.tokenize;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.dictionary.stopword.CoreStopWordDictionary;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;

import dyq.dsearch.nlp.lemm.Lemmatization;
import dyq.dsearch.nlp.stem.LancasterStemmer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TextTokenizer {

    @Autowired
    private LancasterStemmer lancasterStemmer;
    @Autowired
    private StopWordPool stopWordPool;
    @Autowired
    private Lemmatization lemmatization;

    public static void main(String[] args) {
        System.out.println(new TextTokenizer().tokenizerForIndex(
            "For deploying Java applications on servers. Includes tools for JVM monitoring and tools commonly required for server applications, but does not include browser integration (the Java plug-in), auto-update, nor an installer. Learn more"));
    }

    public List<String> tokenizerForIndex(String text) {
        Segment segment = HanLP.newSegment()
            .enableCustomDictionary(true)
            .enableIndexMode(true)
            .enableOrganizationRecognize(true)
            .enableMultithreading(true)
            .enableAllNamedEntityRecognize(true)
            .enableNumberQuantifierRecognize(true);
        List<Term> words = segment.seg(text);
        return lemmatization.lemmatize(words.stream()
            .map(t -> t.word.toLowerCase())
            .filter(w -> !"".equals(w.trim()))
            .collect(Collectors.toList()));
    }

    public List<String> tokenizer(String text) {
        Segment segment = HanLP.newSegment()
            .enableCustomDictionary(true)
            .enableOrganizationRecognize(true)
            .enableMultithreading(true)
            .enableAllNamedEntityRecognize(true)
            .enableNumberQuantifierRecognize(true);
        List<Term> words = segment.seg(text);
        CoreStopWordDictionary.apply(words);
        return words.stream()
            .map(t -> t.word.toLowerCase())
            .filter(w -> !"".equals(w.trim()))
            .collect(Collectors.toList());
    }
}

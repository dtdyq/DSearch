/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2021. All rights reserved.
 */

package dyq.dsearch.nlp.tokenize;

import com.hankcs.hanlp.dictionary.stopword.CoreStopWordDictionary;
import com.hankcs.hanlp.seg.common.Term;

import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Component
public class StopWordPool {

    public StopWordPool() {
        List<String> words = new ArrayList<>();
        try {
            words = Files.readAllLines(ResourceUtils.getFile("classpath:cfg/stopwords.dat").toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        words.forEach(CoreStopWordDictionary::add);
        System.out.println(words);
    }

    public void filter(List<Term> old) {
        CoreStopWordDictionary.apply(old);
    }

}

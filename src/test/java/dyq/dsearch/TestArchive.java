/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2021. All rights reserved.
 */

package dyq.dsearch;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.dictionary.CustomDictionary;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import com.huaban.analysis.jieba.JiebaSegmenter;

import kong.unirest.Unirest;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class TestArchive {
    @Test
    public void test(){
        System.out.println(Unirest.post("http://127.0.0.1:8080/upload")
            .field("file", new File("D:\\Develop_Problem\\210120TBJD\\数据\\tb护理机器人\\618085809803.json")).asEmpty().getStatus());
    }
    @Test
    public void testjb(){
        JiebaSegmenter segmenter = new JiebaSegmenter();
        String[] sentences =
            new String[] {"想到了经典梗：工信处女干事每月经过下属科室都要亲口交代24口交换机等技术性器件的安装工作"};
        for (String sentence : sentences) {
            System.out.println(segmenter.process(sentence, JiebaSegmenter.SegMode.SEARCH).toString());
        }
    }
    @Test
    public void testWorld() throws IOException {
        CustomDictionary.add("女干事");
        Segment seg = HanLP.newSegment().enableIndexMode(true);
        List<Term> ret = seg.seg("想到了经典梗：工信处女干事每月经过下属科室都要亲口交代24口交换机等技术性器件的安装工作");
        System.out.println(ret);
        System.out.println(UUID.nameUUIDFromBytes("想到了经典梗：工信处女干事每月经过下属科室都要亲口交代24口交换机等技术性器件的安装工作".getBytes()));
        System.out.println(UUID.nameUUIDFromBytes("想到了经典梗：工信处女干事每月经过下属科室都要亲口交代24口交换机等技术性器件的安装工作".getBytes()));
    }

}

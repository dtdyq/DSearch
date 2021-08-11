package dyq.dsearch;

import dyq.dsearch.repo.DocumentRepo;
import dyq.dsearch.repo.IndexingItemRepo;
import dyq.dsearch.nlp.simi.TFIDF;
import dyq.dsearch.nlp.tokenize.TextTokenizer;
import dyq.dsearch.service.SearchService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class DsearchApplicationTests {

    String ss = "\"算法工程师\\n\" +\n"
        + "\t\t\t\"算法（Algorithm）是一系列解决问题的清晰指令，也就是说，能够对一定规范的输入，在有限时间内获得所要求的输出。如果一个算法有缺陷，或不适合于某个问题，执行这个算法将不会解决这个问题。不同的算法可能用不同的时间、空间或效率来完成同样的任务。一个算法的优劣可以用空间复杂度与时间复杂度来衡量。算法工程师就是利用算法处理事物的人。\\n\" +\n"
        + "\t\t\t\"\\n\" +\n" + "\t\t\t\"1职位简介\\n\" +\n" + "\t\t\t\"算法工程师是一个非常高端的职位；\\n\" +\n"
        + "\t\t\t\"专业要求：计算机、电子、通信、数学等相关专业；\\n\" +\n" + "\t\t\t\"学历要求：本科及其以上的学历，大多数是硕士学历及其以上；\\n\" +\n"
        + "\t\t\t\"语言要求：英语要求是熟练，基本上能阅读国外专业书刊；\\n\" +\n" + "\t\t\t\"必须掌握计算机相关知识，熟练使用仿真工具MATLAB等，必须会一门编程语言。\\n\" +\n"
        + "\t\t\t\"\\n\" +\n" + "\t\t\t\"2研究方向\\n\" +\n" + "\t\t\t\"视频算法工程师、图像处理算法工程师、音频算法工程师 通信基带算法工程师\\n\" +\n"
        + "\t\t\t\"\\n\" +\n" + "\t\t\t\"3目前国内外状况\\n\" +\n"
        + "\t\t\t\"目前国内从事算法研究的工程师不少，但是高级算法工程师却很少，是一个非常紧缺的专业工程师。算法工程师根据研究领域来分主要有音频/视频算法处理、图像技术方面的二维信息算法处理和通信物理层、雷达信号处理、生物医学信号处理等领域的一维信息算法处理。\\n\" +\n"
        + "\t\t\t\"在计算机音视频和图形图像技术等二维信息算法处理方面目前比较先进的视频处理算法：机器视觉成为此类算法研究的核心；另外还有2D转3D算法(2D-to-3D conversion)，去隔行算法(de-interlacing)，运动估计运动补偿算法(Motion estimation/Motion Compensation)，去噪算法(Noise Reduction)，缩放算法(scaling)，锐化处理算法(Sharpness)，超分辨率算法(Super Resolution),手势识别(gesture recognition),人脸识别(face recognition)。\\n\" +\n"
        + "\t\t\t\"在通信物理层等一维信息领域目前常用的算法：无线领域的RRM、RTT，传送领域的调制解调、信道均衡、信号检测、网络优化、信号分解等。\\n\" +\n"
        + "\t\t\t\"另外数据挖掘、互联网搜索算法也成为当今的热门方向。\\n\" +\n" + "\t\t\t\"算法工程师逐渐往人工智能方向发展。\"";
    String sss = "软件工程师英文是Software Engineer，是从事软件职业的人员的一种职业能力的认证，通过它说明具备了工程师的资格。软件工程师是从事软件开发相关工作的人员的统称。\n"
        + "它是一个广义的概念，包括软件设计人员、软件架构人员、软件工程管理人员、程序员等一系列岗位，工作内容都与软件开发生产相关。软件工程师的技术要求是比较全面的，除了最基础的编程语言（C语言/C++/JAVA等）、数据库技术（SQL/ORACLE/DB2等）等，还有诸多如JAVASCRIPT、AJAX、HIBERNATE、SPRING等前沿技术。此外，关于网络工程和软件测试的其他技术也要有所涉猎。";
    @Autowired
    IndexingItemRepo indexingItemRepo;
    @Autowired
    DocumentRepo documentRepo;

    @Autowired
    TFIDF tfidf;

    @Autowired
    TextTokenizer textTokenizer;

    @Autowired
    SearchService searchService;
    @Test
    void contextLoads() {
        String re = searchService.searchForSimpleText("computer");
        System.out.println("re==="+re);
        // documentRepo.deleteAll();
        // indexingItemRepo.deleteAll();
        // System.out.println("index---"+indexingItemRepo.findAll());
        // System.out.println("doc---"+documentRepo.findAll());
        // String uuid = UUID.randomUUID().toString();
        // Document document = new Document();
        // document.setUuid(uuid);
        // document.setContent(ss);
        // documentRepo.save(document);
        //
        // String uuid1 = UUID.randomUUID().toString();
        // Document document1 = new Document();
        // document1.setUuid(uuid1);
        // document1.setContent(sss);
        // documentRepo.save(document1);
        // textTokenizer.tokenizerForIndex(ss).stream().distinct().forEach(new Consumer<String>() {
        // @Override
        // public void accept(String s) {
        // IndexingItem indexingItem = indexingItemRepo.findById(s).orElse(new IndexingItem());
        // indexingItem.setWord(s);
        // indexingItem.getUuids().add(uuid);
        // indexingItemRepo.save(indexingItem);
        // }
        // });
        // textTokenizer.tokenizerForIndex(sss).stream().distinct().forEach(new Consumer<String>() {
        // @Override
        // public void accept(String s) {
        // IndexingItem indexingItem = indexingItemRepo.findById(s).orElse(new IndexingItem());
        // indexingItem.setWord(s);
        // indexingItem.getUuids().add(uuid1);
        // indexingItemRepo.save(indexingItem);
        // }
        // });
        // System.out.println("index---"+indexingItemRepo.findAll());
        // System.out.println("doc---"+documentRepo.findAll());
        //
        // System.out.println(tfidf.TFDF(ss));
        // System.out.println(tfidf.TFDF("算法"));
        // List<Word> ssf = tfidf.TFDF(ss);
        // List<Word> sssf = tfidf.TFDF(sss);
        // List<Word> q = tfidf.TFDF("工程");
        //
        // TextSimilarity similarity = new CosineSimilarity();
        // double ret = similarity.getSimilarity(ssf.stream().map(new Function<Word, org.xm.nlp.Word>() {
        //     @Override
        //     public org.xm.nlp.Word apply(Word word) {
        //         org.xm.nlp.Word w = new org.xm.nlp.Word(word.getName());
        //         w.setWeight(word.getWeight());
        //         return w;
        //     }
        // }).collect(Collectors.toList()), q.stream().map(new Function<Word, org.xm.nlp.Word>() {
        //     @Override
        //     public org.xm.nlp.Word apply(Word word) {
        //         org.xm.nlp.Word w = new org.xm.nlp.Word(word.getName());
        //         w.setWeight(word.getWeight());
        //         return w;
        //     }
        // }).collect(Collectors.toList()));
        // System.out.println("ret1===" + ret);
        //
        //
        // q = tfidf.TFDF("工程");
        // ret = similarity.getSimilarity(sssf.stream().map(new Function<Word, org.xm.nlp.Word>() {
        //     @Override
        //     public org.xm.nlp.Word apply(Word word) {
        //         org.xm.nlp.Word w = new org.xm.nlp.Word(word.getName());
        //         w.setWeight(word.getWeight());
        //         return w;
        //     }
        // }).collect(Collectors.toList()), q.stream().map(new Function<Word, org.xm.nlp.Word>() {
        //     @Override
        //     public org.xm.nlp.Word apply(Word word) {
        //         org.xm.nlp.Word w = new org.xm.nlp.Word(word.getName());
        //         w.setWeight(word.getWeight());
        //         return w;
        //     }
        // }).collect(Collectors.toList()));
        // System.out.println("ret2===" + ret);
        System.out.println(indexingItemRepo.findAll());
    }

    @Test
    void testTFIDF() {
       List<String> re =  textTokenizer.tokenizerForIndex("A computer is a machine that can be instructed to carry out sequences of arithmetic or logical operations automatically via computer programming. Modern computers have the ability to follow generalized sets of operations, called programs. These programs enable computers to perform an extremely wide range of tasks.");
       System.out.println(re);
    }

}

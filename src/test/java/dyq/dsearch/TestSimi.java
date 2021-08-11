/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2021. All rights reserved.
 */

package dyq.dsearch;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.algorithm.MaxHeap;
import com.hankcs.hanlp.summary.TextRankKeyword;

import dyq.dsearch.model.Word;

import org.junit.jupiter.api.Test;
import org.xm.similarity.text.CosineSimilarity;
import org.xm.similarity.text.TextSimilarity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class TestSimi {
    @Test
    public void test() throws IOException {

        // DocVectorModel vectorModel = new DocVectorModel(new
        // WordVectorModel("D:\\code\\superProject\\dsearch\\data\\vector.vec"));
        // vectorModel.setSegment(HanLP.newSegment()
        // .enableCustomDictionary(true)
        // .enableIndexMode(true)
        // .enableOrganizationRecognize(true)
        // .enableMultithreading(true)
        // .enableAllNamedEntityRecognize(true)
        // .enableNumberQuantifierRecognize(true));

        TextSimilarity similarity = new CosineSimilarity();
        System.out.println(similarity.getSimilarity("算法工程师\n"
            + "算法（Algorithm）是一系列解决问题的清晰指令，也就是说，能够对一定规范的输入，在有限时间内获得所要求的输出。如果一个算法有缺陷，或不适合于某个问题，执行这个算法将不会解决这个问题。不同的算法可能用不同的时间、空间或效率来完成同样的任务。一个算法的优劣可以用空间复杂度与时间复杂度来衡量。算法工程师就是利用算法处理事物的人。\n"
            + "\n" + "1职位简介\n" + "算法工程师是一个非常高端的职位；\n" + "专业要求：计算机、电子、通信、数学等相关专业；\n" + "学历要求：本科及其以上的学历，大多数是硕士学历及其以上；\n"
            + "语言要求：英语要求是熟练，基本上能阅读国外专业书刊；\n" + "必须掌握计算机相关知识，熟练使用仿真工具MATLAB等，必须会一门编程语言。\n" + "\n" + "2研究方向\n"
            + "视频算法工程师、图像处理算法工程师、音频算法工程师 通信基带算法工程师\n" + "\n" + "3目前国内外状况\n"
            + "目前国内从事算法研究的工程师不少，但是高级算法工程师却很少，是一个非常紧缺的专业工程师。算法工程师根据研究领域来分主要有音频/视频算法处理、图像技术方面的二维信息算法处理和通信物理层、雷达信号处理、生物医学信号处理等领域的一维信息算法处理。\n"
            + "在计算机音视频和图形图像技术等二维信息算法处理方面目前比较先进的视频处理算法：机器视觉成为此类算法研究的核心；另外还有2D转3D算法(2D-to-3D conversion)，去隔行算法(de-interlacing)，运动估计运动补偿算法(Motion estimation/Motion Compensation)，去噪算法(Noise Reduction)，缩放算法(scaling)，锐化处理算法(Sharpness)，超分辨率算法(Super Resolution),手势识别(gesture recognition),人脸识别(face recognition)。\n"
            + "在通信物理层等一维信息领域目前常用的算法：无线领域的RRM、RTT，传送领域的调制解调、信道均衡、信号检测、网络优化、信号分解等。\n" + "另外数据挖掘、互联网搜索算法也成为当今的热门方向。\n"
            + "算法工程师逐渐往人工智能方向发展。", "工程"));
        System.out.println(similarity.getSimilarity(
            "\"软件工程师英文是Software Engineer，是从事软件职业的人员的一种职业能力的认证，通过它说明具备了工程师的资格。软件工程师是从事软件开发相关工作的人员的统称。\\n\"\n"
                + "        + \"它是一个广义的概念，包括软件设计人员、软件架构人员、软件工程管理人员、程序员等一系列岗位，工作内容都与软件开发生产相关。软件工程师的技术要求是比较全面的，除了最基础的编程语言（C语言/C++/JAVA等）、数据库技术（SQL/ORACLE/DB2等）等，还有诸多如JAVASCRIPT、AJAX、HIBERNATE、SPRING等前沿技术。此外，关于网络工程和软件测试的其他技术也要有所涉猎。\";",
            "工程"));

    }

    @Test
    public void merge() throws IOException {
        List<String> data = new ArrayList<>();
        Files.list(Paths.get("D:\\code\\superProject\\dsearch\\data\\text0")).forEach(new Consumer<Path>() {
            @Override
            public void accept(Path path) {
                try {
                    data.addAll(Files.readAllLines(path));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Files.write(Paths.get("D:\\code\\superProject\\dsearch\\data\\text0\\all.txt"), data);
    }

    @Test
    public void testRest() {
        String text = "算法工程师\n"
            + "算法（Algorithm）是一系列解决问题的清晰指令，也就是说，能够对一定规范的输入，在有限时间内获得所要求的输出。如果一个算法有缺陷，或不适合于某个问题，执行这个算法将不会解决这个问题。不同的算法可能用不同的时间、空间或效率来完成同样的任务。一个算法的优劣可以用空间复杂度与时间复杂度来衡量。算法工程师就是利用算法处理事物的人。\n"
            + "\n" + "1职位简介\n" + "算法工程师是一个非常高端的职位；\n" + "专业要求：计算机、电子、通信、数学等相关专业；\n" + "学历要求：本科及其以上的学历，大多数是硕士学历及其以上；\n"
            + "语言要求：英语要求是熟练，基本上能阅读国外专业书刊；\n" + "必须掌握计算机相关知识，熟练使用仿真工具MATLAB等，必须会一门编程语言。\n" + "\n" + "2研究方向\n"
            + "视频算法工程师、图像处理算法工程师、音频算法工程师 通信基带算法工程师\n" + "\n" + "3目前国内外状况\n"
            + "目前国内从事算法研究的工程师不少，但是高级算法工程师却很少，是一个非常紧缺的专业工程师。算法工程师根据研究领域来分主要有音频/视频算法处理、图像技术方面的二维信息算法处理和通信物理层、雷达信号处理、生物医学信号处理等领域的一维信息算法处理。\n"
            + "在计算机音视频和图形图像技术等二维信息算法处理方面目前比较先进的视频处理算法：机器视觉成为此类算法研究的核心；另外还有2D转3D算法(2D-to-3D conversion)，去隔行算法(de-interlacing)，运动估计运动补偿算法(Motion estimation/Motion Compensation)，去噪算法(Noise Reduction)，缩放算法(scaling)，锐化处理算法(Sharpness)，超分辨率算法(Super Resolution),手势识别(gesture recognition),人脸识别(face recognition)。\n"
            + "在通信物理层等一维信息领域目前常用的算法：无线领域的RRM、RTT，传送领域的调制解调、信道均衡、信号检测、网络优化、信号分解等。\n" + "另外数据挖掘、互联网搜索算法也成为当今的热门方向。\n"
            + "算法工程师逐渐往人工智能方向发展。";
        Map<String, Float> s = new TextRankKeyword().getTermAndRank(text);
        List var5 = (new MaxHeap(s.size(), new Comparator<Map.Entry<String, Float>>() {
            public int compare(Map.Entry<String, Float> o1, Map.Entry<String, Float> o2) {
                return ((Float) o1.getValue()).compareTo((Float) o2.getValue());
            }
        })).addAll(s.entrySet()).toList();
        System.out.println(var5);
        List<String> phraseList = HanLP.extractKeyword(text, 1);
        System.out.println(phraseList);
    }

    @Test
    public void testWord() {
        System.out.println(new Word("tet", 12.3F));
        System.out.println((float) Math.log(2));
    }
}

/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2021. All rights reserved.
 */

package dyq.dsearch.model;

import lombok.Data;

import org.springframework.data.annotation.Id;

import java.util.Map;

@Data
public class DocumentDescriptor {
    @Id
    private String uuid;
    private String path;
    private Map<String, Integer> words;
    private String time;
    private String title;
}

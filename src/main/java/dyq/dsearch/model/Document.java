/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2021. All rights reserved.
 */

package dyq.dsearch.model;

import com.google.gson.annotations.Expose;

import lombok.Data;

import org.springframework.data.annotation.Id;

import java.util.List;

@Data
public class Document {
    @Id
    private String uuid;
    @Expose
    private String title;
    @Expose
    private String content;
    @Expose
    private List<String> tags;
}

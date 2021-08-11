/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2021. All rights reserved.
 */

package dyq.dsearch.model;

import lombok.Data;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

@Data
public class IndexingItem {
    @Id
    private String word;
    private List<String> uuids = new ArrayList<>();
}

/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2021. All rights reserved.
 */

package dyq.dsearch.repo;

import dyq.dsearch.model.IndexingItem;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface IndexingItemRepo extends MongoRepository<IndexingItem,String> { }

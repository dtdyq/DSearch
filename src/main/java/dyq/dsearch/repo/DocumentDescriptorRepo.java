/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2021. All rights reserved.
 */

package dyq.dsearch.repo;

import dyq.dsearch.model.DocumentDescriptor;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface DocumentDescriptorRepo extends MongoRepository<DocumentDescriptor, String> {
}

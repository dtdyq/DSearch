/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2021. All rights reserved.
 */

package dyq.dsearch.web;

import dyq.dsearch.model.Document;
import dyq.dsearch.service.IndexingService;
import dyq.dsearch.service.SearchService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;

@RestController
public class ApiPort {
    @Autowired
    private IndexingService indexingService;
    @Autowired
    private SearchService searchService;

    @RequestMapping(value = "/archive/file", method = RequestMethod.POST)
    public void upload(@RequestParam("file") MultipartFile[] files) {
        for (MultipartFile file : files) {
            System.out.println(file.getResource().getFilename());
        }
    }

    @RequestMapping(value = "/archive/data", method = RequestMethod.POST)
    public void data(@RequestBody Document document) {
        System.out.println(document);
        indexingService.indexDocuments(Collections.singletonList(document));
    }

    @RequestMapping(value = "/search/text", method = RequestMethod.POST)
    public String simpleSearch(@RequestParam("str") String text) {
        System.out.println(text);
        return searchService.searchForSimpleText(text);
    }
}

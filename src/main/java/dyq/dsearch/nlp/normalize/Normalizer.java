/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2021. All rights reserved.
 */

package dyq.dsearch.nlp.normalize;

/**
 * Normalization transforms text into a canonical form by removing unwanted
 * variations. Normalization may range from light textual cleanup such as
 * compressing whitespace to more aggressive and knowledge-intensive forms
 * like standardizing date formats or expanding abbreviations. The nature and
 * extent of normalization, as well as whether it is most appropriate to apply
 * on the document, sentence, or token level, must be determined in the context
 * of a specific application.
 *
 * @author Mark Arehart
 */
public interface Normalizer {

    /**
     * Normalize the given string.
     * @param text the text.
     * @return the normalized text.
     */
    String normalize(String text);
}

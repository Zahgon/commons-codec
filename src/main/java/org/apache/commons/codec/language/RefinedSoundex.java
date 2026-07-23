/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.codec.language;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringEncoder;

/**
 * Encodes a string into a Refined Soundex value. A refined Soundex code is
 * optimized for spell checking words. Soundex method originally developed by
 * <CITE>Margaret Odell</CITE> and <CITE>Robert Russell</CITE>.
 *
 * <p>
 * This class is immutable and thread-safe.
 * </p>
 */
public class RefinedSoundex implements StringEncoder {

    /**
     * Mapping:
     * <pre>
     * 0: A E I O U Y H W
     * 1: B P
     * 2: F V
     * 3: C K S
     * 4: G J
     * 5: Q X Z
     * 6: D T
     * 7: L
     * 8: M N
     * 9: R
     * </pre>
     *
     * @since 1.4
     */
    //                                                      ABCDEFGHIJKLMNOPQRSTUVWXYZ
    public static final String US_ENGLISH_MAPPING_STRING = "01360240043788015936020505";

    /**
     * RefinedSoundex is *refined* for a number of reasons one being that the
     * mappings have been altered. This implementation contains default
     * mappings for US English.
     */
    private static final char[] US_ENGLISH_MAPPING = US_ENGLISH_MAPPING_STRING.toCharArray();

    /**
     * This static variable contains an instance of the RefinedSoundex using
     * the US_ENGLISH mapping.
     */
    public static final RefinedSoundex US_ENGLISH = new RefinedSoundex();

    /**
     * Every letter of the alphabet is "mapped" to a numerical value. This char
     * array holds the values to which each letter is mapped. This
     * implementation contains a default map for US_ENGLISH.
     */
    private final char[] soundexMapping;

    /**
     * Creates an instance of the RefinedSoundex object using the default US
     * English mapping.
     */
    public RefinedSoundex() {
        this.soundexMapping = US_ENGLISH_MAPPING;
    }

    /**
     * Creates a refined Soundex instance using a custom mapping. This
     * constructor can be used to customize the mapping, and/or possibly
     * provide an internationalized mapping for a non-Western character set.
     *
     * @param mapping
     *                  Mapping array to use when finding the corresponding code for
     *                  a given character.
     */
    public RefinedSoundex(final char[] mapping) {
        this.soundexMapping = mapping.clone();
    }

    /**
     * Creates a refined Soundex instance using a custom mapping. This constructor can be used to customize the mapping,
     * and/or possibly provide an internationalized mapping for a non-Western character set.
     *
     * @param mapping
     *            Mapping string to use when finding the corresponding code for a given character.
     * @since 1.4
     */
    public RefinedSoundex(final String mapping) {
        this.soundexMapping = mapping.toCharArray();
    }

    public int difference(final String s1, final String s2) throws EncoderException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Object encode(final Object obj) throws EncoderException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String encode(final String str) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    char getMappingCode(final char c) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String soundex(String str) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}

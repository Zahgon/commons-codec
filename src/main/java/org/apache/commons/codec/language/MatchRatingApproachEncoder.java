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

import java.util.Locale;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringEncoder;

/**
 * Match Rating Approach Phonetic Algorithm Developed by <CITE>Western Airlines</CITE> in 1977.
 * <p>
 * This class is immutable and thread-safe.
 * </p>
 *
 * @see <a href="https://en.wikipedia.org/wiki/Match_rating_approach">Wikipedia - Match Rating Approach</a>
 * @since 1.8
 */
public class MatchRatingApproachEncoder implements StringEncoder {

    private static final String SPACE = " ";

    private static final String EMPTY = "";

    /**
     * The plain letter equivalent of the accented letters.
     */
    private static final String PLAIN_ASCII = // grave
    "AaEeIiOoUu" + // acute
    "AaEeIiOoUuYy" + // circumflex
    "AaEeIiOoUuYy" + // tilde
    "AaOoNn" + // umlaut
    "AaEeIiOoUuYy" + // ring
    "Aa" + // cedilla
    "Cc" + // double acute
    "OoUu";

    /**
     * Unicode characters corresponding to various accented letters. For example: \u00DA is U acute etc...
     */
    private static final String UNICODE = "\u00C0\u00E0\u00C8\u00E8\u00CC\u00EC\u00D2\u00F2\u00D9\u00F9" + "\u00C1\u00E1\u00C9\u00E9\u00CD\u00ED\u00D3\u00F3\u00DA\u00FA\u00DD\u00FD" + "\u00C2\u00E2\u00CA\u00EA\u00CE\u00EE\u00D4\u00F4\u00DB\u00FB\u0176\u0177\u00C3\u00E3\u00D5\u00F5\u00D1\u00F1" + "\u00C4\u00E4\u00CB\u00EB\u00CF\u00EF\u00D6\u00F6\u00DC\u00FC\u0178\u00FF\u00C5\u00E5\u00C7\u00E7\u0150\u0151\u0170\u0171";

    /**
     * Double consonants.
     */
    private static final String[] DOUBLE_CONSONANT = { "BB", "CC", "DD", "FF", "GG", "HH", "JJ", "KK", "LL", "MM", "NN", "PP", "QQ", "RR", "SS", "TT", "VV", "WW", "XX", "YY", "ZZ" };

    /**
     * Constructs a new instance.
     */
    public MatchRatingApproachEncoder() {
        // empty
    }

    String cleanName(final String name) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public final Object encode(final Object object) throws EncoderException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public final String encode(String name) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    String getFirst3Last3(final String name) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    int getMinRating(final int sumLength) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean isEncodeEquals(String name1, String name2) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    boolean isVowel(final String letter) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    int leftToRightThenRightToLeftProcessing(final String name1, final String name2) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    String removeAccents(final String accentedWord) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    String removeDoubleConsonants(final String name) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    String removeVowels(String name) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}

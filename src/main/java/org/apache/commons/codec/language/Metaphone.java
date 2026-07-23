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
 * Encodes a string into a Metaphone value.
 * <p>
 * Initial Java implementation by <CITE>William B. Brogden. December, 1997</CITE>.
 * Permission given by <CITE>wbrogden</CITE> for code to be used anywhere.
 * </p>
 * <p>
 * <CITE>Hanging on the Metaphone</CITE> by <CITE>Lawrence Philips</CITE> in <CITE>Computer Language of Dec. 1990,
 * p 39.</CITE>
 * </p>
 * <p>
 * Note, that this does not match the algorithm that ships with PHP, or the algorithm found in the Perl implementations:
 * </p>
 * <ul>
 * <li><a href="https://search.cpan.org/~mschwern/Text-Metaphone-1.96/Metaphone.pm">Text:Metaphone-1.96</a>
 *  (broken link 4/30/2013) </li>
 * <li><a href="https://metacpan.org/source/MSCHWERN/Text-Metaphone-1.96//Metaphone.pm">Text:Metaphone-1.96</a>
 *  (link checked 4/30/2013) </li>
 * </ul>
 * <p>
 * They have had undocumented changes from the originally published algorithm.
 * For more information, see <a href="https://issues.apache.org/jira/browse/CODEC-57">CODEC-57</a>.
 * </p>
 * <p>
 * This class is conditionally thread-safe.
 * The instance field for maximum code length is mutable {@link #setMaxCodeLen(int)}
 * but is not volatile, and accesses are not synchronized.
 * If an instance of the class is shared between threads, the caller needs to ensure that suitable synchronization
 * is used to ensure safe publication of the value between threads, and must not invoke {@link #setMaxCodeLen(int)}
 * after initial setup.
 * </p>
 */
public class Metaphone implements StringEncoder {

    /**
     * Five values in the English language.
     */
    private static final String VOWELS = "AEIOU";

    /**
     * Variable used in Metaphone algorithm.
     */
    private static final String FRONTV = "EIY";

    /**
     * Variable used in Metaphone algorithm.
     */
    private static final String VARSON = "CSPTG";

    /**
     * The max code length for Metaphone is 4.
     */
    private int maxCodeLen = 4;

    /**
     * Constructs a new instance.
     */
    public Metaphone() {
        // empty
    }

    @Override
    public Object encode(final Object obj) throws EncoderException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String encode(final String str) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public int getMaxCodeLen() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private boolean isLastChar(final int wdsz, final int n) {
        return n + 1 == wdsz;
    }

    public boolean isMetaphoneEqual(final String str1, final String str2) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private boolean isNextChar(final StringBuilder string, final int index, final char c) {
        boolean matches = false;
        if (index >= 0 && index < string.length() - 1) {
            matches = string.charAt(index + 1) == c;
        }
        return matches;
    }

    private boolean isPreviousChar(final StringBuilder string, final int index, final char c) {
        boolean matches = false;
        if (index > 0 && index < string.length()) {
            matches = string.charAt(index - 1) == c;
        }
        return matches;
    }

    private boolean isVowel(final StringBuilder string, final int index) {
        return VOWELS.indexOf(string.charAt(index)) >= 0;
    }

    public String metaphone(final String txt) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private boolean regionMatch(final StringBuilder string, final int index, final String test) {
        boolean matches = false;
        if (index >= 0 && index + test.length() - 1 < string.length()) {
            final String substring = string.substring(index, index + test.length());
            matches = substring.equals(test);
        }
        return matches;
    }

    public void setMaxCodeLen(final int maxCodeLen) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}

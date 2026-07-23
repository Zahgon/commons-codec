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

import java.util.regex.Pattern;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringEncoder;

/**
 * Encodes a string into a NYSIIS value. NYSIIS is an encoding used to relate similar names, but can also be used as a
 * general purpose scheme to find word with similar phonemes.
 * <p>
 * NYSIIS features an accuracy increase of 2.7% over the traditional Soundex algorithm.
 * </p>
 * <p>
 * Algorithm description:
 * </p>
 * <pre>
 * 1. Transcode first characters of name
 *   1a. MAC -&gt;   MCC
 *   1b. KN  -&gt;   NN
 *   1c. K   -&gt;   C
 *   1d. PH  -&gt;   FF
 *   1e. PF  -&gt;   FF
 *   1f. SCH -&gt;   SSS
 * 2. Transcode last characters of name
 *   2a. EE, IE          -&gt;   Y
 *   2b. DT,RT,RD,NT,ND  -&gt;   D
 * 3. First character of key = first character of name
 * 4. Transcode remaining characters by following these rules, incrementing by one character each time
 *   4a. EV  -&gt;   AF  else A,E,I,O,U -&gt; A
 *   4b. Q   -&gt;   G
 *   4c. Z   -&gt;   S
 *   4d. M   -&gt;   N
 *   4e. KN  -&gt;   N   else K -&gt; C
 *   4f. SCH -&gt;   SSS
 *   4g. PH  -&gt;   FF
 *   4h. H   -&gt;   If previous or next is non-vowel, previous
 *   4i. W   -&gt;   If previous is vowel, previous
 *   4j. Add current to key if current != last key character
 * 5. If last character is S, remove it
 * 6. If last characters are AY, replace with Y
 * 7. If last character is A, remove it
 * 8. Collapse all strings of repeated characters
 * 9. Add original first character of name as first character of key
 * </pre>
 * <p>
 * This class is immutable and thread-safe.
 * </p>
 *
 * @see <a href="https://en.wikipedia.org/wiki/NYSIIS">NYSIIS on Wikipedia</a>
 * @see <a href="https://www.dropby.com/NYSIIS.html">NYSIIS on dropby.com</a>
 * @see Soundex
 * @since 1.7
 */
public class Nysiis implements StringEncoder {

    private static final char[] CHARS_A = { 'A' };

    private static final char[] CHARS_AF = { 'A', 'F' };

    private static final char[] CHARS_C = { 'C' };

    private static final char[] CHARS_FF = { 'F', 'F' };

    private static final char[] CHARS_G = { 'G' };

    private static final char[] CHARS_N = { 'N' };

    private static final char[] CHARS_NN = { 'N', 'N' };

    private static final char[] CHARS_S = { 'S' };

    private static final char[] CHARS_SSS = { 'S', 'S', 'S' };

    private static final Pattern PAT_MAC = Pattern.compile("^MAC");

    private static final Pattern PAT_KN = Pattern.compile("^KN");

    private static final Pattern PAT_K = Pattern.compile("^K");

    private static final Pattern PAT_PH_PF = Pattern.compile("^(PH|PF)");

    private static final Pattern PAT_SCH = Pattern.compile("^SCH");

    private static final Pattern PAT_EE_IE = Pattern.compile("(EE|IE)$");

    private static final Pattern PAT_DT_ETC = Pattern.compile("(DT|RT|RD|NT|ND)$");

    private static final char SPACE = ' ';

    private static final int TRUE_LENGTH = 6;

    /**
     * Tests if the given character is a vowel.
     *
     * @param c
     *            the character to test.
     * @return {@code true} if the character is a vowel, {@code false} otherwise.
     */
    private static boolean isVowel(final char c) {
        return c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U';
    }

    /**
     * Transcodes the remaining parts of the String. The method operates on a sliding window, looking at 4 characters at
     * a time: [i-1, i, i+1, i+2].
     *
     * @param prev
     *            the previous character.
     * @param curr
     *            the current character.
     * @param next
     *            the next character.
     * @param aNext
     *            the after next character.
     * @return a transcoded array of characters, starting from the current position.
     */
    private static char[] transcodeRemaining(final char prev, final char curr, final char next, final char aNext) {
        // 1. EV -> AF
        if (curr == 'E' && next == 'V') {
            return CHARS_AF;
        }
        // A, E, I, O, U -> A
        if (isVowel(curr)) {
            return CHARS_A;
        }
        // 2. Q -> G, Z -> S, M -> N
        // 3. KN -> NN else K -> C
        switch(curr) {
            case 'Q':
                return CHARS_G;
            case 'Z':
                return CHARS_S;
            case 'M':
                return CHARS_N;
            case 'K':
                if (next == 'N') {
                    return CHARS_NN;
                }
                return CHARS_C;
            default:
                break;
        }
        // 4. SCH -> SSS
        if (curr == 'S' && next == 'C' && aNext == 'H') {
            return CHARS_SSS;
        }
        // PH -> FF
        if (curr == 'P' && next == 'H') {
            return CHARS_FF;
        }
        // 5. H -> If previous or next is a non vowel, previous.
        if (curr == 'H' && (!isVowel(prev) || !isVowel(next))) {
            return new char[] { prev };
        }
        // 6. W -> If previous is vowel, previous.
        if (curr == 'W' && isVowel(prev)) {
            return new char[] { prev };
        }
        return new char[] { curr };
    }

    /**
     * Indicates the strict mode.
     */
    private final boolean strict;

    /**
     * Creates an instance of the {@link Nysiis} encoder with strict mode (original form),
     * i.e. encoded strings have a maximum length of 6.
     */
    public Nysiis() {
        this(true);
    }

    /**
     * Create an instance of the {@link Nysiis} encoder with the specified strict mode:
     *
     * <ul>
     *  <li>{@code true}: encoded strings have a maximum length of 6</li>
     *  <li>{@code false}: encoded strings may have arbitrary length</li>
     * </ul>
     *
     * @param strict
     *            the strict mode.
     */
    public Nysiis(final boolean strict) {
        this.strict = strict;
    }

    @Override
    public Object encode(final Object obj) throws EncoderException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String encode(final String str) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean isStrict() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String nysiis(String str) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}

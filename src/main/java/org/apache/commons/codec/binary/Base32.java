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
package org.apache.commons.codec.binary;

import java.util.Arrays;
import org.apache.commons.codec.CodecPolicy;

/**
 * Provides Base32 encoding and decoding as defined by <a href="https://www.ietf.org/rfc/rfc4648.txt">RFC 4648</a>.
 *
 * <p>
 * The class can be parameterized in the following manner with various constructors:
 * </p>
 * <ul>
 * <li>Whether to use the "base32hex" variant instead of the default "base32"</li>
 * <li>Line length: Default 76. Line length that aren't multiples of 8 will still essentially end up being multiples of 8 in the encoded data.
 * <li>Line separator: Default is CRLF ("\r\n")</li>
 * </ul>
 * <p>
 * This class operates directly on byte streams, and not character streams.
 * </p>
 * <p>
 * This class is thread-safe.
 * </p>
 * <p>
 * To configure a new instance, use a {@link Builder}. For example:
 * </p>
 * <pre>
 * Base32 base32 = Base32.builder()
 *   .setDecodingPolicy(DecodingPolicy.LENIENT) // default is lenient
 *   .setLineLength(0)                          // default is none
 *   .setLineSeparator('\r', '\n')              // default is CR LF
 *   .setPadding('=')                           // default is '='
 *   .setEncodeTable(customEncodeTable)         // default is RFC 4648 Section 6, Table 3: The Base 32 Alphabet
 *   .get()
 * </pre>
 *
 * @see Base32InputStream
 * @see Base32OutputStream
 * @see <a href="https://www.ietf.org/rfc/rfc4648.txt">RFC 4648</a>
 * @since 1.5
 */
public class Base32 extends BaseNCodec {

    /**
     * Builds {@link Base32} instances.
     *
     * <p>
     * To configure a new instance, use a {@link Builder}. For example:
     * </p>
     *
     * <pre>
     * Base32 base32 = Base32.builder()
     *   .setDecodingPolicy(DecodingPolicy.LENIENT) // default is lenient
     *   .setLineLength(0)                          // default is none
     *   .setLineSeparator('\r', '\n')              // default is CR LF
     *   .setPadding('=')                           // default is '='
     *   .setEncodeTable(customEncodeTable)         // default is RFC 4648 Section 6, Table 3: The Base 32 Alphabet
     *   .get()
     * </pre>
     *
     * @since 1.17.0
     */
    public static class Builder extends AbstractBuilder<Base32, Builder> {

        /**
         * Constructs a new instance using <a href="https://datatracker.ietf.org/doc/html/rfc4648#section-6">RFC 4648 Section 6, Table 3: The Base 32
         * Alphabet</a>.
         */
        public Builder() {
            super(ENCODE_TABLE);
            setDecodeTableRaw(DECODE_TABLE);
            setEncodeTableRaw(ENCODE_TABLE);
            setEncodedBlockSize(BYTES_PER_ENCODED_BLOCK);
            setUnencodedBlockSize(BYTES_PER_UNENCODED_BLOCK);
        }

        @Override
        public Base32 get() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public Builder setEncodeTable(final byte... encodeTable) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public Builder setHexDecodeTable(final boolean useHex) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public Builder setHexEncodeTable(final boolean useHex) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * BASE32 characters are 5 bits in length. They are formed by taking a block of five octets to form a 40-bit string, which is converted into eight BASE32
     * characters.
     */
    private static final int BITS_PER_ENCODED_BYTE = 5;

    private static final int BYTES_PER_ENCODED_BLOCK = 8;

    private static final int BYTES_PER_UNENCODED_BLOCK = 5;

    /**
     * This array is a lookup table that translates Unicode characters drawn from the "Base32 Alphabet" (as specified in Table 3 of RFC 4648) into their 5-bit
     * positive integer equivalents. Characters that are not in the Base32 alphabet but fall within the bounds of the array are translated to -1.
     */
    // @formatter:off
    private static final byte[] DECODE_TABLE = { //  0   1   2   3   4   5   6   7   8   9   A   B   C   D   E   F
    // 00-0f
    -1, // 00-0f
    -1, // 00-0f
    -1, // 00-0f
    -1, // 00-0f
    -1, // 00-0f
    -1, // 00-0f
    -1, // 00-0f
    -1, // 00-0f
    -1, // 00-0f
    -1, // 00-0f
    -1, // 00-0f
    -1, // 00-0f
    -1, // 00-0f
    -1, // 00-0f
    -1, // 00-0f
    -1, // 10-1f
    -1, // 10-1f
    -1, // 10-1f
    -1, // 10-1f
    -1, // 10-1f
    -1, // 10-1f
    -1, // 10-1f
    -1, // 10-1f
    -1, // 10-1f
    -1, // 10-1f
    -1, // 10-1f
    -1, // 10-1f
    -1, // 10-1f
    -1, // 10-1f
    -1, // 10-1f
    -1, // 10-1f
    -1, // 20-2f
    -1, // 20-2f
    -1, // 20-2f
    -1, // 20-2f
    -1, // 20-2f
    -1, // 20-2f
    -1, // 20-2f
    -1, // 20-2f
    -1, // 20-2f
    -1, // 20-2f
    -1, // 20-2f
    -1, // 20-2f
    -1, // 20-2f
    -1, // 20-2f
    -1, // 20-2f
    -1, // 20-2f
    -1, // 30-3f 2-7
    -1, // 30-3f 2-7
    -1, // 30-3f 2-7
    26, // 30-3f 2-7
    27, // 30-3f 2-7
    28, // 30-3f 2-7
    29, // 30-3f 2-7
    30, // 30-3f 2-7
    31, // 30-3f 2-7
    -1, // 30-3f 2-7
    -1, // 30-3f 2-7
    -1, // 30-3f 2-7
    -1, // 30-3f 2-7
    -1, // 30-3f 2-7
    -1, // 30-3f 2-7
    -1, // 30-3f 2-7
    -1, // 40-4f A-O
    -1, // 40-4f A-O
    0, // 40-4f A-O
    1, // 40-4f A-O
    2, // 40-4f A-O
    3, // 40-4f A-O
    4, // 40-4f A-O
    5, // 40-4f A-O
    6, // 40-4f A-O
    7, // 40-4f A-O
    8, // 40-4f A-O
    9, // 40-4f A-O
    10, // 40-4f A-O
    11, // 40-4f A-O
    12, // 40-4f A-O
    13, // 40-4f A-O
    14, // 50-5a P-Z
    15, // 50-5a P-Z
    16, // 50-5a P-Z
    17, // 50-5a P-Z
    18, // 50-5a P-Z
    19, // 50-5a P-Z
    20, // 50-5a P-Z
    21, // 50-5a P-Z
    22, // 50-5a P-Z
    23, // 50-5a P-Z
    24, // 50-5a P-Z
    25, // 5b-5f
    -1, // 5b-5f
    -1, // 5b-5f
    -1, // 5b-5f
    -1, // 5b-5f
    -1, // 60-6f a-o
    -1, // 60-6f a-o
    0, // 60-6f a-o
    1, // 60-6f a-o
    2, // 60-6f a-o
    3, // 60-6f a-o
    4, // 60-6f a-o
    5, // 60-6f a-o
    6, // 60-6f a-o
    7, // 60-6f a-o
    8, // 60-6f a-o
    9, // 60-6f a-o
    10, // 60-6f a-o
    11, // 60-6f a-o
    12, // 60-6f a-o
    13, // 60-6f a-o
    14, // 70-7a p-z
    15, // 70-7a p-z
    16, // 70-7a p-z
    17, // 70-7a p-z
    18, // 70-7a p-z
    19, // 70-7a p-z
    20, // 70-7a p-z
    21, // 70-7a p-z
    22, // 70-7a p-z
    23, // 70-7a p-z
    24, // 70-7a p-z
    25 };

    // @formatter:on
    /**
     * This array is a lookup table that translates 5-bit positive integer index values into their "Base32 Alphabet" equivalents as specified in
     * <a href="https://datatracker.ietf.org/doc/html/rfc4648#section-6">RFC 4648 Section 6, Table 3: The Base 32 Alphabet</a>.
     *
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc4648#section-6">RFC 4648 Section 6, Table 3: The Base 32 Alphabet</a>
     */
    // @formatter:off
    private static final byte[] ENCODE_TABLE = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '2', '3', '4', '5', '6', '7' };

    // @formatter:on
    /**
     * This array is a lookup table that translates Unicode characters drawn from the "Base32 Hex Alphabet" (as specified in Table 4 of RFC 4648) into their
     * 5-bit positive integer equivalents. Characters that are not in the Base32 Hex alphabet but fall within the bounds of the array are translated to -1.
     */
    // @formatter:off
    private static final byte[] HEX_DECODE_TABLE = { //  0   1   2   3   4   5   6   7   8   9   A   B   C   D   E   F
    // 00-0f
    -1, // 00-0f
    -1, // 00-0f
    -1, // 00-0f
    -1, // 00-0f
    -1, // 00-0f
    -1, // 00-0f
    -1, // 00-0f
    -1, // 00-0f
    -1, // 00-0f
    -1, // 00-0f
    -1, // 00-0f
    -1, // 00-0f
    -1, // 00-0f
    -1, // 00-0f
    -1, // 00-0f
    -1, // 10-1f
    -1, // 10-1f
    -1, // 10-1f
    -1, // 10-1f
    -1, // 10-1f
    -1, // 10-1f
    -1, // 10-1f
    -1, // 10-1f
    -1, // 10-1f
    -1, // 10-1f
    -1, // 10-1f
    -1, // 10-1f
    -1, // 10-1f
    -1, // 10-1f
    -1, // 10-1f
    -1, // 10-1f
    -1, // 20-2f
    -1, // 20-2f
    -1, // 20-2f
    -1, // 20-2f
    -1, // 20-2f
    -1, // 20-2f
    -1, // 20-2f
    -1, // 20-2f
    -1, // 20-2f
    -1, // 20-2f
    -1, // 20-2f
    -1, // 20-2f
    -1, // 20-2f
    -1, // 20-2f
    -1, // 20-2f
    -1, // 20-2f
    -1, // 30-3f 0-9
    0, // 30-3f 0-9
    1, // 30-3f 0-9
    2, // 30-3f 0-9
    3, // 30-3f 0-9
    4, // 30-3f 0-9
    5, // 30-3f 0-9
    6, // 30-3f 0-9
    7, // 30-3f 0-9
    8, // 30-3f 0-9
    9, // 30-3f 0-9
    -1, // 30-3f 0-9
    -1, // 30-3f 0-9
    -1, // 30-3f 0-9
    -1, // 30-3f 0-9
    -1, // 30-3f 0-9
    -1, // 40-4f A-O
    -1, // 40-4f A-O
    10, // 40-4f A-O
    11, // 40-4f A-O
    12, // 40-4f A-O
    13, // 40-4f A-O
    14, // 40-4f A-O
    15, // 40-4f A-O
    16, // 40-4f A-O
    17, // 40-4f A-O
    18, // 40-4f A-O
    19, // 40-4f A-O
    20, // 40-4f A-O
    21, // 40-4f A-O
    22, // 40-4f A-O
    23, // 40-4f A-O
    24, // 50-56 P-V
    25, // 50-56 P-V
    26, // 50-56 P-V
    27, // 50-56 P-V
    28, // 50-56 P-V
    29, // 50-56 P-V
    30, // 50-56 P-V
    31, // 57-5f
    -1, // 57-5f
    -1, // 57-5f
    -1, // 57-5f
    -1, // 57-5f
    -1, // 57-5f
    -1, // 57-5f
    -1, // 57-5f
    -1, // 57-5f
    -1, // 60-6f a-o
    -1, // 60-6f a-o
    10, // 60-6f a-o
    11, // 60-6f a-o
    12, // 60-6f a-o
    13, // 60-6f a-o
    14, // 60-6f a-o
    15, // 60-6f a-o
    16, // 60-6f a-o
    17, // 60-6f a-o
    18, // 60-6f a-o
    19, // 60-6f a-o
    20, // 60-6f a-o
    21, // 60-6f a-o
    22, // 60-6f a-o
    23, // 60-6f a-o
    24, // 70-76 p-v
    25, // 70-76 p-v
    26, // 70-76 p-v
    27, // 70-76 p-v
    28, // 70-76 p-v
    29, // 70-76 p-v
    30, // 70-76 p-v
    31 };

    // @formatter:on
    /**
     * This array is a lookup table that translates 5-bit positive integer index values into their "Base 32 Encoding with Extended Hex Alphabet" equivalents as
     * specified in <a href="https://datatracker.ietf.org/doc/html/rfc4648#section-7">RFC 4648 Section 7, Table 4: Base 32 Encoding with Extended Hex
     * Alphabet</a>.
     *
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc4648#section-7">RFC 4648 Section 7, Table 4: Base 32 Encoding with Extended Hex Alphabet</a>
     */
    // @formatter:off
    private static final byte[] HEX_ENCODE_TABLE = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V' };

    // @formatter:on
    /**
     * Mask used to extract 5 bits, used when encoding Base32 bytes
     */
    private static final int MASK_5_BITS = 0x1f;

    /**
     * Mask used to extract 4 bits, used when decoding final trailing character.
     */
    private static final long MASK_4_BITS = 0x0fL;

    /**
     * Mask used to extract 3 bits, used when decoding final trailing character.
     */
    private static final long MASK_3_BITS = 0x07L;

    /**
     * Mask used to extract 2 bits, used when decoding final trailing character.
     */
    private static final long MASK_2_BITS = 0x03L;

    /**
     * Mask used to extract 1 bits, used when decoding final trailing character.
     */
    private static final long MASK_1_BITS = 0x01L;

    // The static final fields above are used for the original static byte[] methods on Base32.
    // The private member fields below are used with the new streaming approach, which requires
    // some state be preserved between calls of encode() and decode().
    public static Builder builder() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private static byte[] decodeTable(final boolean useHex) {
        return useHex ? HEX_DECODE_TABLE : DECODE_TABLE;
    }

    /**
     * Gets the encoding table that matches {@code useHex}.
     *
     * @param useHex
     *               <ul>
     *               <li>If true, then use <a href="https://datatracker.ietf.org/doc/html/rfc4648#section-7">RFC 4648 Section 7, Table 4: Base 32 Encoding with
     *               Extended Hex Alphabet</a></li>
     *               <li>If false, then use <a href="https://datatracker.ietf.org/doc/html/rfc4648#section-6">RFC 4648 Section 6, Table 3: The Base 32
     *               Alphabet</a></li>
     *               </ul>
     * @return the encoding table that matches {@code useHex}.
     */
    private static byte[] encodeTable(final boolean useHex) {
        return useHex ? HEX_ENCODE_TABLE : ENCODE_TABLE;
    }

    /**
     * Convenience variable to help us determine when our buffer is going to run out of room and needs resizing. {@code encodeSize = {@link
     * #BYTES_PER_ENCODED_BLOCK} + lineSeparator.length;}
     */
    private final int encodeSize;

    /**
     * Line separator for encoding. Not used when decoding. Only used if lineLength &gt; 0.
     */
    private final byte[] lineSeparator;

    /**
     * Constructs a Base32 codec used for decoding and encoding.
     * <p>
     * When encoding the line length is 0 (no chunking).
     * </p>
     */
    public Base32() {
        this(false);
    }

    /**
     * Constructs a Base32 codec used for decoding and encoding.
     * <p>
     * When encoding the line length is 0 (no chunking).
     * </p>
     *
     * @param useHex
     *               <ul>
     *               <li>If true, then use <a href="https://datatracker.ietf.org/doc/html/rfc4648#section-7">RFC 4648 Section 7, Table 4: Base 32 Encoding with
     *               Extended Hex Alphabet</a></li>
     *               <li>If false, then use <a href="https://datatracker.ietf.org/doc/html/rfc4648#section-6">RFC 4648 Section 6, Table 3: The Base 32
     *               Alphabet</a></li>
     *               </ul>
     * @deprecated Use {@link #builder()} and {@link Builder}.
     */
    @Deprecated
    public Base32(final boolean useHex) {
        this(0, null, useHex, PAD_DEFAULT);
    }

    /**
     * Constructs a Base32 codec used for decoding and encoding.
     * <p>
     * When encoding the line length is 0 (no chunking).
     * </p>
     *
     * @param useHex
     *               <ul>
     *               <li>If true, then use <a href="https://datatracker.ietf.org/doc/html/rfc4648#section-7">RFC 4648 Section 7, Table 4: Base 32 Encoding with
     *               Extended Hex Alphabet</a></li>
     *               <li>If false, then use <a href="https://datatracker.ietf.org/doc/html/rfc4648#section-6">RFC 4648 Section 6, Table 3: The Base 32
     *               Alphabet</a></li>
     *               </ul>
     * @param padding byte used as padding byte.
     * @deprecated Use {@link #builder()} and {@link Builder}.
     */
    @Deprecated
    public Base32(final boolean useHex, final byte padding) {
        this(0, null, useHex, padding);
    }

    private Base32(final Builder builder) {
        super(builder);
        if (builder.getLineLength() > 0) {
            final byte[] lineSeparator = builder.getLineSeparator();
            // Must be done after initializing the tables
            if (containsAlphabetOrPad(lineSeparator)) {
                final String sep = StringUtils.newStringUtf8(lineSeparator);
                throw new IllegalArgumentException("lineSeparator must not contain Base32 characters: [" + sep + "]");
            }
            this.encodeSize = BYTES_PER_ENCODED_BLOCK + lineSeparator.length;
            this.lineSeparator = lineSeparator;
        } else {
            this.encodeSize = BYTES_PER_ENCODED_BLOCK;
            this.lineSeparator = null;
        }
        if (isInAlphabet(builder.getPadding()) || Character.isWhitespace(builder.getPadding())) {
            throw new IllegalArgumentException("pad must not be in alphabet or whitespace");
        }
    }

    /**
     * Constructs a Base32 codec used for decoding and encoding.
     * <p>
     * When encoding the line length is 0 (no chunking).
     * </p>
     *
     * @param pad byte used as padding byte.
     * @deprecated Use {@link #builder()} and {@link Builder}.
     */
    @Deprecated
    public Base32(final byte pad) {
        this(false, pad);
    }

    /**
     * Constructs a Base32 codec used for decoding and encoding.
     * <p>
     * When encoding the line length is given in the constructor, the line separator is CRLF.
     * </p>
     *
     * @param lineLength Each line of encoded data will be at most of the given length (rounded down to the nearest multiple of 8). If lineLength &lt;= 0, then
     *                   the output will not be divided into lines (chunks). Ignored when decoding.
     * @deprecated Use {@link #builder()} and {@link Builder}.
     */
    @Deprecated
    public Base32(final int lineLength) {
        this(lineLength, CHUNK_SEPARATOR);
    }

    /**
     * Constructs a Base32 codec used for decoding and encoding.
     * <p>
     * When encoding the line length and line separator are given in the constructor.
     * </p>
     * <p>
     * Line lengths that aren't multiples of 8 will still essentially end up being multiples of 8 in the encoded data.
     * </p>
     *
     * @param lineLength    Each line of encoded data will be at most of the given length (rounded down to the nearest multiple of 8). If lineLength &lt;= 0,
     *                      then the output will not be divided into lines (chunks). Ignored when decoding.
     * @param lineSeparator Each line of encoded data will end with this sequence of bytes.
     * @throws IllegalArgumentException Thrown when the {@code lineSeparator} contains Base32 characters.
     * @deprecated Use {@link #builder()} and {@link Builder}.
     */
    @Deprecated
    public Base32(final int lineLength, final byte[] lineSeparator) {
        this(lineLength, lineSeparator, false, PAD_DEFAULT);
    }

    /**
     * Constructs a Base32 / Base32 Hex codec used for decoding and encoding.
     * <p>
     * When encoding the line length and line separator are given in the constructor.
     * </p>
     * <p>
     * Line lengths that aren't multiples of 8 will still essentially end up being multiples of 8 in the encoded data.
     * </p>
     *
     * @param lineLength    Each line of encoded data will be at most of the given length (rounded down to the nearest multiple of 8). If lineLength &lt;= 0,
     *                      then the output will not be divided into lines (chunks). Ignored when decoding.
     * @param lineSeparator Each line of encoded data will end with this sequence of bytes.
     * @param useHex
     *               <ul>
     *               <li>If true, then use <a href="https://datatracker.ietf.org/doc/html/rfc4648#section-7">RFC 4648 Section 7, Table 4: Base 32 Encoding with
     *               Extended Hex Alphabet</a></li>
     *               <li>If false, then use <a href="https://datatracker.ietf.org/doc/html/rfc4648#section-6">RFC 4648 Section 6, Table 3: The Base 32
     *               Alphabet</a></li>
     *               </ul>
     * @throws IllegalArgumentException Thrown when the {@code lineSeparator} contains Base32 characters. Or the lineLength &gt; 0 and lineSeparator is null.
     * @deprecated Use {@link #builder()} and {@link Builder}.
     */
    @Deprecated
    public Base32(final int lineLength, final byte[] lineSeparator, final boolean useHex) {
        this(lineLength, lineSeparator, useHex, PAD_DEFAULT);
    }

    /**
     * Constructs a Base32 / Base32 Hex codec used for decoding and encoding.
     * <p>
     * When encoding the line length and line separator are given in the constructor.
     * </p>
     * <p>
     * Line lengths that aren't multiples of 8 will still essentially end up being multiples of 8 in the encoded data.
     * </p>
     *
     * @param lineLength    Each line of encoded data will be at most of the given length (rounded down to the nearest multiple of 8). If lineLength &lt;= 0,
     *                      then the output will not be divided into lines (chunks). Ignored when decoding.
     * @param lineSeparator Each line of encoded data will end with this sequence of bytes.
     * @param useHex
     *               <ul>
     *               <li>If true, then use <a href="https://datatracker.ietf.org/doc/html/rfc4648#section-7">RFC 4648 Section 7, Table 4: Base 32 Encoding with
     *               Extended Hex Alphabet</a></li>
     *               <li>If false, then use <a href="https://datatracker.ietf.org/doc/html/rfc4648#section-6">RFC 4648 Section 6, Table 3: The Base 32
     *               Alphabet</a></li>
     *               </ul>
     * @param padding       padding byte.
     * @throws IllegalArgumentException Thrown when the {@code lineSeparator} contains Base32 characters. Or the lineLength &gt; 0 and lineSeparator is null.
     * @deprecated Use {@link #builder()} and {@link Builder}.
     */
    @Deprecated
    public Base32(final int lineLength, final byte[] lineSeparator, final boolean useHex, final byte padding) {
        this(lineLength, lineSeparator, useHex, padding, DECODING_POLICY_DEFAULT);
    }

    /**
     * Constructs a Base32 / Base32 Hex codec used for decoding and encoding.
     * <p>
     * When encoding the line length and line separator are given in the constructor.
     * </p>
     * <p>
     * Line lengths that aren't multiples of 8 will still essentially end up being multiples of 8 in the encoded data.
     * </p>
     *
     * @param lineLength     Each line of encoded data will be at most of the given length (rounded down to the nearest multiple of 8). If lineLength &lt;= 0,
     *                       then the output will not be divided into lines (chunks). Ignored when decoding.
     * @param lineSeparator  Each line of encoded data will end with this sequence of bytes.
     * @param useHex
     *               <ul>
     *               <li>If true, then use <a href="https://datatracker.ietf.org/doc/html/rfc4648#section-7">RFC 4648 Section 7, Table 4: Base 32 Encoding with
     *               Extended Hex Alphabet</a></li>
     *               <li>If false, then use <a href="https://datatracker.ietf.org/doc/html/rfc4648#section-6">RFC 4648 Section 6, Table 3: The Base 32
     *               Alphabet</a></li>
     *               </ul>
     * @param padding        padding byte.
     * @param decodingPolicy The decoding policy.
     * @throws IllegalArgumentException Thrown when the {@code lineSeparator} contains Base32 characters. Or the lineLength &gt; 0 and lineSeparator is null.
     * @since 1.15
     * @deprecated Use {@link #builder()} and {@link Builder}.
     */
    @Deprecated
    public Base32(final int lineLength, final byte[] lineSeparator, final boolean useHex, final byte padding, final CodecPolicy decodingPolicy) {
        // @formatter:off
        this(builder().setLineLength(lineLength).setLineSeparator(lineSeparator != null ? lineSeparator : EMPTY_BYTE_ARRAY).setDecodeTable(decodeTable(useHex)).setEncodeTableRaw(encodeTable(useHex)).setPadding(padding).setDecodingPolicy(decodingPolicy));
        // @formatter:on
    }

    @Override
    void decode(final byte[] input, int inPos, final int inAvail, final Context context) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    void encode(final byte[] input, int inPos, final int inAvail, final Context context) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    byte[] getLineSeparator() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public boolean isInAlphabet(final byte octet) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Validates whether decoding the final trailing character is possible in the context of the set of possible Base32 values.
     * <p>
     * The character is valid if the lower bits within the provided mask are zero. This is used to test the final trailing base-32 digit is zero in the bits
     * that will be discarded.
     * </p>
     *
     * @param emptyBitsMask The mask of the lower bits that should be empty.
     * @param context       the context to be used.
     * @throws IllegalArgumentException if the bits being checked contain any non-zero value.
     */
    private void validateCharacter(final long emptyBitsMask, final Context context) {
        // Use the long bit work area
        if (isStrictDecoding() && (context.lbitWorkArea & emptyBitsMask) != 0) {
            throw new IllegalArgumentException("Strict decoding: Last encoded character (before the paddings if any) is a valid " + "Base32 alphabet but not a possible encoding. Expected the discarded bits from the character to be zero.");
        }
    }

    /**
     * Validates whether decoding allows final trailing characters that cannot be created during encoding.
     *
     * @throws IllegalArgumentException if strict decoding is enabled.
     */
    private void validateTrailingCharacters() {
        if (isStrictDecoding()) {
            throw new IllegalArgumentException("Strict decoding: Last encoded character(s) (before the paddings if any) are valid " + "Base32 alphabet but not a possible encoding. Decoding requires either 2, 4, 5, or 7 trailing 5-bit characters to create bytes.");
        }
    }
}

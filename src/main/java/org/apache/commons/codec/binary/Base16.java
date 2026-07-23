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
 * Provides Base16 encoding and decoding as defined by <a href="https://tools.ietf.org/html/rfc4648#section-8">RFC 4648 - 8. Base 16 Encoding</a>.
 *
 * <p>
 * This class is thread-safe.
 * </p>
 * <p>
 * This implementation strictly follows RFC 4648, and as such unlike the {@link Base32} and {@link Base64} implementations, it does not ignore invalid alphabet
 * characters or whitespace, neither does it offer chunking or padding characters.
 * </p>
 * <p>
 * The only additional feature above those specified in RFC 4648 is support for working with a lower-case alphabet in addition to the default upper-case
 * alphabet.
 * </p>
 *
 * @see Base16InputStream
 * @see Base16OutputStream
 * @see <a href="https://tools.ietf.org/html/rfc4648#section-8">RFC 4648 - 8. Base 16 Encoding</a>
 * @since 1.15
 */
public class Base16 extends BaseNCodec {

    /**
     * Builds {@link Base16} instances.
     *
     * <p>
     * To configure a new instance, use a {@link Builder}. For example:
     * </p>
     *
     * <pre>
     * Base16 Base16 = Base16.builder()
     *   .setDecodingPolicy(DecodingPolicy.LENIENT) // default is lenient
     *   .get()
     * </pre>
     *
     * @since 1.20.0
     */
    public static class Builder extends AbstractBuilder<Base16, Builder> {

        /**
         * Constructs a new instance.
         */
        public Builder() {
            super(null);
            setDecodeTable(UPPER_CASE_DECODE_TABLE);
            setEncodeTable(UPPER_CASE_ENCODE_TABLE);
            setEncodedBlockSize(BYTES_PER_ENCODED_BLOCK);
            setUnencodedBlockSize(BYTES_PER_UNENCODED_BLOCK);
            setLineLength(0);
            setLineSeparator(EMPTY_BYTE_ARRAY);
        }

        @Override
        public Base16 get() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public Builder setEncodeTable(final byte... encodeTable) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public Builder setLowerCase(final boolean lowerCase) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * BASE16 characters are 4 bits in length. They are formed by taking an 8-bit group, which is converted into two BASE16 characters.
     */
    private static final int BITS_PER_ENCODED_BYTE = 4;

    private static final int BYTES_PER_ENCODED_BLOCK = 2;

    private static final int BYTES_PER_UNENCODED_BLOCK = 1;

    /**
     * This array is a lookup table that translates Unicode characters drawn from the "Base16 Alphabet" (as specified in Table 5 of RFC 4648) into their 4-bit
     * positive integer equivalents. Characters that are not in the Base16 alphabet but fall within the bounds of the array are translated to -1.
     */
    // @formatter:off
    private static final byte[] UPPER_CASE_DECODE_TABLE = { //  0   1   2   3   4   5   6   7   8   9   A   B   C   D   E   F
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
    -1, // 40-46 A-F
    -1, // 40-46 A-F
    10, // 40-46 A-F
    11, // 40-46 A-F
    12, // 40-46 A-F
    13, // 40-46 A-F
    14, // 40-46 A-F
    15 };

    // @formatter:on
    /**
     * This array is a lookup table that translates 4-bit positive integer index values into their "Base16 Alphabet" equivalents as specified in Table 5 of RFC
     * 4648.
     */
    private static final byte[] UPPER_CASE_ENCODE_TABLE = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

    /**
     * This array is a lookup table that translates Unicode characters drawn from the a lower-case "Base16 Alphabet" into their 4-bit positive integer
     * equivalents. Characters that are not in the Base16 alphabet but fall within the bounds of the array are translated to -1.
     */
    // @formatter:off
    private static final byte[] LOWER_CASE_DECODE_TABLE = { //  0   1   2   3   4   5   6   7   8   9   A   B   C   D   E   F
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
    -1, // 40-4f
    -1, // 40-4f
    -1, // 40-4f
    -1, // 40-4f
    -1, // 40-4f
    -1, // 40-4f
    -1, // 40-4f
    -1, // 40-4f
    -1, // 40-4f
    -1, // 40-4f
    -1, // 40-4f
    -1, // 40-4f
    -1, // 40-4f
    -1, // 40-4f
    -1, // 40-4f
    -1, // 40-4f
    -1, // 50-5f
    -1, // 50-5f
    -1, // 50-5f
    -1, // 50-5f
    -1, // 50-5f
    -1, // 50-5f
    -1, // 50-5f
    -1, // 50-5f
    -1, // 50-5f
    -1, // 50-5f
    -1, // 50-5f
    -1, // 50-5f
    -1, // 50-5f
    -1, // 50-5f
    -1, // 50-5f
    -1, // 50-5f
    -1, // 60-66 a-f
    -1, // 60-66 a-f
    10, // 60-66 a-f
    11, // 60-66 a-f
    12, // 60-66 a-f
    13, // 60-66 a-f
    14, // 60-66 a-f
    15 };

    // @formatter:on
    /**
     * This array is a lookup table that translates 4-bit positive integer index values into their "Base16 Alphabet" lower-case equivalents.
     */
    private static final byte[] LOWER_CASE_ENCODE_TABLE = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    /**
     * Mask used to extract 4 bits, used when decoding character.
     */
    private static final int MASK_4_BITS = 0x0f;

    public static Builder builder() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Constructs a Base16 codec used for decoding and encoding.
     */
    public Base16() {
        this(false);
    }

    /**
     * Constructs a Base16 codec used for decoding and encoding.
     *
     * @param lowerCase {@code true} to use the lower-case Base16 alphabet.
     * @deprecated Use {@link #builder()} and {@link Builder}.
     */
    @Deprecated
    public Base16(final boolean lowerCase) {
        this(lowerCase, DECODING_POLICY_DEFAULT);
    }

    /**
     * Constructs a Base16 codec used for decoding and encoding.
     *
     * @param lowerCase      {@code true} to use the lower-case Base16 alphabet.
     * @param decodingPolicy Decoding policy.
     * @deprecated Use {@link #builder()} and {@link Builder}.
     */
    @Deprecated
    public Base16(final boolean lowerCase, final CodecPolicy decodingPolicy) {
        this(builder().setEncodeTable(lowerCase ? LOWER_CASE_ENCODE_TABLE : UPPER_CASE_ENCODE_TABLE).setDecodingPolicy(decodingPolicy));
    }

    private Base16(final Builder builder) {
        super(builder);
    }

    @Override
    void decode(final byte[] data, int offset, final int length, final Context context) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private int decodeOctet(final byte octet) {
        int decoded = -1;
        if ((octet & 0xff) < decodeTable.length) {
            decoded = decodeTable[octet];
        }
        if (decoded == -1) {
            throw new IllegalArgumentException("Invalid octet in encoded value: " + (int) octet);
        }
        return decoded;
    }

    @Override
    void encode(final byte[] data, final int offset, final int length, final Context context) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public boolean isInAlphabet(final byte octet) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Validates whether decoding allows an entire final trailing character that cannot be used for a complete byte.
     *
     * @throws IllegalArgumentException if strict decoding is enabled.
     */
    private void validateTrailingCharacter() {
        if (isStrictDecoding()) {
            throw new IllegalArgumentException("Strict decoding: Last encoded character is a valid Base 16 alphabet character but not a possible encoding. " + "Decoding requires at least two characters to create one byte.");
        }
    }
}

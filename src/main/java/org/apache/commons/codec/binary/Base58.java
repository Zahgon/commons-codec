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

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

/**
 * Provides Base58 encoding and decoding as commonly used in cryptocurrency and blockchain applications.
 * <p>
 * Base58 is a binary-to-text encoding scheme that uses a 58-character alphabet to encode data. It avoids characters that can be confused (0/O, I/l, +/) and is
 * commonly used in Bitcoin and other blockchain systems.
 * </p>
 * <p>
 * This implementation accumulates data internally until EOF is signaled, at which point the entire input is converted using BigInteger arithmetic. This is
 * necessary because Base58 encoding/decoding requires access to the complete data to properly handle leading zeros.
 * </p>
 * <p>
 * This class is thread-safe for read operations but the Context object used during encoding/decoding should not be shared between threads.
 * </p>
 * <p>
 * The Base58 alphabet is:
 * </p>
 *
 * <pre>
 * 123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz
 * </pre>
 * <p>
 * This excludes: {@code 0}, {@code I}, {@code O}, and {@code l}.
 * </p>
 *
 * @see Base58InputStream
 * @see Base58OutputStream
 * @see <a href="https://datatracker.ietf.org/doc/html/draft-msporny-base58-03">The Base58 Encoding Scheme draft-msporny-base58-03</a>
 * @since 1.22.0
 */
public class Base58 extends BaseNCodec {

    /**
     * Builds {@link Base58} instances with custom configuration.
     */
    public static class Builder extends AbstractBuilder<Base58, Builder> {

        /**
         * Constructs a new Base58 builder.
         */
        public Builder() {
            super(ENCODE_TABLE);
            setDecodeTable(DECODE_TABLE);
        }

        @Override
        public Base58 get() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public Base58.Builder setEncodeTable(final byte... encodeTable) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    private static final BigInteger BASE = BigInteger.valueOf(58);

    private static final byte[] EMPTY = new byte[0];

    /**
     * Base58 alphabet: 123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz
     * (excludes: 0, I, O, l).
     */
    private static final byte[] ENCODE_TABLE = { '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

    /**
     * This array is a lookup table that translates Unicode characters drawn from the "Base58 Alphabet"
     * into their numeric equivalents (0-57). Characters that are not in the Base58 alphabet are marked
     * with -1.
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
    -1, // 30-3f '1'-'9' -> 0-8
    -1, // 30-3f '1'-'9' -> 0-8
    0, // 30-3f '1'-'9' -> 0-8
    1, // 30-3f '1'-'9' -> 0-8
    2, // 30-3f '1'-'9' -> 0-8
    3, // 30-3f '1'-'9' -> 0-8
    4, // 30-3f '1'-'9' -> 0-8
    5, // 30-3f '1'-'9' -> 0-8
    6, // 30-3f '1'-'9' -> 0-8
    7, // 30-3f '1'-'9' -> 0-8
    8, // 30-3f '1'-'9' -> 0-8
    -1, // 30-3f '1'-'9' -> 0-8
    -1, // 30-3f '1'-'9' -> 0-8
    -1, // 30-3f '1'-'9' -> 0-8
    -1, // 30-3f '1'-'9' -> 0-8
    -1, // 30-3f '1'-'9' -> 0-8
    -1, // 40-4f 'A'-'N', 'P'-'Z' (skip 'I' and 'O')
    -1, // 40-4f 'A'-'N', 'P'-'Z' (skip 'I' and 'O')
    9, // 40-4f 'A'-'N', 'P'-'Z' (skip 'I' and 'O')
    10, // 40-4f 'A'-'N', 'P'-'Z' (skip 'I' and 'O')
    11, // 40-4f 'A'-'N', 'P'-'Z' (skip 'I' and 'O')
    12, // 40-4f 'A'-'N', 'P'-'Z' (skip 'I' and 'O')
    13, // 40-4f 'A'-'N', 'P'-'Z' (skip 'I' and 'O')
    14, // 40-4f 'A'-'N', 'P'-'Z' (skip 'I' and 'O')
    15, // 40-4f 'A'-'N', 'P'-'Z' (skip 'I' and 'O')
    16, // 40-4f 'A'-'N', 'P'-'Z' (skip 'I' and 'O')
    -1, // 40-4f 'A'-'N', 'P'-'Z' (skip 'I' and 'O')
    17, // 40-4f 'A'-'N', 'P'-'Z' (skip 'I' and 'O')
    18, // 40-4f 'A'-'N', 'P'-'Z' (skip 'I' and 'O')
    19, // 40-4f 'A'-'N', 'P'-'Z' (skip 'I' and 'O')
    20, // 40-4f 'A'-'N', 'P'-'Z' (skip 'I' and 'O')
    21, // 40-4f 'A'-'N', 'P'-'Z' (skip 'I' and 'O')
    -1, // 50-5a 'P'-'Z'
    22, // 50-5a 'P'-'Z'
    23, // 50-5a 'P'-'Z'
    24, // 50-5a 'P'-'Z'
    25, // 50-5a 'P'-'Z'
    26, // 50-5a 'P'-'Z'
    27, // 50-5a 'P'-'Z'
    28, // 50-5a 'P'-'Z'
    29, // 50-5a 'P'-'Z'
    30, // 50-5a 'P'-'Z'
    31, // 50-5a 'P'-'Z'
    32, // 5b-5f
    -1, // 5b-5f
    -1, // 5b-5f
    -1, // 5b-5f
    -1, // 5b-5f
    -1, // 60-6f 'a'-'k', 'm'-'o' (skip 'l')
    -1, // 60-6f 'a'-'k', 'm'-'o' (skip 'l')
    33, // 60-6f 'a'-'k', 'm'-'o' (skip 'l')
    34, // 60-6f 'a'-'k', 'm'-'o' (skip 'l')
    35, // 60-6f 'a'-'k', 'm'-'o' (skip 'l')
    36, // 60-6f 'a'-'k', 'm'-'o' (skip 'l')
    37, // 60-6f 'a'-'k', 'm'-'o' (skip 'l')
    38, // 60-6f 'a'-'k', 'm'-'o' (skip 'l')
    39, // 60-6f 'a'-'k', 'm'-'o' (skip 'l')
    40, // 60-6f 'a'-'k', 'm'-'o' (skip 'l')
    41, // 60-6f 'a'-'k', 'm'-'o' (skip 'l')
    42, // 60-6f 'a'-'k', 'm'-'o' (skip 'l')
    43, // 60-6f 'a'-'k', 'm'-'o' (skip 'l')
    -1, // 60-6f 'a'-'k', 'm'-'o' (skip 'l')
    44, // 60-6f 'a'-'k', 'm'-'o' (skip 'l')
    45, // 60-6f 'a'-'k', 'm'-'o' (skip 'l')
    46, // 70-7a 'p'-'z'
    47, // 70-7a 'p'-'z'
    48, // 70-7a 'p'-'z'
    49, // 70-7a 'p'-'z'
    50, // 70-7a 'p'-'z'
    51, // 70-7a 'p'-'z'
    52, // 70-7a 'p'-'z'
    53, // 70-7a 'p'-'z'
    54, // 70-7a 'p'-'z'
    55, // 70-7a 'p'-'z'
    56, // 70-7a 'p'-'z'
    57 };

    // @formatter:on
    public static Builder builder() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Constructs a Base58 codec used for encoding and decoding.
     */
    public Base58() {
        this(new Builder());
    }

    /**
     * Constructs a Base58 codec used for encoding and decoding with custom configuration.
     *
     * @param builder the builder with custom configuration.
     */
    public Base58(final Builder builder) {
        super(builder);
    }

    /**
     * Converts Base58 encoded data to binary.
     * <p>
     * Uses BigInteger arithmetic to convert the Base58 string to binary data. Leading '1' characters in the Base58 encoding represent leading zero bytes in the
     * binary data.
     * </p>
     *
     * @param base58 the Base58 encoded data.
     * @param context    the context for this decoding operation.
     * @throws IllegalArgumentException if the Base58 data contains invalid characters.
     */
    private void convertFromBase58(final byte[] base58, final Context context) {
        BigInteger value = BigInteger.ZERO;
        int leadingOnes = 0;
        for (final byte b : base58) {
            if (b != '1') {
                break;
            }
            leadingOnes++;
        }
        BigInteger power = BigInteger.ONE;
        for (int i = base58.length - 1; i >= leadingOnes; i--) {
            final byte b = base58[i];
            final int digit = b < DECODE_TABLE.length ? DECODE_TABLE[b] : -1;
            if (digit < 0) {
                throw new IllegalArgumentException(String.format("Invalid character in Base58 string: 0x%02x", b));
            }
            value = value.add(BigInteger.valueOf(digit).multiply(power));
            power = power.multiply(BASE);
        }
        byte[] decoded = value.equals(BigInteger.ZERO) ? EMPTY : value.toByteArray();
        if (decoded.length > 1 && decoded[0] == 0) {
            final byte[] tmp = new byte[decoded.length - 1];
            System.arraycopy(decoded, 1, tmp, 0, tmp.length);
            decoded = tmp;
        }
        final byte[] result = new byte[leadingOnes + decoded.length];
        System.arraycopy(decoded, 0, result, leadingOnes, decoded.length);
        final byte[] buffer = ensureBufferSize(result.length, context);
        System.arraycopy(result, 0, buffer, context.pos, result.length);
        context.pos += result.length;
    }

    /**
     * Converts accumulated binary data to Base58 encoding.
     * <p>
     * Uses BigInteger arithmetic to convert the binary data to Base58. Leading zeros in the binary data are represented as '1' characters in the Base58
     * encoding.
     * </p>
     *
     * @param accumulate the binary data to encode.
     * @param context    the context for this encoding operation.
     * @return the buffer containing the encoded data.
     */
    private byte[] convertToBase58(final byte[] accumulate, final Context context) {
        final StringBuilder base58 = getStringBuilder(accumulate);
        final String encoded = base58.reverse().toString();
        final byte[] encodedBytes = encoded.getBytes(StandardCharsets.UTF_8);
        final byte[] buffer = ensureBufferSize(encodedBytes.length, context);
        System.arraycopy(encodedBytes, 0, buffer, context.pos, encodedBytes.length);
        context.pos += encodedBytes.length;
        return buffer;
    }

    @Override
    void decode(final byte[] array, final int offset, final int length, final Context context) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    void encode(final byte[] array, final int offset, final int length, final Context context) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Builds the Base58 string representation of the given binary data.
     * <p>
     * Converts binary data to a BigInteger and divides by 58 repeatedly to get the Base58 digits. Handles leading zeros by counting them and appending '1' for
     * each leading zero byte.
     * </p>
     *
     * @param accumulate the binary data to convert.
     * @return a StringBuilder with the Base58 representation (not yet reversed).
     */
    private StringBuilder getStringBuilder(final byte[] accumulate) {
        BigInteger value = new BigInteger(1, accumulate);
        int leadingZeros = 0;
        for (final byte b : accumulate) {
            if (b != 0) {
                break;
            }
            leadingZeros++;
        }
        final StringBuilder base58 = new StringBuilder();
        while (value.signum() > 0) {
            final BigInteger[] divRem = value.divideAndRemainder(BASE);
            base58.append((char) ENCODE_TABLE[divRem[1].intValue()]);
            value = divRem[0];
        }
        for (int i = 0; i < leadingZeros; i++) {
            base58.append('1');
        }
        return base58;
    }

    @Override
    protected boolean isInAlphabet(final byte value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}

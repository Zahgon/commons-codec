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
import java.util.Arrays;
import java.util.Objects;
import org.apache.commons.codec.CodecPolicy;

/**
 * Provides Base64 encoding and decoding as defined by <a href="https://www.ietf.org/rfc/rfc2045">RFC 2045 Multipurpose Internet Mail Extensions (MIME) Part
 * One: Format of Internet Message Bodies</a> and portions of <a href="https://datatracker.ietf.org/doc/html/rfc4648">RFC 4648 The Base16, Base32, and Base64
 * Data Encodings</a>
 *
 * <p>
 * This class implements <a href="https://www.ietf.org/rfc/rfc2045#section-6.8">RFC 2045 6.8. Base64 Content-Transfer-Encoding</a>.
 * </p>
 * <p>
 * The class can be parameterized in the following manner with its {@link Builder}:
 * </p>
 * <ul>
 * <li>URL-safe mode: Default off.</li>
 * <li>Line length: Default 76. Line length that aren't multiples of 4 will still essentially end up being multiples of 4 in the encoded data.
 * <li>Line separator: Default is CRLF ({@code "\r\n"})</li>
 * <li>Strict or lenient decoding policy; default is {@link CodecPolicy#LENIENT}.</li>
 * <li>Custom decoding table.</li>
 * <li>Custom encoding table.</li>
 * <li>Padding; defaults is {@code '='}.</li>
 * </ul>
 * <p>
 * The URL-safe parameter is only applied to encode operations. Decoding seamlessly handles both modes, see also
 * {@code Builder#setDecodeTableFormat(DecodeTableFormat)}.
 * </p>
 * <p>
 * Since this class operates directly on byte streams, and not character streams, it is hard-coded to only encode/decode character encodings which are
 * compatible with the lower 127 ASCII chart (ISO-8859-1, Windows-1252, UTF-8, etc).
 * </p>
 * <p>
 * This class is thread-safe.
 * </p>
 * <p>
 * To configure a new instance, use a {@link Builder}. For example:
 * </p>
 *
 * <pre>
 * Base64 base64 = Base64.builder()
 *   .setDecodingPolicy(CodecPolicy.LENIENT)    // default is lenient, null resets to default
 *   .setEncodeTable(customEncodeTable)         // default is built in, null resets to default
 *   .setLineLength(0)                          // default is none
 *   .setLineSeparator('\r', '\n')              // default is CR LF, null resets to default
 *   .setPadding('=')                           // default is '='
 *   .setUrlSafe(false)                         // default is false
 *   .get()
 * </pre>
 *
 * @see Base64InputStream
 * @see Base64OutputStream
 * @see <a href="https://www.ietf.org/rfc/rfc2045">RFC 2045 Multipurpose Internet Mail Extensions (MIME) Part One: Format of Internet Message Bodies</a>
 * @see <a href="https://datatracker.ietf.org/doc/html/rfc4648">RFC 4648 The Base16, Base32, and Base64 Data Encodings</a>
 * @since 1.0
 */
public class Base64 extends BaseNCodec {

    /**
     * Builds {@link Base64} instances.
     *
     * <p>
     * To configure a new instance, use a {@link Builder}. For example:
     * </p>
     *
     * <pre>
     * Base64 base64 = Base64.builder()
     *   .setCodecPolicy(CodecPolicy.LENIENT)       // default is lenient, null resets to default
     *   .setEncodeTable(customEncodeTable)         // default is built in, null resets to default
     *   .setLineLength(0)                          // default is none
     *   .setLineSeparator('\r', '\n')              // default is CR LF, null resets to default
     *   .setPadding('=')                           // default is '='
     *   .setUrlSafe(false)                         // default is false
     *   .get()
     * </pre>
     *
     * @since 1.17.0
     */
    public static class Builder extends AbstractBuilder<Base64, Builder> {

        /**
         * Constructs a new instance.
         */
        public Builder() {
            super(STANDARD_ENCODE_TABLE);
            setDecodeTableRaw(DECODE_TABLE);
            setEncodeTableRaw(STANDARD_ENCODE_TABLE);
            setEncodedBlockSize(BYTES_PER_ENCODED_BLOCK);
            setUnencodedBlockSize(BYTES_PER_UNENCODED_BLOCK);
        }

        @Override
        public Base64 get() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public Builder setDecodeTableFormat(final DecodeTableFormat format) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public Builder setEncodeTable(final byte... encodeTable) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public Builder setUrlSafe(final boolean urlSafe) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * Enumerates the Base64 table format to be used on decoding.
     * <p>
     * By default, the method uses {@link DecodeTableFormat#MIXED} approach, allowing a seamless handling of both {@link DecodeTableFormat#URL_SAFE} and
     * {@link DecodeTableFormat#STANDARD} base64 options.
     * </p>
     *
     * @since 1.21
     */
    public enum DecodeTableFormat {

        /**
         * Corresponds to the standard Base64 coding table, as specified in
         * <a href="https://www.ietf.org/rfc/rfc2045#:~:text=Table%201%3A%20The%20Base64%20Alphabet">RFC 2045 Table 1: The Base64 Alphabet</a>.
         */
        STANDARD,
        /**
         * Corresponds to the URL-safe Base64 coding table, as specified in
         * <a href="https://datatracker.ietf.org/doc/html/rfc4648#:~:text=Table%202%3A%20The%20%22URL%20and%20Filename%20safe%22%20Base%2064%20Alphabet">RFC
         * 4648 Table 2: The "URL and Filename safe" Base 64 Alphabet</a>.
         */
        URL_SAFE,
        /**
         * Represents a joint approach, allowing a seamless decoding of both character sets, corresponding to either
         * <a href="https://www.ietf.org/rfc/rfc2045#:~:text=Table%201%3A%20The%20Base64%20Alphabet">RFC 2045 Table 1: The Base64 Alphabet</a> or
         * <a href="https://datatracker.ietf.org/doc/html/rfc4648#:~:text=Table%202%3A%20The%20%22URL%20and%20Filename%20safe%22%20Base%2064%20Alphabet">RFC
         * 4648 Table 2: The "URL and Filename safe" Base 64 Alphabet</a>. This decoding table is used by default.
         */
        MIXED
    }

    /**
     * BASE64 characters are 6 bits in length.
     * They are formed by taking a block of 3 octets to form a 24-bit string,
     * which is converted into 4 BASE64 characters.
     */
    private static final int BITS_PER_ENCODED_BYTE = 6;

    private static final int BYTES_PER_UNENCODED_BLOCK = 3;

    private static final int BYTES_PER_ENCODED_BLOCK = 4;

    private static final int DECODING_TABLE_LENGTH = 256;

    /**
     * This array is a lookup table that translates 6-bit positive integer index values into their "Base64 Alphabet" equivalents as specified in
     * <a href="https://www.ietf.org/rfc/rfc2045#:~:text=Table%201%3A%20The%20Base64%20Alphabet">RFC 2045 Table 1: The Base64 Alphabet</a>.
     * <p>
     * Thanks to "commons" project in ws.apache.org for this code. https://svn.apache.org/repos/asf/webservices/commons/trunk/modules/util/
     * </p>
     */
    // @formatter:off
    private static final byte[] STANDARD_ENCODE_TABLE = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/' };

    /**
     * This is a copy of the STANDARD_ENCODE_TABLE above, but with + and / changed to - and _ to make the encoded Base64 results more URL-SAFE. This table is
     * only used when the Base64's mode is set to URL-SAFE.
     */
    // @formatter:off
    private static final byte[] URL_SAFE_ENCODE_TABLE = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-', '_' };

    // @formatter:on
    /**
     * This array is a lookup table that translates Unicode characters drawn from the "Base64 Alphabet" (as specified in
     * <a href="https://www.ietf.org/rfc/rfc2045#:~:text=Table%201%3A%20The%20Base64%20Alphabet">RFC 2045 Table 1: The Base64 Alphabet</a>) into their 6-bit
     * positive integer equivalents. Characters that are not in the Base64 or Base64 URL-safe alphabets but fall within the bounds of the array are translated
     * to -1.
     * <p>
     * The characters '+' and '-' both decode to 62. '/' and '_' both decode to 63. This means decoder seamlessly handles both URL_SAFE and STANDARD base64.
     * (The encoder, on the other hand, needs to know ahead of time what to emit).
     * </p>
     * <p>
     * Thanks to "commons" project in ws.apache.org for this code. https://svn.apache.org/repos/asf/webservices/commons/trunk/modules/util/
     * </p>
     */
    private static final byte[] DECODE_TABLE = { //   0   1   2   3   4   5   6   7   8   9   A   B   C   D   E   F
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
    -1, // 20-2f + - /
    -1, // 20-2f + - /
    -1, // 20-2f + - /
    -1, // 20-2f + - /
    -1, // 20-2f + - /
    -1, // 20-2f + - /
    -1, // 20-2f + - /
    -1, // 20-2f + - /
    -1, // 20-2f + - /
    -1, // 20-2f + - /
    -1, // 20-2f + - /
    -1, // 20-2f + - /
    62, // 20-2f + - /
    -1, // 20-2f + - /
    62, // 20-2f + - /
    -1, // 20-2f + - /
    63, // 30-3f 0-9
    52, // 30-3f 0-9
    53, // 30-3f 0-9
    54, // 30-3f 0-9
    55, // 30-3f 0-9
    56, // 30-3f 0-9
    57, // 30-3f 0-9
    58, // 30-3f 0-9
    59, // 30-3f 0-9
    60, // 30-3f 0-9
    61, // 30-3f 0-9
    -1, // 30-3f 0-9
    -1, // 30-3f 0-9
    -1, // 30-3f 0-9
    -1, // 30-3f 0-9
    -1, // 30-3f 0-9
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
    14, // 50-5f P-Z _
    15, // 50-5f P-Z _
    16, // 50-5f P-Z _
    17, // 50-5f P-Z _
    18, // 50-5f P-Z _
    19, // 50-5f P-Z _
    20, // 50-5f P-Z _
    21, // 50-5f P-Z _
    22, // 50-5f P-Z _
    23, // 50-5f P-Z _
    24, // 50-5f P-Z _
    25, // 50-5f P-Z _
    -1, // 50-5f P-Z _
    -1, // 50-5f P-Z _
    -1, // 50-5f P-Z _
    -1, // 50-5f P-Z _
    63, // 60-6f a-o
    -1, // 60-6f a-o
    26, // 60-6f a-o
    27, // 60-6f a-o
    28, // 60-6f a-o
    29, // 60-6f a-o
    30, // 60-6f a-o
    31, // 60-6f a-o
    32, // 60-6f a-o
    33, // 60-6f a-o
    34, // 60-6f a-o
    35, // 60-6f a-o
    36, // 60-6f a-o
    37, // 60-6f a-o
    38, // 60-6f a-o
    39, // 60-6f a-o
    40, // 70-7a p-z
    41, // 70-7a p-z
    42, // 70-7a p-z
    43, // 70-7a p-z
    44, // 70-7a p-z
    45, // 70-7a p-z
    46, // 70-7a p-z
    47, // 70-7a p-z
    48, // 70-7a p-z
    49, // 70-7a p-z
    50, // 70-7a p-z
    51 };

    /**
     * This array is a lookup table that translates Unicode characters drawn from the "Base64 Alphabet" (as specified in
     * <a href="https://www.ietf.org/rfc/rfc2045#:~:text=Table%201%3A%20The%20Base64%20Alphabet">RFC 2045 Table 1: The Base64 Alphabet</a>) into their 6-bit
     * positive integer equivalents. Characters that are not in the Base64 alphabet but fall within the bounds of the array are translated to -1. This decoding
     * table handles only the standard base64 characters, such as '+' and '/'. The "url-safe" characters such as '-' and '_' are not supported by the table.
     */
    private static final byte[] STANDARD_DECODE_TABLE = { //   0   1   2   3   4   5   6   7   8   9   A   B   C   D   E   F
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
    -1, // 20-2f + /
    -1, // 20-2f + /
    -1, // 20-2f + /
    -1, // 20-2f + /
    -1, // 20-2f + /
    -1, // 20-2f + /
    -1, // 20-2f + /
    -1, // 20-2f + /
    -1, // 20-2f + /
    -1, // 20-2f + /
    -1, // 20-2f + /
    -1, // 20-2f + /
    62, // 20-2f + /
    -1, // 20-2f + /
    -1, // 20-2f + /
    -1, // 20-2f + /
    63, // 30-3f 0-9
    52, // 30-3f 0-9
    53, // 30-3f 0-9
    54, // 30-3f 0-9
    55, // 30-3f 0-9
    56, // 30-3f 0-9
    57, // 30-3f 0-9
    58, // 30-3f 0-9
    59, // 30-3f 0-9
    60, // 30-3f 0-9
    61, // 30-3f 0-9
    -1, // 30-3f 0-9
    -1, // 30-3f 0-9
    -1, // 30-3f 0-9
    -1, // 30-3f 0-9
    -1, // 30-3f 0-9
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
    14, // 50-5f P-Z
    15, // 50-5f P-Z
    16, // 50-5f P-Z
    17, // 50-5f P-Z
    18, // 50-5f P-Z
    19, // 50-5f P-Z
    20, // 50-5f P-Z
    21, // 50-5f P-Z
    22, // 50-5f P-Z
    23, // 50-5f P-Z
    24, // 50-5f P-Z
    25, // 50-5f P-Z
    -1, // 50-5f P-Z
    -1, // 50-5f P-Z
    -1, // 50-5f P-Z
    -1, // 50-5f P-Z
    -1, // 60-6f a-o
    -1, // 60-6f a-o
    26, // 60-6f a-o
    27, // 60-6f a-o
    28, // 60-6f a-o
    29, // 60-6f a-o
    30, // 60-6f a-o
    31, // 60-6f a-o
    32, // 60-6f a-o
    33, // 60-6f a-o
    34, // 60-6f a-o
    35, // 60-6f a-o
    36, // 60-6f a-o
    37, // 60-6f a-o
    38, // 60-6f a-o
    39, // 60-6f a-o
    40, // 70-7a p-z
    41, // 70-7a p-z
    42, // 70-7a p-z
    43, // 70-7a p-z
    44, // 70-7a p-z
    45, // 70-7a p-z
    46, // 70-7a p-z
    47, // 70-7a p-z
    48, // 70-7a p-z
    49, // 70-7a p-z
    50, // 70-7a p-z
    51 };

    /**
     * This array is a lookup table that translates Unicode characters drawn from the "Base64 URL-safe Alphabet" (as specified in
     * <a href="https://datatracker.ietf.org/doc/html/rfc4648#:~:text=Table%202%3A%20The%20%22URL%20and%20Filename%20safe%22%20Base%2064%20Alphabet">RFC 4648
     * Table 2: The "URL and Filename safe" Base 64 Alphabet</a>) into their 6-bit positive integer equivalents. Characters that are not in the Base64 URL-safe
     * alphabet but fall within the bounds of the array are translated to -1. This decoding table handles only the URL-safe base64 characters, such as '-' and
     * '_'. The standard characters such as '+' and '/' are not supported by the table.
     */
    private static final byte[] URL_SAFE_DECODE_TABLE = { //   0   1   2   3   4   5   6   7   8   9   A   B   C   D   E   F
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
    -1, // 20-2f -
    -1, // 20-2f -
    -1, // 20-2f -
    -1, // 20-2f -
    -1, // 20-2f -
    -1, // 20-2f -
    -1, // 20-2f -
    -1, // 20-2f -
    -1, // 20-2f -
    -1, // 20-2f -
    -1, // 20-2f -
    -1, // 20-2f -
    -1, // 20-2f -
    -1, // 20-2f -
    62, // 20-2f -
    -1, // 20-2f -
    -1, // 30-3f 0-9
    52, // 30-3f 0-9
    53, // 30-3f 0-9
    54, // 30-3f 0-9
    55, // 30-3f 0-9
    56, // 30-3f 0-9
    57, // 30-3f 0-9
    58, // 30-3f 0-9
    59, // 30-3f 0-9
    60, // 30-3f 0-9
    61, // 30-3f 0-9
    -1, // 30-3f 0-9
    -1, // 30-3f 0-9
    -1, // 30-3f 0-9
    -1, // 30-3f 0-9
    -1, // 30-3f 0-9
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
    14, // 50-5f P-Z _
    15, // 50-5f P-Z _
    16, // 50-5f P-Z _
    17, // 50-5f P-Z _
    18, // 50-5f P-Z _
    19, // 50-5f P-Z _
    20, // 50-5f P-Z _
    21, // 50-5f P-Z _
    22, // 50-5f P-Z _
    23, // 50-5f P-Z _
    24, // 50-5f P-Z _
    25, // 50-5f P-Z _
    -1, // 50-5f P-Z _
    -1, // 50-5f P-Z _
    -1, // 50-5f P-Z _
    -1, // 50-5f P-Z _
    63, // 60-6f a-o
    -1, // 60-6f a-o
    26, // 60-6f a-o
    27, // 60-6f a-o
    28, // 60-6f a-o
    29, // 60-6f a-o
    30, // 60-6f a-o
    31, // 60-6f a-o
    32, // 60-6f a-o
    33, // 60-6f a-o
    34, // 60-6f a-o
    35, // 60-6f a-o
    36, // 60-6f a-o
    37, // 60-6f a-o
    38, // 60-6f a-o
    39, // 60-6f a-o
    40, // 70-7a p-z
    41, // 70-7a p-z
    42, // 70-7a p-z
    43, // 70-7a p-z
    44, // 70-7a p-z
    45, // 70-7a p-z
    46, // 70-7a p-z
    47, // 70-7a p-z
    48, // 70-7a p-z
    49, // 70-7a p-z
    50, // 70-7a p-z
    51 };

    /**
     * Base64 uses 6-bit fields.
     */
    /**
     * Mask used to extract 6 bits, used when encoding
     */
    private static final int MASK_6_BITS = 0x3f;

    // The static final fields above are used for the original static byte[] methods on Base64.
    // The private member fields below are used with the new streaming approach, which requires
    // some state be preserved between calls of encode() and decode().
    /**
     * Mask used to extract 4 bits, used when decoding final trailing character.
     */
    private static final int MASK_4_BITS = 0xf;

    /**
     * Mask used to extract 2 bits, used when decoding final trailing character.
     */
    private static final int MASK_2_BITS = 0x3;

    public static Builder builder() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Calculates a decode table for a given encode table.
     *
     * @param encodeTable that is used to determine decode lookup table.
     * @return A new decode table.
     */
    private static byte[] calculateDecodeTable(final byte[] encodeTable) {
        final byte[] decodeTable = new byte[DECODING_TABLE_LENGTH];
        Arrays.fill(decodeTable, (byte) -1);
        for (int i = 0; i < encodeTable.length; i++) {
            decodeTable[encodeTable[i]] = (byte) i;
        }
        return decodeTable;
    }

    public static byte[] decodeBase64(final byte[] base64Data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] decodeBase64(final String base64String) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] decodeBase64Standard(final byte[] base64Data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] decodeBase64Standard(final String base64String) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] decodeBase64UrlSafe(final byte[] base64Data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] decodeBase64UrlSafe(final String base64String) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static BigInteger decodeInteger(final byte[] array) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] encodeBase64(final byte[] binaryData) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] encodeBase64(final byte[] binaryData, final boolean isChunked) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] encodeBase64(final byte[] binaryData, final boolean isChunked, final boolean urlSafe) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] encodeBase64(final byte[] binaryData, final boolean isChunked, final boolean urlSafe, final int maxResultSize) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] encodeBase64Chunked(final byte[] binaryData) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String encodeBase64String(final byte[] binaryData) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] encodeBase64URLSafe(final byte[] binaryData) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String encodeBase64URLSafeString(final byte[] binaryData) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] encodeInteger(final BigInteger bigInteger) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Tests a given byte array to see if it contains only valid characters within the Base64 alphabet. Currently the method treats whitespace as valid.
     *
     * @param arrayOctet byte array to test.
     * @return {@code true} if all bytes are valid characters in the Base64 alphabet or if the byte array is empty; {@code false}, otherwise.
     * @deprecated 1.5 Use {@link #isBase64(byte[])}, will be removed in 2.0.
     */
    @Deprecated
    public static boolean isArrayByteBase64(final byte[] arrayOctet) {
        return isBase64(arrayOctet);
    }

    public static boolean isBase64(final byte octet) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static boolean isBase64(final byte[] arrayOctet) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static boolean isBase64(final String base64) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static boolean isBase64Standard(final byte octet) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static boolean isBase64Standard(final byte[] arrayOctet) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static boolean isBase64Standard(final String base64) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static boolean isBase64Url(final byte octet) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static boolean isBase64Url(final byte[] arrayOctet) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static boolean isBase64Url(final String base64) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    static byte[] toIntegerBytes(final BigInteger bigInt) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    static byte[] toUrlSafeEncodeTable(final boolean urlSafe) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Line separator for encoding. Not used when decoding. Only used if lineLength &gt; 0.
     */
    private final byte[] lineSeparator;

    /**
     * Convenience variable to help us determine when our buffer is going to run out of room and needs resizing. {@code encodeSize = 4 + lineSeparator.length;}
     */
    private final int encodeSize;

    private final boolean isUrlSafe;

    private final boolean isStandardEncodeTable;

    /**
     * Constructs a Base64 codec used for decoding (all modes) and encoding in URL-unsafe mode.
     * <p>
     * When encoding the line length is 0 (no chunking), and the encoding table is STANDARD_ENCODE_TABLE.
     * </p>
     * <p>
     * When decoding all variants are supported.
     * </p>
     */
    public Base64() {
        this(0);
    }

    /**
     * Constructs a Base64 codec used for decoding (all modes) and encoding in the given URL-safe mode.
     * <p>
     * When encoding the line length is 76, the line separator is CRLF, and the encoding table is STANDARD_ENCODE_TABLE.
     * </p>
     * <p>
     * When decoding all variants are supported.
     * </p>
     *
     * @param urlSafe if {@code true}, URL-safe encoding is used. In most cases this should be set to {@code false}.
     * @since 1.4
     * @deprecated Use {@link #builder()} and {@link Builder}.
     */
    @Deprecated
    public Base64(final boolean urlSafe) {
        this(MIME_CHUNK_SIZE, CHUNK_SEPARATOR, urlSafe);
    }

    private Base64(final Builder builder) {
        super(builder);
        final byte[] encTable = builder.getEncodeTable();
        if (encTable.length != STANDARD_ENCODE_TABLE.length) {
            throw new IllegalArgumentException("encodeTable must have exactly 64 entries.");
        }
        this.isStandardEncodeTable = Arrays.equals(encTable, STANDARD_ENCODE_TABLE);
        this.isUrlSafe = Arrays.equals(encTable, URL_SAFE_ENCODE_TABLE);
        // TODO could be simplified if there is no requirement to reject invalid line sep when length <=0
        // @see test case Base64Test.testConstructors()
        if (builder.getLineSeparator().length > 0) {
            final byte[] lineSeparatorB = builder.getLineSeparator();
            if (containsAlphabetOrPad(lineSeparatorB)) {
                final String sep = StringUtils.newStringUtf8(lineSeparatorB);
                throw new IllegalArgumentException("lineSeparator must not contain base64 characters: [" + sep + "]");
            }
            if (builder.getLineLength() > 0) {
                // null line-sep forces no chunking rather than throwing IAE
                this.encodeSize = BYTES_PER_ENCODED_BLOCK + lineSeparatorB.length;
                this.lineSeparator = lineSeparatorB;
            } else {
                this.encodeSize = BYTES_PER_ENCODED_BLOCK;
                this.lineSeparator = null;
            }
        } else {
            this.encodeSize = BYTES_PER_ENCODED_BLOCK;
            this.lineSeparator = null;
        }
    }

    /**
     * Constructs a Base64 codec used for decoding (all modes) and encoding in URL-unsafe mode.
     * <p>
     * When encoding the line length is given in the constructor, the line separator is CRLF, and the encoding table is STANDARD_ENCODE_TABLE.
     * </p>
     * <p>
     * Line lengths that aren't multiples of 4 will still essentially end up being multiples of 4 in the encoded data.
     * </p>
     * <p>
     * When decoding all variants are supported.
     * </p>
     *
     * @param lineLength Each line of encoded data will be at most of the given length (rounded down to the nearest multiple of 4). If lineLength &lt;= 0, then
     *                   the output will not be divided into lines (chunks). Ignored when decoding.
     * @since 1.4
     * @deprecated Use {@link #builder()} and {@link Builder}.
     */
    @Deprecated
    public Base64(final int lineLength) {
        this(lineLength, CHUNK_SEPARATOR);
    }

    /**
     * Constructs a Base64 codec used for decoding (all modes) and encoding in URL-unsafe mode.
     * <p>
     * When encoding the line length and line separator are given in the constructor, and the encoding table is STANDARD_ENCODE_TABLE.
     * </p>
     * <p>
     * Line lengths that aren't multiples of 4 will still essentially end up being multiples of 4 in the encoded data.
     * </p>
     * <p>
     * When decoding all variants are supported.
     * </p>
     *
     * @param lineLength    Each line of encoded data will be at most of the given length (rounded down to the nearest multiple of 4). If lineLength &lt;= 0,
     *                      then the output will not be divided into lines (chunks). Ignored when decoding.
     * @param lineSeparator Each line of encoded data will end with this sequence of bytes.
     * @throws IllegalArgumentException Thrown when the provided lineSeparator included some base64 characters.
     * @since 1.4
     * @deprecated Use {@link #builder()} and {@link Builder}.
     */
    @Deprecated
    public Base64(final int lineLength, final byte[] lineSeparator) {
        this(lineLength, lineSeparator, false);
    }

    /**
     * Constructs a Base64 codec used for decoding (all modes) and encoding in URL-unsafe mode.
     * <p>
     * When encoding the line length and line separator are given in the constructor, and the encoding table is STANDARD_ENCODE_TABLE.
     * </p>
     * <p>
     * Line lengths that aren't multiples of 4 will still essentially end up being multiples of 4 in the encoded data.
     * </p>
     * <p>
     * When decoding all variants are supported.
     * </p>
     *
     * @param lineLength    Each line of encoded data will be at most of the given length (rounded down to the nearest multiple of 4). If lineLength &lt;= 0,
     *                      then the output will not be divided into lines (chunks). Ignored when decoding.
     * @param lineSeparator Each line of encoded data will end with this sequence of bytes.
     * @param urlSafe       Instead of emitting '+' and '/' we emit '-' and '_' respectively. urlSafe is only applied to encode operations. Decoding seamlessly
     *                      handles both modes. <strong>No padding is added when using the URL-safe alphabet.</strong>
     * @throws IllegalArgumentException Thrown when the {@code lineSeparator} contains Base64 characters.
     * @since 1.4
     * @deprecated Use {@link #builder()} and {@link Builder}.
     */
    @Deprecated
    public Base64(final int lineLength, final byte[] lineSeparator, final boolean urlSafe) {
        this(builder().setLineLength(lineLength).setLineSeparator(lineSeparator != null ? lineSeparator : EMPTY_BYTE_ARRAY).setPadding(PAD_DEFAULT).setEncodeTableRaw(toUrlSafeEncodeTable(urlSafe)).setDecodingPolicy(DECODING_POLICY_DEFAULT));
    }

    /**
     * Constructs a Base64 codec used for decoding (all modes) and encoding in URL-unsafe mode.
     * <p>
     * When encoding the line length and line separator are given in the constructor, and the encoding table is STANDARD_ENCODE_TABLE.
     * </p>
     * <p>
     * Line lengths that aren't multiples of 4 will still essentially end up being multiples of 4 in the encoded data.
     * </p>
     * <p>
     * When decoding all variants are supported.
     * </p>
     *
     * @param lineLength     Each line of encoded data will be at most of the given length (rounded down to the nearest multiple of 4). If lineLength &lt;= 0,
     *                       then the output will not be divided into lines (chunks). Ignored when decoding.
     * @param lineSeparator  Each line of encoded data will end with this sequence of bytes.
     * @param urlSafe        Instead of emitting '+' and '/' we emit '-' and '_' respectively. urlSafe is only applied to encode operations. Decoding seamlessly
     *                       handles both modes. <strong>No padding is added when using the URL-safe alphabet.</strong>
     * @param decodingPolicy The decoding policy.
     * @throws IllegalArgumentException Thrown when the {@code lineSeparator} contains Base64 characters.
     * @since 1.15
     * @deprecated Use {@link #builder()} and {@link Builder}.
     */
    @Deprecated
    public Base64(final int lineLength, final byte[] lineSeparator, final boolean urlSafe, final CodecPolicy decodingPolicy) {
        this(builder().setLineLength(lineLength).setLineSeparator(lineSeparator).setPadding(PAD_DEFAULT).setEncodeTableRaw(toUrlSafeEncodeTable(urlSafe)).setDecodingPolicy(decodingPolicy));
    }

    @Override
    void decode(final byte[] input, int inPos, final int inAvail, final Context context) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    void encode(final byte[] in, int inPos, final int inAvail, final Context context) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    byte[] getLineSeparator() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected boolean isInAlphabet(final byte octet) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean isUrlSafe() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Validates whether decoding the final trailing character is possible in the context of the set of possible Base64 values.
     * <p>
     * The character is valid if the lower bits within the provided mask are zero. This is used to test the final trailing base-64 digit is zero in the bits
     * that will be discarded.
     * </p>
     *
     * @param emptyBitsMask The mask of the lower bits that should be empty.
     * @param context       the context to be used.
     * @throws IllegalArgumentException if the bits being checked contain any non-zero value.
     */
    private void validateCharacter(final int emptyBitsMask, final Context context) {
        if (isStrictDecoding() && (context.ibitWorkArea & emptyBitsMask) != 0) {
            throw new IllegalArgumentException("Strict decoding: Last encoded character (before the paddings if any) is a valid " + "Base64 alphabet but not a possible encoding. Expected the discarded bits from the character to be zero.");
        }
    }

    /**
     * Validates whether decoding allows an entire final trailing character that cannot be used for a complete byte.
     *
     * @throws IllegalArgumentException if strict decoding is enabled.
     */
    private void validateTrailingCharacter() {
        if (isStrictDecoding()) {
            throw new IllegalArgumentException("Strict decoding: Last encoded character (before the paddings if any) is a valid " + "Base64 alphabet but not a possible encoding. Decoding requires at least two trailing 6-bit characters to create bytes.");
        }
    }
}

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
package org.apache.commons.codec.net;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;
import java.util.BitSet;
import org.apache.commons.codec.BinaryDecoder;
import org.apache.commons.codec.BinaryEncoder;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringDecoder;
import org.apache.commons.codec.StringEncoder;
import org.apache.commons.codec.binary.StringUtils;

/**
 * Codec for the Quoted-Printable section of <a href="https://www.ietf.org/rfc/rfc1521.txt">RFC 1521</a>.
 * <p>
 * The Quoted-Printable encoding is intended to represent data that largely consists of octets that correspond to printable characters in the ASCII character
 * set. It encodes the data in such a way that the resulting octets are unlikely to be modified by mail transport. If the data being encoded are mostly ASCII
 * text, the encoded form of the data remains largely recognizable by humans. A body which is entirely ASCII may also be encoded in Quoted-Printable to ensure
 * the integrity of the data should the message pass through a character- translating, and/or line-wrapping gateway.
 * </p>
 * <p>
 * Note:
 * </p>
 * <p>
 * Depending on the selected {@code strict} parameter, this class will implement a different set of rules of the quoted-printable spec:
 * </p>
 * <ul>
 * <li>{@code strict=false}: only rules #1 and #2 are implemented</li>
 * <li>{@code strict=true}: all rules #1 through #5 are implemented</li>
 * </ul>
 * <p>
 * Originally, this class only supported the non-strict mode, but the codec in this partial form could already be used for certain applications that do not
 * require quoted-printable line formatting (rules #3, #4, #5), for instance Q codec. The strict mode has been added in 1.10.
 * </p>
 * <p>
 * This class is immutable and thread-safe.
 * </p>
 *
 * @see <a href="https://www.ietf.org/rfc/rfc1521.txt">RFC 1521 MIME (Multipurpose Internet Mail Extensions) Part One: Mechanisms for Specifying and Describing
 *      the Format of Internet Message Bodies </a>
 *
 * @since 1.3
 */
public class QuotedPrintableCodec implements BinaryEncoder, BinaryDecoder, StringEncoder, StringDecoder {

    /**
     * BitSet of printable characters as defined in RFC 1521.
     */
    private static final BitSet PRINTABLE_CHARS = new BitSet(256);

    private static final byte ESCAPE_CHAR = '=';

    private static final byte TAB = 9;

    private static final byte SPACE = 32;

    private static final byte CR = 13;

    private static final byte LF = 10;

    /**
     * Minimum length required for the byte arrays used by encodeQuotedPrintable method.
     */
    private static final int MIN_BYTES = 3;

    /**
     * Safe line length for quoted printable encoded text.
     */
    private static final int SAFE_LENGTH = 73;

    // Static initializer for printable chars collection
    static {
        // alpha characters
        for (int i = 33; i <= 60; i++) {
            PRINTABLE_CHARS.set(i);
        }
        for (int i = 62; i <= 126; i++) {
            PRINTABLE_CHARS.set(i);
        }
        PRINTABLE_CHARS.set(TAB);
        PRINTABLE_CHARS.set(SPACE);
    }

    public static final byte[] decodeQuotedPrintable(final byte[] bytes) throws DecoderException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Encodes a byte in the buffer.
     *
     * @param b      byte to write.
     * @param encode indicates whether the octet shall be encoded.
     * @param buffer the buffer to write to.
     * @return the number of bytes that have been written to the buffer.
     */
    private static int encodeByte(final int b, final boolean encode, final ByteArrayOutputStream buffer) {
        if (encode) {
            return encodeQuotedPrintable(b, buffer);
        }
        buffer.write(b);
        return 1;
    }

    public static final byte[] encodeQuotedPrintable(final BitSet printable, final byte[] bytes) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static final byte[] encodeQuotedPrintable(BitSet printable, final byte[] bytes, final boolean strict) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Encodes byte into its quoted-printable representation.
     *
     * @param b      byte to encode.
     * @param buffer the buffer to write to.
     * @return The number of bytes written to the {@code buffer}.
     */
    private static int encodeQuotedPrintable(final int b, final ByteArrayOutputStream buffer) {
        buffer.write(ESCAPE_CHAR);
        final char hex1 = Utils.hexChar(b >> 4);
        final char hex2 = Utils.hexChar(b);
        buffer.write(hex1);
        buffer.write(hex2);
        return 3;
    }

    /**
     * Gets the byte at position {@code index} of the byte array and make sure it is unsigned.
     *
     * @param index position in the array.
     * @param bytes the byte array.
     * @return the unsigned octet at position {@code index} from the array.
     */
    private static int getUnsignedOctet(final int index, final byte[] bytes) {
        int b = bytes[index];
        if (b < 0) {
            b = 256 + b;
        }
        return b;
    }

    /**
     * Checks whether the given byte is whitespace.
     *
     * @param b byte to be checked.
     * @return {@code true} if the byte is either a space or tab character.
     */
    private static boolean isWhitespace(final int b) {
        return b == SPACE || b == TAB;
    }

    /**
     * The default Charset used for string decoding and encoding.
     */
    private final Charset charset;

    /**
     * Indicates whether soft line breaks shall be used during encoding (rule #3-5).
     */
    private final boolean strict;

    /**
     * Constructs a new instance, assumes default Charset of {@link StandardCharsets#UTF_8}
     */
    public QuotedPrintableCodec() {
        this(StandardCharsets.UTF_8, false);
    }

    /**
     * Constructs a new instance for the selection of the strict mode.
     *
     * @param strict if {@code true}, soft line breaks will be used.
     * @since 1.10
     */
    public QuotedPrintableCodec(final boolean strict) {
        this(StandardCharsets.UTF_8, strict);
    }

    /**
     * Constructs a new instance for the selection of a default Charset.
     *
     * @param charset the default string Charset to use.
     * @since 1.7
     */
    public QuotedPrintableCodec(final Charset charset) {
        this(charset, false);
    }

    /**
     * Constructs a new instance for the selection of a default Charset and strict mode.
     *
     * @param charset the default string Charset to use.
     * @param strict  if {@code true}, soft line breaks will be used.
     * @since 1.10
     */
    public QuotedPrintableCodec(final Charset charset, final boolean strict) {
        this.charset = charset;
        this.strict = strict;
    }

    /**
     * Constructs a new instance for the selection of a default Charset.
     *
     * @param charsetName the default string Charset to use.
     * @throws UnsupportedCharsetException If no support for the named Charset is available in this instance of the Java virtual machine.
     * @throws IllegalArgumentException    If the given charsetName is null.
     * @throws IllegalCharsetNameException If the given Charset name is illegal.
     *
     * @since 1.7 throws UnsupportedCharsetException if the named Charset is unavailable
     */
    public QuotedPrintableCodec(final String charsetName) throws IllegalCharsetNameException, IllegalArgumentException, UnsupportedCharsetException {
        this(Charset.forName(charsetName), false);
    }

    @Override
    public byte[] decode(final byte[] bytes) throws DecoderException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Object decode(final Object obj) throws DecoderException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String decode(final String sourceStr) throws DecoderException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String decode(final String sourceStr, final Charset sourceCharset) throws DecoderException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String decode(final String sourceStr, final String sourceCharset) throws DecoderException, UnsupportedEncodingException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public byte[] encode(final byte[] bytes) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Object encode(final Object obj) throws EncoderException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String encode(final String sourceStr) throws EncoderException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String encode(final String sourceStr, final Charset sourceCharset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String encode(final String sourceStr, final String sourceCharset) throws UnsupportedEncodingException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Charset getCharset() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String getDefaultCharset() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}

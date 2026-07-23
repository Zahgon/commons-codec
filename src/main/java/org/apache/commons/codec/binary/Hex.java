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

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.apache.commons.codec.BinaryDecoder;
import org.apache.commons.codec.BinaryEncoder;
import org.apache.commons.codec.CharEncoding;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;

/**
 * Converts hexadecimal Strings. The Charset used for certain operation can be set, the default is set in
 * {@link #DEFAULT_CHARSET_NAME}
 *
 * This class is thread-safe.
 *
 * @since 1.1
 */
public class Hex implements BinaryEncoder, BinaryDecoder {

    /**
     * Default charset is {@link StandardCharsets#UTF_8}.
     *
     * @since 1.7
     */
    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    /**
     * Default charset name is {@link CharEncoding#UTF_8}.
     *
     * @since 1.4
     */
    public static final String DEFAULT_CHARSET_NAME = CharEncoding.UTF_8;

    /**
     * Used to build output as hex.
     */
    private static final char[] DIGITS_LOWER = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    /**
     * Used to build output as hex.
     */
    private static final char[] DIGITS_UPPER = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

    public static byte[] decodeHex(final char[] data) throws DecoderException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static int decodeHex(final char[] data, final byte[] out, final int outOffset) throws DecoderException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] decodeHex(final String data) throws DecoderException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static char[] encodeHex(final byte[] data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static char[] encodeHex(final byte[] data, final boolean toLowerCase) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected static char[] encodeHex(final byte[] data, final char[] toDigits) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static char[] encodeHex(final byte[] data, final int dataOffset, final int dataLen, final boolean toLowerCase) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void encodeHex(final byte[] data, final int dataOffset, final int dataLen, final boolean toLowerCase, final char[] out, final int outOffset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Converts an array of bytes into an array of characters representing the hexadecimal values of each byte in order.
     *
     * @param data a byte[] to convert to hexadecimal characters.
     * @param dataOffset the position in {@code data} to start encoding from.
     * @param dataLen the number of bytes from {@code dataOffset} to encode.
     * @param toDigits the output alphabet (must contain at least 16 chars).
     * @param out a char[] which will hold the resultant appropriate characters from the alphabet.
     * @param outOffset the position within {@code out} at which to start writing the encoded characters.
     * @return the given {@code out}.
     */
    private static char[] encodeHex(final byte[] data, final int dataOffset, final int dataLen, final char[] toDigits, final char[] out, final int outOffset) {
        // two characters form the hex value.
        for (int i = dataOffset, j = outOffset; i < dataOffset + dataLen; i++) {
            out[j++] = toDigits[(0xF0 & data[i]) >>> 4];
            out[j++] = toDigits[0x0F & data[i]];
        }
        return out;
    }

    public static char[] encodeHex(final ByteBuffer data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static char[] encodeHex(final ByteBuffer data, final boolean toLowerCase) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected static char[] encodeHex(final ByteBuffer byteBuffer, final char[] toDigits) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String encodeHexString(final byte[] data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String encodeHexString(final byte[] data, final boolean toLowerCase) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String encodeHexString(final ByteBuffer data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String encodeHexString(final ByteBuffer data, final boolean toLowerCase) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Converts a boolean to an alphabet.
     *
     * @param toLowerCase true for lowercase, false for uppercase.
     * @return an alphabet.
     */
    private static char[] toAlphabet(final boolean toLowerCase) {
        return toLowerCase ? DIGITS_LOWER : DIGITS_UPPER;
    }

    /**
     * Convert the byte buffer to a byte array. All bytes identified by
     * {@link ByteBuffer#remaining()} will be used.
     *
     * @param byteBuffer the byte buffer.
     * @return the byte[].
     */
    private static byte[] toByteArray(final ByteBuffer byteBuffer) {
        final int remaining = byteBuffer.remaining();
        // Use the underlying buffer if possible
        if (byteBuffer.hasArray()) {
            final byte[] byteArray = byteBuffer.array();
            if (remaining == byteArray.length) {
                byteBuffer.position(remaining);
                return byteArray;
            }
        }
        // Copy the bytes
        final byte[] byteArray = new byte[remaining];
        byteBuffer.get(byteArray);
        return byteArray;
    }

    protected static int toDigit(final char ch, final int index) throws DecoderException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private final Charset charset;

    /**
     * Creates a new codec with the default charset name {@link #DEFAULT_CHARSET}
     */
    public Hex() {
        // use default encoding
        this.charset = DEFAULT_CHARSET;
    }

    /**
     * Creates a new codec with the given Charset.
     *
     * @param charset the charset.
     * @since 1.7
     */
    public Hex(final Charset charset) {
        this.charset = charset;
    }

    /**
     * Creates a new codec with the given charset name.
     *
     * @param charsetName the charset name.
     * @throws java.nio.charset.UnsupportedCharsetException If the named charset is unavailable.
     * @since 1.4
     * @since 1.7 throws UnsupportedCharsetException if the named charset is unavailable
     */
    public Hex(final String charsetName) {
        this(Charset.forName(charsetName));
    }

    @Override
    public byte[] decode(final byte[] array) throws DecoderException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public byte[] decode(final ByteBuffer buffer) throws DecoderException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Object decode(final Object object) throws DecoderException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public byte[] encode(final byte[] array) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public byte[] encode(final ByteBuffer array) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Object encode(final Object object) throws EncoderException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Charset getCharset() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String getCharsetName() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}

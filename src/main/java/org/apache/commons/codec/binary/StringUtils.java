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

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.apache.commons.codec.CharEncoding;

/**
 * Converts String to and from bytes using the encodings required by the Java specification. These encodings are
 * specified in standard {@link Charset}.
 *
 * <p>
 * This class is immutable and thread-safe.
 * </p>
 *
 * @see CharEncoding
 * @see Charset
 * @see StandardCharsets
 * @since 1.4
 */
public class StringUtils {

    public static boolean equals(final CharSequence cs1, final CharSequence cs2) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Calls {@link String#getBytes(Charset)}
     *
     * @param string
     *            The string to encode (if null, return null).
     * @param charset
     *            The {@link Charset} to encode the {@code String}.
     * @return the encoded bytes.
     */
    private static ByteBuffer getByteBuffer(final String string, final Charset charset) {
        if (string == null) {
            return null;
        }
        return ByteBuffer.wrap(string.getBytes(charset));
    }

    public static ByteBuffer getByteBufferUtf8(final String string) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Calls {@link String#getBytes(Charset)}
     *
     * @param string
     *            The string to encode (if null, return null).
     * @param charset
     *            The {@link Charset} to encode the {@code String}.
     * @return the encoded bytes.
     */
    private static byte[] getBytes(final String string, final Charset charset) {
        return string == null ? null : string.getBytes(charset);
    }

    public static byte[] getBytesIso8859_1(final String string) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] getBytesUnchecked(final String string, final String charsetName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] getBytesUsAscii(final String string) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] getBytesUtf16(final String string) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] getBytesUtf16Be(final String string) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] getBytesUtf16Le(final String string) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] getBytesUtf8(final String string) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private static IllegalStateException newIllegalStateException(final String charsetName, final UnsupportedEncodingException e) {
        return new IllegalStateException(charsetName + ": " + e);
    }

    /**
     * Constructs a new {@code String} by decoding the specified array of bytes using the given charset.
     *
     * @param bytes
     *            The bytes to be decoded into characters.
     * @param charset
     *            The {@link Charset} to encode the {@code String}; not {@code null}.
     * @return A new {@code String} decoded from the specified array of bytes using the given charset,
     *         or {@code null} if the input byte array was {@code null}.
     * @throws NullPointerException
     *             Thrown if charset is {@code null}.
     */
    private static String newString(final byte[] bytes, final Charset charset) {
        return bytes == null ? null : new String(bytes, charset);
    }

    public static String newString(final byte[] bytes, final String charsetName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String newStringIso8859_1(final byte[] bytes) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String newStringUsAscii(final byte[] bytes) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String newStringUtf16(final byte[] bytes) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String newStringUtf16Be(final byte[] bytes) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String newStringUtf16Le(final byte[] bytes) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String newStringUtf8(final byte[] bytes) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * TODO Make private in 2.0.
     *
     * @deprecated TODO Make private in 2.0.
     */
    @Deprecated
    public StringUtils() {
        // empty
    }
}

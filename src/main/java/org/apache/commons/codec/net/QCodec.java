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

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;
import java.util.BitSet;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringDecoder;
import org.apache.commons.codec.StringEncoder;

/**
 * Similar to the Quoted-Printable content-transfer-encoding defined in
 * <a href="https://www.ietf.org/rfc/rfc1521.txt">RFC 1521</a> and designed to allow text containing mostly ASCII
 * characters to be decipherable on an ASCII terminal without decoding.
 * <p>
 * <a href="https://www.ietf.org/rfc/rfc1522.txt">RFC 1522</a> describes techniques to allow the encoding of non-ASCII
 * text in various portions of a RFC 822 [2] message header, in a manner which is unlikely to confuse existing message
 * handling software.
 * </p>
 * <p>
 * This class is conditionally thread-safe.
 * The instance field for encoding blanks is mutable {@link #setEncodeBlanks(boolean)}
 * but is not volatile, and accesses are not synchronized.
 * If an instance of the class is shared between threads, the caller needs to ensure that suitable synchronization
 * is used to ensure safe publication of the value between threads, and must not invoke
 * {@link #setEncodeBlanks(boolean)} after initial setup.
 * </p>
 *
 * @see <a href="https://www.ietf.org/rfc/rfc1522.txt">MIME (Multipurpose Internet Mail Extensions) Part Two: Message
 *          Header Extensions for Non-ASCII Text</a>
 *
 * @since 1.3
 */
public class QCodec extends RFC1522Codec implements StringEncoder, StringDecoder {

    /**
     * BitSet of printable characters as defined in RFC 1522.
     */
    private static final BitSet PRINTABLE_CHARS = new BitSet(256);

    // Static initializer for printable chars collection
    static {
        // alpha characters
        PRINTABLE_CHARS.set(' ');
        PRINTABLE_CHARS.set('!');
        PRINTABLE_CHARS.set('"');
        PRINTABLE_CHARS.set('#');
        PRINTABLE_CHARS.set('$');
        PRINTABLE_CHARS.set('%');
        PRINTABLE_CHARS.set('&');
        PRINTABLE_CHARS.set('\'');
        PRINTABLE_CHARS.set('(');
        PRINTABLE_CHARS.set(')');
        PRINTABLE_CHARS.set('*');
        PRINTABLE_CHARS.set('+');
        PRINTABLE_CHARS.set(',');
        PRINTABLE_CHARS.set('-');
        PRINTABLE_CHARS.set('.');
        PRINTABLE_CHARS.set('/');
        for (int i = '0'; i <= '9'; i++) {
            PRINTABLE_CHARS.set(i);
        }
        PRINTABLE_CHARS.set(':');
        PRINTABLE_CHARS.set(';');
        PRINTABLE_CHARS.set('<');
        PRINTABLE_CHARS.set('>');
        PRINTABLE_CHARS.set('@');
        for (int i = 'A'; i <= 'Z'; i++) {
            PRINTABLE_CHARS.set(i);
        }
        PRINTABLE_CHARS.set('[');
        PRINTABLE_CHARS.set('\\');
        PRINTABLE_CHARS.set(']');
        PRINTABLE_CHARS.set('^');
        PRINTABLE_CHARS.set('`');
        for (int i = 'a'; i <= 'z'; i++) {
            PRINTABLE_CHARS.set(i);
        }
        PRINTABLE_CHARS.set('{');
        PRINTABLE_CHARS.set('|');
        PRINTABLE_CHARS.set('}');
        PRINTABLE_CHARS.set('~');
    }

    private static final byte SPACE = 32;

    private static final byte UNDERSCORE = 95;

    private boolean encodeBlanks;

    /**
     * Constructs a new instance.
     */
    public QCodec() {
        this(StandardCharsets.UTF_8);
    }

    /**
     * Constructs a new instance for the selection of a default Charset.
     *
     * @param charset
     *            the default string Charset to use.
     *
     * @see Charset
     * @since 1.7
     */
    public QCodec(final Charset charset) {
        super(charset);
    }

    /**
     * Constructs a new instance for the selection of a default Charset.
     *
     * @param charsetName
     *            the Charset to use.
     * @throws java.nio.charset.UnsupportedCharsetException
     *             If the named Charset is unavailable.
     * @since 1.7 throws UnsupportedCharsetException if the named Charset is unavailable
     * @see Charset
     */
    public QCodec(final String charsetName) {
        this(Charset.forName(charsetName));
    }

    @Override
    public Object decode(final Object obj) throws DecoderException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String decode(final String str) throws DecoderException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected byte[] doDecoding(final byte[] bytes) throws DecoderException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected byte[] doEncoding(final byte[] bytes) {
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

    public String encode(final String sourceStr, final Charset sourceCharset) throws EncoderException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String encode(final String sourceStr, final String sourceCharset) throws EncoderException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected String getEncoding() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean isEncodeBlanks() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void setEncodeBlanks(final boolean b) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}

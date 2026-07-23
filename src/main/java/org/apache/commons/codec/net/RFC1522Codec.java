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
import java.nio.charset.UnsupportedCharsetException;
import java.util.Objects;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.binary.StringUtils;

/**
 * Implements methods common to all codecs defined in RFC 1522.
 * <p>
 * <a href="https://www.ietf.org/rfc/rfc1522.txt">RFC 1522</a> describes techniques to allow the encoding of non-ASCII text in various portions of a RFC 822 [2]
 * message header, in a manner which is unlikely to confuse existing message handling software.
 * </p>
 * <p>
 * This class is immutable and thread-safe.
 * </p>
 *
 * @see <a href="https://www.ietf.org/rfc/rfc1522.txt">MIME (Multipurpose Internet Mail Extensions) Part Two: Message Header Extensions for Non-ASCII Text</a>
 * @since 1.3
 */
abstract class RFC1522Codec {

    /**
     * Separator.
     */
    protected static final char SEP = '?';

    /**
     * Prefix.
     */
    protected static final String POSTFIX = "?=";

    /**
     * Postfix.
     */
    protected static final String PREFIX = "=?";

    /**
     * The default Charset used for string decoding and encoding.
     */
    protected final Charset charset;

    RFC1522Codec(final Charset charset) {
        this.charset = Objects.requireNonNull(charset, "charset");
    }

    protected String decodeText(final String text) throws DecoderException, UnsupportedEncodingException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Decodes an array of bytes using the defined encoding scheme.
     *
     * @param bytes Data to be decoded.
     * @return a byte array that contains decoded data.
     * @throws DecoderException A decoder exception is thrown if a Decoder encounters a failure condition during the decode process.
     */
    protected abstract byte[] doDecoding(byte[] bytes) throws DecoderException;

    /**
     * Encodes an array of bytes using the defined encoding scheme.
     *
     * @param bytes Data to be encoded.
     * @return A byte array containing the encoded data.
     * @throws EncoderException thrown if the Encoder encounters a failure condition during the encoding process.
     */
    protected abstract byte[] doEncoding(byte[] bytes) throws EncoderException;

    protected String encodeText(final String text, final Charset charset) throws EncoderException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected String encodeText(final String text, final String charsetName) throws EncoderException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Charset getCharset() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String getDefaultCharset() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the codec name (referred to as encoding in the RFC 1522).
     *
     * @return name of the codec.
     */
    protected abstract String getEncoding();
}

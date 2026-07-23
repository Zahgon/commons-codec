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
import org.apache.commons.codec.CodecPolicy;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringDecoder;
import org.apache.commons.codec.StringEncoder;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.BaseNCodec;

/**
 * Identical to the Base64 encoding defined by <a href="https://www.ietf.org/rfc/rfc1521.txt">RFC 1521</a>
 * and allows a character set to be specified.
 * <p>
 * <a href="https://www.ietf.org/rfc/rfc1522.txt">RFC 1522</a> describes techniques to allow the encoding of non-ASCII
 * text in various portions of a RFC 822 [2] message header, in a manner which is unlikely to confuse existing message
 * handling software.
 * </p>
 * <p>
 * This class is immutable and thread-safe.
 * </p>
 *
 * @see <a href="https://www.ietf.org/rfc/rfc1522.txt">MIME (Multipurpose Internet Mail Extensions) Part Two: Message
 *          Header Extensions for Non-ASCII Text</a>
 *
 * @since 1.3
 */
public class BCodec extends RFC1522Codec implements StringEncoder, StringDecoder {

    /**
     * The default decoding policy is lenient.
     */
    private static final CodecPolicy DECODING_POLICY_DEFAULT = CodecPolicy.LENIENT;

    /**
     * If true then decoding should throw an exception for impossible combinations of bits at the
     * end of the byte input. The default is to decode as much of them as possible.
     */
    private final CodecPolicy decodingPolicy;

    /**
     * Constructs a new instance.
     */
    public BCodec() {
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
    public BCodec(final Charset charset) {
        this(charset, DECODING_POLICY_DEFAULT);
    }

    /**
     * Constructs a new instance for the selection of a default Charset.
     *
     * @param charset
     *            the default string Charset to use.
     * @param decodingPolicy The decoding policy.
     * @see Charset
     * @since 1.15
     */
    public BCodec(final Charset charset, final CodecPolicy decodingPolicy) {
        super(charset);
        this.decodingPolicy = decodingPolicy;
    }

    /**
     * Constructs a new instance for the selection of a default Charset.
     *
     * @param charsetName
     *            the default Charset to use.
     * @throws java.nio.charset.UnsupportedCharsetException
     *             If the named Charset is unavailable.
     * @since 1.7 throws UnsupportedCharsetException if the named Charset is unavailable
     * @see Charset
     */
    public BCodec(final String charsetName) {
        this(Charset.forName(charsetName));
    }

    @Override
    public Object decode(final Object value) throws DecoderException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String decode(final String value) throws DecoderException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected byte[] doDecoding(final byte[] bytes) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected byte[] doEncoding(final byte[] bytes) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Object encode(final Object value) throws EncoderException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String encode(final String strSource) throws EncoderException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String encode(final String strSource, final Charset sourceCharset) throws EncoderException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String encode(final String strSource, final String sourceCharset) throws EncoderException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected String getEncoding() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean isStrictDecoding() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}

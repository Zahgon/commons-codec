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

import static org.apache.commons.codec.binary.BaseNCodec.EOF;
import java.io.ByteArrayInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import org.apache.commons.codec.binary.BaseNCodec.Context;

/**
 * Abstracts Base-N input streams.
 *
 * @param <C> A BaseNCodec subclass.
 * @param <T> A BaseNCodecInputStream subclass.
 * @param <B> A subclass.
 * @see Base16InputStream
 * @see Base32InputStream
 * @see Base64InputStream
 * @since 1.5
 */
public class BaseNCodecInputStream<C extends BaseNCodec, T extends BaseNCodecInputStream<C, T, B>, B extends BaseNCodecInputStream.AbstracBuilder<T, C, B>> extends FilterInputStream {

    /**
     * Builds input stream instances in {@link BaseNCodec} format.
     *
     * @param <T> the input stream type to build.
     * @param <C> A {@link BaseNCodec} subclass.
     * @param <B> the builder subclass.
     * @since 1.20.0
     */
    public abstract static class AbstracBuilder<T, C extends BaseNCodec, B extends AbstractBaseNCodecStreamBuilder<T, C, B>> extends AbstractBaseNCodecStreamBuilder<T, C, B> {

        private InputStream inputStream;

        /**
         * Constructs a new instance.
         */
        public AbstracBuilder() {
            // super
        }

        protected InputStream getInputStream() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public B setByteArray(final byte[] inputBytes) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public B setInputStream(final InputStream inputStream) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    private final C baseNCodec;

    private final boolean doEncode;

    private final byte[] singleByte = new byte[1];

    private final byte[] buf;

    private final Context context = new Context();

    /**
     * Constructs a new instance.
     *
     * @param builder A builder.
     * @since 1.20.0
     */
    // Caller closes.
    @SuppressWarnings("resource")
    protected BaseNCodecInputStream(final AbstracBuilder<T, C, B> builder) {
        super(builder.getInputStream());
        this.baseNCodec = builder.getBaseNCodec();
        this.doEncode = builder.getEncode();
        this.buf = new byte[doEncode ? 4096 : 8192];
    }

    /**
     * Constructs a new instance.
     *
     * @param inputStream the input stream.
     * @param baseNCodec  the codec.
     * @param doEncode    set to true to perform encoding, else decoding.
     */
    protected BaseNCodecInputStream(final InputStream inputStream, final C baseNCodec, final boolean doEncode) {
        super(inputStream);
        this.doEncode = doEncode;
        this.baseNCodec = baseNCodec;
        this.buf = new byte[doEncode ? 4096 : 8192];
    }

    @Override
    public int available() throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean isStrictDecoding() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public synchronized void mark(final int readLimit) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public boolean markSupported() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public int read() throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public int read(final byte[] array, final int offset, final int len) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public synchronized void reset() throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public long skip(final long n) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}

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
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;
import org.apache.commons.codec.binary.BaseNCodec.Context;
import org.apache.commons.codec.binary.BaseNCodecOutputStream.AbstractBuilder;

/**
 * Abstract superclass for Base-N output streams.
 * <p>
 * To write the EOF marker without closing the stream, call {@link #eof()} or use an <a href="https://commons.apache.org/proper/commons-io/">Apache Commons
 * IO</a>
 * <a href= "https://commons.apache.org/proper/commons-io/apidocs/org/apache/commons/io/output/CloseShieldOutputStream.html" >CloseShieldOutputStream</a>.
 * </p>
 *
 * @param <C> A BaseNCodec subclass.
 * @param <T> A BaseNCodecInputStream subclass.
 * @param <B> A subclass.
 * @see Base16OutputStream
 * @see Base32OutputStream
 * @see Base64OutputStream
 * @since 1.5
 */
public class BaseNCodecOutputStream<C extends BaseNCodec, T extends BaseNCodecOutputStream<C, T, B>, B extends AbstractBuilder<T, C, B>> extends FilterOutputStream {

    /**
     * Builds output stream instances in {@link BaseNCodec} format.
     *
     * @param <T> the output stream type to build.
     * @param <C> A {@link BaseNCodec} subclass.
     * @param <B> the builder subclass.
     * @since 1.20.0
     */
    public abstract static class AbstractBuilder<T, C extends BaseNCodec, B extends AbstractBuilder<T, C, B>> extends AbstractBaseNCodecStreamBuilder<T, C, B> {

        private OutputStream outputStream;

        /**
         * Constructs a new instance.
         */
        public AbstractBuilder() {
            // super
        }

        protected OutputStream getOutputStream() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public B setOutputStream(final OutputStream outputStream) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    private final boolean doEncode;

    private final C baseNCodec;

    private final byte[] singleByte = new byte[1];

    private final Context context = new Context();

    /**
     * Constructs a new instance.
     *
     * @param builder A builder.
     * @since 1.20.0
     */
    // Caller closes.
    @SuppressWarnings("resource")
    protected BaseNCodecOutputStream(final AbstractBuilder<T, C, B> builder) {
        super(builder.getOutputStream());
        this.baseNCodec = builder.getBaseNCodec();
        this.doEncode = builder.getEncode();
    }

    /**
     * Constructs a new instance.
     *
     * TODO should this be protected?
     *
     * @param outputStream the underlying output or null.
     * @param basedCodec   a BaseNCodec.
     * @param doEncode     true to encode, false to decode, TODO should be an enum?.
     */
    public BaseNCodecOutputStream(final OutputStream outputStream, final C basedCodec, final boolean doEncode) {
        super(outputStream);
        this.baseNCodec = basedCodec;
        this.doEncode = doEncode;
    }

    @Override
    public void close() throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void eof() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void flush() throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Flushes this output stream and forces any buffered output bytes to be written out to the stream. If propagate is true, the wrapped stream will also be
     * flushed.
     *
     * @param propagate boolean flag to indicate whether the wrapped OutputStream should also be flushed.
     * @throws IOException if an I/O error occurs.
     */
    private void flush(final boolean propagate) throws IOException {
        final int avail = baseNCodec.available(context);
        if (avail > 0) {
            final byte[] buf = new byte[avail];
            final int c = baseNCodec.readResults(buf, 0, avail, context);
            if (c > 0) {
                out.write(buf, 0, c);
            }
        }
        if (propagate) {
            out.flush();
        }
    }

    public boolean isStrictDecoding() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void write(final byte[] array, final int offset, final int len) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void write(final int i) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}

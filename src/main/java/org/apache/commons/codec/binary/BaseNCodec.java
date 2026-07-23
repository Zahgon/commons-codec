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

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Supplier;
import org.apache.commons.codec.BinaryDecoder;
import org.apache.commons.codec.BinaryEncoder;
import org.apache.commons.codec.CodecPolicy;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;

/**
 * Abstract superclass for Base-N encoders and decoders.
 *
 * <p>
 * This class is thread-safe.
 * </p>
 * <p>
 * You can set the decoding behavior when the input bytes contain leftover trailing bits that cannot be created by a valid encoding. These can be bits that are
 * unused from the final character or entire characters. The default mode is lenient decoding.
 * </p>
 * <ul>
 * <li>Lenient: Any trailing bits are composed into 8-bit bytes where possible. The remainder are discarded.</li>
 * <li>Strict: The decoding will raise an {@link IllegalArgumentException} if trailing bits are not part of a valid encoding. Any unused bits from the final
 * character must be zero. Impossible counts of entire final characters are not allowed.</li>
 * </ul>
 * <p>
 * When strict decoding is enabled it is expected that the decoded bytes will be re-encoded to a byte array that matches the original, i.e. no changes occur on
 * the final character. This requires that the input bytes use the same padding and alphabet as the encoder.
 * </p>
 */
public abstract class BaseNCodec implements BinaryEncoder, BinaryDecoder {

    /**
     * Builds {@link Base64} instances.
     *
     * @param <T> the codec type to build.
     * @param <B> the codec builder subtype.
     * @since 1.17.0
     */
    public abstract static class AbstractBuilder<T, B extends AbstractBuilder<T, B>> implements Supplier<T> {

        private int unencodedBlockSize;

        private int encodedBlockSize;

        private CodecPolicy decodingPolicy = DECODING_POLICY_DEFAULT;

        private int lineLength;

        private byte[] lineSeparator = CHUNK_SEPARATOR;

        private final byte[] defaultEncodeTable;

        private byte[] encodeTable;

        private byte[] decodeTable;

        /**
         * Padding byte.
         */
        private byte padding = PAD_DEFAULT;

        AbstractBuilder(final byte[] defaultEncodeTable) {
            this.defaultEncodeTable = defaultEncodeTable;
            this.encodeTable = defaultEncodeTable;
        }

        @SuppressWarnings("unchecked")
        B asThis() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        byte[] getDecodeTable() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        CodecPolicy getDecodingPolicy() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        int getEncodedBlockSize() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        byte[] getEncodeTable() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        int getLineLength() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        byte[] getLineSeparator() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        byte getPadding() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        int getUnencodedBlockSize() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public B setDecodeTable(final byte[] decodeTable) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        B setDecodeTableRaw(final byte[] decodeTable) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public B setDecodingPolicy(final CodecPolicy decodingPolicy) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        B setEncodedBlockSize(final int encodedBlockSize) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public B setEncodeTable(final byte... encodeTable) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        B setEncodeTableRaw(final byte... encodeTable) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public B setLineLength(final int lineLength) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public B setLineSeparator(final byte... lineSeparator) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public B setPadding(final byte padding) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        B setUnencodedBlockSize(final int unencodedBlockSize) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * Holds thread context so classes can be thread-safe.
     *
     * This class is not itself thread-safe; each thread must allocate its own copy.
     */
    static class Context {

        /**
         * Placeholder for the bytes we're dealing with for our based logic. Bitwise operations store and extract the encoding or decoding from this variable.
         */
        int ibitWorkArea;

        /**
         * Placeholder for the bytes we're dealing with for our based logic. Bitwise operations store and extract the encoding or decoding from this variable.
         */
        long lbitWorkArea;

        /**
         * Buffer for streaming.
         */
        byte[] buffer;

        /**
         * Position where next character should be written in the buffer.
         */
        int pos;

        /**
         * Position where next character should be read from the buffer.
         */
        int readPos;

        /**
         * Boolean flag to indicate the EOF has been reached. Once EOF has been reached, this object becomes useless, and must be thrown away.
         */
        boolean eof;

        /**
         * Variable tracks how many characters have been written to the current line. Only used when encoding. We use it to make sure each encoded line never
         * goes beyond lineLength (if lineLength &gt; 0).
         */
        int currentLinePos;

        /**
         * Writes to the buffer only occur after every 3/5 reads when encoding, and every 4/8 reads when decoding. This variable helps track that.
         */
        int modulus;

        @Override
        public String toString() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * End-of-file marker.
     *
     * @since 1.7
     */
    static final int EOF = -1;

    /**
     * MIME chunk size per RFC 2045 section 6.8.
     *
     * <p>
     * The {@value} character limit does not count the trailing CRLF, but counts all other characters, including any equal signs.
     * </p>
     *
     * @see <a href="https://www.ietf.org/rfc/rfc2045">RFC 2045 section 6.8</a>
     */
    public static final int MIME_CHUNK_SIZE = 76;

    /**
     * PEM chunk size per RFC 1421 section 4.3.2.4.
     *
     * <p>
     * The {@value} character limit does not count the trailing CRLF, but counts all other characters, including any equal signs.
     * </p>
     *
     * @see <a href="https://tools.ietf.org/html/rfc1421">RFC 1421 section 4.3.2.4</a>
     */
    public static final int PEM_CHUNK_SIZE = 64;

    private static final int DEFAULT_BUFFER_RESIZE_FACTOR = 2;

    /**
     * Defines the default buffer size - currently {@value} - must be large enough for at least one encoded block+separator
     */
    private static final int DEFAULT_BUFFER_SIZE = 8192;

    /**
     * The maximum size buffer to allocate.
     *
     * <p>
     * This is set to the same size used in the JDK {@link java.util.ArrayList}:
     * </p>
     * <blockquote> Some VMs reserve some header words in an array. Attempts to allocate larger arrays may result in OutOfMemoryError: Requested array size
     * exceeds VM limit. </blockquote>
     */
    private static final int MAX_BUFFER_SIZE = Integer.MAX_VALUE - 8;

    /**
     * Mask used to extract 8 bits, used in decoding bytes
     */
    protected static final int MASK_8BITS = 0xff;

    /**
     * Byte used to pad output.
     */
    // Allow static access to default
    protected static final byte PAD_DEFAULT = '=';

    /**
     * The default decoding policy.
     *
     * @since 1.15
     */
    protected static final CodecPolicy DECODING_POLICY_DEFAULT = CodecPolicy.LENIENT;

    /**
     * Chunk separator per RFC 2045 section 2.1.
     *
     * @see <a href="https://www.ietf.org/rfc/rfc2045">RFC 2045 section 2.1</a>
     */
    static final byte[] CHUNK_SEPARATOR = { '\r', '\n' };

    /**
     * The empty byte array.
     */
    static final byte[] EMPTY_BYTE_ARRAY = {};

    /**
     * Create a positive capacity at least as large the minimum required capacity. If the minimum capacity is negative then this throws an OutOfMemoryError as
     * no array can be allocated.
     *
     * @param minCapacity the minimum capacity.
     * @return the capacity.
     * @throws OutOfMemoryError if the {@code minCapacity} is negative.
     */
    private static int createPositiveCapacity(final int minCapacity) {
        if (minCapacity < 0) {
            // overflow
            throw new OutOfMemoryError("Unable to allocate array size: " + (minCapacity & 0xffffffffL));
        }
        // This is called when we require buffer expansion to a very big array.
        // Use the conservative maximum buffer size if possible, otherwise the biggest required.
        //
        // Note: In this situation JDK 1.8 java.util.ArrayList returns Integer.MAX_VALUE.
        // This excludes some VMs that can exceed MAX_BUFFER_SIZE but not allocate a full
        // Integer.MAX_VALUE length array.
        // The result is that we may have to allocate an array of this size more than once if
        // the capacity must be expanded again.
        return Math.max(minCapacity, MAX_BUFFER_SIZE);
    }

    public static byte[] getChunkSeparator() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    static int getLength(final byte[] array) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    static boolean isInAlphabet(final byte value, final byte[] table) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Checks if a byte value is whitespace or not.
     *
     * @param byteToCheck the byte to check.
     * @return true if byte is whitespace, false otherwise.
     * @see Character#isWhitespace(int)
     * @deprecated Use {@link Character#isWhitespace(int)}.
     */
    @Deprecated
    protected static boolean isWhiteSpace(final byte byteToCheck) {
        return Character.isWhitespace(byteToCheck);
    }

    /**
     * Increases our buffer by the {@link #DEFAULT_BUFFER_RESIZE_FACTOR}.
     *
     * @param context     the context to be used.
     * @param minCapacity the minimum required capacity.
     * @return the resized byte[] buffer.
     * @throws OutOfMemoryError if the {@code minCapacity} is negative.
     */
    private static byte[] resizeBuffer(final Context context, final int minCapacity) {
        // Overflow-conscious code treats the min and new capacity as unsigned.
        final int oldCapacity = context.buffer.length;
        int newCapacity = oldCapacity * DEFAULT_BUFFER_RESIZE_FACTOR;
        if (Integer.compareUnsigned(newCapacity, minCapacity) < 0) {
            newCapacity = minCapacity;
        }
        if (Integer.compareUnsigned(newCapacity, MAX_BUFFER_SIZE) > 0) {
            newCapacity = createPositiveCapacity(minCapacity);
        }
        final byte[] b = Arrays.copyOf(context.buffer, newCapacity);
        context.buffer = b;
        return b;
    }

    /**
     * Deprecated: Will be removed in 2.0.
     * <p>
     * Instance variable just in case it needs to vary later
     * </p>
     *
     * @deprecated Use {@link #pad}. Will be removed in 2.0.
     */
    @Deprecated
    protected final byte PAD = PAD_DEFAULT;

    /**
     * Pad byte. Instance variable just in case it needs to vary later.
     */
    protected final byte pad;

    /**
     * Number of bytes in each full block of unencoded data, for example 4 for Base64 and 5 for Base32
     */
    private final int unencodedBlockSize;

    /**
     * Number of bytes in each full block of encoded data, for example 3 for Base64 and 8 for Base32
     */
    private final int encodedBlockSize;

    /**
     * Chunksize for encoding. Not used when decoding. A value of zero or less implies no chunking of the encoded data. Rounded down to the nearest multiple of
     * encodedBlockSize.
     */
    protected final int lineLength;

    /**
     * Size of chunk separator. Not used unless {@link #lineLength} &gt; 0.
     */
    private final int chunkSeparatorLength;

    /**
     * Defines the decoding behavior when the input bytes contain leftover trailing bits that cannot be created by a valid encoding. These can be bits that are
     * unused from the final character or entire characters. The default mode is lenient decoding. Set this to {@code true} to enable strict decoding.
     * <ul>
     * <li>Lenient: Any trailing bits are composed into 8-bit bytes where possible. The remainder are discarded.</li>
     * <li>Strict: The decoding will raise an {@link IllegalArgumentException} if trailing bits are not part of a valid encoding. Any unused bits from the final
     * character must be zero. Impossible counts of entire final characters are not allowed.</li>
     * </ul>
     * <p>
     * When strict decoding is enabled it is expected that the decoded bytes will be re-encoded to a byte array that matches the original, i.e. no changes occur
     * on the final character. This requires that the input bytes use the same padding and alphabet as the encoder.
     * </p>
     */
    private final CodecPolicy decodingPolicy;

    /**
     * Decode table to use.
     */
    final byte[] decodeTable;

    /**
     * Encode table.
     */
    final byte[] encodeTable;

    /**
     * Constructs a new instance for a subclass.
     *
     * @param builder How to build this portion of the instance.
     * @since 1.20.0
     */
    protected BaseNCodec(final AbstractBuilder<?, ?> builder) {
        this.unencodedBlockSize = builder.unencodedBlockSize;
        this.encodedBlockSize = builder.encodedBlockSize;
        final boolean useChunking = builder.lineLength > 0 && builder.lineSeparator.length > 0;
        this.lineLength = useChunking ? builder.lineLength / builder.encodedBlockSize * builder.encodedBlockSize : 0;
        this.chunkSeparatorLength = builder.lineSeparator.length;
        this.pad = builder.padding;
        this.decodingPolicy = Objects.requireNonNull(builder.decodingPolicy, "codecPolicy");
        this.encodeTable = Objects.requireNonNull(builder.getEncodeTable(), "builder.getEncodeTable()");
        this.decodeTable = builder.getDecodeTable();
    }

    /**
     * Constructs a new instance.
     * <p>
     * Note {@code lineLength} is rounded down to the nearest multiple of the encoded block size. If {@code chunkSeparatorLength} is zero, then chunking is
     * disabled.
     * </p>
     *
     * @param unencodedBlockSize   the size of an unencoded block (for example Base64 = 3).
     * @param encodedBlockSize     the size of an encoded block (for example Base64 = 4).
     * @param lineLength           if &gt; 0, use chunking with a length {@code lineLength}.
     * @param chunkSeparatorLength the chunk separator length, if relevant.
     * @deprecated Use {@link BaseNCodec#BaseNCodec(AbstractBuilder)}.
     */
    @Deprecated
    protected BaseNCodec(final int unencodedBlockSize, final int encodedBlockSize, final int lineLength, final int chunkSeparatorLength) {
        this(unencodedBlockSize, encodedBlockSize, lineLength, chunkSeparatorLength, PAD_DEFAULT);
    }

    /**
     * Constructs a new instance.
     * <p>
     * Note {@code lineLength} is rounded down to the nearest multiple of the encoded block size. If {@code chunkSeparatorLength} is zero, then chunking is
     * disabled.
     * </p>
     *
     * @param unencodedBlockSize   the size of an unencoded block (for example Base64 = 3).
     * @param encodedBlockSize     the size of an encoded block (for example Base64 = 4).
     * @param lineLength           if &gt; 0, use chunking with a length {@code lineLength}.
     * @param chunkSeparatorLength the chunk separator length, if relevant.
     * @param pad                  byte used as padding byte.
     * @deprecated Use {@link BaseNCodec#BaseNCodec(AbstractBuilder)}.
     */
    @Deprecated
    protected BaseNCodec(final int unencodedBlockSize, final int encodedBlockSize, final int lineLength, final int chunkSeparatorLength, final byte pad) {
        this(unencodedBlockSize, encodedBlockSize, lineLength, chunkSeparatorLength, pad, DECODING_POLICY_DEFAULT);
    }

    /**
     * Constructs a new instance.
     * <p>
     * Note {@code lineLength} is rounded down to the nearest multiple of the encoded block size. If {@code chunkSeparatorLength} is zero, then chunking is
     * disabled.
     * </p>
     *
     * @param unencodedBlockSize   the size of an unencoded block (for example Base64 = 3).
     * @param encodedBlockSize     the size of an encoded block (for example Base64 = 4).
     * @param lineLength           if &gt; 0, use chunking with a length {@code lineLength}.
     * @param chunkSeparatorLength the chunk separator length, if relevant.
     * @param pad                  byte used as padding byte.
     * @param decodingPolicy       Decoding policy.
     * @since 1.15
     * @deprecated Use {@link BaseNCodec#BaseNCodec(AbstractBuilder)}.
     */
    @Deprecated
    protected BaseNCodec(final int unencodedBlockSize, final int encodedBlockSize, final int lineLength, final int chunkSeparatorLength, final byte pad, final CodecPolicy decodingPolicy) {
        this.unencodedBlockSize = unencodedBlockSize;
        this.encodedBlockSize = encodedBlockSize;
        final boolean useChunking = lineLength > 0 && chunkSeparatorLength > 0;
        this.lineLength = useChunking ? lineLength / encodedBlockSize * encodedBlockSize : 0;
        this.chunkSeparatorLength = chunkSeparatorLength;
        this.pad = pad;
        this.decodingPolicy = Objects.requireNonNull(decodingPolicy, "codecPolicy");
        this.encodeTable = null;
        this.decodeTable = null;
    }

    int available(final Context context) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected boolean containsAlphabetOrPad(final byte[] arrayOctet) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public byte[] decode(final byte[] array) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    // package protected for access from I/O streams
    abstract void decode(byte[] array, int i, int length, Context context);

    @Override
    public Object decode(final Object obj) throws DecoderException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public byte[] decode(final String array) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public byte[] encode(final byte[] array) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public byte[] encode(final byte[] array, final int offset, final int length) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    // package protected for access from I/O streams
    abstract void encode(byte[] array, int i, int length, Context context);

    @Override
    public Object encode(final Object obj) throws EncoderException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String encodeAsString(final byte[] array) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String encodeToString(final byte[] array) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected byte[] ensureBufferSize(final int size, final Context context) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public CodecPolicy getCodecPolicy() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected int getDefaultBufferSize() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public long getEncodedLength(final byte[] array) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    boolean hasData(final Context context) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Tests whether or not the {@code octet} is in the current alphabet. Does not allow whitespace or pad.
     *
     * @param value The value to test.
     * @return {@code true} if the value is defined in the current alphabet, {@code false} otherwise.
     */
    protected abstract boolean isInAlphabet(byte value);

    public boolean isInAlphabet(final byte[] arrayOctet, final boolean allowWhitespacePad) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean isInAlphabet(final String basen) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean isStrictDecoding() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    int readResults(final byte[] b, final int position, final int available, final Context context) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}

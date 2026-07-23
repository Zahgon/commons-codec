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

import org.apache.commons.codec.BinaryDecoder;
import org.apache.commons.codec.BinaryEncoder;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;

/**
 * Converts between byte arrays and strings of "0"s and "1"s.
 *
 * <p>
 * This class is immutable and thread-safe.
 * </p>
 *
 * TODO: may want to add more bit vector functions like and/or/xor/nand TODO: also might be good to generate boolean[] from byte[] et cetera.
 *
 * @since 1.3
 */
public class BinaryCodec implements BinaryDecoder, BinaryEncoder {

    /*
     * tried to avoid using ArrayUtils to minimize dependencies while using these empty arrays - dep is just not worth it.
     */
    /**
     * Empty char array.
     */
    private static final char[] EMPTY_CHAR_ARRAY = {};

    /**
     * Empty byte array.
     */
    private static final byte[] EMPTY_BYTE_ARRAY = {};

    /**
     * Mask for bit 0 of a byte.
     */
    private static final int BIT_0 = 1;

    /**
     * Mask for bit 1 of a byte.
     */
    private static final int BIT_1 = 0x02;

    /**
     * Mask for bit 2 of a byte.
     */
    private static final int BIT_2 = 0x04;

    /**
     * Mask for bit 3 of a byte.
     */
    private static final int BIT_3 = 0x08;

    /**
     * Mask for bit 4 of a byte.
     */
    private static final int BIT_4 = 0x10;

    /**
     * Mask for bit 5 of a byte.
     */
    private static final int BIT_5 = 0x20;

    /**
     * Mask for bit 6 of a byte.
     */
    private static final int BIT_6 = 0x40;

    /**
     * Mask for bit 7 of a byte.
     */
    private static final int BIT_7 = 0x80;

    private static final int[] BITS = { BIT_0, BIT_1, BIT_2, BIT_3, BIT_4, BIT_5, BIT_6, BIT_7 };

    public static byte[] fromAscii(final byte[] ascii) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] fromAscii(final char[] ascii) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    static boolean isEmpty(final byte[] array) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] toAsciiBytes(final byte[] raw) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static char[] toAsciiChars(final byte[] raw) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String toAsciiString(final byte[] raw) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Constructs a new instance.
     */
    public BinaryCodec() {
        // empty
    }

    @Override
    public byte[] decode(final byte[] ascii) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Object decode(final Object ascii) throws DecoderException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public byte[] encode(final byte[] raw) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Object encode(final Object raw) throws EncoderException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public byte[] toByteArray(final String ascii) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}

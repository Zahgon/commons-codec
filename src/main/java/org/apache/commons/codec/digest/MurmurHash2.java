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
package org.apache.commons.codec.digest;

import org.apache.commons.codec.binary.StringUtils;

/**
 * Implements the MurmurHash2 32-bit and 64-bit hash functions.
 *
 * <p>MurmurHash is a non-cryptographic hash function suitable for general
 * hash-based lookup. The name comes from two basic operations, multiply (MU)
 * and rotate (R), used in its inner loop. Unlike cryptographic hash functions,
 * it is not specifically designed to be difficult to reverse by an adversary,
 * making it unsuitable for cryptographic purposes.</p>
 *
 * <p>This contains a Java port of the 32-bit hash function {@code MurmurHash2}
 * and the 64-bit hash function {@code MurmurHash64A} from Austin Appleby's
 * original {@code c++} code in SMHasher.</p>
 *
 * <p>This is a re-implementation of the original C code plus some additional
 * features.</p>
 *
 * <p>This is public domain code with no copyrights. From home page of
 * <a href="https://github.com/aappleby/smhasher">SMHasher</a>:</p>
 *
 * <blockquote>
 * "All MurmurHash versions are public domain software, and the author
 * disclaims all copyright to their code."
 * </blockquote>
 *
 * @see <a href="https://en.wikipedia.org/wiki/MurmurHash">MurmurHash</a>
 * @see <a href="https://github.com/aappleby/smhasher/blob/master/src/MurmurHash2.cpp">
 *   Original MurmurHash2 c++ code</a>
 * @since 1.13
 */
public final class MurmurHash2 {

    // Constants for 32-bit variant
    private static final int M32 = 0x5bd1e995;

    private static final int R32 = 24;

    // Constants for 64-bit variant
    private static final long M64 = 0xc6a4a7935bd1e995L;

    private static final int R64 = 47;

    public static int hash32(final byte[] data, final int length) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static int hash32(final byte[] data, final int length, final int seed) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static int hash32(final String text) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static int hash32(final String text, final int from, final int length) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static long hash64(final byte[] data, final int length) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static long hash64(final byte[] data, final int length, final int seed) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static long hash64(final String text) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static long hash64(final String text, final int from, final int length) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * No instance methods.
     */
    private MurmurHash2() {
    }
}

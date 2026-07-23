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

import static java.lang.Integer.rotateLeft;
import java.util.zip.Checksum;

/**
 * Implements the xxHash32 hash algorithm.
 *
 * <p>
 * Copied from Commons Compress 1.14 <a href=
 * "https://gitbox.apache.org/repos/asf?p=commons-compress.git;a=blob;f=src/main/java/org/apache/commons/compress/compressors/lz4/XXHash32.java;h=a406ffc197449be594d46f0d2712b2d4786a1e68;hb=HEAD">https://gitbox.apache.org/repos/asf?p=commons-compress.git;a=blob;f=src/main/java/org/apache/commons/compress/compressors/lz4/XXHash32.java;h=a406ffc197449be594d46f0d2712b2d4786a1e68;hb=HEAD</a>
 * </p>
 * <p>
 * NotThreadSafe
 * </p>
 *
 * @see <a href="https://cyan4973.github.io/xxHash/">xxHash</a>
 * @since 1.11
 */
public class XXHash32 implements Checksum {

    private static final int BUF_SIZE = 16;

    private static final int ROTATE_BITS = 13;

    private static final int PRIME1 = (int) 2654435761L;

    private static final int PRIME2 = (int) 2246822519L;

    private static final int PRIME3 = (int) 3266489917L;

    private static final int PRIME4 = 668265263;

    private static final int PRIME5 = 374761393;

    /**
     * Gets the little-endian int from 4 bytes starting at the specified index.
     *
     * @param buffer The data.
     * @param idx The index.
     * @return The little-endian int.
     */
    private static int getInt(final byte[] buffer, final int idx) {
        return buffer[idx] & 0xff | (buffer[idx + 1] & 0xff) << 8 | (buffer[idx + 2] & 0xff) << 16 | (buffer[idx + 3] & 0xff) << 24;
    }

    private final byte[] oneByte = new byte[1];

    private final int[] state = new int[4];

    // Note: The code used to use ByteBuffer but the manual method is 50% faster
    // See: https://gitbox.apache.org/repos/asf/commons-compress/diff/2f56fb5c
    private final byte[] buffer = new byte[BUF_SIZE];

    private final int seed;

    private int totalLen;

    private int pos;

    /**
     * Sets to true when the state array has been updated since the last reset.
     */
    private boolean stateUpdated;

    /**
     * Creates an XXHash32 instance with a seed of 0.
     */
    public XXHash32() {
        this(0);
    }

    /**
     * Creates an XXHash32 instance.
     *
     * @param seed the seed to use.
     */
    public XXHash32(final int seed) {
        this.seed = seed;
        initializeState();
    }

    @Override
    public long getValue() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private void initializeState() {
        state[0] = seed + PRIME1 + PRIME2;
        state[1] = seed + PRIME2;
        state[2] = seed;
        state[3] = seed - PRIME1;
    }

    private void process(final byte[] b, final int offset) {
        // local shadows for performance
        int s0 = state[0];
        int s1 = state[1];
        int s2 = state[2];
        int s3 = state[3];
        s0 = rotateLeft(s0 + getInt(b, offset) * PRIME2, ROTATE_BITS) * PRIME1;
        s1 = rotateLeft(s1 + getInt(b, offset + 4) * PRIME2, ROTATE_BITS) * PRIME1;
        s2 = rotateLeft(s2 + getInt(b, offset + 8) * PRIME2, ROTATE_BITS) * PRIME1;
        s3 = rotateLeft(s3 + getInt(b, offset + 12) * PRIME2, ROTATE_BITS) * PRIME1;
        state[0] = s0;
        state[1] = s1;
        state[2] = s2;
        state[3] = s3;
        stateUpdated = true;
    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void update(final byte[] b, int off, final int len) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void update(final int b) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}

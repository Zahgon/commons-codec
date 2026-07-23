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

import java.security.SecureRandom;
import java.util.Random;

/**
 * Base64-like method to convert binary bytes into ASCII chars.
 * <p>
 * TODO: Can Base64 be reused?
 * </p>
 * <p>
 * This class is immutable and thread-safe.
 * </p>
 */
final class B64 {

    /**
     * Table with characters for Base64 transformation.
     */
    static final String B64T_STRING = "./0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    /**
     * Table with characters for Base64 transformation.
     */
    // package access for testing
    static final char[] B64T_ARRAY = B64T_STRING.toCharArray();

    // Do not make this protected or public. Array contents are mutable!
    static void b64from24bit(final byte b2, final byte b1, final byte b0, final int outLen, final StringBuilder buffer) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    static String getRandomSalt(final int num) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    static String getRandomSalt(final int num, final Random random) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}

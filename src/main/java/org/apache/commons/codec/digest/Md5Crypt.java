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

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The libc crypt() "$1$" and Apache "$apr1$" MD5-based hash algorithm.
 * <p>
 * Based on the public domain ("beer-ware") C implementation from Poul-Henning Kamp which was found at: <a
 * href="https://www.freebsd.org/cgi/cvsweb.cgi/src/lib/libcrypt/crypt-md5.c?rev=1.1;content-type=text%2Fplain">
 * crypt-md5.c @ freebsd.org</a>
 * </p>
 * <p>
 * Source:
 * </p>
 * <pre>
 * $FreeBSD: src/lib/libcrypt/crypt-md5.c,v 1.1 1999/01/21 13:50:09 brandon Exp $
 * </pre>
 * <p>
 * Conversion to Kotlin and from there to Java in 2012.
 * </p>
 * <p>
 * The C style comments are from the original C code, the ones with "//" from the port.
 * </p>
 * <p>
 * This class is immutable and thread-safe.
 * </p>
 *
 * @since 1.7
 */
public class Md5Crypt {

    /**
     * The Identifier of the Apache variant.
     */
    static final String APR1_PREFIX = "$apr1$";

    /**
     * The number of bytes of the final hash.
     */
    private static final int BLOCKSIZE = 16;

    /**
     * The Identifier of this crypt() variant.
     */
    static final String MD5_PREFIX = "$1$";

    /**
     * The number of rounds of the big loop.
     */
    private static final int ROUNDS = 1000;

    public static String apr1Crypt(final byte[] keyBytes) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String apr1Crypt(final byte[] keyBytes, final Random random) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String apr1Crypt(final byte[] keyBytes, String salt) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String apr1Crypt(final String keyBytes) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String apr1Crypt(final String keyBytes, final String salt) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String md5Crypt(final byte[] keyBytes) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String md5Crypt(final byte[] keyBytes, final Random random) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String md5Crypt(final byte[] keyBytes, final String salt) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String md5Crypt(final byte[] keyBytes, final String salt, final String prefix) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String md5Crypt(final byte[] keyBytes, final String salt, final String prefix, final Random random) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * TODO Make private in 2.0.
     *
     * @deprecated TODO Make private in 2.0.
     */
    @Deprecated
    public Md5Crypt() {
        // empty
    }
}

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
import java.security.SecureRandom;

/**
 * GNU libc crypt(3) compatible hash method.
 * <p>
 * See {@link #crypt(String, String)} for further details.
 * </p>
 * <p>
 * This class is immutable and thread-safe.
 * </p>
 *
 * @since 1.7
 */
public class Crypt {

    public static String crypt(final byte[] keyBytes) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String crypt(final byte[] keyBytes, final String salt) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String crypt(final String key) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String crypt(final String key, final String salt) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * TODO Make private in 2.0.
     *
     * @deprecated TODO Make private in 2.0.
     */
    @Deprecated
    public Crypt() {
        // empty
    }
}

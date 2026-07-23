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
package org.apache.commons.codec.language;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringEncoder;

/**
 * Encodes a string into a Caverphone 2.0 value. Delegate to a {@link Caverphone2} instance.
 *
 * This is an algorithm created by the Caversham Project at the University of Otago. It implements the Caverphone 2.0
 * algorithm:
 *
 * @see <a href="https://en.wikipedia.org/wiki/Caverphone">Wikipedia - Caverphone</a>
 * @see <a href="https://caversham.otago.ac.nz/files/working/ctp150804.pdf">Caverphone 2.0 specification</a>
 * @since 1.4
 * @deprecated 1.5 Replaced by {@link Caverphone2}, will be removed in 2.0.
 */
@Deprecated
public class Caverphone implements StringEncoder {

    /**
     * Delegate to a {@link Caverphone2} instance to avoid code duplication.
     */
    private final Caverphone2 encoder = new Caverphone2();

    /**
     * Constructs a new instance.
     */
    public Caverphone() {
        // empty
    }

    public String caverphone(final String source) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Object encode(final Object obj) throws EncoderException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String encode(final String str) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean isCaverphoneEqual(final String str1, final String str2) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}

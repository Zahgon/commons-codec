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
 * Encodes a string into a Caverphone value.
 *
 * This is an algorithm created by the Caversham Project at the University of Otago. It implements the Caverphone 2.0
 * algorithm:
 *
 * <p>This class is immutable and thread-safe.</p>
 *
 * @see <a href="https://en.wikipedia.org/wiki/Caverphone">Wikipedia - Caverphone</a>
 * @since 1.5
 */
public abstract class AbstractCaverphone implements StringEncoder {

    /**
     * Constructs a new instance for subclasses.
     */
    public AbstractCaverphone() {
        // empty
    }

    @Override
    public Object encode(final Object source) throws EncoderException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean isEncodeEqual(final String str1, final String str2) throws EncoderException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}

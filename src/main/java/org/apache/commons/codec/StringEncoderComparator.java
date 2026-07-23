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
package org.apache.commons.codec;

import java.util.Comparator;

/**
 * Compares Strings using a {@link StringEncoder}. This comparator is used to sort Strings by an encoding scheme such as
 * Soundex, Metaphone, etc. This class can come in handy if one need to sort Strings by an encoded form of a name such
 * as Soundex.
 *
 * <p>This class is immutable and thread-safe.</p>
 */
@SuppressWarnings("rawtypes")
public class // TODO ought to implement Comparator<String> but that's not possible whilst maintaining binary compatibility.
StringEncoderComparator implements Comparator {

    /**
     * Internal encoder instance.
     */
    private final StringEncoder stringEncoder;

    /**
     * Constructs a new instance.
     *
     * @deprecated Creating an instance without a {@link StringEncoder} leads to a {@link NullPointerException}. Will be
     *             removed in 2.0.
     */
    @Deprecated
    public StringEncoderComparator() {
        // Trying to use this will cause things to break
        this.stringEncoder = null;
    }

    /**
     * Constructs a new instance with the given algorithm.
     *
     * @param stringEncoder
     *            the StringEncoder used for comparisons.
     */
    public StringEncoderComparator(final StringEncoder stringEncoder) {
        this.stringEncoder = stringEncoder;
    }

    @Override
    public int compare(final Object o1, final Object o2) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}

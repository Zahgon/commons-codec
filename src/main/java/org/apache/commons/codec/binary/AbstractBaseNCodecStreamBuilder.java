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

import java.util.function.Supplier;

/**
 * Builds input and output stream instances in {@link BaseNCodec} format.
 *
 * @param <T> the stream type to build.
 * @param <C> A {@link BaseNCodec} subclass.
 * @param <B> the builder subclass.
 * @since 1.20.0
 */
public abstract class AbstractBaseNCodecStreamBuilder<T, C extends BaseNCodec, B extends AbstractBaseNCodecStreamBuilder<T, C, B>> implements Supplier<T> {

    private C baseNCodec;

    private boolean encode;

    /**
     * Constructs a new instance.
     */
    public AbstractBaseNCodecStreamBuilder() {
        baseNCodec = newBaseNCodec();
    }

    @SuppressWarnings("unchecked")
    B asThis() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected C getBaseNCodec() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected boolean getEncode() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Creates a new BaseNCodec subclass of type C.
     *
     * @return a new BaseNCodec subclass of type C.
     */
    protected abstract C newBaseNCodec();

    public B setBaseNCodec(final C baseNCodec) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public B setEncode(final boolean encode) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}

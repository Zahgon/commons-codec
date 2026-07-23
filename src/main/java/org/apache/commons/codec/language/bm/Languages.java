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
package org.apache.commons.codec.language.bm;

import java.util.Collections;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.codec.Resources;

/**
 * Language codes.
 * <p>
 * Language codes are typically loaded from resource files. These are UTF-8
 * encoded text files. They are systematically named following the pattern:
 * </p>
 * <blockquote>org/apache/commons/codec/language/bm/${{@link NameType#getName()}
 * languages.txt</blockquote>
 * <p>
 * The format of these resources is the following:
 * </p>
 * <ul>
 * <li><strong>Language:</strong> a single string containing no whitespace</li>
 * <li><strong>End-of-line comments:</strong> Any occurrence of '//' will cause all text
 * following on that line to be discarded as a comment.</li>
 * <li><strong>Multi-line comments:</strong> Any line starting with '/*' will start
 * multi-line commenting mode. This will skip all content until a line ending in
 * '*' and '/' is found.</li>
 * <li><strong>Blank lines:</strong> All blank lines will be skipped.</li>
 * </ul>
 * <p>
 * Ported from language.php
 * </p>
 * <p>
 * This class is immutable and thread-safe.
 * </p>
 *
 * @since 1.6
 */
public class Languages {

    // Implementation note: This class is divided into two sections. The first part
    // is a static factory interface that
    // exposes org/apache/commons/codec/language/bm/%s_languages.txt for %s in
    // NameType.* as a list of supported
    // languages, and a second part that provides instance methods for accessing
    // this set for supported languages.
    /**
     * A set of languages.
     */
    public abstract static class LanguageSet {

        public static LanguageSet from(final Set<String> languages) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Constructs a new instance for subclasses.
         */
        public LanguageSet() {
            // empty
        }

        /**
         * Tests whether this instance contains the given value.
         *
         * @param language the value to test.
         * @return whether this instance contains the given value.
         */
        public abstract boolean contains(String language);

        /**
         * Gets any of this instance's element.
         *
         * @return any of this instance's element.
         */
        public abstract String getAny();

        /**
         * Tests whether this instance is empty.
         *
         * @return whether this instance is empty.
         */
        public abstract boolean isEmpty();

        /**
         * Tests whether this instance contains a single element.
         *
         * @return whether this instance contains a single element.
         */
        public abstract boolean isSingleton();

        abstract LanguageSet merge(LanguageSet other);

        /**
         * Returns an instance restricted to this instances and the given values'.
         *
         * @param other The other instance.
         * @return an instance restricted to this instances and the given values'.
         */
        public abstract LanguageSet restrictTo(LanguageSet other);
    }

    /**
     * Some languages, explicitly enumerated.
     */
    public static final class SomeLanguages extends LanguageSet {

        private final Set<String> languages;

        private SomeLanguages(final Set<String> languages) {
            this.languages = Collections.unmodifiableSet(languages);
        }

        @Override
        public boolean contains(final String language) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String getAny() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public Set<String> getLanguages() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public boolean isEmpty() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public boolean isSingleton() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public LanguageSet merge(final LanguageSet other) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public LanguageSet restrictTo(final LanguageSet other) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String toString() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * Marker for any language.
     */
    public static final String ANY = "any";

    private static final Map<NameType, Languages> LANGUAGES = new EnumMap<>(NameType.class);

    /**
     * No languages at all.
     */
    public static final LanguageSet NO_LANGUAGES = new LanguageSet() {

        @Override
        public boolean contains(final String language) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String getAny() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public boolean isEmpty() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public boolean isSingleton() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public LanguageSet merge(final LanguageSet other) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public LanguageSet restrictTo(final LanguageSet other) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String toString() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    };

    /**
     * Any/all languages.
     */
    public static final LanguageSet ANY_LANGUAGE = new LanguageSet() {

        @Override
        public boolean contains(final String language) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String getAny() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public boolean isEmpty() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public boolean isSingleton() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public LanguageSet merge(final LanguageSet other) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public LanguageSet restrictTo(final LanguageSet other) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String toString() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    };

    static {
        for (final NameType s : NameType.values()) {
            LANGUAGES.put(s, getInstance(langResourceName(s)));
        }
    }

    public static Languages getInstance(final NameType nameType) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static Languages getInstance(final String languagesResourceName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private static String langResourceName(final NameType nameType) {
        return String.format("/org/apache/commons/codec/language/bm/%s_languages.txt", nameType.getName());
    }

    private final Set<String> languages;

    private Languages(final Set<String> languages) {
        this.languages = languages;
    }

    public Set<String> getLanguages() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}

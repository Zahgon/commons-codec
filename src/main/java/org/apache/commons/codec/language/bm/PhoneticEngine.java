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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.apache.commons.codec.language.bm.Languages.LanguageSet;
import org.apache.commons.codec.language.bm.Rule.Phoneme;

/**
 * Converts words into potential phonetic representations.
 * <p>
 * This is a two-stage process. Firstly, the word is converted into a phonetic representation that takes
 * into account the likely source language. Next, this phonetic representation is converted into a
 * pan-European 'average' representation, allowing comparison between different versions of essentially
 * the same word from different languages.
 * </p>
 * <p>
 * This class is intentionally immutable and thread-safe.
 * If you wish to alter the settings for a PhoneticEngine, you
 * must make a new one with the updated settings.
 * </p>
 * <p>
 * Ported from phoneticengine.php
 * </p>
 *
 * @since 1.6
 */
public class PhoneticEngine {

    /**
     * Utility for manipulating a set of phonemes as they are being built up. Not intended for use outside
     * this package, and probably not outside the {@link PhoneticEngine} class.
     *
     * @since 1.6
     */
    static final class PhonemeBuilder {

        public static PhonemeBuilder empty(final Languages.LanguageSet languages) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        private final Set<Rule.Phoneme> phonemes;

        private PhonemeBuilder(final Rule.Phoneme phoneme) {
            this.phonemes = new LinkedHashSet<>();
            this.phonemes.add(phoneme);
        }

        private PhonemeBuilder(final Set<Rule.Phoneme> phonemes) {
            this.phonemes = phonemes;
        }

        public void append(final CharSequence str) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public void apply(final Rule.PhonemeExpr phonemeExpr, final int maxPhonemes) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public Set<Rule.Phoneme> getPhonemes() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public String makeString() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * A function closure capturing the application of a list of rules to an input sequence at a particular offset.
     * After invocation, the values {@code i} and {@code found} are updated. {@code i} points to the
     * index of the next char in {@code input} that must be processed next (the input up to that index having been
     * processed already), and {@code found} indicates if a matching rule was found or not. In the case where a
     * matching rule was found, {@code phonemeBuilder} is replaced with a new builder containing the phonemes
     * updated by the matching rule.
     * <p>
     * Although this class is not thread-safe (it has mutable unprotected fields), it is not shared between threads
     * as it is constructed as needed by the calling methods.
     * </p>
     *
     * @since 1.6
     */
    private static final class RulesApplication {

        private final Map<String, List<Rule>> finalRules;

        private final CharSequence input;

        private final PhonemeBuilder phonemeBuilder;

        private int i;

        private final int maxPhonemes;

        private boolean found;

        RulesApplication(final Map<String, List<Rule>> finalRules, final CharSequence input, final PhonemeBuilder phonemeBuilder, final int i, final int maxPhonemes) {
            Objects.requireNonNull(finalRules, "finalRules");
            this.finalRules = finalRules;
            this.phonemeBuilder = phonemeBuilder;
            this.input = input;
            this.i = i;
            this.maxPhonemes = maxPhonemes;
        }

        public int getI() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public PhonemeBuilder getPhonemeBuilder() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public RulesApplication invoke() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public boolean isFound() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    private static final int DEFAULT_MAX_PHONEMES = 20;

    private static final Map<NameType, Set<String>> NAME_PREFIXES = new EnumMap<>(NameType.class);

    private static final Pattern QUOTE = Pattern.compile("'");

    static {
        NAME_PREFIXES.put(NameType.ASHKENAZI, Collections.unmodifiableSet(new HashSet<>(Arrays.asList("bar", "ben", "da", "de", "van", "von"))));
        NAME_PREFIXES.put(NameType.SEPHARDIC, Collections.unmodifiableSet(new HashSet<>(Arrays.asList("al", "el", "da", "dal", "de", "del", "dela", "de la", "della", "des", "di", "do", "dos", "du", "van", "von"))));
        NAME_PREFIXES.put(NameType.GENERIC, Collections.unmodifiableSet(new HashSet<>(Arrays.asList("da", "dal", "de", "del", "dela", "de la", "della", "des", "di", "do", "dos", "du", "van", "von"))));
    }

    /**
     * Joins some strings with an internal separator.
     *
     * @param strings   Strings to join.
     * @param sep       String to separate them with.
     * @return a single String consisting of each element of {@code strings} interleaved by {@code sep}.
     */
    private static String join(final List<String> strings, final String sep) {
        return strings.stream().collect(Collectors.joining(sep));
    }

    private final Lang lang;

    private final NameType nameType;

    private final RuleType ruleType;

    private final boolean concat;

    private final int maxPhonemes;

    /**
     * Generates a new, fully-configured phonetic engine.
     *
     * @param nameType
     *            the type of names it will use.
     * @param ruleType
     *            the type of rules it will apply.
     * @param concatenate
     *            if it will concatenate multiple encodings.
     */
    public PhoneticEngine(final NameType nameType, final RuleType ruleType, final boolean concatenate) {
        this(nameType, ruleType, concatenate, DEFAULT_MAX_PHONEMES);
    }

    /**
     * Generates a new, fully-configured phonetic engine.
     *
     * @param nameType
     *            the type of names it will use.
     * @param ruleType
     *            the type of rules it will apply.
     * @param concatenate
     *            if it will concatenate multiple encodings.
     * @param maxPhonemes
     *            the maximum number of phonemes that will be handled.
     * @since 1.7
     */
    public PhoneticEngine(final NameType nameType, final RuleType ruleType, final boolean concatenate, final int maxPhonemes) {
        if (ruleType == RuleType.RULES) {
            throw new IllegalArgumentException("ruleType must not be " + RuleType.RULES);
        }
        this.nameType = nameType;
        this.ruleType = ruleType;
        this.concat = concatenate;
        this.lang = Lang.instance(nameType);
        this.maxPhonemes = maxPhonemes;
    }

    /**
     * Applies the final rules to convert from a language-specific phonetic representation to a
     * language-independent representation.
     *
     * @param phonemeBuilder the current phonemes.
     * @param finalRules the final rules to apply.
     * @return the resulting phonemes.
     */
    private PhonemeBuilder applyFinalRules(final PhonemeBuilder phonemeBuilder, final Map<String, List<Rule>> finalRules) {
        Objects.requireNonNull(finalRules, "finalRules");
        if (finalRules.isEmpty()) {
            return phonemeBuilder;
        }
        final Map<Rule.Phoneme, Rule.Phoneme> phonemes = new TreeMap<>(Rule.Phoneme.COMPARATOR);
        phonemeBuilder.getPhonemes().forEach(phoneme -> {
            PhonemeBuilder subBuilder = PhonemeBuilder.empty(phoneme.getLanguages());
            final CharSequence phonemeText = phoneme.getPhonemeText();
            for (int i = 0; i < phonemeText.length(); ) {
                final RulesApplication rulesApplication = new RulesApplication(finalRules, phonemeText, subBuilder, i, maxPhonemes).invoke();
                final boolean found = rulesApplication.isFound();
                subBuilder = rulesApplication.getPhonemeBuilder();
                if (!found) {
                    // not found, appending as-is
                    subBuilder.append(phonemeText.subSequence(i, i + 1));
                }
                i = rulesApplication.getI();
            }
            // the phonemes map orders the phonemes only based on their text, but ignores the language set
            // when adding new phonemes, check for equal phonemes and merge their language set, otherwise
            // phonemes with the same text but different language set get lost
            subBuilder.getPhonemes().forEach(newPhoneme -> {
                if (phonemes.containsKey(newPhoneme)) {
                    final Rule.Phoneme oldPhoneme = phonemes.remove(newPhoneme);
                    final Rule.Phoneme mergedPhoneme = oldPhoneme.mergeWithLanguage(newPhoneme.getLanguages());
                    phonemes.put(mergedPhoneme, mergedPhoneme);
                } else {
                    phonemes.put(newPhoneme, newPhoneme);
                }
            });
        });
        return new PhonemeBuilder(phonemes.keySet());
    }

    public String encode(final String input) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String encode(String input, final Languages.LanguageSet languageSet) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Lang getLang() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public int getMaxPhonemes() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public NameType getNameType() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public RuleType getRuleType() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean isConcat() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}

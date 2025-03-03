/*
 * Author: noties <https://noties.io/>
 * date: 2019/06/11
 *
 * Modified by: VerNANDo57 <silvenation@gmail.com>
 * date: 2022/01/24 6:01PM GMT+7
 */

package com.verNANDo57.rulebook_educational.markwon.prism4j;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class GrammarUtils {

    /**
     * Used when extending an existing grammar to filter out tokens that should not be cloned.
     *
     * @see #extend(Prism4j.Grammar, String, TokenFilter, Prism4j.Token...)
     */
    public interface TokenFilter {

        /**
         * @param token {@link Prism4j.Token} to validate
         * @return a boolean indicating if supplied token should be included (passes the test)
         */
        boolean test(@NotNull Prism4j.Token token);
    }

    /**
     * Helper method to find a token inside grammar. Supports lookup in `inside` grammars. For
     * example given the path: {@code first-token/then-another/and-more } this method will do:
     * <ul>
     * <li>Look for `first-token` at root level of supplied grammar</li>
     * <li>If it\'s found search for first pattern with `inside` grammar</li>
     * <li>If it\'s found search for `then-another` token in this inside grammar</li>
     * <li>etc</li>
     * </ul>
     * Simple path {@code simple-root-level } is also supported
     *
     * @param grammar {@link Prism4j.Grammar}
     * @param path    argument to find a {@link Prism4j.Token}
     * @return a found {@link Prism4j.Token} or null
     */
    @Nullable
    public static Prism4j.Token findToken(@NotNull Prism4j.Grammar grammar, @NotNull String path) {
        final String[] parts = path.split("/");
        return findToken(grammar, parts, 0);
    }

    @Nullable
    private static Prism4j.Token findToken(@NotNull Prism4j.Grammar grammar, @NotNull String[] parts, int index) {

        final String part = parts[index];
        final boolean last = index == parts.length - 1;

        for (Prism4j.Token token : grammar.tokens()) {
            if (part.equals(token.name())) {
                if (last) {
                    return token;
                } else {
                    final Prism4j.Grammar inside = findFirstInsideGrammar(token);
                    if (inside != null) {
                        return findToken(inside, parts, index + 1);
                    } else {
                        break;
                    }
                }
            }
        }

        return null;
    }

    // won't work if there are multiple patterns provided for a token (each with inside grammar)
    public static void insertBeforeToken(
            @NotNull Prism4j.Grammar grammar,
            @NotNull String path,
            Prism4j.Token... tokens
    ) {

        if (tokens == null
                || tokens.length == 0) {
            return;
        }

        final String[] parts = path.split("/");

        insertBeforeToken(grammar, parts, 0, tokens);
    }

    private static void insertBeforeToken(
            @NotNull Prism4j.Grammar grammar,
            @NotNull String[] parts,
            int index,
            @NotNull Prism4j.Token[] tokens) {

        final String part = parts[index];
        final boolean last = index == parts.length - 1;

        final List<Prism4j.Token> grammarTokens = grammar.tokens();

        Prism4j.Token token;

        for (int i = 0, size = grammarTokens.size(); i < size; i++) {

            token = grammarTokens.get(i);

            if (part.equals(token.name())) {

                // here we must decide what to do next:
                //  - it can be out found one
                //  - or we need to go deeper (c)
                if (last) {
                    // here we go, it's our token
                    insertTokensAt(i, grammarTokens, tokens);
                } else {
                    // now we must find a grammar that is inside
                    // token can have multiple patterns
                    // but as they are not identified somehow (no name or anything)
                    // we will try to find first pattern with inside grammar
                    final Prism4j.Grammar inside = findFirstInsideGrammar(token);
                    if (inside != null) {
                        insertBeforeToken(inside, parts, index + 1, tokens);
                    }
                }

                // break after we have found token with specified name (most likely it won't repeat itself)
                break;
            }
        }
    }

    @Nullable
    public static Prism4j.Grammar findFirstInsideGrammar(@NotNull Prism4j.Token token) {
        Prism4j.Grammar grammar = null;
        for (Prism4j.Pattern pattern : token.patterns()) {
            if (pattern.inside() != null) {
                grammar = pattern.inside();
                break;
            }
        }
        return grammar;
    }

    private static void insertTokensAt(
            int start,
            @NotNull List<Prism4j.Token> grammarTokens,
            @NotNull Prism4j.Token[] tokens
    ) {
        for (int i = 0, length = tokens.length; i < length; i++) {
            grammarTokens.add(start + i, tokens[i]);
        }
    }

    @NotNull
    public static Prism4j.Grammar clone(@NotNull Prism4j.Grammar grammar) {
        return CLONER.clone(grammar);
    }

    @NotNull
    public static Prism4j.Token clone(@NotNull Prism4j.Token token) {
        return CLONER.clone(token);
    }

    @NotNull
    public static Prism4j.Pattern clone(@NotNull Prism4j.Pattern pattern) {
        return CLONER.clone(pattern);
    }

    @NotNull
    public static Prism4j.Grammar extend(
            @NotNull Prism4j.Grammar grammar,
            @NotNull String name,
            Prism4j.Token... tokens) {

        // we clone the whole grammar, but override top-most tokens that are passed here

        final int size = tokens != null
                ? tokens.length
                : 0;

        if (size == 0) {
            return new GrammarImpl(name, clone(grammar).tokens());
        }

        final Map<String, Prism4j.Token> overrides = new HashMap<>(size);
        for (Prism4j.Token token : tokens) {
            overrides.put(token.name(), token);
        }

        final List<Prism4j.Token> origins = grammar.tokens();
        final List<Prism4j.Token> out = new ArrayList<>(origins.size());

        Prism4j.Token override;

        for (Prism4j.Token origin : origins) {
            override = overrides.get(origin.name());
            if (override != null) {
                out.add(override);
            } else {
                out.add(clone(origin));
            }
        }

        return new GrammarImpl(name, out);
    }

    @NotNull
    public static Prism4j.Grammar extend(
            @NotNull Prism4j.Grammar grammar,
            @NotNull String name,
            @NotNull TokenFilter filter,
            Prism4j.Token... tokens) {

        final int size = tokens != null
                ? tokens.length
                : 0;

        final Map<String, Prism4j.Token> overrides;
        if (size == 0) {
            overrides = Collections.emptyMap();
        } else {
            overrides = new HashMap<>(size);
            for (Prism4j.Token token : tokens) {
                overrides.put(token.name(), token);
            }
        }

        final List<Prism4j.Token> origins = grammar.tokens();
        final List<Prism4j.Token> out = new ArrayList<>(origins.size());

        Prism4j.Token override;

        for (Prism4j.Token origin : origins) {

            // filter out undesired tokens
            if (!filter.test(origin)) {
                continue;
            }

            override = overrides.get(origin.name());
            if (override != null) {
                out.add(override);
            } else {
                out.add(clone(origin));
            }
        }

        return new GrammarImpl(name, out);
    }

    @NotNull
    public static Prism4j.Grammar require(@NotNull Prism4j prism4j, @NotNull String name) {
        final Prism4j.Grammar grammar = prism4j.grammar(name);
        if (grammar == null) {
            throw new IllegalStateException("Unexpected state, requested language is not found: " + name);
        }
        return grammar;
    }

    private GrammarUtils() {
    }

    private static final Cloner CLONER = Cloner.create();
}

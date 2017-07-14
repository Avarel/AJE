/*
 * Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package xyz.avarel.aje.parser;

import xyz.avarel.aje.exceptions.SyntaxException;
import xyz.avarel.aje.lexer.AJELexer;
import xyz.avarel.aje.lexer.Token;
import xyz.avarel.aje.lexer.TokenType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class Parser {
    private final AJELexer lexer;
    private final List<Token> tokens;
    private final Grammar grammar;

    private Token last;

    public Parser(AJELexer lexer) {
        this(lexer, new Grammar());
    }

    public Parser(AJELexer lexer, Grammar grammar) {
        this.lexer = lexer;
        this.tokens = new ArrayList<>();
        this.grammar = grammar;
    }

    protected Parser(Parser proxy) {
        this.lexer = proxy.lexer;
        this.tokens = proxy.tokens;
        this.grammar = proxy.grammar;
    }

    public Token getLast() {
        return last;
    }

    public Map<TokenType, PrefixParser> getPrefixParsers() {
        return grammar.getPrefixParsers();
    }

    public Map<TokenType, InfixParser> getInfixParsers() {
        return grammar.getInfixParsers();
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public AJELexer getLexer() {
        return lexer;
    }

    public Token eat(TokenType expected) {
        Token token = peek(0);
        if (token.getType() != expected) {
            throw new SyntaxException("Expected token " + expected + " but found " + token.getType(), token.getPosition());
        }
        return eat();
    }

    public boolean match(TokenType expected) {
        Token token = peek(0);
        if (token.getType() != expected) {
            return false;
        }
        eat();
        return true;
    }

    public Token eat() {
        // Make sure we've read the token.
        peek(0);

        return last = tokens.remove(0);
    }


    public Token peek(int distance) {
        // Read in as many as needed.
        while (distance >= tokens.size()) {
            tokens.add(lexer.next());
        }

        // Get the queued token.
        return tokens.get(distance);
    }

    public boolean nextIs(TokenType... tokens) {
        for (int i = 0; i < tokens.length; i++) {
            if (peek(i).getType() != tokens[i]) return false;
        }

        return true;
    }

    public boolean nextIsAny(TokenType... tokens) {
        for (TokenType token : tokens) {
            if (this.nextIs(token)) return true;
        }

        return false;
    }

    public boolean nextIs(TokenType type) {
        return peek(0).getType() == type;
    }

    public int getPrecedence() {
        InfixParser parser = grammar.getInfixParsers().get(peek(0).getType());
        if (parser != null) return parser.getPrecedence();

        return 0;
    }
}

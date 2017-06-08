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

package xyz.avarel.aje.parser.lexer;

public enum TokenType {
    // PAIRS
    LEFT_PAREN,
    RIGHT_PAREN,

    LEFT_BRACKET,
    RIGHT_BRACKET,

    LEFT_BRACE,
    RIGHT_BRACE,

    OPTIONAL_ASSIGN,
    ELVIS,

    // ASSIGNMENT
    ASSIGN,

    // TYPES
    INT,
    DECIMAL,
    BOOLEAN,
    FUNCTION,
    TEXT,
    ATOM,

    // ARITHMETIC
    PLUS,
    MINUS,
    ASTERISK,
    SLASH,
    BACKSLASH,
    CARET,
    PERCENT,

    // RELATIONAL
    EQUALS,
    NOT_EQUAL,
    GT,
    GTE,
    LT,
    LTE,
    OR,
    AND,

    // BOOLEAN
    AMPERSAND,
    VERTICAL_BAR,

    // MISC
    RANGE_TO,
    ARROW,
    PIPE_FORWARD,
    TILDE,
    BANG,
    QUESTION,
    COLON,
    COMMA,
    DOT,

    UNDERSCORE,

    // SCRIPT
    IDENTIFIER,
    UNDEFINED,
    VAR,
    RETURN,
    IF,
    ELSE,
    FOR,
    IN,

    SKIP,
    SEMICOLON,
    LINE,
    EOF,
}

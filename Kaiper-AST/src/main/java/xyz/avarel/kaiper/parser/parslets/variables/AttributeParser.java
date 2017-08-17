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

package xyz.avarel.kaiper.parser.parslets.variables;

import xyz.avarel.kaiper.Precedence;
import xyz.avarel.kaiper.ast.Expr;
import xyz.avarel.kaiper.ast.Single;
import xyz.avarel.kaiper.ast.invocation.Invocation;
import xyz.avarel.kaiper.ast.variables.AssignmentExpr;
import xyz.avarel.kaiper.ast.variables.Identifier;
import xyz.avarel.kaiper.lexer.Token;
import xyz.avarel.kaiper.lexer.TokenType;
import xyz.avarel.kaiper.parser.BinaryParser;
import xyz.avarel.kaiper.parser.KaiperParser;

public class AttributeParser extends BinaryParser {
    public AttributeParser() {
        super(Precedence.DOT);
    }

    @Override
    public Expr parse(KaiperParser parser, Single left, Token token) {
        Token name = parser.eat(TokenType.IDENTIFIER);

        if (parser.match(TokenType.ASSIGN)) {
            return new AssignmentExpr(token.getPosition(), left, name.getString(), parser.parseSingle());
        }

        Identifier id = new Identifier(token.getPosition(), left, name.getString());

        if (parser.nextIsAny(
                TokenType.STRING, TokenType.INT, TokenType.NUMBER,
                TokenType.IDENTIFIER, TokenType.LEFT_BRACE, TokenType.LEFT_PAREN,
                TokenType.UNDERSCORE, TokenType.FUNCTION
        )) {
            Single argument = parser.parseSingle();
            return new Invocation(token.getPosition(), id, argument);
        }

        return id;
    }
}

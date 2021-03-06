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

package xyz.avarel.kaiper.parser.parslets.flow;

import xyz.avarel.kaiper.ast.Expr;
import xyz.avarel.kaiper.ast.Single;
import xyz.avarel.kaiper.ast.flow.ConditionalExpr;
import xyz.avarel.kaiper.ast.value.NullNode;
import xyz.avarel.kaiper.exceptions.SyntaxException;
import xyz.avarel.kaiper.lexer.Token;
import xyz.avarel.kaiper.lexer.TokenType;
import xyz.avarel.kaiper.parser.KaiperParser;
import xyz.avarel.kaiper.parser.PrefixParser;

public class IfElseParser implements PrefixParser {
    @Override
    public Expr parse(KaiperParser parser, Token token) {
        if (!parser.getParserFlags().allowControlFlows()) {
            throw new SyntaxException("Control flows are disabled");
        }

        parser.eat(TokenType.LEFT_PAREN);
        Single condition = parser.parseSingle();
        parser.eat(TokenType.RIGHT_PAREN);

        Expr ifBranch;

        if (parser.matchSignificant(TokenType.LEFT_BRACE)) {
            if (!parser.matchSignificant(TokenType.RIGHT_BRACE)) {
                ifBranch = parser.parseStatements();
                parser.eat(TokenType.RIGHT_BRACE);
            } else {
                ifBranch = NullNode.VALUE;
            }
        } else {
            ifBranch = parser.parseExpr();
        }

        Expr elseBranch = null;

        if (parser.matchSignificant(TokenType.ELSE)) {
            if (parser.matchSignificant(TokenType.LEFT_BRACE)) {
                if (!parser.matchSignificant(TokenType.RIGHT_BRACE)) {
                    elseBranch = parser.parseStatements();
                    parser.eat(TokenType.RIGHT_BRACE);
                } else {
                    elseBranch = NullNode.VALUE;
                }
            } else {
                elseBranch = parser.parseExpr();
            }
        }

        return new ConditionalExpr(token.getPosition(), condition, ifBranch, elseBranch);
    }
}

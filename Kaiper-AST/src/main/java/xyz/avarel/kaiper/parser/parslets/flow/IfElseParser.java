/*
 *  Copyright 2017 An Tran and Adrian Todt
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package xyz.avarel.kaiper.parser.parslets.flow;

import xyz.avarel.kaiper.ast.expr.Expr;
import xyz.avarel.kaiper.ast.expr.flow.ConditionalExpr;
import xyz.avarel.kaiper.ast.expr.value.BooleanNode;
import xyz.avarel.kaiper.ast.expr.value.NullNode;
import xyz.avarel.kaiper.lexer.Token;
import xyz.avarel.kaiper.lexer.TokenType;
import xyz.avarel.kaiper.parser.ExprParser;
import xyz.avarel.kaiper.parser.PrefixParser;

public class IfElseParser implements PrefixParser {
    @Override
    public Expr parse(ExprParser parser, Token token) {
        Expr condition = parser.parseExpr();

        Expr ifBranch = parser.parseBlock();

        Expr elseBranch = null;

        if (parser.matchSignificant(TokenType.ELSE)) {
            elseBranch = parser.nextIs(TokenType.IF) ? parser.parseExpr() : parser.parseBlock();
        }

        if (condition == BooleanNode.TRUE) {
            return ifBranch;
        } else if (condition == BooleanNode.FALSE) {
            return elseBranch != null ? elseBranch : NullNode.VALUE;
        }

        return new ConditionalExpr(token.getPosition(), condition, ifBranch, elseBranch);
    }
}

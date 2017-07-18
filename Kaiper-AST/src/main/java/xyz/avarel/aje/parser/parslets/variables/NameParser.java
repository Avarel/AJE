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

package xyz.avarel.aje.parser.parslets.variables;

import xyz.avarel.aje.ast.Expr;
import xyz.avarel.aje.ast.Single;
import xyz.avarel.aje.ast.flow.ConditionalExpr;
import xyz.avarel.aje.ast.invocation.Invocation;
import xyz.avarel.aje.ast.operations.BinaryOperation;
import xyz.avarel.aje.operations.BinaryOperatorType;
import xyz.avarel.aje.ast.value.UndefinedNode;
import xyz.avarel.aje.ast.variables.AssignmentExpr;
import xyz.avarel.aje.ast.variables.Identifier;
import xyz.avarel.aje.lexer.Token;
import xyz.avarel.aje.lexer.TokenType;
import xyz.avarel.aje.parser.AJEParser;
import xyz.avarel.aje.parser.PrefixParser;

import java.util.ArrayList;
import java.util.List;

public class NameParser implements PrefixParser {
    @Override
    public Expr parse(AJEParser parser, Token token) {
        if (parser.match(TokenType.ASSIGN)) {
            return new AssignmentExpr(token.getString(), parser.parseSingle());
        } else if (parser.match(TokenType.OPTIONAL_ASSIGN)) {
            Single getOp = new Identifier(token.getString());
            return new ConditionalExpr(
                    new BinaryOperation(
                            getOp,
                            UndefinedNode.VALUE,
                            BinaryOperatorType.EQUALS),
                    new AssignmentExpr(token.getString(), parser.parseSingle()),
                    getOp);
        }

        Identifier id = new Identifier(token.getString());

        if (parser.nextIsAny(TokenType.TEXT, TokenType.INT, TokenType.NUMBER, TokenType.IDENTIFIER, TokenType.LEFT_BRACE)) {
            List<Single> arguments = new ArrayList<>();
            arguments.add(parser.parseSingle());

            while (parser.match(TokenType.COMMA)) {
                arguments.add(parser.parseSingle());
            }

            return new Invocation(id, arguments);
        }

        return id;
    }
}
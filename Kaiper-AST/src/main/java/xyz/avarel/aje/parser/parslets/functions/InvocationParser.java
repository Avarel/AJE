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

package xyz.avarel.aje.parser.parslets.functions;

import xyz.avarel.aje.Precedence;
import xyz.avarel.aje.ast.Expr;
import xyz.avarel.aje.ast.Single;
import xyz.avarel.aje.ast.invocation.Invocation;
import xyz.avarel.aje.exceptions.SyntaxException;
import xyz.avarel.aje.lexer.Token;
import xyz.avarel.aje.lexer.TokenType;
import xyz.avarel.aje.parser.AJEParser;
import xyz.avarel.aje.parser.BinaryParser;

import java.util.ArrayList;
import java.util.List;

public class InvocationParser extends BinaryParser {
    public InvocationParser() {
        super(Precedence.POSTFIX);
    }

    @Override
    public Expr parse(AJEParser parser, Single left, Token token) {
        if (!parser.getParserFlags().allowInvocation()) {
            throw new SyntaxException("Function creation are disabled");
        }

        List<Single> arguments = new ArrayList<>();

        if (!parser.match(TokenType.RIGHT_PAREN)) {
            do {
                arguments.add(parser.parseSingle());
            } while (parser.match(TokenType.COMMA));
            parser.eat(TokenType.RIGHT_PAREN);
        }

        return new Invocation(left, arguments);
    }
}
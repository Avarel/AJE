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

package xyz.avarel.aje.parser.parslets.operators;

import xyz.avarel.aje.Precedence;
import xyz.avarel.aje.ast.Expr;
import xyz.avarel.aje.ast.collections.RangeNode;
import xyz.avarel.aje.exceptions.SyntaxException;
import xyz.avarel.aje.lexer.Token;
import xyz.avarel.aje.parser.AJEParser;
import xyz.avarel.aje.parser.BinaryParser;

public class RangeOperatorParser extends BinaryParser {
    public RangeOperatorParser() {
        super(Precedence.RANGE_TO);
    }

    @Override
    public Expr parse(AJEParser parser, Expr left, Token token) {
        if (!parser.getParserFlags().allowRanges()) {
            throw new SyntaxException("Ranges are disabled");
        }

        Expr right = parser.parseExpr(getPrecedence());
        return new RangeNode(left, right);
    }
}
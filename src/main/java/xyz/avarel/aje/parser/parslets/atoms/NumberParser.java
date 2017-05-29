/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
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

package xyz.avarel.aje.parser.parslets.atoms;

import xyz.avarel.aje.Precedence;
import xyz.avarel.aje.ast.Expr;
import xyz.avarel.aje.ast.atoms.ValueAtom;
import xyz.avarel.aje.ast.operations.BinaryOperation;
import xyz.avarel.aje.parser.AJEParser;
import xyz.avarel.aje.parser.PrefixParser;
import xyz.avarel.aje.parser.lexer.Token;
import xyz.avarel.aje.parser.lexer.TokenType;
import xyz.avarel.aje.runtime.Obj;
import xyz.avarel.aje.runtime.Undefined;
import xyz.avarel.aje.runtime.numbers.Complex;
import xyz.avarel.aje.runtime.numbers.Decimal;
import xyz.avarel.aje.runtime.numbers.Int;

public class NumberParser implements PrefixParser {
    @Override
    public Expr parse(AJEParser parser, Token token) {
        Expr value = null;

        if (parser.match(TokenType.IMAGINARY)) {
            String str = token.getString();
            value = new ValueAtom(token.getPosition(), Complex.of(0, Double.parseDouble(str)));
        } else if (token.getType() == TokenType.IMAGINARY) {
            value = new ValueAtom(token.getPosition(), Complex.of(0, 1));
        } else if (token.getType() == TokenType.INT) {
            String str = token.getString();
            value = new ValueAtom(token.getPosition(), Int.of(Integer.parseInt(str)));
        } else if (token.getType() == TokenType.DECIMAL) {
            String str = token.getString();
            value = new ValueAtom(token.getPosition(), Decimal.of(Double.parseDouble(str)));
        }

        if (parser.nextIs(TokenType.IDENTIFIER)) {
            Expr right = parser.parseExpr(Precedence.MULTIPLICATIVE);
            return new BinaryOperation(right.getPosition(), value, right, Obj::times);
        }

        return value != null ? value : new ValueAtom(token.getPosition(), Undefined.VALUE);
    }
}

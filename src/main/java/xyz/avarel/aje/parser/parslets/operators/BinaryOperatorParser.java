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

package xyz.avarel.aje.parser.parslets.operators;

import xyz.avarel.aje.ast.Expr;
import xyz.avarel.aje.ast.operations.BinaryOperation;
import xyz.avarel.aje.ast.variables.AssignmentExpr;
import xyz.avarel.aje.ast.variables.Identifier;
import xyz.avarel.aje.parser.AJEParser;
import xyz.avarel.aje.parser.BinaryParser;
import xyz.avarel.aje.parser.lexer.Token;
import xyz.avarel.aje.parser.lexer.TokenType;
import xyz.avarel.aje.runtime.Obj;

import java.util.function.BinaryOperator;

public class BinaryOperatorParser extends BinaryParser {
    private final BinaryOperator<Obj> operator;

    public BinaryOperatorParser(int precedence, boolean leftAssoc, BinaryOperator<Obj> operator) {
        super(precedence, leftAssoc);
        this.operator = operator;
    }

    @Override
    public Expr parse(AJEParser parser, Expr left, Token token) {
        if (left instanceof Identifier) {
            if (parser.match(TokenType.ASSIGN)) {
                Expr right = parser.parseExpr(0);
                return new AssignmentExpr(token.getPosition(), ((Identifier) left).getName(), new BinaryOperation(token.getPosition(), left, right, operator));
            }
        }

        Expr right = parser.parseExpr(getPrecedence() - (isLeftAssoc() ? 0 : 1));
        return new BinaryOperation(token.getPosition(), left, right, operator);
    }
}
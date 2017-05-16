package xyz.avarel.aje.parser.parslets;

import xyz.avarel.aje.Precedence;
import xyz.avarel.aje.parser.AJEParser;
import xyz.avarel.aje.parser.BinaryParser;
import xyz.avarel.aje.parser.expr.AssignmentExpr;
import xyz.avarel.aje.parser.expr.Expr;
import xyz.avarel.aje.parser.lexer.Token;

public class AssignmentParser extends BinaryParser<Expr, Expr> {
    public AssignmentParser() {
        super(Precedence.ASSIGNMENT);
    }

    @Override
    public Expr parse(AJEParser parser, Expr left, Token token) {
        Expr right = parser.parse(getPrecedence());
        return new AssignmentExpr(left, right);
    }
}
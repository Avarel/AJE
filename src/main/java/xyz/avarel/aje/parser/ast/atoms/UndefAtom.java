package xyz.avarel.aje.parser.ast.atoms;

import xyz.avarel.aje.parser.ast.Expr;
import xyz.avarel.aje.runtime.Undefined;

public enum UndefAtom implements Expr {
    VALUE;

    @Override
    public Undefined compute() {
        return Undefined.VALUE;
    }

    @Override
    public void ast(StringBuilder builder, String prefix, boolean isTail) {
        builder.append(prefix).append(isTail ? "└── " : "├── ").append(Undefined.VALUE);
    }
}

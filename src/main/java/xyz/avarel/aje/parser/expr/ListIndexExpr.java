package xyz.avarel.aje.parser.expr;

import xyz.avarel.aje.runtime.Any;
import xyz.avarel.aje.runtime.Slice;
import xyz.avarel.aje.runtime.Undefined;
import xyz.avarel.aje.runtime.types.numbers.Int;

public class ListIndexExpr implements Expr {
    private final Expr left;
    private final Expr index;

    public ListIndexExpr(Expr left, Expr index) {
        this.left = left;
        this.index = index;
    }

    @Override
    public Any compute() {
        Any obj = left.compute();
        Any iexpr = index.compute();

        if (obj instanceof Slice && iexpr instanceof Int) {
            return ((Slice) obj).get(((Int) iexpr).value());
        }

        return Undefined.VALUE;
    }

    @Override
    public String toString() {
        return "(get index " + index + " of " + left + ")";
    }
}
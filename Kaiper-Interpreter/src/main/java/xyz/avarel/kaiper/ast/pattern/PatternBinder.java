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

package xyz.avarel.kaiper.ast.pattern;

import xyz.avarel.kaiper.interpreter.ExprInterpreter;
import xyz.avarel.kaiper.runtime.Null;
import xyz.avarel.kaiper.runtime.Obj;
import xyz.avarel.kaiper.runtime.Tuple;
import xyz.avarel.kaiper.scope.Scope;

public class PatternBinder implements PatternVisitor<Boolean, Tuple> {

    private final PatternCase patternCase;
    private final ExprInterpreter interpreter;
    private final Scope scope;

    private boolean usedValue = false;

    public PatternBinder(PatternCase patternCase, ExprInterpreter interpreter, Scope<String, Obj> scope) {
        this.patternCase = patternCase;
        this.interpreter = interpreter;
        this.scope = scope;
    }

    public boolean declareFrom(Tuple tuple) {
        for (Pattern pattern : patternCase.getPatterns()) {
            boolean result = pattern.accept(this, tuple);

            if (!result) {
                return false;
            }
        }

        return true;
    }

    @Override
    public Boolean visit(VariablePattern pattern, Tuple obj) {
        Obj value;

        if (obj.hasAttr(pattern.getName())) {
            value = obj.getAttr(pattern.getName());
        } else if (!usedValue && obj.hasAttr("value")) {
            value = obj.getAttr("value");
            usedValue = true;
        } else {
            return false;
        }

        if (!pattern.isNullable() && value == Null.VALUE) {
            return false;
        } else {
            ExprInterpreter.declare(scope, pattern.getName(), value);
            return true;
        }
    }

    @Override
    public Boolean visit(DefaultPattern pattern, Tuple obj) {
        boolean result = pattern.getDelegate().accept(this, obj);

        if (!result) {
            Obj value = pattern.getDefault().accept(interpreter, scope);
            ExprInterpreter.declare(scope, pattern.getDelegate().getName(), value);
        }

        return true;
    }

    @Override
    public Boolean visit(TuplePattern pattern, Tuple obj) {
        Obj value;

        if (obj.hasAttr(pattern.getName())) {
            value = obj.getAttr(pattern.getName());
        }  else if (!usedValue && obj.hasAttr("value")) {
            value = obj.getAttr("value");
            usedValue = true;
        } else {
            return false;
        }

        return interpreter.resultOf(pattern.getExpr(), scope).equals(value);
    }
}

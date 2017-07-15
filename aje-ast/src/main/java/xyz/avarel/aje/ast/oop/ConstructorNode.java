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

package xyz.avarel.aje.ast.oop;

import xyz.avarel.aje.ast.Expr;
import xyz.avarel.aje.ast.ExprVisitor;
import xyz.avarel.aje.ast.Single;
import xyz.avarel.aje.ast.functions.ParameterData;

import java.util.List;

public class ConstructorNode implements Single {
    private final List<ParameterData> parameters;
    private final List<Expr> superInvocation;
    private final Expr expr;

    public ConstructorNode(List<ParameterData> parameters, List<Expr> superInvocation, Expr expr) {
        this.parameters = parameters;
        this.superInvocation = superInvocation;
        this.expr = expr;
    }

    public List<ParameterData> getParameterExprs() {
        return parameters;
    }

    public List<Expr> getSuperInvocation() {
        return superInvocation;
    }

    public Expr getExpr() {
        return expr;
    }

    @Override
    public <R, C> R accept(ExprVisitor<R, C> visitor, C scope) {
        return visitor.visit(this, scope);
    }
}

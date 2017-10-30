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

package xyz.avarel.kaiper.runtime.runtime_pattern;

// (delegate) = (defaultExpr)
public class NestedPattern extends Pattern {
    private final PatternCase pattern;

    public NestedPattern(PatternCase pattern) {
        this.pattern = pattern;
    }

    public PatternCase getPattern() {
        return pattern;
    }

    @Override
    public <R, C> R accept(PatternVisitor<R, C> visitor, C scope) {
        return visitor.visit(this, scope);
    }

    @Override
    public int nodeWeight() {
        return 2;
    }

    @Override
    public int compareTo(Pattern other) {
        int compare = super.compareTo(other);

        if (compare == 0 && other instanceof NestedPattern) {
            return pattern.compareTo(((NestedPattern) other).pattern);
        }

        return compare;
    }

    @Override
    public String toString() {
        return pattern.toString();
    }
}

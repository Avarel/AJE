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

package xyz.avarel.kaiper.runtime;

import xyz.avarel.kaiper.runtime.modules.Module;
import xyz.avarel.kaiper.runtime.modules.NativeModule;
import xyz.avarel.kaiper.runtime.types.Type;

/**
 * Every operation results in the same
 * instance, NOTHING.
 */
public enum Null implements Obj {
    VALUE;

    public static final Type<Null> TYPE = new Type<>("Null");
    public static final Module MODULE = new NativeModule() {{
        declare("TYPE", Null.TYPE);
    }};

    @Override
    public String toString() {
        return "null";
    }

    @Override
    public Null toJava() {
        return this;
    }

    @Override
    public Type getType() {
        return TYPE;
    }
}

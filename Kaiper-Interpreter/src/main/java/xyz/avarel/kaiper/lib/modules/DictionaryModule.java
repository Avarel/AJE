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

package xyz.avarel.kaiper.lib.modules;

import xyz.avarel.kaiper.interpreter.ExprInterpreter;
import xyz.avarel.kaiper.runtime.collections.Dictionary;
import xyz.avarel.kaiper.runtime.modules.NativeModule;

public class DictionaryModule extends NativeModule {
    public DictionaryModule(ExprInterpreter interpreter) {
        super("Dictionary");
        declare("TYPE", Dictionary.TYPE);

        //        declare("size", new NativeFunc("size", "dict") {
//            @Override
//            protected Obj eval(Map<String, Obj> arguments) {
//                return Int.of(arguments.get("dict").size());
//            }
//        });
    }
}

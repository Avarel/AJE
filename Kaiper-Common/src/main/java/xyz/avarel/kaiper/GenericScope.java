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

package xyz.avarel.kaiper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

// inheritable map
public class GenericScope<K, V> {
    private final GenericScope<K, V>[] parents;
    private final Map<K, V> map;

    @SafeVarargs
    public GenericScope(GenericScope<K, V>... parents) {
        this(new HashMap<>(), parents);
    }

    @SafeVarargs
    public GenericScope(Map<K, V> map, GenericScope<K, V>... parents) {
        this.map = map;
        this.parents = parents;
    }

    public V get(K key) {
        V value = map.get(key);
        if (value != null) {
            return value;
        } else for (GenericScope<K, V> parent : parents) {
            V value0 = parent.get(key);
            if (value0 != null) {
                return value0;
            }
        }
        return null;
    }

    public V put(K key, V value) {
        return map.put(key, value);
    }

    public GenericScope<K, V>[] getParents() {
        return parents;
    }

    public Map<K, V> getMap() {
        return map;
    }

    public boolean contains(K key) {
        if (map.containsKey(key)) return true;
        for (GenericScope<K,V> parent : parents) {
            if (parent.contains(key)) {
                return true;
            }
        }
        return false;
    }

    public GenericScope<K, V> copy() {
        return new GenericScope<>(new HashMap<>(map), parents);
    }

    public GenericScope<K, V> subPool() {
        return new GenericScope<>(this);
    }

    public GenericScope<K, V> combine(GenericScope<K, V> otherScope) {
        GenericScope<K, V>[] array = Arrays.copyOf(parents, parents.length + 1);
        array[array.length - 1] = otherScope;

        return new GenericScope<>(new HashMap<>(map), array);
    }

    @Override
    public String toString() {
        return map.toString();
    }
}
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

package xyz.avarel.aje.loops;

import xyz.avarel.aje.lexer.AJELexer;

import java.util.Scanner;

public class LexerLoop {
    public static void main(String[] args) {
        System.out.println("LEXER EVALUATOR");
        System.out.println();

        Scanner sc = new Scanner(System.in);

        boolean running = true;

        while (running) {
            try {
                System.out.print("String | ");

                String input = sc.nextLine();

                switch (input) {
                    case "-stop":
                        running = false;
                        continue;
                }

                AJELexer lexer = new AJELexer(input);

                System.out.println(lexer.tokensToString());

            } catch (RuntimeException e) {
                System.out.println("    Result | Caught an error: " + e.getMessage() + "\n");
                e.printStackTrace();
                return;
            }
        }
    }
}
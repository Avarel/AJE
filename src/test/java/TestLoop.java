import xyz.avarel.aje.ExpressionBuilder;
import xyz.avarel.aje.MathExpression;
import xyz.avarel.aje.types.Any;

import java.util.Scanner;

public class TestLoop {
    public static void main(String[] args) {
        System.out.println("AJE REPL");
        System.out.println();

        Scanner sc = new Scanner(System.in);

        boolean running = true;

        while (running) {
            try {
                System.out.print("  REPL | ");

                String input = sc.nextLine();

                switch (input) {
                    case "-stop":
                        running = false;
                        continue;
                }

                //Function exp = new Function(input);

                MathExpression exp = new ExpressionBuilder(input)
                        .addVariable("tau")
                        .build();
//                        .setVariable("tau", Math.PI * 2);

                //System.out.println(function);

                long start = System.nanoTime();
                Any result = exp.compute();
                long end = System.nanoTime();

                Object obj = result.toNative();

                long ns =  (end - start);
                double ms = ns / 1000000D;

                System.out.println("  Time | " + ms + "ms " + ns + "ns" );
                System.out.println("Result | " + result + " : " + result.getType());
                System.out.println("Native | " + obj + " : " + obj.getClass().getSimpleName());

                System.out.println();
            } catch (RuntimeException e) {
                System.out.println(" ERROR | " + e.getMessage() + "\n");
                e.printStackTrace();
                return;
            }
        }
    }
}
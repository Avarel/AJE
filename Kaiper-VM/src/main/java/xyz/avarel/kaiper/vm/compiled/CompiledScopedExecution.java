package xyz.avarel.kaiper.vm.compiled;

import xyz.avarel.kaiper.bytecode.reader.OpcodeReader;
import xyz.avarel.kaiper.runtime.Obj;
import xyz.avarel.kaiper.scope.Scope;
import xyz.avarel.kaiper.vm.executor.StackMachineConsumer;

import java.util.List;

public class CompiledScopedExecution extends CompiledExecution {
    private final Scope baseScope;

    public CompiledScopedExecution(OpcodeReader reader, byte[] bytecode, int depth, List<String> stringPool, Scope baseScope) {
        super(reader, bytecode, depth, stringPool);
        this.baseScope = baseScope;
    }

    public Obj execute() {
        return execute(baseScope.subPool());
    }

    public Obj execute(StackMachineConsumer executor) {
        return execute(executor, baseScope.subPool());
    }
}

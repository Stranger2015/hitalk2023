package org.stranger2015.hitalk.core;

import org.stranger2015.hitalk.core.runtime.CodeBase;
import org.stranger2015.hitalk.core.runtime.PrologRuntime;

import java.util.Deque;
import java.util.List;
import java.util.function.Function;

/**
 *
 */
public
class LogtalkInterpreter extends PrologInterpreter{
    /**
     * @param runtime
     * @param heap
     * @param localStack
     * @param trailStack
     * @param codebase
     * @param code
     */
    public
    LogtalkInterpreter ( PrologRuntime runtime,
                         List <Term> heap,
                         Deque <Term> localStack,
                         Deque <Term> trailStack,
                         CodeBase codebase,
                         Function <PredicateIndicator, Boolean> code ) {

        super(runtime, heap, localStack, trailStack, codebase, code);
    }
}

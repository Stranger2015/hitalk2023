package org.stranger2015.hitalk.core;

import java.util.function.Consumer;
import java.util.function.Function;

import static org.stranger2015.hitalk.core.AtomTerm.*;

/**
 *
 */
public abstract
class DirectiveProc<T extends Entity<T>> implements Function <CompoundTerm, Entity<T>>,
                                                    IExecutable {
    /**
     * Applies this function to the given argument.
     *
     * @param compoundTerm the function argument
     * @return the function result
     */
    @Override
    public abstract
    Entity<T> apply ( CompoundTerm compoundTerm );
}

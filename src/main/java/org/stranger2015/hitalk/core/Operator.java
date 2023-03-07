package org.stranger2015.hitalk.core;

import org.jetbrains.annotations.Contract;

/**
 *
 */
public
class Operator {
    /**
     * @param name
     * @param specifier
     * @param priority
     */
    @Contract(pure = true)
    public
    Operator ( AtomTerm name, AtomTerm specifier, int priority) {
        this.priority = priority;
        this.name = name;
        this.specifier = specifier;
    }

    private final int priority;
    private final AtomTerm name;
    private final AtomTerm specifier;

    /**
     * @return
     */
    public
    int getPriority () {
        return priority;
    }

    /**
     * @return
     */
    AtomTerm getName(){
        return name;
    }

    /**
     * @return
     */
    public
    AtomTerm getSpecifier () {
        return specifier;
    }
}

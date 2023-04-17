package org.stranger2015.hitalk.core;

import org.jetbrains.annotations.Contract;

/**
 *
 */
public
record Directive<T extends Entity <T>>(DirectiveProc <T> getProc) {
    /**
     * @param getProc
     */
    @Contract(pure = true)
    public
    Directive {
    }

    /**
     * @return
     */
    @Override
    @Contract(pure = true)
    public
    DirectiveProc <T> getProc () {
        return getProc;
    }
}

package org.stranger2015.hitalk.core;

import org.jetbrains.annotations.Contract;

/**
 *
 */
public
class ModeIndicator {
    private final CompoundTerm modeTerm;

    /**
     * @param modeTerm
     */
    @Contract(pure = true)
    public
    ModeIndicator ( CompoundTerm modeTerm ) {
        this.modeTerm = modeTerm;
    }

    /**
     * @return
     */
    public
    CompoundTerm getModeTerm () {
        return modeTerm;
    }
}

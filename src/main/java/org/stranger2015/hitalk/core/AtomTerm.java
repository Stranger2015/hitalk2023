package org.stranger2015.hitalk.core;

import org.stranger2015.hitalk.core.runtime.MemoryCell;

import static org.stranger2015.hitalk.core.runtime.MemoryCell.ETypeMemoryCells.*;

/**
 *
 */
public
class AtomTerm extends MemoryCell {
    /**
     * @param type
     */
    public
    AtomTerm ( ETypeMemoryCells type ) {
        super(type);
    }

    private static int id=0;
    public static
    Term createAtom ( String s ) {
        id++;
        return new AtomTerm(CON) ;
    }
}

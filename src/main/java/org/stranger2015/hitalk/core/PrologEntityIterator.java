package org.stranger2015.hitalk.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 *
 */
public
class PrologEntityIterator implements Iterator <Entity> {
    protected final List <Entity> entities = new ArrayList <>();
    protected final PrologParser parser;

    /**
     * @param parser
     */
    public
    PrologEntityIterator ( PrologParser parser ) {
        this.parser = parser;
    }

    /**
     * Returns {@code true} if the iteration has more elements.
     * (In other words, returns {@code true} if {@link #next} would
     * return an element rather than throwing an exception.)
     *
     * @return {@code true} if the iteration has more elements
     */
    @Override
    public
    boolean hasNext () {
        return false;
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     * @throws NoSuchElementException if the iteration has no more elements
     */
    @Override
    public
    Entity next () {
        return null;
    }

    /**
     * @return
     */
    public
    Entity nextModule () {
        Entity entity;
        while (this.hasNext()) {
            entity = this.next();
            if (entity instanceof ModuleEntity) {
                return entity;
            }
        }

        throw new NoSuchElementException();
    }

    /**
     * @return
     */
    public
    PrologParser getParser () {
        return parser;
    }
}

package org.stranger2015.hitalk.core.compiler;

import org.stranger2015.hitalk.core.*;

import java.util.NoSuchElementException;

/**
 *
 */
public
class HitalkEntityIterator extends PrologEntityIterator {
    /**
     * @param parser
     */
    public
    HitalkEntityIterator ( HitalkParser parser ) {
        super(parser);
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
        return super.hasNext();
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
        return super.next();
    }

    /**
     * @return
     */
    public
    ObjectEntity nextObject(){

        return null;
    }

    /**
     * @return
     */
    public
    ProtocolEntity nextProtocol(){

        return null;
    }

    /**
     * @return
     */
    public
    CategoryEntity nextCategory(){

        return null;
    }
}

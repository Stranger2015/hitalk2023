package org.stranger2015.hitalk.core;

import java.util.*;

import static org.stranger2015.hitalk.core.PrologParser.END_OF_FILE;

/**
 *
 */
public
class TermIterator implements Iterator <Term> {
    private final PrologParser parser;
    private boolean hasNext = true;

    protected final Deque <Term> list = new ArrayDeque <>();

    /**
     * @param parser
     */
    public
    TermIterator ( PrologParser parser ) {
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
        return hasNext || list.size()>0;
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     * @throws NoSuchElementException if the iteration has no more elements
     */
    @Override
    public
    Term next () {
        Term term;
        if (!list.isEmpty()){
            term=list.pop();
        }else {
            term = parser.nextTerm(true);
            if (term == END_OF_FILE) {
                hasNext = false;
            }
        }

        return term;
    }
}

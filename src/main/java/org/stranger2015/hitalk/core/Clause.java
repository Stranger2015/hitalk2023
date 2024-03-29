package org.stranger2015.hitalk.core;

import java.util.function.Function;

/**
 *
 */
public
class Clause extends ListTerm implements Function <ListTerm, Boolean> {
    private final CompoundTerm head;
    private Term body = EMPTY_LIST;
    private Clause previous;
    private Clause next;

    /**
     * @param head
     * @param body
     */
    public
    Clause ( CompoundTerm head, ListTerm body ) {
        super(head);

        this.head = head;
        this.body = body;
    }

    /**
     * @return
     */
    public
    Term getBody () {
        return body;
    }

    /**
     * @return
     */
    @Override
    public
    Term getHead () {
        return head;
    }

    /**
     * @return
     */
    public
    Clause getNext () {
        return next;
    }

    /**
     * @param next
     */
    public
    void setNext ( Clause next ) {
        this.next = next;
    }

    /**
     * Applies this function to the given argument.
     *
     * @param listTerm the function argument
     * @return the function result
     */
    @Override
    public
    Boolean apply ( ListTerm listTerm ) {
        return null;
    }

    /**
     * @return
     */
    public
    Clause getPrevious () {
        return previous;
    }


    public
    void setPrevious ( Clause previous ) {
        this.previous = previous;
    }
}

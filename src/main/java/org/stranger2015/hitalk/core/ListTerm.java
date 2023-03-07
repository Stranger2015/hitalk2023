package org.stranger2015.hitalk.core;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import static org.stranger2015.hitalk.core.AtomTerm.createAtom;

/**
 *
 */
public
class ListTerm extends CompoundTerm {
    protected static final Term EMPTY_LIST = createAtom("[]");

    private final Term tail=new Variable();
    private final Term head=new Variable();

    /**
     * @param nameArgs
     */
    public
    ListTerm ( ListTerm nameArgs ) {
        super(nameArgs);
    }

    /**
     *
     */
    public
    ListTerm ( Term head ) {
        this(head, EMPTY_LIST);
    }

    public
    ListTerm ( Term name, Term tail ) {
        super(new ListTerm(name, tail));
    }

    public
    ListTerm ( AtomTerm qualifiedName, Term arg1, Term arg2 ) {
        super(qualifiedName, arg1, arg2);
    }

    /**
     * @return
     */
    @Override
    public
    byte getKind () {
        return 0b0110000;
    }

    /**
     * @param head
     * @return
     */
    @Contract(pure = true)
    private @NotNull
    ListTerm prepend ( Term head ) {
        return new ListTerm(head);
    }

    public
    ListTerm getTail () {
        return (ListTerm) tail;
    }

    public
    int getLength () {
        Term h=this.head;
        Term t=this.tail;

        for (int i = 0;; i++) {
            if (t==ListTerm.EMPTY_LIST){
                return i;
            }
            if (t.isFree()){
//             i -n
            }
        }
    }

    /**
     * @return
     */
    public
    Term getHead () {
        return head;
    }
}

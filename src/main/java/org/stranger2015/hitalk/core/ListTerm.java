package org.stranger2015.hitalk.core;

import java.util.ArrayList;
import java.util.List;

import static org.stranger2015.hitalk.core.AtomTerm.createAtom;

/**
 *
 */
public
class ListTerm extends CompoundTerm {
    public static final ListTerm EMPTY_LIST = createAtom("[]").getArgs();
    private Term head;
    private Term tail;

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
    private
    ListTerm prepend ( Term head ) {
        return new ListTerm(head);
    }

    //    public
//    ListTerm getTail () {
//        return (ListTerm) value;
//    }
//
    public
   RangeTerm getLength () {
        RangeTerm result = new RangeTerm(0,0);
        Term h = this.head;
//        Term t = this.value;

//        for (int i = 0; ; i++) {
//            if (t == EMPTY_LIST) {
//                result = i;
//                break;
//            }
//            t.isFree();//             i - n
//        }
        return result;
    }

    /**
     * @return
     */
    public
    Term getHead () {
        return head;
    }

    /**
     * @param i
     * @return
     */
    public
    Term getArg ( int i ) {
        return getNth(i);
    }

    /**
     * @param i
     * @return
     */
    public
    Term getNth ( int i ) {
        ListTerm tail = this;
        for (int j = i; j >= 0; j--) {
            tail = getTail();
        }

        return tail.getHead();
    }

    public
    Term popArg () {
        Term h = getHead();
        Term t = getTail();
//        update term??
        return h;
    }

    /**
     * @return
     */
    public
    List <Term> toList () {
        final List <Term> l = new ArrayList <>();
        for (Term h = head, t = tail; !tail.isFree() && !tail.isAtom(); h = tail.getHead(), t = tail.getTail()) {
            l.add(h);
        }

        return l;
    }
}

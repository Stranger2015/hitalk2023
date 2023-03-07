package org.stranger2015.hitalk.core;

public
class CompoundTerm extends Term {
    private Term nameArgs;

    public
    CompoundTerm ( AtomTerm qualifiedName, Term atom, Term arg2 ) {
        super();
    }

    public
    CompoundTerm ( ListTerm nameArgs ) {
        value=nameArgs;
    }

    @Override
    public
    String toString () {
        return "CompoundTerm{" + '}';
    }

    /**
     * @return
     */
    @Override
    public
    byte getKind () {
        return 0;
    }

    /**
     * @return
     */
    @Override
    public
    boolean isFree () {
        return false;
    }

    public
    PredicateIndicator toPredicateIndicator ( boolean b ) {
        return null;
    }

    public
    int getArity () {
        return nameArgs.getArgCount()-1;
    }

    public
    AtomTerm getName () {
        return nameArgs.getHead();
    }

    public
    ListTerm getArgs () {
        return nameArgs.getTail();
    }
}
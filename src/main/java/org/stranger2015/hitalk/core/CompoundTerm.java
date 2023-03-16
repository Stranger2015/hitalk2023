package org.stranger2015.hitalk.core;

public
class CompoundTerm extends Term {
    protected ListTerm nameArgs;

    public
    CompoundTerm ( AtomTerm qualifiedName, Term atom, Term arg2 ) {
        super();
    }

    public
    CompoundTerm ( AtomTerm minus, int nameArgs ) {
//       this.nameArgs = nameArgs;
        super(nameArgs);
    }

    public
    CompoundTerm ( AtomTerm atom, AtomTerm term ) {

    }

    public
    CompoundTerm ( String seq, AtomTerm result, AtomTerm result1 ) {


    }

    public
    CompoundTerm ( String seq, AtomTerm result ) {

    }

    public
    CompoundTerm ( AtomTerm name ) {
        //ariry==0
    }

    public
    CompoundTerm ( String termExpansion, int i ) {

    }

    public
    CompoundTerm ( ListTerm nameArgs ) {

    }

    @Override
    public
    String toString () {
        return "CompoundTerm{}";
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
    boolean isAtomic () {
        return false;//if arity == 0 ?????
    }

    @Override
    public
    boolean isVar () {
        return false;
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
        return new PredicateIndicator((AtomTerm) nameArgs.getHead(), nameArgs.getTail().getLength());
    }

    /**
     * @return
     */
    public
    int getArity () {
        return nameArgs.getArgCount() - 1;
    }

    /**
     * @return
     */
    public
    AtomTerm getName () {
        return (AtomTerm) nameArgs.getHead();
    }

    /**
     * @return
     */
    public
    ListTerm getArgs () {
        return nameArgs.getTail();
    }
}
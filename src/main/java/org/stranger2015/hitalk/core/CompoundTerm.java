package org.stranger2015.hitalk.core;

/**
 *
 */
public
class CompoundTerm extends Term {
    protected ListTerm nameArgs;

    public
    CompoundTerm ( AtomTerm qualifiedName, Term atom, Term arg2 ) {
        super();
    }

    public
    CompoundTerm ( AtomTerm name, int arity ) {
        super();
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
        //ariry==y0
    }

    public
    CompoundTerm ( String termExpansion, int i ) {

    }

    public
    CompoundTerm ( ListTerm nameArgs ) {
        this.nameArgs = nameArgs;
    }

    public
    CompoundTerm ( AtomTerm name, RangeTerm rangeTerm ) {
    }

    public
    CompoundTerm ( AtomTerm name, CompoundTerm compoundTerm, int arity ) {
        nameArgs = new ListTerm(name);//fixme
    }

    @Override
    public
    String toString () {
        return "CompoundTerm %s%s%d".formatted(getName(), "/", getArity());
    }

    /**
     * @return
     */
    @Override
    public
    boolean isPredicateIndicator () {
        return false;
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
    PredicateIndicator toPredicateIndicator () {
        return new PredicateIndicator((AtomTerm) nameArgs.getHead(), nameArgs.getTail().getLength());
    }

    /**
     * @return
     */
    public
    RangeTerm getArity () {
        return nameArgs.getTail().getArityRange();
    }

    /**
     * @return
     */
    public
    RangeTerm getArityRange () {
        return nameArgs.getTail().getLength();
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
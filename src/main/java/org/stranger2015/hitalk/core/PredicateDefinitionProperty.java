package org.stranger2015.hitalk.core;

public
class PredicateDefinitionProperty implements IProperty {
    protected AtomTerm name;
    protected Term value;

    /**
     * @return
     */
    @Override
    public
    AtomTerm getName () {
        return name;
    }

    /**
     * @return
     */
    @Override
    public
    Term getValue () {
        return value;
    }
}

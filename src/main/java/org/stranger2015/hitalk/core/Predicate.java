package org.stranger2015.hitalk.core;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public
class Predicate implements IPropertyOwner {
    private final CompoundTerm name;
    private final Map <AtomTerm, IProperty> props = new HashMap <>();

    /**
     * @param name
     */
    public
    Predicate ( CompoundTerm name ) {
        this.name = name;
    }

    /**
     * @return
     */
    @Override
    public
    Map <AtomTerm, IProperty> getProperties () {
        return props;
    }

    /**
     * @return
     */
    public
    CompoundTerm getName () {
        return name;
    }

    /**
     * @param name
     * @return
     */
    public
    IProperty getProperty ( String name ) {
        return props.get(name);
    }


}

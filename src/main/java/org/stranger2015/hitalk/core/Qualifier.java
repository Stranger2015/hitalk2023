package org.stranger2015.hitalk.core;

public
class Qualifier extends CompoundTerm {
   private final AtomTerm name1;

    /**
     * @param name
     * @param args
     * @param name1
     */
    public
    Qualifier ( AtomTerm name, ListTerm args, AtomTerm name1 ) {
        super();
//        super(qualifiedName, nameArgs, arg2);
        this.name1 = name1;
    }

    /**
     * @return
     */
    public
    AtomTerm getName1 () {
        return name1;
    }
}

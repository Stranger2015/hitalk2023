package org.stranger2015.hitalk.core;

import org.stranger2015.hitalk.core.runtime.CodeBase;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public
class PredicateDefinition extends PredicateDeclaration {
    //    private final List <Clause> code;
    private CodeBase codeBase;

    private final List <Proc> procs = new ArrayList <>();

    /**
     * @param predicateIndicator
     */
    public
    PredicateDefinition ( PredicateIndicator predicateIndicator ) {
        super(predicateIndicator);

        this.procs.addAll(procs);

    }

    /**
     *
     * @return
     */
    public
    List <Proc> getProcs () {
        return procs;
    }

    public
    CodeBase getCodeBase () {
        return codeBase;
    }

    public void addClause (Clause clause){

    }
}

/**
 *
 */
class Proc extends CodeBase {
      private    java.lang.Object code;
}

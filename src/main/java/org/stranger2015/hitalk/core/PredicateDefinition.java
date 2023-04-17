package org.stranger2015.hitalk.core;

import org.jetbrains.annotations.Contract;
import org.stranger2015.hitalk.core.runtime.CodeBase;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public
class PredicateDefinition implements IProperty {
    /**
     * @return
     */
    enum EProperty {
        AUXILIARY("auxiliary"),
        NON_TERMINAL("non_terminal_indicator"),
        INCLUDE("atom"),
        LINE_COUNT("integer"),
        NUMBER_OF_CLAUSES("integer"),
        NUMBER_OF_RULES("integer");

        private final String termType;

        /**
         * @param termType
         */
        @Contract(pure = true)
        EProperty ( String termType ) {
            this.termType = termType;
        }

        /**
         * @return
         */
        @Contract(pure = true)
        public
        String getTermType () {
            return termType;
        }
    }

    protected final List <Clause> code = new ArrayList <>();
    protected final PredicateIndicator predicateIndicator;
    protected final DirectiveProc proc;
    protected final CodeBase codeBase;

    /**
     * @param predicateIndicator
     * @param code
     * @param proc
     * @param codeBase
     */
    public
    PredicateDefinition ( PredicateIndicator predicateIndicator,
                          List <Clause> code,
                          DirectiveProc proc,
                          CodeBase codeBase ) {

        this.predicateIndicator = predicateIndicator;
        this.proc = proc;
        this.codeBase = codeBase;
        this.code.addAll(code);
    }

    /**
     * @return
     */
    public
    CodeBase getCodeBase () {
        return codeBase;
    }

    /**
     * @param clause
     */
    public
    void addClause ( Clause clause ) {

    }

    /**
     * @return
     */
    public
    List <Clause> getCode () {
        return code;
    }

    /**
     * @return
     */
    @Override
    public
    AtomTerm getName () {
        return null;
    }

    /**
     * @return
     */
    @Override
    public
    Term getValue () {
        return null;
    }
}

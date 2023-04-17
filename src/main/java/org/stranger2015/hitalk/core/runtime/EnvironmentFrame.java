package org.stranger2015.hitalk.core.runtime;

/**
 *
 */
public
class EnvironmentFrame extends Frame {
    private final int cec;

    /**
     *
     */
    public
    EnvironmentFrame ( int cp_index,
                       CodeClause cp_clause,
                       int variableCount,
                       int b0,
                       int cec) {

        super(cp_index, cp_clause, variableCount,b0);
        this.cec = cec;
    }

    /**
     * @return
     */
    @Override
    public
    String toString () {
        return "EnvironmentFrame %s".formatted(super.toString());
    }

    public
    int getCec () {
        return cec;
    }
}

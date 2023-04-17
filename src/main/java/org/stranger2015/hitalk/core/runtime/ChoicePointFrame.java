package org.stranger2015.hitalk.core.runtime;

/**
 *
 */
public
class ChoicePointFrame extends Frame {
    private final int e;
    private final int tr;
    private final int h;

    /**
     *
     * @param cp_index
     * @param cp_clause
     * @param variableCount
     * @param b0
     */
    public
    ChoicePointFrame ( int cp_index,
                       CodeClause cp_clause,
                       int e,
                       int tr,
                       int h,
                       int b0,
                       int variableCount ) {

        super(cp_index, cp_clause, variableCount, b0);

        this.e = e;
        this.tr = tr;
        this.h = h;

        for (int i = 0; i < variableCount; i++) {
            variables.get(i).copyFrom(getRegisterCell(i));
        }

        //        this.e = e;
//        this.cp_index = cp_index;
//        this.cp_clause = cp_clause;
//        this.variableCount = variableCount;
//        while (variableCount > variables.size()) {
//            variables.add(new MemoryCell());
//        }
//        for (int i = 0; i < variableCount; i++) {
//            variables.get(i).copyFrom(getRegisterCell(i));
//        }
//        this.tr = tr;
//        this.h = h;
//        this.b0 = b0;
//        this.currentClause = currentClause; // Functions as L

    }

    /**
     * @return
     */
    @Override
    public
    String toString () {
        return "ChoicePointFrame %s".formatted(super.toString());
    }

    /**
     * @return
     */
    @Override
    public
    int getE () {
        return e;
    }

    /**
     * @return
     */
    @Override
    public
    int getTr () {
        return tr;
    }

    /**
     * @return
     */
    @Override
    public
    int getH () {
        return h;
    }
}
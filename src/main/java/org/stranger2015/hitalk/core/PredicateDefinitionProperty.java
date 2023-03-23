package org.stranger2015.hitalk.core;

public
class PredicateDefinitionProperty implements IProperty {
    /**
     * @return
     */
    enum EProperty {
AUXILIARY,
NON_TERMINAL( NON_TERMINAL_INDICATOR ),
INCLUDE( ATOM ),
LINE_COUNT( INTEGER),
NUMBER_OF_CLAUSES( INTEGER ),
NUMBER_OF_RULES( INTEGER )

"auxiliary",
"non_terminal( non_terminal_indicator )",
"include( atom )",
"line_count( integer)",
"number_of_clauses( integer )",
"number_of_rules( integer )"

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

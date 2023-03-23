package org.stranger2015.hitalk.core;

/**
 *
 */
public
enum EReadState {
    READ_FIRST_PART(entityDirectiveGoal),
    READ_BODY,
    END_READ,
    ;

    EReadState () {
    }

    private static CompoundTerm entityDirectiveGoal;
}

package org.stranger2015.hitalk.core.compiler;

public
enum EInstructions {
    ALLOCATE,  //MODIFIED
    ARITHMETIC,
    ASSERT,
    ALLOCATE_CTX,            //NEW
    ALLOCATE_LAST_CTX,       //NEW
    ALLOCATE_L_CTX,          //NEW
    ALLOCATE_L_LAST_CTX,    //NEW
    CALL,
    CALL_VARIABLE,
    CUT,                     //MODIFIED
    DEALLOCATE,              //MODIFIED
    DEALLOCATE_CTX,          //NEW
    DEALLOCATE_LAST_CTX,     //NEW
    END_OF_QUERY,
    FAIL,
    GET_CONSTANT,
    GET_LIST,
    GET_NUMBER,
    GET_STRUCTURE,
    GET_VALUE,
    GET_VARIABLE,
    PROCEED,               //MODIFIED
    PUT_CONSTANT,
    PUT_LIST,
    PUT_NUMBER,
    PUT_STRUCTURE,
    PUT_VALUE,
    PUT_VARIABLE,
    RETRACT,
    RETRY_ME_ELSE,
    SET_CONSTANT,
    SET_NUMBER,
    SET_VALUE,
    SET_VARIABLE,
    TRUST_ME,
    TRY_ME,
    UNIFY_CONSTANT,
    UNIFY_NUMBER,
    UNIFY_VALUE,
    UNIFY_VARIABLE;
}

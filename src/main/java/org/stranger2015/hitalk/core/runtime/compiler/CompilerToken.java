package org.stranger2015.hitalk.core.runtime.compiler;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
/**
 * PrologToWAMCompiler token streams are used by the compiler to convert Prolog into WAM instructions.
 * This class is currently not used but in the future all token classes will be replaced by this one.
 * The purpose is that then compiler tokens can be reused for better assert/retract operations at runtime.
 *
 * @author Bas Testerink
 */

public
class CompilerToken {
    public
    CompilerToken () {
    }

    /**
     *
     */
    // All the token types:
    public enum ETokenKind {
        ALLOCATE,
        ARGUMENT_VARIABLE,
        CALL,
        CALL_VARIABLE,
        CONSTANT,
        CUT,
        DEALLOCATE,
        END_OF_HEAD,
        LIST,
        NUMBER,
        PROCEED,
        STRING_REPRESENTATION,
        STRUCTURE,
        SUBTERM_REGISTER
    }

    /**
     *
     */
    // Attributes
    private ETokenKind type;
    private int i1;
    private int i2;
    private String str;
    private double num;

    public
    CompilerToken ( ETokenKind type, int i1, int i2, String str, double num ) {
        set(type, i1, i2, str, num);
    }

    /**
     * Setter for the whole token.
     */
    public
    void set ( ETokenKind type, int i1, int i2, String str, double num ) {
        this.type = type;
        this.i1 = i1;
        this.i2 = i2;
        this.str = str;
        this.num = num;
    }

    // Getters/setters
    public
    ETokenKind getType () {
        return type;
    }

    public
    void setType ( ETokenKind type ) {
        this.type = type;
    }

    public
    int getI1 () {
        return i1;
    }

    public
    void setI1 ( int i ) {
        i1 = i;
    }

    public
    int getI2 () {
        return i2;
    }

    public
    void setI2 ( int i ) {
        i2 = i;
    }

    public
    String getStr () {
        return str;
    }

    public
    void setStr ( String str ) {
        this.str = str;
    }

    public
    double getNum () {
        return num;
    }

    public
    void setNum ( double num ) {
        this.num = num;
    }

    public
    String toString () {
        StringBuilder r = new StringBuilder();
        r.append("<");
        switch (type) {
            case ALLOCATE -> r.append("allocate ").append(i1);
            case ARGUMENT_VARIABLE ->
                    r.append("A").append(i2 + 1).append(" = ").append(varRegisterToString(i1)).append(" = ").append(str);
            case CALL -> r.append("call ").append(str).append("/").append(i1);
            case CALL_VARIABLE -> r.append("call %s".formatted(varRegisterToString(i1)));
            case CONSTANT -> r.append("constant ").append(str);
            case CUT -> r.append("cut");
            case DEALLOCATE -> r.append("deallocate");
            case END_OF_HEAD -> r.append("End of Head");
            case LIST -> r.append("list X").append(i1 + 1);
            case NUMBER -> r.append("num ").append(num);
            case PROCEED -> r.append("proceed");
            case STRING_REPRESENTATION -> r.append("String representation = ").append(str);
            case STRUCTURE -> r.append("X").append(i1 + 1).append(" = ").append(str).append("/").append(i2);
            case SUBTERM_REGISTER -> r.append(varRegisterToString(i1));
        }
        r.append(">");

        return r.toString();
    }

    /**
     *
     * @param i1
     * @return
     */
    @Contract(pure = true)
    public @NotNull
    String varRegisterToString ( int i1 ) {

        return String.valueOf(i1);
    }
}

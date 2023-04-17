package org.stranger2015.hitalk.core.compiler;

import org.jetbrains.annotations.Contract;
import org.stranger2015.hitalk.core.PredicateIndicator;
import org.stranger2015.hitalk.core.compiler.instructions.Instruction;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class is the result of the compilation process. It contains WAM instructions, a string representation of the code and the functor/arity.
 * This class can be removed in the future as <code>CodeClause</code> instances can also be used instead of CompiledClause.
 *
 * @author Bas Testerink
 */
public
final
class CompiledClause {
    private final String prologString;

    /**
     * @return
     */
    @Contract(pure = true)
    public
    PredicateIndicator getPredicateIndicator () {
        return predicateIndicator;
    }

    //    private final String functor;
    private final PredicateIndicator predicateIndicator;
    private final List <Instruction> instructions = new ArrayList <>();

    /**
     *
     */
    public
    CompiledClause ( String prologString,
                     PredicateIndicator predicateIndicator,
                     List <Instruction> instructions ){
        this.prologString = prologString;
        this.predicateIndicator = predicateIndicator;
//        this.functor = functor;
        this.instructions.addAll(instructions);
    }
    /**
     * @return
     */
    public
    String toString () {
        return "%s\r\n%s\r\n%s\r\n".formatted(predicateIndicator, prologString, instructions);
    }

    /**
     * @return
     */
    @Contract(pure = true)
    public
    String prologString () {
        return prologString;
    }

    /**
     * @return
     */
//    @Contract(pure = true)
//    public
//    String functor () {
//        return functor;
//    }
//
    /**
     * @return
     */
    @Contract(pure = true)
    public
    List <Instruction> instructions () {
        return instructions;
    }

    /**
     * @param obj
     * @return
     */
    @Contract(value = "null -> false", pure = true)
    @Override
    public
    boolean equals ( Object obj ) {
        boolean result;
        if (obj == this) {
            result = true;
        }
        else if (obj == null || obj.getClass() != this.getClass()) {
            result = false;
        }
        else {
            var that = (CompiledClause) obj;
            result = Objects.equals(this.prologString, that.prologString) &&
                    Objects.equals(this.predicateIndicator, that.predicateIndicator) &&
                    Objects.equals(this.instructions, that.instructions);
        }

        return result;
    }

    /**
     * @return
     */
    @Override
    public
    int hashCode () {
        return Objects.hash(prologString, predicateIndicator, instructions);
    }

}

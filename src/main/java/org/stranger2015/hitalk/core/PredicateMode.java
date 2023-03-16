package org.stranger2015.hitalk.core;

import org.jetbrains.annotations.Contract;

import java.util.List;
import java.util.Objects;

/**
 *
 */
public
final
class PredicateMode {
    private static CompoundTerm mode;
    private final List <ModeIndicator> args;

    public
    PredicateMode ( List <ModeIndicator> args ) {
        this.args = args;
    }

    /**
     * @return
     */
    public static
    CompoundTerm getMode () {
        return mode;
    }

    public
    List <ModeIndicator> args () {
        return args;
    }

    @Override
    public
    boolean equals ( java.lang.Object obj ) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (PredicateMode) obj;
        return Objects.equals(this.args, that.args);
    }

    @Override
    public
    int hashCode () {
        return Objects.hash(args);
    }

    @Override
    public
    String toString () {
        return "PredicateMode[" +
                "args=" + args + ']';
    }

}

package org.stranger2015.hitalk.core.compiler;

import org.stranger2015.hitalk.core.Clause;
import org.stranger2015.hitalk.core.CompoundTerm;
import org.stranger2015.hitalk.core.ListTerm;
import org.stranger2015.hitalk.core.Term;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 */
public
class Preprocessor {
    private final Clause clause;

    private final List <ITransformer> transformers = new ArrayList <>();

    /**
     * @param clause
     */
    public
    Preprocessor ( Clause clause ) {
        transformers.add(new TermExpander());
        transformers.add(new GoalExpander());
        transformers.add(new Flattener());

        this.clause = clause;
    }

    /**
     * @return
     */
    public
    Clause clause () {
        return clause;
    }

    /**
     * @param clause
     * @return
     */
    private
    Clause flatten ( Clause clause ) {
        CompoundTerm flattenHead = (CompoundTerm) flatten(clause.getHead());
        ListTerm flattenBody = (ListTerm) flatten(clause.getBody());

        return clause;
    }

    private
    Term flatten ( Term head ) {

        return head;
    }

    @Override
    public
    boolean equals ( Object obj ) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (Preprocessor) obj;
        return Objects.equals(this.clause, that.clause);
    }

    @Override
    public
    int hashCode () {
        return Objects.hash(clause);
    }

    @Override
    public
    String toString () {
        return "Preprocessor[clause=%s]".formatted(clause);
    }

}


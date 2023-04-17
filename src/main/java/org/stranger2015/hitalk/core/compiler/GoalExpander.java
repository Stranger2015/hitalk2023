package org.stranger2015.hitalk.core.compiler;

import org.stranger2015.hitalk.core.Term;

import java.util.List;

import static org.stranger2015.hitalk.core.AtomTerm.BYPASS;

public
class GoalExpander extends TermExpander {
    public
    GoalExpander () {

    }

    /**
     * default exp {x}
     *
     * @param goal
     * @return
     */
    public
    List <Term> expand ( Term goal ) {
        List <Term> result = List.of();
        if (goal.isAtom() && goal == BYPASS) {
            termsToExpand.remove(goal);//fixme
            result = List.of(goal);
        }
        else {
            /*termsToExpand.addAll(*/
            Object l = call(AtomTerm.createAtom("goal_expansion"));
        }

        return result;
    }
}

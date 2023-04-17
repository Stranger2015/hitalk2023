package org.stranger2015.hitalk.core.compiler.instructions.extension;

import org.stranger2015.hitalk.core.Callable;

/**
 *
 */
public
class Context {

    private Context currentContext;

    /**
     * @return
     */
    public
    Context create () {
        return new Context();
    }

    /**
     * <b>Context Switch - {@code <</2<}/b>
     * Templates
     * << (+entity descriptor list, +callable term)
     * Description
     * Context :< Goal evaluates Goal in context Context, ie. totally bypassing the current context.
     * <p>
     * Context <code> :< Goal</code> evaluates Goal in context <code>Context</code>, ie.
     * totally bypassing the current context.
     *
     * @return4
     */
    public
    Context contextSwitch () {

        return null;
    }

    /**
     * <code> :< Context</code> unifies <code>Context</code> with the current context.
     *
     * @return
     */
    public
    Context currentContext ( Context context ) {
        context.getCurrentContext();
        return context.unify();
    }

    private
    boolean unify () {
        return false;
    }

    /**
     * <code> :> Context</code> unifies with the calling context.
     *
     * @return
     */
    public
    Context callingContext () {

        return null;
    }

    /**
     * <b>Context Extension - <code>:>/2</code> </b>
     * <p>
     * <b>Templates</b>
     * <p>
     * {@code :> (+entity descriptor, +callable term)}
     * <p>
     * <b>Description</b>
     * <p>
     * {@code entity :> Goal} extends the current context with {@code Context} before attempting to reduce goal {@code Goal}.
     * This operator behaves as if defined by the <i>Prolog</i> clause:
     * <p>
     * {@code U :> G :- :< C, [U|C] :< G. }
     *
     * @return
     */
    public
    Context contextExtension () {

        return null;
    }

    /**
     * <b>Guided Context Traversal - {@code ::/2}</b>
     * <p>
     * <b>Templates</b>
     * <p>
     * {@code ::(+entity_identifier, +callable term) }
     * <p>
     * <b>Description</b>
     * <p>
     * {@code entity :: Goal} behaves as if defined by the <i>Prolog</i> clause:
     * <p>
     * {@code U :: G :- :< C, GC = [U|_], suffix_chk(GC, C), GC :< G.}
     * <p>
     * Where {@code suffix_chk/2 } is a deterministic predicate where {@code suffix_chk(SUFFIX, LIST)}
     * succeeds if {@code SUFFIX} is a suffix of {@code LIST}.
     */
    public
    Context guidedContextTraversal () {

        return null;
    }

    /**
     * <b>Supercontext - {@code :^/1}</b>
     * <p>
     * <b>Templates</b>
     * <p>
     * {@code :^(+callable term)}
     * <p>
     * <b>Description</b>
     * <p>
     * {@code ^ Goal} evaluates {@code Goal} in the context obtained by dropping the topmost entity
     * of the current context.
     * This operator behaves as if defined by the <i>Prolog</i> clause:
     * <p>
     * {@code :^ G :- :< [_|C], C :< G.}
     */
    public
    Context superContext () {

        return null;
    }

    /**
     * <b>Lazy Call - <code> :#/1 </code></b>
     * <p>
     * <b>Templates</b>
     * <p>
     * <code> :# G :- :> C, C :< G. </code>
     * <p>
     * <b>Description</b>
     * <p>
     * <code># Goal</code> <code>Goal</code> evaluates <code>Goal</code> in the calling context.
     * This operator behaves as if defined by the <i>Prolog</i> clause:
     * <p>
     * <code> :# G :- :> C, C :< G. </code>
     */
    public
    void lazyCall ( Callable goal ) {

    }

    /**
     * @return
     */
    public
    Context getCurrentContext () {
        return currentContext;
    }
}

package org.stranger2015.hitalk.core.runtime;

import org.stranger2015.hitalk.core.PredicateIndicator;

import java.util.List;

/**
 * NEW INSTRUCTIONS:
 * =================
 * <p>
 * allocate_ctx
 * allocate_last_ctx
 * allocate_l_ctx
 * allocate_l_last_ctx
 * deallocate_ctx
 * deallocate_last_ctx
 * call_lazy
 * execute_lazy
 * call_eager
 * execute_eager
 * trust_extends
 * <p>
 * NEW MEMORY AREAS:
 * ====================
 *
 * <p>
 * unit descriptor area
 * context-stack area
 * <p>
 * NEW STRUCTURES:
 * ===============
 *
 * <p>
 * ENVIRONMENT
 * ===========
 *
 * <p>
 * CEC
 * E
 * CP
 * NP
 * B
 * CHP
 * <p>
 * X1
 * Xk
 * MP =
 * K
 * IE TOP
 * NP
 * E
 * CP
 * B
 * NEXT
 * TR
 * CHP EXT
 * <p>
 * CEC
 * EC
 * LC
 * <p>
 * INSTANCE
 * ========
 *
 * <p>
 * LC
 * UNIT_REF
 * CHAIN
 * IE_TOP
 * eager &
 * extend area
 * <p>
 * CEC
 * EC
 * <p>
 * <p>
 * NEW REGISTERS:
 * ================
 * IE_top EC
 * LC CEC
 * <p>
 * <p>
 * MODIFIED WAM INSTRUCTIONS:
 * ==========================
 * <p>
 * allocate
 * deallocate
 * proceed
 * cut
 */
public
class CtxFrameStack {

    /**
     *
     */
    private final
    List <PredicateIndicator> entityIds;

    /**
     *
     */
    public
    CtxFrameStack ( List <PredicateIndicator> entityIds ) {
        this.entityIds = entityIds;
    }

    /**
     * @param entityId
     */
    public
    void add ( PredicateIndicator entityId ) {

    }

    /**
     * @return
     */
    public
    CtxFrame newFrame () {
        return newCtxFrame();
    }

    /**
     * @return
     */
    private
    CtxFrame newCtxFrame () {
        return new CtxFrame();
    }

    /**
     * @return
     */
    public
    List <PredicateIndicator> getEntityIds () {
        return entityIds;
    }
}

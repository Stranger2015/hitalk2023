package org.stranger2015.hitalk.core.runtime;

import org.stranger2015.hitalk.core.compiler.CompiledClause;
import org.stranger2015.hitalk.core.compiler.instructions.*;
import org.stranger2015.hitalk.core.runtime.compiler.CompilerToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class contains all the compiled Prolog code that the engine uses to answer queries.
 *
 * @author Bas Testerink
 */
public
class CodeBase {
    private final Map <String, List <List <CompilerToken>>> tokens = new HashMap <>();
    // Contains all the compiler tokens of
    //	the code, can be used for changing the code base and recompile it
    private final Map <String, CodeClause> clauses = new HashMap <>();
    private final Map <String, CodeClause> builtin = new HashMap <>();
    private CodeClause query = new CodeClause(); // The latest compiled query

    public
    CodeBase () {
        makeBuiltIn();
    }

    /**
     * Add all the input tokens to the token based (appending after the current tokens in case of same predicates).
     */
    public
    Map <String, List <List <CompilerToken>>> mergeTokens ( Map <String, List <List <CompilerToken>>> input ) {
        for (String f : input.keySet()) {
            List <List <CompilerToken>> clauses = tokens.computeIfAbsent(f, k -> new ArrayList <>());
            clauses.addAll(input.get(f));
        }

        return tokens;
    }

    /**
     * Override the current query.
     */
    public
    void setQuery ( CompiledClause query ) {
        this.query.reset();
        this.query.setInstructions(query.instructions());
        this.query.setPrologString(query.prologString());
    }

    /**
     * Override the current code.
     */
    public
    void setCode ( Map <String, List <CompiledClause>> code ) {
        for (String f : code.keySet()) {
            boolean initial = true;
            CodeClause clause = null;
            List <CompiledClause> clauses = code.get(f);
            for (CompiledClause c : clauses) {
                CodeClause newClause = new CodeClause();
                newClause.belongsTo = c.getPredicateIndicator();
                newClause.setInstructions(c.instructions());
                newClause.setPrologString(c.prologString());
                newClause.setPrevious(clause);
                if (clause != null) {
                    clause.setNext(newClause);
                }
                if (initial) {
                    this.clauses.put(f, newClause);
                    initial = false;
                }
                clause = newClause;
            }
        }
    }

    /**
     * Remove a fact. Used by retract. It is assumed that the to-be-retracted fact is fully substituted with
     * the first possible fact.
     */
    public
    void removeFact ( String functor, String fact ) {
        CodeClause clause = clauses.get(functor);
        while (!clause.getPrologString().equals(fact)) {
            clause = clause.getNext();
            if (clause == null) {
                return;
            }
        }
        Instruction i = clause.getInstruction(0);
        if (i instanceof TryMe) {
            Instruction i2 = clause.getNext().getInstruction(0);
            if (i2 instanceof RetryMeElse) {
                // Remove first of >2 clauses
                clause.getNext().getInstructions().set(0, new TryMe(((TryMe) i).getArity()));
            }
            else if (i2 instanceof TrustMe) {
                // Remove first of 2 clauses
                clause.getNext().getInstructions().remove(0); // Remove the TryMe instruction
            }
            clauses.put(functor, clause.getNext()); // Replace the top-of-list pointer
        }
        else if (i instanceof RetryMeElse) {
            // Remove middle of >2 (do nothing, just remove the clause)
        }
        else if (i instanceof TrustMe) {
            Instruction i2 = clause.getPrevious().getInstruction(0);
            if (i2 instanceof RetryMeElse) {
                // Remove last of >2 clauses
                clause.getPrevious().getInstructions().set(0, new TrustMe()); // Set retry to trust
            }
            else if (i2 instanceof TryMe) {
                clause.getPrevious().getInstructions().remove(0); // Remove the try_me instruction
            }
        }
        else {
            // Removed the last fact
            clauses.remove(functor);
        }
        clause.remove(); // Simply remove the clause
    }

    /**
     * Add a code clause (used by assert).
     */
    public
    void addCodeClause ( CompiledClause c ) {
        CodeClause newClause = new CodeClause();
        newClause.setInstructions(c.instructions());
        newClause.setPrologString(c.prologString());
        newClause.belongsTo = c.functor();
        CodeClause clause = clauses.get(c.functor());
        if (clause == null) {
            // first of its kind
            clauses.put(c.functor(), newClause);
        }
        else {
            while (clause.getNext() != null) {
                clause = clause.getNext(); // Go to last clause
            }
            Instruction i = clause.getInstruction(0);
            if (i instanceof TrustMe) { // There was already a bunch of facts, so make new trust entry.
                clause.getInstructions().set(0, new RetryMeElse());
                newClause.getInstructions().add(0, new TrustMe());
            }
            else { // There was only one fact, add tryme and trustme
                int arity = Integer.parseInt(c.functor().substring(c.functor().lastIndexOf('/') + 1));
                clause.getInstructions().add(0, new TryMe(arity));
                newClause.getInstructions().add(0, new TrustMe());
            }
            clause.setNext(newClause);
            newClause.setPrevious(clause);
        }
    }

    /**
     * @param query
     */
    // Setters/getters
    public
    void setQuery ( CodeClause query ) {
        this.query = query;
    }

    /**
     * @return
     */
    public
    CodeClause getQueryClause () {
        return query;
    }

    /**
     * Get the top clause for a functor.
     */
    public
    CodeClause getClause ( String functor ) {
        CodeClause code = clauses.get(functor);
        while (code != null && code.isRetracted()) {
            code = code.getNext();
        }
        if (code == null) {
            return builtin.get(functor);
        }

        return code;
    }

    /**
     * Make the special built-in code.
     */
    private
    void makeBuiltIn () {
        // Arithmetic
        CodeClause c = new CodeClause();
        List <Instruction> instr = new ArrayList <>();
        instr.add(new Arithmetic());
        c.setInstructions(instr);
        c.belongsTo = " is /2";
        builtin.put(" is /2", c);

        // Retract
        c = new CodeClause();
        instr = new ArrayList <>();
        instr.add(new TryMe(1));
        instr.add(new Allocate(1));
        instr.add(new GetVariable(-1, 0));
        instr.add(new CallVariable(-1));
        instr.add(new Cut());
        instr.add(new PutValue(-1, 0));
        instr.add(new Retract());
        instr.add(new Deallocate());
        instr.add(new TrustMe());
        instr.add(new Proceed());
        c.setInstructions(instr);
        c.belongsTo = "retract/1";
        builtin.put("retract/1", c);

        // Assert
        c = new CodeClause();
        instr = new ArrayList <>();
        instr.add(new Assert());
        c.setInstructions(instr);
        c.belongsTo = "assert/1";
        builtin.put("assert/1", c);
    }

    public
    String toString () {
        StringBuilder s = new StringBuilder();
        for (String f : clauses.keySet()) {
            s.append("%s:\r\n".formatted(f));
            CodeClause clause = clauses.get(f);
            int c = 0;
            while (clause != null) {
                Instruction instruction;
                s.append("\t Prolog: %s\r\n".formatted(clause.getPrologString()));
                for (int i = 0; i < clause.size(); i++) {
                    instruction = clause.getInstruction(i);
                    s.append("\t%d: %s\r\n".formatted(c, instruction));
                    c++;
                }
                clause = clause.getNext();
            }
        }
        s.append("Current query: %s\r\n".formatted(query.getPrologString()));
        int c = 0;
        Instruction instruction;
        for (int i = 0; i < query.size(); i++) {
            instruction = query.getInstruction(i);
            s.append("\t%d: %s\r\n".formatted(c, instruction));
            c++;
        }

        return s.toString();
    }

} 
package org.stranger2015.hitalk.core.compiler;

import org.stranger2015.hitalk.core.PrologParser;
import org.stranger2015.hitalk.core.Term;
import org.stranger2015.hitalk.core.compiler.instructions.*;
import org.stranger2015.hitalk.core.compiler.tokens.*;
import org.stranger2015.hitalk.core.runtime.CodeBase;
import org.stranger2015.hitalk.core.runtime.compiler.CompilerToken;

import java.io.*;
import java.util.*;

/*
 A Prolog Object-Oriented System: an
Exercise in Contextual Logic Programming

An Extended Warren Abstract
Machine for the Execution of Structured Logic Programs

The design of an abstract
machine for efficient implementation of contexts in Logic
Programming

Contextual Logic Programming,

Implementing Structured Logic Programs on a Dedicated
VLSI Coprocessor
 */

/**
 * I have never encountered a document that explains how to compile Prolog for the WAM. The compilation process I devised is based on
 * what appears to be patterns from the tutorial book on the WAM. For instance, the assignment of registers to subterms is done in
 * breadth first order, even though it says nowhere that this is the correct way to do it. If anyone knows a good way to compile Prolog
 * then please let me know.
 * <p>
 * Notes on the implementation:
 * Input is first converted into tokens. Tokens are created in three steps 1) a preprocessor checks what variables are permanent and fixes
 * various parsetree issues, 2) a register assigner gives each subterm a register, 3) the WAMTokenizer travels the tree and creates a list of
 * tokens. The tokens are then handled here as per the explanations in the WAM tutorial book.
 *
 * @author Bas Testerink
 */
public
class PrologToWAMCompiler {
    /**
     * Compile a file and add it to the code base. Redefinitions of predicates append to the old ones (so they do not override!).
     */
    public static
    void compileFile ( String file, CodeBase codebase ) throws FileNotFoundException {
        Map <String, List <List <CompilerToken>>> tokens = codebase.mergeTokens(tokenizeFile(file));
        codebase.setCode(compile(tokens));
    }

    /**
     * @param file
     * @return
     * @throws FileNotFoundException
     */
//    private static
//    Map <String, List <List <CompilerToken>>> tokenizeFile ( String file ) throws FileNotFoundException {
//
//        return new HashMap <>();
//    }

    /**
     * Compile the lists of tokens (one for each clause) for predicates.
     */
    private static
    Map <String, List <CompiledClause>> compile ( Map <String, List <List <CompilerToken>>> tokens ) {
        Map <String, List <CompiledClause>> result = new HashMap <>();
        for (String functor : tokens.keySet()) {
            if (!functor.equals("fail/0")) { // Cannot define the fail predicate as it should always fail
                List <CompiledClause> clauses = new ArrayList <>();
                List <List <CompilerToken>> clauseTokens = tokens.get(functor);
                int arity = Integer.parseInt(functor.substring(functor.lastIndexOf('/') + 1));
                for (int i = 0; i < clauseTokens.size(); i++) {
                    CompiledClause compiledClause = compileSingleClause(clauseTokens.get(i), functor, false);
                    if (i == 0 && clauseTokens.size() > 1) {
                        assert compiledClause != null;
                        compiledClause.instructions().add(0, new TryMe(arity));
                    }
                    if (i > 0 && clauseTokens.size() > 2 && clauseTokens.size() - 1 > i) {
                        compiledClause.instructions().add(0, new RetryMeElse());
                    }
                    if (i > 0 && clauseTokens.size() - 1 == i) {
                        compiledClause.instructions().add(0, new TrustMe());
                    }
                    clauses.add(compiledClause);
                }
                result.put(functor, clauses);
            }
        }

        return result;
    }
//
//   public static
//    CompiledClause compileSingleClause ( List <CompilerToken> compileTokens, String functor, boolean isQuery ) {
//        return null;
//    }

    /**
     * Compile a single fact (used by assert).
     */
    public static
    CompiledClause compileStringFact ( String functor, String fact ) {
        return compileSingleClause(tokenizeFactString(fact), functor, false);
    }


    /**
     * Compile a single clause. So no try/retry/trust/indexing instructions are added here.
     */
    private static
    CompiledClause compileSingleClause ( List <CompilerToken> tokens, String functor, boolean isQuery )
            throws IllegalStateException {

        final List <Instruction> instructions = new ArrayList <>();

        for (int c = 1; c < tokens.size(); c++) {
            CompilerToken t = tokens.get(c);
            final Set <Integer> encountered = new HashSet <>();

            switch (t.getType()) {
//            } else if (t instanceof CallVariableToken) {
////                instructions.add(new CallVariable(((CallVariableToken) t).getRegister()));
//                final Set <Integer> toRemove = new HashSet <>();
//                for (int i : encountered) { // needed? A variable call always immediately follows some other call,
//                or is the body of a chain rule.
//                    if (i >= 0) {
//                        toRemove.add(i);
//                    }
//                }
//                encountered.removeAll(toRemove);
//            } else if (t instanceof CallToken) {
                //instructions.add(new Call(((CallToken) t).getFunctor(), ((CallToken) t).getArity()));
                // After a call you have to build the next query. XRegisters are reset after
                // each top term. So you need to remove their occurrences.
//              case Set <Integer> toRemove = new HashSet <>();
//                for (int i : encountered) {
//                    if (i >= 0) {
//                        toRemove.add(i);
//                    }
//                }
//                encountered.removeAll(toRemove);
////            } else if (t instanceof NumberToken.EndOfHead) {
//                isQuery = true;
                //} else if (isQuery) instructions.add(processQueryTermToken(t, encountered));
                /*else*/

                case ALLOCATE -> {
                }
                case ARGUMENT_VARIABLE -> {
                }
                case CALL -> {
                    instructions.add(new Call(((CallToken) t).getFunctor(), ((CallToken) t).getArity()));
                   final Set<Integer> toRemove = new HashSet <>();
                    for (int i : encountered) {
                        if (i >= 0) {
                            toRemove.add(i);
                        }
                    }
                    encountered.removeAll(toRemove);
                }
                case CALL_VARIABLE -> {
                    instructions.add(new CallVariable(((CallVariableToken) t).getRegister()));
//
//                    toRemove = new HashSet <>();
//                    for (int i : encountered) { // needed? A variable call always immediately follows some other call,
//                    or is the body of a chain rule.
//                        if (i >= 0) {
//                            toRemove.add(i);
//                        }
//                    }
//                    encountered.removeAll(toRemove);
                }
//                case CONSTANT -> {
//                }
                case CUT -> {
                    instructions.add(new Cut());
                }
                case DEALLOCATE -> {
                    instructions.add(new Deallocate());
                }
                case END_OF_HEAD -> {
                    isQuery = true;
                }
//                case LIST -> {
//                }
//                case NUMBER -> {
//                }
                case PROCEED -> {
                    instructions.add(new Proceed());
                }

//                case STRING_REPRESENTATION -> {
//                }
//                case STRUCTURE -> {
//                }
//                case SUBTERM_REGISTER -> {
//                }
                default -> {
                    instructions.add(processQueryTermToken(t, encountered));
//                        throw new IllegalStateException("Unexpected value: %s".formatted(t.getType()));
                }
            }

            return new CompiledClause(
                    ((StringRepresentationToken) tokens.get(0)).value(),
                    functor,
                    instructions
            );
        }
        return null;
    }

        /**
         * Handle the encounter of a register inside a fact/program context.
         */
        protected static Instruction processProgramTermToken (CompilerToken t, Set < Integer > encountered){
//       switch (){
            if (t instanceof ListToken) {
                return new GetList(((ListToken) t).register());
            }
            else if (t instanceof Structure s) {
                return new GetStructure(s.register(), s.functor(), s.arity());
            }
            else if (t instanceof ConstantToken s) {
                if (s.getArgument() < 0) return new UnifyConstant(s.getName());
                else return new GetConstant(s.getArgument(), s.getName());
            }
            else if (t instanceof NumberToken s) {
                if (s.argument() < 0) return new UnifyNumber(s.number());
                else return new GetNumber(s.argument(), s.number());
            }
            else if (t instanceof SubtermRegister s) {
                if (encountered.contains(s.getRegister())) {
                    return new UnifyValue(s.getRegister());
                }
                else {
                    encountered.add(s.getRegister());
                    return new UnifyVariable(s.getRegister());
                }
            }
            else if (t instanceof ArgumentVariable s) {
                if (encountered.contains(s.primeRegister())) {
                    return new GetValue(s.primeRegister(), s.argumentRegister());
                }
                else {
                    encountered.add(s.primeRegister());
                    return new GetVariable(s.primeRegister(), s.argumentRegister());
                }
            }
            return null;
        }

        /**
         * Handle the encounter of a register inside a body goal/query context.
         */
        private static Instruction processQueryTermToken ( CompilerToken t, Set < Integer > encountered){
            if (t instanceof ListToken) {
                encountered.add(((ListToken) t).register());
                return new PutList(((ListToken) t).register());
            }
            else if (t instanceof Structure s) {
                encountered.add(((Structure) t).register());
                return new PutStructure(s.register(), s.functor(), s.arity());
            }
            else if (t instanceof ConstantToken s) {
                if (s.getArgument() < 0) {
                    return new SetConstant(s.getName());
                }
                else {
                    return new PutConstant(s.getArgument(), s.getName());
                }
            }
            else if (t instanceof NumberToken s) {
                if (s.argument() < 0) return new SetNumber(s.number());
                else {
                    return new PutNumber(s.argument(), s.number());
                }
            }
            else if (t instanceof SubtermRegister s) {
                if (encountered.contains(s.getRegister())) {
                    return new SetValue(s.getRegister());
                }
                else {
                    encountered.add(s.getRegister());
                    return new SetVariable(s.getRegister());
                }
            }
            else if (t instanceof ArgumentVariable s) {
                if (encountered.contains(s.primeRegister())) {
                    return new PutValue(s.primeRegister(), s.argumentRegister());
                }
                else {
                    encountered.add(s.primeRegister());
                    return new PutVariable(s.primeRegister(), s.argumentRegister());
                }
            }
            return null;
        }

//        /**
//         * Compile a query. Will overwrite the last compiled query.
//         *
//         * @return
//         */
//        public static
//        Map <String, Integer> compileQuery (String query, CodeBase codebase){
//
////            WAMTokenizer tokenizer = visitor(new ANTLRInputStream(query));
//            PrologParser parser = new PrologParser(new PrologInputStream(query));
////            List <CompilerToken> tokens = tokenizer.getQuery();
////            CompiledClause queryInstructions = compileSingleClause(tokens, "query", true);
////            queryInstructions.instructions().add(new EndOfQuery());
////            codebase.setQuery(queryInstructions);
////
//            return tokenizer.getQueryVariablePositions();
//        }
//
        // Some calls for tokenizing input.

        public static Map <String, List <List <CompilerToken>>> tokenizeFile (String file){
            try {
//            ANTLRFileStream stream = new ANTLRFileStream(file);
//            return visitor(stream).getTokens();
                PrologParser parser = new PrologParser(new PrologInputStream(new FileInputStream(file)));
                for (int i = 0; ; i++) {
                    Term token = parser.nextTerm(true);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        // For assert
        public static
        List <CompilerToken> tokenizeFactString (String fact){
//        ANTLRInputStream stream = new ANTLRInputStream(fact);
//        Map<String, List<List<CompileToken>>> map = visitor(stream).getTokens();
//        return map.get(map.keySet().termIterator().next()).get(0);
            return null;
        }

//    /**
//     * Create a visitor for a char stream. ANTLR will create a parsetree and then the visitor can convert it into tokens.
//     private static
//    WAMTokenizer visitor( CharStream stream) {
//        PrologLexer lexer = new PrologLexer(stream);
////        CommonTokenStream tokens = new CommonTokenStream(lexer);
//        PrologParser parser = new PrologParser(tokens);
//        ParseTree tree = parser.toprule();
//        WAMTokenizer visitor = new WAMTokenizer();
//        visitor.p = parser; // For debugging, needed for outputting register assignment
//        visitor.visit(tree);
//        return visitor;
//    }

//    /**
//     * @param t
//     * @param encountered
//     * @return
//     */
//    @Contract(pure = true)
//    private static @Nullable
//    Instruction processProgramTermToken ( CompilerToken t, Set <Integer> encountered ) {
//
//
//        return null;
//    }
}

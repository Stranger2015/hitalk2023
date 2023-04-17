package org.stranger2015.hitalk.core.compiler;

import org.stranger2015.hitalk.core.*;
import org.stranger2015.hitalk.core.compiler.instructions.Instruction;
import org.stranger2015.hitalk.core.runtime.CodeBase;
import org.stranger2015.hitalk.core.runtime.PrologRuntime;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import static org.stranger2015.hitalk.core.AtomTerm.IMPLIES;
import static org.stranger2015.hitalk.core.ListTerm.EMPTY_LIST;
import static org.stranger2015.hitalk.core.PrologParser.END_OF_FILE;

/**
 * % usage: ?-wam_compiler(ProcNames,WAMcode).
 * %   where ProcNames is a list of predicate indicators
 * %   you want to compile (input)
 * %   WAMcode is output WAM code (output)
 * % example: ?-wam_compiler([q/2,p/2],W).
 * <p>
 * :-op(400,xfx,:).
 * <p>
 * wam_compiler( ClausesNames, WAMcode  ):-
 * preprocess( ClausesNames, Program ),
 * compile( Program, WAMcode ),
 * nl, list_WAM_code( WAMcode ).
 * <p>
 * preprocess([ ClauseName | RestNames ],[ ClauseName-BinProgram | RestPrograms ]):-
 * collect_program( ClauseName,Program),
 * binarize_and_flatten(Program,BinProgram),
 * preprocess(RestNames,RestPrograms).
 * <p>
 * preprocess([],[]).
 * <p>
 * compile([ ProcName-Procedure | RestProcedures ],Code):-
 * compile_procedure(TailCode,ProcName,Procedure,Code),
 * compile(RestProcedures,TailCode).
 * <p>
 * compile([],[]).
 * <p>
 * collect_program(Functor/Arity,Procedure):-
 * functor(Head,Functor,Arity),
 * findall((Head:-Body),clause(Head,Body),Procedure).
 * <p>
 * construction of the program's normal form
 * <p>
 * binarize_and_flatten([Clause|Rest],[FlatClause|FlatRest]):-
 * binarize(Clause,BinClause),
 * flatten(BinClause,FlatClause),
 * binarize_and_flatten(Rest,FlatRest).
 * <p>
 * binarize_and_flatten([],[]).
 * <p>
 * binarize((H:-B),(BinH:-BinB)):-
 * binarize_term(Cont,H,BinH),
 * binarize_term(Cont,B,BinB).
 * <p>
 * binarize_term(Cont,true,call(Cont)).
 * <p>
 * binarize_term(Cont,(A,B),BinForm):-
 * binarize_term(Cont,B,BinB),
 * binarize_term(BinB,A,BinForm).
 * <p>
 * binarize_term(Cont,Term,BinTerm):-
 * Term \=(_,_),
 * Term \==true,
 * Term=..List,
 * add_to_list(List,Cont,BinList),
 * BinTerm=..BinList.
 * <p>
 * add_to_list([H|T],X,[H|NewX]):-
 * add_to_list(T,X,NewX).
 * <p>
 * add_to_list([],X,[X]).
 * <p>
 * flatten((H:-B),(FlatH:-FlatBody)):-
 * flatten_term(true,H,FlatH,HeadAdditions),
 * flatten_term(HeadAdditions,B,FlatB,Additions),
 * and(Additions,FlatB,FlatBody).
 * <p>
 * flatten_term(Eq,Term,FlatTerm,NewEq):-
 * Term=..[F|Args],
 * flatten_args(Eq,Args,FlatArgs,NewEq),
 * FlatTerm=..[F|FlatArgs].
 * <p>
 * flatten_args(Eq,[A|T],[FlatA|FlatT],NewEq):-
 * flatten_arg(Eq,A,FlatA,SubEq),
 * flatten_args(SubEq,T,FlatT,NewEq).
 * <p>
 * flatten_args(Eq,[],[],Eq).
 * <p>
 * flatten_arg(Eq,V,V,Eq):-
 * var(V).
 * flatten_arg(Eq,Term,FlatTerm,NewEq):-
 * nonvar(Term),
 * Term=..[F|Args],
 * zero_args(Eq,Args,FlatArgs,NewEq),
 * FlatTerm=..[F|FlatArgs].
 * <p>
 * zero_args(Eq,[A|T],[FlatA|FlatT],NewEq):-
 * zero_arg(Eq,A,FlatA,SubEq),
 * zero_args(SubEq,T,FlatT,NewEq).
 * <p>
 * zero_args(Eq,[],[],Eq).
 * <p>
 * zero_arg(Eq,V,V,Eq):-
 * var(V).
 * <p>
 * zero_arg(Eq,T,V,NewEq):-
 * nonvar(T),
 * T=..[F|Args],
 * zero_args(Eq,Args,FlatArgs,SubEq),
 * FlatT=..[F|FlatArgs],
 * and(SubEq,V=FlatT,NewEq).
 * <p>
 * and(true,A,A):-!.
 * and(A,B,(A,B)).
 * ^
 * compiler
 * <p>
 * compile_clause(TailProg,(H:-B),ClauseProg):-
 * compile_head(BodyProg,H,ClauseProg,Vars),
 * compile_body(TailProg,Vars,B,BodyProg,NewVars).
 * <p>
 * compile_head(TailProg,Head,Prog,Vars):-
 * Head=..[F|Args],
 * compile_head_args(TailProg,1-[],1,Args,Prog,Vars).
 * <p>
 * compile_head_args(TailProg,OldVars,ArgNum,[A|RestA],[Command|RestComm],Vars):-
 * var(A),
 * gen_var(A,OldVars,NewVars,Num,Pres),
 * if_then_else(Pres=yes,CommName=get_value,CommName=get_variable),
 * Command=..[CommName,y:Num,a:ArgNum],
 * NextArgNum is ArgNum+1,
 * compile_head_args(TailProg,NewVars,NextArgNum,RestA,RestComm,Vars).
 * <p>
 * compile_head_args(TailProg,OldVars,ArgNum,[A|RestA],[get_structure(F/N,a:ArgNum)|RestComm],Vars):-
 * nonvar(A),
 * functor(A,F,N),
 * A=..[F|Args],
 * unify_args(RestProg,OldVars,Args,RestComm,NewVars),
 * NextArgNum is ArgNum+1,
 * compile_head_args(TailProg,NewVars,NextArgNum,RestA,RestProg,Vars).
 * <p>
 * compile_head_args(Prog,Vars,_,[],Prog,Vars).
 * <p>
 * unify_args(TailProg,OldVars,[A|RestA],[Command|RestComm],Vars):-
 * gen_var(A,OldVars,NewVars,Num,Pres),
 * if_then_else(Pres=yes,CommName=unify_value,CommName=unify_variable),
 * Command=..[CommName,y:Num],
 * unify_args(TailProg,NewVars,RestA,RestComm,Vars).
 * <p>
 * unify_args(Prog,Vars,[],Prog,Vars).
 * <p>
 * if_then_else(Cond,Then,Else):-
 * Cond,!,Then.
 * <p>
 * if_then_else(Cond,Then,Else):-
 * Else.
 * <p>
 * gen_var(V,NextFree-[X:N|T],NextFree-[X:N|T],N,yes):-
 * V==X.
 * gen_var(V,NextFree-[X:N|T],NewNextFree-[X:N|NewT],Num,Pres):-
 * V \== X,
 * gen_var(V,NextFree-T,NewNextFree-NewT,Num,Pres).
 * gen_var(V,N-[],NextFree-[V:N],N,no):-
 * NextFree is N+1.
 * <p>
 * compile_body(TailProg,OldVars,(A,B),Prog,NewVars):-
 * compile_body(RestProg,OldVars,A,Prog,Vars),
 * compile_body(TailProg,Vars,B,RestProg,NewVars).
 * <p>
 * compile_body(TailProg,OldVars,X=T,[Comm|RestComm],NewVars):-
 * gen_var(X,OldVars,Vars,M,Pres),
 * functor(T,F,N),
 * T=..[F|Args],
 * if_then_else(Pres=yes,
 * (CommName=get_structure,unify_args(TailProg,Vars,Args,RestComm,NewVars)),
 * (CommName=put_structure,prepare_zero_args(TailProg,Vars,Args,RestComm,NewVars))
 * ),
 * Comm=..[CommName,F/N,y:M].
 * <p>
 * compile_body(TailProg,OldVars,call(G),[bcall(y:N)|TailProg],NewVars):-
 * gen_var(G,OldVars,NewVars,N,_).
 * <p>
 * compile_body(TailProg,OldVars,T,Prog,NewVars):-
 * T\=(_,_),T\=(_=_),T\=call(_),
 * functor(T,F,N),
 * T=..[F|Args],
 * prepare_args([execute(F/N)|TailProg],OldVars,1,Args,Prog,NewVars).
 * <p>
 * prepare_args(TailProg,OldVars,ArgNum,[A|RestA],Prog,NewVars):-
 * prepare_arg(RestProg,OldVars,ArgNum,A,Prog,Vars),
 * NewArgNum is ArgNum+1,
 * prepare_args(TailProg,Vars,NewArgNum,RestA,RestProg,NewVars).
 * <p>
 * prepare_args(Prog,Vars,_,[],Prog,Vars).
 * <p>
 * prepare_arg(TailProg,OldVars,N,A,[Command|TailProg],NewVars):-
 * var(A),
 * gen_var(A,OldVars,NewVars,M,Pres),
 * if_then_else(Pres=yes,CommName=put_value,CommName=put_variable),
 * Command=..[CommName,y:M,a:N].
 * <p>
 * prepare_arg(TailProg,OldVars,N,A,[put_structure(F/M,a:N)|RestProg],NewVars):-
 * nonvar(A),
 * functor(A,F,M),
 * A=..[F|Args],
 * prepare_zero_args(TailProg,OldVars,Args,RestProg,NewVars).
 * <p>
 * prepare_zero_args(TailProg,OldVars,[A|RestA],[Command|RestComm],NewVars):-
 * gen_var(A,OldVars,Vars,N,Pres),
 * if_then_else(Pres=yes,CommName=set_value,CommName=set_variable),
 * Command=..[CommName,y:N],
 * prepare_zero_args(TailProg,Vars,RestA,RestComm,NewVars).
 * <p>
 * prepare_zero_args(Prog,Vars,[],Prog,Vars).
 * <p>
 * compile_procedure(TailProg,Label,[Clause|RestClauses],[Command|RestComms]):-
 * if_then_else(Label=F/N,
 * (Command=Label:try_me_else(NextLabel),NextLabel=F:1),
 * (Command=Label:retry_me_else(NextLabel),Label=F:N,NewN is N+1,NextLabel=F:NewN)),
 * compile_clause(RestProg,Clause,RestComms),
 * compile_procedure(TailProg,NextLabel,RestClauses,RestProg).
 * <p>
 * compile_procedure(TailProg,Label,[],[Label:pop|TailProg]).
 * <p>
 * list_WAM_code([Label:Command|RestWAMcode]):-
 * write(Label),write(' '),write(Command),nl,
 * list_WAM_code(RestWAMcode).
 * <p>
 * list_WAM_code([Command|RestWAMcode]):-
 * Command\=_:_,
 * write('    '),write(Command),nl,
 * list_WAM_code(RestWAMcode).
 * <p>
 * list_WAM_code([]).
 */
public
class PrologCompiler extends PrologRuntime {

    protected final PrologParser parser;
    protected final TermIterator termIterator;
    protected final PrologEntityIterator prologEntityIterator;
    protected final CodeBase codeBase;
    protected Preprocessor preprocessor;
    /**
     *
     */
    protected PredicateIndicator directiveId;
    protected TermOptions options;

    private CompoundTerm termExpansion = new CompoundTerm("term_expansion", 2);
    private CompoundTerm goalExpansion = new CompoundTerm("goal_expansion", 2);

    /**
     * @param parser
     * @param codeBase
     */
    public
    PrologCompiler ( PrologParser parser, Preprocessor preprocessor, CodeBase codeBase ) {
        super(codeBase);

        this.parser = parser;
        termIterator = (TermIterator) parser.termIterator();
        prologEntityIterator = (PrologEntityIterator) parser.entityIterator();
        this.preprocessor = preprocessor;
        this.codeBase = codeBase;
    }

    /**
     * @param files
     */
    public
    void compileFiles ( String... files ) throws IOException {
        for (String file : files) {
            compileFile(file);
        }
    }

    /**
     * @param entityTable
     */
    public
    void compileEntities ( Map <PredicateIndicator, Entity> entityTable ) {
        for (Entry <PredicateIndicator, Entity> entry : entityTable.entrySet()) {
//            PredicateIndicator predicateIndicator = entry.getKey();
            Entity entity = entry.getValue();
            compileEntity(entity);
        }
    }

    /**
     * @param entity
     */
    public
    void compileEntity ( Entity entity ) {
        var predDeclTable = entity.getPredicateDeclTable();
        for (Entry <PredicateIndicator, PredicateDeclaration> predicateDeclEntry : predDeclTable.entrySet()) {
            var key = predicateDeclEntry.getKey();
            var pdecl = predicateDeclEntry.getValue();

            if (pdecl instanceof PredicateDefinition) {
                PredicateDefinition pdef = new PredicateDefinition(key, code);
            }
        }
    }

    /**
     * @param file
     */
    public
    void compileFile ( String file ) throws IOException {
        PrologInputStream stream = new PrologInputStream(new FileInputStream(file));
        while (true) {
            Clause clause = readClause(stream, options);
            if (clause.getHead() == END_OF_FILE) {
                break;
            }
            clause = preprocess(clause);
            compileClause(clause);
        }
    }

    /**
     * compile_clause( TailProg, (H:-B), ClauseProg):-
     * compile_head( BodyProg, H, ClauseProg, Vars),
     * compile_body( TailProg, Vars, B, BodyProg, NewVars).
     *
     * @param clause
     */
    private
    void compileClause ( Clause clause ) {
        List <Instruction> cmds = compileHead(clause.getHead());
        List <Instruction> cmds2 = compileBody(clause.getBody());
    }

    /**
     * @param head
     * @return
     */
    private
    List <Instruction> compileHead ( Term head ) {
        List <Instruction> l = new ArrayList <>();

        return l;
    }

    /**
     * @param body
     * @return
     */
    private
    List <Instruction> compileBody ( Term body ) {
        List <Instruction> l = new ArrayList <>();

        return l;
    }

    /**
     * @return
     */
    public
    Clause readClause ( PrologInputStream stream, TermOptions options ) throws IOException {
        Term head;
        ListTerm body;
        Clause flattenClause;
        while (true) {
            Term t = readTerm(stream, options);
            if (t.isCompound()) {
                CompoundTerm c = (CompoundTerm) t;
                if (c.getName() == IMPLIES) {
                    ListTerm args = c.getArgs();
                    RangeTerm length = args.getLength();
                    if (length instanceof RangeTerm) {
                        int arity = 0;
                        if (length.getArityLow() == length.getArityHigh()) {
                            arity = length.getArityLow();
                            switch (arity) {
                                case 0 -> {
                                    callDirective(args);
                                }
                                case 1 -> {
                                    callDirective(args.getHead());
                                }
                                case 2 -> {
                                    head = args.getArg(0);
                                    body = args.getTail();

                                    return flatten(head, body);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * @param head
     * @param body
     * @return
     */
    private
    Clause flatten ( Term head, ListTerm body ) {
        List <Term> hl = flattenTerm(new ArrayList <>(), head);
        List <Term> bl = flattenTerm(hl, body);

        return simplify(Collections.unmodifiableList(hl), bl);
    }

    private
    Clause simplify ( List <Term> hl, List <Term> bl ) {
        Term flatT = hl.get(0);

        return new Clause((CompoundTerm) flatT, (ListTerm) bl.get(0));//fixme
    }

    /**
     * @param additions
     * @param term
     * @return
     */
    private
    List <Term> flattenTerm ( List <Term> additions, Term term ) {
        return null;
    }

    /**
     * @param clause
     * @return
     */
    private
    Clause preprocess ( Clause clause ) {
        clause = expandGoal(clause);

        return flatten(clause.getHead(), (ListTerm) clause.getBody());
    }

    /**
     * @param clause
     * @return
     */
    private
    Clause expandGoal ( Clause clause ) {
        ListTerm body = (ListTerm) clause.getBody();
        if (body != EMPTY_LIST) {
            while (true) {
                CompoundTerm head = (CompoundTerm) body.getHead();
            }
        }
        else {
        }

        return clause;
    }

    /**
     * @param stream
     * @param options
     * @return
     * @throws IOException
     */
    private
    Term readTerm ( PrologInputStream stream, TermOptions options ) throws IOException {
        parser.setStream(stream);
        Term t = termIterator.next();
        List <Term> l = expandTerm(t);
        t = l.remove(0);

        return t;
    }

    // private @NotNull
    List <Term> expandTerm ( Term t ) {
        return call(AtomTerm.createAtom("term_expansion"));
    }
//

    /**
     * @param term
     */
    private
    void callDirective ( Term term ) {
        call(term);
    }

    /**
     * @param terms
     * @return
     */
    public
    List <Term> call ( Term... terms ) {
        return PrologRuntime.;
    }

    /**
     * @return
     */
    public
    Term readTerm ( PrologInputStream in, ListTerm options ) {
        TermIterator iterator = (TermIterator) parser.termIterator();

        return iterator.next();
    }
}

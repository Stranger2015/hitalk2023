package org.stranger2015.hitalk.core;

import org.stranger2015.hitalk.core.compiler.PrologInputStream;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import static java.lang.Integer.*;
import static java.lang.String.valueOf;
import static org.stranger2015.hitalk.core.AtomTerm.createAtom;
import static org.stranger2015.hitalk.core.ListTerm.EMPTY_LIST;
import static org.stranger2015.hitalk.core.OperatorManager.OP_HIGH;
import static org.stranger2015.hitalk.core.OperatorManager.OP_LOW;
import static org.stranger2015.hitalk.core.Tokenizer.*;

/**
 * This class defines a parser of prolog terms and sentences.
 * <p/>
 * BNF part 2: PrologParser
 * term ::= exprA(1200)
 * exprA(n) ::= exprB(n) { op(yfx,n) exprA(n-1) |
 * op(yf,n) }*
 * exprB(n) ::= exprC(n-1) { op(xfx,n) exprA(n-1) |
 * op(xfy,n) exprA(n) |
 * op(xf,n) }*
 * // exprC is called parseLeftSide in the code
 * exprC(n) ::= '-' integer | '-' float |
 * op( fx,n ) exprA(n-1) |
 * op( fy,n ) exprA(n) |
 * exprA(n)
 * exprA(0) ::= integer |
 * float |
 * atom |
 * variable |
 * atom'(' exprA(1200) { ',' exprA(1200) }* ')' |
 * '[' [ exprA(1200) { ',' exprA(1200) }* [ '|' exprA(1200) ] ] ']' |
 * '(' { exprA(1200) }* ')'
 * '{' { exprA(1200) }* '}'
 * op(type,n) ::= atom | { symbol }+
 */
public
class PrologParser implements Serializable {
    boolean bof = true;
    public static final AtomTerm BEGIN_OF_FILE = createAtom("begin_of_file");
    public static final AtomTerm END_OF_FILE = createAtom("end_of_file");
    private PrologInputStream stream;

    /**
     * @param stream
     * @throws IOException
     */
    public
    void setStream ( PrologInputStream stream ) throws IOException {
        this.stream.close();
        this.stream = stream;
    }

    /**
     * @return
     */
    public
    PrologInputStream getStream () {
        return stream;
    }

    /**
     *
     */
    private static
    class IdentifiedTerm {
        private final int priority;
        private final AtomTerm result;

        public
        IdentifiedTerm ( int priority, AtomTerm result ) {
            this.priority = priority;
            this.result = result;
        }

        public
        IdentifiedTerm ( int priority, CompoundTerm compoundTerm ) {
            this.priority = priority;
            result = (AtomTerm) compoundTerm;//check
        }
    }

    private static final OperatorManager defaultOperatorManager = new DefaultOperatorManager();
    private final Tokenizer tokenizer;
    private OperatorManager opManager = defaultOperatorManager;

    /**
     * creating a PrologParser specifying how to handle operators
     * and what text to parse
     */
    public
    PrologParser ( OperatorManager op, Tokenizer tokenizer ) {
        this.tokenizer = tokenizer;
        if (op != null) {
            opManager = op;
        }
    }

    /**
     * creating a PrologParser specifying how to handle operators
     * and what text to parse
     */
    public
    PrologParser ( OperatorManager op, PrologInputStream stream ) {
        this(stream);
        if (op != null) {
            opManager = op;
        }
    }

    /**
     * creating a parser with default operator interpretation
     */
    public
    PrologParser ( PrologInputStream stream ) {
        tokenizer = new Tokenizer(stream);
    }

    /**
     * creating a parser with default operator interpretation
     */
    public
    PrologParser ( ByteArrayInputStream stream ) {
        tokenizer = new Tokenizer(new PrologInputStream(stream));
    }
//  user interface

    /**
     * @return
     */
    public
    Iterator <Term> termIterator () {
        return new TermIterator(this);
    }

    public
    Iterator <Entity<?>> entityIterator () {
        return new PrologEntityIterator(this);
    }

    /**
     * Parses next term from the stream built on string.
     *
     * @param endNeeded <tt>true</tt> if it is required to parse the end token
     *                  (a period), <tt>false</tt> otherwise.
     * @throws IllegalArgumentException if a syntax error is found.
     */
    public
    Term nextTerm ( boolean endNeeded ) throws IllegalArgumentException {
        try {
            if (bof) {
                bof = false;
                return BEGIN_OF_FILE;
            }
            Token t = tokenizer.readToken();
            if (t.isEOF()) {
                return END_OF_FILE;
            }

            tokenizer.unreadToken(t);
            AtomTerm term = expr(false);
            if (term == null) {
                throw new IllegalArgumentException("The parser is unable to finish.");
            }
            if (endNeeded && tokenizer.readToken().getType() != END) {
                throw new IllegalArgumentException("The term %s is not ended with a period.".formatted(term));
            }

            term.resolveTerm();

            return term;
        } catch (IOException ex) {
            throw new IllegalArgumentException("An I/O error occurred.");
        }
    }

    /**
     * Static service to get a term from its string representation
     */
    public static
    AtomTerm parseSingleTerm ( String st ) throws IllegalArgumentException {
        return parseSingleTerm(st, null);
    }

    /**
     * Static service to get a term from its string representation,
     * providing a specific operator manager
     */
    public static
    AtomTerm parseSingleTerm ( String st, OperatorManager op ) throws IllegalArgumentException {
        try {
            PrologParser p = new PrologParser(op, new PrologInputStream(st));
            Token t = p.tokenizer.readToken();
            if (t.isEOF()) {
                throw new IllegalArgumentException("Term starts with EOF");
            }

            p.tokenizer.unreadToken(t);
            AtomTerm term = p.expr(false);
            if (term == null) {
                throw new IllegalArgumentException("Term is null");
            }
            if (!p.tokenizer.readToken().isEOF()) {
                throw new IllegalArgumentException("The entire string could not be read as one term");
            }
            term.resolveTerm();

            return term;
        } catch (IOException ex) {
            throw new IllegalArgumentException("An I/O error occurred");
        }
    }

    // internal parsing procedures

    private
    AtomTerm expr ( boolean commaIsEndMarker ) throws IllegalArgumentException, IOException {
        return exprA(OP_HIGH, commaIsEndMarker).result;
    }

    private
    IdentifiedTerm exprA ( int maxPriority, boolean commaIsEndMarker ) throws IllegalArgumentException, IOException {

        IdentifiedTerm leftSide = exprB(maxPriority, commaIsEndMarker);
        if (leftSide == null)
            return null;

        //{op(yfx,n) exprA(n-1) | op(yf,n)}*
        Token t = tokenizer.readToken();
        for (; t.isOperator(commaIsEndMarker); t = tokenizer.readToken()) {

            int YFX = opManager.opPrio(t.seq, "yfx");
            int YF = opManager.opPrio(t.seq, "yf");

            //YF and YFX has a higher priority than the left side expr and less then top limit
            // if (YF < leftSide.priority && YF > OperatorManager.OP_HIGH) YF = -1;
            if (YF < leftSide.priority || YF > maxPriority) YF = -1;
            // if (YFX < leftSide.priority && YFX > OperatorManager.OP_HIGH) YFX = -1;
            if (YFX < leftSide.priority || YFX > maxPriority) YFX = -1;

            //YFX has priority over YF
            if (YFX >= YF && YFX >= OP_LOW) {
                IdentifiedTerm ta = exprA(YFX - 1, commaIsEndMarker);
                if (ta != null) {
                    leftSide = new IdentifiedTerm(YFX, new CompoundTerm(createAtom(t.seq), leftSide.result, ta.result));
                    continue;
                }
            }
            //either YF has priority over YFX or YFX failed
            if (YF >= OP_LOW) {
                leftSide = new IdentifiedTerm(YF, new CompoundTerm(createAtom(t.seq), leftSide.result));
                continue;
            }
            break;
        }
        tokenizer.unreadToken(t);

        return leftSide;
    }

    private
    IdentifiedTerm exprB ( int maxPriority, boolean commaIsEndMarker )
            throws IllegalArgumentException, IOException {

        //1. op(fx,n) exprA(n-1) | op(fy,n) exprA(n) | expr0
        IdentifiedTerm left = parseLeftSide(commaIsEndMarker, maxPriority);

        //2.left is followed by either xfx, xfy or xf operators, parse these
        Token operator = tokenizer.readToken();
        for (; operator.isOperator(commaIsEndMarker); operator = tokenizer.readToken()) {
            int XFX = opManager.opPrio(operator.seq, "xfx");
            int XFY = opManager.opPrio(operator.seq, "xfy");
            int XF = opManager.opPrio(operator.seq, "xf");

            //check that no operator has a priority higher than permitted
            //or a lower priority than the left side expression
            if (XFX > maxPriority || XFX < OP_LOW) XFX = -1;
            if (XFY > maxPriority || XFY < OP_LOW) XFY = -1;
            if (XF > maxPriority || XF < OP_LOW) XF = -1;

            //XFX
            boolean haveAttemptedXFX = false;
            if (XFX >= XFY && XFX >= XF && XFX >= left.priority) {     //XFX has priority
                IdentifiedTerm found = exprA(XFX - 1, commaIsEndMarker);
                if (found != null) {
                    AtomTerm xfx = new CompoundTerm(operator.seq, left.result, found.result).getName();//fixm
                    left = new IdentifiedTerm(XFX, xfx);
                    continue;
                }
                else {
                    haveAttemptedXFX = true;
                }
            }
            //XFY
            if (XFY >= XF && XFY >= left.priority) {           //XFY has priority, or XFX has failed
                IdentifiedTerm found = exprA(XFY, commaIsEndMarker);
                if (found != null) {
                    AtomTerm xfy = new CompoundTerm(operator.seq, left.result, found.result).getName();
                    left = new IdentifiedTerm(XFY, xfy);
                    continue;
                }
            }
            //XF
            if (XF >= left.priority)                   //XF has priority, or XFX and/or XFY has failed
            {
                return new IdentifiedTerm(XF, new CompoundTerm(operator.seq, left.result));
            }

            //XFX did not have top priority, but XFY failed
            if (!haveAttemptedXFX && XFX >= left.priority) {
                IdentifiedTerm found = exprA(XFX - 1, commaIsEndMarker);
                if (found != null) {
                    AtomTerm xfx = new CompoundTerm(operator.seq, left.result, found.result).getName();
                    left = new IdentifiedTerm(XFX, xfx);
                    continue;
                }
            }
            break;
        }
        tokenizer.unreadToken(operator);
        return left;
    }

    /**
     * Parses and returns a valid 'leftside' of an expression.
     * If the left side starts with a prefix, it consumes other expressions with a lower priority than itself.
     * If the left side does not have a prefix it must be an expr0.
     *
     * @param commaIsEndMarker used when the leftside is part of and argument list of expressions
     * @param maxPriority      operators with a higher priority than this will effectivly end the expression
     * @return a wrapper of: 1. term correctly structured and 2. the priority of its root operator
     * @throws IllegalArgumentException
     */
    private
    IdentifiedTerm parseLeftSide ( boolean commaIsEndMarker, int maxPriority ) throws IllegalArgumentException, IOException {
        //1. prefix expression
        Token f = tokenizer.readToken();
        if (f.isOperator(commaIsEndMarker)) {
            int FX = opManager.opPrio(f.seq, "fx");
            int FY = opManager.opPrio(f.seq, "fy");

            if (f.seq.equals("-")) {
                Token t = tokenizer.readToken();
                if (t.isNumber()) {
                    return new IdentifiedTerm(0, AtomTerm.createAtom("-%s".formatted(t.seq)));//fixme
                }
                else {
                    tokenizer.unreadToken(t);
                }
            }

            //check that no operator has a priority higher than permitted
            if (FY > maxPriority) {
                FY = -1;
            }
            if (FX > maxPriority) {
                FX = -1;
            }
            //FX has priority over FY
            boolean haveAttemptedFX = false;
            if (FX >= FY && FX >= OP_LOW) {
                IdentifiedTerm found = exprA(FX - 1, commaIsEndMarker);    //op(fx, n) exprA(n - 1)
                if (found != null) {
                    return new IdentifiedTerm(FX, new CompoundTerm(f.seq, found.result));
                }
                else {
                    haveAttemptedFX = true;
                }
            }
            //FY has priority over FX, or FX has failed
            if (FY >= OP_LOW) {
                IdentifiedTerm found = exprA(FY, commaIsEndMarker); //op(fy,n) exprA(1200)  or   op(fy,n) exprA(n)
                if (found != null) {
                    return new IdentifiedTerm(FY, new CompoundTerm(f.seq, found.result));
                }
            }
            //FY has priority over FX, but FY failed
            if (!haveAttemptedFX && FX >= OP_LOW) {
                IdentifiedTerm found = exprA(FX - 1, commaIsEndMarker);    //op(fx, n) exprA(n - 1)
                if (found != null) {
                    return new IdentifiedTerm(FX, new CompoundTerm(f.seq, found.result));
                }
            }
        }
        tokenizer.unreadToken(f);
        //2. expr0
        return new IdentifiedTerm(0, expr0(a));
    }

    private
    NumberTerm createNumber ( String s ) {
        try {
            return parseInteger(s);
        } catch (RuntimeException e) {
            return parseDouble(s);
        }

   }

    /**
     * exprA(0) ::= integer |
     * float |
     * variable |
     * atom |
     * atom( exprA(1200) { , exprA(1200) }* ) |
     * '[' exprA(1200) { , exprA(1200) }* [ | exprA(1200) ] ']' |
     * '{' [ exprA(1200) ] '}' |
     * '(' exprA(1200) ')'
     */
    private
    Term expr0 ( Term a ) throws IllegalArgumentException, IOException {
        Token t1 = tokenizer.readToken();

        if (t1.isType(INTEGER)) {
            return parseInteger(t1.seq); //todo moved method to Number
        }

        if (t1.isType(FLOAT)) {
            return parseDouble(t1.seq);   //todo moved method to Number
        }

        if (t1.isType(VARIABLE)) {
            return new Variable(t1.seq);         //todo switched to use the internal check for "_" in Var(String)
        }

        if (t1.isType(ATOM) || t1.isType(SQ_SEQUENCE) || t1.isType(DQ_SEQUENCE)) {
            if (!t1.isFunctor()) {
                return new CompoundTerm(new ListTerm(AtomTerm.createAtom(t1.seq)));
            }

            String functor = t1.seq;
            Token t2 = tokenizer.readToken();   //reading left par
            if (!t2.isType(LPAR)) {
                throw new IllegalArgumentException(("bug in parsing process. " +
                        "Something identified as functor misses its first left parenthesis"));//todo check can be skipped
            }
            List <AtomTerm> listTerm = expr0_arglist();     //reading arguments
            Token t3 = tokenizer.readToken();
            if (t3.isType(RPAR))      //reading right par
            {
                return new CompoundTerm(functor, (AtomTerm) a);
            }
            throw new IllegalArgumentException("Missing right parenthesis: (%s -> here <-".formatted(a));
        }

        if (t1.isType(LPAR)) {
            AtomTerm term = expr(false);
            if (tokenizer.readToken().isType(RPAR)) {
                return term;
            }
            throw new IllegalArgumentException("Missing right parenthesis: (%s -> here <-".formatted(term));
        }

        if (t1.isType(LBRA)) {
            Token t2 = tokenizer.readToken();
            if (t2.isType(RBRA)) {
                AtomTerm qualifiedName = null;
                Term arg2 = null;

                return new CompoundTerm(qualifiedName, createAtom(CompoundTerm..COLON_COLON), arg2);
            }

            tokenizer.unreadToken(t2);
            AtomTerm term = (AtomTerm) expr0_list();
            if (tokenizer.readToken().isType(RBRA)) {
                return term;
            }
            throw new IllegalArgumentException("Missing right bracket: [%s -> here <-".formatted(term));
        }

        if (t1.isType(LBRA2)) {
            Token t2 = tokenizer.readToken();
            ListTerm nameArgs = EMPTY_LIST;
            if (t2.isType(RBRA2)) {
                return new CompoundTerm(nameArgs.getName());
            }

            tokenizer.unreadToken(t2);
            AtomTerm arg = expr(false);
            t2 = tokenizer.readToken();
            if (t2.isType(RBRA2)) {
                return new CompoundTerm(nameArgs);
            }
            throw new IllegalArgumentException("Missing right braces: {%s -> here <-".formatted(arg));
        }

        throw new IllegalArgumentException("The following token could not be identified: " + t1.seq);
    }

    //todo make non-recursive?
    private
    CompoundTerm expr0_list () throws IllegalArgumentException, IOException {
        AtomTerm head = expr(true);
        Token t = tokenizer.readToken();
        ListTerm nameArgs = null;
        if (",".equals(t.seq)) {
            return new CompoundTerm(nameArgs);
        }
        if ("|".equals(t.seq)) {
            return new CompoundTerm(nameArgs);
        }
        if ("]".equals(t.seq)) {
            tokenizer.unreadToken(t);
            return new CompoundTerm(nameArgs);
        }
        throw new IllegalArgumentException("The expression: %s\nis not followed by either a ',' or '|' or ']'."
                .formatted(head));
    }

    //todo make non-recursive
    private
    List <AtomTerm> expr0_arglist () throws IllegalArgumentException, IOException {
        AtomTerm head = expr(true);
        Token t = tokenizer.readToken();
        if (",".equals(t.seq)) {
            return new ArrayList <>();
        }
        if (")".equals(t.seq)) {
            tokenizer.unreadToken(t);
            List <AtomTerm> l = new ArrayList <>();
            l.add(head);

            return l;
        }
        throw new IllegalArgumentException("The argument: %s\nis not followed by either a ',' or ')'.\nline: %d".formatted(head, tokenizer.lineno()));
    }

    // commodity methods to parse numbers

    static
    NumberTerm parseInteger ( String s ) {
        long num = Long.parseLong(s);
        if (num > MIN_VALUE && num < MAX_VALUE) {
            return new IntTerm(parseInt(valueOf(num)));
        }
        else {
            return new IntTerm((int) num);
        }
    }

    static
    NumberTerm parseDouble ( String s ) {
        return new DoubleTerm(Double.parseDouble(s));
    }

    public
    int getCurrentLine () {
        return tokenizer.lineno();
    }

    /**
     * @return true if the String could be a prolog atom
     */
    public static
    boolean isAtom ( String s ) {
        return atom.matcher(s).matches();
    }

    static private final Pattern atom = Pattern.compile("(!|[a-z][a-zA-Z_0-9]*)");

}

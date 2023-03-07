package org.stranger2015.hitalk.core.runtime;

import org.stranger2015.hitalk.core.Entity;
import org.stranger2015.hitalk.core.HitalkInterpreter;
import org.stranger2015.hitalk.core.PredicateIndicator;
import org.stranger2015.hitalk.core.Term;
import org.stranger2015.hitalk.core.compiler.instructions.PrologRuntime;

import java.util.*;

/**
 mode_MemoryCell :-
    “@” [ type ] ;
    “+” [ type ] ;
    “-” [ type ] ;
    “?” [ type ] ;
    “++” [ type ] ;
    “--” [ type ].

 type :-
    prolog_type ;
    logtalk_type ;
    user_defined_type.

 prolog_type :-
    “MemoryCell” ;
    “nonvar” ;
    “var” ;
    “compound” ;
    “ground” ;
    “callable” ;
    “list” ;
    “atomic” ;
    “atom” ;
    “number” ;
    “integer” ;
    “float”.

 logtalk_type :-
    “object” ;
    “category” ;
    “protocol” ;
    “event”.

 user_defined_type :-
    atom ;
    compound.

 number_of_proofs :-
    “zero” ;
    “zero_or_one” ;
    “zero_or_more” ;
    “one” ;
    “one_or_more” ;
    “zero_or_error” ;
    “one_or_error” ;
    “zero_or_one_or_error” ;
    “error”.

 meta_predicate_template_MemoryCell :-
    meta_predicate_template ;
    meta_predicate_template_sequence ;
    meta_predicate_template_list.

 meta_predicate_template_sequence :-
    meta_predicate_template ;
    meta_predicate_template “,”
    meta_predicate_template_sequence.

 meta_predicate_template_list :-
    “[” meta_predicate_template_sequence “]”.

 meta_predicate_template :-
    object_identifier “::” atom “(” meta_predicate_specifiers “)” ;
    category_identifier “::” atom “(” meta_predicate_specifiers “)” ;
    atom “(” meta_predicate_specifiers “)".

 meta_predicate_specifiers :-
    meta_predicate_specifier ;
    meta_predicate_specifier “,”
    meta_predicate_specifiers.

 meta_predicate_specifier :-
    non-negative integer ;
    “::” ;
    “^” ;
    “*”.

 meta_non_MemoryCellinal_template_MemoryCell :-
    meta_predicate_template_MemoryCell.

 entity_info_list :-
    “[]” ;
    “[” entity_info_item “is” nonvar “;” entity_info_list,
    “]”.

 entity_info_item :-
    “comment” ;
    “remarks” ;
    “author” ;
    “version” ;
    “date” ;
    “copyright” ;
    “license” ;
    “parameters” ;
    “parnames” ;
    “see_also” ;
    atom.

 predicate_info_list :-
    “[]” ;
    “[” predicate_info_item “is” nonvar “;” predicate_info_list “]”.

 predicate_info_item :-
    “comment” ;
    “remarks” ;
    “arguments” ;
    “argnames” ;
    “redefinition” ;
    “allocation” ;
    “examples” ;
    “exceptions” ;
    atom.

 object_alias :-
    object_identifier “as” object_identifier.

 object_alias_sequence :-
    object_alias ;
    object_alias “,”
    object_alias_sequence.

 object_alias_list :-
    “[” object_alias_sequence “]”.

// Clauses and goals

 clause :-
    object_identifier “::” head “:-” body ;
    module_identifier “:” head “:-” body ;
    (head :- body) ;
    fact.

 goal :-
    message_sending ;
    super_call ;
    external_call ;
    context_switching_call ;
    callable.

 message_sending :-
    message_to_object ;
    message_delegation ;
    message_to_self.

 message_to_object :-
    receiver “::” messages.

 message_delegation :-
    “[” message_to_object “]”.

 message_to_self :-
    “::” messages.

 super_call :-
    “^^” message.

 messages :-
    message ;
    “(” message “,” messages “)” ;
    “(” message “;” messages “)” ;
    “(” message “->” messages “)”.

 message :-
    callable ;
    variable.

 receiver :-
    “{” callable “}” ;
    object_identifier ;
    variable.

 external_call :-
    “{” callable “}”.
 */
public
class HitalkRuntime extends PrologRuntime {
    private final HitalkInterpreter interpreter;
    private final Map <PredicateIndicator, Entity> entityMap = new HashMap <>();

    /**
     * @param interpreter
     * @param heap
     * @param localStack
     * @param trailStack
     */
    public
    HitalkRuntime ( HitalkInterpreter interpreter,
                    List <Term> heap,
                    Deque <Term> localStack,
                    Deque <Term> trailStack,
                    CodeBase codebase
    ) {
        super(heap, localStack, trailStack, codebase);
        this.interpreter = interpreter;
    }

    /**
     * @return
     */
    public
    Map <PredicateIndicator, Entity> getEntityMap () {
        return entityMap;
    }

    /**
     * @return
     */
    public
    HitalkInterpreter getInterpreter () {
        return interpreter;
    }
}
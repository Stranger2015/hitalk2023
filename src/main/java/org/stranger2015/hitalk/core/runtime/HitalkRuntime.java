package org.stranger2015.hitalk.core.runtime;

/**
 * mode_ :-
 * <!-->“@” [ type ] ;
 * “+” [ type ] ;
 * “-” [ type ] ;
 * “-” [ type ] ;
 * “?” [ type ] ;
 * “++” [ type ] ;
 * “--” [ type ].
 * <p>
 * type :-
 * prolog_type ;
 * logtalk_type ;
 * user_defined_type.
 * <p>
 * prolog_type :-
 * “MemoryCell” ;
 * “nonvar” ;
 * “var” ;
 * “compound” ;
 * “ground” ;
 * “callable” ;
 * “list” ;
 * “atomic” ;
 * “atom” ;
 * “number” ;
 * “integer” ;
 * “float”.
 * <p>
 * logtalk_type :-
 * “object” ;
 * “category” ;
 * “protocol” ;
 * “event”.
 * <p>
 * user_defined_type :-
 * atom ;
 * compound.
 * <p>
 * number_of_proofs :-
 * “zero” ;
 * “zero_or_one” ;
 * “zero_or_more” ;
 * “one” ;
 * “one_or_more” ;
 * “zero_or_error” ;
 * “one_or_error” ;
 * “zero_or_one_or_error” ;
 * “error”.
 * <p>
 * meta_predicate_template_MemoryCell :-
 * meta_predicate_template ;
 * meta_predicate_template_sequence ;
 * meta_predicate_template_list.
 * <p>
 * meta_predicate_template_sequence :-
 * meta_predicate_template ;
 * meta_predicate_template “,”
 * meta_predicate_template_sequence.
 * <p>
 * meta_predicate_template_list :-
 * “[” meta_predicate_template_sequence “]”.
 * <p>
 * meta_predicate_template :-
 * object_identifier “::” atom “(” meta_predicate_specifiers “)” ;
 * category_identifier “::” atom “(” meta_predicate_specifiers “)” ;
 * atom “(” meta_predicate_specifiers “)".
 * <p>
 * meta_predicate_specifiers :-
 * meta_predicate_specifier ;
 * meta_predicate_specifier “,”
 * meta_predicate_specifiers.
 * <p>
 * meta_predicate_specifier :-
 * non-negative integer ;
 * “::” ;
 * “^” ;
 * “*”.
 * <p>
 * meta_non_MemoryCellinal_template_MemoryCell :-
 * meta_predicate_template_MemoryCell.
 * <p>
 * entity_info_list :-
 * “[]” ;
 * “[” entity_info_item “is” nonvar “;” entity_info_list,
 * “]”.
 * <p>
 * entity_info_item :-
 * “comment” ;
 * “remarks” ;
 * “author” ;
 * “version” ;
 * “date” ;
 * “copyright” ;
 * “license” ;
 * “parameters” ;
 * “parnames” ;
 * “see_also” ;
 * atom.
 * <p>
 * predicate_info_list :-
 * “[]” ;
 * “[” predicate_info_item “is” nonvar “;” predicate_info_list “]”.
 * <p>
 * predicate_info_item :-
 * “comment” ;
 * “remarks” ;
 * “arguments” ;
 * “argnames” ;
 * “redefinition” ;
 * “allocation” ;
 * “examples” ;
 * “exceptions” ;
 * atom.
 * <p>
 * object_alias :-
 * object_identifier “as” object_identifier.
 * <p>
 * object_alias_sequence :-
 * object_alias ;
 * object_alias “,”
 * object_alias_sequence.
 * <p>
 * object_alias_list :-
 * “[” object_alias_sequence “]”.
 * <p>
 * // Clauses and goals
 * <p>
 * clause :-
 * object_identifier “::” head “:-” body ;
 * module_identifier “:” head “:-” body ;
 * (head :- body) ;
 * fact.
 * <p>
 * goal :-
 * message_sending ;
 * super_call ;
 * external_call ;
 * context_switching_call ;
 * callable.
 * <p>
 * message_sending :-
 * message_to_object ;
 * message_delegation ;
 * message_to_self.
 * <p>
 * message_to_object :-
 * receiver “::” messages.
 * <p>
 * message_delegation :-
 * “[” message_to_object “]”.
 * <p>
 * message_to_self :-
 * “::” messages.
 * <p>
 * super_call :-
 * “^^” message.
 * <p>
 * messages :-
 * message ;
 * “(” message “,” messages “)” ;
 * “(” message “;” messages “)” ;
 * “(” message “->” messages “)”.
 * <p>
 * message :-
 * callable ;
 * variable.
 * <p>
 * receiver :-
 * “{” callable “}” ;
 * object_identifier ;
 * variable.
 * <p>
 * external_call :-
 * “{” callable “}”.
 */
public
class HitalkRuntime extends PrologRuntime {

    /**
     *
     */
    public
    HitalkRuntime ( CodeBase codebase ) {
        super(codebase);
    }
}
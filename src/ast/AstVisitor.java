package ast;

public interface AstVisitor<T> {

    public T visit(Program affect);
    public T visit(Or or);
    public T visit(Flush_ flush_);
    public T visit(Exit_ exit_);
    public T visit(Getchar getchar);
    public T visit(Chr_ chr_);
    public T visit(And and);
    public T visit(Compare_equal_1 compare_equal_1);
    public T visit(Compare_equal_2 compare_equal_2);
    public T visit(GreaterThan1 greaterThan1);
    public T visit(GreaterThan2 greaterThan2);
    public T visit(LessThan1 lessThan1);
    public T visit(LessThan2 lessThan2);
    public T visit(Plus plus);
    public T visit(Moins moins);
    public T visit(Mult mult);
    public T visit(Divide divide);
    public T visit(Whiledo whiledo);
    public T visit(For_ for_);
    public T visit(Identifier identifier);
    public T visit(Expr_seq expr_seq);
    public T visit(Declaration_list declaration_list);
    public T visit(Let_in_end let_in_end);
    public T visit(Type_declaration type_declaration);
    public T visit(StringNode string_node);
    public T visit(IntNode int_node);
    public T visit(Nil nil);
    public T visit(Break_ break_);
    public T visit(Print_ print_);
    public T visit(Printi printi);
    public T visit(Negation negation); 
    public T visit(Expr_list expr_list);
    public T visit(FunctionCall functionCall);
    public T visit(Assignement assignement);
    public T visit(IfThenElse ifthenelse);
    public T visit(Substring_ substring);
    public T visit(Concat_ concat_);
    public T visit(Ord_ ord_);
    public T visit(Size_ size_);
    public T visit(Not_ not_);
    public T visit(TypeDec1 typeDec1);
    public T visit(TypeDec2 typeDec2);
    public T visit(Type_fields type_fields);
    public T visit(Type_field type_field);
    public T visit(Variable_declaration variable_declaration);
    public T visit(Function_declaration function_declaration);
    public T visit(Field field);
    public T visit(Field_list field_list);
    public T visit(AccessId accessId);
    public T visit(AccessIndex accessIndex);
    public T visit(RecordDec recordDec);
    public T visit(ArrayDec arrayDec);
    
}

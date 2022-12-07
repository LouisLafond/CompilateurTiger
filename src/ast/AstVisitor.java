package ast;

public interface AstVisitor<T> {

    public T visit(Program affect);
    public T visit(Or or);
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
    public T visit(Negation negation); 


}

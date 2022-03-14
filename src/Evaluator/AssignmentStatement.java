package Evaluator;

import Entity.Host;
import Game.CellsField;

import java.util.Map;

public class AssignmentStatement implements Statement {
    public static StringBuilder testOut = new StringBuilder();

    private Identifier var;
    private Expression expr;

    public AssignmentStatement(Identifier var, Expression expr) {
        this.var = var;
        this.expr = expr;
    }

    @Override
    public void eval(Map<String, Integer> binding, Host unit, CellsField cf) throws EvalError {
        var.assign(expr.eval(binding));
        var.update(binding);

        System.out.println(var.name + " = " + var.eval(binding));
        testOut.append(var.name).append(" = ").append(var.eval(binding)).append("\n");

    }

}

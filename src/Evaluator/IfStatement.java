package Evaluator;

import Entity.Host;
import Game.CellsField;

import java.util.Map;

public class IfStatement implements Statement {
    private Expression expr;
    private Statement trueStatement;
    private Statement falseStatement;

    public IfStatement(Expression expr, Statement ts, Statement fs) {
        this.expr = expr;
        this.trueStatement = ts;
        this.falseStatement = fs;
    }

    @Override
    public void eval(Map<String, Integer> binding, Host unit, CellsField cf) throws EvalError {
        if (expr.eval(binding, unit) > 0)
            trueStatement.eval(binding, unit, cf);
        else
            falseStatement.eval(binding, unit, cf);
    }
}

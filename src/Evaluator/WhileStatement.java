package Evaluator;

import Entity.Host;
import Game.CellsField;

import java.util.Map;

public class WhileStatement implements Statement {
    private Expression expr;
    private Statement stm;


    public WhileStatement(Expression expr, Statement stm) {
        this.expr = expr;
        this.stm = stm;
    }

    @Override
    public void eval(Map<String, Integer> strg) throws EvalError {
        for (int i = 0; i < 1000 && expr.eval(strg) > 0; i++) {
            stm.eval(strg);
        }
    }
}

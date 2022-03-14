package Evaluator;

import Entity.Host;
import Game.CellsField;

import java.util.List;
import java.util.Map;

public class BlockStatement implements Statement {
    private List<Statement> list;

    public BlockStatement(List<Statement> l) {
        this.list = l;
    }

    @Override
    public void eval(Map<String, Integer> strg) throws EvalError {
        for (Statement s : list)
            s.eval(strg);
    }
}

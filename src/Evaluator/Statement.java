package Evaluator;

import Entity.Host;
import Game.CellsField;

import java.util.Map;

public interface Statement {
    void eval(Map<String, Integer> strg) throws EvalError;
}

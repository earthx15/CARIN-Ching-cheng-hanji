package Evaluator;

import java.util.Map;

public interface Expression {
    int eval(Map<String, Integer> strg) throws EvalError;
}

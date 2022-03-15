package Evaluator;

import Entity.Host;

import java.util.Map;

public interface Expression {
    int eval(Map<String, Integer> binding, Host unit) throws EvalError;
}

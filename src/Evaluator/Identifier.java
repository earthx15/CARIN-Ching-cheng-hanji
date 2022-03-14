package Evaluator;

import java.util.Map;

public class Identifier implements Expression {
    public final String name;
    private int value = 0;

    public Identifier(String name) {
        this.name = name;
    }

    public int eval(Map<String, Integer> strg) {
        if (strg.containsKey(name)) {
            value = strg.get(name);
            return value;
        } else {
            strg.put(name, value);
            return value;
        }
    }

    void assign(int evaluatedExpr) {
        value = evaluatedExpr;
    }

    void update(Map<String, Integer> strg) {
        if (strg.containsKey(name)) {
            strg.replace(name, value);
        } else {
            strg.put(name, value);
        }
    }
}

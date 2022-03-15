package Evaluator;

import Entity.Host;

import java.util.Map;

public class IntLit implements Expression {
    private int val;

    public IntLit(int val) {
        this.val = val;
    }


    @Override
    public int eval(Map<String, Integer> strg, Host unit) {
        return val;
    }
}

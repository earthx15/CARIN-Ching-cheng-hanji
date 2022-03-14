package Evaluator;

import java.util.Map;

public class BinaryArithExpr implements Expression {
    private Expression left;
    private Expression right;
    private String op;

    public BinaryArithExpr(
            Expression left, String op, Expression right) {
        this.left = left;
        this.op = op;
        this.right = right;
    }

    @Override
    public int eval(Map<String, Integer> strg) throws EvalError {
        int lv = left.eval(strg);
        int rv = right.eval(strg);

        try {
            if (op.equals("+")) return lv + rv;
            if (op.equals("-")) return lv - rv;
            if (op.equals("*")) return lv * rv;
            if (op.equals("/")) return lv / rv;
            if (op.equals("%")) return lv % rv;
            if (op.equals("^")) return (int) Math.pow(lv, rv);
            else
                throw new EvalError("undefined operator");
        } catch (ArithmeticException | EvalError e) {
            throw new EvalError("Evaluating error : " + e.getLocalizedMessage());
        }

    }
}

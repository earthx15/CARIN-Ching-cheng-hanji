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
    public void eval(Map<String, Integer> strg) throws EvalError {
        if (expr.eval(strg) > 0)
            trueStatement.eval(strg);
        else
            falseStatement.eval(strg);
    }
}

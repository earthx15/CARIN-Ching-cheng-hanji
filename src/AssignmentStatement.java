import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;

public class AssignmentStatement implements Statement {
    public static StringBuilder testOut = new StringBuilder();

    private Identifier var;
    private Expression expr;

    public AssignmentStatement(Identifier var, Expression expr) {
        this.var = var;
        this.expr = expr;
    }

    @Override
    public void eval(Map<String, Integer> strg) throws EvalError {
        var.assign(expr.eval(strg));
        var.update(strg);

        System.out.println(var.name + " = " + var.eval(strg));
        testOut.append(var.name).append(" = ").append(var.eval(strg)).append("\n");

    }
}

import java.util.Map;

public interface Statement {
    void eval(Map<String, Integer> strg) throws EvalError;
}

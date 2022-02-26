import java.util.Map;

public class SensorExpression implements Expression {
    private int distance;

    public SensorExpression(int dt) {
        this.distance = dt;
    }

    @Override
    public int eval(Map<String, Integer> strg) {
        return distance;
    }
}

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

class Identifier{
    private final String name;
    private int value = 0;

    public Identifier(String name){
        this.name = name;
    }

    int eval(Map<String, Integer> strg){
        if(strg.containsKey(name)){
            value = strg.get(name);
            return value;
        }else
            return -1;
            // throw undefined identifier
    }

    void assign(int evaluatedExpr){
        value = evaluatedExpr;
    }

    void update(Map<String, Integer> strg){
        if(strg.containsKey(name)){
            strg.replace(name, value);
        }
//        else throw undefined identifier;
    }
}

interface Statement{
    void eval(Map<String, Integer> strg);
}

class IfStatement implements Statement{
    private Expression expr;
    private Statement trueStatement;
    private Statement falseStatement;

    public IfStatement(Expression expr, Statement ts){
        this.expr = expr;
        this.trueStatement = ts;
    }

    public IfStatement(Expression expr, Statement ts, Statement fs){
        this(expr, ts);
        this.falseStatement = fs;
    }

    @Override
    public void eval() {
        if(expr.eval() != 0)
            trueStatement.eval();
        else
            falseStatement.eval();
    }
}

class WhileStatement implements Statement{
    private Expression expr;
    private Statement stm;


    public WhileStatement(Expression expr, Statement stm) {
        this.expr = expr;
        this.stm = stm;
    }

    @Override
    public void eval(Map<String, Integer> strg) {
        for(int i = 0; i < 1000 && expr.eval(strg) > 0; i++){
            stm.eval(strg);
        }
    }
}

class BlockStatement implements Statement{
    private List<Statement> list;

    public BlockStatement(List<Statement> l){
        this.list = l;
    }

    @Override
    public void eval(Map<String, Integer> strg){
        for(Statement s : list)
            s.eval(strg);
    }
}

class IfStatement implements Statement {
    private Expression expr;
    private Statement trueStatement;
    private Statement falseStatement;

    public IfStatement(Expression expr, Statement ts, Statement fs) {
        this.expr = expr;
        this.trueStatement = ts;
        this.falseStatement = fs;
    }

    @Override
    public void eval(Map<String, Integer> strg) {
        if (expr.eval(strg) != 0)
            trueStatement.eval(strg);
        else
            falseStatement.eval(strg);
    }
}


class AssignmentStatement implements Statement{
    private Identifier var;
    private Expression expr;

    public AssignmentStatement(Identifier var, Expression expr){
        this.var = var;
        this.expr = expr;
    }

    @Override
    public void eval(Map<String, Integer> strg) {
        var.assign(expr.eval(strg));
        var.update(strg);
        System.out.println(var.eval(strg));
    }
}

class AttackCommand implements Statement{
    private Direction direction;

    public AttackCommand(Direction direction){
        this.direction = direction;
    }

    @Override
    public void eval(Map<String, Integer> strg) {
        // attack
    }
}

class MoveCommand implements Statement{
    private Direction direction;

    public MoveCommand(Direction direction){
        this.direction = direction;
    }

    @Override
    public void eval(Map<String, Integer> strg) {
        // move
    }
}

class Direction{
    private final String direction;

    public Direction(String direction){
        this.direction = direction;
    }

    public String eval(Map<String, Integer> strg){
        return direction;
    }
}

interface Expression{
    int eval(Map<String, Integer> strg);
}

class Identifier implements Expression {
    private final String name;
    private int value = 0;

    public Identifier(String name){
        this.name = name;
    }

    public int eval(Map<String, Integer> strg){
        if(strg.containsKey(name)){
            value = strg.get(name);
            return value;
        }else{
            strg.put(name, value);
            return value;
        }
    }

    void assign(int evaluatedExpr){
        value = evaluatedExpr;
    }

    void update(Map<String, Integer> strg){
        if(strg.containsKey(name)){
            strg.replace(name, value);
        }
//        else throw cannot update to the undefined identifier;
    }
}


class BinaryArithExpr implements Expression {
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
    public int eval(Map<String, Integer> strg) {
        int lv = left.eval(strg);
        int rv = right.eval(strg);

        if (op.equals("+")) return lv + rv;
        if (op.equals("-")) return lv - rv;
        if (op.equals("*")) return lv * rv;
        if (op.equals("/")) return lv / rv;
        if (op.equals("%")) return lv % rv;
        if (op.equals("^")) return (int) Math.pow(lv,rv);
        else
            return -1;  //throw undefined operator

    }
}

class IntLit implements Expression{
    private int val;
    public IntLit(int val) {
        this.val = val;
    }


    @Override
    public int eval(Map<String, Integer> strg) {
        return val;
    }
}

class SensorExpression implements Expression{
    @Override
    public int eval(Map<String, Integer> strg) {
        return 0;
    }
}

public class Parser {

    protected Tokenizer tkz;
    public Map<String, Integer> sampleStorage;

    public Parser(String src) {
        this.tkz = new Tokenizer(src);
        sampleStorage.put("a", 0);
        sampleStorage.put("b", 0);
        sampleStorage.put("c", 0);
    }


}

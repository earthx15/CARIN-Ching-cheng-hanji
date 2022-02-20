import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


interface Statement{
    void eval(Map<String, Integer> strg) throws EvalError;
}

class WhileStatement implements Statement{
    private Expression expr;
    private Statement stm;


    public WhileStatement(Expression expr, Statement stm) {
        this.expr = expr;
        this.stm = stm;
    }

    @Override
    public void eval(Map<String, Integer> strg) throws EvalError {
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
    public void eval(Map<String, Integer> strg) throws EvalError {
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
    public void eval(Map<String, Integer> strg) throws EvalError {
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
    public void eval(Map<String, Integer> strg) throws EvalError {
        var.assign(expr.eval(strg));
        var.update(strg);
        System.out.println(var.name + " = " + var.eval(strg));
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
    public Map<String, Integer> sampleStorage = new HashMap<>();

    public Parser(String src) {
        this.tkz = new Tokenizer(src);
        sampleStorage.put("a", 0);
        sampleStorage.put("b", 0);
        sampleStorage.put("c", 0);
    }

    public List<Statement> parseProgram(){
        List<Statement> stmList = new LinkedList<>();
        while(tkz.hasNext){
            Statement stm = parseStm();
            stmList.add(stm);
        }
        return stmList;
    }

    public Statement parseStm(){
        if(tkz.peek().equals("{")){
            return parseBlkStm();
        }else if(tkz.peek().equals("if")){
            return parseIfStm();
        }else if(tkz.peek().equals("while")){
            return parseWhileStm();
        }else
            return parseCommand();
    }

    public Statement parseBlkStm(){
        tkz.consume();      // consume {
        List<Statement> stmList = new LinkedList<>();
        while (!tkz.peek().equals("}")){
            Statement stm = parseStm();
            stmList.add(stm);
        }// check for missing }
        tkz.consume();      //consume }
        return new BlockStatement(stmList);
    }

    public Statement parseIfStm(){
        tkz.consume();      // consume if
        //check for (
        tkz.consume();      // consume (

        Expression expr = parseExpr();

        //check for )
        tkz.consume();      // consume )

        //check for then
        tkz.consume();      // consume then

        Statement ts = parseStm();

        //check for else
        tkz.consume();      // consume else

        Statement fs = parseStm();
        return new IfStatement(expr, ts, fs);
    }

    public Statement parseWhileStm(){
        tkz.consume();      // consume while
        //check for (
        tkz.consume();      // consume (

        Expression expr = parseExpr();

        //check for )
        tkz.consume();      // consume )

        Statement stm = parseStm();

        return new WhileStatement(expr, stm);
    }

    public Statement parseAsgnStm(){
        Identifier iden = new Identifier(tkz.peek());
        tkz.consume();      // consume identifier
        //check if next token is not expr
        tkz.consume();      // consume =
        Expression expr = parseExpr();
        return new AssignmentStatement(iden, expr);
    }

    public Statement parseCommand(){
        if(tkz.peek().equals("move")){
            tkz.consume();      // consume move
            Direction dir = parseDirection();
            return new MoveCommand(dir);

        }else if(tkz.peek().equals("shoot")){
            tkz.consume();      // consume shoot
            Direction dir = parseDirection();
            return new AttackCommand(dir);
        }else
            // if next token is neither shoot nor move
            // assume next token is identifier
            return parseAsgnStm();
    }

    private Direction parseDirection() {
        if(tkz.peek().equals("left") ||
                tkz.peek().equals("right") ||
                tkz.peek().equals("up") ||
                tkz.peek().equals("down") ||
                tkz.peek().equals("upleft") ||
                tkz.peek().equals("upright") ||
                tkz.peek().equals("downleft") ||
                tkz.peek().equals("downright") ){
            Direction dir = new Direction(tkz.peek());
            tkz.consume();
            return dir;
        }else{
            return null;// throw unknown direction
        }
    }

    public Expression parseExpr(){
        Expression t = paresTerm();
        while (tkz.peek().equals("+") || tkz.peek().equals("-")) {
            if(tkz.peek().equals("+")){
                tkz.consume();
                t = new BinaryArithExpr(t, "+", paresTerm());
            }else if(tkz.peek().equals("-")){
                tkz.consume();
                t = new BinaryArithExpr(t, "-", paresTerm());
            }
        }
        return t;
    }

    public Expression paresTerm(){
        Expression f = paresFactor();
        while (tkz.peek().equals("*") || tkz.peek().equals("/") ||
                tkz.peek().equals("%")) {
            if(tkz.peek().equals("*")){
                tkz.consume();
                f = new BinaryArithExpr(f, "*", paresFactor());
            }else if(tkz.peek().equals("/")){
                tkz.consume();
                f = new BinaryArithExpr(f, "/", paresFactor());
            }else if(tkz.peek().equals("%")){
                tkz.consume();
                f = new BinaryArithExpr(f, "%", paresFactor());
            }
        }
        return f;
    }

    public Expression paresFactor(){
        Expression p = parsePower();
        while (tkz.peek().equals("^")){
            tkz.consume();
            p = new BinaryArithExpr(p, "^", parsePower());
        }
        return p;
    }

    public Expression parsePower(){
        Expression expr;
        if(isNumber(tkz.peek())){
            expr = new IntLit(Integer.parseInt(tkz.peek()));
            tkz.consume();
        }else if(tkz.peek().equals("(")){
            tkz.consume();
            expr = parseExpr();
            tkz.consume();
        }else if(tkz.peek().equals("virus") ||
                tkz.peek().equals("antibody") ||
                tkz.peek().equals("nearby") ){
            expr = parseSensor();
        }else{
            expr = new Identifier(tkz.peek());
        }
        return expr;
    }

    public Expression parseSensor(){
        if(tkz.peek().equals("virus")){
            return new IntLit(0);
        }else if(tkz.peek().equals("virus")){
            return new IntLit(0);
        }else if(tkz.peek().equals("virus")){
            return new IntLit(0);
        }else{
            return new IntLit(0);
        }
    }

    public void eval(){
        for(Statement stm : parseProgram()){
            stm.eval(sampleStorage);
        }
    }

    private boolean isNumber(String s){
        try {
            Integer.parseInt(s);
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }






}

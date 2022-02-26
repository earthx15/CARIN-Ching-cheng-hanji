import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class Parser {

    protected Tokenizer tkz;
    public Map<String, Integer> sampleStorage = new HashMap<>();

    public Parser(String src) {
        this.tkz = new Tokenizer(src);
    }

    public List<Statement> parseProgram() throws EvalError {
            List<Statement> stmList = new LinkedList<>();
            while(tkz.hasNext){
                Statement stm = parseStm();
                stmList.add(stm);
            }
            return stmList;

    }

    public Statement parseStm() throws EvalError {
        if(tkz.peek().equals("{")){
            return parseBlkStm();
        }else if(tkz.peek().equals("if")){
            return parseIfStm();
        }else if(tkz.peek().equals("while")){
            return parseWhileStm();
        }else
            return parseCommand();
    }

    public Statement parseBlkStm() throws EvalError {
        tkz.consume();      // consume {
        List<Statement> stmList = new LinkedList<>();
        while (!tkz.peek().equals("}")){
            Statement stm = parseStm();
            stmList.add(stm);
        }// check for missing }
        tkz.consume();      //consume }
        return new BlockStatement(stmList);
    }

    public Statement parseIfStm() throws EvalError {
        tkz.consume();      // consume if
        //check for (
        if(!tkz.peek().equals("("))
            throw new EvalError("missing (");
        tkz.consume();      // consume (

        Expression expr = parseExpr();

        //check for )
        if(!tkz.peek().equals(")"))
            throw new EvalError("missing )");
        tkz.consume();      // consume )

        //check for then
        tkz.consume();      // consume then

        Statement ts = parseStm();

        //check for else
        if(!tkz.peek().equals("else"))
            throw new EvalError("missing else");
        tkz.consume();      // consume else

        Statement fs = parseStm();
        return new IfStatement(expr, ts, fs);
    }

    public Statement parseWhileStm() throws EvalError {
        tkz.consume();      // consume while

        //check for (
        if(!tkz.peek().equals("("))
            throw new EvalError("missing (");
        tkz.consume();      // consume (

        Expression expr = parseExpr();

        //check for )
        if(!tkz.peek().equals(")"))
            throw new EvalError("missing )");
        tkz.consume();      // consume )

        Statement stm = parseStm();

        return new WhileStatement(expr, stm);
    }

    public Statement parseAsgnStm() throws EvalError {
        Identifier iden = new Identifier(tkz.peek());
        tkz.consume();      // consume identifier
        //check if next token is not expr
        tkz.consume();      // consume =
        Expression expr = parseExpr();
        return new AssignmentStatement(iden, expr);
    }

    public Statement parseCommand() throws EvalError {
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

    public Expression parseExpr() throws EvalError {
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

    public Expression paresTerm() throws EvalError {
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

    public Expression paresFactor() throws EvalError {
        Expression p = parsePower();
        while (tkz.peek().equals("^")){
            tkz.consume();
            p = new BinaryArithExpr(p, "^", parsePower());
        }
        return p;
    }

    public Expression parsePower() throws EvalError {
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

    public void eval() throws EvalError {
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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


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

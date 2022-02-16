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
    void eval();
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
    public void eval() {
        for(int i = 0; i < 1000 && expr.eval() > 0; i++){
            stm.eval();
        }
    }
}


class BlockStatement implements Statement{
    private List<Statement> list;

    public BlockStatement(List<Statement> l){
        this.list = l;
    }

    @Override
    public void eval(){
        for(Statement s : list)
            s.eval();
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
    public void eval() {
        var.assign(expr.eval());
    }
}

class Expression{

    int eval(){
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

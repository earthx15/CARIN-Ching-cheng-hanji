import java.util.LinkedList;
import java.util.List;

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


class Expression{

    int eval(){
        return 0;
    }
}



public class Parser {
    
}

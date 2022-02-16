import java.util.LinkedList;
import java.util.List;

interface Statement{
    void eval();
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

import java.util.*;

public class Tokenizer {
    protected String src;
    private String next;
    private int pos;

    private List<String> terminalSymbol = new ArrayList<>(Arrays.asList
        (new String[]{ "antibody", "down", "downleft", "downright", "else"
        , "if", "left", "move", "nearby", "right", "shoot", "then", "up"
        , "upleft", "upright", "virus", "while"}));

    private List<String> operators = new ArrayList<>(Arrays.asList
        (new String[]{ "+", "-", "*", "/", "%"
        , "^", "(", ")", "{", "}",}));
        

    public Tokenizer(String src){
        this.src = src;
        pos = 0;
        
        computeNext();
    }

    private void computeNext(){
        StringBuilder s = new StringBuilder();
        while (pos < src.length() && isSpace(src.charAt(pos)))
            pos++;  // ignore whitespace

        if(pos < src.length()){
            char c = src.charAt(pos);

            if(isNumber(c)){
                s.append(c);
                for (pos++; pos < src.length() && isNumber(src.charAt(pos)); pos++)
                    s.append(src.charAt(pos));

            }else if(isOperator(c)){
                s.append(c);
                pos++;
                
            }else{
                
            }


        }else
            next = "";
    }

    private boolean isSpace(char c) {
        return String.valueOf(c).equals(" ");
    }

    private boolean isNumber(char s){
        try {
            Integer.parseInt(String.valueOf(s));
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }

    private boolean isOperator(char c){
        String s = Character.toString(c);
        if(operators.contains(c))
            return true;
        else
            return false;
    }
}

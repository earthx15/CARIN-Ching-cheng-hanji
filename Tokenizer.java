import java.util.*;

public class Tokenizer {
    protected String src;
    private String next;
    private int pos;

    private final List<String> terminalSymbol = new ArrayList<>(Arrays.asList
        ("antibody", "down", "downleft", "downright", "else"
                , "if", "left", "move", "nearby", "right", "shoot", "then", "up"
                , "upleft", "upright", "virus", "while"));

    private final List<String> operators = new ArrayList<>(Arrays.asList
        ("+", "-", "*", "/", "%", "^", "(", ")", "{", "}"));
        

    public Tokenizer(String src){
        this.src = src;
        pos = 0;
        
        computeNext();
    }

    private void computeNext(){
        StringBuilder s = new StringBuilder();
        while (pos < src.length() && isSpace(src.charAt(pos)))
            pos++;  // ignore whitespace

        /**
         * possible token
         * 1. integer
         * 2. operator, i.e. + - / * ( {
         * 3. terminal symbol, i.e. down, if, while
         * 4. identifier (ตัวแปร)
         *
         * */

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
                s.append(c);
                for (pos++; pos < src.length() && !Character.toString(src.charAt(pos)).equals(" "); pos++)
                    s.append(src.charAt(pos));

                if(!terminalSymbol.contains(s.toString())){    //need to check for identifier later
                    // throw the token is neither terminalSymbol nor identifier
                }
            }
            next = s.toString();

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
        return operators.contains(s);
    }

    public String peek(){
        StringBuilder n = new StringBuilder();
        n.append(next);
        return n.toString();
    }

    public void consume() {
        computeNext();
    }

}

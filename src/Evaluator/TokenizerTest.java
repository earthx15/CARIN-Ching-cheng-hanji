package Evaluator;

import org.junit.jupiter.api.Test;

class TokenizerTest {

    @Test
    void simpleTest(){
        Tokenizer tkz = new Tokenizer(
                "Genetic codes of viruses and antibodies are governed by the following grammar.  Terminal symbols are written in monospace font; nonterminal symbols are written in italics."
        );
        while (tkz.hasNext) {
            System.out.println(tkz.peek());
            tkz.consume();
        }
//        assertEquals("if", tkz.peek());
//        tkz.consume();
//        assertEquals("(", tkz.peek());
//        tkz.consume();
//        assertEquals("virusLoc", tkz.peek());
//        tkz.consume();
//        assertEquals("/", tkz.peek());
//        tkz.consume();
//        assertEquals("10", tkz.peek());
//        tkz.consume();
//        assertEquals("-", tkz.peek());
//        tkz.consume();
//        assertEquals("1", tkz.peek());
//        tkz.consume();
//        assertEquals(")", tkz.peek());
//        tkz.consume();
    }

}
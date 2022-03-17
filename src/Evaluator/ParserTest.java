package Evaluator;

import Entity.Antibody;
import Entity.Host;
import Entity.Virus;
import Game.CellsField;
import org.junit.jupiter.api.Test;


import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    //================== Evaluator.Expression Part ==================

    //using Evaluator.AssignmentStatement for correcting



    @Test
    void exprBasicTest_1(){
        Parser psr = new Parser("t = 15 - 17 + 5 + 10");
        assertDoesNotThrow(psr::eval);
        assertEquals("t = 13\n", AssignmentStatement.testOut.toString());
        // t = 13
    }

    @Test
    void exprBasicTest_2(){
        Parser psr = new Parser("t = 2 * 39 / 13 * 5 ");
        assertDoesNotThrow(psr::eval);
        assertEquals("t = 30\n", AssignmentStatement.testOut.toString());
        // t = 30
    }

    @Test
    void exprBasicTest_3(){
        Parser psr = new Parser("t = 10 % 7 % 2");
        assertDoesNotThrow(psr::eval);
        assertEquals("t = 1\n", AssignmentStatement.testOut.toString());
        // t = 1
    }

    @Test
    void exprBasicTest_4(){     //check for right most parseFactor
        Parser psr = new Parser("t = 2^3^2^1^4 ");
        assertDoesNotThrow(psr::eval);
        assertEquals("t = 512\n", AssignmentStatement.testOut.toString());
        // t = 225
    }

    @Test
    void exprMix_1(){
        Parser psr = new Parser("t = (4*5) % 2 ");
        assertDoesNotThrow(psr::eval);
        assertEquals("t = 0\n", AssignmentStatement.testOut.toString());
        // t = 0
    }

    @Test
    void exprMix_2(){
        Parser psr = new Parser("t = 1200+(380/190)*128 ");
        assertDoesNotThrow(psr::eval);
        assertEquals("t = 1456\n", AssignmentStatement.testOut.toString());
        // t = 1456
    }

    @Test
    void exprMix_3(){
        Parser psr = new Parser("t = (5+45)*2-1 ");
        assertDoesNotThrow(psr::eval);
        assertEquals("t = 99\n", AssignmentStatement.testOut.toString());
        // t = 99
    }

    @Test
    void exprError_divideByZero() {
        Parser psr = new Parser("t = 999 / 0 ");
        assertThrows(EvalError.class, psr::eval);
    }

    @Test
    void exprError_modByZero() {
        Parser psr = new Parser("t = 999 % 0 ");
        assertThrows(EvalError.class, psr::eval);
    }

    @Test
    void exprError_finishWithOperator() {
        Parser psr = new Parser("t = 32 + 5 * ");
        assertThrows(EvalError.class, psr::eval);
    }



    //================== Evaluator.Statement Part ==================
    // Evaluator.IfStatement
    @Test
    void IS_simple1() throws EvalError {
        Parser psr = new Parser("t = 1 " +
                "if ( t-1 ) then t = 0 else t = 99");
        psr.eval();
        assertEquals("""
                        t = 1
                        t = 99
                        """
                , AssignmentStatement.testOut.toString());
    }

    @Test
    void IS_simple2() throws EvalError {
        Parser psr = new Parser("a = 50" +
                "b = a * 2 " +
                "if ( b - 100 ) then t = 0 else t = 99");
        psr.eval();
        assertEquals("""
                        a = 50
                        b = 100
                        t = 99
                        """
                , AssignmentStatement.testOut.toString());
    }

    @Test
    void IS_error2() throws EvalError {
        Parser psr = new Parser("""
                a = 50
                if ( a - 49 ) then t = 10 else t = 98
                """);

        psr.eval();
        assertEquals("""
                        a = 50
                        t = 10
                        """
                , AssignmentStatement.testOut.toString());
    }

    // Evaluator.WhileStatement
    @Test
    void WS_simple1() throws EvalError {
        Parser psr = new Parser("""
                a = 6
                while ( a ) a = a - 2
                """);
        psr.eval();
        assertEquals("""
                        a = 6
                        a = 4
                        a = 2
                        a = 0
                        """
                , AssignmentStatement.testOut.toString());
    }

    @Test
    void WS_simple2() throws EvalError {
        Parser psr = new Parser("""
                a = 30
                b = 10
                while ( a ) a = a - b
                """);
        psr.eval();
        assertEquals("""
                        a = 30
                        b = 10
                        a = 20
                        a = 10
                        a = 0
                        """
                , AssignmentStatement.testOut.toString());
    }

    // Evaluator.BlockStatement
    @Test
    void BS_simple1() throws EvalError {
        Parser psr = new Parser("""
                { a = 5
                b = 10
                c = 9 }
                """);
        psr.eval();
        assertEquals("""
                        a = 5
                        b = 10
                        c = 9
                        """
                , AssignmentStatement.testOut.toString());
    }

    @Test
    void BS_simple2() throws EvalError {
        Parser psr = new Parser("""
                { a = 5
                { b = 10
                b = b + 5 }
                c = a + b }
                """);
        psr.eval();
        assertEquals("""
                        a = 5
                        b = 10
                        b = 15
                        c = 20
                        """
                , AssignmentStatement.testOut.toString());
    }

    //Evaluator.AttackCommand
    @Test
    void AC_simple1() throws EvalError {
        Parser psr = new Parser("""
                shoot up
                shoot downright
                shoot down
                """, new Antibody("Normal" ,10,10,10,10,new int[]{0,0}), new CellsField(3,3));
        psr.eval();
        assertEquals("""
                        Attack : up
                        Attack : downright
                        Attack : down
                        """
                , AttackCommand.testOut.toString());
    }

    //Evaluator.MoveCommand
    @Test
    void MC_simple1() throws EvalError {
        CellsField cf = new CellsField(10,10);
        Antibody ab = new Antibody("Normal" ,2,2,2, 2, new int[]{3,3});
        Parser psr = new Parser("""
                 move upleft
                 move left
                 move downright
                """, ab, cf);
        psr.eval();
        assertEquals("""
                        Normal Antibody from 2,1 move to 3,2
                        """
                , MoveCommand.testOut.toString());
        // move upleft      3,3 -> 2,2
        // move left        2,2 -> 2,1
        // move downright   2,1 -> 3,2
        assertEquals("empty"
                , cf.checkUnit(3,3));
        assertEquals("empty"
                , cf.checkUnit(2,2));
        assertEquals("empty"
                , cf.checkUnit(2,1));
        assertEquals("Antibody"
                , cf.checkUnit(3,2));
    }

    //Mix
    @Test
    void MovingTest_1() throws EvalError {
        CellsField cf = new CellsField(10,10);
        Virus v1 = new Virus("Normal" ,10,10,2,10, new int[]{4, 4});
        Virus v2 = new Virus("Normal" ,10,10,2,10, new int[]{8, 4});
        cf.addUnit(v1);
        cf.addUnit(v2);
        Parser psr = new Parser("""
                 virusLoc = virus
                 if (virusLoc / 10 - 1)
                 then
                    if (virusLoc % 10 - 7) then move upleft
                   else if (virusLoc % 10 - 6) then move left
                   else if (virusLoc % 10 - 5) then move downleft
                   else if (virusLoc % 10 - 4) then move down
                   else if (virusLoc % 10 - 3) then move downright
                   else if (virusLoc % 10 - 2) then move right
                   else if (virusLoc % 10 - 1) then move upright
                   else move up
                 else shoot up
                """, v1, cf);
        psr.eval();
        assertEquals(""" 
                        virusLoc = 45
                        """
                , AssignmentStatement.testOut.toString());
        assertEquals("""
                        Normal Virus from 4,4 move to 5,4
                        """
                , MoveCommand.testOut.toString());
        // virus move down
        assertEquals("empty"
                , cf.checkUnit(4,4));
        assertEquals("Virus"
                , cf.checkUnit(5,4));
    }

    @Test
    void MovingTest_2() throws EvalError {
        CellsField cf = new CellsField(10,10);
        Virus v1 = new Virus("Normal" ,10,10,2,10, new int[]{4, 4});
        Virus v2 = new Virus("Normal" ,10,10,2,10, new int[]{9, 9});
        cf.addUnit(v1);
        cf.addUnit(v2);
        Parser psr = new Parser("""
                 virusLoc = virus
                 if (virusLoc / 10 - 1)
                 then
                    if (virusLoc % 10 - 7) then move upleft
                   else if (virusLoc % 10 - 6) then move left
                   else if (virusLoc % 10 - 5) then move downleft
                   else if (virusLoc % 10 - 4) then move down
                   else if (virusLoc % 10 - 3) then move downright
                   else if (virusLoc % 10 - 2) then move right
                   else if (virusLoc % 10 - 1) then move upright
                   else move up
                 else shoot up
                """, v1, cf);
        psr.eval();
        assertEquals(""" 
                        virusLoc = 54
                        """
                , AssignmentStatement.testOut.toString());
        assertEquals("""
                        Normal Virus from 4,4 move to 5,5
                        """
                , MoveCommand.testOut.toString());
        // virus move downleft
        assertEquals("empty"
                , cf.checkUnit(4,4));
        assertEquals("Virus"
                , cf.checkUnit(5,5));
    }

    @Test
    void moveToClosestVirus() throws EvalError {
        CellsField cf = new CellsField(10,10);
        Virus v1 = new Virus("Normal" ,10,10,2,10, new int[]{4, 4});
        Virus v2 = new Virus("Normal" ,10,10,2,10, new int[]{0, 0});
        Virus v3 = new Virus("Normal" ,10,10,2,10, new int[]{9, 9});
        cf.addUnit(v1);
        cf.addUnit(v2);
        cf.addUnit(v3);
        Parser psr = new Parser("""
                 virusLoc = virus
                 if (virusLoc / 10 - 1)
                 then
                    if (virusLoc % 10 - 7) then move upleft
                   else if (virusLoc % 10 - 6) then move left
                   else if (virusLoc % 10 - 5) then move downleft
                   else if (virusLoc % 10 - 4) then move down
                   else if (virusLoc % 10 - 3) then move downright
                   else if (virusLoc % 10 - 2) then move right
                   else if (virusLoc % 10 - 1) then move upright
                   else move up
                 else shoot up
                """, v1, cf);
        psr.eval();
        assertEquals(""" 
                        virusLoc = 48
                        """
                , AssignmentStatement.testOut.toString());
        assertEquals("""
                        Normal Virus from 4,4 move to 3,3
                        """
                , MoveCommand.testOut.toString());
        // virus move upleft
        assertEquals("empty"
                , cf.checkUnit(4,4));
        assertEquals("Virus"
                , cf.checkUnit(3,3));
    }

    @Test
    void moveFromCorner() throws EvalError {
        CellsField cf = new CellsField(10,10);
        Virus v1 = new Virus("Normal" ,10,10,2,10, new int[]{0, 0});
        Virus v2 = new Virus("Normal" ,10,10,2,10, new int[]{9, 0});
        cf.addUnit(v1);
        cf.addUnit(v2);
        Parser psr = new Parser("""
                 virusLoc = virus
                 if (virusLoc / 10 - 1)
                 then
                    if (virusLoc % 10 - 7) then move upleft
                   else if (virusLoc % 10 - 6) then move left
                   else if (virusLoc % 10 - 5) then move downleft
                   else if (virusLoc % 10 - 4) then move down
                   else if (virusLoc % 10 - 3) then move downright
                   else if (virusLoc % 10 - 2) then move right
                   else if (virusLoc % 10 - 1) then move upright
                   else move up
                 else shoot up
                """, v1, cf);
        psr.eval();
        assertEquals(""" 
                        virusLoc = 95
                        """
                , AssignmentStatement.testOut.toString());
        assertEquals("""
                       Normal Virus from 0,0 move to 1,0
                        """
                , MoveCommand.testOut.toString());
        // virus move down
        assertEquals("empty"
                , cf.checkUnit(0,0));
        assertEquals("Virus"
                , cf.checkUnit(1,0));
    }

    @Test
    void virusMoveToAntibody() throws EvalError {
        CellsField cf = new CellsField(10,10);
        Virus v1 = new Virus("Normal" ,10,10,2,10, new int[]{3, 4});
        Antibody a2 = new Antibody("Normal" ,10,10,10, 2, new int[]{6, 1});
        cf.addUnit(v1);
        cf.addUnit(a2);
        Parser psr = new Parser("""
                 antibodyLoc = antibody
                 if (antibodyLoc / 10 - 1)
                 then
                    if (antibodyLoc % 10 - 7) then move upleft
                   else if (antibodyLoc % 10 - 6) then move left
                   else if (antibodyLoc % 10 - 5) then move downleft
                   else if (antibodyLoc % 10 - 4) then move down
                   else if (antibodyLoc % 10 - 3) then move downright
                   else if (antibodyLoc % 10 - 2) then move right
                   else if (antibodyLoc % 10 - 1) then move upright
                   else move up
                 else shoot up
                """, v1, cf);
        psr.eval();
        assertEquals(""" 
                        antibodyLoc = 36
                        """
                , AssignmentStatement.testOut.toString());
        assertEquals("""
                        Normal Virus from 3,4 move to 4,3
                        """
                , MoveCommand.testOut.toString());
        // virus move downleft
        assertEquals("empty"
                , cf.checkUnit(4,4));
        assertEquals("Virus"
                , cf.checkUnit(4,3));
    }

    @Test
    void moveFromCornerButAntibodyMoves() throws EvalError {
        CellsField cf = new CellsField(10,10);
        Virus v1 = new Virus("Normal" ,10,10,2,10, new int[]{0, 0});
        Antibody a2 = new Antibody("Normal" ,10,10,10, 2, new int[]{9, 0});
        cf.addUnit(v1);
        cf.addUnit(a2);
        Parser psr = new Parser("""
                 virusLoc = virus
                 if (virusLoc / 10 - 1)
                 then
                    if (virusLoc % 10 - 7) then move upleft
                   else if (virusLoc % 10 - 6) then move left
                   else if (virusLoc % 10 - 5) then move downleft
                   else if (virusLoc % 10 - 4) then move down
                   else if (virusLoc % 10 - 3) then move downright
                   else if (virusLoc % 10 - 2) then move right
                   else if (virusLoc % 10 - 1) then move upright
                   else move up
                 else shoot up
                """, a2, cf);
        psr.eval();
        assertEquals(""" 
                        virusLoc = 91
                        """
                , AssignmentStatement.testOut.toString());
        assertEquals("""
                        Normal Antibody from 9,0 move to 8,0
                        """
                , MoveCommand.testOut.toString());
        // antibody move up
        assertEquals("empty"
                , cf.checkUnit(9,0));
        assertEquals("Antibody"
                , cf.checkUnit(8,0));
    }

    @Test
    void foundVirusButAntibodyBlock() throws EvalError {
        CellsField cf = new CellsField(10,10);
        Virus v1 = new Virus("Normal" ,10,10,2,10, new int[]{0, 0});
        Antibody a2 = new Antibody("Normal" ,10,10,10, 2, new int[]{9, 0});
        Antibody a3 = new Antibody("Normal" ,10,10,10, 2, new int[]{8, 0});
        cf.addUnit(v1);
        cf.addUnit(a2);
        cf.addUnit(a3);
        Parser psr = new Parser("""
                 virusLoc = virus
                 if (virusLoc / 10 - 1)
                 then
                    if (virusLoc % 10 - 7) then move upleft
                   else if (virusLoc % 10 - 6) then move left
                   else if (virusLoc % 10 - 5) then move downleft
                   else if (virusLoc % 10 - 4) then move down
                   else if (virusLoc % 10 - 3) then move downright
                   else if (virusLoc % 10 - 2) then move right
                   else if (virusLoc % 10 - 1) then move upright
                   else move up
                 else shoot up
                """, a2, cf);
        psr.eval();
        assertEquals(""" 
                        virusLoc = 91
                        """
                , AssignmentStatement.testOut.toString());
        assertEquals("""
                        Normal Antibody from 9,0 cannot move to 8,0
                        """
                , MoveCommand.testOut.toString());
        // antibody (try to) move up
        assertEquals("Antibody"
                , cf.checkUnit(8,0));
        assertEquals("Antibody"
                , cf.checkUnit(9,0));
    }

    @Test
    void nearBy_Virus() throws EvalError {
        CellsField cf = new CellsField(10,10);
        Virus v1 = new Virus("Normal" ,10,10,2,10, new int[]{7, 4});
        Antibody a2 = new Antibody("Normal" ,10,10,10,2, new int[]{2, 4});
        Antibody a3 = new Antibody("Normal" ,10,10,10, 2, new int[]{8, 8});
        cf.addUnit(v1);
        cf.addUnit(a2);
        cf.addUnit(a3);
        Parser psr = new Parser("""
                 nearbyLoc = nearby up
                 if (nearbyLoc / 10 - 1)
                 then
                   if (nearbyLoc % 10 - 7) then move upleft
                   else if (nearbyLoc % 10 - 6) then move left
                   else if (nearbyLoc % 10 - 5) then move downleft
                   else if (nearbyLoc % 10 - 4) then move down
                   else if (nearbyLoc % 10 - 3) then move downright
                   else if (nearbyLoc % 10 - 2) then move right
                   else if (nearbyLoc % 10 - 1) then move upright
                   else move up
                 else shoot up
                """, v1, cf);
        psr.eval();
        assertEquals(""" 
                        nearbyLoc = 51
                        """
                , AssignmentStatement.testOut.toString());
        assertEquals("""
                        Normal Virus from 7,4 move to 6,4
                        """
                , MoveCommand.testOut.toString());
        // virus 7,4 -> 6,4
        assertEquals("empty"
                , cf.checkUnit(7,4));
        assertEquals("Virus"
                , cf.checkUnit(6,4));
    }

    @Test
    void unfound() throws EvalError {
        CellsField cf = new CellsField(10,10);
        Virus v1 = new Virus("Normal" ,10,10,2,10, new int[]{7, 4});
        Antibody a2 = new Antibody("Normal" ,10,10,10, 2, new int[]{2, 4});
        Antibody a3 = new Antibody("Normal" ,10,10,10, 2, new int[]{8, 8});
        cf.addUnit(v1);
        cf.addUnit(a2);
        cf.addUnit(a3);
        Parser psr = new Parser("""
                 nearbyLoc = nearby right
                 if (nearbyLoc / 10 - 1)
                 then
                   if (nearbyLoc % 10 - 7) then move upleft
                   else if (nearbyLoc % 10 - 6) then move left
                   else if (nearbyLoc % 10 - 5) then move downleft
                   else if (nearbyLoc % 10 - 4) then move down
                   else if (nearbyLoc % 10 - 3) then move downright
                   else if (nearbyLoc % 10 - 2) then move right
                   else if (nearbyLoc % 10 - 1) then move upright
                   else move up
                 else shoot up
                """, v1, cf);
        psr.eval();
        assertEquals(""" 
                        nearbyLoc = 0
                        """
                , AssignmentStatement.testOut.toString());
        assertNull(MoveCommand.testOut);
        //virus do not found any antibody
        assertEquals("Virus"
                , cf.checkUnit(7,4));
    }

    @Test
    void virusAttack() throws EvalError {
        CellsField cf = new CellsField(10,10);
        Virus v1 = new Virus("Normal" ,10,3,1,2, new int[]{5, 4});
        Antibody a2 = new Antibody("Normal" ,10,10,10, 4, new int[]{5, 5});
        cf.addUnit(v1);
        cf.addUnit(a2);
        Parser psr = new Parser("""
                 antibodyLoc = antibody
                 if ( antibodyLoc )
                 then
                   if ( antibodyLoc % 10 - 7 ) then shoot upleft
                   else if ( antibodyLoc % 10 - 6 ) then shoot left
                   else if ( antibodyLoc % 10 - 5 ) then shoot downleft
                   else if ( antibodyLoc % 10 - 4 ) then shoot down
                   else if ( antibodyLoc % 10 - 3 ) then shoot downright
                   else if ( antibodyLoc % 10 - 2 ) then shoot right
                   else if ( antibodyLoc % 10 - 1 ) then shoot upright
                   else shoot up
                 else move down
                """, v1, cf);
        psr.eval();
        assertEquals(7
                , cf.getUnit(5,5).getCurHp());      //receiving damage antibody
        assertEquals(10
                , cf.getUnit(5,4).getCurHp());      //attacking virus
    }

    @Test
    void antibodyAttack() throws EvalError {
        CellsField cf = new CellsField(10,10);
        Virus v1 = new Virus("Normal" ,10,3,1,2, new int[]{5, 2});
        Antibody a2 = new Antibody("Normal" ,10,4,10, 4, new int[]{7, 2});
        cf.addUnit(v1);
        cf.addUnit(a2);
        Parser psr = new Parser("""
                 virusLoc = virus
                 if ( virusLoc )
                 then
                   if ( virusLoc % 10 - 7 ) then shoot upleft
                   else if ( virusLoc % 10 - 6 ) then shoot left
                   else if ( virusLoc % 10 - 5 ) then shoot downleft
                   else if ( virusLoc % 10 - 4 ) then shoot down
                   else if ( virusLoc % 10 - 3 ) then shoot downright
                   else if ( virusLoc % 10 - 2 ) then shoot right
                   else if ( virusLoc % 10 - 1 ) then shoot upright
                   else shoot up
                 else move down
                """, a2, cf);
        psr.eval();
        assertEquals(6
                , cf.getUnit(5,2).getCurHp());      //receiving damage virus
        assertEquals(10
                , cf.getUnit(7,2).getCurHp());      //attacking antibody
    }

    @Test
    void moveThenAttack_virus() throws EvalError {
        CellsField cf = new CellsField(10,10);
        Virus v1 = new Virus("Normal" ,10,3,1,2, new int[]{5, 2});
        Antibody a2 = new Antibody("Normal" ,10,4,10, 4, new int[]{7, 2});
        cf.addUnit(v1);
        cf.addUnit(a2);
        Parser psr = new Parser("""
                 loc = antibody
                 if ( loc / 10 - 1 )
                 then
                   if ( loc % 10 - 7 ) then move upleft
                   else if ( loc % 10 - 6 ) then move left
                   else if ( loc % 10 - 5 ) then move downleft
                   else if ( loc % 10 - 4 ) then move down
                   else if ( loc % 10 - 3 ) then move downright
                   else if ( loc % 10 - 2 ) then move right
                   else if ( loc % 10 - 1 ) then move upright
                   else move up
                 else if ( loc )
                 then
                   if ( loc % 10 - 7 ) then shoot upleft
                   else if ( loc % 10 - 6 ) then shoot left
                   else if ( loc % 10 - 5 ) then shoot downleft
                   else if ( loc % 10 - 4 ) then shoot down
                   else if ( loc % 10 - 3 ) then shoot downright
                   else if ( loc % 10 - 2 ) then shoot right
                   else if ( loc % 10 - 1 ) then shoot upright
                   else shoot up
                 else shoot left
                """, v1, cf);
        psr.eval();
        psr.eval();
        //virus : 5,2 go down 6,2
        //virus : 5,2 attack antibody at 7,2
        assertEquals(7
                , cf.getUnit(7,2).getCurHp());      //receiving damage antibody
        assertEquals(10
                , cf.getUnit(6,2).getCurHp());      //attacking virus
    }

    @Test
    void simpleBattle_1() throws EvalError {
        CellsField cf = new CellsField(10,10);
        Virus v1 = new Virus("Normal" ,10,3,1,1, new int[]{1, 1});
        Antibody a2 = new Antibody("Normal" ,10,2,2, 4, new int[]{5, 5});
        cf.addUnit(v1);
        cf.addUnit(a2);
        Parser psr1 = new Parser("""
                 loc = antibody
                 if ( loc / 10 - 1 )
                 then
                   if ( loc % 10 - 7 ) then move upleft
                   else if ( loc % 10 - 6 ) then move left
                   else if ( loc % 10 - 5 ) then move downleft
                   else if ( loc % 10 - 4 ) then move down
                   else if ( loc % 10 - 3 ) then move downright
                   else if ( loc % 10 - 2 ) then move right
                   else if ( loc % 10 - 1 ) then move upright
                   else move up
                 else if ( loc )
                 then
                   if ( loc % 10 - 7 ) then shoot upleft
                   else if ( loc % 10 - 6 ) then shoot left
                   else if ( loc % 10 - 5 ) then shoot downleft
                   else if ( loc % 10 - 4 ) then shoot down
                   else if ( loc % 10 - 3 ) then shoot downright
                   else if ( loc % 10 - 2 ) then shoot right
                   else if ( loc % 10 - 1 ) then shoot upright
                   else shoot up
                 else shoot left
                """, v1, cf);
        Parser psr2 = new Parser("""
                 loc = virus
                 if ( loc / 10 - 1 )
                 then
                   if ( loc % 10 - 7 ) then move upleft
                   else if ( loc % 10 - 6 ) then move left
                   else if ( loc % 10 - 5 ) then move downleft
                   else if ( loc % 10 - 4 ) then move down
                   else if ( loc % 10 - 3 ) then move downright
                   else if ( loc % 10 - 2 ) then move right
                   else if ( loc % 10 - 1 ) then move upright
                   else move up
                 else if ( loc )
                 then
                   if ( loc % 10 - 7 ) then shoot upleft
                   else if ( loc % 10 - 6 ) then shoot left
                   else if ( loc % 10 - 5 ) then shoot downleft
                   else if ( loc % 10 - 4 ) then shoot down
                   else if ( loc % 10 - 3 ) then shoot downright
                   else if ( loc % 10 - 2 ) then shoot right
                   else if ( loc % 10 - 1 ) then shoot upright
                   else shoot up
                 else shoot left
                """, a2, cf);
        psr1.eval();
        psr2.eval();
        //virus move 1,1 -> 2,2
        //antibody move 5,5 -> 4,4
        assertEquals("empty"
                , cf.checkUnit(1,1));
        assertEquals("Virus"
                , cf.checkUnit(2,2));
        assertEquals("empty"
                , cf.checkUnit(5,5));
        assertEquals("Antibody"
                , cf.checkUnit(4,4));

        psr1.eval();
        psr2.eval();
        //virus move 2,2 -> 3,3
        //antibody attack virus at 3,3
        assertEquals("empty"
                , cf.checkUnit(2,2));
        assertEquals("Virus"
                , cf.checkUnit(3,3));

        assertEquals(8
                , cf.getUnit(3,3).getCurHp());      //receiving damage virus
        assertEquals(10
                , cf.getUnit(4,4).getCurHp());      //attacking antibody

        psr1.eval();
        psr2.eval();
        assertEquals(7
                , cf.getUnit(3,3).getCurHp());      //HP virus  8 + 1 - 2
        assertEquals(7
                , cf.getUnit(4,4).getCurHp());      //HP antibody   10 - 3

        psr1.eval();
        psr2.eval();
        assertEquals(6
                , cf.getUnit(3,3).getCurHp());      //HP virus  7 + 1 - 2
        assertEquals(4
                , cf.getUnit(4,4).getCurHp());      //HP antibody   7-3

        psr1.eval();
        psr2.eval();
        psr1.eval();
        psr2.eval();
        assertEquals(4
                , cf.getUnit(3,3).getCurHp());      //HP virus  6 + 1 - 2 + 1 - 2
        assertEquals("empty"
                , cf.checkUnit(4,4));      //HP antibody   4 - 3 - 3

    }

    @Test
    void useSameParser() throws EvalError {
        CellsField cf = new CellsField(10,10);
        Virus v1 = new Virus("Normal" ,10,3,1,1, new int[]{1, 1});
        Virus v2 = new Virus("Normal" ,10,4,2, 4, new int[]{5, 5});
        cf.addUnit(v1);
        cf.addUnit(v2);
        Parser psr = new Parser("""
                move right
                """);
        psr.addCellsField(cf);
        psr.addUnit(v1, new HashMap<>());
        psr.eval();
        psr.addUnit(v2, new HashMap<>());
        psr.eval();

        // virus v1 move 1,1 -> 1,2
        // virus v2 move 5,5 -> 5,6
        assertEquals("empty"
                , cf.checkUnit(1,1));
        assertEquals("Virus"
                , cf.checkUnit(1,2));
        assertEquals("empty"
                , cf.checkUnit(5,5));
        assertEquals("Virus"
                , cf.checkUnit(5,6));
    }

    @Test
    void useSameParserButDifferentBinding() throws EvalError {
        CellsField cf = new CellsField(10,10);
        Virus v1 = new Virus("Normal" ,10,3,1,1, new int[]{1, 1});
        Virus v2 = new Virus("Normal" ,10,2,2, 4, new int[]{5, 5});
        cf.addUnit(v1);
        cf.addUnit(v2);
        Map<Host, Map<String, Integer>> bindingStorage = new HashMap<>();
        bindingStorage.put(v1, new HashMap<>());
        bindingStorage.put(v2, new HashMap<>());
        Parser psr = new Parser("""
                a = a + 5
                """);
        psr.addCellsField(cf);
        psr.addUnit(v1, bindingStorage.get(v1));
        psr.eval();
        psr.addUnit(v2, bindingStorage.get(v2));
        psr.eval();

    }

    @Test
    void simpleBattle_2() throws EvalError {
        CellsField cf = new CellsField(10,10);
        Virus v1 = new Virus("Normal" ,10,3,1,1, new int[]{1, 1});
        Antibody a2 = new Antibody("Normal" ,10,2,2, 4, new int[]{8, 6});
        cf.addUnit(v1);
        cf.addUnit(a2);
        Map<Host, Map<String, Integer>> bindingStorage = new HashMap<>();
        bindingStorage.put(v1, new HashMap<>());
        bindingStorage.put(a2, new HashMap<>());
        Parser psr1 = new Parser("""
                 loc = antibody
                 if ( loc / 10 - 1 )
                 then
                   if ( loc % 10 - 7 ) then move upleft
                   else if ( loc % 10 - 6 ) then move left
                   else if ( loc % 10 - 5 ) then move downleft
                   else if ( loc % 10 - 4 ) then move down
                   else if ( loc % 10 - 3 ) then move downright
                   else if ( loc % 10 - 2 ) then move right
                   else if ( loc % 10 - 1 ) then move upright
                   else move up
                 else if ( loc )
                 then
                   if ( loc % 10 - 7 ) then shoot upleft
                   else if ( loc % 10 - 6 ) then shoot left
                   else if ( loc % 10 - 5 ) then shoot downleft
                   else if ( loc % 10 - 4 ) then shoot down
                   else if ( loc % 10 - 3 ) then shoot downright
                   else if ( loc % 10 - 2 ) then shoot right
                   else if ( loc % 10 - 1 ) then shoot upright
                   else shoot up
                 else
                 {
                   dir = random % 8
                   if ( dir - 6 ) then move upleft
                   else if ( dir - 5 ) then move left
                   else if ( dir - 4 ) then move downleft
                   else if ( dir - 3 ) then move down
                   else if ( dir - 2 ) then move downright
                   else if ( dir - 1 ) then move right
                   else if ( dir ) then move upright
                   else move up
                 }
                 
                """);
        psr1.addCellsField(cf);
        psr1.addUnit(v1, bindingStorage.get(v1));
        Parser psr2 = new Parser("""
                 loc = virus
                 if ( loc / 10 - 2 )
                 then
                   if ( loc % 10 - 7 ) then move upleft
                   else if ( loc % 10 - 6 ) then move left
                   else if ( loc % 10 - 5 ) then move downleft
                   else if ( loc % 10 - 4 ) then move down
                   else if ( loc % 10 - 3 ) then move downright
                   else if ( loc % 10 - 2 ) then move right
                   else if ( loc % 10 - 1 ) then move upright
                   else move up
                 else if ( loc )
                 then
                   if ( loc % 10 - 7 ) then shoot upleft
                   else if ( loc % 10 - 6 ) then shoot left
                   else if ( loc % 10 - 5 ) then shoot downleft
                   else if ( loc % 10 - 4 ) then shoot down
                   else if ( loc % 10 - 3 ) then shoot downright
                   else if ( loc % 10 - 2 ) then shoot right
                   else if ( loc % 10 - 1 ) then shoot upright
                   else shoot up
                 else
                 {
                   dir = random % 8
                   if ( dir - 6 ) then move upleft
                   else if ( dir - 5 ) then move left
                   else if ( dir - 4 ) then move downleft
                   else if ( dir - 3 ) then move down
                   else if ( dir - 2 ) then move downright
                   else if ( dir - 1 ) then move right
                   else if ( dir ) then move upright
                   else move up
                 }
                 
                """);
        psr2.addCellsField(cf);
        psr2.addUnit(a2, bindingStorage.get(a2));
        for(int i = 0; i < 10; i++){
            if(cf.isAlive(v1))
                psr1.eval();
            if(cf.isAlive(a2))
                psr2.eval();
        }

    }


}
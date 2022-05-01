import java.util.Stack;
import java.lang.System;

public class Table {

    boolean [][] hasCardTop = new boolean[3][6];
    boolean [][] hasCardTable = new boolean [13][7];

    Card [][] cardsOnTable = new Card[13][7];
    Deck deck = new Deck();

    Stack<Card>down = new Stack<>();
    Card [] up = new Card[3];
    Stack<Card> throwaway = new Stack<>();

    Stack<Card>hearts = new Stack<>();
    Stack<Card>diamonds = new Stack<>();
    Stack<Card>spades = new Stack<>();
    Stack<Card>clovers = new Stack<>();

    Table(){
        hasCardTop[0][0] = true;

        for (int rows = 0; rows < 7; rows++){
            for (int cols = 0; cols < 7; cols++) {
                if (rows <= cols) {
                    hasCardTable[rows][cols] = true;
                    cardsOnTable[rows][cols] = deck.cardStack.peek();
                    deck.cardStack.pop();
                }

                if (rows == cols)
                    cardsOnTable[rows][cols].isUp();
            }
        }
        down = deck.cardStack;
    }

    public void DisplayTable(){
        //Initalizing longbar
        String longbar = "";
        for (int i = 0; i < 137; i++)
            longbar += '=';

        //Finding max rows
        int max = 1;
        for (int cols = 0; cols < 7; cols++) {
            int r = 0;
            int contestor = 0;
            while (hasCardTable[r++][cols])
                contestor++;
            if (contestor > max)
                max = contestor;
        } //finding max loop

        //Begin printing
        System.out.println(longbar);
        {
            if (!hasCardTop[0][0])
                System.out.print("EMPTY\t\t\t");
            else {
                down.peek().Display();
                System.out.print("\t");
            }
            if (!hasCardTop[0][1])
                System.out.print("EMPTY\t\t\t");
            else
                up[0].Display();

            System.out.print("\t\t\t\t\t\t");
        }//stack and overflow
        {
            if (!hasCardTop[0][2])
                System.out.print("EMPTY\t\t\t\t");
            else
                hearts.peek().Display();

            if (!hasCardTop[0][3])
                System.out.print("EMPTY\t\t\t\t");
            else
                diamonds.peek().Display();

            if (!hasCardTop[0][4])
                System.out.print("EMPTY\t\t\t\t");
            else
                spades.peek().Display();

            if (!hasCardTop[0][5])
                System.out.print("EMPTY\t\t\t\t");
            else
                clovers.peek().Display();

            System.out.println();
        } //foundations
        {
            if (hasCardTop[1][1]) {
                System.out.println("\t\t\t\t\t");
                up[1].Display();
                System.out.println();
            }

            if (hasCardTop[2][1]) {
                System.out.println("\t\t\t\t\t");
                up[2].Display();
                System.out.println();
            }
            System.out.println(longbar);
        } //second and third rows

        //Printing table
        for (int rows = 0; rows < max; rows++){
            for (int cols = 0; cols < 7; cols++){
                if (hasCardTable[rows][cols]) {
                    cardsOnTable[rows][cols].Display();
                    System.out.print("\t");
                }
                else
                    System.out.print("\t\t\t\t\t");
            }
            System.out.println();
        }
        System.out.println(longbar);
    }

}

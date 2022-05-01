import java.util.Stack;
import java.lang.System;

public class Table {

    boolean[][] hasCardTop = new boolean[3][6];
    boolean[][] hasCardTable = new boolean[13][7];

    Card[][] cardsOnTable = new Card[13][7];
    Deck deck = new Deck();

    Stack<Card> down = new Stack<>();
    Card[] up = new Card[3];
    Stack<Card> throwaway = new Stack<>();

    Stack<Card> hearts = new Stack<>();
    Stack<Card> diamonds = new Stack<>();
    Stack<Card> spades = new Stack<>();
    Stack<Card> clovers = new Stack<>();

    private int drawPileSize = 0;
    private int downSize = 24;

    Table() {
        hasCardTop[0][0] = true;

        for (int rows = 0; rows < 7; rows++) {
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

    public void Draw() {
        if (!down.isEmpty()) { //IDEAL

            CardPileStackPlusPlus();

            if (hasCardTop[2][1])
                throwaway.push(up[2]);

            for (int i = 0; i < 3; i++)
                hasCardTop[i][1] = i < stackSize();

            for (int i = 2; i > 0; i--)
                up[i] = up[i - 1];

            up[0] = down.peek();
            up[0].isUp();

            down.pop();

            if (down.isEmpty())
                hasCardTop[0][0] = false;

            DisplayTable();

        } else { //RESET
            CardPileReset();
            hasCardTop[0][0] = true;

            for (int i = 0; i < 3; i++) {
                throwaway.push(up[i]);
                up[i] = null;
                hasCardTop[i][1] = false;
            }

            int throwawaySize = throwaway.size();
            updateDownSize(throwawaySize);

            for (int i = 0; i < throwawaySize; i++) {
                throwaway.peek().isDown();
                down.push(throwaway.peek());
                throwaway.pop();

            }
            throwaway.clear();

            DisplayTable();
        }
    }

    public void DisplayTable() {
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
                System.out.print("EMPTY\t\t\t\t");
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
                System.out.print("\t\t\t\t\t");
                up[1].Display();
                System.out.println();
            }

            if (hasCardTop[2][1]) {
                System.out.print("\t\t\t\t\t");
                up[2].Display();
                System.out.println();
            }
            System.out.println(longbar);
        } //second and third rows

        //Printing table
        for (int rows = 0; rows < max; rows++) {
            for (int cols = 0; cols < 7; cols++) {
                if (hasCardTable[rows][cols]) {
                    cardsOnTable[rows][cols].Display();
                    System.out.print("\t");
                } else
                    System.out.print("\t\t\t\t\t");
            }
            System.out.println();
        }

        System.out.println(longbar);
    }

    public void CardPileStackPlusPlus() {
        if (drawPileSize < 3)
            drawPileSize++;
    }

    public void CardPileReset() {
        drawPileSize = 0;
    }

    public int stackSize() {
        return drawPileSize;
    }

    public void updateDownSize(int size){
        downSize = size;
    }
}

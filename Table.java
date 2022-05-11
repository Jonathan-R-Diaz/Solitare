import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import java.util.Scanner;
import java.util.Stack;
import java.lang.System;

public class Table{

    static final int MAX_ROWS = 20;
    static final int MAX_COLUMNS = 7;

    boolean[][] hasCardTop = new boolean[3][7];
    boolean[][] hasCardTable = new boolean[MAX_ROWS][MAX_COLUMNS];

    Card[][] cardsOnTable = new Card[MAX_ROWS][MAX_COLUMNS];

    Deck d = new Deck();

    Stack<Card> deck = new Stack<>();
    Card[] up = new Card[3];
    Stack<Card> throwaway = new Stack<>();

    Stack<Card> hearts = new Stack<>();
    Stack<Card> diamonds = new Stack<>();
    Stack<Card> spades = new Stack<>();
    Stack<Card> clubs = new Stack<>();

    private int drawPileSize = 0;
    private int deckSize = 24;

    Table() {
        hasCardTop[0][0] = true;

        for (int rows = 0; rows < 7; rows++) {
            for (int cols = 0; cols < 7; cols++) {
                if (rows <= cols) {
                    hasCardTable[rows][cols] = true;
                    cardsOnTable[rows][cols] = d.cardStack.peek();
                    d.cardStack.pop();
                }

                if (rows == cols)
                    cardsOnTable[rows][cols].Flip();
            }
        }
        deck = d.cardStack;
        setDeckSize(24);

    }

    public int findFirstCard(int col) {
        int row = 0;
        while (hasCardTable[row][col] && row < MAX_ROWS) {
            row++;
        }
        row--;
        return row;
    }

    public void DisplayTable() {
        //Initalizing longbar
        String longbar = longbar();

        //Begin printing
        System.out.println(longbar);

        //New Stack and overflow
        for (int i = 0; i < 2; i++) {
            if (hasCardTop[0][i]) {
                switch (i) {
                    case 0 -> {
                        deck.peek().Display();
                        System.out.print("\t");
                    }
                    case 1 -> up[0].Display();
                }
            } else {
                System.out.print("EMPTY\t\t\t\t");
            }
        }
        System.out.print("\t\t\t\t\t");

        //New Foundations
        for (int i = 3; i < 7; i++) {
            if (hasCardTop[0][i]) {
                switch (i) {
                    case 3:
                        hearts.peek().Display();
                        System.out.print("\t");
                        break;
                    case 4:
                        diamonds.peek().Display();
                        System.out.print("\t");
                        break;
                    case 5:
                        spades.peek().Display();
                        System.out.print("\t");
                        break;
                    case 6:
                        clubs.peek().Display();
                        System.out.println("\t");
                        break;
                }
            } else
                System.out.print("EMPTY\t\t\t\t");
        }
        System.out.println();

        //Second/Third rows
        for (int i = 1; i <= 2; i++) {
            if (hasCardTop[i][1]) {
                System.out.print("\t\t\t\t\t");
                up[i].Display();
                System.out.println();
            }
        }
            System.out.println(longbar);

        //Printing table
        for (int rows = 0; rows < max(); rows++) {
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

    public void addToDrawPile() {
        if (drawPileSize < 3)
            drawPileSize++;
    }
    public void subFromDrawPile() {
        if (drawPileSize > 0)
            drawPileSize--;
    }

    public void CardPileReset() {
        drawPileSize = 0;
    }

    public int stackSize() {
        return drawPileSize;
    }

    public void setDeckSize(int size) {
        deckSize = size;
    }

    public int getDeckSize() {
        return deckSize;
    }

    public void subDeckSize(){deckSize--;}

    public int max(){
        int max = 1;
        for (int cols = 0; cols < 7; cols++) {
            int r = 0;
            int contestor = 0;
            while (hasCardTable[r++][cols])
                contestor++;
            if (contestor > max)
                max = contestor;
        }
        return max;
    }

    public String longbar(){
        String longbar = "";
        for (int i = 0; i < 137; i++)
            longbar += '=';
        return longbar;
    }
}

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

    public int findFirstCard(int col) {
        int row = 0;
        while (hasCardTable[row][col]) {
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
            int j = 0;
            if (hasCardTop[0][i]) {
                switch (i) {
                    case 0 -> {
                        down.peek().Display();
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
        for (int i = 2; i < 7; i++) {
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
                        clovers.peek().Display();
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
    public void cardPileStackPlusPlus() {
        if (drawPileSize < 3)
            drawPileSize++;
    }

    public void cardPileStackMinusMinus() {
        drawPileSize--;
    }

    public void CardPileReset() {
        drawPileSize = 0;
    }

    public int stackSize() {
        return drawPileSize;
    }

    public void setDownSize(int size) {
        downSize = size;
    }

    public int getDownsize() {
        return downSize;
    }

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

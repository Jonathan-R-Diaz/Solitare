import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.awt.*;

public class Game extends Table{

    public void Draw() {
        if (!down.isEmpty()) { //IDEAL

            cardPileStackPlusPlus();

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

            DisplayTableOld();

        } else { //RESET
            CardPileReset();
            hasCardTop[0][0] = true;

            for (int i = 0; i < 3; i++) {
                throwaway.push(up[i]);
                up[i] = null;
                hasCardTop[i][1] = false;
            }

            int throwawaySize = throwaway.size();
            setDownSize(throwawaySize);

            for (int i = 0; i < getDownsize(); i++) {
                throwaway.peek().isDown();
                down.push(throwaway.peek());
                throwaway.pop();

            }
            throwaway.clear();

            DisplayTable();
        }
    }

    public void moveCard(int fromRow, int fromCol, int toCol) {

        System.out.println("moveCard called.");
        int toCardCounter = 0;

        if (hasCardTable[fromRow][fromCol] && cardsOnTable[fromRow][fromCol].isCurrentlyUp()) { //Has a card

            //Step 1. Find the position of the top card of the "to" stack
            while (hasCardTable[toCardCounter][toCol])
                toCardCounter++;

            //Step 2. Flip previous card if it exists
            if (fromRow > 0) { //Flips over previous card
                if (hasCardTable[fromRow - 1][fromCol]) {
                    cardsOnTable[fromRow - 1][fromCol].isUp();
                }
            }

            //Step 3. Move stack
            System.out.println("fromRow: " + fromRow + ", fromCol: " + fromCol + ", toRow: " + toCardCounter + ", toCol: " + toCol);
            while (hasCardTable[fromRow][fromCol]) {
                System.out.println("test");
                cardsOnTable[toCardCounter][toCol] = cardsOnTable[fromRow][fromCol];
                hasCardTable[toCardCounter++][toCol] = true;
                hasCardTable[fromRow][fromCol] = false;
                cardsOnTable[fromRow++][fromCol] = null;
            }

            //Step 4. Display Table and exit
            DisplayTable();

        } else {
            if (hasCardTable[fromRow][fromCol]) {
                if (cardsOnTable[fromRow][fromCol].isCurrentlyDown()) {
                    System.out.println("Card is not flipped over");
                }

            } else
                System.out.println("No card at [" + fromRow + "][" + fromCol + "]");
        } //No card
    }

    public void moveIntoFoundation(int fromRow, int fromCol, String suit) {
        System.out.println("moveIntoFoundation called.");
        //System.out.println("*Move into foundation*\nfromRow: " + fromRow + ", fromCol: " + fromCol + ", suit: " + suit);
        //System.out.print("Card trying to move: ");
        cardsOnTable[fromRow][fromCol].Display();
        System.out.println();
        //System.out.println("suit: |" + suit + "| = |" + cardsOnTable[fromRow][fromCol].getSuit() + '|');
        //System.out.println(cardsOnTable[fromRow][fromCol].getSuit() + '|');
        if (cardsOnTable[fromRow][fromCol].getSuit() == suit) {
            System.out.println("GOT THROUGH THE FIRST IF STATEMENT");
            //Flips previous card if exists
            if (fromRow > 0) { //Flips over previous card
                if (hasCardTable[fromRow - 1][fromCol]) {
                    cardsOnTable[fromRow - 1][fromCol].isUp();
                }
            }


            switch (suit) {

                case "Hearts" -> {
                    hearts.push(cardsOnTable[fromRow][fromCol]);
                    hasCardTop[0][2] = true;
                    hasCardTable[fromRow][fromCol] = false;
                    cardsOnTable[fromRow][fromCol] = null;
                }
                case "Diamonds" -> {
                    diamonds.push(cardsOnTable[fromRow][fromCol]);
                    hasCardTop[0][3] = true;
                    cardsOnTable[fromRow][fromCol] = null;
                    hasCardTable[fromRow][fromCol] = false;
                }
                case "Spades*" -> {
                    spades.push(cardsOnTable[fromRow][fromCol]);
                    hasCardTop[0][4] = true;
                    cardsOnTable[fromRow][fromCol] = null;
                    hasCardTable[fromRow][fromCol] = false;
                }
                case "Clovers*" -> {
                    clovers.push(cardsOnTable[fromRow][fromCol]);
                    hasCardTop[0][5] = true;
                    cardsOnTable[fromRow][fromCol] = null;
                    hasCardTable[fromRow][fromCol] = false;
                }
            }
        } else
            System.out.println("FOUNDATIONS DIDNT MATCH");
        DisplayTable();
    }

    public void DrawToBoard(int col) {

        int topCard = findFirstCard(col) + 1;

        cardPileStackMinusMinus();

        if (!hasCardTop[1][1])
            hasCardTop[0][1] = false;

        cardsOnTable[topCard][col] = up[0];
        for (int i = 0; i < 2; i++) {
            up[i] = up[i + 1];
        }
        up[2] = null;
        hasCardTable[topCard][col] = true;

        for (int i = 0; i < 3; i++)
            hasCardTop[i][1] = i < stackSize();

        DisplayTable();
    }

}


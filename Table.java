import java.util.Stack;
import java.lang.System;

public class Table{

    static final int MAX_ROWS = 20;
    static final int MAX_COLUMNS = 7;

    boolean[][] hasCardTop = new boolean[3][7];
    boolean[][] hasCardTable = new boolean[MAX_ROWS][MAX_COLUMNS];

    Card[][] cardsOnTable = new Card[MAX_ROWS][MAX_COLUMNS];

    Deck d;

    Stack<Card> deck = new Stack<>();
    Card[] up = new Card[3];
    Stack<Card> throwaway = new Stack<>();

    Stack<Card> hearts = new Stack<>();
    Stack<Card> diamonds = new Stack<>();
    Stack<Card> spades = new Stack<>();
    Stack<Card> clubs = new Stack<>();

    private int drawPileSize = 0;
    private int deckSize = 24;
    private int currentDeckSize = 24;

    private int throwawaySize = 0;

    Table() {
        System.out.println("Table constructor called");
        d = new Deck();
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

    Table(Table oldTable){

        //Copy for cardsOnTable and hasCardTable
        for (int rows = 0; rows < MAX_ROWS; rows++){
            for (int cols = 0; cols < MAX_COLUMNS; cols++) {
                this.hasCardTable[rows][cols] = oldTable.hasCardTable[rows][cols];
                this.cardsOnTable[rows][cols] = new Card();
                if (oldTable.hasCardTable[rows][cols])
                    this.cardsOnTable[rows][cols].copyThisCard(oldTable.cardsOnTable[rows][cols]);
            }
        }

        //Copy for top panel
        for (int rows = 0; rows < 3; rows++){
            for (int cols = 0; cols < 7; cols++){
                this.hasCardTop[0][cols] = oldTable.hasCardTop[0][cols];
                if (cols == 1)
                    this.hasCardTop[rows][1] = oldTable.hasCardTop[rows][1];
            }
        }

        //System.out.println("Copying deck");
        stackCopier(this.deck, oldTable.deck, oldTable.deck.size());
        if (this.deck.size() > 0 && this.deck.peek().isUp())
            this.deck.peek().Flip();

        for (int i = 0; i < 3; i++) {
            this.hasCardTop[i][1] = oldTable.hasCardTop[i][1];
            this.up[i] = null;
            this.up[i] = oldTable.up[i];
        }

        stackCopier(this.throwaway, oldTable.throwaway, oldTable.throwaway.size());
        stackCopier(this.hearts, oldTable.hearts, oldTable.hearts.size());
        stackCopier(this.diamonds, oldTable.diamonds, oldTable.diamonds.size());
        stackCopier(this.spades, oldTable.spades, oldTable.spades.size());
        stackCopier(this.clubs, oldTable.clubs, oldTable.clubs.size());

        this.setDeckSize(oldTable.getDeckSize());
        this.setDrawPileSize(oldTable.getDeckSize());
    }

    public static void stackCopier(Stack<Card> newStack, final Stack<Card> oldStack, int size){
        Card [] cardArray = new Card[25];
        newStack.clear();
        for (int card = 0; card < size; card++) {
            cardArray[card] = new Card();
            cardArray[card].copyThisCard(oldStack.peek());
            oldStack.pop();
        }
        for (int card = size - 1; card >= 0; card--) {
            newStack.push(cardArray[card]);
            oldStack.push(cardArray[card]);
            newStack.peek().copyThisCard(cardArray[card]);
            oldStack.peek().copyThisCard(cardArray[card]);
        }
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
        System.out.println(longbar());

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
            System.out.println(longbar());

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
        System.out.println(longbar());
    }

    public void addToThrowawayPile(){throwawaySize++;}
    public void addToDrawPile() {
        if (drawPileSize < 3)
            drawPileSize++;
    }
    public void subFromDrawPile() {
        if (drawPileSize > 0)
            drawPileSize--;
    }

    public int stackSize() {
        return drawPileSize;
    }

    public void setDeckSize(int size) {
        deckSize = size;
    }
    public void setDrawPileSize(int size){ if (0 <= size && size < 4) drawPileSize = size;}

    public int getCurrentDeckSize(){return currentDeckSize;}
    public int getDeckSize() {
        return deckSize;
    }
    public int getDrawPileSize(){ return drawPileSize;}
    public int getThrowAwaySize(){ return throwawaySize;}

    public void subDeckSize(){ deckSize--; }
    public void subCurrentDeckSize(){ currentDeckSize--; }
    public void resetCurrentDeckSize(){ currentDeckSize = deckSize; throwawaySize = 0; drawPileSize = 0;}

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

    //RULES

    //DrawToTable
    public boolean isValidMove(int toCol) {
        Card draw = up[0];
        Card table = null;

        if (findFirstCard(toCol) != -1)
            table = cardsOnTable[findFirstCard(toCol)][toCol];

        if (isKing(draw, toCol)) {
            System.out.println("King selected from draw");
            return true;
        }

        return TableRules(draw, table);
    }

    //DrawToFoundation
    public boolean isValidMove(String suit) {
        Card draw = up[0];
        Card foundation = FoundationCardFinder(suit);

        return FoundationRules(draw, foundation);
    }

    //moveCard
    public boolean isValidMove(int fromRow, int fromCol, int toCol) {
        Card card1 = null;
        Card card2 = null;

        if (hasCardTable[fromRow][fromCol] && cardsOnTable[fromRow][fromCol].isUp())
            card1 = cardsOnTable[fromRow][fromCol];

        if (isKing(card1, toCol))
            return true;

        if (findFirstCard(toCol) >= 0)
            if (hasCardTable[findFirstCard(toCol)][toCol])
                card2 = cardsOnTable[findFirstCard(toCol)][toCol];

        return TableRules(card1, card2);
    }

    //TableToFoundation
    public boolean isValidMove(int fromRow, int fromCol, String suit){

        System.out.println("Table to foundation called");
        Card table =  cardsOnTable[fromRow][fromCol];
        Card foundation = FoundationCardFinder(suit);
        System.out.println(FoundationRules(table, foundation));

        return FoundationRules(table, foundation);
    }

    //FoundationToTable
    public boolean isValidMove(int pile, int toCol){
        Card foundation = FoundationCardFinder(pile);
        Card table = null;
        if (findFirstCard(toCol) >= 0 )
            table = cardsOnTable[findFirstCard(toCol)][toCol];

        if (table != null)
            return TableRules(foundation, table);

        return false;
    }

    public boolean TableRules(Card from, Card to){
        if (from != null && to != null)
            return isOneSmaller(from, to) && hasDifferentColors(from, to);

        else
            return false;
    }
    public boolean FoundationRules(Card from, Card to) {
        if (from != null)
            return hasSameSuits(from, to) && isOneBigger(from, to);

        return false;
    }


    public Card FoundationCardFinder(String suit){
        Card foundation = null;
        int s = 0;
        switch (suit) {

            case "hearts" -> {
                if (!hearts.isEmpty())
                    foundation = hearts.peek();
                s = 1;
            }
            case "diamonds" -> {
                if (!diamonds.isEmpty())
                    foundation = diamonds.peek();
                s = 2;
            }
            case "spades" -> {
                if (!spades.isEmpty())
                    foundation = spades.peek();
                s = 3;
            }
            case "clubs" -> {
                if (!clubs.isEmpty())
                    foundation = clubs.peek();
                s = 4;
            }
        }

        if (foundation == null)
            foundation = new Card(0, s);

        return foundation;
    }
    public Card FoundationCardFinder(int suit){
        Card foundation = null;
        int s = 0;
        switch (suit) {

            case 3 -> {
                if (!hearts.isEmpty())
                    foundation = hearts.peek();
                s = 1;
            }
            case 4 -> {
                if (!diamonds.isEmpty())
                    foundation = diamonds.peek();
                s = 2;
            }
            case 5 -> {
                if (!spades.isEmpty())
                    foundation = spades.peek();
                s = 3;
            }
            case 6 -> {
                if (!clubs.isEmpty())
                    foundation = clubs.peek();
                s = 4;
            }
        }

        if (foundation == null)
            foundation = new Card(0, s);

        return foundation;
    }

    public boolean hasSameSuits(Card from, Card to){
        if (from.getSuit() == to.getSuit())
            return true;
        else
            return false;
    }
    public boolean hasDifferentColors(Card from, Card to){
        if ((from.isRed() && to.isBlack()) || (from.isBlack() && to.isRed()))
            return true;
        else
            return false;
    }

    public boolean isOneSmaller(Card from, Card to){
        if (from.getValue() + 1 == to.getValue())
            return true;
        else
            return false;
    }
    public boolean isOneBigger(Card from, Card to){
        if (from.getValue() - 1 == to.getValue())
            return true;
        else
            return false;
    }
    public boolean isKing(Card card, int toCol){
        if (card != null) {
            return findFirstCard(toCol) == -1 && card.getValue() == 13 && !hasCardTable[0][toCol];
        }
        return false;
    }

    public boolean WinCondition(){
        return (AllCardsFlipped() && DrawPileEmpty()) || AllCardsSorted();
    }
    public boolean AllCardsFlipped(){
        for (int row = 0; row < MAX_ROWS; row++){
            for(int col = 0; col < MAX_COLUMNS; col++){
                if(hasCardTable[row][col] && cardsOnTable[row][col].isDown())
                    return false;
            }
        }
        return true;
    }
    public boolean DrawPileEmpty(){
        return deck.empty() && throwaway.empty() && up[0] == null;
    }
    public boolean AllCardsSorted(){
        return hearts.size() + diamonds.size() + spades.size() + clubs.size() == 52;
    }

    //MOVES

    public void Draw() {
        //System.out.println("Draw function called");

        if (!deck.isEmpty()) { //IDEAL

            subCurrentDeckSize();
            addToDrawPile();

            if (hasCardTop[2][1]) {
                throwaway.push(up[2]);
                addToThrowawayPile();
            }

            for (int i = 0; i < 3; i++)
                hasCardTop[i][1] = i < stackSize();

            for (int i = 2; i > 0; i--)
                up[i] = up[i - 1];

            up[0] = deck.peek();
            up[0].Flip();

            deck.pop();

            if (deck.isEmpty())
                hasCardTop[0][0] = false;


        } else { //RESET

            if (getDeckSize() > 0) {

                //System.out.println("Resetting deck");
                for (int i = 0; i < stackSize(); i++) {
                    throwaway.push(up[i]);
                    up[i] = null;
                    hasCardTop[i][1] = false;
                    addToThrowawayPile();
                }

                //System.out.println("Should be equal, ThrowawaySize == DeckSize");
                //System.out.println(getThrowAwaySize() + " == " + getDeckSize());

                for (int i = 0; i < getThrowAwaySize(); i++) {
                    deck.push(throwaway.peek());
                    throwaway.pop();
                    deck.peek().Flip();
                }

                if (deck.size() > 0)
                    hasCardTop[0][0] = true;

                resetCurrentDeckSize();
            }
            else
                hasCardTop[0][0] = false;
        }
        //System.out.println("Total cards in deck and draw: " + getDeckSize());
        //System.out.println("Total cards currently in deck: " + getCurrentDeckSize());
        //System.out.println("Total cards in throwaway: " + getThrowAwaySize());
    }
    public boolean DrawToTable(int toCol) {

        if (isValidMove(toCol)) {

            int topCard = findFirstCard(toCol) + 1;

            subFromDrawPile();
            subDeckSize();

            if (!hasCardTop[1][1])
                hasCardTop[0][1] = false;

            cardsOnTable[topCard][toCol] = up[0];
            hasCardTable[topCard][toCol] = true;

            DrawCycler();
        }

        return isValidMove(toCol);
    }
    public boolean DrawToFoundation(String suit) {

        System.out.println("DrawToFoundation called");
        System.out.println(suit);

        if (isValidMove(suit)) {
            subFromDrawPile();
            subDeckSize();
            switch (suit) {

                case "hearts" -> {
                    hearts.push(up[0]);
                    hasCardTop[0][3] = true;
                }
                case "diamonds" -> {
                    diamonds.push(up[0]);
                    hasCardTop[0][4] = true;
                }
                case "spades" -> {
                    spades.push(up[0]);
                    hasCardTop[0][5] = true;
                }
                case "clubs" -> {
                    clubs.push(up[0]);
                    hasCardTop[0][6] = true;
                }
            }
            DrawCycler();
        }

        return isValidMove(suit);
    }


    public void DrawCycler() {
        //System.out.println("DrawCycler called");
        for (int i = 0; i < 2; i++) {
            up[i] = up[i + 1];
        }
        up[2] = null;

        for (int i = 0; i < 3; i++) {
            hasCardTop[i][1] = i < stackSize();
            //System.out.println(i < stackSize());
        }
    }

    public boolean TableToTable(int fromRow, int fromCol, int toCol) {

        //System.out.println("moveCard called.");

        int toCardCounter = findFirstCard(toCol) + 1;

        //System.out.println(toCardCounter);
        if (isValidMove(fromRow, fromCol, toCol)){

            //Step 1. Flip previous card if it exists
            if (fromRow > 0) { //Flips over previous card
                if (hasCardTable[fromRow - 1][fromCol] && cardsOnTable[fromRow - 1][fromCol].isDown()) {
                    cardsOnTable[fromRow - 1][fromCol].Flip();
                }
            }

            //Step 2. Move stack
            //System.out.println("fromRow: " + fromRow + ", fromCol: " + fromCol + ", toRow: " + toCardCounter + ", toCol: " + toCol);
            while (hasCardTable[fromRow][fromCol]) {
                //System.out.println("test");
                cardsOnTable[toCardCounter][toCol] = cardsOnTable[fromRow][fromCol];
                hasCardTable[toCardCounter++][toCol] = true;
                hasCardTable[fromRow][fromCol] = false;
                cardsOnTable[fromRow++][fromCol] = null;
            }
        }
        return isValidMove(fromRow, fromCol, toCol);
    }

    public boolean TableToFoundation(int fromRow, int fromCol, String suit) {
        System.out.println("moveIntoFoundation called.");
        System.out.println("TABLE.TableIntoFoundation(" + fromRow + ", " + fromCol + ", " + suit);
        System.out.println("\nfromRow: " + fromRow + ", fromCol: " + fromCol + ", suit: " + suit);
        System.out.print("Card trying to move: ");
        cardsOnTable[fromRow][fromCol].Display();
        System.out.println();
        System.out.println("suit passed in: |" + suit + "| = |" + cardsOnTable[fromRow][fromCol].getSuit() + '|');
        System.out.println(cardsOnTable[fromRow][fromCol].getSuit() + '|');

        if (isValidMove(fromRow, fromCol, suit)) {

            //Flips previous card if exists
            if (fromRow > 0) { //Flips over previous card
                if (hasCardTable[fromRow - 1][fromCol] && cardsOnTable[fromRow - 1][fromCol].isDown())
                    cardsOnTable[fromRow - 1][fromCol].Flip();
            }


            switch (suit) {

                case "hearts" -> {
                    hearts.push(cardsOnTable[fromRow][fromCol]);
                    hasCardTop[0][3] = true;
                    hasCardTable[fromRow][fromCol] = false;
                    cardsOnTable[fromRow][fromCol] = null;
                }
                case "diamonds" -> {
                    diamonds.push(cardsOnTable[fromRow][fromCol]);
                    hasCardTop[0][4] = true;
                    cardsOnTable[fromRow][fromCol] = null;
                    hasCardTable[fromRow][fromCol] = false;
                }
                case "spades" -> {
                    spades.push(cardsOnTable[fromRow][fromCol]);
                    hasCardTop[0][5] = true;
                    cardsOnTable[fromRow][fromCol] = null;
                    hasCardTable[fromRow][fromCol] = false;
                }
                case "clubs" -> {
                    clubs.push(cardsOnTable[fromRow][fromCol]);
                    hasCardTop[0][6] = true;
                    cardsOnTable[fromRow][fromCol] = null;
                    hasCardTable[fromRow][fromCol] = false;
                }
            }
        }
        return isValidMove(fromRow, fromCol, suit);
    }
    public boolean FoundationToTable(int pile, int toCol){
        //System.out.println("Foundation to table called.");
        //System.out.println("FoundationToTable(" + pile + ", " + toCol + ")");
        Card temp = null;
        int toCardCounter = findFirstCard(toCol) + 1;
        if (isValidMove(pile, toCol)){

            //Step 2. Get card
            switch (pile) {
                case 3:
                    temp = hearts.peek();
                    hearts.pop();
                    if (hearts.size() == 0)
                        hasCardTop[0][pile] = false;
                    break;
                case 4:
                    temp = diamonds.peek();
                    diamonds.pop();
                    if (hearts.size() == 0)
                        hasCardTop[0][pile] = false;
                    break;
                case 5:
                    temp = spades.peek();
                    spades.pop();
                    if (hearts.size() == 0)
                        hasCardTop[0][pile] = false;
                    break;
                case 6:
                    temp = clubs.peek();
                    clubs.pop();
                    if (hearts.size() == 0)
                        hasCardTop[0][pile] = false;
                    break;
                default:
                    System.out.println("FOUNDATION TO TABLE CRASH");
            }
            //Step 3. Place card
            cardsOnTable[toCardCounter][toCol] = temp;
            hasCardTable[toCardCounter][toCol] = true;
        }
        return isValidMove(pile, toCol);
    }

    public void CHEAT_DeleteDeck(){
        int temp = getDeckSize();
        for (int i = 0; i < temp; i++){
            deck.pop();
            subDeckSize();
        }
        hasCardTop[0][0] = false;
    }
    public void CHEAT_FlipAllCards(){
        for (int i = 0; i < MAX_ROWS; i++){
            for (int j = 0; j < MAX_COLUMNS; j++){
                if (hasCardTable[i][j] && cardsOnTable[i][j].isDown())
                    cardsOnTable[i][j].Flip();
            }
        }
    }
    public void CHEAT_PumpFoundations(){
        for (int i = 0; i < 13; i++){
            Card temp = new Card(69,420);
            hearts.push(temp);
            diamonds.push(temp);
            spades.push(temp);
            clubs.push(temp);
        }
    }
}

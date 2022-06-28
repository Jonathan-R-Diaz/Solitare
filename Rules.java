public class Rules {
/*
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

        Card table =  cardsOnTable[fromRow][fromCol];
        Card foundation = FoundationCardFinder(suit);

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
    */
}

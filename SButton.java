import javax.swing.*;

public class SButton extends JButton {
    private int row, col, pile;
    private boolean initialized, isDraw, isFoundation, isCard, isKing, isOnTable;

    public void setCard(int r, int c){
        row = r;
        col = c;
        initialized = true;
        isCard = true;
        isDraw = false;
        isFoundation = false;
        isOnTable = true;
    }

    public void setDraw(){
        initialized = true;
        isCard = false;
        isDraw = true;
        isFoundation = false;
        isKing = false;
        isOnTable = false;
    }

    public void setFoundation(int num){
        pile = num;

        initialized = true;
        isCard = false;
        isDraw = false;
        isFoundation = true;
        isOnTable = false;
    }

    public void reset(){
        row = 0;
        col = 0;
        initialized = false;
        isCard = false;
        isDraw = false;
        isFoundation = false;
        isOnTable = false;
    }

    public int getRow() {
        return row;
    }
    public int getCol(){
        return col;
    }
    public int getPile(){ return pile; }


    public boolean isInitialized(){
        return initialized;
    }
    public boolean isNotInitialized(){ return !initialized;}
    public boolean isCard(){ return isCard;}
    public boolean isDraw(){
        return isDraw;
    }
    public boolean isFoundation(){ return isFoundation;}
    public boolean isKing(){return isKing;}
    public boolean isOnTable(){return isOnTable;}

    public boolean isSameButtonAs(SButton other){
        return getRow() == other.getRow() && getCol() == other.getCol();
    }
}

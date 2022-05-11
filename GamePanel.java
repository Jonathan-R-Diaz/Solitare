import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePanel extends JPanel{
    static final int MAX_ROWS = 20;
    static final int MAX_COLS = 7;
    static final int TABLE_GREEN = 0x158209;
    private final ActionListener AL = new AL();

    JButton [] cardsOnTop = new JButton[7];
    JButton [][] cardsOnTable = new JButton[MAX_ROWS][MAX_COLS];

    JButton DeckButton;
    JButton DrawPileButton;
    JButton Hearts;
    JButton Diamonds;
    JButton Spades;
    JButton Clubs;

    PButton button1 = new PButton();
    PButton button2 = new PButton();
    Moves TABLE;
    JPanel TopPanel;
    JPanel CardGridPanel;

    public class PButton extends JButton{
        private int row, col, pile;
        private boolean initialized, isDraw, isFoundation, isCard, isKing, isEmptyCol;

        public void setCard(int r, int c){
            row = r;
            col = c;
            initialized = true;
            isCard = true;
            isDraw = false;
            isFoundation = false;
            if (!TABLE.hasCardTable[r][c] && r == 0)
                isEmptyCol = true;
            else if (TABLE.cardsOnTable[r][c].getValue() == 13)
                isKing = true;

            button1.cout();
        }

        public void setDraw(){
            initialized = true;
            isCard = false;
            isDraw = true;
            isFoundation = false;
            isKing = false;
            isEmptyCol = false;
        }

        public void setFoundation(int num){
            pile = num;

            initialized = true;
            isCard = false;
            isDraw = false;
            isFoundation = true;
        }

        public void reset(){
            row = 0;
            col = 0;
            initialized = false;
            isCard = false;
            isDraw = false;
            isFoundation = false;
            isEmptyCol = false;
        }

        public int getRow() {
            return row;
        }
        public int getCol(){
            return col;
        }

        public boolean isInitialized(){
            return initialized;
        }
        public boolean isCard(){ return isCard;}
        public boolean isDraw(){
            return isDraw;
        }
        public boolean isFoundation(){ return isFoundation;}
        public boolean isKing(){return isKing;}
        public boolean isEmpty(){return isEmptyCol;}

        public void cout(){
            System.out.println("\trow: " + row);
            System.out.println("\tcol: " + col);
            System.out.println("\tinitialized: " + initialized);
            System.out.println("\tisCard: " + isCard);
            System.out.println("\tisDraw: " + isDraw);
            System.out.println("\tisFoundations: " + isFoundation);
            if (isKing)
                System.out.println("\tIS KING");
        }
    }

    GamePanel() {

        setLayout(new BorderLayout());
        TABLE = new Moves();

        CardGridPanel = new JPanel(new GridLayout(MAX_ROWS + 1, MAX_COLS, 0 ,-96));
        CardGridPanel.setBackground(new Color(TABLE_GREEN));


        TopPanel = new JPanel();
        TopPanel.setLayout(new FlowLayout());
        TopPanel.setBackground(new Color(TABLE_GREEN));

        JLabel title = new JLabel("Solitare");

        add(title, BorderLayout.NORTH);

        AddTopPanel();
        add(TopPanel, BorderLayout.NORTH);
        AddCardGrid();
        add(CardGridPanel, BorderLayout.CENTER);

        TABLE.DisplayTable();
        //SolitaireAlgorithm(5);
        firstRound(2);
        updateGUI();
    }

    public class AL implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

            //FIND BUTTON
            int r = 0, c = 0;


            for (int i = 0; i < MAX_ROWS; i++) {
                for (int j = 0; j < MAX_COLS; j++) {
                    if (e.getSource() == cardsOnTable[i][j]) {
                        r = i;
                        c = j;
                    }
                }
            }

            //CARD
            if (button1.isInitialized()) {

                //FOUNDATION TO TABLE
                if(button1.isFoundation) {
                    button2.setCard(r, c);
                    TABLE.FoundationToTable(button1.pile, button2.col);
                    ResetButtons();
                    updateFoundations();
                    updateTable();
                }

                //DRAW TO *
                else if (button1.isDraw()) {
                    //System.out.println("Button 1 isDraw();");
                    //DRAW TO FOUNDATION
                    if (foundationsClicked(e)) {
                        //System.out.println("In foundationsClicked");
                        if (e.getSource() == Hearts) {
                            TABLE.DrawToFoundation("Hearts");
                        }
                        else if (e.getSource() == Diamonds) {
                            TABLE.DrawToFoundation("Diamonds");
                        }
                        else if (e.getSource() == Spades) {
                            TABLE.DrawToFoundation("Spades");
                        }
                        else if (e.getSource() == Clubs) {
                            TABLE.DrawToFoundation("clubs");
                        }
                        updateDeckAndDraw();
                        updateFoundations();
                        ResetButtons();
                    }

                    //DRAW TO TABLE
                    else {
                        button2.setCard(r,c);
                        TABLE.DrawToTable(button2.col);
                        updateDeckAndDraw();
                        updateTable();
                    }
                    ResetButtons();
                }

                //KING MOVE
                else if (button1.isDraw() && r == 0) {
                    button2.setCard(r, c);
                    TABLE.isValidMove(c);
                    TABLE.moveCard(button1.row, button1.col, button2.col);
                    updateTable();
                    ResetButtons();
                }

                //TABLE TO *
                else {

                    //TABLE TO FOUNDATION
                    if (foundationsClicked(e) && button1.isCard()) {

                        if (e.getSource() == cardsOnTop[3]) {
                            //System.out.println("TABLE.TableIntoFoundation(" + button1.row + ", " + button1.col + ", \"Hearts\")");
                            TABLE.TableIntoFoundation(button1.row, button1.col, "hearts");
                            TABLE.TableIntoFoundation(button1.row, button1.col, "hearts");
                        } else if (e.getSource() == cardsOnTop[4]) {
                            //System.out.println("TABLE.TableIntoFoundation(" + button1.row + ", " + button1.col + ", \"Diamonds\")");
                            TABLE.TableIntoFoundation(button1.row, button1.col, "diamonds");
                        } else if (e.getSource() == cardsOnTop[5]) {
                            //System.out.println("TABLE.TableIntoFoundation(" + button1.row + ", " + button1.col + ", \"Spades\")");
                            TABLE.TableIntoFoundation(button1.row, button1.col, "spades");
                        } else if (e.getSource() == cardsOnTop[6]) {
                            //System.out.println("TABLE.TableIntoFoundation(" + button1.row + ", " + button1.col + ", \"clubs\")");
                            TABLE.TableIntoFoundation(button1.row, button1.col, "clubs");
                        }
                        updateFoundations();
                        updateTable();
                        ResetButtons();
                    }

                    //TABLE TO TABLE
                    else {
                        if (button1.isKing || (TABLE.hasCardTable[r][c] && TABLE.cardsOnTable[r][c].isUp())) {
                            button2.setCard(r, c);
                            if (TABLE.isValidMove(button1.row, button1.col, button2.col))
                                TABLE.moveCard(button1.row, button1.col, button2.col);

                        } else {
                            System.out.println("Can't pick that card, face down");
                        }
                        updateTable();
                        ResetButtons();
                    }
                }
            }

            //DECK
            else if (e.getSource() == cardsOnTop[0]) {
                TABLE.Draw();
                ResetButtons();
                updateDeckAndDraw();
            }
            //DRAW
            else if (e.getSource() == cardsOnTop[1] && !button1.isInitialized())
                if (TABLE.up[0] != null)
                    button1.setDraw();
                else
                    ResetButtons();

                //FOUNDATION
            else if (foundationsClicked(e) && !button1.isInitialized()) {
                for(int i = 3; i < 7; i++){
                    if (e.getSource() == cardsOnTop[i])
                        button1.setFoundation(i);
                }
            }

            else {
                if (TABLE.hasCardTable[r][c] && TABLE.cardsOnTable[r][c].isUp()) {
                    button1.setCard(r, c);
                } else {
                    System.out.println("Can't pick that card, face down");
                    ResetButtons();
                }
            }

            if (TABLE.WinCondition())
                System.out.println("YOU WON!!!");

            PrintExitStatement("Leaving ActionListener");

            TABLE.DisplayTable();
        }
    }

    public final void updateGUI() {
        //System.out.println("updateGrid called");

        updateDeckAndDraw();
        updateFoundations();
        updateTable();


    }
    public final void updateDeckAndDraw(){
        //System.out.println("updateDeckAndDraw called");
        //DECK
        if (TABLE.hasCardTop[0][0]){
            cardsOnTop[0].setText("");
            cardsOnTop[0].setIcon(TABLE.deck.peek().getCardIcon());
            cardsOnTop[0].setBorderPainted(false);
        }
        else {
            cardsOnTop[0].setText("Deck empty");
            cardsOnTop[0].setIcon(null);
            cardsOnTop[0].setBorderPainted(true);
        }

        //DRAW
        if (TABLE.hasCardTop[0][1]) {
            DrawPileButton.setText("");
            DrawPileButton.setIcon(TABLE.up[0].getCardIcon());
        }
        else {
            DrawPileButton.setText("Empty");
            cardsOnTop[1].setIcon(null);
        }
    }
    public final void updateFoundations(){
        System.out.println("updateFoundations called");
        for (int i = 3; i < 7; i++) {

            switch (i){
                case 3 -> {
                    if (!TABLE.hearts.isEmpty()) {
                        cardsOnTop[i].setIcon(TABLE.hearts.peek().getCardIcon());
                        cardsOnTop[i].setText("");
                    }
                    else
                    {
                        cardsOnTop[i].setIcon(null);
                        cardsOnTop[i].setText("Hearts");
                    }
                }
                case 4 -> {
                    if (!TABLE.diamonds.isEmpty()) {
                        cardsOnTop[i].setIcon(TABLE.diamonds.peek().getCardIcon());
                        cardsOnTop[i].setText("");
                    }
                    else
                    {
                        cardsOnTop[i].setIcon(null);
                        cardsOnTop[i].setText("Diamonds");
                    }
                }
                case 5 -> {
                    if (!TABLE.spades.isEmpty()) {
                        cardsOnTop[i].setIcon(TABLE.spades.peek().getCardIcon());
                        cardsOnTop[i].setText("");
                    }
                    else
                    {
                        cardsOnTop[i].setIcon(null);
                        cardsOnTop[i].setText("Spades");
                    }
                }
                case 6 -> {
                    if (!TABLE.clubs.isEmpty()) {
                        cardsOnTop[i].setIcon(TABLE.clubs.peek().getCardIcon());
                        cardsOnTop[i].setText("");
                    }
                    else
                    {
                        cardsOnTop[i].setIcon(null);
                        cardsOnTop[i].setText("Clubs");
                    }
                }
            }
        }
    }
    public final void updateTable(){
        System.out.println("updateTable called");
        for (int r = MAX_ROWS - 1; r >= 0; r--) {
            for (int c = 0; c < MAX_COLS; c++) {
                CardGridFiller(r, c);
            }
        }
    }

    public void ResetButtons(){
        button1.reset();
        button2.reset();
        System.out.println("!!!!!BUTTONS RESET!!!!!");
    }

    //Constructor button adders
    public void AddTopPanel(){

        for (int i = 0; i < 7; i++){

            String buttonName = "";
            switch(i) {
                case 0:
                    buttonName = "";
                    break;
                case 1:
                    buttonName = "Empty";
                    break;
                case 3:
                    buttonName = "Hearts";
                    break;
                case 4:
                    buttonName = "Diamonds";
                    break;
                case 5:
                    buttonName = "Spades";
                    break;
                case 6:
                    buttonName = "Clubs";
                    break;
            }
            if (i != 2)
            PileButtonAdder(buttonName, i);
        }
    }
    public void PileButtonAdder(String type, int pile) {

        JButton button = new JButton();
        button.setBackground(Color.white);
        button.setOpaque(false);
        button.setBorderPainted(false);

        button.setText(type);
        button.setSize(new Dimension(75, 100));
        button.addActionListener(AL);
        cardsOnTop[pile] = button;

        if (pile == 0) {
            button.setIcon(TABLE.deck.peek().getCardIcon());
            DeckButton = button;
        }
        if (pile == 1)
            DrawPileButton = button;

        else {
            switch(pile){
                case 3 ->{ Hearts = button; }
                case 4 ->{ Diamonds = button; }
                case 5 ->{ Spades = button; }
                case 6 ->{ Clubs = button; }
            }
        }
        TopPanel.add(button);
    }
    public void AddCardGrid() {

        for (int r = 0; r < MAX_ROWS; r++) {
            for (int c = 0; c < MAX_COLS; c++){
                JButton button;
                if (TABLE.hasCardTable[r][c]) {
                    button = new JButton(TABLE.cardsOnTable[r][c].getCardIcon());
                } else {
                    button = new JButton();
                }
                cardsOnTable[r][c] = button;
                CardGridFiller(r,c);
                button.addActionListener(AL);
                CardGridPanel.add(cardsOnTable[r][c]);
            }
        }
    }
    public void CardGridFiller(int r, int c) {

        cardsOnTable[r][c].setBackground(null);
        cardsOnTable[r][c].setOpaque(false);
        cardsOnTable[r][c].setBorderPainted(false);


        if (TABLE.hasCardTable[r][c]) {
            cardsOnTable[r][c].setIcon(TABLE.cardsOnTable[r][c].getCardIcon());
            cardsOnTable[r][c].setVisible(true);
        }
        else
        {
            if (r != 0)
                cardsOnTable[r][c].setVisible(false);
            else
                cardsOnTable[r][c].setIcon(null);
        }
    }


    public void ButtonPrinter(){
         if (button1.isInitialized()){
             System.out.println("Button 1");
             button1.cout();
             if (TABLE.hasCardTable[button1.row][button1.row]) {
                 System.out.print("CARD SELECTED: ");
                 TABLE.cardsOnTable[button1.row][button1.col].Display();
                 System.out.println();
             }
         }
         if (button2.isInitialized()){

             System.out.println("***THIS SHOULD NEVER FIRE OFF***");
             System.out.println("Button 2");
             button2.cout();
             if (TABLE.hasCardTable[button2.row][button2.row]) {
                 System.out.print("CARD SELECTED: ");
                 TABLE.cardsOnTable[button2.row][button2.col].Display();
                 System.out.println();
             }
         }
     }
    public void PrintExitStatement(String statement){
        System.out.println(TABLE.longbar());
        System.out.println(statement);
        System.out.println(TABLE.longbar());
    }

    public boolean foundationsClicked(ActionEvent e){
        return e.getSource() == cardsOnTop[3] || e.getSource() == cardsOnTop[4] || e.getSource() == cardsOnTop[5] || e.getSource() == cardsOnTop[6];
    }

    public void SolitaireAlgorithm(int rec){

        firstRound(1);
        midGame(1);
        updateGUI();

        if (rec > 1)
            SolitaireAlgorithm(rec - 1);
        else return;
    }
    public void firstRound(int rec){
        DrawtoFoundationAutoSort(5);
        TableToFoundationAutoSort(5);
        TableToTableAutoSort(5);
        if (rec > 0)
            firstRound(rec - 1);
    }
    public void midGame(int rec){
        FoundationToTableAutoSort(5);
        TableToTableAutoSort(5);
        DrawToTableAutoSort(5);
        TableToFoundationAutoSort(13);
        if (rec > 0)
            midGame(rec - 1);
    }
    public void DrawtoFoundationAutoSort(int rec){
        for (int num = 1; num < 14; num++) {
            System.out.println("autoSort for: " + num + ", Deck size: " + TABLE.getDeckSize());
            for (int i = 0; i < TABLE.getDeckSize(); i++) {
                TABLE.Draw();
                if (TABLE.hasCardTop[0][1])
                    if (TABLE.up[0].getValue() == num) {
                        System.out.println(num);
                        TABLE.DrawToFoundation(TABLE.up[0].getSuit());
                    }
            }
            TABLE.Draw();
        }
        if (rec > 1)
            DrawtoFoundationAutoSort(rec - 1);
    }
    public void TableToFoundationAutoSort(int rec){
        for (int i = 0; i < 7; i++) {
            int fromRow = TABLE.findFirstCard(i);
            if (fromRow >= 0)
                TABLE.TableIntoFoundation(TABLE.findFirstCard(i), i, TABLE.cardsOnTable[TABLE.findFirstCard(i)][i].getSuit());
        }
        if (rec > 1)
            TableToFoundationAutoSort(rec - 1);
    }
    public void TableToTableAutoSort(int rec){
        for (int fromRow = 0; fromRow < 20; fromRow++)
            for (int fromCol = 0; fromCol < 7; fromCol++)
                for (int toCol = 0; toCol < 7; toCol++)
                    if (TABLE.hasCardTable[fromRow][fromCol])
                        TABLE.moveCard(fromRow, fromCol, toCol);
        if (rec > 0)
            TableToFoundationAutoSort(rec - 1);
    }
    public void DrawToTableAutoSort(int rec){
        for (int i = 0; i < TABLE.getDeckSize(); i++) {
            TABLE.Draw();
            for (int toCol = 0; toCol < 7; toCol++)
                TABLE.DrawToTable(toCol);
        }
        TABLE.Draw();
        if (rec > 0)
            DrawToTableAutoSort(rec - 1);
    }
    public void FoundationToTableAutoSort(int rec){
        for (int pile = 1; pile < 5; pile++){
            for(int toCol = 0; toCol < 7; toCol++)
                TABLE.FoundationToTable(pile, toCol);
        }
        if (rec > 0)
            FoundationToTableAutoSort(rec - 1);
    }
}

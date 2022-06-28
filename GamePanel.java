import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

public class GamePanel extends JPanel{
    static final int MAX_ROWS = Table.MAX_ROWS;
    static final int MAX_COLS = Table.MAX_COLUMNS;
    static final int TABLE_GREEN = 0x158209;

    static final int BUTTON_WIDTH = 124;
    static final int BUTTON_HEIGHT = 136;
    static final int BUTTON_OVERLAP = (int)(BUTTON_HEIGHT * .3);

    static final int GRID_WIDTH = BUTTON_WIDTH * 7;
    static final int GRID_HEIGHT = BUTTON_OVERLAP * 20 + BUTTON_HEIGHT;

    private final ActionListener AL = new AL();

    Table TABLE;

    JButton [] cardsOnTop = new JButton[7];
    JButton [][] cardsOnTable = new JButton[MAX_ROWS][MAX_COLS];

    JButton DeckButton;
    JButton DrawPileButton;
    JButton Hearts;
    JButton Diamonds;
    JButton Spades;
    JButton Clubs;

    JButton Undo;
    JButton NewGame;

    SButton button1 = new SButton();
    SButton button2 = new SButton();

    JPanel TopPanel;
    JPanel CardGridPanel;
    JPanel BottomPanel;

    JLayeredPane CardGridPane;

    JLabel youWin = new JLabel("You win!!!");

    static Stack<Table>History = new Stack<>();

    GamePanel() {

        setLayout(new BorderLayout());
        TABLE = new Table();

        CardGridPanel = new JPanel(new FlowLayout());
        CardGridPanel.setLayout(null);
        CardGridPanel.setSize(GRID_WIDTH, GRID_HEIGHT);
        CardGridPanel.setBackground(new Color(TABLE_GREEN));

        CardGridPane = new JLayeredPane();
        CardGridPane.setLayout(null);
        CardGridPane.setSize(GRID_WIDTH, GRID_HEIGHT);
        CardGridPane.setBackground(new Color(TABLE_GREEN));

        CardGridPanel.add(CardGridPane);

        TopPanel = new JPanel();
        TopPanel.setLayout(new FlowLayout());
        TopPanel.setBackground(new Color(TABLE_GREEN));

        BottomPanel = new JPanel();

        addTopPanel();
        add(TopPanel, BorderLayout.NORTH);
        addCardGrid();
        add(CardGridPanel, BorderLayout.CENTER);
        addBottomPanel();
        add(BottomPanel, BorderLayout.SOUTH);

        updateHistory();
        updateGUI();


    }

    //Constructor button adders
    public void addTopPanel(){
        for (int i = 0; i < 7; i++){

            String buttonName = switch (i) {
                case 0 -> "";
                case 1 -> "Empty";
                case 3 -> "Hearts";
                case 4 -> "Diamonds";
                case 5 -> "Spades";
                case 6 -> "Clubs";
                default -> "";
            };
            if (i != 2)
                pileButtonAdder(buttonName, i);
        }
    }
    public void addCardGrid(){
        for (int c = MAX_COLS - 1; c >= 0; c--){
            for (int r = MAX_ROWS - 1; r >= 0; r--)
            {
                JButton button;
                if (TABLE.hasCardTable[r][c]) {
                    button = new JButton(TABLE.cardsOnTable[r][c].getCardIcon());
                } else {
                    button = new JButton();
                }
                button.setBounds( c * BUTTON_WIDTH, r * BUTTON_OVERLAP, BUTTON_WIDTH, BUTTON_HEIGHT);
                cardsOnTable[r][c] = button;
                cardGridFiller(r,c);
                button.addActionListener(AL);
                CardGridPane.add(button);
                CardGridPane.setLayer(button, r);
            }
        }
    }
    private void addBottomPanel() {
        BottomPanel.setBackground(new Color(TABLE_GREEN));

        JButton undo = new JButton("Undo");
        undo.addActionListener(AL);
        Undo = undo;
        BottomPanel.add(undo);

        JButton reset = new JButton("New Game");
        reset.addActionListener(AL);
        NewGame = reset;
        BottomPanel.add(reset);
    }
    public void pileButtonAdder(String type, int pile) {

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

    //ActionListener
    public class AL implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            //FIND BUTTON
            int r = 0, c = 0;

            if (gridClicked(e)) {
                for (int i = 0; i < MAX_ROWS; i++) {
                    for (int j = 0; j < MAX_COLS; j++) {
                        if (e.getSource() == cardsOnTable[i][j]) {
                            r = i;
                            c = j;
                        }
                    }
                }
            }

            if (e.getSource() == Undo) {
                Undo();
                return;
            }

            if (e.getSource() == NewGame) {
                Reset();
                return;
            }

            if (e.getSource() == DeckButton) {
                Draw();
                return;
            }

            if (!actionListenerStepOne(e, r, c))
                actionListenerStepTwo(e, r, c);

            if (WinCondition())
                TopPanel.add(youWin);

        }
    }
    public boolean actionListenerStepOne(ActionEvent e, int r, int c){
        if (button1.isNotInitialized()) {
            if (gridClicked(e))
                button1.setCard(r, c);
            else if (e.getSource() == DrawPileButton && TABLE.up[0] != null)
                button1.setDraw();
            else
                button1.setFoundation(pileFinder(e));
            return true;
        }
        else
            return false;
    }
    public void actionListenerStepTwo(ActionEvent e, int r, int c){
        if (button1.isInitialized()){
            //DRAW TO *
            if (button1.isDraw()) {

                if (gridClicked(e))
                    DrawToTable(c);
                else {
                    DrawToFoundation(TABLE.up[0].getSuit());
                }
            }

            //TABLE TO *
            else if (button1.isOnTable()) {

                if (gridClicked(e))
                    TableToTable(button1.getRow(), button1.getCol(), c);
                else
                    TableToFoundation(button1.getRow(), button1.getCol(), TABLE.cardsOnTable[button1.getRow()][button1.getCol()].getSuit());

            }
            //FOUNDATION TO TABLE
            else
                FoundationToTable(button1.getPile(), c);

        }
    }
    public int pileFinder(ActionEvent e){
        if (e.getSource() == Hearts)
            return 3;
        else if (e.getSource() == Diamonds)
            return 4;
        else if (e.getSource() == Spades)
            return 5;
        else if (e.getSource() == Clubs)
            return 6;
        else
            return 0;
    }

    //Utilities
    public void resetAndLog(){
        resetButtons();
        updateHistory();
    }
    public void resetButtons(){
        button1.reset();
        button2.reset();
        //System.out.println("!!!!!BUTTONS RESET!!!!!");
    }
    public void cardGridFiller(int r, int c) {

        cardsOnTable[r][c].setBackground(Color.white);
        cardsOnTable[r][c].setOpaque(false);
        cardsOnTable[r][c].setBorderPainted(false);


        if (TABLE.hasCardTable[r][c]) {
            cardsOnTable[r][c].setIcon(TABLE.cardsOnTable[r][c].getCardIcon());
            cardsOnTable[r][c].setVisible(true);
        }
        else
        {
            if (r != 0) {
                cardsOnTable[r][c].setVisible(false);
            }
            else
                cardsOnTable[r][c].setIcon(null);
        }
    }

    //Click locators
    public boolean foundationsClicked(ActionEvent e){
        return e.getSource() == Hearts || e.getSource() == Diamonds || e.getSource() == Spades || e.getSource() == Clubs;
    }
    public boolean gridClicked(ActionEvent e){
        return !foundationsClicked(e) && e.getSource() != DrawPileButton && e.getSource() != DeckButton;
    }

    //Table Commands for GUI
    public void Draw(){
        TABLE.Draw();
        updateHistory();
        updateDeckAndDraw();
        resetButtons();
    }
    public void DrawToTable(int toCol){
        TABLE.DrawToTable(toCol);

        updateTable();
        updateDeckAndDraw();

        resetAndLog();
    }
    public void DrawToFoundation(String suit){
        TABLE.DrawToFoundation(suit);

        updateDeckAndDraw();
        updateFoundations();

        resetAndLog();
    }
    public void TableToTable(int fromRow, int fromCol, int toCol){
        TABLE.TableToTable(fromRow, fromCol, toCol);

        updateTable();

        resetAndLog();
    }
    public void TableToFoundation(int fromRow, int fromCol, String suit){
        TABLE.TableToFoundation(fromRow, fromCol, suit);

        updateTable();
        updateFoundations();

        resetAndLog();
    }
    public void FoundationToTable(int pile, int toCol){
        TABLE.FoundationToTable(pile, toCol);

        updateFoundations();
        updateTable();

        resetAndLog();
    }
    public boolean WinCondition(){
        return TABLE.WinCondition();
    }

    //Menu Commands
    public void Undo(){
        if (History.size() > 1)
            History.pop();
        this.TABLE = new Table(History.peek());
        updateGUI();
        resetButtons();
    }
    public void Reset(){
        NewGame.setText("Loading...");
        History.clear();
        TABLE = new Table();
        updateGUI();
        NewGame.setText("New Game");
    }

    //Updates
    public final void updateGUI() {
        //System.out.println("updateGrid called");

        updateDeckAndDraw();
        updateFoundations();
        updateTable();
        resetButtons();


    }
    public final void updateDeckAndDraw(){
        //System.out.println("updateDeckAndDraw called");
        //DECK
        if (TABLE.deck.size() > 0){
            DeckButton.setText("");
            DeckButton.setIcon(TABLE.deck.peek().getCardIcon());
            DeckButton.setBorderPainted(false);
        }
        else {
            DeckButton.setText("Deck empty");
            DeckButton.setIcon(null);
            DeckButton.setBorderPainted(true);
        }

        //DRAW
        if (TABLE.up[0] != null) {
            DrawPileButton.setText("");
            DrawPileButton.setIcon(TABLE.up[0].getCardIcon());
        }
        else {
            DrawPileButton.setText("Empty");
            DrawPileButton.setIcon(null);
        }
    }
    public final void updateFoundations(){
        //System.out.println("updateFoundations called");
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
        //System.out.println("updateTable called");
        for (int r = MAX_ROWS - 1; r >= 0; r--) {
            for (int c = 0; c < MAX_COLS; c++) {
                cardGridFiller(r, c);
            }
        }
    }
    public final void updateHistory(){
        Table temp = new Table(this.TABLE);
        History.push(temp);
    }

    //AI Stuff
    public void solitaireAlgorithm(int rec){

        firstRound(1);
        midGame(3);
        updateGUI();

        if (rec > 1)
            solitaireAlgorithm(rec - 1);
    }
    public void firstRound(int rec){
        DrawToFoundationAutoSort(5);
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

    //AI Subclasses
    public void DrawToFoundationAutoSort(int rec){
        //System.out.println("Ripping through deck");
        for (int num = 1; num < 14; num++) {
            //System.out.println("autoSort for: " + num + ", Deck size: " + TABLE.getDeckSize());
            for (int i = 0; i < TABLE.getDeckSize(); i++) {
                TABLE.Draw();
                if (TABLE.hasCardTop[0][1])
                    if (TABLE.up[0].getValue() == num) {
                        //System.out.println(num);
                        if (TABLE.DrawToFoundation(TABLE.up[0].getSuit()))
                            updateHistory();
                    }
            }
            TABLE.Draw();
        }
        if (rec > 1)
            DrawToFoundationAutoSort(rec - 1);
    }
    public void TableToFoundationAutoSort(int rec){
        for (int i = 0; i < 7; i++) {
            int fromRow = TABLE.findFirstCard(i);
            if (fromRow >= 0)
                if (TABLE.TableToFoundation(TABLE.findFirstCard(i), i, TABLE.cardsOnTable[TABLE.findFirstCard(i)][i].getSuit()))
                    updateHistory();
        }

        if (rec > 1)
            TableToFoundationAutoSort(rec - 1);
    }
    public void TableToTableAutoSort(int rec){
        for (int fromRow = 0; fromRow < 20; fromRow++)
            for (int fromCol = 0; fromCol < 7; fromCol++)
                for (int toCol = 0; toCol < 7; toCol++)
                    if (TABLE.hasCardTable[fromRow][fromCol])
                        if (TABLE.TableToTable(fromRow, fromCol, toCol))
                            updateHistory();

        if (rec > 0)
            TableToFoundationAutoSort(rec - 1);
    }
    public void DrawToTableAutoSort(int rec){
        for (int i = 0; i < TABLE.getDeckSize(); i++) {
            TABLE.Draw();
            for (int toCol = 0; toCol < 7; toCol++)
                if (TABLE.DrawToTable(toCol))
                    updateHistory();
        }
        TABLE.Draw();
        if (rec > 0)
            DrawToTableAutoSort(rec - 1);
    }
    public void FoundationToTableAutoSort(int rec){
        for (int pile = 1; pile < 5; pile++){
            for(int toCol = 0; toCol < 7; toCol++)
                if(TABLE.FoundationToTable(pile, toCol))
                    updateHistory();
        }

        if (rec > 0)
            FoundationToTableAutoSort(rec - 1);
    }
}

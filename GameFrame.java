import javax.swing.*;
import java.awt.*;
import java.io.File;

public class GameFrame extends JFrame {

    GamePanel panel;

    GameFrame(){

        panel = new GamePanel();
        add(panel, BorderLayout.CENTER);


        setBackground(new Color (GamePanel.TABLE_GREEN));
        setTitle("Solitare");

        setIconImage(new ImageIcon("GameIcon.png").getImage());
        setResizable(true);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        setSize(884,1025);

        System.out.println("Top Panel width: " + panel.TopPanel.getWidth());
        System.out.println("Top Panel height: " + panel.TopPanel.getHeight());
        System.out.println();
        System.out.println("Card grid width: " + panel.CardGridPanel.getWidth());
        System.out.println("Card grid height: " + panel.CardGridPanel.getHeight());
        System.out.println();
        System.out.println("Bottom panel width: " + panel.BottomPanel.getWidth());
        System.out.println("Bottom panel height: " + panel.BottomPanel.getHeight());
        System.out.println();
    }
}

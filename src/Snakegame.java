import javax.swing.*;

public class Snakegame extends JFrame {
    private Gameboard gameboard;
    public Snakegame(){
       
        gameboard=new Gameboard();
        add(gameboard);
        setResizable(false);
        pack();
        setBounds(100,100,800,500);
        setVisible(true);
    }


    public static void main(String[] args) {
        JFrame snakegame=new Snakegame();
    }
}
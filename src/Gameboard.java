import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Gameboard extends JPanel implements ActionListener {
    int board_height=400;
    int board_width=400;
    int[] x= new int[board_height*board_width];
    int[] y=new int[board_height*board_width];
    int dots=3;
    int apple_x=0;
    int apple_y=0;
    int dotsize=10;
    Image apple;
    Image body;
    Image head;
    boolean leftdir=false;
    boolean rightdir=true;
    boolean updir=false;
    boolean downdir=false;
    Timer timer;
    int Delay=100;
    boolean ingame=true;
    public Gameboard()
    {
        addKeyListener(new Tadapter());
        setFocusable(true);
        setPreferredSize(new Dimension(board_width,board_height));
        setBackground(Color.black);
        loadimages();
        initGame();


    }
    public void initGame(){
        dots=3;
        for(int i=0;i<dots;i++)
        {
            x[i]=150+(i*dotsize);
            y[i]=150;
            apple_x=x[i]+dotsize;
            apple_y=y[i];
        }
      timer=new Timer(Delay,this);
        timer.start();
    }
 private void loadimages() {
     ImageIcon image_apple = new ImageIcon("src/resources/apple.png");
     apple = image_apple.getImage();
     ImageIcon image_head = new ImageIcon("src/resources/head.png");
     head = image_head.getImage();
     ImageIcon image_body = new ImageIcon("src/resources/dot.png");
     body = image_body.getImage();
 }
 private void locatingApple()
 {
     int r=(int)(Math.random()*(39));
     apple_x=r*dotsize;
     r=(int)(Math.random()*(39));
     apple_y=r*dotsize;
 }
 private void checkapple()
 {
     if(x[0]==apple_x && y[0]==apple_y)
     {
         dots++;
         locatingApple();
     }
 }
 @Override
 public void paintComponent(Graphics graphics)
 {
     super.paintComponent(graphics);
     if(ingame)
     {
         graphics.drawImage(apple,apple_x,apple_y,this);
         for(int i=0;i<dots;i++)
         {
             if(i==0)
             {
                 graphics.drawImage(head,x[0],y[0],this);
             }
             else{
                 graphics.drawImage(body,x[i],y[i],this);
             }
         }
         Toolkit.getDefaultToolkit().sync();
     }
    else{
        gameover(graphics);
     }
 }
 private void move(){
        for(int i=dots-1;i>0;i--)
        {
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        if(leftdir)
        {
            x[0]-=dotsize;
        }
        if(rightdir)
        {
            x[0]+=dotsize;

        }
        if(updir)
        {
            y[0]-=dotsize;
        }
        if(downdir)
        {
            y[0]+=dotsize;
        }
 }
public void checkcollision(){
        if(x[0]<0)
        {
            ingame=false;
        }
        if(x[0]>=board_width)
        {
           ingame=false;
        }
        if(y[0]<0)
        {
            ingame=false;
        }
        if(y[0]>=board_height)
        {
           ingame=false;
        }
        for(int i=dots-1;i>=3;i--)
        {
            if(x[0]==x[i] && y[0]==y[i])
            {
             ingame=false;
             break;
            }
        }
    }
    private void gameover(Graphics graphics){
        String msg="GAME OVER";
        Font small=new Font("Helvetica",Font.BOLD,14);
        FontMetrics metrics=getFontMetrics(small);
        graphics.setColor(Color.WHITE);
        graphics.setFont(small);
        graphics.drawString(msg,(board_width-metrics.stringWidth(msg))/2,board_height/2);
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(ingame)
        {
            checkapple();
            checkcollision();
            move();
        }
      repaint();
    }
    public class Tadapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent keyEvent)
        {
            int key=keyEvent.getKeyCode();
            if(key==KeyEvent.VK_LEFT && (rightdir!=true))
            {
                leftdir=true;
                updir=false;
                downdir=false;
            }
            if(key==KeyEvent.VK_RIGHT && (leftdir!=true))
            {
                rightdir=true;
                updir=false;
                downdir=false;
            }
            if(key==KeyEvent.VK_UP && (downdir!=true))
            {
                updir=true;
                leftdir=false;
                rightdir=false;
            }
            if(key==KeyEvent.VK_DOWN && (updir!=true))
            {
                downdir=true;
                leftdir=false;
                rightdir=false;
            }

        }
    }
}

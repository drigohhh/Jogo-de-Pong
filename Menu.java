import java.awt.*;

public class Menu extends Rectangle {
    static int GAME_WIDTH;
    static int GAME_HEIGHT;
    Rectangle playButton;
    Rectangle helpButton;
    Rectangle quitButton;

    Menu(int GAME_WIDTH, int GAME_HEIGHT){
        Menu.GAME_WIDTH = GAME_WIDTH;
        Menu.GAME_HEIGHT = GAME_HEIGHT;
        playButton = new Rectangle(100, 150, 300, 50);
        helpButton = new Rectangle(100, 250, 300, 50);
        quitButton = new Rectangle(100, 350, 300, 50);
    }
    
    public void draw(Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        g.setFont(new Font("Consolas", Font.BOLD, 60));
        g.setColor(Color.white);
        g.drawString("JOGO DE PONG", 100, 100);

        g.setFont(new Font("Consolas", Font.PLAIN, 30));
        g.drawString("Jogar", playButton.x + 19, playButton.y + 35);
        g2d.draw(playButton);
        g.drawString("Ajuda", helpButton.x + 19, helpButton.y + 35);
        g2d.draw(helpButton);
        g.drawString("Sair do jogo", quitButton.x + 19, quitButton.y + 35);
        g2d.draw(quitButton);
    }

   
}

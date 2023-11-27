import java.awt.event.*;

public class MouseInput implements MouseListener{
    static int GAME_WIDTH;
    static int GAME_HEIGHT;
    Menu menu;
    int state;

    MouseInput(int GAME_WIDTH, int GAME_HEIGHT, Menu menu, int state){
        MouseInput.GAME_WIDTH = GAME_WIDTH;
        MouseInput.GAME_HEIGHT = GAME_HEIGHT;
        this.menu = menu;
        this.state = state;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        //playButton
        if(mx >= menu.playButton.x && mx <= menu.playButton.width + menu.playButton.x){
            if(my >= menu.playButton.y && my <= menu.playButton.height + menu.playButton.y){
                state = 1;
            }
        }
       
        //helpButton

        //quitButton
        if(mx >= menu.quitButton.x && mx <= menu.quitButton.width + menu.quitButton.x){
            if(my >= menu.quitButton.y && my <= menu.quitButton.height + menu.quitButton.y){
                System.exit(1);
            }
        }
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    public int getState(){
        return state;
    }
    
}

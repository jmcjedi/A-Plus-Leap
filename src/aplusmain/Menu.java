package aplusmain;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class Menu extends BasicGameState
{ 
  int counter = 0;
  public static int chalkX = 200;
  public static int chalkY = 200;
  public static double irX = 0;
  public static double irY = 0;
  public static double irZ = 0;
  public static String buttonpressed = "";
  public static Image chalk, chalkShadow, button1, button2, button3;
  
  public Menu(int state)
  {
    
  }
  
  // creation of the screen and housekeeping items
  public void init(GameContainer container, StateBasedGame stategame) throws SlickException
  {
    chalk = new Image("resources/images/chalk_white.png");
    chalkShadow = new Image("resources/images/chalk_shadow.png");
    button1 = new Image("resources/images/button1.png");
    button2 = new Image("resources/images/button2.png");
    button3 = new Image("resources/images/button3.png");
  }
  
  // creation of graphics elements
  public void render(GameContainer container, StateBasedGame stategame, Graphics g) throws SlickException
  {
    Image background = new Image("resources/images/background_small.png");
    Image logo = new Image("resources/images/logo.png");
    
    g.drawImage(background, 0, 0);
    //g.drawImage(logo, 403, 298);
    g.setColor(Color.white);
    //g.drawString(mouse, 50, 50);
    //g.drawString("This is the 'Menu' state", 50, 100);
    
    g.drawImage(logo, 298, 25);
    g.drawImage(button1, 325, 295);
    g.drawImage(button2, 325, 445);
    g.drawImage(button3, 325, 595);
    g.drawImage(chalkShadow, chalkX, chalkY);
    g.drawImage(chalk, chalkX, chalkY);
    
  }
  
  // method for updating the screen at regular intervals when things change
  public void update(GameContainer container, StateBasedGame stategame, int delta) throws SlickException
  {
    double mappedX = (Connect.listener.x + 200.00) * 2.56;
    double mappedY = 768 - ((Connect.listener.y * -1) + 100.00) * -2.56;
  	  
    irX = mappedX;
    if (irX < 0) irX = 0;
      
    irY = mappedY;
    if (irY < 0) irY = 0;
      
    irZ = Connect.listener.z;
    
    chalkX = (int)irX;
    chalkY = (int)irY;
    
    buttonpressed = Connect.buttonpressed;
    
    //if (buttonpressed=="A")
    if (irZ < -30)
    {
      /*try
      {
        Thread.sleep(50);
      } catch (InterruptedException e1)
      {
        // TODO Auto-generated catch block
        e1.printStackTrace();
      }*/ 
      if(chalkY > 295 && chalkY < 445 && chalkX > 325 && chalkX < 700)
      {
        stategame.enterState(3);
      }
      else if(chalkY > 445 && chalkY < 595 && chalkX > 325 && chalkX < 700)
      {
        stategame.enterState(3);
      }
      else if(chalkY > 595 && chalkY < 700 && chalkX > 325 && chalkX < 700)
      {
        stategame.enterState(3);
      }
    }

    //samples for using keyboard input from user
    //Input input = container.getInput();
    //if(input.isKeyDown(Input.KEY_UP)) { chalkY-=1; }
    //if(input.isKeyDown(Input.KEY_DOWN)) { chalkY+=1; }
    //if(input.isKeyDown(Input.KEY_RIGHT)) { chalkX+=1; }
    //if(input.isKeyDown(Input.KEY_LEFT)) { chalkX-=1; }
  }
  
  public int getID()
  {
    return 2;
  }
}
package aplusmain;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

import java.lang.Math;
import com.leapmotion.leap.*;
import com.leapmotion.leap.Gesture.State;

public class Connect extends BasicGameState 
{
  
  //new
  //public static WiiRemoteListener listener = null;
  //public static WiiRemote remote;
  static leapListener listener = new leapListener();
  static Controller controller = new Controller();
    
  public static boolean accelerometerSource = true; //true = wii remote, false = nunchuk
  public static boolean lastSource = true;
  
  public static boolean mouseTestingOn;
  public static int status = 0;
  public static int accelerometerStatus = 0;
  public static int analogStickStatus = 0;
  public static JFrame mouseTestFrame;
  public static JPanel mouseTestPanel;
  
  public static JFrame graphFrame;
  public static JPanel graph;
  public static int[][] pixels;
  public static int t = 0;
  public static int x = 0;
  public static int y = 0;
  public static int z = 0;
  
  public static int lastX = 0;
  public static int lastY = 0;
  public static int lastZ = 0;
  
  //public static final MoteFinderListener listener = null;
  public static int connected = 0;
  int counter, counter2 = 0;
  int checkCounter = 0;
  static boolean go = false;
  public static String buttonpressed = "";
  public static int accelX = 0;
  public static int accelY = 0;
  public static int accelZ = 0;
  public static double irX = 0;
  public static double irY = 0;
  public static double irZ = 0;
  int pixelArrayX[]= new int[424];
  int pixelArrayY[] = new int[424];
  int pixelArrayZ[] = new int[424];
  Image chalk;
  public static int chalkX, chalkY;
  public static int chalkTrail[][] = new int[1024][1024];
  public static int inputLetter[][] = new int[1084][1084];
  public static String[] wheel_spokes = {"resources/images/spin1.png","resources/images/spin2.png",
                                         "resources/images/spin3.png","resources/images/spin4.png",
                                         "resources/images/spin5.png","resources/images/spin6.png",
                                         "resources/images/spin7.png","resources/images/spin8.png",
                                         "resources/images/spin9.png","resources/images/spin10.png",
                                         "resources/images/spin11.png","resources/images/spin12.png",
                                         "resources/images/spin13.png"};
  
  public static String[] check_mark = {"resources/images/check_mark1.png","resources/images/check_mark2.png",
                                         "resources/images/check_mark3.png","resources/images/check_mark4.png",
                                         "resources/images/check_mark5.png","resources/images/check_mark6.png",
                                         "resources/images/check_mark7.png","resources/images/check_mark8.png",
                                         "resources/images/check_mark9.png","resources/images/check_mark10.png",
                                         "resources/images/check_mark11.png","resources/images/check_mark12.png",
                                         "resources/images/check_mark13.png","resources/images/check_mark14.png",
                                         "resources/images/check_mark15.png","resources/images/check_mark16.png",
                                         "resources/images/check_mark17.png","resources/images/check_mark18.png",
                                         "resources/images/check_mark19.png"};
  public static Image rotating_wheel; 
  public static Image checkMark;
  public static int wheel_spoke = 0;
  public static boolean connectedPlay = false;
  public static Sound getConnected;
  
  public Connect(int state) 
  {
    
  }
  
  public void init(GameContainer container, StateBasedGame stategame) throws SlickException
  {
    chalk = new Image("resources/images/chalk_white.png"); 
    getConnected = new Sound("resources/sounds/getConnected.wav");
  }
  
  public void render(GameContainer container, StateBasedGame stategame, Graphics g) throws SlickException
  {
    rotating_wheel = new Image(wheel_spokes[wheel_spoke]); 
    Image background = new Image("resources/images/background_small.png");
    Image connect1 = new Image("resources/images/connect1.png");
    Image connect2 = new Image("resources/images/connect2.png");
    Image connectarrow = new Image("resources/images/connectarrow.png");
    Image checkMark = new Image(check_mark[checkCounter]);
    
    g.drawImage(background, 0, 0); 
    //g.drawImage(connect1, 20, 134);
    //g.drawImage(connect2, 523, 134);
    //g.drawImage(connectarrow, 412, 284);
    //g.drawImage(rotating_wheel, 350, 200);
    
    if(connected==0)
    {
      if (connectedPlay==false)
      {
        getConnected.play();
        connectedPlay = true;
      }
      g.drawImage(connect1, 20, 134);
      g.drawImage(connect2, 523, 134);
      g.drawImage(connectarrow, 412, 284);
      g.drawImage(rotating_wheel, 380, 150);
    }
    if(connected==1)
    {
      counter2++;
      if(counter2 % 2 == 0)
      {
        if(checkCounter==18) checkCounter = 18;
        else checkCounter++;
        
        if(counter2 > 90)
        {
          go = true;
        }
      }
      g.drawImage(checkMark, 300, 200);
    }
    //g.setColor(Color.white);
    //g.drawString("This is the 'Connect' screen", 50, 50);
    //g.drawString(buttonpressed, 50, 100);
    //g.drawString("Accelerometer X: " + accelX + " Y: " + accelY + " Z: " + accelZ, 50, 120);
    //g.drawString("IR Camera X: " + irX + " Y: " + irY, 50, 140);
    //g.drawImage(chalk, chalkX, chalkY);
    
    int x, y;
    for (x = 0; x < 1024; x++)
    {
      for (y = 0; y < 1024; y++)
      {
        if (chalkTrail[x][y] == 1)
        {
          g.fillRect((float)x, (float)y, 8, 8);
        }
        
      }
    } 
 
  }
  
  // method for updating the screen at regular intervals for 
  public void update(GameContainer container, StateBasedGame stategame, int delta) throws SlickException
  {
    counter++;
    if(counter % 2 == 0)
    {
      if(wheel_spoke==12) wheel_spoke = 0;
      else wheel_spoke++;
    }
    
    if(connected==1)
    {
      //System.out.println("Listener.x = " + listener.x);
      //System.out.println("Listener.y = " + listener.y);
      //System.out.println("Listener.z = " + listener.z);
    	
      irX = listener.x;
      irY = listener.y;
      irZ = listener.z;
      //accelX = WiimoteAdapter.x;
      //accelY = WiimoteAdapter.y;
      //accelZ = WiimoteAdapter.z;
    }
    //else System.out.println("NotConnected...");
    
    //Input input = container.getInput();
    //int x = Mouse.getX();
    //int y = Mouse.getY();
    //mouse = "Mouse X: " + x + ", Mouse Y: " + y;

 
      if(connected==0 && counter > 90)
      {  
        try
        {
          connectLeap();
        } catch (IllegalStateException e)
        {
          // TODO Auto-generated catch block
          e.printStackTrace();
        } catch (InterruptedException e)
        {
          // TODO Auto-generated catch block
          e.printStackTrace();
        } catch (IOException e)
        {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
      
      //  *** NEED TO DESIGN SOME TRANSITIONAL GRAHPICS BETWEEN STATES ***
      // connecting to the wiimote
      /*if(connected==1)
      {
        stategame.enterState(2);
      }*/
      
      if(go==true)
      {
        stategame.enterState(2);
      }

  }
  
  public static void connectLeap() throws IllegalStateException, InterruptedException, IOException
  {
    //basic console logging options...
    //WiiRemoteJ.setConsoleLoggingAll();
    //WiiRemoteJ.setConsoleLoggingOff();
    //System.setProperty("bluecove.jsr82.psm_minimum_off", "true");
    
    //remote = WiiRemoteJ.findRemote();
    //remote.addWiiRemoteListener(new WiimoteAdapter(remote));
    //remote.setAccelerometerEnabled(true);
    //remote.setSpeakerEnabled(true);
    //remote.setIRSensorEnabled(true, WRIREvent.BASIC);
    //remote.setLEDIlluminated(0, true);
    
	  
	// Have the sample listener receive events from the controller
    controller.addListener(listener);

      // Keep this process running until Enter is pressed
    /*System.out.println("Press Enter to quit...");
    try 
    {
      System.in.read();
    } 
    catch (IOException e) 
    {
      e.printStackTrace();
    }*/
    
   // Remove the sample listener when done
    //controller.removeListener(listener);
    
    /*remote.getButtonMaps().add(new ButtonMap(WRButtonEvent.HOME, ButtonMap.NUNCHUK, WRNunchukExtensionEvent.C, new int[]{java.awt.event.KeyEvent.VK_CONTROL}, 
        java.awt.event.InputEvent.BUTTON1_MASK, 0, -1));
                
    //Prebuffer a preformatted audio file
    System.out.println("Buffering audio file...");
    long time = System.currentTimeMillis();
    AudioInputStream audio = null;
    try
    {
      audio = AudioSystem.getAudioInputStream(new java.io.File("resources/sounds/audio8.wav"));
    } catch (UnsupportedAudioFileException e)
    {
      e.printStackTrace();
    }
   
    time = System.currentTimeMillis()-time;
    time /= 1000;
    System.out.println("Prebuf done: " + time + " seconds.");  */
    
    connected = 1;
    
  }
  
  public int getID()
  {
    return 1;
  }
}
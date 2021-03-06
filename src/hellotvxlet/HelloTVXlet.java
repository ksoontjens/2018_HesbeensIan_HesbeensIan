package hellotvxlet;

import java.awt.event.ActionEvent;
import javax.tv.xlet.*;
import org.bluray.ui.event.HRcEvent;
import org.davic.resources.ResourceClient;
import org.davic.resources.ResourceProxy;
import org.dvb.event.EventManager;
import org.dvb.event.UserEvent;
import org.dvb.event.UserEventListener;
import org.dvb.event.UserEventRepository;
import org.havi.ui.HBackgroundConfigTemplate;
import org.havi.ui.HBackgroundDevice;
import org.havi.ui.HBackgroundImage;
import org.havi.ui.HScene;
import org.havi.ui.HSceneFactory;
import org.havi.ui.HScreen;
import org.havi.ui.HStaticText;
import org.havi.ui.HStillImageBackgroundConfiguration;


//import + implement all abstract... (om ResourceClient te gebruiken)

import org.havi.ui.HVisible;
import org.havi.ui.event.HBackgroundImageEvent;
import org.havi.ui.event.HBackgroundImageListener;

public class HelloTVXlet implements Xlet, ResourceClient, HBackgroundImageListener,
UserEventListener{
   
 HScreen screen;  //Dit maakt enkel de reference, object aanmaken via new
                //Hier gezet om globale/klasse variable te hebben
 HBackgroundDevice bgDevice;
 HBackgroundConfigTemplate bgTemplate;
 HStillImageBackgroundConfiguration bgConfiguration;
 
 //C:\Program Files\TechnoTrend\TT-MHP-Browser\fileio\DSMCC\0.0.3
 HBackgroundImage image[]=new HBackgroundImage[7];
 int geladen =0;
 int huidig = 0;
    private HScene scene;
    private HStaticText hst;
    //private HStaticText totaalPrijs;
    private HStaticText infoFilm;
    private boolean pushed;
    private boolean infoIsVisible = true;
    private boolean factuurIsVisible = false;
    
  
    public HelloTVXlet() {
        
    }

    public void initXlet(XletContext context) throws XletStateChangeException{
      //Alle initialisaties
        screen = HScreen.getDefaultHScreen();
        bgDevice = screen.getDefaultHBackgroundDevice();
        if(bgDevice.reserveDevice(this)){

            System.out.println("Background image device has been reserved");

        }
        else{
            System.out.println("Background image device cannot be reserved");
        }      
       bgTemplate = new HBackgroundConfigTemplate();
       bgTemplate.setPreference(HBackgroundConfigTemplate.STILL_IMAGE, 
               HBackgroundConfigTemplate.REQUIRED);
       
       bgConfiguration = (HStillImageBackgroundConfiguration) 
               bgDevice.getBestConfiguration(bgTemplate);
       
       try{
        bgDevice.setBackgroundConfiguration(bgConfiguration);
    }
       catch(Exception ex){
           ex.printStackTrace();
       }
       image[0]=new HBackgroundImage("BatmanPROXIMUS.png");
       image[1]=new HBackgroundImage("StarWarsPROXIMUS.png");
       image[2]=new HBackgroundImage("NewKidsPROXIMUS.png");
       image[3]=new HBackgroundImage("TheAvengersPROXIMUS.png");
       image[4]=new HBackgroundImage("TheHangoverPROXIMUS.png");
       image[5]=new HBackgroundImage("YesManPROXIMUS.png");
       image[6]=new HBackgroundImage("Factuur.png");
       
       for(int i=0;i<7;i++)
        { 
            image[i].load(this); 
        }
       UserEventRepository repo = new UserEventRepository("naam");
       repo.addAllArrowKeys();
       repo.addKey(HRcEvent.VK_ENTER);
       EventManager.getInstance().addUserEventListener(this, repo);
       //public class HelloTVXlet implements Xlet, ResourceClient, 
       //HBackgroundImageListener,UserEventListener{
       //import + implement all
       
       
       scene = HSceneFactory.getInstance().getDefaultHScene();
       //globaal:
       //  private HScene scene;
       //private HStaticText hst;
       
       hst = new HStaticText(" \n",400,100,300,400); //x,y,w,h
       //totaalPrijs = new HStaticText("$" ,320,100,300,400); //x,y,w,h
       infoFilm = new HStaticText(" \n",70,320,300,400); //x,y,w,h
       hst.setVerticalAlignment(HStaticText.VALIGN_TOP);
       hst.setHorizontalAlignment(HStaticText.HALIGN_RIGHT);
       //TO DO: gebruik string tekst = hst.getTextContent(HVisible.NORMAL_STATE);
       //en hst.setTextContent(text, HVisible.NORMAL_STATE); om de tekst aan te passen
       //in UserEventReceived
       scene.add(hst);
       //scene.add(totaalPrijs);
       scene.add(infoFilm);
       scene.validate();
       scene.setVisible(true);
    }
    
    public void imageLoaded(HBackgroundImageEvent e) {
        geladen++;
        System.out.println("Images geladen: " + geladen);
        if (geladen ==5){
            try{
                bgConfiguration.displayImage(image[0]);
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }
    public void imageLoadFailed(HBackgroundImageEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void userEventReceived(UserEvent e) {
        String info = infoFilm.getTextContent(HVisible.NORMAL_STATE);
        String bestelling = hst.getTextContent(HVisible.NORMAL_STATE);
        //String bestellingPrijs = totaalPrijs.getTextContent(HVisible.NORMAL_STATE);
        if(e.getType()==HRcEvent.KEY_PRESSED){
            if(e.getCode()==HRcEvent.VK_ENTER){

                if(huidig ==0){
                    bestelling = bestelling + "Batman - The Dark Knight\n";
                }
                if(huidig ==1){
                    bestelling = bestelling + "Star Wars - The Last Jedi\n";
                }
                if(huidig ==2){
                    bestelling = bestelling + "New Kids - Turbo\n";
                }
                if(huidig ==3){
                    bestelling = bestelling + "The Avengers\n";
                }
                if(huidig ==4){
                    bestelling = bestelling + "The Hangover\n";
                }
                if(huidig ==5){
                    bestelling = bestelling + "Yes Man\n";
                }
                hst.setTextContent(bestelling, HVisible.NORMAL_STATE);
                hst.repaint();
                //totaalPrijs.setTextContent(bestellingPrijs, HVisible.NORMAL_STATE);
                //totaalPrijs.repaint(); 
            }
            if(e.getCode()==HRcEvent.VK_UP){
                
                if (infoIsVisible == true){
                    if(huidig ==0){
                        info = "$4.99";
                        infoIsVisible = false;
                    }
                    if(huidig ==1){
                        info = "$5.99";
                        infoIsVisible = false;
                    }
                    if(huidig ==2){
                        info = "$8.99";
                        infoIsVisible = false;
                    }
                    if(huidig ==3){
                        info = "$5.99";
                        infoIsVisible = false;
                    }
                    if(huidig ==4){
                        info = "$9.99";
                        infoIsVisible = false;
                    }
                    if(huidig ==5){
                        info = "$2.99";
                        infoIsVisible = false;
                    }
                }
                else{
                    info= "";
                    infoIsVisible = true;
                }
                infoFilm.setTextContent(info, HVisible.NORMAL_STATE);
                infoFilm.repaint();
            }
            if(e.getCode()==HRcEvent.VK_RIGHT){
                huidig++;
                info = "";
                infoIsVisible = true;
                if(huidig>5){
                    huidig = 0;
                }
            }
            if(e.getCode()==HRcEvent.VK_LEFT){
                info = "";
                infoIsVisible = true;
                huidig--;
                if(huidig<0){
                    huidig = 5;
                }
            }
            if(e.getCode()==HRcEvent.VK_DOWN){
                bestelling = " \n";
                if(factuurIsVisible == false){
                    huidig = 6;
                    info = "";
                    factuurIsVisible = true;
                }
                else{
                    huidig = 1;
                    info = "";
                    factuurIsVisible = false;
                }
                hst.setTextContent(bestelling, HVisible.NORMAL_STATE);
                hst.repaint();
            }
            infoFilm.setTextContent(info, HVisible.NORMAL_STATE);
            infoFilm.repaint();
            
            try{
                bgConfiguration.displayImage(image[huidig]);
            }
            catch(Exception ex){
                ex.printStackTrace();
        }
    }

}
    
    public void startXlet() {
        //Al de andere code
        
    }

    public void pauseXlet() {
     
    }

    public void destroyXlet(boolean unconditional) {
     
    }

    public void actionPerformed(ActionEvent arg0) {
        
    }

    public boolean requestRelease(ResourceProxy proxy, Object requestData) {
        return false;
    }

    public void release(ResourceProxy proxy) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void notifyRelease(ResourceProxy proxy) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    
}

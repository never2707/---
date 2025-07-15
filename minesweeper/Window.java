import java.awt.Color;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class Window extends JFrame{

    static Images images = new Images();
    int timeCount = 0;
    int bombsCount = 0;
    int spaceCount = 0;
    boolean continous = false;
    Timer timer = new Timer();
    boolean gameIsExist = false;

    public Window(int mode){ 


        //插入layeredPane

        //DEFAULT_LAYER  (背景)
        layeredPane.add(lbackground, JLayeredPane.DEFAULT_LAYER);

        //MODAL_LAYER  (元件)
        layeredPane.add(pBombs, JLayeredPane.MODAL_LAYER);
        layeredPane.add(lBombBg, JLayeredPane.MODAL_LAYER);
        layeredPane.add(lTimeBg, JLayeredPane.MODAL_LAYER);
        layeredPane.add(bFace, JLayeredPane.MODAL_LAYER);

        //POPUP_LAYER  (數字)
        layeredPane.add(lBombHunDig, JLayeredPane.POPUP_LAYER);
        layeredPane.add(lBombTenDig, JLayeredPane.POPUP_LAYER);
        layeredPane.add(lBombOneDig, JLayeredPane.POPUP_LAYER);
        layeredPane.add(lTimeHunDig, JLayeredPane.POPUP_LAYER);
        layeredPane.add(lTimeTenDig, JLayeredPane.POPUP_LAYER);
        layeredPane.add(lTimeOneDig, JLayeredPane.POPUP_LAYER);

        setLayeredPane(layeredPane);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setWindow(mode);

        int[] b = {bombsCount/100, bombsCount/10 %10, bombsCount%10};
        setNum(1, b);
        setVisible(true);
    }

    //引入圖片
    ImageIcon iBackground_easy = Images.getIcon(images.background_easy, 292, 365);
    ImageIcon iBackground_normal = Images.getIcon(images.background_normal, 490, 561);
    ImageIcon iBackground_hard = Images.getIcon(images.background_hard, 880, 561);
    ImageIcon iBackground = Images.getIcon(images.background, 71, 43);
    ImageIcon iFace = Images.getIcon(images.face, 43, 44); 
    ImageIcon iFace_press = Images.getIcon(images.face_press, 43, 44); 
    ImageIcon iFace_lose = Images.getIcon(images.face_lose, 43, 44); 
    ImageIcon iFace_win = Images.getIcon(images.face_win, 43, 44);
    ImageIcon iSpace = Images.getIcon(images.space,28,28);
    ImageIcon iSpace_press = Images.getIcon(images.space_press, 28, 28);
    ImageIcon iBomb = Images.getIcon(images.bomb, 28, 28);
    ImageIcon iBomb_red = Images.getIcon(images.bomb_red, 28, 28);
    ImageIcon iFlag = Images.getIcon(images.flag, 28, 28);
    ImageIcon d0 = Images.getIcon(images.d0, 18, 35);
    ImageIcon d1 = Images.getIcon(images.d1, 18, 35);
    ImageIcon d2 = Images.getIcon(images.d2, 18, 35);
    ImageIcon d3 = Images.getIcon(images.d3, 18, 35);
    ImageIcon d4 = Images.getIcon(images.d4, 18, 35);
    ImageIcon d5 = Images.getIcon(images.d5, 18, 35);
    ImageIcon d6 = Images.getIcon(images.d6, 18, 35);
    ImageIcon d7 = Images.getIcon(images.d7, 18, 35);
    ImageIcon d8 = Images.getIcon(images.d8, 18, 35);
    ImageIcon d9 = Images.getIcon(images.d9, 18, 35);
    ImageIcon n1 = Images.getIcon(images.n1, 28, 28);
    ImageIcon n2 = Images.getIcon(images.n2, 28, 28);
    ImageIcon n3 = Images.getIcon(images.n3, 28, 28);
    ImageIcon n4 = Images.getIcon(images.n4, 28, 28);
    ImageIcon n5 = Images.getIcon(images.n5, 28, 28);
    ImageIcon n6 = Images.getIcon(images.n6, 28, 28);
    ImageIcon n7 = Images.getIcon(images.n7, 28, 28);
    ImageIcon n8 = Images.getIcon(images.n8, 28, 28);

    //設置Components
    JLabel lbackground = new JLabel();
    JLabel lBombHunDig = new JLabel(d0);
    JLabel lBombTenDig = new JLabel(d0);
    JLabel lBombOneDig = new JLabel(d0);
    JLabel lTimeHunDig = new JLabel(d0);
    JLabel lTimeTenDig = new JLabel(d0);
    JLabel lTimeOneDig = new JLabel(d0);
    JLabel lBombBg = new JLabel(iBackground);
    JLabel lTimeBg = new JLabel(iBackground);
    JButton bFace = new JButton(iFace);
    JButton[][] buttons;
    JPanel pBombs = new JPanel();
    JLayeredPane layeredPane = new JLayeredPane();

    public void setWindow(int mode){
        switch (mode) {
            case 0:
                if(!gameIsExist){
                    setSize(292+14, 365+37);
                    bombsCount = 10;
                    lbackground.setIcon(iBackground_easy);
                    buttons = new JButton[9][9];

                    lbackground.setBounds(0,0,292,365);
                    lBombBg.setBounds(25,24,71,43);
                    lTimeBg.setBounds(194, 24, 71, 43);
                    bFace.setBounds(126,24,43,44);
                    lBombHunDig.setBounds(30,29,18,35);
                    lBombTenDig.setBounds(53,29,18,35);
                    lBombOneDig.setBounds(75,29,18,35);
                    lTimeHunDig.setBounds(199,29,18,35);
                    lTimeTenDig.setBounds(222,29,18,35);
                    lTimeOneDig.setBounds(244,29,18,35);
                    pBombs.setBounds(20,95,28*9,28*9);
                    pBombs.setLayout(null);

                    bFace.setBorder(null);
                    lBombBg.setBorder(BorderFactory.createLineBorder(Color.GRAY, 4));
                    lTimeBg.setBorder(BorderFactory.createLineBorder(Color.GRAY, 4));
                    for(int i=0; i<9; i++)
                    for(int j=0; j<9; j++){
                        if(!gameIsExist) {
                            buttons[i][j] = new JButton(iSpace);
                            pBombs.add(buttons[i][j]);
                            buttons[i][j].setBorder(null);
                            buttons[i][j].setBounds(28*i,28*j,28,28);
                        }
                    }
                    gameIsExist = true;
                }

                else{
                    for(int i=0; i<9; i++)
                    for(int j=0; j<9; j++){
                        buttons[i][j].setIcon(iSpace);
                    }
                }
                break;

            case 1:
                if(!gameIsExist){
                    setSize(490+14,561+37);
                    bombsCount = 40;
                    lbackground.setIcon(iBackground_normal);
                    buttons = new JButton[16][16];

                    lbackground.setBounds(0,0,490,561);
                    lBombBg.setBounds(30,24,71,43);
                    lTimeBg.setBounds(387, 24, 71, 43);
                    bFace.setBounds(222,24,43,44);
                    lBombHunDig.setBounds(35,29,18,35);
                    lBombTenDig.setBounds(58,29,18,35);
                    lBombOneDig.setBounds(80,29,18,35);
                    lTimeHunDig.setBounds(393,29,18,35);
                    lTimeTenDig.setBounds(415,29,18,35);
                    lTimeOneDig.setBounds(437,29,18,35);
                    pBombs.setBounds(21,94,28*16,28*16);

                    pBombs.setLayout(null);
                    bFace.setBorder(null);
                    lBombBg.setBorder(BorderFactory.createLineBorder(Color.GRAY, 4));
                    lTimeBg.setBorder(BorderFactory.createLineBorder(Color.GRAY, 4));

                    for(int i=0; i<16; i++)
                    for(int j=0; j<16; j++){
                        if(!gameIsExist) {
                            buttons[i][j] = new JButton(iSpace);
                            pBombs.add(buttons[i][j]);
                            buttons[i][j].setBorder(null);
                            buttons[i][j].setBounds(28*i,28*j,28,28);
                        }
                    }
                    gameIsExist = true;
                }
                else{
                    for(int i=0; i<16; i++)
                    for(int j=0; j<16; j++){
                        buttons[i][j].setIcon(iSpace);
                    }
                }
                break;

            case 2:
                if(!gameIsExist){
                    setSize(880+14,561+37);
                    bombsCount = 99;
                    lbackground.setIcon(iBackground_hard);
                    buttons = new JButton[30][16];

                    lbackground.setBounds(0,0,880,561);
                    lBombBg.setBounds(30,24,71,43);
                    lTimeBg.setBounds(777, 24, 71, 43);
                    bFace.setBounds(419,24,43,44);
                    lBombHunDig.setBounds(35,29,18,35);
                    lBombTenDig.setBounds(58,29,18,35);
                    lBombOneDig.setBounds(80,29,18,35);
                    lTimeHunDig.setBounds(782,29,18,35);
                    lTimeTenDig.setBounds(805,29,18,35);
                    lTimeOneDig.setBounds(827,29,18,35);
                    pBombs.setBounds(21,94,28*30,28*16);
                    pBombs.setLayout(null);

                    bFace.setBorder(null);
                    lBombBg.setBorder(BorderFactory.createLineBorder(Color.GRAY, 4));
                    lTimeBg.setBorder(BorderFactory.createLineBorder(Color.GRAY, 4));

                    for(int i=0; i<30; i++)
                    for(int j=0; j<16; j++){
                        if(!gameIsExist) {
                            buttons[i][j] = new JButton(iSpace);
                            pBombs.add(buttons[i][j]);
                            buttons[i][j].setBorder(null);
                            buttons[i][j].setBounds(28*i,28*j,28,28);
                        }
                    }
                    gameIsExist = true;
                }

                else{
                    for(int i=0; i<30; i++)
                    for(int j=0; j<16; j++){
                        buttons[i][j].setIcon(iSpace);
                    }
                }
                break;
        }
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(this);
    }
    
    public void start(){
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if(continous == true){
                    timeCount += 1;
                    int[] t = {timeCount/100, timeCount/10 %10, timeCount%10};
                    setNum(0,t);
                }
            }
        };
        timer.schedule(timerTask, 0, 1000);
    }

    //m == 0(time)  m == 1(bombs)
    public void setNum(int m, int[] n){
        if(m == 0){
            JLabel[] j = {lTimeHunDig, lTimeTenDig, lTimeOneDig};
            for(int i=0; i<3; i++)
            switch (n[i]) {
                case 0:
                    j[i].setIcon(d0);
                    break;
                case 1:
                    j[i].setIcon(d1);
                    break;
                case 2:
                    j[i].setIcon(d2);
                    break;
                case 3:
                    j[i].setIcon(d3);
                    break;
                case 4:
                    j[i].setIcon(d4);
                    break;
                case 5:
                    j[i].setIcon(d5);
                    break;
                case 6:
                    j[i].setIcon(d6);
                    break;
                case 7:
                    j[i].setIcon(d7);
                    break; 
                case 8:
                    j[i].setIcon(d8);
                    break;
                case 9:
                    j[i].setIcon(d9);
                    break;
                default:
                    break;
            }
        }
        
        if(m == 1){
            JLabel[] j = {lBombHunDig, lBombTenDig, lBombOneDig};
            for(int i=0; i<3; i++)
            switch (n[i]) {
                case 0:
                    j[i].setIcon(d0);
                    break;
                case 1:
                    j[i].setIcon(d1);
                    break;
                case 2:
                    j[i].setIcon(d2);
                    break;
                case 3:
                    j[i].setIcon(d3);
                    break;
                case 4:
                    j[i].setIcon(d4);
                    break;
                case 5:
                    j[i].setIcon(d5);
                    break;
                case 6:
                    j[i].setIcon(d6);
                    break;
                case 7:
                    j[i].setIcon(d7);
                    break; 
                case 8:
                    j[i].setIcon(d8);
                    break;
                case 9:
                    j[i].setIcon(d9);
                    break;
                default:
                    break;
            }
        }
    }
     
}


import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Timer;
import java.util.Vector;

import javax.swing.JButton;

public class Game extends Window implements MouseListener{

    private int[][] bombMap;
    private int[][] bombAround;
    private boolean[][] buttonIsPress;
    private boolean[][] buttonIsFlag;
    private int direct[][]={{0,0},{0,1},{0,-1},{1,0},{-1,0},{1,1},{-1,-1},{-1,1},{1,-1}}; //8方位。
    private int mode;
    private int row;
    private int col;
    private boolean mouseIsExit = false;

    //已完成
    public Game(int mode){
        super(mode);
        this.mode = mode;
        switch (mode) {
            case 0:
                this.col = 9;
                this.row = 9;
                bombsCount = 10;
                spaceCount = 71; // 81-10
                break;
            case 1:
                this.col = 16;
                this.row = 16;
                bombsCount = 40;
                spaceCount = 216; // 256-40
                break;
            case 2:
                this.col = 30;
                this.row = 16;
                bombsCount = 99;
                spaceCount = 381; // 480-99
                break;
        }
        bombMap = new int[col][row];
        bombAround = new int[col][row];
        buttonIsPress = new boolean[col][row];
        buttonIsFlag = new boolean[col][row];
        setButtons();  //在開始遊戲後才設置地雷
    }

    //已完成
    private void restart(){
                
        bFace.setIcon(iFace);
        continous = false;
        setButtons();
        timer.cancel();
        timeCount = 0;
        timer = new Timer();

        setNum(0, new int[] {0,0,0});
        switch (mode) {
            case 0:
                bombsCount = 10;
                spaceCount = 71;
                setNum(1, new int[] {0,1,0});
                break;
            case 1:
                bombsCount = 40;
                spaceCount = 216;
                setNum(1, new int[] {0,4,0});
                break;
            case 2:
                bombsCount = 99;
                spaceCount = 381;
                setNum(1, new int[] {0,9,9});
                break;
        }
    }

    //已完成
    private void setButtons(){

        //先清空Button
        for(int i=0; i<col; i++)
        for(int j=0; j<row; j++){    
            buttons[i][j].removeMouseListener(this);
        }

        //設定Button
        for(int i=0; i<col; i++)
        for(int j=0; j<row; j++){
            buttons[i][j].setIcon(iSpace);
            //避免程式過載
            if(buttons[i][j].getActionCommand() == "") 
            buttons[i][j].setActionCommand(i+" "+j);
            buttons[i][j].addMouseListener(this);
            buttonIsFlag[i][j] = false;
            buttonIsPress[i][j] = false;
        }

        //設定face  
        //避免程式過載
        if(bFace.getActionCommand() == ""){
            bFace.setActionCommand("restart");
            bFace.addMouseListener(this);
        }
    }

    //已完成
    private void setBombs(int x, int y){
        //清空地雷
        for(int i=0; i<col; i++)
        for(int j=0; j<row; j++){
            bombMap[i][j] = 0;
        }

        //鋪地雷
        int count=0;
		while(count != bombsCount){
			int i = 0;
            int j = 0;
			if(this.mode == 0){
				i = (int)(Math.random()*9);
				j = (int)(Math.random()*9); 
			}
			else if(this.mode == 1){
				i = (int)(Math.random()*16);
				j = (int)(Math.random()*16);
			}
			else if(this.mode == 2){
				i = (int)(Math.random()*30);
				j = (int)(Math.random()*16);
			}

			if(bombMap[i][j] == 0 && !(i == x && j == y)){
				bombMap[i][j] = 1;
				count++;
			}
		}
    }

    //已完成
    private void setArroundBombs(){
        //清空數字
        for(int i=0; i<col; i++)
        for(int j=0; j<row; j++){
            bombAround[i][j] = 0;
        }
    
        //重新設置
        for(int i=0; i<col; i++)
        for(int j=0; j<row; j++){
            if(bombMap[i][j] == 1){
                //炸彈設為-1
                bombAround[i][j] = -1;
            }
            else{
                for(int k=0; k < direct.length; k++){
                    int x = i + direct[k][0];
                    int y = j + direct[k][1];
                    if((x < col && y < row) && (x >= 0 && y >= 0) && bombMap[x][y] == 1){
                        bombAround[i][j]++;
                    }
                }
            }
        }
    }

    //已完成
    private void clickButton(int x, int y, MouseEvent e){

        //開始遊戲
        if(!continous){
            setBombs(x, y);
            setArroundBombs();
            continous = true;
            start();
        }

        //左鍵
        if(e.getButton() == MouseEvent.BUTTON1 && !buttonIsPress[x][y] && !buttonIsFlag[x][y]){

            switch (bombAround[x][y]) {
                //地雷
                case -1:
                    endGame(1, x, y); //失敗
                    break;
            
                //空格
                case 0:
                    openSpace(x, y);
                    break;
                    
                //數字
                default:
                    setNumOfSpace(x, y);
                    break;
            }
        }

        //右鍵
        if(e.getButton() == MouseEvent.BUTTON3 && !buttonIsPress[x][y]){
            
            //放旗子
            if(!buttonIsFlag[x][y] && bombsCount != 0){
                buttons[x][y].setIcon(iFlag);
                buttonIsFlag[x][y] = true;
                bombsCount -= 1;
            }

            //拔旗子
            else if(buttonIsFlag[x][y]){
                buttons[x][y].setIcon(iSpace);
                buttonIsFlag[x][y] = false;
                bombsCount += 1;
            }    
        }   

    }

    //已完成
    private void openSpace(int x, int y){

        Vector<int[]> vector = new Vector<int[]>();
        vector.add(new int[] {x,y});

        //紀錄空格
        for(int i=0; i<vector.size(); i++)
        for(int j=0; j<direct.length; j++){
            int tempCol = vector.get(i)[0] + direct[j][0];
            int tempRow = vector.get(i)[1] + direct[j][1];
            if((tempCol >= 0 && tempRow >= 0) && (tempCol < col && tempRow < row) && bombAround[tempCol][tempRow] == 0){
                boolean mark = true;
                for(int k=0; k<vector.size(); k++){
                    if(tempCol == vector.get(k)[0] && tempRow == vector.get(k)[1]){
                        mark = false;
                        break;
                    }
                }
                if(mark){
                    vector.add(new int[] {tempCol, tempRow});
                }
            }
        }

        //翻開空格
        for(int i=0; i<vector.size(); i++)
        for(int j=0; j<direct.length; j++){
            int tempCol = vector.get(i)[0] + direct[j][0];
            int tempRow = vector.get(i)[1] + direct[j][1];
            if((tempCol >= 0 && tempCol < col) && (tempRow >= 0 && tempRow < row)){

                //避免將旗子翻開
                if(buttonIsFlag[tempCol][tempRow] || buttonIsPress[tempCol][tempRow]){
                    continue;
                }

                if(bombAround[tempCol][tempRow] != 0){
                    buttonIsPress[tempCol][tempRow] = true;
                    setNumOfSpace(tempCol, tempRow);
                }
                else{
                    buttonIsPress[tempCol][tempRow] = true;
                    buttons[tempCol][tempRow].setIcon(iSpace_press);
                    spaceCount -= 1;
                }
            }
        }
    }

    //已完成
    private void setNumOfSpace(int x, int y){
        switch (bombAround[x][y]) {
            case 1:
                buttons[x][y].setIcon(n1);
                break;
            case 2:
                buttons[x][y].setIcon(n2);
                break;
            case 3:
                buttons[x][y].setIcon(n3);
                break;
            case 4:
                buttons[x][y].setIcon(n4);
                break;
            case 5:
                buttons[x][y].setIcon(n5);
                break;
            case 6:
                buttons[x][y].setIcon(n6);
                break;
            case 7:
                buttons[x][y].setIcon(n7);
                break;
            default:
                buttons[x][y].setIcon(n8);
                break;
        }
        buttonIsPress[x][y] = true;
        spaceCount -= 1;
    }

    //已完成
    private void endGame(int mode, int x, int y){
        continous = false;
        
        switch(mode){
            //勝利
            case 0:
                bFace.setIcon(iFace_win);
                for(int i=0; i<col; i++)
                for(int j=0; j<row; j++){

                    buttons[i][j].removeMouseListener(this); 
                    
                    if(buttonIsPress[i][j] ||buttonIsFlag[i][j]){
                        continue;
                    }

                    if(bombMap[i][j] == 1){
                        buttons[i][j].setIcon(iBomb);
                    }

                    else if(bombAround[i][j] == 0){
                        buttons[i][j].setIcon(iSpace);
                    }

                    else{
                        setNumOfSpace(i, j);
                    }
                }
                break;

            //失敗
            case 1:
                bFace.setIcon(iFace_lose);
                for(int i=0; i<col; i++)
                for(int j=0; j<row; j++){
                    // 避免覆蓋旗子、減少重複翻格
                    if(buttonIsPress[i][j] || buttonIsFlag[i][j]){
                        continue;
                    }
                    switch (bombAround[i][j]) {
                        case -1:
                        buttons[i][j].setIcon(iBomb);
                        break;
                                
                        case 0:
                        buttons[i][j].setIcon(iSpace_press);
                        break;

                        default :
                        setNumOfSpace(i, j);
                        break;
                    }
                    buttons[i][j].removeMouseListener(this);
                    buttonIsPress[i][j] = true;
                }
                buttons[x][y].setIcon(iBomb_red);
                break;

        }
    }

    @Override
    //已完成
    public void mousePressed(MouseEvent e) {

        //處裡例外
        if(e.getButton() != MouseEvent.BUTTON1 && e.getButton() != MouseEvent.BUTTON3){
            return;
        }

        String[] command = ((JButton)e.getSource()).getActionCommand().split(" ");
        //重置滑鼠位置
        mouseIsExit = false; 

        //face
        if(command[0].equals("restart")){
            bFace.setIcon(iFace_press);
        }

        //button
        else{
            int x = Integer.parseInt(command[0]);
            int y = Integer.parseInt(command[1]);
            if(e.getButton() == MouseEvent.BUTTON3 && bombsCount == 0){
                return;
            }
            if(!buttonIsPress[x][y] && !buttonIsFlag[x][y]){
                buttons[x][y].setIcon(iSpace_press);
            }
        }
    }

    @Override
    //已完成
    public void mouseReleased(MouseEvent e) {

        //處裡例外button
        if(e.getButton() != MouseEvent.BUTTON1 && e.getButton() != MouseEvent.BUTTON3){
            return;
        }

        String[] command = ((JButton)e.getSource()).getActionCommand().split(" ");
        
        //若滑鼠離開則不做動作
        if(mouseIsExit){
            if(command[0].equals("restart")){
                bFace.setIcon(iFace);
            }  
            else{
                int x = Integer.parseInt(command[0]);
                int y = Integer.parseInt(command[1]);
                if(!buttonIsPress[x][y] && !buttonIsFlag[x][y]){
                    buttons[x][y].setIcon(iSpace);
                }
            }
        }
        
        //若滑鼠未離開(click動作)
        else{
            if(command[0].equals("restart")){
                restart();
            }

            else{
                int x = Integer.parseInt(command[0]);
                int y = Integer.parseInt(command[1]);
                clickButton(x, y, e);

                int h = bombsCount/100;
                int t = bombsCount/10 %10;
                int o = bombsCount%10;
                setNum(1, new int[] {h,t,o}); 
            }
        }

        //判斷是否勝利
        if(spaceCount == 0){
            endGame(0, 0, 0);
        }
    }
    
    @Override
    //已完成
    public void mouseEntered(MouseEvent e) {
        mouseIsExit = true;
    }


    // no function
    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}

     
}

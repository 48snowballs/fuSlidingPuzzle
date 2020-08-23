/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import event.ButtonManager;
import gui.Puzzle;
import java.awt.Dimension;
import java.awt.GridLayout;
import static java.lang.Thread.sleep;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Dell
 */
public class Controller {
    
    private final Puzzle p;
    private ButtonManager bm;
    private static final JLabel jLabelCount = new JLabel("Move(s) counted: 0");
    private static final JLabel jLabelTime = new JLabel("Elapsed: 0");
    public int count = 0;
    static int time = 0;
    public int height = 0;
    public int width = 0;
    int size;
    
    // creat game screen
    public Controller(Puzzle p) {
        this.p = p;
        p.jPanelMove.setLayout(new GridLayout(1,1));
        p.jPanelTime.setLayout(new GridLayout(1,1));
        p.jPanelMove.add(jLabelCount);
        p.jPanelTime.add(jLabelTime);
    }
    
    //show the moves counted
    public void changeCount() {
        jLabelCount.setText("Move(s) counted: " + count);
    }
    
    //insert options
    public void insert() {
        //loop to insert each option at jComboBoxOption
        for (int i=0; i<7; i++){
            int i2 = i+3;
            p.jComboBoxSize.insertItemAt(i2 + " x " + i2, i);
        }
        p.jComboBoxSize.setSelectedIndex(0);
    }
    
    //creat new game
    public void createNewGame() {
        size = p.jComboBoxSize.getSelectedIndex() + 3;
        time = 0;
        if(cTime.isAlive()){
            cTime.resume();
        }
        else{
            cTime.start();
        }
        create(size);
    }
    
    //show
    public void show(JFrame jFrame) {
        jFrame.setBounds(0, 0, width + 50, height + 200);
        jFrame.setLocationRelativeTo(null);
    }
    
    //creat the game
    public void create(int length) {
        bm = new ButtonManager(length, this);
        //clear the remained buttons
        p.jPanelPuzzle.removeAll();
        //10: space between 2 buttons
        width = height = length * 50 + 10;
        //set size that depends on the number of buttons
        p.jPanelPuzzle.setPreferredSize(new Dimension(width, height));
        //set GridLayout depends on the length
        p.jPanelPuzzle.setLayout(new GridLayout(length, length));
        //Begin set button
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                p.jPanelPuzzle.add(bm.matrixButton[i][j]);
            }
        }
        count = 0;
        changeCount();
        //randomly mix the buttons
        random(length);
    }
    
    
    //randomly mix the buttons
    void random(int size) {
        Random r = new Random();
        for (int i=0; i<1000; i++){
            int n = r.nextInt(size*size);
            bm.mixAll(n);
        }
    }
    
    //pause
    public void pause(){
        cTime.suspend();
    }
    //resume
    public void resume(){
        cTime.resume();
    }
    
    public static countTime cTime = new countTime();

    //Increase the time passed after every second
    public static class countTime extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    sleep(1000);
                    jLabelTime.setText("Elapsed: " + time++);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Puzzle.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
}

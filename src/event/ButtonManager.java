/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package event;

import bo.Button;
import control.Controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author Dell
 */
public class ButtonManager implements ActionListener{
    public Button [][] matrixButton;
    int length = 0;
    private final Controller c;

    public ButtonManager(int size, Controller c) {
        this.c = c;
        //constuct matrixButton
        matrixButton = new Button[size][size];
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                matrixButton [i][j] = new Button(i, j);
                matrixButton [i][j].number = i*size + j + 1;
                matrixButton [i][j].setText(matrixButton [i][j].number + "");
                matrixButton [i][j].addActionListener((ActionListener)this);
            }
        }
        matrixButton[size-1][size-1].setText("");
        length = size;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Switch only when x-y=1
        //Coordinate of the button that can swap with the blank button
        int x = 0, y = 0;
        //Coordinate of the blank button
        int x2 = 0, y2 = 0;
        String name = e.getActionCommand();
        if (name.equalsIgnoreCase("")) {
            return;
        }
        //set the non-blank button
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (matrixButton[i][j].getText().equals(name)) {
                    x = i;
                    y = j;
                    break;
                }
            }
        }
        //set the blank button
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (matrixButton[i][j].getText().equals("")) {
                    x2 = i;
                    y2 = j;
                    break;
                }
            }
        }
        //swap if true
        if (check(x, y, x2, y2)) {
            String temp = matrixButton[x][y].getText();
            matrixButton[x][y].setText("");
            matrixButton[x2][y2].setText(temp);
            //increase the moves counted
            c.count++;
            c.changeCount();
        }

        //win
        if(end()){
            Controller.cTime.suspend();
            for (int i=0; i<length; i++){
                for (int j=0; j<length; j++){
                    matrixButton[i][j].setEnabled(false);
                }
            }
            JOptionPane.showMessageDialog(null, "You win!");
        }
    }
    
    //mix the buttons 
    public void mixAll(int n) {
        int x = 0, y = 0; //Coordinate of the swappable button
        int x2 = 0, y2 = 0; //Coordinate of the blank button
        String name;
        if (n == 0) {
            return;
        } else {
            name = n + "";
        }
        //Coordinate of the randomly created button
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (matrixButton[i][j].getText().equals(name)) {
                    x = i;
                    y = j;
                    break;
                }
            }
        }
        //Coordinate of the blank button
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (matrixButton[i][j].getText().equals("")) {
                    x2 = i;
                    y2 = j;
                    break;
                }
            }
        }
        if (check(x, y, x2, y2)) {
            //swap
            String temp = matrixButton[x][y].getText();
            matrixButton[x][y].setText("");
            matrixButton[x2][y2].setText(temp);
        } 
    }
    
    //Check the coorinates of the blank button and the button that can swap with it
    boolean check(int x, int y, int x2, int y2) {
        //Swappable cases
        if (x == x2 && y == y2 - 1) {
            return true;
        }
        if (x == x2 && y == y2 + 1) {
            return true;
        }
        if (x == x2 + 1 && y == y2) {
            return true;
        }
        return x == x2 - 1 && y == y2;
    }

    //Check if the player had won the game
    boolean end() {
        for (int i=0; i<length*length-1; i++){
            if (!matrixButton[i/length][i%length].getText().equals(i+1 + "")) return false;
        }
        return true;
    }
}

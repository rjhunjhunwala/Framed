/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package framed;

import java.awt.Color;
import javax.swing.JFrame;

/**
 *
 * @author rohan
 */
public class GameFrame extends JFrame{
	public GameFrame(){
		super("Framed");
		this.setResizable(false);
		this.add(new GamePanel());
		this.pack();
		this.addKeyListener(new Controller());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setBackground(Color.black);
	}
}

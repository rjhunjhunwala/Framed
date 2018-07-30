/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package framed;

import javafx.scene.Scene;
import javafx.scene.effect.PerspectiveTransform;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author rohan
 */
public class ReRenderRunnable implements Runnable{
	
	public void run(){

		StackPane root = new StackPane();
Scene gameScene = new Scene(root,GamePanel.X_SIZE,GamePanel.Y_SIZE);

 PerspectiveTransform perspectiveTransform = new PerspectiveTransform();
 perspectiveTransform.setUlx(10.0-Framed.p.x);
 perspectiveTransform.setUly(10.0+Framed.p.y);
 perspectiveTransform.setUrx(310.0-Framed.p.x);
 perspectiveTransform.setUry(40.0+Framed.p.y);
 perspectiveTransform.setLrx(310.0-Framed.p.x);
 perspectiveTransform.setLry(60.0+Framed.p.y);
 perspectiveTransform.setLlx(10.0-Framed.p.x);
 perspectiveTransform.setLly(90.0+Framed.p.y);


Rectangle rect = new Rectangle();
rect.setFill(Color.web("0xFF0000"));
rect.setWidth(140);
rect.setHeight(120);
rect.setEffect(perspectiveTransform);
root.getChildren().add(rect);
GameFrame.jFXPanel.setScene(gameScene);

		}
	
}

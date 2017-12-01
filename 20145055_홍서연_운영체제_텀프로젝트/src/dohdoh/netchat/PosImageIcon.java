package dohdoh.netchat;
import javax.swing.*;
import java.awt.*;

public class PosImageIcon extends ImageIcon {
	int pX;		
	int pY;			
	int width;		
	int height;			
	int xDirection=1,yDirection=1;
	
	public PosImageIcon(String img, int x, int y, int width, int height) {
		super(img);
		pX=x;
		pY=y;
		this.width = width;
		this.height = height;
	}
	
	public void move(int x, int y) {
		pX += x;
		pY += y;
	}
	
	public void draw(Graphics g) {
		g.drawImage(this.getImage(), pX, pY, width, height, null);
	}
	
	public double distance(PosImageIcon r){
		return Math.sqrt(Math.pow(pX-r.pX , 2) + Math.pow(pY-r.pY, 2));
	}
}

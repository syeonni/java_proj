import javax.swing.*;
import java.awt.*;

public class PosImageIcon extends ImageIcon {
	int pX;		
	int pY;			
	int width;		
	int height;			
	
	public PosImageIcon(String img, int x, int y, int width, int height) {
		super(img);
		pX=x;
		pY=y;
		this.width = width;
		this.height = height;
	}	
	public void draw(Graphics g) {
		g.drawImage(this.getImage(), pX, pY, width, height, null);
	}
	public void move(int x, int y) {
		pX = x;
		pY = y;
	}
}

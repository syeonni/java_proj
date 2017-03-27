package dohdoh.netchat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.*;


public class CatchMindGame extends JPanel implements MouseListener, MouseMotionListener{
	ObjectInputStream reader;	// ���ſ� ��Ʈ��
	ObjectOutputStream writer;	// �۽ſ� ��Ʈ��
	//���,���� ���, ��ŷ���, �����ڷ�ŷ���, ��ĳ����, ���� ĳ����, �� ��ǳ��, ���� ��ǳ��
	PosImageIcon back,questionBack,rank,rank2,myCharacter,youCharater,myspeech,youspeech;
	//�� üũ, �� �α��� üũ, ���� �α��� üũ, ��ŸƮ üũ, ���� ���� ����, ���� üũ, ���� ���ߴ���, ������ ���ߴ���
	boolean turnCheck=false,loginCheck=false,youloginCheck=false,startCheck=false,gameEnd = false,userCheck = false,myTellFlag=false, youTellFlag=false;
	//user1=�� ���̵�, user2=���� ���̵�, ����, ����, ���� �� ��, ������ �� ��
	String user1="",user2="",question="",round="round ",myTell="", youTell="";
	//����
	String word[] = {"�����","����","�Ǹ�","���","������","���ȣ","����","ö��","����","�ﱹ�ô�","��ȸ��","����","�ڳ�",
			"��Ź��","�ǰ���","��ȭ��","������ȭ","����ϴü�","Ÿ�Ǳ�","�δ��","������","������","�ε���","ġŲ",
			"�������","���","��Ʈ��ũ","����","�ʵ��б�","��Ǫ","����û�ұ�","â����","����","����","¥�ӻ�","������",
			"Ŀ��","��å","����","��ħ��","���̾�","ī�޶�","��ȭ","����","����","���","�޷�","�ڹ�","�����ͺ��̽�","�ü��",
			"īī����","����Ÿ��","���","�ϸ�","�ڳ���","�⸰","����","���","�ð�","�ȵ���̵�","�Ƶ��̳�","C++","����",
			"�Ӻ����","�����","������","����","������","���","������","��������"};
	
	//start, ���� ��ư, ���찳 ��ư
	JButton startButton = new JButton("start"),blackButton, yellowButton, redButton, greenButton, blueButton, eraserButton;	
	//�� ����, �޴� x,y��ǥ, �����������ι��� ��, �����, �� �����, ���� ���� ��, �� ĳ����
	int color=0,x=100,y=100,randomNum,roundNum=0,myScore=0, youScore=0,myCharacterNum;
	//�׸� �׸����� ��ǥ�� ������ ������ ArrayList
	ArrayList <sketch> list = new ArrayList<sketch>();
	//���� �ð�, ���� ���� �ð��� ������ ���ѽð� 2�� ���� �����ֱ� ���� Ÿ�̸�
	Timer timer,myTellTimer,youTellTimer;
	//�ð��� ���� ����
	long startTime,saveTime;
	public CatchMindGame()	
	{
		//�⺻ gui ����
		back = new PosImageIcon("src/res/���.PNG",0,0,820,580);	
		questionBack = new PosImageIcon("src/res/����.png",240,140,170,50);
		rank = new PosImageIcon("src/res/����.png",250,180,340,250);
		rank2 = new PosImageIcon("src/res/����2.png",250,180,340,250);
		myspeech = new PosImageIcon("src/res/��ǳ��.PNG",30,360,148,103);
		youspeech = new PosImageIcon("src/res/��ǳ��.PNG",660,360,148,103);
		startButton = new JButton("Start");
		startButton.addActionListener(new StartButtonListener());
		blackButton = new JButton(new ImageIcon("src/res/��.png"));
		blackButton.addActionListener(new BlackButtonListener());
		yellowButton = new JButton(new ImageIcon("src/res/��.png"));
		yellowButton.addActionListener(new YellowButtonListener());
		redButton = new JButton(new ImageIcon("src/res/��.png"));
		redButton.addActionListener(new RedButtonListener());
		greenButton = new JButton(new ImageIcon("src/res/��.png"));
		greenButton.addActionListener(new GreenButtonListener());
		blueButton = new JButton(new ImageIcon("src/res/��.png"));
		blueButton.addActionListener(new BlueButtonListener());
		eraserButton = new JButton(new ImageIcon("src/res/���찳.png"));
		eraserButton.addActionListener(new EraserButtonListener());
		add(startButton);
		startButton.setBackground(Color.white);
		add(blackButton);
		blackButton.setBorderPainted(false);
		blackButton.setOpaque(false);
		blackButton.setContentAreaFilled(false);
		add(yellowButton);
		yellowButton.setBorderPainted(false);
		yellowButton.setOpaque(false);
		yellowButton.setContentAreaFilled(false);
		add(redButton);
		redButton.setBorderPainted(false);
		redButton.setOpaque(false);
		redButton.setContentAreaFilled(false);
		add(greenButton);
		greenButton.setBorderPainted(false);
		greenButton.setOpaque(false);
		greenButton.setContentAreaFilled(false);
		add(blueButton);
		blueButton.setBorderPainted(false);
		blueButton.setOpaque(false);
		blueButton.setContentAreaFilled(false);
		add(eraserButton);
		eraserButton.setBorderPainted(false);
		eraserButton.setOpaque(false);
		eraserButton.setContentAreaFilled(false);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		timer = new Timer(1000, new TimerListener());
		myTellTimer = new Timer(2000, new MyTellTimerListener());
		youTellTimer = new Timer(2000, new YouTellTimerListener());
	}	
	public void paintComponent(Graphics g) {
		back.draw(g); //���
		startButton.setBounds(680,500, 80, 30); //��ư��
		blackButton.setBounds(220,500, 38, 24);
		yellowButton.setBounds(260,500, 38, 24);
		redButton.setBounds(300,500, 38, 24);
		greenButton.setBounds(340,500, 38, 24);
		blueButton.setBounds(380,500, 38, 24);
		eraserButton.setBounds(420,500, 38, 24);
		g.setFont(new Font("HY����B", Font.BOLD,30));
		g.drawString(user1, 25, 300); //���� �̸�
		g.drawString(user2, 630, 300);
		if(loginCheck) myCharacter.draw(g); //���� �α��� �ϸ� �� ĳ���� �׸�
		if(youloginCheck) youCharater.draw(g); //������ �α��� �ϸ� ���� ĳ���� �׸�
		for(int i = 0; i<list.size()-1; i++){ //arrayList�� �ִ� ��ǥ�� ���� ���� �׸�
			if(list.get(i).color==0) g.setColor(Color.BLACK);
			else if(list.get(i).color==1) g.setColor(Color.yellow);
			else if(list.get(i).color==2) g.setColor(Color.red);
			else if(list.get(i).color==3) g.setColor(Color.green);
			else if(list.get(i).color==4) g.setColor(Color.blue);
			else g.setColor(Color.white);
			g.fillOval(list.get(i).x, list.get(i).y, list.get(i).width, list.get(i).height);

		}
		if(startCheck&&turnCheck){ //��ŸƮ ��ư�� ������ �� ���̸� ������ ����
			g.setColor(Color.BLACK);
			questionBack.draw(g);
			g.setFont(new Font("HY����B", Font.BOLD,20));
			g.drawString(question, 290, 170);
		}
		g.setColor(Color.BLACK);
		g.setFont(new Font("HY����B", Font.BOLD,15));
		g.drawString(round+roundNum, 530, 480); //���� ǥ��
		g.setFont(new Font("HY����B", Font.BOLD,35)); 
		g.drawString(saveTime/60+" : "+saveTime%60, 500, 540); //���� �ð� ǥ��
		g.setFont(new Font("HY����B", Font.BOLD,20));
		g.drawString(myScore+"", 60, 345); //�� ����
		g.drawString(youScore+"", 670, 345); //���� ����
		if(gameEnd){ //���� �������� ���� ǥ��
			g.setFont(new Font("HY����B", Font.BOLD,25));
			if(myScore>youScore){ //���� �̱� ��� �� ������ �� ���� 
				rank.draw(g);
				g.drawString(user1, 370, 305);
				g.drawString(myScore+"��", 450, 305);
				g.drawString(myScore*10+"��", 510, 305);
				g.drawString(user2, 370, 375);
				g.drawString(youScore+"��", 450, 375);
				g.drawString(youScore*10+"��", 510, 375);
			}
			else if(myScore==youScore){ //������ ��� ��� ����
				rank2.draw(g);
				g.drawString(user1, 370, 305);
				g.drawString(myScore+"��", 450, 305);
				g.drawString(myScore*10+"��", 510, 305);
				g.drawString(user2, 370, 375);
				g.drawString(youScore+"��", 450, 375);
				g.drawString(youScore*10+"��", 510, 375);
			}
			else{ //��밡 �̱��� ���� ������ ���� 
				rank.draw(g);
				g.drawString(user2, 370, 305);
				g.drawString(youScore+"��", 450, 305);
				g.drawString(youScore*10+"��", 510, 305);
				g.drawString(user1, 370, 375);
				g.drawString(myScore+"��", 450, 375);
				g.drawString(myScore*10+"��", 510, 375);	
			}
		}
		g.setFont(new Font("�޸ո���ü", Font.PLAIN,25));
		if(myTellFlag){ //���� ���Ѱ� 2�� ���̸� ǥ��
			myspeech.draw(g);
			g.drawString(myTell, 50, 420);
		}
		if(youTellFlag){ //������ ���Ѱ� 2�� ���̸� ǥ��
			youspeech.draw(g);
			g.drawString(youTell, 680, 420);
		}
	}

	public void gameSet(String my, String you){ //���� ���� �� ���� ����� ���̵� ����
		user1 = my;
		user2 = you;
	}
	public void gameSet2(){ //������ ������ ���� �ٲ� �� ���� ���� ���� ����. �� ���� �ٽ� ���������� ����
		randomNum = (int) (Math.random()*(word.length-1));
		question = word[randomNum];
		color=0;
		repaint();
	}
	public void listAdd(int x, int y,int height, int width,int color){ //�׸� ������ ����Ʈ�� �߰�
		list.add(new sketch(x,y,height,width,color));
		repaint();
	}
	public void timeAdd(long time){ //�ð��� ���� ������ �ð� ����
		saveTime = time;
		repaint();
	}
	public class StartButtonListener implements ActionListener { //��ŸƮ ��ư. ������ ����� ���� true�� �ǹǷ� ���� �� �� ����
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(!startCheck){ 
				try {
					startCheck = true;
					turnCheck = true;
					userCheck = true;
					writer.writeObject(new ChatMessage(ChatMessage.MsgType.GAME_START, userCheck,myCharacterNum,user1));
					writer.flush();
					repaint();			   		 
				} catch(Exception ex) {
					JOptionPane.showMessageDialog(null, "�޽��� ������ ������ �߻��Ͽ����ϴ�.");
					ex.printStackTrace();
				}
			}
			else  JOptionPane.showMessageDialog(null, "������ �������Դϴ�.");
			startTime = 20; //�ð� �ʱ�ȭ
			timer.start();  //�ð� ����
			repaint();
		}
	}
	//���� ��ư��. ���� ��ư�� Ŀ���� ������ ���찳�� ���찳��
	public class BlackButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			color=0;
			Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(
					new ImageIcon("src/res/��.png").getImage(), new Point(0, 30), "focus");
			setCursor(cursor);
		}
	}
	public class YellowButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			color=1;
			Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(
					new ImageIcon("src/res/��.png").getImage(), new Point(0, 30), "focus");
			setCursor(cursor);
		}
	}
	public class RedButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			color=2;
			Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(
					new ImageIcon("src/res/��.png").getImage(), new Point(0, 30), "focus");
			setCursor(cursor);
		}
	}
	public class GreenButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			color=3;
			Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(
					new ImageIcon("src/res/��.png").getImage(), new Point(0, 30), "focus");
			setCursor(cursor);
		}
	}
	public class BlueButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			color=4;
			Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(
					new ImageIcon("src/res/��.png").getImage(), new Point(0, 30), "focus");
			setCursor(cursor);
		}
	}
	public class EraserButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			color=5;
			Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(
					new ImageIcon("src/res/���찳.png").getImage(), new Point(0, 30), "focus");
			setCursor(cursor);
		}
	}
	//���� �ð� Ÿ�Ӹ�����. �ð��� ���� ������ ��ε鿡�� �˸�
	class TimerListener implements ActionListener {
		public void actionPerformed (ActionEvent event) {
			startTime--;
			try {
				writer.writeObject(new ChatMessage(ChatMessage.MsgType.GAME_TIME, startTime));
				writer.flush();
				repaint();

			} catch(Exception ex) {
				JOptionPane.showMessageDialog(null, "�޽��� ������ ������ �߻��Ͽ����ϴ�.");
				ex.printStackTrace();
			}
		}
	}
	//���� ���� �� Ÿ�� ������ 2�� �Ŀ� myTellFlag�� false�ؼ� �ȳ������� ��
	class MyTellTimerListener implements ActionListener {
		public void actionPerformed (ActionEvent event) {
			myTellFlag=false;
			myTellTimer.stop();
			repaint();
		}
	}
	//���� ���� �� Ÿ�� ������ 2�� �Ŀ� youTellFlag�� false�ؼ� �ȳ������� ��
	class YouTellTimerListener implements ActionListener {
		public void actionPerformed (ActionEvent event) {
			youTellFlag=false;
			youTellTimer.stop();
			repaint();
		}
	}
	//��ǥ�� ���� ���� Ŭ����
	class sketch {
		int x,y,color,height,width;
		public sketch(int x, int y,int height, int width,int color){
			this.x = x;
			this.y = y;
			this.height=height;
			this.width=width;
			this.color = color;
		}
	}
	
	//�׸� �гο� �巡�� �� �� ���� �۵��ؼ� �� ���� ��ǥ�� ��� ������� ����. ���� ���� ���찳�� ���� ������
	public void mouseDragged(MouseEvent e) {
		if(turnCheck){
    		if(color!=5){
    			x = e.getX() - 7;
    			y = e.getY() - 7;
    			if(x>230 &&x<595 && y>130 && y<470){ //�Ͼ� �� ��ǥ
    				try {
    					writer.writeObject(new ChatMessage(ChatMessage.MsgType.GAME_DRAW, x,y,15,15,color));
    					writer.flush();
    					repaint();

    				} catch(Exception ex) {
    					JOptionPane.showMessageDialog(null, "�޽��� ������ ������ �߻��Ͽ����ϴ�.");
    					ex.printStackTrace();
    				}
    			}
    		}
    		else{
    			x = e.getX() - 25; //���찳�� �� �� ũ��.
    			y = e.getY() - 25;
    			if(x>216 &&x<565 && y>130 && y<440) { //�Ͼ� �� ��ǥ
    				try {
    					writer.writeObject(new ChatMessage(ChatMessage.MsgType.GAME_DRAW, x,y,50,50,5));
    					writer.flush();
    					repaint();

    				} catch(Exception ex) {
    					JOptionPane.showMessageDialog(null, "�޽��� ������ ������ �߻��Ͽ����ϴ�.");
    					ex.printStackTrace();
    				}
    			}
    		}
    	}
    	else{
    		JOptionPane.showMessageDialog(null, "���� ���� �Դϴ�.");
    	}
    	repaint();
	}

	public void mouseMoved(MouseEvent e) {

	}

	public void mouseClicked(MouseEvent e) {

	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {	
	}

	public void mouseReleased(MouseEvent e) {
	}

}
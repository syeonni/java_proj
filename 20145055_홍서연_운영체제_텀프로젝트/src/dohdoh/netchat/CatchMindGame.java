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
	ObjectInputStream reader;	// 수신용 스트림
	ObjectOutputStream writer;	// 송신용 스트림
	//배경,문제 배경, 랭킹배경, 동점자랭킹배경, 내캐릭터, 상대방 캐릭터, 내 말풍선, 상대방 말풍선
	PosImageIcon back,questionBack,rank,rank2,myCharacter,youCharater,myspeech,youspeech;
	//턴 체크, 내 로그인 체크, 상대방 로그인 체크, 스타트 체크, 게임 끝난 유무, 유저 체크, 내가 말했는지, 상대방이 말했는지
	boolean turnCheck=false,loginCheck=false,youloginCheck=false,startCheck=false,gameEnd = false,userCheck = false,myTellFlag=false, youTellFlag=false;
	//user1=내 아이디, user2=상대방 아이디, 문제, 라운드, 내가 한 말, 상대방이 한 말
	String user1="",user2="",question="",round="round ",myTell="", youTell="";
	//문제
	String word[] = {"엿장수","퇴학","피멍","약속","세차장","김경호","애인","철학","공고문","삼국시대","개회식","도시","자녀",
			"세탁소","피고인","전화국","공중전화","장수하늘소","타악기","부대찌개","전과자","해적선","부동산","치킨",
			"운전기사","잠수","네트워크","진실","초등학교","샴푸","진공청소기","창조물","강변","열매","짜임새","문방사우",
			"커피","공책","수건","받침대","라이언","카메라","만화","모자","가방","드라마","달력","자바","데이터베이스","운영체제",
			"카카오톡","구데타마","펭귄","하마","코끼리","기린","나무","우산","시계","안드로이드","아두이노","C++","피자",
			"임베디드","라즈베리","에어컨","액자","버물리","모기","프린터","교착상태"};
	
	//start, 색깔 버튼, 지우개 버튼
	JButton startButton = new JButton("start"),blackButton, yellowButton, redButton, greenButton, blueButton, eraserButton;	
	//펜 색깔, 받는 x,y좌표, 문제랜덤으로받을 수, 라운드수, 내 정답수, 상대방 정답 수, 내 캐릭터
	int color=0,x=100,y=100,randomNum,roundNum=0,myScore=0, youScore=0,myCharacterNum;
	//그린 그림들의 좌표와 색깔을 저장할 ArrayList
	ArrayList <sketch> list = new ArrayList<sketch>();
	//가는 시간, 내가 말한 시간과 상대방이 말한시간 2초 동안 보여주기 위한 타이머
	Timer timer,myTellTimer,youTellTimer;
	//시간을 받을 변수
	long startTime,saveTime;
	public CatchMindGame()	
	{
		//기본 gui 설정
		back = new PosImageIcon("src/res/배경.PNG",0,0,820,580);	
		questionBack = new PosImageIcon("src/res/문제.png",240,140,170,50);
		rank = new PosImageIcon("src/res/순위.png",250,180,340,250);
		rank2 = new PosImageIcon("src/res/순위2.png",250,180,340,250);
		myspeech = new PosImageIcon("src/res/말풍선.PNG",30,360,148,103);
		youspeech = new PosImageIcon("src/res/말풍선.PNG",660,360,148,103);
		startButton = new JButton("Start");
		startButton.addActionListener(new StartButtonListener());
		blackButton = new JButton(new ImageIcon("src/res/검.png"));
		blackButton.addActionListener(new BlackButtonListener());
		yellowButton = new JButton(new ImageIcon("src/res/노.png"));
		yellowButton.addActionListener(new YellowButtonListener());
		redButton = new JButton(new ImageIcon("src/res/빨.png"));
		redButton.addActionListener(new RedButtonListener());
		greenButton = new JButton(new ImageIcon("src/res/초.png"));
		greenButton.addActionListener(new GreenButtonListener());
		blueButton = new JButton(new ImageIcon("src/res/파.png"));
		blueButton.addActionListener(new BlueButtonListener());
		eraserButton = new JButton(new ImageIcon("src/res/지우개.png"));
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
		back.draw(g); //배경
		startButton.setBounds(680,500, 80, 30); //버튼들
		blackButton.setBounds(220,500, 38, 24);
		yellowButton.setBounds(260,500, 38, 24);
		redButton.setBounds(300,500, 38, 24);
		greenButton.setBounds(340,500, 38, 24);
		blueButton.setBounds(380,500, 38, 24);
		eraserButton.setBounds(420,500, 38, 24);
		g.setFont(new Font("HY나무B", Font.BOLD,30));
		g.drawString(user1, 25, 300); //유저 이름
		g.drawString(user2, 630, 300);
		if(loginCheck) myCharacter.draw(g); //내가 로그인 하면 내 캐릭터 그림
		if(youloginCheck) youCharater.draw(g); //상대방이 로그인 하면 상대방 캐릭터 그림
		for(int i = 0; i<list.size()-1; i++){ //arrayList에 있는 좌표들 색깔에 따라 그림
			if(list.get(i).color==0) g.setColor(Color.BLACK);
			else if(list.get(i).color==1) g.setColor(Color.yellow);
			else if(list.get(i).color==2) g.setColor(Color.red);
			else if(list.get(i).color==3) g.setColor(Color.green);
			else if(list.get(i).color==4) g.setColor(Color.blue);
			else g.setColor(Color.white);
			g.fillOval(list.get(i).x, list.get(i).y, list.get(i).width, list.get(i).height);

		}
		if(startCheck&&turnCheck){ //스타트 버튼도 누르고 내 턴이면 문제가 보임
			g.setColor(Color.BLACK);
			questionBack.draw(g);
			g.setFont(new Font("HY나무B", Font.BOLD,20));
			g.drawString(question, 290, 170);
		}
		g.setColor(Color.BLACK);
		g.setFont(new Font("HY나무B", Font.BOLD,15));
		g.drawString(round+roundNum, 530, 480); //라운드 표시
		g.setFont(new Font("HY나무B", Font.BOLD,35)); 
		g.drawString(saveTime/60+" : "+saveTime%60, 500, 540); //남은 시간 표시
		g.setFont(new Font("HY나무B", Font.BOLD,20));
		g.drawString(myScore+"", 60, 345); //내 점수
		g.drawString(youScore+"", 670, 345); //상대방 점수
		if(gameEnd){ //게임 끝났으면 점수 표시
			g.setFont(new Font("HY나무B", Font.BOLD,25));
			if(myScore>youScore){ //내가 이긴 경우 내 점수가 더 위에 
				rank.draw(g);
				g.drawString(user1, 370, 305);
				g.drawString(myScore+"개", 450, 305);
				g.drawString(myScore*10+"점", 510, 305);
				g.drawString(user2, 370, 375);
				g.drawString(youScore+"개", 450, 375);
				g.drawString(youScore*10+"점", 510, 375);
			}
			else if(myScore==youScore){ //동점인 경우 상관 없음
				rank2.draw(g);
				g.drawString(user1, 370, 305);
				g.drawString(myScore+"개", 450, 305);
				g.drawString(myScore*10+"점", 510, 305);
				g.drawString(user2, 370, 375);
				g.drawString(youScore+"개", 450, 375);
				g.drawString(youScore*10+"점", 510, 375);
			}
			else{ //상대가 이긴경우 상대방 점수가 위에 
				rank.draw(g);
				g.drawString(user2, 370, 305);
				g.drawString(youScore+"개", 450, 305);
				g.drawString(youScore*10+"점", 510, 305);
				g.drawString(user1, 370, 375);
				g.drawString(myScore+"개", 450, 375);
				g.drawString(myScore*10+"점", 510, 375);	
			}
		}
		g.setFont(new Font("휴먼매직체", Font.PLAIN,25));
		if(myTellFlag){ //내가 말한게 2초 전이면 표시
			myspeech.draw(g);
			g.drawString(myTell, 50, 420);
		}
		if(youTellFlag){ //상대방이 말한게 2초 전이면 표시
			youspeech.draw(g);
			g.drawString(youTell, 680, 420);
		}
	}

	public void gameSet(String my, String you){ //게임 시작 시 나와 상대의 아이디 저장
		user1 = my;
		user2 = you;
	}
	public void gameSet2(){ //게임이 진행중 턴이 바뀔 때 마다 랜덤 문제 만듬. 펜 색깔 다시 검은색으로 만듬
		randomNum = (int) (Math.random()*(word.length-1));
		question = word[randomNum];
		color=0;
		repaint();
	}
	public void listAdd(int x, int y,int height, int width,int color){ //그릴 때마다 리스트에 추가
		list.add(new sketch(x,y,height,width,color));
		repaint();
	}
	public void timeAdd(long time){ //시간이 지날 때마다 시간 저장
		saveTime = time;
		repaint();
	}
	public class StartButtonListener implements ActionListener { //스타트 버튼. 누르는 사람의 턴이 true가 되므로 턴을 알 수 있음
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
					JOptionPane.showMessageDialog(null, "메시지 전송중 문제가 발생하였습니다.");
					ex.printStackTrace();
				}
			}
			else  JOptionPane.showMessageDialog(null, "게임이 진행중입니다.");
			startTime = 20; //시간 초기화
			timer.start();  //시간 시작
			repaint();
		}
	}
	//색깔 버튼들. 색인 버튼은 커서를 펜으로 지우개는 지우개로
	public class BlackButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			color=0;
			Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(
					new ImageIcon("src/res/펜.png").getImage(), new Point(0, 30), "focus");
			setCursor(cursor);
		}
	}
	public class YellowButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			color=1;
			Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(
					new ImageIcon("src/res/펜.png").getImage(), new Point(0, 30), "focus");
			setCursor(cursor);
		}
	}
	public class RedButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			color=2;
			Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(
					new ImageIcon("src/res/펜.png").getImage(), new Point(0, 30), "focus");
			setCursor(cursor);
		}
	}
	public class GreenButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			color=3;
			Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(
					new ImageIcon("src/res/펜.png").getImage(), new Point(0, 30), "focus");
			setCursor(cursor);
		}
	}
	public class BlueButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			color=4;
			Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(
					new ImageIcon("src/res/펜.png").getImage(), new Point(0, 30), "focus");
			setCursor(cursor);
		}
	}
	public class EraserButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			color=5;
			Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(
					new ImageIcon("src/res/지우개.png").getImage(), new Point(0, 30), "focus");
			setCursor(cursor);
		}
	}
	//게임 시간 타임리스너. 시간이 지날 때마다 모두들에게 알림
	class TimerListener implements ActionListener {
		public void actionPerformed (ActionEvent event) {
			startTime--;
			try {
				writer.writeObject(new ChatMessage(ChatMessage.MsgType.GAME_TIME, startTime));
				writer.flush();
				repaint();

			} catch(Exception ex) {
				JOptionPane.showMessageDialog(null, "메시지 전송중 문제가 발생하였습니다.");
				ex.printStackTrace();
			}
		}
	}
	//내가 말한 것 타임 리스너 2초 후에 myTellFlag를 false해서 안나오도록 함
	class MyTellTimerListener implements ActionListener {
		public void actionPerformed (ActionEvent event) {
			myTellFlag=false;
			myTellTimer.stop();
			repaint();
		}
	}
	//상대방 말한 것 타임 리스너 2초 후에 youTellFlag를 false해서 안나오도록 함
	class YouTellTimerListener implements ActionListener {
		public void actionPerformed (ActionEvent event) {
			youTellFlag=false;
			youTellTimer.stop();
			repaint();
		}
	}
	//좌표를 저장 형식 클래스
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
	
	//그림 패널에 드래그 할 때 마다 작동해서 그 곳의 좌표를 모든 사람에게 보냄. 색일 때와 지우개일 때로 나뉜다
	public void mouseDragged(MouseEvent e) {
		if(turnCheck){
    		if(color!=5){
    			x = e.getX() - 7;
    			y = e.getY() - 7;
    			if(x>230 &&x<595 && y>130 && y<470){ //하얀 곳 좌표
    				try {
    					writer.writeObject(new ChatMessage(ChatMessage.MsgType.GAME_DRAW, x,y,15,15,color));
    					writer.flush();
    					repaint();

    				} catch(Exception ex) {
    					JOptionPane.showMessageDialog(null, "메시지 전송중 문제가 발생하였습니다.");
    					ex.printStackTrace();
    				}
    			}
    		}
    		else{
    			x = e.getX() - 25; //지우개는 좀 더 크다.
    			y = e.getY() - 25;
    			if(x>216 &&x<565 && y>130 && y<440) { //하얀 곳 좌표
    				try {
    					writer.writeObject(new ChatMessage(ChatMessage.MsgType.GAME_DRAW, x,y,50,50,5));
    					writer.flush();
    					repaint();

    				} catch(Exception ex) {
    					JOptionPane.showMessageDialog(null, "메시지 전송중 문제가 발생하였습니다.");
    					ex.printStackTrace();
    				}
    			}
    		}
    	}
    	else{
    		JOptionPane.showMessageDialog(null, "상대방 차례 입니다.");
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
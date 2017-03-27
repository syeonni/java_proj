package dohdoh.netchat;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

import dohdoh.netchat.CatchMindGame.TimerListener;

import java.awt.*;
import java.awt.event.*;

public class ChatClient{
	JFrame frame,joinFrame,currectFrame,timeFrame;
	String frameTitle = "채팅 클라이언트";
	String joinFrameTitle = "로그인";
	JTextArea incoming;			// 수신된 메시지를 출력하는 곳
	JTextArea outgoing;			// 송신할 메시지를 작성하는 곳
	JList counterParts;			// 현재 로그인한 채팅 상대목록을 나타내는 리스트.
	ObjectInputStream reader;	// 수신용 스트림
	ObjectOutputStream writer;	// 송신용 스트림
	Socket sock;				// 서버 연결용 소켓
	String user;				// 이 클라이언트로 로그인 한 유저의 이름
	JButton logButton;			// 토글이 되는 로그인/로그아웃 버튼
	JScrollPane qScroller;
	
	CatchMindGame gamePanel; //게임이 진행되는 패널
	JLabel nickName,character; //로그인 패널의 라벨 "아이디","캐릭터 선택"
	JTextField nickNameField;  //로그인 패널의 아이디 필드
	JButton b1,b2,b3,b4,ok; //로그인 패널의 버튼들
	int characterNum; //로그인 패널에서 선택한 캐릭터의 넘버
	String currecter; //맞춘사람의 아이디
	PosImageIcon currectBack,timeBack; //정답 패널의 배경, 타임오버 패널의 배경, 
	int roundPass=0; //양쪽 팀다 해야 라운드가 바뀌므로 그거 체크하기 위한 변수
	int threetwoone=3; //3초뒤에 게임이 넘어감
	Timer timer = new Timer(1000, new threetwoone());
	boolean gameend=false; //맞춰서 넘어가는지 시간이 다되서 넘어가는지 true면 맞춘거 false면 타임오버
	public static void main(String[] args) {
		ChatClient client = new ChatClient();
		client.go();
	}
	private void go() {
		// build GUI
		frame = new JFrame(frameTitle + " : 로그인하세요");
		joinFrame = new JFrame(joinFrameTitle+"");
		currectFrame = new JFrame();
		timeFrame = new JFrame();
		gamePanel = new CatchMindGame();
		currectBack = new PosImageIcon("src/res/정답배경.png",0,0,500,300);
		timeBack = new PosImageIcon("src/res/타임오버.png",0,0,500,300);
		// 메시지 디스플레이 창
		incoming = new JTextArea(15,20);
		incoming.setLineWrap(true);
		incoming.setWrapStyleWord(true);
		incoming.setEditable(false);
		incoming.setBackground(new Color(255,241,204));
		qScroller = new JScrollPane(incoming);
		qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	

		// 대화 상대 목록. 초기에는 "전체" - ChatMessage.ALL 만 있음
		String[] list = {ChatMessage.ALL};
		counterParts = new JList(list);
		JScrollPane cScroller = new JScrollPane(counterParts);
		cScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		cScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		counterParts.setVisibleRowCount(5);
		counterParts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		counterParts.setFixedCellWidth(100);
		counterParts.setBackground(new Color(255,241,204));

		// 메시지 전송을 위한 버튼
		JButton sendButton = new JButton("Send");
		sendButton.addActionListener(new SendButtonListener());
		sendButton.setBackground(Color.white);

		// 메시지 디스플레이 창  
		outgoing = new JTextArea(5,20);
		outgoing.addKeyListener(new EnterKeyListener());
		outgoing.setLineWrap(true);
		outgoing.setWrapStyleWord(true);
		outgoing.setEditable(true);
		outgoing.setBackground(new Color(255,241,204));

		JScrollPane oScroller = new JScrollPane(outgoing);
		oScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		oScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		// 로그인과 아웃을 담당하는 버튼. 처음에는 Login 이었다가 일단 로그인 되고나면 Logout으로 바뀜
		logButton = new JButton("Login");
		logButton.addActionListener(new LogButtonListener());
		logButton.setBackground(Color.white);

		// GUI 배치
		JPanel mainPanel = new mainPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		JPanel upperPanel = new mainPanel();
		upperPanel.setLayout(new BoxLayout(upperPanel, BoxLayout.X_AXIS));
		upperPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		JPanel lowerPanel = new mainPanel();
		lowerPanel.setLayout(new BoxLayout(lowerPanel, BoxLayout.X_AXIS));
		lowerPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		JPanel buttonPanel = new mainPanel();
		buttonPanel.setLayout(new GridLayout(1,2));

		JPanel userPanel = new mainPanel();
		userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.Y_AXIS));

		JPanel inputPanel = new mainPanel();
		inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));

		JPanel sendPanel = new mainPanel();
		sendPanel.setLayout(new BorderLayout());

		//로그인 패널 GUI
		JPanel loginPanel = new loginPanel();
		loginPanel.setLayout(new BorderLayout());
		nickName = new JLabel("아이디");
		character = new JLabel("캐릭터 선택");
		nickNameField = new JTextField();
		b1 = new JButton(new ImageIcon("src/res/구데타마1.png"));
		b2 = new JButton(new ImageIcon("src/res/구데타마2.png"));
		b3 = new JButton(new ImageIcon("src/res/구데타마3.png"));
		b4 = new JButton(new ImageIcon("src/res/구데타마4.png"));
		b1.addActionListener(new B1ButtonListener());
		b2.addActionListener(new B2ButtonListener());
		b3.addActionListener(new B3ButtonListener());
		b4.addActionListener(new B4ButtonListener());
		ok= new JButton("확인");
		ok.addActionListener(new OkButtonListener());

		//정답 패널 GUI
		JPanel currectPanel = new currectPanel();
		
		//타임오버 패널 GUI
		JPanel timePanel = new timePanel();
		
		userPanel.add(new JLabel("대화상대목록"));
		userPanel.add(Box.createRigidArea(new Dimension(0,5)));
		userPanel.setPreferredSize(new Dimension(100,200));	
		userPanel.add(cScroller);

		inputPanel.add(new JLabel("메시지입력"));
		inputPanel.add(Box.createRigidArea(new Dimension(0,5)));
		inputPanel.add(oScroller);

		buttonPanel.add(sendButton);
		buttonPanel.add(logButton);

		sendPanel.add(BorderLayout.CENTER, inputPanel);
		sendPanel.add(BorderLayout.SOUTH, buttonPanel);

		lowerPanel.add(userPanel);
		lowerPanel.add(Box.createRigidArea(new Dimension(5,0)));
		lowerPanel.add(sendPanel);
		lowerPanel.add(Box.createRigidArea(new Dimension(5,0)));

		upperPanel.add(qScroller);

		mainPanel.add(upperPanel);
		mainPanel.add(lowerPanel);

		loginPanel.add(ok);
		loginPanel.add(b1);
		loginPanel.add(b2);
		loginPanel.add(b3);
		loginPanel.add(b4);
		loginPanel.add(nickNameField);
		loginPanel.add(nickName);
		loginPanel.add(character);
		
		//게임 패널에서 마우스 이미지 
		Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(
				/*이미지     */   new ImageIcon("src/res/펜.png").getImage(), 
				/*중심점     */   new Point(0, 30), 
				/*커서이름*/   "focus");
		gamePanel.setCursor(cursor);
		
		// 네트워킹을 시동하고, 서버에서 메시지를 읽을 스레드 구동
		setUpNetworking();
		Thread readerThread = new Thread(new IncomingReader());
		readerThread.start();

		// 클라이언드 프레임 창 조정
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(BorderLayout.EAST, mainPanel);
		frame.getContentPane().add(BorderLayout.CENTER, gamePanel);
		frame.setBounds(350, 200, 1200, 620);
		frame.setVisible(true);

		// 로그인 창 프레임 창 조정
		joinFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		joinFrame.setBounds(750, 250, 300, 500);
		joinFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		joinFrame.getContentPane().add(loginPanel);

		//정답 창 프레임 조정
		currectFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		currectFrame.setBounds(700, 350, 500, 300);
		currectFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		currectFrame.getContentPane().add(currectPanel);

		//타임오버창 프레임 조정
		timeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		timeFrame.setBounds(700, 350, 500, 300);
		timeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		timeFrame.getContentPane().add(timePanel);
	} // close go

	private void setUpNetworking() {  
		try {
			// sock = new Socket("220.69.203.11", 5000);		// 오동익의 컴퓨터
			sock = new Socket("127.0.0.1", 5000);			// 소켓 통신을 위한 포트는 5000번 사용키로 함
			reader = new ObjectInputStream(sock.getInputStream());
			writer = new ObjectOutputStream(sock.getOutputStream());
			gamePanel.writer=writer;
			gamePanel.reader=reader;
		} catch(Exception ex) {
			JOptionPane.showMessageDialog(null, "서버접속에 실패하였습니다. 접속을 종료합니다.");
			ex.printStackTrace();
			frame.dispose();		// 네트워크가 초기 연결 안되면 클라이언트 강제 종료
		}
	} // close setUpNetworking   
	public class mainPanel extends JPanel {
		public void paintComponent(Graphics g) {
			g.setColor(new Color(255,211,99));
			g.fillRect(0, 0, 800, 800);
		}
	}

	public class loginPanel extends JPanel {
		public void paintComponent(Graphics g) {
			g.setColor(new Color(255,211,99));
			g.fillRect(0, 0, 800, 800);
			nickName.setFont(new Font("휴먼매직체", Font.BOLD,20));
			nickName.setBounds(43,32,200,100);
			character.setFont(new Font("휴먼매직체", Font.BOLD,20));
			character.setBounds(43,100,200,100);
			nickNameField.setBounds(100, 65, 130, 30);
			b1.setBounds(40,180,90,90);
			b2.setBounds(160,180,90,90);
			b3.setBounds(40,300,90,90);
			b4.setBounds(160,300,90,90);
			ok.setBounds(110,410,70,30);
			ok.setBackground(Color.white);
		}

	}
	public class currectPanel extends JPanel {
		public void paintComponent(Graphics g) {
			currectBack.draw(g);
			g.setColor(Color.BLACK);
			g.setFont(new Font("HY나무B", Font.BOLD,20));
			g.drawString(currecter, 180, 70); //정답자 알려주기
			if(gamePanel.roundNum==2 && roundPass==1){ //게임이 끝났다면 초기화하고 알림
				gamePanel.gameEnd=true;
				gamePanel.timer.stop();
				gamePanel.startTime=20;
				gamePanel.startCheck=false;
				gamePanel.youloginCheck=false;
				gamePanel.list.clear();
				gamePanel.repaint();
				gamePanel.turnCheck = false;
				g.drawString("게임이 끝났습니다!", 160, 100);
				timer.start();
			}
			else{
				if(roundPass==1)g.drawString("다음 라운드로 넘어갑니다.", 150, 90); //둘 다 하면 라운드로 넘어가고 아니면 다음 차례
				else g.drawString("다음 차례입니다.", 160, 90);
				g.drawString(threetwoone+"초 뒤에 시작합니다.", 150, 120);
				timer.start();
			}
		}

	}
	public class timePanel extends JPanel {
		public void paintComponent(Graphics g) {
			timeBack.draw(g);
			g.setColor(Color.BLACK);
			g.setFont(new Font("HY나무B", Font.BOLD,20));
			if(gamePanel.roundNum==2 && roundPass==1){ //정답자 패널과 같음
				gamePanel.gameEnd=true;
				gamePanel.timer.stop();
				gamePanel.startTime=20;
				gamePanel.startCheck=false;
				gamePanel.list.clear();
				gamePanel.turnCheck=false;
				gamePanel.youloginCheck=false;
				gamePanel.repaint();
				g.drawString("게임이 끝났습니다!", 140, 85);
				timer.start();
			}
			else{
				if(roundPass==1)g.drawString("다음 라운드로 넘어갑니다.", 140, 65);
				else g.drawString("다음 차례입니다.", 160, 65);
				g.drawString(threetwoone+"초 뒤에 시작합니다.", 160, 95);
				timer.start();
			}
		}

	}
	// 로그인과 아웃을 담당하는 버튼의 감청자. 처음에는 Login 이었다가 일단 로그인 되고나면 Logout을 처리
	private class LogButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			if (logButton.getText().equals("Login")) {
				joinFrame.setVisible(true);
				logButton.setText("Logout");
			}
			else
				processLogout();
		}

	}  // close LoginButtonListener inner class// 로그인 처리
	private void processLogin() {
		try {
			writer.writeObject(new ChatMessage(ChatMessage.MsgType.LOGIN, user, "", ""));
			writer.flush();
			frame.setTitle(frameTitle + " (로그인 : " + user + ")");
		} catch(Exception ex) {
			JOptionPane.showMessageDialog(null, "로그인 중 서버접속에 문제가 발생하였습니다.");
			ex.printStackTrace();
		}
	}
	// 로그아웃 처리
	private void processLogout() {
		int choice = JOptionPane.showConfirmDialog(null, "Logout합니다");
		if (choice == JOptionPane.YES_OPTION) {
			try {
				writer.writeObject(new ChatMessage(ChatMessage.MsgType.LOGOUT, user, "", ""));
				writer.flush();
				// 연결된 모든 스트림과 소켓을 닫고 프로그램을 종료 함
				writer.close(); reader.close(); sock.close();
			} catch(Exception ex) {
				JOptionPane.showMessageDialog(null, "로그아웃 중 서버접속에 문제가 발생하였습니다. 강제종료합니다");
				ex.printStackTrace();
			} finally {
				System.exit(100);			// 클라이언트 완전 종료 
			}
		}
	}

	public class SendButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			String to = (String) counterParts.getSelectedValue();
			if (to == null) {
				JOptionPane.showMessageDialog(null, "송신할 대상을 선택한 후 메시지를 보내세요");
				return;
			}
			try {
				String myTell=outgoing.getText();
				incoming.append(user + " : " + myTell + "\n"); // 나의 메시지 창에 보이기
				gamePanel.myTellTimer.stop(); //내가 전의 말한 타임 리스너 스탑
				gamePanel.myTell=myTell; //내가 한 말 저장
				gamePanel.myTellFlag=true; //내가 한 말 보이게 하기
				gamePanel.myTellTimer.start(); //2초동안 보이기 하기 위해서
				writer.writeObject(new ChatMessage(ChatMessage.MsgType.CLIENT_MSG, user, to, outgoing.getText()));
				writer.flush();
				outgoing.setText("");
				outgoing.requestFocus();
			} catch(Exception ex) {
				JOptionPane.showMessageDialog(null, "메시지 전송중 문제가 발생하였습니다.");
				ex.printStackTrace();
			}
		}
	}  // close SendButtonListener inner class
	//로그인 패널의 버튼들 다른게 눌리면 나머지 초기화
	public class B1ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			b1.setIcon(new ImageIcon("src/res/선택.png"));
			b1.setBorderPainted(false);
			b1.setOpaque(false);
			b1.setContentAreaFilled(false);
			b2.setIcon(new ImageIcon("src/res/구데타마2.png"));
			b3.setIcon(new ImageIcon("src/res/구데타마3.png"));
			b4.setIcon(new ImageIcon("src/res/구데타마4.png"));
			characterNum=1;
		}
	}
	public class B2ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			b2.setIcon(new ImageIcon("src/res/선택.png"));
			b2.setBorderPainted(false);
			b2.setOpaque(false);
			b2.setContentAreaFilled(false);
			b1.setIcon(new ImageIcon("src/res/구데타마1.png"));
			b3.setIcon(new ImageIcon("src/res/구데타마3.png"));
			b4.setIcon(new ImageIcon("src/res/구데타마4.png"));
			characterNum=2;
		}
	} 
	public class B3ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			b3.setIcon(new ImageIcon("src/res/선택.png"));
			b3.setBorderPainted(false);
			b3.setOpaque(false);
			b3.setContentAreaFilled(false);
			b2.setIcon(new ImageIcon("src/res/구데타마2.png"));
			b1.setIcon(new ImageIcon("src/res/구데타마1.png"));
			b4.setIcon(new ImageIcon("src/res/구데타마4.png"));
			characterNum=3;
		}
	} 
	public class B4ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			b4.setIcon(new ImageIcon("src/res/선택.png"));
			b4.setBorderPainted(false);
			b4.setOpaque(false);
			b4.setContentAreaFilled(false);
			b2.setIcon(new ImageIcon("src/res/구데타마2.png"));
			b3.setIcon(new ImageIcon("src/res/구데타마3.png"));
			b1.setIcon(new ImageIcon("src/res/구데타마1.png"));
			characterNum=4;
		}
	}
	//로그인 패널의 확인 버튼
	public class OkButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			user=nickNameField.getText(); //확인 시 유저 이름 저장
			gamePanel.user1 = user; //게임 패널의 유저 이름 저장
			gamePanel.myCharacter = new PosImageIcon("src/res/구데타마"+characterNum+".png",100,230,90,140); //게임 패널의 내 캐릭터 저장
			gamePanel.loginCheck=true; //내가 로그인 했다고 체크
			gamePanel.myCharacterNum = characterNum; //내 캐릭터 저장
			processLogin();
			joinFrame.setVisible(false);
		}
	} 
	//sendButton말고 엔터키 했을때도 송신
	public class EnterKeyListener implements KeyListener{
		boolean presscheck=false;
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			if(e.getKeyCode()==KeyEvent.VK_SHIFT){
				presscheck = true;
			}
			else if(e.getKeyCode()==KeyEvent.VK_ENTER){
				if(presscheck == true){
					String str = outgoing.getText() +"\r\n";
					outgoing.setText(str);
					presscheck = false;
				}
				else{
					e.consume();
					presscheck = false;
					String to = (String) counterParts.getSelectedValue();
					if (to == null) {
						JOptionPane.showMessageDialog(null, "송신할 대상을 선택한 후 메시지를 보내세요");
						return;
					}
					try {
						String myTell=outgoing.getText();
						incoming.append(user + " : " + myTell + "\n"); // 나의 메시지 창에 보이기
						gamePanel.myTellTimer.stop(); //내 말을 2초동안 말풍선에 보이기 위한 작업
						gamePanel.myTell=myTell;
						gamePanel.myTellFlag=true;
						gamePanel.myTellTimer.start();
						incoming.setSelectionStart(incoming.getText().length());
						qScroller.getVerticalScrollBar().setValue(qScroller.getVerticalScrollBar().getMaximum());
						writer.writeObject(new ChatMessage(ChatMessage.MsgType.CLIENT_MSG, user, to, outgoing.getText()));
						writer.flush();
						outgoing.setText("");
						outgoing.requestFocus();
					} catch(Exception ex) {
						JOptionPane.showMessageDialog(null, "메시지 전송중 문제가 발생하였습니다.");
						ex.printStackTrace();
					}
				}
			}
			gamePanel.repaint();
		}

		@Override
		public void keyReleased(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_SHIFT){
				presscheck = false;
			}
		}

		@Override
		public void keyTyped(KeyEvent e) {
		}
	}
	//3초 후에 다시 시작하도록 함
	class threetwoone implements ActionListener {
		public void actionPerformed (ActionEvent event) {
			threetwoone--;
			if(threetwoone==0){
				timer.stop();
				if(!gamePanel.gameEnd){ //게임이 끝나지 않았다면
					roundPass++; //다음 차례로 갈껀지 라운드를 넘길건지
					if(roundPass==2){
						gamePanel.roundNum++;
						roundPass=0;
					}
					if(gamePanel.turnCheck==true) //턴 체인지
						gamePanel.turnCheck=false;
					else{
						gamePanel.turnCheck=true;
						gamePanel.timer.start();
					}
					gamePanel.color=1; //턴이 바뀌면서 초기화 할 것들 초기화
					gamePanel.gameSet2();
					gamePanel.list.clear();
					gamePanel.startCheck = true;
					Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(
							/*이미지     */   new ImageIcon("src/res/펜.png").getImage(), 
							/*중심점     */   new Point(0, 30), 
							/*커서이름*/   "focus");
					gamePanel.setCursor(cursor);
				}
				if(gameend) currectFrame.setVisible(false); //창 닫기
				else timeFrame.setVisible(false); //창 닫기
				threetwoone=3;
				gamePanel.repaint();
			}
			else{
				currectFrame.repaint();
				timeFrame.repaint();
			}
		}
	}
	// 서버에서 보내는 메시지를 받는 스레드 작업을 정의하는 클래스
	public class IncomingReader implements Runnable {
		public void run() {
			ChatMessage message;             
			ChatMessage.MsgType type;
			String[] users={};
			try {
				while (true) {
					message = (ChatMessage) reader.readObject();     	 // 서버로기 부터의 메시지 대기                   
					type = message.getType();
					if (type == ChatMessage.MsgType.LOGIN_FAILURE) {	 // 로그인이 실패한 경우라면
						JOptionPane.showMessageDialog(null, "Login이 실패하였습니다. 다시 로그인하세요");
						frame.setTitle(frameTitle + " : 로그인 하세요");
						logButton.setText("Login");
					} else if (type == ChatMessage.MsgType.SERVER_MSG) { // 메시지를 받았다면 보여줌
						if (message.getSender().equals(user)) continue;  // 내가 보낸 편지면 보일 필요 없음
						String youTell = message.getContents();
						incoming.append(message.getSender() + " : " + youTell+ "\n"); //상대방 메세지 말풍선에 2초동안 보이기 위한 ㅈ가업
						gamePanel.youTellTimer.stop();
						gamePanel.youTell=youTell;
						gamePanel.youTellFlag=true;
						gamePanel.youTellTimer.start();
						gamePanel.repaint();
						if(gamePanel.question.equals(message.getContents())){ //상대방의 보낸 메세지가 문제와 맞을 경우 정답 처리
							writer.writeObject(new ChatMessage(ChatMessage.MsgType.GAME_CURRECT, message.getSender()+"님 정답"));
					   		writer.flush();
						}
					} else if (type == ChatMessage.MsgType.LOGIN_LIST) {
						// 유저 리스트를 추출 해서 counterParts 리스트에 넣어 줌.
						// 나는  빼고 (""로 만들어 정렬 후 리스트 맨 앞에 오게 함)
						 users = message.getContents().split("/");
						for (int i=0; i<users.length; i++) {
							if (user.equals(users[i])) users[i] = "";
						}
						users = sortUsers(users);		// 유저 목록을 쉽게 볼 수 있도록 정렬해서 제공
						users[0] =  ChatMessage.ALL;	// 리스트 맨 앞에 "전체"가 들어가도록 함
						counterParts.setListData(users);
						counterParts.setSelectedIndex(0); //로그인 하면 전체 선택하도록 만듬
						frame.repaint();
					} else if (type == ChatMessage.MsgType.NO_ACT){
						// 아무 액션이 필요없는 메시지. 그냥 스킵
					} else if(type == ChatMessage.MsgType.GAME_START){
						if(!gamePanel.youloginCheck){ //상대가 로그인 하지 않았다면
							String my="", you="";
							gamePanel.gameEnd = false; //게임을 시작하니까 gameEnd를 안했다고 표시
							gamePanel.roundNum=0; //라운드 초기화
							roundPass=0; //차례 초기화
							for (int i=0; i<users.length; i++) {
								if (user.equals(users[i])); //나랑 이름이 같으면 아무 작업안하고
								else
									you = users[i]; //다르면 상대방으로 해서 저장
							}
							if(!user.equals(message.startSender)){ //상대방이 스타트를 눌렀다면
								gamePanel.youloginCheck=true; //상대방이 로그인 했다고하고
								gamePanel.youCharater=new PosImageIcon("src/res/구데타마"+message.character+".png",710,230,90,140); //상대방 캐릭터 그림
								try { //다른 상대방도 이를 알아야 하고 초기화 해야 되기 떄문에 알려줌
									writer.writeObject(new ChatMessage(ChatMessage.MsgType.GAME_START, gamePanel.userCheck,gamePanel.myCharacterNum,gamePanel.user1));
									writer.flush();

								} catch(Exception ex) {
									JOptionPane.showMessageDialog(null, "메시지 전송중 문제가 발생하였습니다.");
									ex.printStackTrace();
								}
							}
							if(users.length>1) 
							{
								gamePanel.gameSet(user, you); //상대가 있다면 게임 셋팅
								gamePanel.gameSet2();
								gamePanel.roundNum++;
								gamePanel.repaint();
							}
							else
							{	
								JOptionPane.showMessageDialog(null, "게임 상대가 없습니다.");
							}
						}
					}else if(type == ChatMessage.MsgType.GAME_DRAW){ //그림을 그릴 때 쓰는 메세지 
						gamePanel.listAdd(message.x, message.y, message.height, message.width, message.color);
						gamePanel.repaint();
					}
					else if(type == ChatMessage.MsgType.GAME_CURRECT){ //정답을 맞췄을 때
						currecter = message.Currecter; //정답을 맞춘 사람의 이름
						if(gamePanel.turnCheck) gamePanel.youScore++; //턴이 나라면 상대방 점수올리고
						else gamePanel.myScore++; //턴이 상대방이라면 내 점수를 올리고
						gamePanel.timer.stop(); //게임 타이머 멈추고
						gamePanel.startTime = 20; //초기화
						gameend=true;
						currectFrame.setVisible(true); // 알림 창 열기

					}else if(type == ChatMessage.MsgType.GAME_TIME){ //계속해서 게임 시간을 받음
						if(message.time>=0) gamePanel.timeAdd(message.time); //시간이 0이상이면 계속해서 타임 업데이트
						else{ //0초라면 타임 아웃이기때문에
							gameend=false;
							timeFrame.setVisible(true); //알림 창 띄우고
							gamePanel.timer.stop(); //시간 멈추고
							gamePanel.startTime = 20; //초기화
						}
					}else {
						// 정체가 확인되지 않는 이상한 메시지
						throw new Exception("서버에서 알 수 없는 메시지 도착했음");
					}
				} // close while
			} catch(Exception ex) {
				System.out.println("클라이언트 스레드 종료");		// 프레임이 종료될 경우 이를 통해 스레드 종료
			}
		} // close run

		// 주어진 String 배열을 정렬한 새로운 배열 리턴
		private String [] sortUsers(String [] users) {
			String [] outList = new String[users.length];
			ArrayList<String> list = new ArrayList<String>();
			for (String s : users) {
				list.add(s);
			}
			Collections.sort(list);				// Collections.sort를 사용해 한방에 정렬
			for (int i=0; i<users.length; i++) {
				outList[i] = list.get(i);
			}
			return outList;
		}
	} // close inner class     
	
}

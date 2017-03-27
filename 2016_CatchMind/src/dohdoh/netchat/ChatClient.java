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
	String frameTitle = "ä�� Ŭ���̾�Ʈ";
	String joinFrameTitle = "�α���";
	JTextArea incoming;			// ���ŵ� �޽����� ����ϴ� ��
	JTextArea outgoing;			// �۽��� �޽����� �ۼ��ϴ� ��
	JList counterParts;			// ���� �α����� ä�� ������� ��Ÿ���� ����Ʈ.
	ObjectInputStream reader;	// ���ſ� ��Ʈ��
	ObjectOutputStream writer;	// �۽ſ� ��Ʈ��
	Socket sock;				// ���� ����� ����
	String user;				// �� Ŭ���̾�Ʈ�� �α��� �� ������ �̸�
	JButton logButton;			// ����� �Ǵ� �α���/�α׾ƿ� ��ư
	JScrollPane qScroller;
	
	CatchMindGame gamePanel; //������ ����Ǵ� �г�
	JLabel nickName,character; //�α��� �г��� �� "���̵�","ĳ���� ����"
	JTextField nickNameField;  //�α��� �г��� ���̵� �ʵ�
	JButton b1,b2,b3,b4,ok; //�α��� �г��� ��ư��
	int characterNum; //�α��� �гο��� ������ ĳ������ �ѹ�
	String currecter; //�������� ���̵�
	PosImageIcon currectBack,timeBack; //���� �г��� ���, Ÿ�ӿ��� �г��� ���, 
	int roundPass=0; //���� ���� �ؾ� ���尡 �ٲ�Ƿ� �װ� üũ�ϱ� ���� ����
	int threetwoone=3; //3�ʵڿ� ������ �Ѿ
	Timer timer = new Timer(1000, new threetwoone());
	boolean gameend=false; //���缭 �Ѿ���� �ð��� �ٵǼ� �Ѿ���� true�� ����� false�� Ÿ�ӿ���
	public static void main(String[] args) {
		ChatClient client = new ChatClient();
		client.go();
	}
	private void go() {
		// build GUI
		frame = new JFrame(frameTitle + " : �α����ϼ���");
		joinFrame = new JFrame(joinFrameTitle+"");
		currectFrame = new JFrame();
		timeFrame = new JFrame();
		gamePanel = new CatchMindGame();
		currectBack = new PosImageIcon("src/res/������.png",0,0,500,300);
		timeBack = new PosImageIcon("src/res/Ÿ�ӿ���.png",0,0,500,300);
		// �޽��� ���÷��� â
		incoming = new JTextArea(15,20);
		incoming.setLineWrap(true);
		incoming.setWrapStyleWord(true);
		incoming.setEditable(false);
		incoming.setBackground(new Color(255,241,204));
		qScroller = new JScrollPane(incoming);
		qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	

		// ��ȭ ��� ���. �ʱ⿡�� "��ü" - ChatMessage.ALL �� ����
		String[] list = {ChatMessage.ALL};
		counterParts = new JList(list);
		JScrollPane cScroller = new JScrollPane(counterParts);
		cScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		cScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		counterParts.setVisibleRowCount(5);
		counterParts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		counterParts.setFixedCellWidth(100);
		counterParts.setBackground(new Color(255,241,204));

		// �޽��� ������ ���� ��ư
		JButton sendButton = new JButton("Send");
		sendButton.addActionListener(new SendButtonListener());
		sendButton.setBackground(Color.white);

		// �޽��� ���÷��� â  
		outgoing = new JTextArea(5,20);
		outgoing.addKeyListener(new EnterKeyListener());
		outgoing.setLineWrap(true);
		outgoing.setWrapStyleWord(true);
		outgoing.setEditable(true);
		outgoing.setBackground(new Color(255,241,204));

		JScrollPane oScroller = new JScrollPane(outgoing);
		oScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		oScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		// �α��ΰ� �ƿ��� ����ϴ� ��ư. ó������ Login �̾��ٰ� �ϴ� �α��� �ǰ��� Logout���� �ٲ�
		logButton = new JButton("Login");
		logButton.addActionListener(new LogButtonListener());
		logButton.setBackground(Color.white);

		// GUI ��ġ
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

		//�α��� �г� GUI
		JPanel loginPanel = new loginPanel();
		loginPanel.setLayout(new BorderLayout());
		nickName = new JLabel("���̵�");
		character = new JLabel("ĳ���� ����");
		nickNameField = new JTextField();
		b1 = new JButton(new ImageIcon("src/res/����Ÿ��1.png"));
		b2 = new JButton(new ImageIcon("src/res/����Ÿ��2.png"));
		b3 = new JButton(new ImageIcon("src/res/����Ÿ��3.png"));
		b4 = new JButton(new ImageIcon("src/res/����Ÿ��4.png"));
		b1.addActionListener(new B1ButtonListener());
		b2.addActionListener(new B2ButtonListener());
		b3.addActionListener(new B3ButtonListener());
		b4.addActionListener(new B4ButtonListener());
		ok= new JButton("Ȯ��");
		ok.addActionListener(new OkButtonListener());

		//���� �г� GUI
		JPanel currectPanel = new currectPanel();
		
		//Ÿ�ӿ��� �г� GUI
		JPanel timePanel = new timePanel();
		
		userPanel.add(new JLabel("��ȭ�����"));
		userPanel.add(Box.createRigidArea(new Dimension(0,5)));
		userPanel.setPreferredSize(new Dimension(100,200));	
		userPanel.add(cScroller);

		inputPanel.add(new JLabel("�޽����Է�"));
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
		
		//���� �гο��� ���콺 �̹��� 
		Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(
				/*�̹���     */   new ImageIcon("src/res/��.png").getImage(), 
				/*�߽���     */   new Point(0, 30), 
				/*Ŀ���̸�*/   "focus");
		gamePanel.setCursor(cursor);
		
		// ��Ʈ��ŷ�� �õ��ϰ�, �������� �޽����� ���� ������ ����
		setUpNetworking();
		Thread readerThread = new Thread(new IncomingReader());
		readerThread.start();

		// Ŭ���̾�� ������ â ����
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(BorderLayout.EAST, mainPanel);
		frame.getContentPane().add(BorderLayout.CENTER, gamePanel);
		frame.setBounds(350, 200, 1200, 620);
		frame.setVisible(true);

		// �α��� â ������ â ����
		joinFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		joinFrame.setBounds(750, 250, 300, 500);
		joinFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		joinFrame.getContentPane().add(loginPanel);

		//���� â ������ ����
		currectFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		currectFrame.setBounds(700, 350, 500, 300);
		currectFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		currectFrame.getContentPane().add(currectPanel);

		//Ÿ�ӿ���â ������ ����
		timeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		timeFrame.setBounds(700, 350, 500, 300);
		timeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		timeFrame.getContentPane().add(timePanel);
	} // close go

	private void setUpNetworking() {  
		try {
			// sock = new Socket("220.69.203.11", 5000);		// �������� ��ǻ��
			sock = new Socket("127.0.0.1", 5000);			// ���� ����� ���� ��Ʈ�� 5000�� ���Ű�� ��
			reader = new ObjectInputStream(sock.getInputStream());
			writer = new ObjectOutputStream(sock.getOutputStream());
			gamePanel.writer=writer;
			gamePanel.reader=reader;
		} catch(Exception ex) {
			JOptionPane.showMessageDialog(null, "�������ӿ� �����Ͽ����ϴ�. ������ �����մϴ�.");
			ex.printStackTrace();
			frame.dispose();		// ��Ʈ��ũ�� �ʱ� ���� �ȵǸ� Ŭ���̾�Ʈ ���� ����
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
			nickName.setFont(new Font("�޸ո���ü", Font.BOLD,20));
			nickName.setBounds(43,32,200,100);
			character.setFont(new Font("�޸ո���ü", Font.BOLD,20));
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
			g.setFont(new Font("HY����B", Font.BOLD,20));
			g.drawString(currecter, 180, 70); //������ �˷��ֱ�
			if(gamePanel.roundNum==2 && roundPass==1){ //������ �����ٸ� �ʱ�ȭ�ϰ� �˸�
				gamePanel.gameEnd=true;
				gamePanel.timer.stop();
				gamePanel.startTime=20;
				gamePanel.startCheck=false;
				gamePanel.youloginCheck=false;
				gamePanel.list.clear();
				gamePanel.repaint();
				gamePanel.turnCheck = false;
				g.drawString("������ �������ϴ�!", 160, 100);
				timer.start();
			}
			else{
				if(roundPass==1)g.drawString("���� ����� �Ѿ�ϴ�.", 150, 90); //�� �� �ϸ� ����� �Ѿ�� �ƴϸ� ���� ����
				else g.drawString("���� �����Դϴ�.", 160, 90);
				g.drawString(threetwoone+"�� �ڿ� �����մϴ�.", 150, 120);
				timer.start();
			}
		}

	}
	public class timePanel extends JPanel {
		public void paintComponent(Graphics g) {
			timeBack.draw(g);
			g.setColor(Color.BLACK);
			g.setFont(new Font("HY����B", Font.BOLD,20));
			if(gamePanel.roundNum==2 && roundPass==1){ //������ �гΰ� ����
				gamePanel.gameEnd=true;
				gamePanel.timer.stop();
				gamePanel.startTime=20;
				gamePanel.startCheck=false;
				gamePanel.list.clear();
				gamePanel.turnCheck=false;
				gamePanel.youloginCheck=false;
				gamePanel.repaint();
				g.drawString("������ �������ϴ�!", 140, 85);
				timer.start();
			}
			else{
				if(roundPass==1)g.drawString("���� ����� �Ѿ�ϴ�.", 140, 65);
				else g.drawString("���� �����Դϴ�.", 160, 65);
				g.drawString(threetwoone+"�� �ڿ� �����մϴ�.", 160, 95);
				timer.start();
			}
		}

	}
	// �α��ΰ� �ƿ��� ����ϴ� ��ư�� ��û��. ó������ Login �̾��ٰ� �ϴ� �α��� �ǰ��� Logout�� ó��
	private class LogButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			if (logButton.getText().equals("Login")) {
				joinFrame.setVisible(true);
				logButton.setText("Logout");
			}
			else
				processLogout();
		}

	}  // close LoginButtonListener inner class// �α��� ó��
	private void processLogin() {
		try {
			writer.writeObject(new ChatMessage(ChatMessage.MsgType.LOGIN, user, "", ""));
			writer.flush();
			frame.setTitle(frameTitle + " (�α��� : " + user + ")");
		} catch(Exception ex) {
			JOptionPane.showMessageDialog(null, "�α��� �� �������ӿ� ������ �߻��Ͽ����ϴ�.");
			ex.printStackTrace();
		}
	}
	// �α׾ƿ� ó��
	private void processLogout() {
		int choice = JOptionPane.showConfirmDialog(null, "Logout�մϴ�");
		if (choice == JOptionPane.YES_OPTION) {
			try {
				writer.writeObject(new ChatMessage(ChatMessage.MsgType.LOGOUT, user, "", ""));
				writer.flush();
				// ����� ��� ��Ʈ���� ������ �ݰ� ���α׷��� ���� ��
				writer.close(); reader.close(); sock.close();
			} catch(Exception ex) {
				JOptionPane.showMessageDialog(null, "�α׾ƿ� �� �������ӿ� ������ �߻��Ͽ����ϴ�. ���������մϴ�");
				ex.printStackTrace();
			} finally {
				System.exit(100);			// Ŭ���̾�Ʈ ���� ���� 
			}
		}
	}

	public class SendButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			String to = (String) counterParts.getSelectedValue();
			if (to == null) {
				JOptionPane.showMessageDialog(null, "�۽��� ����� ������ �� �޽����� ��������");
				return;
			}
			try {
				String myTell=outgoing.getText();
				incoming.append(user + " : " + myTell + "\n"); // ���� �޽��� â�� ���̱�
				gamePanel.myTellTimer.stop(); //���� ���� ���� Ÿ�� ������ ��ž
				gamePanel.myTell=myTell; //���� �� �� ����
				gamePanel.myTellFlag=true; //���� �� �� ���̰� �ϱ�
				gamePanel.myTellTimer.start(); //2�ʵ��� ���̱� �ϱ� ���ؼ�
				writer.writeObject(new ChatMessage(ChatMessage.MsgType.CLIENT_MSG, user, to, outgoing.getText()));
				writer.flush();
				outgoing.setText("");
				outgoing.requestFocus();
			} catch(Exception ex) {
				JOptionPane.showMessageDialog(null, "�޽��� ������ ������ �߻��Ͽ����ϴ�.");
				ex.printStackTrace();
			}
		}
	}  // close SendButtonListener inner class
	//�α��� �г��� ��ư�� �ٸ��� ������ ������ �ʱ�ȭ
	public class B1ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			b1.setIcon(new ImageIcon("src/res/����.png"));
			b1.setBorderPainted(false);
			b1.setOpaque(false);
			b1.setContentAreaFilled(false);
			b2.setIcon(new ImageIcon("src/res/����Ÿ��2.png"));
			b3.setIcon(new ImageIcon("src/res/����Ÿ��3.png"));
			b4.setIcon(new ImageIcon("src/res/����Ÿ��4.png"));
			characterNum=1;
		}
	}
	public class B2ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			b2.setIcon(new ImageIcon("src/res/����.png"));
			b2.setBorderPainted(false);
			b2.setOpaque(false);
			b2.setContentAreaFilled(false);
			b1.setIcon(new ImageIcon("src/res/����Ÿ��1.png"));
			b3.setIcon(new ImageIcon("src/res/����Ÿ��3.png"));
			b4.setIcon(new ImageIcon("src/res/����Ÿ��4.png"));
			characterNum=2;
		}
	} 
	public class B3ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			b3.setIcon(new ImageIcon("src/res/����.png"));
			b3.setBorderPainted(false);
			b3.setOpaque(false);
			b3.setContentAreaFilled(false);
			b2.setIcon(new ImageIcon("src/res/����Ÿ��2.png"));
			b1.setIcon(new ImageIcon("src/res/����Ÿ��1.png"));
			b4.setIcon(new ImageIcon("src/res/����Ÿ��4.png"));
			characterNum=3;
		}
	} 
	public class B4ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			b4.setIcon(new ImageIcon("src/res/����.png"));
			b4.setBorderPainted(false);
			b4.setOpaque(false);
			b4.setContentAreaFilled(false);
			b2.setIcon(new ImageIcon("src/res/����Ÿ��2.png"));
			b3.setIcon(new ImageIcon("src/res/����Ÿ��3.png"));
			b1.setIcon(new ImageIcon("src/res/����Ÿ��1.png"));
			characterNum=4;
		}
	}
	//�α��� �г��� Ȯ�� ��ư
	public class OkButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			user=nickNameField.getText(); //Ȯ�� �� ���� �̸� ����
			gamePanel.user1 = user; //���� �г��� ���� �̸� ����
			gamePanel.myCharacter = new PosImageIcon("src/res/����Ÿ��"+characterNum+".png",100,230,90,140); //���� �г��� �� ĳ���� ����
			gamePanel.loginCheck=true; //���� �α��� �ߴٰ� üũ
			gamePanel.myCharacterNum = characterNum; //�� ĳ���� ����
			processLogin();
			joinFrame.setVisible(false);
		}
	} 
	//sendButton���� ����Ű �������� �۽�
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
						JOptionPane.showMessageDialog(null, "�۽��� ����� ������ �� �޽����� ��������");
						return;
					}
					try {
						String myTell=outgoing.getText();
						incoming.append(user + " : " + myTell + "\n"); // ���� �޽��� â�� ���̱�
						gamePanel.myTellTimer.stop(); //�� ���� 2�ʵ��� ��ǳ���� ���̱� ���� �۾�
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
						JOptionPane.showMessageDialog(null, "�޽��� ������ ������ �߻��Ͽ����ϴ�.");
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
	//3�� �Ŀ� �ٽ� �����ϵ��� ��
	class threetwoone implements ActionListener {
		public void actionPerformed (ActionEvent event) {
			threetwoone--;
			if(threetwoone==0){
				timer.stop();
				if(!gamePanel.gameEnd){ //������ ������ �ʾҴٸ�
					roundPass++; //���� ���ʷ� ������ ���带 �ѱ����
					if(roundPass==2){
						gamePanel.roundNum++;
						roundPass=0;
					}
					if(gamePanel.turnCheck==true) //�� ü����
						gamePanel.turnCheck=false;
					else{
						gamePanel.turnCheck=true;
						gamePanel.timer.start();
					}
					gamePanel.color=1; //���� �ٲ�鼭 �ʱ�ȭ �� �͵� �ʱ�ȭ
					gamePanel.gameSet2();
					gamePanel.list.clear();
					gamePanel.startCheck = true;
					Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(
							/*�̹���     */   new ImageIcon("src/res/��.png").getImage(), 
							/*�߽���     */   new Point(0, 30), 
							/*Ŀ���̸�*/   "focus");
					gamePanel.setCursor(cursor);
				}
				if(gameend) currectFrame.setVisible(false); //â �ݱ�
				else timeFrame.setVisible(false); //â �ݱ�
				threetwoone=3;
				gamePanel.repaint();
			}
			else{
				currectFrame.repaint();
				timeFrame.repaint();
			}
		}
	}
	// �������� ������ �޽����� �޴� ������ �۾��� �����ϴ� Ŭ����
	public class IncomingReader implements Runnable {
		public void run() {
			ChatMessage message;             
			ChatMessage.MsgType type;
			String[] users={};
			try {
				while (true) {
					message = (ChatMessage) reader.readObject();     	 // �����α� ������ �޽��� ���                   
					type = message.getType();
					if (type == ChatMessage.MsgType.LOGIN_FAILURE) {	 // �α����� ������ �����
						JOptionPane.showMessageDialog(null, "Login�� �����Ͽ����ϴ�. �ٽ� �α����ϼ���");
						frame.setTitle(frameTitle + " : �α��� �ϼ���");
						logButton.setText("Login");
					} else if (type == ChatMessage.MsgType.SERVER_MSG) { // �޽����� �޾Ҵٸ� ������
						if (message.getSender().equals(user)) continue;  // ���� ���� ������ ���� �ʿ� ����
						String youTell = message.getContents();
						incoming.append(message.getSender() + " : " + youTell+ "\n"); //���� �޼��� ��ǳ���� 2�ʵ��� ���̱� ���� ������
						gamePanel.youTellTimer.stop();
						gamePanel.youTell=youTell;
						gamePanel.youTellFlag=true;
						gamePanel.youTellTimer.start();
						gamePanel.repaint();
						if(gamePanel.question.equals(message.getContents())){ //������ ���� �޼����� ������ ���� ��� ���� ó��
							writer.writeObject(new ChatMessage(ChatMessage.MsgType.GAME_CURRECT, message.getSender()+"�� ����"));
					   		writer.flush();
						}
					} else if (type == ChatMessage.MsgType.LOGIN_LIST) {
						// ���� ����Ʈ�� ���� �ؼ� counterParts ����Ʈ�� �־� ��.
						// ����  ���� (""�� ����� ���� �� ����Ʈ �� �տ� ���� ��)
						 users = message.getContents().split("/");
						for (int i=0; i<users.length; i++) {
							if (user.equals(users[i])) users[i] = "";
						}
						users = sortUsers(users);		// ���� ����� ���� �� �� �ֵ��� �����ؼ� ����
						users[0] =  ChatMessage.ALL;	// ����Ʈ �� �տ� "��ü"�� ������ ��
						counterParts.setListData(users);
						counterParts.setSelectedIndex(0); //�α��� �ϸ� ��ü �����ϵ��� ����
						frame.repaint();
					} else if (type == ChatMessage.MsgType.NO_ACT){
						// �ƹ� �׼��� �ʿ���� �޽���. �׳� ��ŵ
					} else if(type == ChatMessage.MsgType.GAME_START){
						if(!gamePanel.youloginCheck){ //��밡 �α��� ���� �ʾҴٸ�
							String my="", you="";
							gamePanel.gameEnd = false; //������ �����ϴϱ� gameEnd�� ���ߴٰ� ǥ��
							gamePanel.roundNum=0; //���� �ʱ�ȭ
							roundPass=0; //���� �ʱ�ȭ
							for (int i=0; i<users.length; i++) {
								if (user.equals(users[i])); //���� �̸��� ������ �ƹ� �۾����ϰ�
								else
									you = users[i]; //�ٸ��� �������� �ؼ� ����
							}
							if(!user.equals(message.startSender)){ //������ ��ŸƮ�� �����ٸ�
								gamePanel.youloginCheck=true; //������ �α��� �ߴٰ��ϰ�
								gamePanel.youCharater=new PosImageIcon("src/res/����Ÿ��"+message.character+".png",710,230,90,140); //���� ĳ���� �׸�
								try { //�ٸ� ���浵 �̸� �˾ƾ� �ϰ� �ʱ�ȭ �ؾ� �Ǳ� ������ �˷���
									writer.writeObject(new ChatMessage(ChatMessage.MsgType.GAME_START, gamePanel.userCheck,gamePanel.myCharacterNum,gamePanel.user1));
									writer.flush();

								} catch(Exception ex) {
									JOptionPane.showMessageDialog(null, "�޽��� ������ ������ �߻��Ͽ����ϴ�.");
									ex.printStackTrace();
								}
							}
							if(users.length>1) 
							{
								gamePanel.gameSet(user, you); //��밡 �ִٸ� ���� ����
								gamePanel.gameSet2();
								gamePanel.roundNum++;
								gamePanel.repaint();
							}
							else
							{	
								JOptionPane.showMessageDialog(null, "���� ��밡 �����ϴ�.");
							}
						}
					}else if(type == ChatMessage.MsgType.GAME_DRAW){ //�׸��� �׸� �� ���� �޼��� 
						gamePanel.listAdd(message.x, message.y, message.height, message.width, message.color);
						gamePanel.repaint();
					}
					else if(type == ChatMessage.MsgType.GAME_CURRECT){ //������ ������ ��
						currecter = message.Currecter; //������ ���� ����� �̸�
						if(gamePanel.turnCheck) gamePanel.youScore++; //���� ����� ���� �����ø���
						else gamePanel.myScore++; //���� �����̶�� �� ������ �ø���
						gamePanel.timer.stop(); //���� Ÿ�̸� ���߰�
						gamePanel.startTime = 20; //�ʱ�ȭ
						gameend=true;
						currectFrame.setVisible(true); // �˸� â ����

					}else if(type == ChatMessage.MsgType.GAME_TIME){ //����ؼ� ���� �ð��� ����
						if(message.time>=0) gamePanel.timeAdd(message.time); //�ð��� 0�̻��̸� ����ؼ� Ÿ�� ������Ʈ
						else{ //0�ʶ�� Ÿ�� �ƿ��̱⶧����
							gameend=false;
							timeFrame.setVisible(true); //�˸� â ����
							gamePanel.timer.stop(); //�ð� ���߰�
							gamePanel.startTime = 20; //�ʱ�ȭ
						}
					}else {
						// ��ü�� Ȯ�ε��� �ʴ� �̻��� �޽���
						throw new Exception("�������� �� �� ���� �޽��� ��������");
					}
				} // close while
			} catch(Exception ex) {
				System.out.println("Ŭ���̾�Ʈ ������ ����");		// �������� ����� ��� �̸� ���� ������ ����
			}
		} // close run

		// �־��� String �迭�� ������ ���ο� �迭 ����
		private String [] sortUsers(String [] users) {
			String [] outList = new String[users.length];
			ArrayList<String> list = new ArrayList<String>();
			for (String s : users) {
				list.add(s);
			}
			Collections.sort(list);				// Collections.sort�� ����� �ѹ濡 ����
			for (int i=0; i<users.length; i++) {
				outList[i] = list.get(i);
			}
			return outList;
		}
	} // close inner class     
	
}

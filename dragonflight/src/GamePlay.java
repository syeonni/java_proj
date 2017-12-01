import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

public class GamePlay {
	JFrame frame=new JFrame();
	JPanel firstPanel, gamePanel, scorePanel, rankPanel;
	JLabel nameLabel = new JLabel("�̸��Է�"); //�̸��Է� ��
	JLayeredPane lp = new JLayeredPane();// ȭ���� ������ ��ġ�� ���� PaneL ���̾�
	JTextField user = new JTextField(); // �̸� �Է� ���� �ؽ�Ʈ�ʵ� ����
	JButton startButton,pauseButton,rankButton, endButton; //����,�Ͻ����� ��ư
	boolean right=false, left=false;
	PosImageIcon character, finalCharacter; //���� ĳ����
	PosImageIcon firstBack,secondBack,secondBack2,thirdBack,fourBack,clearBack; //���ȭ��
	String name = " ";    //�̸�
	int finalHuntScore, finalDistanceScore; //�����������
	Timer monsterTimer,missileTimer, meteorTimer,meteorTimer2;//���� ����, �̻���, � Ÿ�̸�
	JLabel label; //�̸��Է� ��
	AudioClip StartSound, GameSound, meteorSound, missileSound; //�����
	HardAndFast hf = new HardAndFast(); //�ð��� ������ ���� ���̵� �������� �޼ҵ�
	int hfHp = 100, hfScore = 800, hfFast = 3; //ó�� ���� ��, ó�� ���� ���� �� ����, ó�� ���� �ӵ�
	PosImageIcon kingGuage; //���� ������
	
	//score��
	int huntScore = 0; //ó�� ��� ����
	int distanceScore = 0; //ó�� �Ÿ� ����
	double startTime , endTime , pauseTime; //�Ÿ� ������ ���� �μ�
	
	//���� ����Ʈ, �̻���, �
	ArrayList<PosImageIcon> monsterList1 = new ArrayList<PosImageIcon>(); //����
	ArrayList<PosImageIcon> monsterList2 = new ArrayList<PosImageIcon>();
	ArrayList<PosImageIcon> monsterList3 = new ArrayList<PosImageIcon>();
	ArrayList<PosImageIcon> missile = new ArrayList<PosImageIcon>(); //�̻���
	ArrayList<PosImageIcon> meteor = new ArrayList<PosImageIcon>(); //�
	ArrayList<PosImageIcon> meteorWarning = new ArrayList<PosImageIcon>(); //� ���
	ArrayList<PosImageIcon> meteorWarningLine = new ArrayList<PosImageIcon>(); //� ����
	ArrayList<PosImageIcon> king = new ArrayList<PosImageIcon>(); //����
	ArrayList<PosImageIcon> kingAttack = new ArrayList<PosImageIcon>(); //���հ����̹���
	ArrayList<PosImageIcon> kingAttackRemove = new ArrayList<PosImageIcon>(); //���հ����� ����� ���� ������ ��� ������
	
	//���⸮��Ʈ
	private final String START_SOUND = "/res/��������.wav";
	private final String GAME_SOUND = "/res/��������.wav";
	private final String METEOR_SOUND = "/res/����.wav";
	private final String MISSILE_SOUND = "/res/�̻��ϼҸ�.wav";
	
	int clear=0;
	int MeteorDelay = 6000, meteorDamage = 30; //�������
	int distanceHP = 80; //������ ����
	int pauseCnt = 0; //¦���϶� ���� Ȧ���϶� �ٽý���
	int meteorNums = 5; //���׿� ����
	int nums = 5; //���� ��
	int STEP = 20; //ĳ���� ������ ����Ʈ
	int missileCnt = 1,missileCnt2 = 0; //�̻��� �׷��ִ� ��
	int MonsterDelay = 7, MissileDelay =20; //���Ϳ� �̻����� ������
	private int WIDTH=800, HEIGHT=1000; //â�� ũ��
	
	//����
	Scanner file; //���� ������� ���� ��
	ArrayList<Record> recordList = new ArrayList<Record>(); //��� ����Ʈ
	int rankY1=98, rankY2=118; //���� ǥ�� �̸�, ���� Y��ǥ
	int rankCnt = 0;
	int rankCheck = 0;

	public static void main (String[] args) {
		GamePlay gui = new GamePlay();
		gui.go();
	}
	
	public void go() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(400, 0);
		
		//���� ��ư
		user.addActionListener(new startButton());
		startButton = new JButton(new ImageIcon("src/���۹�ư.png"));
		startButton.addActionListener(new startButton());
		frame.getContentPane().add(startButton);
		//�Ͻ�������ư
		pauseButton = new JButton(new ImageIcon("src/�Ͻ�������ư.png"));
		pauseButton.addActionListener(new pauseButton());
		frame.getContentPane().add(pauseButton);
		//����Ȯ�ι�ư
		rankButton = new JButton(new ImageIcon("src/����Ȯ�ι�ư.png"));
		rankButton.addActionListener(new rankButton());
		frame.getContentPane().add(rankButton);
		
		//�����ư
		endButton = new JButton(new ImageIcon("src/�����ư.png"));
		endButton.addActionListener(new endButton());
		frame.getContentPane().add(endButton);
		
		//���׸�, ĳ����,�̻���
		firstBack = new PosImageIcon("src/����ȭ��.png",0,0,800,1000,0);	
		secondBack = new PosImageIcon("src/�����������.png",0,0,WIDTH,HEIGHT,0); //�ΰ��� ������ ������ �����ν� �̵��ϴ°�ó�� ����
		secondBack2 = new PosImageIcon("src/�����������.png",0,-1000,WIDTH,HEIGHT,0);
		thirdBack = new PosImageIcon("src/����° ���.png",0,0,WIDTH,HEIGHT,0);
		fourBack = new PosImageIcon("src/����.png",0,0,WIDTH,HEIGHT,0);
		clearBack = new PosImageIcon("src/Ŭ����ȭ��.png",0,0,WIDTH,HEIGHT,0);
		character = new PosImageIcon("src/ĳ����.png",320,700,150,150,0);
		finalCharacter = new PosImageIcon("src/������ĳ����.png",250,130,300,300,0);
		missile.add(new PosImageIcon("src/�̻���.PNG",character.pX+40,character.pY,80,80,10));
		
		//����
		for(int i=0; i<5; i++){
			monsterList1.add(new PosImageIcon("src/����1.png",155*i,-155,155,155,100));
			monsterList2.add(new PosImageIcon("src/����2.png",155*i,-155,155,155,150));
			monsterList3.add(new PosImageIcon("src/����3.png",155*i,-155,155,155,200));
		}
		//����
		for(int i=0; i<5000; i+=800){
			king.add(new PosImageIcon("src/����.png",1*i,-500,800,500,160000));
		} 
		for(int i=1; i<6; i++){
			kingAttack.add(new PosImageIcon("src/���հ���.png",100*i,-100,70,70,0));
			kingAttackRemove.add(new PosImageIcon("src/��.png",100*i,-100,50,50,0));
		} //���հ���
		kingGuage = new PosImageIcon("src/������.png",170,-140,king.get(0).hp/350 -20,30,0); //������ ������

		//�
		for(int i=0; i<meteorNums; i++){
			meteor.add(new PosImageIcon("src/�.png",(int)(Math.random()*600),-155,90,90,0));
			meteorWarning.add( new PosImageIcon("src/����.png",0,-1500,80,80,0));
			meteorWarningLine.add( new PosImageIcon("src/���.png",0,-1500,5,1000,0));
		}
		
		//Ÿ�̸� ��ġ
		monsterTimer = new Timer(MonsterDelay, new MonsterTimerListener());
		missileTimer = new Timer(MissileDelay, new MissileTimerListener());
		meteorTimer = new Timer(MonsterDelay, new MeteorTimerListener());
		meteorTimer2 = new Timer(MeteorDelay, new MeteorTimerListener2()); //8�ʿ� �ѹ��� ��� ������

		//�ʱ��г�
		firstPanel = new firstPanel();
		firstPanel.setBounds(0,0,800,1000);
		firstPanel.add(nameLabel);
		firstPanel.add(user);
		firstPanel.add(startButton);
		//�����г�
		gamePanel = new gamePanel();
		gamePanel.setBounds(0,0,800,1000);
		gamePanel.addKeyListener(new CharacterListener()); //Ű���� ������ ��ġ
		gamePanel.setFocusable(false);
		gamePanel.add(pauseButton);
		//���� �г�
		scorePanel = new scorePanel();
		scorePanel.setBounds(0,0,800,1000);
		scorePanel.add(rankButton);
		//���� �г�
		rankPanel = new rankPanel();
		rankPanel.setBounds(0,0,800,1000);
		rankPanel.add(endButton);
		
		lp.add(rankPanel, new Integer(0));
		lp.add(scorePanel, new Integer(1));
		lp.add(gamePanel, new Integer(2));
		lp.add(firstPanel, new Integer(3));
		
		//���⼳ġ
		try {
			StartSound = JApplet.newAudioClip(getClass().getResource(START_SOUND));	
			GameSound = JApplet.newAudioClip(getClass().getResource(GAME_SOUND));	
			meteorSound = JApplet.newAudioClip(getClass().getResource(METEOR_SOUND));
			missileSound = JApplet.newAudioClip(getClass().getResource(MISSILE_SOUND));
		}
		catch(Exception e){
			System.out.println("���� ���� �ε� ����");
		}
		frame.add(lp);
		frame.setSize(800,1000);
		frame.setVisible(true);
		StartSound.play(); //�������
	} 
	
	//ù��° �г�
	class firstPanel extends JPanel {
		public void paintComponent(Graphics g) {
			firstBack.draw(g); //ùȭ�� �׷���
			//���̵� �ޱ�
			nameLabel.setForeground(new Color(204,84,72));
			nameLabel.setFont(new Font("�޸ո���ü", Font.PLAIN,60));
			nameLabel.setBounds(220,700,200,60);
			user.setFont(new Font("�޸ո���ü", Font.BOLD,40));
			user.setBounds(420, 700, 200, 70);
			//��ư��ġ
			startButton.setBounds(300, 790, 220, 80);
		}
	}
	
	//���� �г�
	class gamePanel extends JPanel {
		public void paintComponent(Graphics g) {
			//�Ͻ�������ư
			pauseButton.setBackground(new Color(120,168,15)); //�Ͻ����� ��ư Ȱ��ȭ
			pauseButton.setBounds(330, 900, 65, 65);
			//���
			secondBack.draw(g); //�ι�° ȭ�� �׷���
			secondBack2.draw(g); //�ι�°ȭ�� �����̰� ���̴� ȭ��
			//�������
			g.setColor(Color.WHITE);
			g.setFont(new Font("�޸ո���ü", Font.BOLD,40));
			g.drawString(String.format("������� %,d",huntScore), 0, 40);
			//�Ÿ�����
			endTime = (new Date()).getTime();
			g.drawString(String.format("�Ÿ����� %,d CM",(int)(endTime - startTime)), 400, 40);
			//ĳ����
			character.draw(g);
			//����
			for(int i=0; i<nums; i++){
				if(monsterList1.get(i).hp<=0){	//�ǰ� �޸� �׷����� ����
				}
				else{
					//���Ϳ� ĳ���Ͱ� �ε��� �� ���� => ���ӳ�
					if(monsterList1.get(i).distance(character)<100) {
						finalHuntScore = huntScore; //������ ���� ���, �Ÿ������� �ð������� �ٲ��� repaint()���� �ٽ� �������Ƿ� ������־����
						finalDistanceScore = (int) (endTime - startTime);
						monsterTimer.stop(); //Ÿ�̸� , �Ҹ� ����
						meteorTimer.stop();
						meteorTimer2.stop();
						missileTimer.stop();
						missileSound.stop();
						GameSound.stop();
						meteorSound.stop();
						missileSound.stop();
						gamePanel.setFocusable(false); //Ű���嵵����
						lp.setLayer(scorePanel, 5);						// scorePanel �� ������ ������ ��
						scorePanel.setFocusable(true);					// scorePanel�� ��Ŀ�̵� �� �ְ� ��
						scorePanel.requestFocus();	// score�� ������(�̰� �ݵ�� �ʿ�)
				
					}
					//�ε����������� �׸�
					else {
						kingGuage.draw(g); //���� ������
						monsterList1.get(i).draw(g); //���� , ���� �׷���
					}
				}
			}
			//�̻��� �׷���
			for(int i=missileCnt2; i<missileCnt; i++){
				if(missile.get(i).pY < -100) missileCnt2++;
				for(int j=0; j<nums; j++){
					//�̻����� ���Ϳ� ������ ������ ü���� ����
					if(missile.get(i).distance(monsterList1.get(j))<distanceHP){
						monsterList1.get(j).hp -= missile.get(i).hp; //�̻����� ���Ϳ� ������ ���� ü���� ����
						if(monsterList1.get(j).hp == 0) huntScore += hfScore; //���� ������ ������� �ö�
						missile.get(i).pY -= 25; //�̻����� ���� �ö󰡰���
						missile.get(i).draw(g); //�̻����� �׷���
					}
					else{
						missileSound.play(); //�̻��� ���� ���
						missile.get(i).pY -= 5; //�̻����� �ö�
						missile.get(i).draw(g); //�̻����� �׷���
					}
				}
			}
			//�
			for(int i=0; i<meteorNums; i++){
				//��� ĳ���Ͱ� �ε��� �� ����
				if(meteor.get(i).distance(character)< meteorDamage) {
					finalHuntScore = huntScore; //������ ���� ���, �Ÿ������� �ð������� �ٲ��� repaint()���� �ٽ� �������Ƿ� ������־����
					finalDistanceScore = (int) (endTime - startTime); 
					monsterTimer.stop(); //Ÿ�̸� , �Ҹ� ����
					meteorTimer.stop();
					meteorTimer2.stop();
					missileTimer.stop();
					missileSound.stop();
					GameSound.stop();
					meteorSound.stop();
					missileSound.stop();
					frame.remove(pauseButton);
					gamePanel.setFocusable(false);
					lp.setLayer(scorePanel, 5);						// scorePanel �� ������ ������ ��
					scorePanel.setFocusable(true);					// scorePanel�� ��Ŀ�̵� �� �ְ� ��
					scorePanel.requestFocus();						// score�� ������(�̰� �ݵ�� �ʿ�)
				}
				//�ε����������� �׸�
				else {
					meteorWarningLine.get(i).draw(g); //��� �ʿ��� �̹����� �׸�
					meteorWarning.get(i).draw(g);
					meteor.get(i).draw(g);

				}
			}
			if(clear == 1){
				clearBack.draw(g);
				clear++;
				gamePanel.setFocusable(false); //Ű���嵵����
				lp.setLayer(scorePanel, 5);						// scorePanel �� ������ ������ ��
				scorePanel.setFocusable(true);					// scorePanel�� ��Ŀ�̵� �� �ְ� ��
				scorePanel.requestFocus();	// score�� ������(�̰� �ݵ�� �ʿ�)
			}
		}
	}
	
	//���� �г�
	class scorePanel extends JPanel {
		public void paintComponent(Graphics g) {
			if(clear == 2){
				try{
					Thread.sleep(1000);
				}
				catch(Exception ex){
					System.out.println("����");
				}
				StartSound.play(); //������ �����
				clear++;
			}
			else
				StartSound.play(); //������ �����
			thirdBack.draw(g); //���
			finalCharacter.draw(g); //ĳ����
			g.setColor(Color.WHITE); //���� ǥ��
			g.setFont(new Font("�޸ո���ü", Font.BOLD,50));
			g.drawString(String.format("��������    %,d",finalHuntScore + (int)finalDistanceScore), 180, 550);
			g.setColor(Color.DARK_GRAY);
			g.setFont(new Font("�޸ո���ü", Font.ITALIC,40));
			g.drawString(String.format("�Ÿ�����      %,d",(int)finalDistanceScore), 220, 610);
			g.drawString(String.format("�������      %,d",finalHuntScore), 215, 650);
			if(rankCheck == 0 ){
			//���� �ڷῡ�� �о���̰� ����Ʈ�� �߰��ؼ� ����
				try {
					file = new Scanner(new File("src/�������.txt"));
					while (file.hasNext()) {
						String line = file.nextLine();
						Scanner tokenGet = new Scanner(line);
						recordList.add(new Record(tokenGet.next(), Integer.valueOf(tokenGet.next())));
					}
					recordList.add(new Record(name, finalHuntScore + (int)finalDistanceScore));
				} catch (Exception ex) {
					System.out.println("������ ���µ� ������ ������ϴ�");
				}	   
				Collections.sort(recordList);
				//���������� ���� ���� ����
				FileWriter outFile;
				try {
					outFile = new FileWriter(new File("src/�������.txt"));
					for (Record r : recordList) {
						outFile.write(r.name + "    " + r.score + "\n");
					}
					outFile.close();
				} catch (Exception ex) {
					System.out.println("������ ���µ� ������ ������ϴ�.");
				}
			}
			rankCheck++;
			//��ư��ġ
			rankButton.setBounds(300, 790, 220, 80);
		}
	}
	//���� �г�
	class rankPanel extends JPanel {
		public void paintComponent(Graphics g) {
			fourBack.draw(g);		//ȭ���� �׷���		
			for(Record r : recordList){ //������ �׷���
				g.setColor(Color.WHITE);
				g.setFont(new Font("�޸ո���ü", Font.ITALIC,30));
				g.drawString(r.name,290,rankY1);
				g.setFont(new Font("�޸ո���ü", Font.BOLD,50));
				g.drawString(String.format("%,d",r.score), 400, rankY2);
				if(rankCnt < 2){ //3������ â�� ���ݴ� Ŀ��
					rankY1+=130;
					rankY2+=130;
				}
				else if(rankCnt <6){
					rankY1+=100;
					rankY2+=100;
				}
				else{ //8�� ���ķ� �ʿ�����Ƿ�
					rankY1+=300;
					rankY2+=300;
				}
				rankCnt++;
			}
			rankY1=98;
			rankY2=118;
			rankCnt=0;
			//��ư��ġ
			endButton.setBounds(280,830,220,80);
		}
	}
	//���۹�ư
	class startButton implements ActionListener{
		public void actionPerformed(ActionEvent event){
			if(user.getText().equals(""))
				name = "����"; //�̸��� ������ ���߿� ����� �о���ϼ� �����Ƿ� �Է¾��� �ÿ� ���� ��
			else
				name = user.getText(); //�̸�����
			StartSound.stop(); //�Ҹ��ٲ�
			GameSound.play();
			lp.setLayer(gamePanel, 4);						// gamePanel �� ������ ������ ��
			gamePanel.setFocusable(true);					// gamePanel�� ��Ŀ�̵� �� �ְ� ��
			gamePanel.requestFocus();						// ��Ŀ���� ������(�̰� �ݵ�� �ʿ�)
			startTime = (new Date()).getTime(); //�Ÿ� ���� ����
			monsterTimer.start(); //Ÿ�̸� �۵�
			missileTimer.start();
			meteorTimer2.start();
		}
	}
	//�Ͻ�������ư
	class pauseButton implements ActionListener{
		public void actionPerformed(ActionEvent event){
			if(pauseCnt % 2 ==0){
				monsterTimer.stop(); //Ÿ�̸� ����
				missileTimer.stop();
				meteorTimer.stop();
				meteorTimer2.stop();
				missileSound.stop(); //�Ҹ�����
				GameSound.stop();
				pauseTime =  (new Date()).getTime(); //�Ͻ������� �Ÿ����������°��� ���־���� 
				pauseButton.setIcon(new ImageIcon("src/�Ͻ��������۹�ư.png"));
			}
			else{ //���� �ݴ�
				pauseButton.setIcon(new ImageIcon("src/�Ͻ�������ư.png"));
				monsterTimer.start(); //Ÿ�̸� ����
				missileTimer.start();
				meteorTimer.start();
				meteorTimer2.start();
				GameSound.play();//�Ҹ�����
				endTime = (new Date()).getTime();
				startTime += endTime - pauseTime; //������ �ɸ���ŭ�� ������

				gamePanel.setFocusable(true); //Ű���� �۵�					
				gamePanel.requestFocus();
			}
			pauseCnt++;
		}
	}
	//������ư
	class rankButton implements ActionListener{
		public void actionPerformed(ActionEvent event){
			lp.setLayer(rankPanel, 6);						// scorePanel �� ������ ������ ��
			rankPanel.setFocusable(true);					// scorePanel�� ��Ŀ�̵� �� �ְ� ��
			rankPanel.requestFocus();	// score�� ������(�̰� �ݵ�� �ʿ�)
		}
	}

	//���� ��ư
	class endButton implements ActionListener{
		public void actionPerformed(ActionEvent event){
			System.exit(0);
		}
	}
	//����,��� Ÿ�Ӹ�����
	class MonsterTimerListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			//�ð��� ���� ���� ���Ͱ� �׷����� �ٽ� ���� ���� 800������ ����
			if((int)(endTime - startTime) < 20000){
				for(int i=0; i<nums; i++){
					if(monsterList1.get(i).pY > 800){
						monsterList1.get(i).pY = -105; //���� ����
						monsterList1.get(i).hp = hfHp; //hp ����
						hf.hardAndFast();; //�ð��� ���� �� ���� �����ϰ� �ӵ��� ������ ���µǰ� ���;� �ϴϱ� ����� �־���

					}
					else monsterList1.get(i).pY += hfFast; //hfFast��ŭ �̵�
				}
			}
			else if((int)(endTime - startTime) > 20000){ //��������������
				hf.hardAndFast(); //�����ҷ���
				kingGuage.width = monsterList1.get(0).hp / 350; //������ �⵵��
				if(monsterList1.get(0).pY < 0){
					monsterList1.get(0).pY += 1; //������ ������� ��ġ������ ������
					if(monsterList1.get(0).pY == 0){
						distanceHP = 800; //������ġ�� 0,0�̶� �̻��� ������ ������� �÷������
					}
					kingGuage.pY += 1; //����������������
				}
				else if(monsterList1.get(0).hp < 0){ //���� ���̸� ������� x2 �ǰ� ����
					clear = 1;
					finalHuntScore = huntScore*2; //������ ���� ���, �Ÿ������� �ð������� �ٲ��� repaint()���� �ٽ� �������Ƿ� ������־����
					finalDistanceScore = (int) (endTime - startTime);
					monsterTimer.stop(); //Ÿ�̸� , �Ҹ� ����
					meteorTimer.stop();
					meteorTimer2.stop();
					missileSound.stop();
					GameSound.stop();
					frame.remove(pauseButton);
					gamePanel.repaint();
				}
			}
			//����� �����̰� ����
			if(secondBack.pY <= 1000) 
				secondBack.pY +=3; //ó�� ����� �����̰���
			else
				secondBack.pY =-990; //ó������� �� �������� �ٽ� ����
			if(secondBack2.pY <= 1000) 
				secondBack2.pY +=3; //�ι�° ����� �����̰���
			else
				secondBack2.pY =-990; //�ι�°����� �� �������� �ٽ� ����
			frame.repaint();
			//ĳ���Ͱ� �����̰�����
			if(right){
				if(character.pX<710)
					character.move(3, 0);
				else
					right = false;
			}
			else if(left){
				if(character.pX>-90)
					character.move(-3, 0);
				else
					left = false;
			}
		}
	}
	//�̻���Ÿ�Ӹ�����
	class MissileTimerListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			missile.add(new PosImageIcon("src/�̻���.PNG",character.pX+40,character.pY,80,80,10)); //���������� �̻����� �߰�����
			missileCnt++; //�̻����� �߰��ȸ�ŭ �� ���°��� ������Ų��
			frame.repaint();
		}
	}
	//�Ÿ�Ӹ�����
	class MeteorTimerListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			for(int i=0; i<meteorNums; i++){
				if(meteor.get(i).pY > 1000){ //��� ȭ���� �����
					meteorWarning.get(i).pY = 1500; //�������
					meteorWarningLine.get(i).pY = 1500; //������� ����
					meteorTimer.stop(); //��� �ٶ������� ��ž�ϰ� 6�ʵڿ� ����
				}
				else meteor.get(i).pY += hfFast; //��� ȭ��ʿ� ������ hfFast��ŭ �̵�
			}
		}
	}
	//��� 6�ʿ� �ѹ� �������� ����
	class MeteorTimerListener2 implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			meteorNums = (int)(Math.random()*3+2); //����� ����
			for(int i=0; i<meteorNums; i++){
				meteor.get(i).pY = -105; //���ġ ����
				meteorWarning.get(i).pY = 50; //���� ����
				meteorWarningLine.get(i).pY = 0; //������� ����
				meteor.get(i).pX = (int)(Math.random()*800); //� ��ġ ��������
				meteorWarning.get(i).pX = meteor.get(i).pX; //����� ��̶� ��ġ ������
				meteorWarningLine.get(i).pX = meteor.get(i).pX+35; //��������̶� ��̶� ��ġ������
			}
			meteor.get(0).pX = character.pX; //ù��° ��� ĳ���Ϳ��� �������� �ؼ� ������ �ִ°��� ����
			meteorWarning.get(0).pX = meteor.get(0).pX;
			meteorWarningLine.get(0).pX = meteor.get(0).pX+35;
			meteorTimer.start(); //�Ÿ�̸Ӹ� �ٽ� �۵���Ŵ
			meteorSound.play(); //��Ҹ��� �����Ŵ
		}
	}
	//�ð��� ������ ���� ���Ͱ� �� ���� ���� ����, �� ������
	class HardAndFast{
		public void hardAndFast(){
			if((int)(endTime - startTime) >= 7000 && (int)(endTime - startTime) <= 12000){
				monsterList1 = monsterList2; //���� �̹��� �ٲ�
				hfHp = 300; //���� ü�� ������
				hfScore = 1600; //�� ���� �׿��� �� �� ���� ���� ����
				hfFast = 5; //���� �ӵ� ������
			}
			else if((int)(endTime - startTime) >= 12000 && (int)(endTime - startTime) < 20000){
				monsterList1 = monsterList3; //���� ����
				hfHp = 450;
				hfScore = 2400;
				hfFast = 6;
			}
			else if((int)(endTime - startTime) >= 20000){
				if((int)(endTime - startTime) < 21000){
					//���Ϳ� ������ ���� ������ �ϱ⶧���� �̹����� ��ü����
					monsterList1 = king; //�����̹����� �������� �ٲ�

					//��� ���հ����� ���� ������ �ϱ� ������ �̹�����ü, ������ �����
					meteor = kingAttack; //���׿��͵��� ���� �������� ���
					meteorWarning = kingAttackRemove; //���׿� ��� ����
					meteorWarningLine = kingAttackRemove; //���׿� �������� ����
					hfFast = 8; //�������� ��
					//meteorDamage = 24; //� ���� ���հ����� �� �����Ƿ� ������ �ٿ���
					meteorTimer.setDelay(1); //���հ����� �� ������ ����������
					meteorTimer2.setDelay(2000); //���հ����� �ֱⰡ �� ª����
				}
			}
		}
	}
	//ĳ���� Ű����(ĳ���͸� �����̰� �ϴ� Ű���� ������)
	private class CharacterListener implements KeyListener {
		public void keyPressed (KeyEvent event) {
			switch (event.getKeyCode()){ 
			case KeyEvent.VK_LEFT:
					left = true;
				break;
			case KeyEvent.VK_RIGHT:
					right = true;
				break;
			}
			gamePanel.repaint();
		}
		public void keyTyped (KeyEvent event) {}
		public void keyReleased (KeyEvent event) {
			switch (event.getKeyCode()){ 
			case KeyEvent.VK_LEFT:
				left = false;
				break;
			case KeyEvent.VK_RIGHT:
				right = false;
				break;
			}
			gamePanel.repaint();
		}
	}
}
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
	JLabel nameLabel = new JLabel("이름입력"); //이름입력 라벨
	JLayeredPane lp = new JLayeredPane();// 화면을 여러장 겹치기 위한 PaneL 레이어
	JTextField user = new JTextField(); // 이름 입력 받을 텍스트필드 선언
	JButton startButton,pauseButton,rankButton, endButton; //시작,일시정지 버튼
	boolean right=false, left=false;
	PosImageIcon character, finalCharacter; //메인 캐릭터
	PosImageIcon firstBack,secondBack,secondBack2,thirdBack,fourBack,clearBack; //배경화면
	String name = " ";    //이름
	int finalHuntScore, finalDistanceScore; //최종점수기록
	Timer monsterTimer,missileTimer, meteorTimer,meteorTimer2;//각각 몬스터, 미사일, 운석 타이머
	JLabel label; //이름입력 라벨
	AudioClip StartSound, GameSound, meteorSound, missileSound; //오디오
	HardAndFast hf = new HardAndFast(); //시간이 지남에 따라 난이도 높아지는 메소드
	int hfHp = 100, hfScore = 800, hfFast = 3; //처음 몬스터 피, 처음 몬스터 죽일 때 점수, 처음 몬스터 속도
	PosImageIcon kingGuage; //왕의 게이지
	
	//score들
	int huntScore = 0; //처음 사냥 점수
	int distanceScore = 0; //처음 거리 점수
	double startTime , endTime , pauseTime; //거리 점수를 위한 인수
	
	//몬스터 리스트, 미사일, 운석
	ArrayList<PosImageIcon> monsterList1 = new ArrayList<PosImageIcon>(); //몬스터
	ArrayList<PosImageIcon> monsterList2 = new ArrayList<PosImageIcon>();
	ArrayList<PosImageIcon> monsterList3 = new ArrayList<PosImageIcon>();
	ArrayList<PosImageIcon> missile = new ArrayList<PosImageIcon>(); //미사일
	ArrayList<PosImageIcon> meteor = new ArrayList<PosImageIcon>(); //운석
	ArrayList<PosImageIcon> meteorWarning = new ArrayList<PosImageIcon>(); //운석 경고
	ArrayList<PosImageIcon> meteorWarningLine = new ArrayList<PosImageIcon>(); //운석 라인
	ArrayList<PosImageIcon> king = new ArrayList<PosImageIcon>(); //마왕
	ArrayList<PosImageIcon> kingAttack = new ArrayList<PosImageIcon>(); //마왕공격이미지
	ArrayList<PosImageIcon> kingAttackRemove = new ArrayList<PosImageIcon>(); //마왕공격을 운석으로 쓰기 때문에 경고를 없애줌
	
	//음향리스트
	private final String START_SOUND = "/res/시작음악.wav";
	private final String GAME_SOUND = "/res/게임음악.wav";
	private final String METEOR_SOUND = "/res/운석경고.wav";
	private final String MISSILE_SOUND = "/res/미사일소리.wav";
	
	int clear=0;
	int MeteorDelay = 6000, meteorDamage = 30; //운석딜레이
	int distanceHP = 80; //때리는 범위
	int pauseCnt = 0; //짝수일때 정지 홀수일때 다시시작
	int meteorNums = 5; //메테오 숫자
	int nums = 5; //몬스터 수
	int STEP = 20; //캐릭터 움직일 포인트
	int missileCnt = 1,missileCnt2 = 0; //미사일 그려주는 것
	int MonsterDelay = 7, MissileDelay =20; //몬스터와 미사일의 딜레이
	private int WIDTH=800, HEIGHT=1000; //창의 크기
	
	//순위
	Scanner file; //파일 입출력을 위한 것
	ArrayList<Record> recordList = new ArrayList<Record>(); //기록 리스트
	int rankY1=98, rankY2=118; //순위 표시 이름, 점수 Y좌표
	int rankCnt = 0;
	int rankCheck = 0;

	public static void main (String[] args) {
		GamePlay gui = new GamePlay();
		gui.go();
	}
	
	public void go() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(400, 0);
		
		//시작 버튼
		user.addActionListener(new startButton());
		startButton = new JButton(new ImageIcon("src/시작버튼.png"));
		startButton.addActionListener(new startButton());
		frame.getContentPane().add(startButton);
		//일시정지버튼
		pauseButton = new JButton(new ImageIcon("src/일시정지버튼.png"));
		pauseButton.addActionListener(new pauseButton());
		frame.getContentPane().add(pauseButton);
		//순위확인버튼
		rankButton = new JButton(new ImageIcon("src/순위확인버튼.png"));
		rankButton.addActionListener(new rankButton());
		frame.getContentPane().add(rankButton);
		
		//종료버튼
		endButton = new JButton(new ImageIcon("src/종료버튼.png"));
		endButton.addActionListener(new endButton());
		frame.getContentPane().add(endButton);
		
		//배경그림, 캐릭터,미사일
		firstBack = new PosImageIcon("src/시작화면.png",0,0,800,1000,0);	
		secondBack = new PosImageIcon("src/스테이지배경.png",0,0,WIDTH,HEIGHT,0); //두개가 번갈아 나오게 함으로써 이동하는것처럼 보임
		secondBack2 = new PosImageIcon("src/스테이지배경.png",0,-1000,WIDTH,HEIGHT,0);
		thirdBack = new PosImageIcon("src/세번째 배경.png",0,0,WIDTH,HEIGHT,0);
		fourBack = new PosImageIcon("src/순위.png",0,0,WIDTH,HEIGHT,0);
		clearBack = new PosImageIcon("src/클리어화면.png",0,0,WIDTH,HEIGHT,0);
		character = new PosImageIcon("src/캐릭터.png",320,700,150,150,0);
		finalCharacter = new PosImageIcon("src/마지막캐릭터.png",250,130,300,300,0);
		missile.add(new PosImageIcon("src/미사일.PNG",character.pX+40,character.pY,80,80,10));
		
		//몬스터
		for(int i=0; i<5; i++){
			monsterList1.add(new PosImageIcon("src/몬스터1.png",155*i,-155,155,155,100));
			monsterList2.add(new PosImageIcon("src/몬스터2.png",155*i,-155,155,155,150));
			monsterList3.add(new PosImageIcon("src/몬스터3.png",155*i,-155,155,155,200));
		}
		//마왕
		for(int i=0; i<5000; i+=800){
			king.add(new PosImageIcon("src/마왕.png",1*i,-500,800,500,160000));
		} 
		for(int i=1; i<6; i++){
			kingAttack.add(new PosImageIcon("src/마왕공격.png",100*i,-100,70,70,0));
			kingAttackRemove.add(new PosImageIcon("src/무.png",100*i,-100,50,50,0));
		} //마왕공격
		kingGuage = new PosImageIcon("src/게이지.png",170,-140,king.get(0).hp/350 -20,30,0); //보스의 게이지

		//운석
		for(int i=0; i<meteorNums; i++){
			meteor.add(new PosImageIcon("src/운석.png",(int)(Math.random()*600),-155,90,90,0));
			meteorWarning.add( new PosImageIcon("src/운석경고.png",0,-1500,80,80,0));
			meteorWarningLine.add( new PosImageIcon("src/경고.png",0,-1500,5,1000,0));
		}
		
		//타이머 설치
		monsterTimer = new Timer(MonsterDelay, new MonsterTimerListener());
		missileTimer = new Timer(MissileDelay, new MissileTimerListener());
		meteorTimer = new Timer(MonsterDelay, new MeteorTimerListener());
		meteorTimer2 = new Timer(MeteorDelay, new MeteorTimerListener2()); //8초에 한번씩 운석이 떨어짐

		//초기패널
		firstPanel = new firstPanel();
		firstPanel.setBounds(0,0,800,1000);
		firstPanel.add(nameLabel);
		firstPanel.add(user);
		firstPanel.add(startButton);
		//게임패널
		gamePanel = new gamePanel();
		gamePanel.setBounds(0,0,800,1000);
		gamePanel.addKeyListener(new CharacterListener()); //키보드 리스너 설치
		gamePanel.setFocusable(false);
		gamePanel.add(pauseButton);
		//점수 패널
		scorePanel = new scorePanel();
		scorePanel.setBounds(0,0,800,1000);
		scorePanel.add(rankButton);
		//순위 패널
		rankPanel = new rankPanel();
		rankPanel.setBounds(0,0,800,1000);
		rankPanel.add(endButton);
		
		lp.add(rankPanel, new Integer(0));
		lp.add(scorePanel, new Integer(1));
		lp.add(gamePanel, new Integer(2));
		lp.add(firstPanel, new Integer(3));
		
		//음향설치
		try {
			StartSound = JApplet.newAudioClip(getClass().getResource(START_SOUND));	
			GameSound = JApplet.newAudioClip(getClass().getResource(GAME_SOUND));	
			meteorSound = JApplet.newAudioClip(getClass().getResource(METEOR_SOUND));
			missileSound = JApplet.newAudioClip(getClass().getResource(MISSILE_SOUND));
		}
		catch(Exception e){
			System.out.println("음향 파일 로딩 실패");
		}
		frame.add(lp);
		frame.setSize(800,1000);
		frame.setVisible(true);
		StartSound.play(); //음악재생
	} 
	
	//첫번째 패널
	class firstPanel extends JPanel {
		public void paintComponent(Graphics g) {
			firstBack.draw(g); //첫화면 그려줌
			//아이디 받기
			nameLabel.setForeground(new Color(204,84,72));
			nameLabel.setFont(new Font("휴먼매직체", Font.PLAIN,60));
			nameLabel.setBounds(220,700,200,60);
			user.setFont(new Font("휴먼매직체", Font.BOLD,40));
			user.setBounds(420, 700, 200, 70);
			//버튼위치
			startButton.setBounds(300, 790, 220, 80);
		}
	}
	
	//게임 패널
	class gamePanel extends JPanel {
		public void paintComponent(Graphics g) {
			//일시정지버튼
			pauseButton.setBackground(new Color(120,168,15)); //일시정지 버튼 활성화
			pauseButton.setBounds(330, 900, 65, 65);
			//배경
			secondBack.draw(g); //두번째 화면 그려줌
			secondBack2.draw(g); //두번째화면 움직이게 보이는 화면
			//사냥점수
			g.setColor(Color.WHITE);
			g.setFont(new Font("휴먼매직체", Font.BOLD,40));
			g.drawString(String.format("사냥점수 %,d",huntScore), 0, 40);
			//거리점수
			endTime = (new Date()).getTime();
			g.drawString(String.format("거리점수 %,d CM",(int)(endTime - startTime)), 400, 40);
			//캐릭터
			character.draw(g);
			//몬스터
			for(int i=0; i<nums; i++){
				if(monsterList1.get(i).hp<=0){	//피가 달면 그려주지 않음
				}
				else{
					//몬스터와 캐릭터가 부딪힐 때 멈춤 => 게임끝
					if(monsterList1.get(i).distance(character)<100) {
						finalHuntScore = huntScore; //마지막 점수 기록, 거리점수는 시간에따라 바껴서 repaint()마다 다시 적어지므로 기록해주어야함
						finalDistanceScore = (int) (endTime - startTime);
						monsterTimer.stop(); //타이머 , 소리 멈춤
						meteorTimer.stop();
						meteorTimer2.stop();
						missileTimer.stop();
						missileSound.stop();
						GameSound.stop();
						meteorSound.stop();
						missileSound.stop();
						gamePanel.setFocusable(false); //키보드도멈춤
						lp.setLayer(scorePanel, 5);						// scorePanel 이 앞으로 나오게 함
						scorePanel.setFocusable(true);					// scorePanel이 포커싱될 수 있게 함
						scorePanel.requestFocus();	// score을 맞춰줌(이것 반드시 필요)
				
					}
					//부딪히지않으면 그림
					else {
						kingGuage.draw(g); //왕의 게이지
						monsterList1.get(i).draw(g); //몬스터 , 마왕 그려줌
					}
				}
			}
			//미사일 그려줌
			for(int i=missileCnt2; i<missileCnt; i++){
				if(missile.get(i).pY < -100) missileCnt2++;
				for(int j=0; j<nums; j++){
					//미사일이 몬스터에 닿으면 몬스터의 체력이 닳음
					if(missile.get(i).distance(monsterList1.get(j))<distanceHP){
						monsterList1.get(j).hp -= missile.get(i).hp; //미사일이 몬스터에 닿으면 몬스터 체력을 깎음
						if(monsterList1.get(j).hp == 0) huntScore += hfScore; //몬스터 죽을때 사냥점수 올라감
						missile.get(i).pY -= 25; //미사일이 위로 올라가게함
						missile.get(i).draw(g); //미사일을 그려줌
					}
					else{
						missileSound.play(); //미사일 사운드 재생
						missile.get(i).pY -= 5; //미사일이 올라감
						missile.get(i).draw(g); //미사을을 그려줌
					}
				}
			}
			//운석
			for(int i=0; i<meteorNums; i++){
				//운석과 캐릭터가 부딪힐 때 멈춤
				if(meteor.get(i).distance(character)< meteorDamage) {
					finalHuntScore = huntScore; //마지막 점수 기록, 거리점수는 시간에따라 바껴서 repaint()마다 다시 적어지므로 기록해주어야함
					finalDistanceScore = (int) (endTime - startTime); 
					monsterTimer.stop(); //타이머 , 소리 멈춤
					meteorTimer.stop();
					meteorTimer2.stop();
					missileTimer.stop();
					missileSound.stop();
					GameSound.stop();
					meteorSound.stop();
					missileSound.stop();
					frame.remove(pauseButton);
					gamePanel.setFocusable(false);
					lp.setLayer(scorePanel, 5);						// scorePanel 이 앞으로 나오게 함
					scorePanel.setFocusable(true);					// scorePanel이 포커싱될 수 있게 함
					scorePanel.requestFocus();						// score을 맞춰줌(이것 반드시 필요)
				}
				//부딪히지않으면 그림
				else {
					meteorWarningLine.get(i).draw(g); //운석에 필요한 이미지를 그림
					meteorWarning.get(i).draw(g);
					meteor.get(i).draw(g);

				}
			}
			if(clear == 1){
				clearBack.draw(g);
				clear++;
				gamePanel.setFocusable(false); //키보드도멈춤
				lp.setLayer(scorePanel, 5);						// scorePanel 이 앞으로 나오게 함
				scorePanel.setFocusable(true);					// scorePanel이 포커싱될 수 있게 함
				scorePanel.requestFocus();	// score을 맞춰줌(이것 반드시 필요)
			}
		}
	}
	
	//점수 패널
	class scorePanel extends JPanel {
		public void paintComponent(Graphics g) {
			if(clear == 2){
				try{
					Thread.sleep(1000);
				}
				catch(Exception ex){
					System.out.println("오류");
				}
				StartSound.play(); //음악을 깔아줌
				clear++;
			}
			else
				StartSound.play(); //음악을 깔아줌
			thirdBack.draw(g); //배경
			finalCharacter.draw(g); //캐릭터
			g.setColor(Color.WHITE); //점수 표시
			g.setFont(new Font("휴먼매직체", Font.BOLD,50));
			g.drawString(String.format("종합점수    %,d",finalHuntScore + (int)finalDistanceScore), 180, 550);
			g.setColor(Color.DARK_GRAY);
			g.setFont(new Font("휴먼매직체", Font.ITALIC,40));
			g.drawString(String.format("거리점수      %,d",(int)finalDistanceScore), 220, 610);
			g.drawString(String.format("사냥점수      %,d",finalHuntScore), 215, 650);
			if(rankCheck == 0 ){
			//원래 자료에서 읽어들이고 리스트에 추가해서 정렬
				try {
					file = new Scanner(new File("src/순위기록.txt"));
					while (file.hasNext()) {
						String line = file.nextLine();
						Scanner tokenGet = new Scanner(line);
						recordList.add(new Record(tokenGet.next(), Integer.valueOf(tokenGet.next())));
					}
					recordList.add(new Record(name, finalHuntScore + (int)finalDistanceScore));
				} catch (Exception ex) {
					System.out.println("파일을 여는데 문제가 생겼습니다");
				}	   
				Collections.sort(recordList);
				//순위정렬한 것을 새로 써줌
				FileWriter outFile;
				try {
					outFile = new FileWriter(new File("src/순위기록.txt"));
					for (Record r : recordList) {
						outFile.write(r.name + "    " + r.score + "\n");
					}
					outFile.close();
				} catch (Exception ex) {
					System.out.println("파일을 여는데 문제가 생겼습니다.");
				}
			}
			rankCheck++;
			//버튼위치
			rankButton.setBounds(300, 790, 220, 80);
		}
	}
	//순위 패널
	class rankPanel extends JPanel {
		public void paintComponent(Graphics g) {
			fourBack.draw(g);		//화면을 그려줌		
			for(Record r : recordList){ //점수를 그려줌
				g.setColor(Color.WHITE);
				g.setFont(new Font("휴먼매직체", Font.ITALIC,30));
				g.drawString(r.name,290,rankY1);
				g.setFont(new Font("휴먼매직체", Font.BOLD,50));
				g.drawString(String.format("%,d",r.score), 400, rankY2);
				if(rankCnt < 2){ //3위까진 창이 조금더 커서
					rankY1+=130;
					rankY2+=130;
				}
				else if(rankCnt <6){
					rankY1+=100;
					rankY2+=100;
				}
				else{ //8위 이후론 필요없으므로
					rankY1+=300;
					rankY2+=300;
				}
				rankCnt++;
			}
			rankY1=98;
			rankY2=118;
			rankCnt=0;
			//버튼위치
			endButton.setBounds(280,830,220,80);
		}
	}
	//시작버튼
	class startButton implements ActionListener{
		public void actionPerformed(ActionEvent event){
			if(user.getText().equals(""))
				name = "무명"; //이름이 없으면 나중에 기록을 읽어들일수 없으므로 입력안할 시에 값을 줌
			else
				name = user.getText(); //이름받음
			StartSound.stop(); //소리바꿈
			GameSound.play();
			lp.setLayer(gamePanel, 4);						// gamePanel 이 앞으로 나오게 함
			gamePanel.setFocusable(true);					// gamePanel이 포커싱될 수 있게 함
			gamePanel.requestFocus();						// 포커싱을 맞춰줌(이것 반드시 필요)
			startTime = (new Date()).getTime(); //거리 점수 시작
			monsterTimer.start(); //타이머 작동
			missileTimer.start();
			meteorTimer2.start();
		}
	}
	//일시정지버튼
	class pauseButton implements ActionListener{
		public void actionPerformed(ActionEvent event){
			if(pauseCnt % 2 ==0){
				monsterTimer.stop(); //타이머 정지
				missileTimer.stop();
				meteorTimer.stop();
				meteorTimer2.stop();
				missileSound.stop(); //소리정지
				GameSound.stop();
				pauseTime =  (new Date()).getTime(); //일시정지때 거리점수오르는것을 빼주어야함 
				pauseButton.setIcon(new ImageIcon("src/일시정지시작버튼.png"));
			}
			else{ //위와 반대
				pauseButton.setIcon(new ImageIcon("src/일시정지버튼.png"));
				monsterTimer.start(); //타이머 시작
				missileTimer.start();
				meteorTimer.start();
				meteorTimer2.start();
				GameSound.play();//소리시작
				endTime = (new Date()).getTime();
				startTime += endTime - pauseTime; //정지시 걸린만큼을 더해줌

				gamePanel.setFocusable(true); //키보드 작동					
				gamePanel.requestFocus();
			}
			pauseCnt++;
		}
	}
	//순위버튼
	class rankButton implements ActionListener{
		public void actionPerformed(ActionEvent event){
			lp.setLayer(rankPanel, 6);						// scorePanel 이 앞으로 나오게 함
			rankPanel.setFocusable(true);					// scorePanel이 포커싱될 수 있게 함
			rankPanel.requestFocus();	// score을 맞춰줌(이것 반드시 필요)
		}
	}

	//종료 버튼
	class endButton implements ActionListener{
		public void actionPerformed(ActionEvent event){
			System.exit(0);
		}
	}
	//몬스터,배경 타임리스너
	class MonsterTimerListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			//시간이 감에 따라 몬스터가 그려지고 다시 가기 위해 800넘으면 리셋
			if((int)(endTime - startTime) < 20000){
				for(int i=0; i<nums; i++){
					if(monsterList1.get(i).pY > 800){
						monsterList1.get(i).pY = -105; //위로 리셋
						monsterList1.get(i).hp = hfHp; //hp 리셋
						hf.hardAndFast();; //시간에 따라 쎈 몬스터 출현하고 속도도 빨라짐 리셋되고 나와야 하니까 여기다 넣어줌

					}
					else monsterList1.get(i).pY += hfFast; //hfFast만큼 이동
				}
			}
			else if((int)(endTime - startTime) > 20000){ //마지막보스출현
				hf.hardAndFast(); //보스불러줌
				kingGuage.width = monsterList1.get(0).hp / 350; //게이지 닳도록
				if(monsterList1.get(0).pY < 0){
					monsterList1.get(0).pY += 1; //마왕이 어느정도 위치까지만 내려움
					if(monsterList1.get(0).pY == 0){
						distanceHP = 800; //마왕위치가 0,0이라서 미사일 범위를 어느정도 늘려줘야함
					}
					kingGuage.pY += 1; //게이지내려오게함
				}
				else if(monsterList1.get(0).hp < 0){ //마왕 죽이면 사냥점수 x2 되고 끝남
					clear = 1;
					finalHuntScore = huntScore*2; //마지막 점수 기록, 거리점수는 시간에따라 바껴서 repaint()마다 다시 적어지므로 기록해주어야함
					finalDistanceScore = (int) (endTime - startTime);
					monsterTimer.stop(); //타이머 , 소리 멈춤
					meteorTimer.stop();
					meteorTimer2.stop();
					missileSound.stop();
					GameSound.stop();
					frame.remove(pauseButton);
					gamePanel.repaint();
				}
			}
			//배경이 움직이게 해줌
			if(secondBack.pY <= 1000) 
				secondBack.pY +=3; //처음 배경이 움직이게함
			else
				secondBack.pY =-990; //처음배경이 다 지나가면 다시 리셋
			if(secondBack2.pY <= 1000) 
				secondBack2.pY +=3; //두번째 배경이 움직이게함
			else
				secondBack2.pY =-990; //두번째배경이 다 지나가면 다시 리셋
			frame.repaint();
			//캐릭터가 움직이게해줌
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
	//미사일타임리스너
	class MissileTimerListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			missile.add(new PosImageIcon("src/미사일.PNG",character.pX+40,character.pY,80,80,10)); //지속적으로 미사일을 추가해줌
			missileCnt++; //미사일이 추가된만큼 수 세는것을 증가시킨다
			frame.repaint();
		}
	}
	//운석타임리스너
	class MeteorTimerListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			for(int i=0; i<meteorNums; i++){
				if(meteor.get(i).pY > 1000){ //운석이 화면을 벗어나면
					meteorWarning.get(i).pY = 1500; //운석경고없앰
					meteorWarningLine.get(i).pY = 1500; //운석경고라인 없앰
					meteorTimer.stop(); //운석이 다떨어지면 스탑하고 6초뒤에 실행
				}
				else meteor.get(i).pY += hfFast; //운석이 화면않에 있을때 hfFast만큼 이동
			}
		}
	}
	//운석이 6초에 한번 떨어지게 해줌
	class MeteorTimerListener2 implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			meteorNums = (int)(Math.random()*3+2); //운석개수 랜덤
			for(int i=0; i<meteorNums; i++){
				meteor.get(i).pY = -105; //운석위치 리셋
				meteorWarning.get(i).pY = 50; //운석경고 리셋
				meteorWarningLine.get(i).pY = 0; //운석경고라인 리셋
				meteor.get(i).pX = (int)(Math.random()*800); //운석 위치 랜덤배정
				meteorWarning.get(i).pX = meteor.get(i).pX; //운석경고랑 운석이랑 위치 같게함
				meteorWarningLine.get(i).pX = meteor.get(i).pX+35; //운석경고라인이랑 운석이랑 위치같게함
			}
			meteor.get(0).pX = character.pX; //첫번째 운석은 캐릭터에게 떨어지게 해서 가만히 있는것을 방지
			meteorWarning.get(0).pX = meteor.get(0).pX;
			meteorWarningLine.get(0).pX = meteor.get(0).pX+35;
			meteorTimer.start(); //운석타이머를 다시 작동시킴
			meteorSound.play(); //운석소리를 재생시킴
		}
	}
	//시간이 지남에 따라 몬스터가 더 강한 몬스터 출현, 더 빨라짐
	class HardAndFast{
		public void hardAndFast(){
			if((int)(endTime - startTime) >= 7000 && (int)(endTime - startTime) <= 12000){
				monsterList1 = monsterList2; //몬스터 이미지 바꿈
				hfHp = 300; //몬스터 체력 높아짐
				hfScore = 1600; //그 몬스터 죽엿을 때 더 많은 점수 얻음
				hfFast = 5; //몬스터 속도 높아짐
			}
			else if((int)(endTime - startTime) >= 12000 && (int)(endTime - startTime) < 20000){
				monsterList1 = monsterList3; //위와 같음
				hfHp = 450;
				hfScore = 2400;
				hfFast = 6;
			}
			else if((int)(endTime - startTime) >= 20000){
				if((int)(endTime - startTime) < 21000){
					//몬스터와 마왕이 같은 역할을 하기때문에 이미지를 대체해줌
					monsterList1 = king; //몬스터이미지를 마왕으로 바꿈

					//운석과 마왕공격이 같은 역할을 하기 때문에 이미지대체, 공격을 향상함
					meteor = kingAttack; //메테오것들을 마왕 공격으로 사용
					meteorWarning = kingAttackRemove; //메테오 경고를 없앰
					meteorWarningLine = kingAttackRemove; //메테오 경고라인을 없앰
					hfFast = 8; //더빠르게 함
					//meteorDamage = 24; //운석 보다 마왕공격이 더 작으므로 범위를 줄여줌
					meteorTimer.setDelay(1); //마왕공격이 더 빠르게 내려오게함
					meteorTimer2.setDelay(2000); //마왕공격의 주기가 더 짧아짐
				}
			}
		}
	}
	//캐릭터 키보드(캐릭터를 움직이게 하는 키보드 리스너)
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
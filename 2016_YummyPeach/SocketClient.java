import java.awt.*;
import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.util.Collections;
import java.util.Vector;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class SocketClient {
	//소켓
	PrintWriter writer;
	Socket sock;
	Scanner scan = new Scanner(System.in);
	InputStream is = null;
	InputStreamReader isr = null;
	BufferedReader br = null;

	//프레임
	JFrame frame = new JFrame("Embeded Project");
	JPanel firstPanel, secondPanel, thirdPanel;
	JLayeredPane lp = new JLayeredPane();

	PosImageIcon firstBack, gameBack, character;

	ArrayList<PosImageIcon> monster = new ArrayList<PosImageIcon>();
	ArrayList<PosImageIcon> upup = new ArrayList<PosImageIcon>();
	ArrayList<JLabel> labels = new ArrayList<JLabel>();
	ArrayList<JLabel> scores = new ArrayList<JLabel>();
	
	JButton bExit;		// 삭제 실행을 위한 버튼

	JTextField user = new JTextField();
	JLabel id = new JLabel();

	Connection conn = null ;  
        Statement stmt = null;  
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	int oldD=0, newD=0, peachCnt = 0;
	boolean isMonster = true;
	int layCnt=3, speed=10, speedCnt=0;

	public static void main(String[] args) {
		SocketClient client = new SocketClient();
		client.go();
	}

	private void go() {
		// Setting up network
		

		try {
			sock = new Socket("127.0.0.1", 8081);
			writer = new PrintWriter(sock.getOutputStream(), true);

			is = sock.getInputStream();
			isr = new InputStreamReader(is);
			br = new BufferedReader(isr);

		} catch(Exception ex) {
			System.out.println("Error opening a socket");
		}

	  
		try  
		{  
		        Class.forName( "com.mysql.jdbc.Driver" ).newInstance();  
		}  
		catch(Exception etc) {  
		        System.out.println(etc.getMessage());  
		}  
		try  
		{  
		    String url = "jdbc:mysql://localhost/proj";  
		    String userId = "root";  
		    String userPass = "mite" ;  
	  
		    conn = DriverManager.getConnection(url, userId, userPass);  
		   stmt = conn.createStatement();
		    
		}
		catch( SQLException e )  
		{  
		    System.out.println( "SQLException : " + e.getMessage() ) ;  
		} 

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(500,100);

		firstBack  = new PosImageIcon("start_frame.png", 0,0,600,800, true);
		gameBack  = new PosImageIcon("gameBack.jpg", 0,0,600,800, true);
		character = new PosImageIcon("lion.gif", 250,650,100,100, true);


		for(int i=0; i<2; i++){
			PosImageIcon character = new PosImageIcon("monster.png", (int)(Math.random()*600) + 1 ,-( (int)(Math.random()*100) + 1),100,100, true);
			monster.add(character);
		}
		for(int i=0; i<2; i++){
			PosImageIcon character = new PosImageIcon("monster.png", (int)(Math.random()*600) + 1 ,-( (int)(Math.random()*400) + 200),100,100, true);
			monster.add(character);
		}

		for(int i=0; i<2; i++){
			PosImageIcon upupa = new PosImageIcon("peach.png", (int)(Math.random()*600) + 1 ,-( (int)(Math.random()*100) + 1),80,80, true);
			upup.add(upupa);
		}
		for(int i=0; i<2; i++){
			PosImageIcon upupa = new PosImageIcon("peach.png", (int)(Math.random()*600) + 1 ,-( (int)(Math.random()*400) + 200),80,80, true);
			upup.add(upupa);
		}

		bExit = new JButton("EXIT");

		firstPanel = new firstPanel();
		firstPanel.setBounds(0,0,600,800);
		firstPanel.add(user);

		secondPanel = new secondPanel();
		secondPanel.setBounds(0,0,600,800);
		secondPanel.add(id);

		thirdPanel = new thirdPanel();
		thirdPanel.setBounds(0,0,600,800);
		for(int i=0; i<10; i++){
			JLabel label = new JLabel();
			labels.add(label);
			thirdPanel.add(labels.get(i));
			JLabel score = new JLabel();
			scores.add(score);
			thirdPanel.add(scores.get(i));
		}
		thirdPanel.add(id);

		bExit.addActionListener(new ExitButtonListener());

		lp.add(firstPanel, new Integer(2));
		lp.add(secondPanel, new Integer(1));
		lp.add(thirdPanel, new Integer(0));

		frame.add(lp);
		frame.setSize(600,800);
		frame.setVisible(true);

		MultiThread mt1 = new MultiThread("시작 스레드");
		mt1.start();

	}//end go

	class firstPanel extends JPanel {
		public void paintComponent(Graphics g) {
			super.paintComponent(g); 
			firstBack.draw(g);

			user.setFont(new Font("휴먼매직체", Font.BOLD, 40));
			user.setBounds(200,650,200,70);

		}
	}

	class secondPanel extends JPanel {
		public void paintComponent(Graphics g) {
			super.paintComponent(g); 
			gameBack.draw(g);
			character.draw(g);
			for(int i=0; i<monster.size(); i++){
					monster.get(i).draw(g);
			}
			for(int i=0; i<upup.size(); i++){
					upup.get(i).draw(g);
			}
			id.setText(user.getText()+"님");
			id.setFont(new Font("휴먼매직체", Font.BOLD, 40));
			id.setBounds(20,10,200,70);
		}
	}
	class thirdPanel extends JPanel {
		public void paintComponent(Graphics g) {
			super.paintComponent(g); 
			gameBack.draw(g);
	
			id.setText(user.getText()+"님 "+peachCnt+"점");
			id.setFont(new Font("휴먼매직체", Font.BOLD, 40));
			id.setBounds(20,10,200,70);
			
			try  
			{  
				int j=0;
				String	sql = "SELECT * FROM proj ORDER BY socre DESC LIMIT 7"; 
			 	  rs = stmt.executeQuery(sql); 
				  while(rs.next()){ 
				    	//System.out.println("이름 : " + rs.getString(1) + " 점수 : " + rs.getInt(2) ); 
					scores.get(j).setText("이름 : " + rs.getString(1) + " 점수 : " + rs.getInt(2));
					j++;
				   } 

			}
			catch( SQLException e )  
			{  
			    System.out.println( "SQLException : " + e.getMessage() ) ;  
			} 
			int rectH = 100;
			for(int i=0; i<10; i++){
				PosImageIcon rank = new PosImageIcon("upup.png", 65,rectH,65,65, true);
				rank.draw(g);

				labels.get(i).setText((i+1)+"");
				labels.get(i).setFont(new Font("휴먼매직체", Font.BOLD, 40));
				labels.get(i).setBounds(85,rectH+8,50,50);
				 g.setColor(Color.WHITE);
				g.fillRect(140,rectH,400,65);
				scores.get(i).setFont(new Font("휴먼매직체", Font.BOLD, 40));
				scores.get(i).setBounds(150,rectH+8,400,50);
				rectH+=95;
			}
			
			
		}
	}

	public class PosImageIcon extends ImageIcon{
		int pX,pY,width,height;
		boolean upup;

		public PosImageIcon(String img, int x, int y, int width, int height, boolean upup){
			super(img);
			pX = x;
			pY = y;
			this.width = width;
			this.height = height;
			this.upup = upup;
		}	
		public void move( int x, int y){
			pX += x;
			pY += y;
		}	
		public void draw(Graphics g){
			g.drawImage(this.getImage(), pX, pY, width, height, null);
		}
		public double distance(PosImageIcon r){
			return Math.sqrt(Math.pow((pX+(r.width)/2)-(r.pX+(r.width)/2),2)+Math.pow((pY+(r.width)/2)-(r.pY+(r.width)/2),2));
		}
	}


	class MultiThread extends Thread { // Thread 클래스를 상속
		String name;

		public MultiThread(String name) {
			System.out.println(getName() + " 스레드가 생성되었습니다.");
			this.name = name;
		}
	
		public void run() {
			try {

				String s = "2";
				writer.println(s);
				writer.flush();
				
				while(true){
					String receiveData = "";
					receiveData = br.readLine();
					System.out.println("서버로 부터 받은 데이터 : " + receiveData);
					if(receiveData.equals("2")) { //2면 조도가 꺼져서 게임이 시작됨
						lp.setLayer(secondPanel, layCnt);
						layCnt++;
						break;
					}
					user.setText(user.getText()+receiveData);
				}
				
				sleep(100);

				s = "7"; //노래 시작
				writer.println(s);
				writer.flush();

				sleep(100);
			
				s = "10"; //게임 진행
				writer.println(s);
				writer.flush();

				MultiThread2 mt2 = new MultiThread2("게임 스레드");
				mt2.start();
				MonsterThread mt3 = new MonsterThread("몬스터 스레드");
				mt3.start();
			} catch(Exception ex) {
				System.out.println("Error writing to socket");
			}
		}
	}

	class MultiThread2 extends Thread { // Thread 클래스를 상속
		String name;

		public MultiThread2(String name) {
			System.out.println(getName() + " 스레드가 생성되었습니다.");
			this.name = name;
		}
	
		public void run() {
			while(true){
				try {
					oldD = newD; 		//전의 데이터 저장
					String receiveData = "";
					receiveData = br.readLine();
					System.out.println("서버로 부터 받은 데이터 : " + receiveData);
					newD = Integer.parseInt(receiveData);						//새로운 데이터 저장

					String s;
					if(isMonster){	//게임이 끝나지 않았다면
						if(peachCnt < 10)	//얻은 점수 보냄
							s = "0" + peachCnt;
						else
							s = peachCnt + "";
					}
					else	{		//끝나면 끝났다는 표시 99를 보냄
						 s = "99";
					}
					System.out.println("보낸 데이터: " + s);
					writer.println(s);
					writer.flush();

					if(s.equals("99")){
						System.out.println("게임 종료");
						sleep(1000);
						lp.setLayer(thirdPanel, layCnt); //마지막 패널
						layCnt++;
						frame.repaint();
						
						s = "11";
						System.out.println("보낸 데이터: " + s);
						writer.println(s);
						writer.flush();
						
						receiveData = br.readLine();
						System.out.println("서버로 부터 받은 데이터 : " + receiveData);	
						
						if(receiveData.equals("a")){
							System.out.println("재시작");
							s = "10";
							oldD=0; 
							newD=0;
							peachCnt = 0;
							isMonster = true;
							speed=10;
							 speedCnt=0;
							
							character.pX = 250;
							character.pY = 650;
							
							for(int i=0; i<2; i++){
								monster.get(i).pX = (int)(Math.random()*600) + 1;
								monster.get(i).pY = -( (int)(Math.random()*100) + 1);
								upup.get(i).pX = (int)(Math.random()*600) + 1;
								upup.get(i).pY = -( (int)(Math.random()*100) + 1);
							}
							for(int i=2; i<monster.size();i++){
								monster.get(i).pX = (int)(Math.random()*600) + 1;
								monster.get(i).pY = -( (int)(Math.random()*400) + 200);
								upup.get(i).pX = (int)(Math.random()*600) + 1;
								upup.get(i).pY = -( (int)(Math.random()*400) + 200);
							}
			
							System.out.println("보낸 데이터: " + s);
							writer.println(s);
							writer.flush();
							lp.setLayer(secondPanel, layCnt);
							layCnt++;
							MonsterThread mt4 = new MonsterThread("몬스터 스레드");
							mt4.start();
						}
						else if(receiveData.equals("b")){
							System.out.println("끝");
							writer.close();
							sock.close();
							frame.dispose();
							break;
						}
					}

					if(oldD > newD && character.pX > 0){ //초음파 값이 전보다 작다면 왼쪽으로
						for(int j=0; j<13; j++){
							character.move(-3,0);		
						}		
					}
					else if(oldD < newD && character.pX < 600){ //초음파 값이 전보다 크다면 왼쪽으로
						for(int j=0; j<13; j++){
							character.move(3,0);		
						}
					}
					secondPanel.repaint();
				} catch(Exception ex) {
					System.out.println("Error writing to socket");
				}
			}
		}
	}

	class MonsterThread extends Thread { // Thread 클래스를 상속
		String name;

		public MonsterThread(String name) {
			System.out.println(getName() + " 스레드가 생성되었습니다.");
			this.name = name;
		}
	
		public void run() {
			while(isMonster){
				for(int i=0; i<monster.size()/2; i++){ //몬스터가내려옴
					monster.get(i).pY += speed;
					if(monster.get(i).pY > 800) {
						monster.get(i).pY = -( (int)(Math.random()*100) + 1);
						monster.get(i).pX =  (int)(Math.random()*600) + 1;
					}
				}
				for(int i=monster.size()/2; i<monster.size(); i++){
					monster.get(i).pY += speed;
					if(monster.get(i).pY > 800) {
						monster.get(i).pY = -( (int)(Math.random()*400) + 1);
						monster.get(i).pX =  (int)(Math.random()*600) + 1;
					}
				}
				for(int i=0; i<monster.size(); i++){
					if(monster.get(i).distance(character)<70){

						System.out.println("qq");
						isMonster = false;
						try{
							String sql = "insert into proj values(?,?)";        // sql 쿼리
							pstmt = conn.prepareStatement(sql);                          // prepareStatement에서 해당 sql을 미리 컴파일한다.
							pstmt.setString(1,user.getText());
							pstmt.setInt(2,peachCnt);
							pstmt.executeUpdate();                                        // 쿼리를 실행한다.
						}
						  catch( SQLException e )  
						{  
						    System.out.println( "SQLException : " + e.getMessage() ) ;  
						} 
						thirdPanel.repaint();
					}

					
				}

				for(int i=0; i<upup.size()/2; i++){
					upup.get(i).pY += speed;
					if(upup.get(i).pY > 800) {
						upup.get(i).pY = -( (int)(Math.random()*100) + 1);
						upup.get(i).pX =  (int)(Math.random()*600) + 1;
						upup.get(i).upup = true;
					}
				}
				for(int i=upup.size()/2; i<upup.size(); i++){
					upup.get(i).pY += speed;
					if(upup.get(i).pY > 800) {
						upup.get(i).pY = -( (int)(Math.random()*400) + 1);
						upup.get(i).pX =  (int)(Math.random()*600) + 1;
						upup.get(i).upup = true;
					}
				}
				for(int i=0; i<upup.size(); i++){
					if(upup.get(i).distance(character)<70 && upup.get(i).upup){
						
						peachCnt += 2;
						System.out.println("냠냠" + peachCnt);
						upup.get(i).upup = false;					
					}
				}
				try{				
					sleep(100);
				}catch(Exception ex){
					ex.printStackTrace();
				}
				
				speedCnt++;
				if(speedCnt == 30 && speed < 20){
					speed++;
					speedCnt = 0;	
				}
				secondPanel.repaint();
			}
		}
	}

	public class ExitButtonListener implements ActionListener {
		public void actionPerformed (ActionEvent e) {
	    	try {
	    		writer.println('6');
			writer.close();
			sock.close();
			frame.dispose();
	    	} catch (Exception ex) {
	    		ex.printStackTrace();
	    	}
		}
	}
}






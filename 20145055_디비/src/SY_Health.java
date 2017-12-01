import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.text.Position;

public class SY_Health {
	Connection conn;
	
	JFrame frame = new JFrame();
	String frameTitle = "SY Health 데이터베이스 클라이언트";
	JPanel loginPanel, joinPanel, mainPanel, profilePanel, foodPanel, fitnessPanel;
	JLayeredPane lp = new JLayeredPane();
	JLabel idLabel = new JLabel("ID : ");
	JLabel passwordLabel = new JLabel("password : ");
	JLabel idLabel2 = new JLabel("ID : ");
	JLabel passwordLabel2 = new JLabel("비밀번호 : ");
	JLabel nameLabel = new JLabel("이름 : ");
	JLabel genderLabel = new JLabel("성별 : ");
	JLabel heightLabel = new JLabel("키 : ");
	JLabel weightLabel = new JLabel("몸무게 : ");
	JLabel birthdayLabel = new JLabel("생일 : ");
	JLabel goalKcalLabel = new JLabel("목표 칼로리 : ");
	JLabel goalFitLabel = new JLabel("목표 운동시간 : ");
	JLabel cmLabel = new JLabel("cm");
	JLabel kgLabel = new JLabel("kg");
	JLabel kcalLabel = new JLabel("kcal");
	JLabel minuteLabel = new JLabel("분");
	
	JTextField idField = new JTextField();
	JTextField passwordField = new JTextField();
	JTextField idField2 = new JTextField();
	JTextField passwordField2 = new JTextField();
	JTextField nameField = new JTextField();
	JTextField heightField = new JTextField();
	JTextField weightField = new JTextField();
	JTextField birthdayField = new JTextField();
	JTextField goalKcalField = new JTextField();
	JTextField goalFitField = new JTextField();
	
	JRadioButton male = new JRadioButton("남");
    JRadioButton female = new JRadioButton("여");
	
	JButton loginButton = new JButton("로그인");
	JButton joinButton = new JButton("회원가입");
	JButton cancleButton = new JButton("취소");
	JButton joinButton2 = new JButton("회원가입");
	
	int cnt = 5;
	
	public static void main(String[] args) {
		SY_Health client = new SY_Health();
		client.setUpGUI();
		client.dbConnectionInit();
	}
	
	private void setUpGUI() {
		frame = new JFrame(frameTitle);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(400, 100);
		
		loginButton.addActionListener(new loginButton());
		joinButton.addActionListener(new joinButton());
		cancleButton.addActionListener(new cancleButton());
		joinButton2.addActionListener(new joinButton2());
		
		loginPanel = new loginPanel();
		loginPanel.setBounds(0,0,800,600);
		loginPanel.add(idLabel);
		loginPanel.add(passwordLabel);
		loginPanel.add(idField);
		loginPanel.add(passwordField);
		loginPanel.add(loginButton);
		loginPanel.add(joinButton);
		
		ButtonGroup sex = new ButtonGroup();
	    sex.add(male);
	    sex.add(female);
	    
		joinPanel = new joinPanel();
		joinPanel.setBounds(0,0,800,600);
		joinPanel.add(idLabel2);
		joinPanel.add(passwordLabel2);
		joinPanel.add(nameLabel);
		joinPanel.add(genderLabel);
		joinPanel.add(heightLabel);
		joinPanel.add(weightLabel);
		joinPanel.add(birthdayLabel);
		joinPanel.add(goalKcalLabel);
		joinPanel.add(goalFitLabel);
		joinPanel.add(idField2);
		joinPanel.add(passwordField2);
		joinPanel.add(nameField);
		joinPanel.add(heightField);
		joinPanel.add(weightField);
		joinPanel.add(birthdayField);
		joinPanel.add(goalKcalField);
		joinPanel.add(goalFitField);
		joinPanel.add(male);
		joinPanel.add(female);
		joinPanel.add(cancleButton);
		joinPanel.add(joinButton2);
		joinPanel.add(cmLabel);
		joinPanel.add(kgLabel);
		joinPanel.add(kcalLabel);
		joinPanel.add(minuteLabel);
		
		
		lp.add(joinPanel, new Integer(4));
		lp.add(loginPanel, new Integer(cnt));
		frame.add(lp);
		frame.setSize(800,550);
		frame.setVisible(true);
	}
	
	class loginPanel extends JPanel {
		public void paintComponent(Graphics g) {
			g.setColor(new Color(193,241,190));
			g.fillRect(0, 0, 800, 600);
			idLabel.setFont(new Font("휴먼매직체", Font.PLAIN,30));
			idLabel.setBounds(278,170,200,60);
			passwordLabel.setFont(new Font("휴먼매직체", Font.PLAIN,30));
			passwordLabel.setBounds(170,220,200,60);
			idField.setFont(new Font("휴먼매직체", Font.PLAIN,30));
			idField.setBounds(338, 170, 200, 40);
			passwordField.setFont(new Font("휴먼매직체", Font.PLAIN,30));
			passwordField.setBounds(338,220,200,40);
			loginButton.setBounds(220, 310, 150, 40);
			loginButton.setBackground(new Color(245,245,245));
			joinButton.setBounds(400, 310, 150, 40);
			joinButton.setBackground(new Color(245,245,245));
		}
	}
	class joinPanel extends JPanel {
		public void paintComponent(Graphics g) {
			g.setColor(new Color(193,241,190));
			g.fillRect(0, 0, 800, 600);
			idLabel2.setFont(new Font("휴먼매직체", Font.PLAIN,30));
			idLabel2.setBounds(50,50,200,60);
			passwordLabel2.setFont(new Font("휴먼매직체", Font.PLAIN,30));
			passwordLabel2.setBounds(50,110,200,60);
			nameLabel.setFont(new Font("휴먼매직체", Font.PLAIN,30));
			nameLabel.setBounds(50,170,200,60);
			genderLabel.setFont(new Font("휴먼매직체", Font.PLAIN,30));
			genderLabel.setBounds(50,230,200,60);
			heightLabel.setFont(new Font("휴먼매직체", Font.PLAIN,30));
			heightLabel.setBounds(50,300,200,60);
			cmLabel.setFont(new Font("휴먼매직체", Font.PLAIN,20));
			cmLabel.setBounds(360,310,200,60);
			weightLabel.setFont(new Font("휴먼매직체", Font.PLAIN,30));
			weightLabel.setBounds(50,370,200,60);
			kgLabel.setFont(new Font("휴먼매직체", Font.PLAIN,20));
			kgLabel.setBounds(360,375,200,60);
			goalKcalLabel.setFont(new Font("휴먼매직체", Font.PLAIN,30));
			goalKcalLabel.setBounds(400,110,200,60);
			kcalLabel.setFont(new Font("휴먼매직체", Font.PLAIN,20));
			kcalLabel.setBounds(733,120,200,60);
			birthdayLabel.setFont(new Font("휴먼매직체", Font.PLAIN,30));
			birthdayLabel.setBounds(400,50,200,60);
			goalFitLabel.setFont(new Font("휴먼매직체", Font.PLAIN,30));
			goalFitLabel.setBounds(400,170,200,60);
			minuteLabel.setFont(new Font("휴먼매직체", Font.PLAIN,20));
			minuteLabel.setBounds(730,180,200,60);
			idField2.setFont(new Font("휴먼매직체", Font.PLAIN,30));
			idField2.setBounds(178, 60, 180, 40);
			passwordField2.setFont(new Font("휴먼매직체", Font.PLAIN,30));
			passwordField2.setBounds(178,120,180,40);
			nameField.setFont(new Font("휴먼매직체", Font.PLAIN,30));
			nameField.setBounds(178,180,180,40);
			heightField.setFont(new Font("휴먼매직체", Font.PLAIN,30));
			heightField.setBounds(178,310,180,40);
			weightField.setFont(new Font("휴먼매직체", Font.PLAIN,30));
			weightField.setBounds(178,375,180,40);
			birthdayField.setFont(new Font("휴먼매직체", Font.PLAIN,30));
			birthdayField.setBounds(570, 60, 180, 40);
			goalKcalField.setFont(new Font("휴먼매직체", Font.PLAIN,30));
			goalKcalField.setBounds(570, 120, 160, 40);
			goalFitField.setFont(new Font("휴먼매직체", Font.PLAIN,30));
			goalFitField.setBounds(570, 185, 160, 40);
			male.setFont(new Font("휴먼매직체", Font.PLAIN,25));
			male.setBounds(178, 240, 70, 40);
			male.setOpaque(false);
			female.setFont(new Font("휴먼매직체", Font.PLAIN,25));
			female.setBounds(248, 240, 70, 40);
			female.setOpaque(false);
			cancleButton.setBounds(450, 300, 100, 40);
			cancleButton.setBackground(new Color(245,245,245));
			joinButton2.setBounds(570, 300, 150, 40);
			joinButton2.setBackground(new Color(245,245,245));
		}
	}
	class loginButton implements ActionListener{
		public void actionPerformed(ActionEvent event){

		}
	}
	class joinButton implements ActionListener{
		public void actionPerformed(ActionEvent event){
			cnt = cnt+1;
			lp.setLayer(joinPanel, cnt);
		}
	}
	class cancleButton implements ActionListener{
		public void actionPerformed(ActionEvent event){
			cnt = cnt+1;
			lp.setLayer(loginPanel, cnt);
		}
	}
	class joinButton2 implements ActionListener{
		public void actionPerformed(ActionEvent event){
			try {
	    		Statement stmt = conn.createStatement();				// SQL 문을 작성을 위한  Statement 객체 생성
	    		String sex;
	    		if (male.isSelected())
	    			sex = "M";
	    		else
	    			sex = "F";
	    		stmt.executeUpdate("INSERT INTO member_info (id,password,name,gender,birthday,height,weight,goal_kcal,goal_fit) VALUES ('" +	// 새 레코드로 변경
	    				idField2.getText().trim() + "', '" +
	    				passwordField2.getText().trim() + "', '" +
	    				nameField.getText().trim() + "', '" +
	    				sex + "', '" +
	    				birthdayField.getText().trim() + "', '" +
	    				heightField.getText().trim() + "', '" +
	    				weightField.getText().trim() + "', '" +
	    				goalKcalField.getText().trim() + "', '" +
	    				goalFitField.getText().trim() + "')");
	    		stmt.close();
	    	} catch (SQLException sqlex) {
	    		System.out.println("SQL 에러 : " + sqlex.getMessage());
	    		sqlex.printStackTrace();
	    	} catch (Exception ex) {
	    		System.out.println("DB Handling 에러(SAVE 리스너) : " + ex.getMessage());
	    		ex.printStackTrace();
	    	}
			cnt = cnt+1;
			lp.setLayer(loginPanel, cnt);
		}
	}
	private void dbConnectionInit() {
    	try {
    		Class.forName("com.mysql.jdbc.Driver");					// JDBC드라이버를 JVM영역으로 가져오기
    		conn = DriverManager.getConnection("jdbc:mysql://localhost/SY_Health", "root", "mitemite");	// DB 연결하기
    		//prepareList();
    	}
        catch (ClassNotFoundException cnfe) {
            System.out.println("JDBC 드라이버 클래스를 찾을 수 없습니다 : " + cnfe.getMessage());
        }
        catch (Exception ex) {
            System.out.println("DB 연결 에러 : " + ex.getMessage());
        }
	}

}

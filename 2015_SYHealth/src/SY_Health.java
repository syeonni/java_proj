import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Vector;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.Position;

public class SY_Health {
	Connection conn;

	JFrame frame = new JFrame();
	JFrame foodFrame = new JFrame("식단 관리");
	JFrame fitFrame = new JFrame("운동 관리");
	JFrame traningFrame = new JFrame("추천 운동");
	String frameTitle = "SY Health 데이터베이스 클라이언트";
	JPanel loginPanel, joinPanel, mainPanel, profilePanel,foodPanel,fitPanel,traningPanel;
	JLayeredPane lp = new JLayeredPane();
	//
	JLabel idLabel = new JLabel("ID : ");
	JLabel passwordLabel = new JLabel("password : ");
	//
	JLabel idLabel2 = new JLabel("ID : ");
	JLabel passwordLabel2 = new JLabel("비밀번호 : ");
	JLabel nameLabel = new JLabel("이름 : ");
	JLabel genderLabel = new JLabel("성별 : ");
	JLabel heightLabel = new JLabel("키 : ");
	JLabel weightLabel = new JLabel("몸무게 : ");
	JLabel birthdayLabel = new JLabel("생일 : ");
	JLabel goalKcalLabel = new JLabel("목표 음식칼로리");
	JLabel goalFitLabel = new JLabel("목표 운동칼로리");
	JLabel cmLabel = new JLabel("cm");
	JLabel kgLabel = new JLabel("kg");
	JLabel kcalLabel = new JLabel("kcal");
	JLabel minuteLabel = new JLabel("kcal");
	JLabel feedbackLabel = new JLabel();
	//
	JLabel profileLabel = new JLabel("프로필");
	JLabel nameLabel2 = new JLabel();
	JLabel genderLabel2 = new JLabel();
	JLabel birthdayLabel2 = new JLabel();
	JLabel heightLabel2 = new JLabel();
	JLabel weightLabel2 = new JLabel();
	JLabel bmiLabel = new JLabel("BMI 지수  : ");
	JLabel bmiValLabel = new JLabel();
	JLabel bmiValLabel2 = new JLabel();
	JLabel foodLabel = new JLabel("음식");
	JLabel fitLabel = new JLabel("운동");
	JLabel waterLabel = new JLabel("물(오늘)");
	JLabel caffeineLabel = new JLabel("카페인(오늘)");
	JLabel waterCount = new JLabel("0");
	JLabel caffeineCount = new JLabel("0");
	//
	JLabel nameLabel3 = new JLabel();
	//
	JLabel foodKaclLabel = new JLabel();
	JLabel foodUnitLabel = new JLabel("인분");
	JLabel foodGLabel = new JLabel("▶분량(g)");
	JLabel foodKcalLabel = new JLabel("▶칼로리(kcal)");
	//
	JLabel fitKaclLabel = new JLabel();
	JLabel fitLevelLabel = new JLabel("▶운동강도");
	JLabel fitMinuteLabel = new JLabel("▶운동시간(분)");
	JLabel fitKcalLabel = new JLabel("▶칼로리(kcal)");
	JTextArea fitEffectArea = new JTextArea("효과");
	//
	JLabel fitGifLabel = new JLabel();
	JLabel fitExpainLabel = new JLabel();

	JTextField idField = new JTextField();
	JPasswordField passwordField = new JPasswordField();
	JTextField idField2 = new JTextField();
	JPasswordField passwordField2 = new JPasswordField();
	JTextField nameField = new JTextField();
	JTextField heightField = new JTextField();
	JTextField weightField = new JTextField();
	JTextField birthdayField = new JTextField();
	JTextField goalKcalField = new JTextField();
	JTextField goalFitField = new JTextField();
	JTextField heightField2 = new JTextField();
	JTextField weightField2 = new JTextField();
	JTextField goalKcalField2 = new JTextField();
	JTextField goalFitField2 = new JTextField();
	//
	JTextField dateField = new JTextField();
	JTextField foodSearchField = new JTextField();
	JTextField foodUnitField = new JTextField("1");
	JTextField foodGField = new JTextField();
	JTextField foodKcalField = new JTextField();
	//
	JTextField fitDateField = new JTextField();
	JTextField fitSearchField = new JTextField();
	JTextField fitMinuteField = new JTextField();
	JTextField fitKcalField = new JTextField();

	JRadioButton male = new JRadioButton("남");
	JRadioButton female = new JRadioButton("여");
	JRadioButton male2 = new JRadioButton("남");
	JRadioButton female2 = new JRadioButton("여");
	JRadioButton morning = new JRadioButton("아침");
	JRadioButton lunch = new JRadioButton("점심");
	JRadioButton diner = new JRadioButton("저녁");
	JRadioButton snack = new JRadioButton("간식");

	JButton loginButton = new JButton("로그인");
	JButton joinButton = new JButton("회원가입");
	JButton cancleButton = new JButton("취소");
	JButton joinButton2 = new JButton("회원가입");
	JButton reButton = new JButton(new ImageIcon("src/귄귄이.png"));
	JButton foodAdd = new JButton("추가");
	JButton fitAdd = new JButton("추가");
	JButton waterAdd = new JButton(new ImageIcon("src/물버튼.png"));
	JButton caffeineAdd = new JButton(new ImageIcon("src/카페인버튼.png"));
	JButton alterButton = new JButton("#편집");
	JButton cancleButton2 = new JButton(new ImageIcon("src/취소.png"));
	JButton saveButton = new JButton(new ImageIcon("src/저장.png"));
	//
	JButton leftButton = new JButton("<");
	JButton rightButton = new JButton(">");
	JButton delButton = new JButton("삭제");
	JButton foodSearchButton = new JButton("찾기");
	JButton foodLeftUnitButton = new JButton("<");
	JButton foodRightUnitButton = new JButton(">");
	JButton foodAddButton = new JButton("추가");
	JButton bPrint;			// 출력을 위한 버튼
    JButton bPreview;
    JButton aPrint;			// 출력을 위한 버튼
    JButton aPreview;
	//
	JButton fitLeftButton = new JButton("<");
	JButton fitRightButton = new JButton(">");
	JButton fitDelButton = new JButton("삭제");
	JButton fitSearchButton = new JButton("찾기");
	JButton fitAddButton = new JButton("추가");
	JButton recommendButton = new JButton("추천 운동");
	//
	JButton traningLeftButton = new JButton("<");
	JButton traningRightButton = new JButton(">");

	JTable table = null;
	JScrollPane scroll;
	JTable fitTable = null;
	JScrollPane fitScroll;

	JList foodNames = new JList();
	JScrollPane cScroller;
	JList fitNames = new JList();
	JScrollPane fitScroller;

	JComboBox fitComboBox, traningComboBox;

	String[] colName = { "이름","분량(g)", "칼로리(kcal)", "시간" };
	String[] fitColName = { "이름","운동시간", "칼로리(kcal)", "강도" };
	String traningName;
	DefaultTableModel model,fitModel;

	PosImageIcon guinguin,huhu,BMI,food,fit,water,caffeine,profile,arrow,fitguinguin,achievement,traningguinguin;

	int cnt = 5, feedback = 1,foodCnt=0,fitCnt=0;
	int arrowX=30, goal_kcal, real_kcal;
	long currentTime = (new Date()).getTime();
	long currentTime2 = (new Date()).getTime();
	int goal_fit,real_fit;
	Double BMInum;
	String member;
	int gifCnt = 1, gitLastCnt=1;

	public static void main(String[] args) {
		SY_Health client = new SY_Health();
		client.setUpGUI();
		client.dbConnectionInit();
	}

	private void setUpGUI() {
		frame = new JFrame(frameTitle);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(500, 200);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		foodFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		foodFrame.setLocation(900, 50);
		foodFrame.setSize(800,900);
		foodFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		fitFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fitFrame.setLocation(900, 50);
		fitFrame.setSize(800,900);
		fitFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		traningFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		traningFrame.setLocation(600, 200);
		traningFrame.setSize(800,550);
		traningFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		guinguin = new PosImageIcon("src/귄귄이.png",5,5,80,80);
		huhu = new PosImageIcon("src/달려.jpg",0,0,800,600);
		BMI = new PosImageIcon("src/BMI.jpg",30,360,330,100);
		food = new PosImageIcon("src/음식.png",410,120,110,110);
		fit = new PosImageIcon("src/운동.png",610,120,110,110);
		water = new PosImageIcon("src/물.jpg",410,320,110,110);
		caffeine = new PosImageIcon("src/카페인.jpg",620,320,110,110);
		profile = new PosImageIcon("src/프로필패널.png",0,0,800,600);
		arrow = new PosImageIcon("src/화살표.png",arrowX,425,20,20);
		fitguinguin = new PosImageIcon("src/운동귄귄이.png",70,680,80,80);
		achievement = new PosImageIcon("src/달성.png",600,80,80,80);
		traningguinguin = new PosImageIcon("src/트레이닝귄귄.png",90,400,80,80);

		passwordField.addActionListener(new loginButton());
		loginButton.addActionListener(new loginButton());
		joinButton.addActionListener(new joinButton());
		cancleButton.addActionListener(new cancleButton());
		joinButton2.addActionListener(new joinButton2());
		reButton.addActionListener(new reButton());
		waterAdd.addActionListener(new waterButton());
		caffeineAdd.addActionListener(new caffeineButton());
		alterButton.addActionListener(new alterButton());
		cancleButton2.addActionListener(new cancleButton2());
		saveButton.addActionListener(new saveButton());
		foodAdd.addActionListener(new foodAddButton());
		fitAdd.addActionListener(new fitAddButton());
		//
		leftButton.addActionListener(new leftButton());
		rightButton.addActionListener(new rightButton());
		delButton.addActionListener(new delButton());
		foodLeftUnitButton.addActionListener(new unitLeftButton());
		foodRightUnitButton.addActionListener(new unitRightButton());
		foodUnitField.addActionListener(new unitTextButton());
		foodAddButton.addActionListener(new foodEatButton());
		foodSearchField.addActionListener(new searchButton());
		foodSearchButton.addActionListener(new searchButton());
		fitLeftButton.addActionListener(new fitLeftButton());
		fitRightButton.addActionListener(new fitRightButton());
		fitDelButton.addActionListener(new fitDelButton());
		fitSearchField.addActionListener(new fitSearchButton());
		fitSearchButton.addActionListener(new fitSearchButton());
		fitAddButton.addActionListener(new fitDoButton());
		recommendButton.addActionListener(new recommendButton());
		fitMinuteField.addActionListener(new fitMinuteField());
		traningLeftButton.addActionListener(new traningLeftButton());
		traningRightButton.addActionListener(new traningRightButton());
		bPrint = new JButton("출력");
        bPreview = new JButton("미리보기");
        bPrint.addActionListener(new DisplayButtonListener());
        bPreview.addActionListener(new DisplayButtonListener());
        aPrint = new JButton("출력");
        aPreview = new JButton("미리보기");
        aPrint.addActionListener(new DisplayButtonListener2());
        aPreview.addActionListener(new DisplayButtonListener2());
		//
		cScroller = new JScrollPane(foodNames);
		cScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		cScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		cScroller.getVerticalScrollBar().setUnitIncrement(16);
		foodNames.setVisibleRowCount(7);
		foodNames.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		foodNames.setFixedCellWidth(100);
		foodNames.addListSelectionListener(new foodListListener());

		fitScroller = new JScrollPane(fitNames);
		fitScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		fitScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		fitNames.setVisibleRowCount(7);
		fitNames.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		fitNames.setFixedCellWidth(100);
		fitNames.addListSelectionListener(new fitListListener());
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
		ButtonGroup sex2 = new ButtonGroup();
		sex.add(male2);
		sex.add(female2);
		ButtonGroup meal = new ButtonGroup();
		meal.add(morning);
		meal.add(lunch);
		meal.add(diner);
		meal.add(snack);
		morning.setSelected(true);

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

		mainPanel = new mainPanel();
		mainPanel.setBounds(0,0,800,600);
		mainPanel.add(feedbackLabel);
		mainPanel.add(reButton);
		mainPanel.add(profileLabel);
		mainPanel.add(nameLabel2);
		mainPanel.add(genderLabel2);
		mainPanel.add(birthdayLabel2);
		mainPanel.add(heightLabel2);
		mainPanel.add(weightLabel2);
		mainPanel.add(bmiLabel);
		mainPanel.add(bmiValLabel);
		mainPanel.add(bmiValLabel2);
		mainPanel.add(foodLabel);
		mainPanel.add(fitLabel);
		mainPanel.add(waterLabel);
		mainPanel.add(caffeineLabel);
		mainPanel.add(foodAdd);
		mainPanel.add(fitAdd);
		mainPanel.add(waterAdd);
		mainPanel.add(caffeineAdd);
		mainPanel.add(waterCount);
		mainPanel.add(caffeineCount);
		mainPanel.add(alterButton);

		profilePanel = new profilePanel();
		profilePanel.setBounds(0,0,800,600);
		profilePanel.add(heightField2);
		profilePanel.add(weightField2);
		profilePanel.add(goalKcalField2);
		profilePanel.add(goalFitField2);
		profilePanel.add(male2);
		profilePanel.add(female2);
		profilePanel.add(nameLabel3);
		profilePanel.add(cancleButton2);
		profilePanel.add(saveButton);

		//
		foodPanel = new foodPanel();
		foodPanel.add(dateField);
		String currentDate2 = new SimpleDateFormat("YYYY-MM-dd").format(new Date());
		dateField.setText(currentDate2);
		foodPanel.add(leftButton);
		foodPanel.add(rightButton);
		foodPanel.add(foodKaclLabel);
		foodPanel.add(delButton);
		model = new DefaultTableModel(null,colName);
		table = new JTable(model);
		table.setBackground(Color.white);
		table.setSize(100,50);
		scroll = new JScrollPane(table);
		scroll.setPreferredSize(new Dimension(500,100));
		foodPanel.add(scroll);
		foodPanel.add(cScroller);
		foodPanel.add(foodUnitLabel);
		foodPanel.add(foodGLabel);
		foodPanel.add(foodKcalLabel);
		foodPanel.add(foodSearchField);
		foodPanel.add(foodUnitField);
		foodPanel.add(foodGField);
		foodPanel.add(foodKcalField);
		foodPanel.add(foodSearchButton);
		foodPanel.add(foodLeftUnitButton);
		foodPanel.add(foodRightUnitButton);
		foodPanel.add(foodAddButton);
		foodPanel.add(morning);
		foodPanel.add(lunch);
		foodPanel.add(diner);
		foodPanel.add(snack);
		foodPanel.add(bPrint);
        foodPanel.add(bPreview);
		//
		fitPanel = new fitPanel();
		fitPanel.add(fitDateField);
		fitDateField.setText(currentDate2);
		fitPanel.add(fitLeftButton);
		fitPanel.add(fitRightButton);
		fitPanel.add(fitKaclLabel);
		fitModel = new DefaultTableModel(null,fitColName);
		fitTable = new JTable(fitModel);
		fitTable.setBackground(Color.white);
		fitTable.setSize(100,50);
		fitScroll = new JScrollPane(fitTable);
		fitScroll.setPreferredSize(new Dimension(500,100));
		fitPanel.add(fitScroll);
		fitPanel.add(fitDelButton);
		fitPanel.add(fitSearchField);
		fitPanel.add(fitSearchButton);
		fitPanel.add(fitScroller);
		fitComboBox= new JComboBox();
		fitComboBox.addItem("가볍게");
		fitComboBox.addItem("보통");
		fitComboBox.addItem("빠르게");
		fitPanel.add(fitComboBox);
		fitPanel.add(fitLevelLabel);
		fitPanel.add(fitMinuteLabel);
		fitPanel.add(fitKcalLabel);
		fitPanel.add(fitMinuteField);
		fitPanel.add(fitKcalField);
		fitPanel.add(fitAddButton);
		fitComboBox.addActionListener(new fitMinuteField());
		fitPanel.add(fitEffectArea);
		fitPanel.add(recommendButton);
		fitPanel.add(aPrint);
        fitPanel.add(aPreview);

		traningPanel = new traningPanel();
		traningPanel.add(fitGifLabel);
		traningPanel.add(traningLeftButton);
		traningPanel.add(traningRightButton);
		traningComboBox = new JComboBox();
		traningComboBox.addActionListener(new traningComboBoxListener());
		traningPanel.add(traningComboBox);
		traningPanel.add(fitExpainLabel);

		lp.add(profilePanel, new Integer(2));
		lp.add(mainPanel, new Integer(3));
		lp.add(joinPanel, new Integer(4));
		lp.add(loginPanel, new Integer(cnt));
		frame.add(lp);
		frame.setSize(800,550);
		frame.setVisible(true);

		foodFrame.add(foodPanel);
		fitFrame.add(fitPanel);
		traningFrame.add(traningPanel);
	}
	class loginPanel extends JPanel {
		public void paintComponent(Graphics g) {
			huhu.draw(g);		
		}
		public loginPanel(){
			setLayout(null);
			idLabel.setForeground(Color.white);
			idLabel.setFont(new Font("휴먼매직체", Font.PLAIN,30));
			idLabel.setBounds(278,170,200,60);
			passwordLabel.setForeground(Color.white);
			passwordLabel.setFont(new Font("휴먼매직체", Font.PLAIN,30));
			passwordLabel.setBounds(170,220,200,60);
			idField.setFont(new Font("휴먼매직체", Font.PLAIN,30));
			idField.setBounds(338, 170, 200, 40);
			passwordField.setFont(new Font("굴림체", Font.PLAIN,30));
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
			passwordField2.setFont(new Font("굴림체", Font.PLAIN,30));
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
	class mainPanel extends JPanel {
		public void paintComponent(Graphics g) {
			g.setColor(new Color(193,241,190));
			g.fillRect(0, 0, 800, 600);
			guinguin.draw(g);
			g.setColor(Color.gray);
			g.drawLine(85, 240, 85, 260);
			g.setColor(Color.gray);
			g.drawLine(110, 280, 110, 300);
			BMI.draw(g);
			g.setColor(Color.white);
			g.fillRect(380, 110, 180, 180);
			g.fillRect(580, 110, 180, 180);
			g.fillRect(380, 310, 180, 180);
			g.fillRect(580, 310, 180, 180);
			food.draw(g);
			fit.draw(g);
			water.draw(g);
			caffeine.draw(g);
			waterCount.setForeground(Color.white);
			waterCount.setFont(new Font("휴먼매직체", Font.BOLD,45));
			waterCount.setBounds(455,350,200,60);
			caffeineCount.setForeground(Color.white);
			caffeineCount.setFont(new Font("휴먼매직체", Font.BOLD,45));
			caffeineCount.setBounds(652,350,200,60);
			arrow.draw(g);
		}
		public mainPanel(){
			setLayout(null);
			reButton.setBounds(700, 30, 50, 50);
			alterButton.setBorderPainted(false);
			alterButton.setOpaque(false);
			alterButton.setContentAreaFilled(false);
			alterButton.setBounds(285, 115, 100, 25);
			foodAdd.setBorderPainted(false);
			foodAdd.setBackground(new Color(255,255,102));
			foodAdd.setBounds(415, 260, 100, 25);
			fitAdd.setBorderPainted(false);
			fitAdd.setBackground(new Color(255,138,101));
			fitAdd.setBounds(615, 260, 100, 25);
			waterAdd.setBorderPainted(false);
			waterAdd.setBounds(455, 460, 22, 22);
			caffeineAdd.setBorderPainted(false);
			caffeineAdd.setBounds(655, 460, 22, 22);
			profileLabel.setFont(new Font("휴먼매직체", Font.PLAIN,22));
			profileLabel.setBounds(30,100,200,60);
			feedbackLabel.setFont(new Font("휴먼매직체", Font.PLAIN,22));
			feedbackLabel.setBounds(100, 10, 600, 80);
			nameLabel2.setFont(new Font("휴먼매직체", Font.PLAIN,25));
			nameLabel2.setBounds(30,160,200,60);
			genderLabel2.setFont(new Font("휴먼매직체", Font.PLAIN,22));
			genderLabel2.setBounds(30,220,200,60);
			birthdayLabel2.setFont(new Font("휴먼매직체", Font.PLAIN,22));
			birthdayLabel2.setBounds(100,220,200,60);
			heightLabel2.setFont(new Font("휴먼매직체", Font.PLAIN,22));
			heightLabel2.setBounds(30,260,200,60);
			weightLabel2.setFont(new Font("휴먼매직체", Font.PLAIN,22));
			weightLabel2.setBounds(125,260,200,60);
			bmiLabel.setFont(new Font("휴먼매직체", Font.PLAIN,22));
			bmiLabel.setBounds(30,300,200,60);
			bmiValLabel.setFont(new Font("휴먼매직체", Font.PLAIN,22));
			bmiValLabel.setBounds(150,300,200,60);
			bmiValLabel2.setFont(new Font("휴먼매직체", Font.PLAIN,22));
			bmiValLabel2.setBounds(220,300,200,60);
			foodLabel.setFont(new Font("휴먼매직체", Font.PLAIN,22));
			foodLabel.setBounds(445,215,200,60);
			fitLabel.setFont(new Font("휴먼매직체", Font.PLAIN,22));
			fitLabel.setBounds(645,215,200,60);
			waterLabel.setFont(new Font("휴먼매직체", Font.PLAIN,22));
			waterLabel.setBounds(435,415,200,60);
			caffeineLabel.setFont(new Font("휴먼매직체", Font.PLAIN,22));
			caffeineLabel.setBounds(620,415,200,60);
		}
	}
	class profilePanel extends JPanel {
		public void paintComponent(Graphics g) {
			profile.draw(g);
		}
		public profilePanel(){
			setLayout(null);
			heightField2.setFont(new Font("휴먼매직체", Font.PLAIN,30));
			heightField2.setBounds(168,280,180,40);
			weightField2.setFont(new Font("휴먼매직체", Font.PLAIN,30));
			weightField2.setBounds(168,355,180,40);
			goalKcalField2.setFont(new Font("휴먼매직체", Font.PLAIN,30));
			goalKcalField2.setBounds(575, 200, 160, 40);
			goalFitField2.setFont(new Font("휴먼매직체", Font.PLAIN,30));
			goalFitField2.setBounds(575, 275, 160, 40);
			male2.setFont(new Font("휴먼매직체", Font.PLAIN,25));
			male2.setBounds(170, 200, 70, 40);
			male2.setOpaque(false);
			female2.setFont(new Font("휴먼매직체", Font.PLAIN,25));
			female2.setBounds(240, 200, 70, 40);
			female2.setOpaque(false);
			nameLabel3.setFont(new Font("휴먼매직체", Font.PLAIN,30));
			nameLabel3.setBounds(50,90,200,60);
			cancleButton2.setBorderPainted(false);
			cancleButton2.setOpaque(false);
			cancleButton2.setContentAreaFilled(false);
			cancleButton2.setBounds(465, 355, 100, 50);
			saveButton.setBorderPainted(false);
			saveButton.setOpaque(false);
			saveButton.setContentAreaFilled(false);
			saveButton.setBounds(605, 355, 100, 50);

		}
	}
	class foodPanel extends JPanel {
		public void paintComponent(Graphics g) {
			g.setColor((new Color(255,255,142)));
			g.fillRect(0, 0, 800, 900);
			dateField.setBounds(325, 100, 150, 40);
			leftButton.setFont(new Font("휴먼매직체", Font.BOLD,25));
			leftButton.setBorderPainted(false);
			leftButton.setContentAreaFilled(false);
			leftButton.setBounds(260, 100, 50, 50);
			rightButton.setFont(new Font("휴먼매직체", Font.BOLD,25));
			rightButton.setBorderPainted(false);
			rightButton.setOpaque(false);
			rightButton.setContentAreaFilled(false);
			rightButton.setBounds(490, 100, 50, 50);
			delButton.setBounds(600, 370, 80, 30);
			delButton.setBackground(Color.white);
			g.setFont(new Font("휴먼매직체", Font.PLAIN,24));
			g.setColor(Color.darkGray);
			g.drawString("▶섭취 칼로리", 70, 200);
			g.setColor(Color.white);
			g.fillRect(70, 230, 640, 30);
			foodKaclLabel.setBounds(610,190,200,60);
			g.setColor(new Color(1,1,100));
			if(real_kcal < goal_kcal) g.fillRect(70, 230, (((710-70)*1000)/(goal_kcal)*(real_kcal))/1000, 30);
			else g.fillRect(70, 230, 640, 30);
			table.setSize(500,200);
			foodNames.setSize(150, 200);
			cScroller.setPreferredSize(new Dimension(150,150));
			cScroller.setBounds(70, 500, 150, 150);
			scroll.setBounds(70, 300, 500, 100);
			foodSearchField.setFont(new Font("휴먼매직체", Font.PLAIN,22));
			foodSearchField.setBounds(70,450,100,30);
			foodSearchButton.setBounds(180,450,80,30);
			foodSearchButton.setBackground(Color.white);
			foodLeftUnitButton.setFont(new Font("휴먼매직체", Font.BOLD,22));
			foodLeftUnitButton.setBounds(280,450,80,30);
			foodLeftUnitButton.setBorderPainted(false);
			foodLeftUnitButton.setOpaque(false);
			foodLeftUnitButton.setContentAreaFilled(false);
			foodUnitField.setFont(new Font("휴먼매직체", Font.PLAIN,22));
			foodUnitField.setBounds(350,450,130,30);
			foodUnitLabel.setFont(new Font("휴먼매직체", Font.PLAIN,22));
			foodUnitLabel.setBounds(480,450,150,30);
			foodRightUnitButton.setFont(new Font("휴먼매직체", Font.BOLD,22));
			foodRightUnitButton.setBounds(530,450,80,30);
			foodRightUnitButton.setBorderPainted(false);
			foodRightUnitButton.setOpaque(false);
			foodRightUnitButton.setContentAreaFilled(false);
			foodGLabel.setFont(new Font("휴먼매직체", Font.PLAIN,20));
			foodGLabel.setBounds(295,500,100,30);
			foodGField.setFont(new Font("휴먼매직체", Font.PLAIN,20));
			foodGField.setBounds(420,500,150,30);
			foodKcalLabel.setFont(new Font("휴먼매직체", Font.PLAIN,20));
			foodKcalLabel.setBounds(295,545,180,30);
			foodKcalField.setFont(new Font("휴먼매직체", Font.PLAIN,20));
			foodKcalField.setBounds(420,545,150,30);
			foodAddButton.setBounds(600, 600, 80, 30);
			foodAddButton.setBackground(Color.white);
			morning.setFont(new Font("휴먼매직체", Font.PLAIN,20));
			morning.setBounds(300, 590, 70, 40);
			morning.setOpaque(false);
			lunch.setFont(new Font("휴먼매직체", Font.PLAIN,20));
			lunch.setBounds(365, 590, 70, 40);
			lunch.setOpaque(false);
			diner.setFont(new Font("휴먼매직체", Font.PLAIN,20));
			diner.setBounds(430, 590, 70, 40);
			diner.setOpaque(false);
			snack.setFont(new Font("휴먼매직체", Font.PLAIN,20));
			snack.setBounds(495, 590, 70, 40);
			snack.setOpaque(false);
			if(real_kcal < goal_kcal && !(new SimpleDateFormat("YYYY-MM-dd").format(currentTime).equals(new SimpleDateFormat("YYYY-MM-dd").format(new Date())))) 
				achievement.draw(g);
			bPrint.setBounds(280, 730, 80, 30);
			bPrint.setBackground(Color.white);
			bPreview.setBounds(380, 730, 130, 30);
			bPreview.setBackground(Color.white);
		}
		public foodPanel(){
			dateField.setFont(new Font("휴먼매직체", Font.PLAIN,25));
			dateField.setFocusable(false);
			foodKaclLabel.setFont(new Font("휴먼매직체", Font.PLAIN,20));
			foodUnitField.setHorizontalAlignment(JTextField.CENTER);
			foodGField.setHorizontalAlignment(JTextField.CENTER);
			foodKcalField.setHorizontalAlignment(JTextField.CENTER);
		}
	}
	class fitPanel extends JPanel {
		public void paintComponent(Graphics g) {
			g.setColor(new Color(255,178,151));
			g.fillRect(0, 0, 800, 900);
			fitDateField.setBounds(325, 100, 150, 40);
			fitLeftButton.setFont(new Font("휴먼매직체", Font.BOLD,25));
			fitLeftButton.setBorderPainted(false);
			fitLeftButton.setContentAreaFilled(false);
			fitLeftButton.setBounds(260, 100, 50, 50);
			fitRightButton.setFont(new Font("휴먼매직체", Font.BOLD,25));
			fitRightButton.setBorderPainted(false);
			fitRightButton.setOpaque(false);
			fitRightButton.setContentAreaFilled(false);
			fitRightButton.setBounds(490, 100, 50, 50);
			g.setFont(new Font("휴먼매직체", Font.PLAIN,24));
			g.setColor(Color.darkGray);
			g.drawString("▶소모 칼로리", 70, 200);
			g.setColor(Color.white);
			g.fillRect(70, 230, 640, 30);
			fitKaclLabel.setBounds(610,190,200,60);
			g.setColor(new Color(1,1,70));
			if(real_fit < goal_fit) g.fillRect(70, 230, (((710-70)*1000)/(goal_fit)*(real_fit))/1000, 30);
			else g.fillRect(70, 230, 640, 30);
			fitTable.setSize(500,200);;
			fitScroll.setBounds(70, 300, 500, 100);
			fitDelButton.setBounds(600, 370, 80, 30);
			fitDelButton.setBackground(Color.white);
			fitSearchField.setFont(new Font("휴먼매직체", Font.PLAIN,22));
			fitSearchField.setBounds(70,450,100,30);
			fitSearchButton.setFont(new Font("휴먼매직체", Font.PLAIN,10));
			fitSearchButton.setBounds(180,450,40,30);
			fitSearchButton.setBackground(Color.white);
			fitNames.setSize(150, 200);
			fitScroller.setPreferredSize(new Dimension(150,150));
			fitScroller.setBounds(70, 500, 150, 150);
			fitComboBox.setBounds(430, 470, 130, 30);
			fitComboBox.setPreferredSize(new Dimension(130,30));
			fitLevelLabel.setFont(new Font("휴먼매직체", Font.PLAIN,20));
			fitLevelLabel.setBounds(300,470,150,30);
			fitMinuteLabel.setFont(new Font("휴먼매직체", Font.PLAIN,20));
			fitMinuteLabel.setBounds(300,530,150,30);
			fitKcalLabel.setFont(new Font("휴먼매직체", Font.PLAIN,20));
			fitKcalLabel.setBounds(300,600,150,30);
			fitMinuteField.setFont(new Font("휴먼매직체", Font.PLAIN,22));
			fitMinuteField.setBounds(430,530,130,30);
			fitKcalField.setFont(new Font("휴먼매직체", Font.PLAIN,22));
			fitKcalField.setBounds(430,600,130,30);
			fitguinguin.draw(g);
			fitEffectArea.setFont(new Font("휴먼매직체", Font.PLAIN,22));
			fitEffectArea.setBounds(160,700,430,60);
			fitEffectArea.setLineWrap(true);
			fitEffectArea.setOpaque(false);
			fitAddButton.setBounds(600, 700, 80,30);
			fitAddButton.setBackground(Color.white);
			if(real_fit > goal_fit) achievement.draw(g);
			recommendButton.setBounds(320,760,150,30);
			recommendButton.setBackground(Color.white);
			aPrint.setBounds(280, 800, 80, 30);
			aPrint.setBackground(Color.white);
			aPreview.setBounds(380, 800, 130, 30);
			aPreview.setBackground(Color.white);
		}
		public fitPanel(){
			fitDateField.setFont(new Font("휴먼매직체", Font.PLAIN,25));
			fitDateField.setFocusable(false);
			fitKaclLabel.setFont(new Font("휴먼매직체", Font.PLAIN,20));
		}
	}
	class traningPanel extends JPanel {
		public void paintComponent(Graphics g) {
			g.setColor(new Color(255,207,207));
			g.fillRect(0, 0, 800, 550);
			traningLeftButton.setFont(new Font("휴먼매직체", Font.BOLD,25));
			traningLeftButton.setBorderPainted(false);
			traningLeftButton.setContentAreaFilled(false);
			traningLeftButton.setBounds(160, 250, 50, 50);
			traningRightButton.setFont(new Font("휴먼매직체", Font.BOLD,25));
			traningRightButton.setBorderPainted(false);
			traningRightButton.setOpaque(false);
			traningRightButton.setContentAreaFilled(false);
			traningRightButton.setBounds(590, 250, 50, 50);
			traningComboBox.setBounds(80, 50, 150, 30);
			traningComboBox.setPreferredSize(new Dimension(150,30));
			fitGifLabel.setBounds(200, 0, 500, 500);
			fitGifLabel.setIcon(new ImageIcon("src/"+traningName+gifCnt+".gif"));
			traningguinguin.draw(g);
			fitExpainLabel.setFont(new Font("휴먼매직체", Font.PLAIN,27));
			fitExpainLabel.setBounds(180,430,800,30);
		}
	}
	class loginButton implements ActionListener{
		public void actionPerformed(ActionEvent event){
			try {
				Statement stmt = conn.createStatement();				// SQL 문을 작성을 위한  Statement 객체 생성
				ResultSet rs = stmt.executeQuery("SELECT * FROM member_info WHERE id = '" + idField.getText().trim() +"' AND password = '" + passwordField.getText().trim() + "'");
				if(rs.next()){
					cnt = cnt+1;
					lp.setLayer(mainPanel, cnt);
					member=rs.getString("mi_id");
					mainpare();
				}
				else{
					JOptionPane.showMessageDialog(null, "아이디 또는 비밀번호가 틀렸습니다.", "오류 메시지 제목", JOptionPane.ERROR_MESSAGE);
				}
			} catch (SQLException sqlex) {
				System.out.println("SQL 에러 : " + sqlex.getMessage());
				sqlex.printStackTrace();
			} catch (Exception ex) {
				System.out.println("DB Handling 에러(SAVE 리스너) : " + ex.getMessage());
				ex.printStackTrace();
			}
			traningpare();
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
				ResultSet rs = stmt.executeQuery("SELECT * FROM member_info WHERE id = '" + idField2.getText().trim() +"'");
				String sex;
				if (male.isSelected())
					sex = "M";
				else
					sex = "F";
				if(rs.next()){
					JOptionPane.showMessageDialog(null, "해당 아이디가 존재합니다.", "오류 메시지 제목", JOptionPane.ERROR_MESSAGE);
				}
				else{
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
					cnt = cnt+1;
					lp.setLayer(loginPanel, cnt);
				}
			} catch (SQLException sqlex) {
				System.out.println("SQL 에러 : " + sqlex.getMessage());
				sqlex.printStackTrace();
			} catch (Exception ex) {
				System.out.println("DB Handling 에러(SAVE 리스너) : " + ex.getMessage());
				ex.printStackTrace();
			}

		}
	}
	class reButton implements ActionListener{
		public void actionPerformed(ActionEvent event){
			int temp;	
			temp = feedback;	
			while(temp == feedback){	
				feedback=(int)(Math.random() * 8)+6;
			}
			try {
				Statement stmt = conn.createStatement();				// SQL 문을 작성을 위한  Statement 객체 생성
				Statement stmt2 = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM feedback WHERE feedback_id = " + feedback);
				rs.next();
				ResultSet rs2 = stmt2.executeQuery("SELECT * FROM member_info WHERE mi_id = " + member);
				rs2.next();
				feedbackLabel.setText(rs2.getString("name") + rs.getString("feedback"));
			} catch (SQLException sqlex) {
				System.out.println("SQL 에러 : " + sqlex.getMessage());
				sqlex.printStackTrace();
			} catch (Exception ex) {
				System.out.println("DB Handling 에러(SAVE 리스너) : " + ex.getMessage());
				ex.printStackTrace();
			}

		}
	}
	class waterButton implements ActionListener{
		public void actionPerformed(ActionEvent event){
			String currentDate = new SimpleDateFormat("YYYY-MM-dd").format(new Date());
			try {
				Statement stmt = conn.createStatement();				// SQL 문을 작성을 위한  Statement 객체 생성
				Statement stmt2 = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM water WHERE mi_id = " + member + " AND date = '" + currentDate + "';");
				if(rs.next()){
					stmt2.executeUpdate("UPDATE water SET water_count = water_count + 1 WHERE mi_id = " + member + " AND date = '" + currentDate + "';");
					waterCount.setText((rs.getInt("water_count")+1)+"");
				}
				else{
					stmt2.executeUpdate("INSERT INTO water (mi_id, date, water_count) VALUES (" + member + ",'" + currentDate + "', 0);");
				}
			} catch (SQLException sqlex) {
				System.out.println("SQL 에러 : " + sqlex.getMessage());
				sqlex.printStackTrace();
			} catch (Exception ex) {
				System.out.println("DB Handling 에러(SAVE 리스너) : " + ex.getMessage());
				ex.printStackTrace();
			}
			if(waterCount.getText().equals("8")){
				feedback = 4;
				mainpare();
			}
		}
	}
	class caffeineButton implements ActionListener{
		public void actionPerformed(ActionEvent event){
			String currentDate = new SimpleDateFormat("YYYY-MM-dd").format(new Date());
			try {
				Statement stmt = conn.createStatement();				// SQL 문을 작성을 위한  Statement 객체 생성
				Statement stmt2 = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM caffeine WHERE mi_id = " + member + " AND date = '" + currentDate + "';");
				if(rs.next()){
					stmt2.executeUpdate("UPDATE caffeine SET caffeine_count = caffeine_count + 1 WHERE mi_id = " + member + " AND date = '" + currentDate + "';");
					caffeineCount.setText((rs.getInt("caffeine_count")+1)+"");
				}
				else{
					stmt2.executeUpdate("INSERT INTO caffeine (mi_id, date, caffeine_count) VALUES (" + member + ",'" + currentDate + "', 0);");
				}
			} catch (SQLException sqlex) {
				System.out.println("SQL 에러 : " + sqlex.getMessage());
				sqlex.printStackTrace();
			} catch (Exception ex) {
				System.out.println("DB Handling 에러(SAVE 리스너) : " + ex.getMessage());
				ex.printStackTrace();
			}
			if(caffeineCount.getText().equals("3")){
				feedback = 5;
				mainpare();
			}
		}
	}
	class foodAddButton implements ActionListener{
		public void actionPerformed(ActionEvent event){
			foodFrame.setVisible(true);
			frame.setLocation(50, 200);
			foodFrame.setLocation(900, 50);
			foodpare();
		}
	}
	class fitAddButton implements ActionListener{
		public void actionPerformed(ActionEvent event){
			fitFrame.setVisible(true);
			frame.setLocation(50, 200);
			fitFrame.setLocation(900, 50);
			fitpare();
		}
	}
	class recommendButton implements ActionListener{
		public void actionPerformed(ActionEvent event){
			traningFrame.setVisible(true);
		}
	}
	class alterButton implements ActionListener{
		public void actionPerformed(ActionEvent event){
			cnt = cnt+1;
			lp.setLayer(profilePanel, cnt);
			try {
				Statement stmt = conn.createStatement();				// SQL 문을 작성을 위한  Statement 객체 생성
				ResultSet rs = stmt.executeQuery("SELECT * FROM member_info WHERE mi_id = '" + member +"'");
				rs.next();
				heightField2.setText(rs.getString("height"));
				weightField2.setText(rs.getString("weight"));
				goalKcalField2.setText(rs.getString("goal_Kcal"));
				goalFitField2.setText(rs.getString("goal_Fit"));
				if(rs.getString("gender").equals("F"))
					female2.setSelected(true);
				else
					male2.setSelected(true);
				nameLabel3.setText(rs.getString("name"));
			} catch (SQLException sqlex) {
				System.out.println("SQL 에러 : " + sqlex.getMessage());
				sqlex.printStackTrace();
			} catch (Exception ex) {
				System.out.println("DB Handling 에러(SAVE 리스너) : " + ex.getMessage());
				ex.printStackTrace();
			}
		}
	}
	class cancleButton2 implements ActionListener{
		public void actionPerformed(ActionEvent event){
			cnt = cnt+1;
			lp.setLayer(mainPanel, cnt);
		}
	}
	class saveButton implements ActionListener{
		public void actionPerformed(ActionEvent event){

			try {
				Statement stmt = conn.createStatement();
				String sex;
				if (male2.isSelected())
					sex = "M";
				else
					sex = "F";
				stmt.executeUpdate("UPDATE member_info SET gender = '" + sex + "' WHERE mi_id = " + member + ";");
				stmt.executeUpdate("UPDATE member_info SET weight = '" + weightField2.getText() + "' WHERE mi_id = " + member + ";");
				stmt.executeUpdate("UPDATE member_info SET height = '" + heightField2.getText() + "' WHERE mi_id = " + member + ";");
				stmt.executeUpdate("UPDATE member_info SET goal_kcal = '" + goalKcalField2.getText() + "' WHERE mi_id = " + member + ";");
				stmt.executeUpdate("UPDATE member_info SET goal_fit = '" + goalFitField2.getText() + "' WHERE mi_id = " + member + ";");
				stmt.close();
				cnt = cnt+1;
				lp.setLayer(mainPanel, cnt);
				mainpare();
			} catch (SQLException sqlex) {
				System.out.println("SQL 에러 : " + sqlex.getMessage());
				sqlex.printStackTrace();
			} catch (Exception ex) {
				System.out.println("DB Handling 에러(SAVE 리스너) : " + ex.getMessage());
				ex.printStackTrace();
			}
		}
	}
	class leftButton implements ActionListener{
		public void actionPerformed(ActionEvent event){
			currentTime -= 86400000;
			String currentDate = new SimpleDateFormat("YYYY-MM-dd").format(currentTime);
			dateField.setText(currentDate);
			foodpare();
			foodPanel.repaint();
		}
	}
	class rightButton implements ActionListener{
		public void actionPerformed(ActionEvent event){
			if((new Date()).getTime() > currentTime+86400000){
				currentTime += 86400000;
				String currentDate = new SimpleDateFormat("YYYY-MM-dd").format(currentTime);
				dateField.setText(currentDate);
				foodpare();
				foodPanel.repaint();
			}
		}
	}
	class fitLeftButton implements ActionListener{
		public void actionPerformed(ActionEvent event){
			currentTime2 -= 86400000;
			String currentDate = new SimpleDateFormat("YYYY-MM-dd").format(currentTime2);
			fitDateField.setText(currentDate);
			fitpare();
			fitPanel.repaint();
		}
	}
	class fitRightButton implements ActionListener{
		public void actionPerformed(ActionEvent event){
			if((new Date()).getTime() > currentTime2+86400000){
				currentTime2 += 86400000;
				String currentDate = new SimpleDateFormat("YYYY-MM-dd").format(currentTime2);
				fitDateField.setText(currentDate);
				fitpare();
				fitPanel.repaint();
			}
		}
	}
	class delButton implements ActionListener{
		public void actionPerformed(ActionEvent event){

			int delRow=table.getSelectedRow();
			String currentDate = new SimpleDateFormat("YYYY-MM-dd").format(currentTime);
			Double count;
			if(delRow != -1){
				try {
					Statement stmt = conn.createStatement();				// SQL 문을 작성을 위한  Statement 객체 생성
					stmt.executeUpdate("DELETE FROM meal WHERE mi_id = " + member
							+ " AND date = '" + currentDate + "'");
					model.removeRow(delRow);
					for(int i=0; i<table.getRowCount(); i++){
						ResultSet rs = stmt.executeQuery("SELECT * FROM food WHERE name = '" 
								+ model.getValueAt(i,0) + "'");
						rs.next();
						count = Double.valueOf(model.getValueAt(i,2)+"")/rs.getDouble("kcal"); 
						stmt.executeUpdate("INSERT INTO meal(mi_id,date,food_id,count,day) VALUES(" + member
								+ ",'" + currentDate + "',(SELECT food_id FROM food WHERE name = '" 
								+ model.getValueAt(i,0) + "')," + count + ",'" + model.getValueAt(i,3) + "');");
					}
					foodpare();
					foodPanel.repaint();
				} catch (SQLException sqlex) {
					System.out.println("SQL 에러 : " + sqlex.getMessage());
					sqlex.printStackTrace();
				} catch (Exception ex) {
					System.out.println("DB Handling 에러(SAVE 리스너) : " + ex.getMessage());
					ex.printStackTrace();
				}
			}
		}
	}
	class fitDelButton implements ActionListener{
		public void actionPerformed(ActionEvent event){
			int delRow=fitTable.getSelectedRow();
			String currentDate = new SimpleDateFormat("YYYY-MM-dd").format(currentTime2);
			double level;
			if(delRow != -1){
				try {
					Statement stmt = conn.createStatement();				// SQL 문을 작성을 위한  Statement 객체 생성
					stmt.executeUpdate("DELETE FROM mi_exercise WHERE mi_id = " + member
							+ " AND date = '" + currentDate + "'");
					fitModel.removeRow(delRow);
					for(int i=0; i<fitTable.getRowCount(); i++){
						if(fitModel.getValueAt(i,3).equals("가볍게")) level = 1;
						else if(fitModel.getValueAt(i,3).equals("보통")) level = 1.4;
						else level = 1.8;
						stmt.executeUpdate("INSERT INTO mi_exercise(mi_id,date,exercise_id,exercise_minute,level) VALUES(" + member
								+ ",'" + currentDate + "',(SELECT exercise_id FROM exercise WHERE name = '" 
								+ fitModel.getValueAt(i,0) + "')," + fitModel.getValueAt(i,1) + "," + level + ");");
					}
					fitpare();
					fitPanel.repaint();
				} catch (SQLException sqlex) {
					System.out.println("SQL 에러 : " + sqlex.getMessage());
					sqlex.printStackTrace();
				} catch (Exception ex) {
					System.out.println("DB Handling 에러(SAVE 리스너) : " + ex.getMessage());
					ex.printStackTrace();
				}
			}
		}
	}
	class unitLeftButton implements ActionListener{
		public void actionPerformed(ActionEvent event){
			float unitVal = Float.valueOf(foodUnitField.getText());
			float gVal;
			float kcalVal;
			unitVal -= 0.25;
			foodUnitField.setText(unitVal+"");
			try {
				Statement stmt = conn.createStatement();				// SQL 문장 만들기 위한 Statement 객체
				ResultSet rs = stmt.executeQuery("SELECT * FROM food WHERE name = '" +
						(String)foodNames.getSelectedValue() + "'");
				rs.next();// 여러개가 리턴되어도 첫번째 것으로 사용
				gVal = Float.valueOf(rs.getString("g"));
				kcalVal = Float.valueOf(rs.getString("kcal"));
				stmt.close();
				gVal *= unitVal;
				kcalVal *= unitVal;
				foodGField.setText(gVal+"");
				foodKcalField.setText(kcalVal+"");
			} catch (SQLException sqlex) {
				System.out.println("SQL 에러 : " + sqlex.getMessage());
				sqlex.printStackTrace();
			} catch (Exception ex) {
				System.out.println("DB Handling 에러(리스트 리스너) : " + ex.getMessage());
				ex.printStackTrace();
			}
		}
	}
	class unitRightButton implements ActionListener{
		public void actionPerformed(ActionEvent event){
			float unitVal = Float.valueOf(foodUnitField.getText());
			float gVal;
			float kcalVal;
			unitVal += 0.25;
			foodUnitField.setText(unitVal+"");
			try {
				Statement stmt = conn.createStatement();				// SQL 문장 만들기 위한 Statement 객체
				ResultSet rs = stmt.executeQuery("SELECT * FROM food WHERE name = '" +
						(String)foodNames.getSelectedValue() + "'");
				rs.next();// 여러개가 리턴되어도 첫번째 것으로 사용
				gVal = Float.valueOf(rs.getString("g"));
				kcalVal = Float.valueOf(rs.getString("kcal"));
				stmt.close();
				gVal *= unitVal;
				kcalVal *= unitVal;
				foodGField.setText(gVal+"");
				foodKcalField.setText(kcalVal+"");
			} catch (SQLException sqlex) {
				System.out.println("SQL 에러 : " + sqlex.getMessage());
				sqlex.printStackTrace();
			} catch (Exception ex) {
				System.out.println("DB Handling 에러(리스트 리스너) : " + ex.getMessage());
				ex.printStackTrace();
			}
		}
	}
	class unitTextButton implements ActionListener{
		public void actionPerformed(ActionEvent event){
			float unitVal = Float.valueOf(foodUnitField.getText());
			float gVal;
			float kcalVal;
			try {
				Statement stmt = conn.createStatement();				// SQL 문장 만들기 위한 Statement 객체
				ResultSet rs = stmt.executeQuery("SELECT * FROM food WHERE name = '" +
						(String)foodNames.getSelectedValue() + "'");
				rs.next();// 여러개가 리턴되어도 첫번째 것으로 사용
				gVal = Float.valueOf(rs.getString("g"));
				kcalVal = Float.valueOf(rs.getString("kcal"));
				stmt.close();
				gVal *= unitVal;
				kcalVal *= unitVal;
				foodGField.setText(gVal+"");
				foodKcalField.setText(kcalVal+"");
			} catch (SQLException sqlex) {
				System.out.println("SQL 에러 : " + sqlex.getMessage());
				sqlex.printStackTrace();
			} catch (Exception ex) {
				System.out.println("DB Handling 에러(리스트 리스너) : " + ex.getMessage());
				ex.printStackTrace();
			}
		}
	}
	class fitMinuteField implements ActionListener{
		public void actionPerformed(ActionEvent event){
			double level;
			if(fitComboBox.getSelectedItem().equals("가볍게")) level = 1.0;
			else if(fitComboBox.getSelectedItem().equals("보통")) level = 1.4;
			else level = 1.8;
			float minuteVal = Float.valueOf(fitMinuteField.getText());

			try {
				Statement stmt = conn.createStatement();				// SQL 문장 만들기 위한 Statement 객체
				ResultSet rs = stmt.executeQuery("SELECT * FROM exercise WHERE name = '" +
						(String)fitNames.getSelectedValue() + "'");
				rs.next();// 여러개가 리턴되어도 첫번째 것으로 사용
				fitKcalField.setText((int)(rs.getDouble("kcal")/rs.getDouble("minute")*minuteVal*level)+"");
			} catch (SQLException sqlex) {
				System.out.println("SQL 에러 : " + sqlex.getMessage());
				sqlex.printStackTrace();
			} catch (Exception ex) {
				System.out.println("DB Handling 에러(리스트 리스너) : " + ex.getMessage());
				ex.printStackTrace();
			}
		}
	}
	class foodEatButton implements ActionListener{
		public void actionPerformed(ActionEvent event){
			String currentDate = new SimpleDateFormat("YYYY-MM-dd").format(currentTime);
			String foodName = (String)foodNames.getSelectedValue();
			String foodCount = foodUnitField.getText();
			String foodDay;
			String food_id;
			if(morning.isSelected()) foodDay = morning.getText();
			else if(lunch.isSelected()) foodDay = lunch.getText();
			else if(diner.isSelected()) foodDay = diner.getText();
			else foodDay = snack.getText();

			try {
				Statement stmt = conn.createStatement();				// SQL 문장 만들기 위한 Statement 객체
				ResultSet rs = stmt.executeQuery("SELECT * FROM food WHERE name = '" +
						(String)foodNames.getSelectedValue() + "'");
				rs.next();
				food_id = rs.getString("food_id");
				stmt.executeUpdate("INSERT INTO meal(mi_id,date,food_id,count,day) VALUES (" + member + ",'" + currentDate + "', " + food_id + "," + foodCount + ",'" + foodDay +"');");

			} catch (SQLException sqlex) {
				System.out.println("SQL 에러 : " + sqlex.getMessage());
				sqlex.printStackTrace();
			} catch (Exception ex) {
				System.out.println("DB Handling 에러(리스트 리스너) : " + ex.getMessage());
				ex.printStackTrace();
			}

			foodpare();
			foodPanel.repaint();
		}
	}
	class fitDoButton implements ActionListener{
		public void actionPerformed(ActionEvent event){

			String currentDate = new SimpleDateFormat("YYYY-MM-dd").format(currentTime2);
			String exercise_id;
			String level;
			if(fitComboBox.getSelectedItem().equals("가볍게")) level = "1";
			else if(fitComboBox.getSelectedItem().equals("보통")) level = "1.4";
			else level = "1.8";
			try {
				Statement stmt = conn.createStatement();				// SQL 문장 만들기 위한 Statement 객체
				ResultSet rs = stmt.executeQuery("SELECT * FROM exercise WHERE name = '" +
						(String)fitNames.getSelectedValue() + "'");
				rs.next();
				exercise_id = rs.getString("exercise_id");

				stmt.executeUpdate("INSERT INTO mi_exercise(mi_id,date,exercise_id,exercise_minute,level) VALUES (" 
						+ member + ",'" + currentDate + "', " + exercise_id + "," + fitMinuteField.getText() + "," + level +");");

			} catch (SQLException sqlex) {
				System.out.println("SQL 에러 : " + sqlex.getMessage());
				sqlex.printStackTrace();
			} catch (Exception ex) {
				System.out.println("DB Handling 에러(리스트 리스너) : " + ex.getMessage());
				ex.printStackTrace();
			}
			fitpare();
			fitPanel.repaint();

		}
	}
	public class searchButton implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			int index = foodNames.getNextMatch(foodSearchField.getText().trim(), 0, Position.Bias.Forward);
			if (index != -1) {
				foodNames.setSelectedIndex(index);
			}
			foodSearchField.setText("");
		}
	}
	public class fitSearchButton implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			int index = fitNames.getNextMatch(fitSearchField.getText().trim(), 0, Position.Bias.Forward);
			if (index != -1) {
				fitNames.setSelectedIndex(index);
			}
			fitSearchField.setText("");
		}
	}
	class traningLeftButton implements ActionListener{
		public void actionPerformed(ActionEvent event){
			if(gifCnt > 1) gifCnt--;
			traningPanel.repaint();
		}
	}
	class traningRightButton implements ActionListener{
		public void actionPerformed(ActionEvent event){
			if(gitLastCnt != gifCnt)
				gifCnt++;
			traningPanel.repaint();
		}
	}
	private void dbConnectionInit() {
		try {
			Class.forName("com.mysql.jdbc.Driver");					// JDBC드라이버를 JVM영역으로 가져오기
			conn = DriverManager.getConnection("jdbc:mysql://localhost/sy_health", "root", "1234");	// DB 연결하기
			//prepareList();
		}
		catch (ClassNotFoundException cnfe) {
			System.out.println("JDBC 드라이버 클래스를 찾을 수 없습니다 : " + cnfe.getMessage());
		}
		catch (Exception ex) {
			System.out.println("DB 연결 에러 : " + ex.getMessage());
		}
	}
	public void mainpare(){
		String currentDate = new SimpleDateFormat("YY-MM-dd").format(new Date());

		try {
			Statement stmt = conn.createStatement();				// SQL 문을 작성을 위한  Statement 객체 생성
			Statement stmt2 = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM feedback WHERE feedback_id = " + feedback);
			rs.next();
			ResultSet rs2 = stmt2.executeQuery("SELECT * FROM member_info WHERE mi_id = " + member);
			rs2.next();
			feedbackLabel.setText(rs2.getString("name") + rs.getString("feedback"));
			nameLabel2.setText(rs2.getString("name"));
			if (rs2.getString("gender").equals("M"))			
				genderLabel2.setText("남성");
			else
				genderLabel2.setText("여성");
			birthdayLabel2.setText(rs2.getDate("birthday").toString());
			heightLabel2.setText(rs2.getString("height").substring(0, 3) + "cm");
			weightLabel2.setText(rs2.getString("weight").substring(0, 2) + "kg");
			BMInum = rs2.getDouble("weight")/((rs2.getDouble("height")/100)*(rs2.getDouble("height")/100));
			bmiValLabel.setText(String.format("%.2f",BMInum));
			if(BMInum < 18.6) {
				bmiValLabel2.setText("저체중입니다.");
				arrowX = (int)(88/18.6*BMInum+29);
				arrow.move(arrowX, 425);
			}
			else if(BMInum < 22.9) {
				arrowX = (int)(70/4.3*(BMInum-18.6)+117);
				arrow.move(arrowX, 425);
				bmiValLabel2.setText("정상입니다.");
			}
			else if(BMInum < 24.9) {
				arrowX = (int)(52/2*(BMInum-22.9)+187);
				arrow.move(arrowX, 425);
				bmiValLabel2.setText("과체중입니다.");
			}
			else if(BMInum < 30){
				arrowX = (int)(53/5.1*(BMInum-24.9)+239);
				arrow.move(arrowX, 425);
				bmiValLabel2.setText("비만입니다.");
			}
			else{
				arrowX = (int)(53/5.1*(BMInum-30)+292);
				arrow.move(arrowX, 425);
				bmiValLabel2.setText("고도비만입니다.");
			}

			Statement stmt3 = conn.createStatement();				// SQL 문을 작성을 위한  Statement 객체 생성
			ResultSet rs3 = stmt3.executeQuery("SELECT * FROM water WHERE mi_id = " + member + " AND date = '" + currentDate + "';");
			if(rs3.next())
				waterCount.setText((rs3.getInt("water_count"))+"");

			Statement stmt4 = conn.createStatement();				// SQL 문을 작성을 위한  Statement 객체 생성
			ResultSet rs4 = stmt4.executeQuery("SELECT * FROM caffeine WHERE mi_id = " + member + " AND date = '" + currentDate + "';");
			if(rs4.next())
				caffeineCount.setText((rs4.getInt("caffeine_count"))+"");
		} catch (SQLException sqlex) {
			System.out.println("SQL 에러 : " + sqlex.getMessage());
			sqlex.printStackTrace();
		} catch (Exception ex) {
			System.out.println("DB Handling 에러(SAVE 리스너) : " + ex.getMessage());
			ex.printStackTrace();
		}
	}
	public void foodpare(){
		String currentDate = new SimpleDateFormat("YYYY-MM-dd").format(currentTime);
		int modelRow = model.getRowCount();
		for(int i=0; i<modelRow; i++){
			model.removeRow(0);
		}
		try {
			Statement stmt = conn.createStatement();				// SQL 문을 작성을 위한  Statement 객체 생성
			ResultSet rs = stmt.executeQuery("SELECT goal_kcal FROM member_info WHERE mi_id = " + member);
			rs.next();
			goal_kcal = rs.getInt("goal_kcal");
			ResultSet rs2 = stmt.executeQuery("SELECT SUM((SELECT kcal FROM food WHERE food_id = meal.food_id)*count)food_sum FROM meal WHERE mi_id = " + member + " AND date = '" + currentDate +"';");
			if(rs2.next()) real_kcal = rs2.getInt("food_sum");
			else real_kcal = 0;
			foodKaclLabel.setText(real_kcal + "/" + goal_kcal);

			ResultSet rs3 = stmt.executeQuery("SELECT food.name name, (food.g)*count g, (food.kcal)*count kcal,day FROM meal NATURAL JOIN food WHERE mi_id = " + member + " AND date = '" + currentDate +"';");
			while(rs3.next()){
				Object data[] = new Object[4];
				data[0] = rs3.getString("name");
				data[1] = rs3.getInt("g");
				data[2] = rs3.getInt("kcal");
				data[3] = rs3.getString("day");
				model.addRow(data);
			}

			ResultSet rs4 = stmt.executeQuery("SELECT * FROM food ORDER BY name");
			Vector<String> list = new Vector<String>();
			while (rs4.next()) {
				list.add(rs4.getString("name"));		
			}
			stmt.close();										// statement는 사용후 닫는 습관
			foodNames.setListData(list);							// names의 각종 속성은 그대로 두고 내용물만 바꾼다
			if (!list.isEmpty())					
				foodNames.setSelectedIndex(foodCnt);  
		} catch (SQLException sqlex) {
			System.out.println("SQL 에러 : " + sqlex.getMessage());
			sqlex.printStackTrace();
		} catch (Exception ex) {
			System.out.println("DB Handling 에러(SAVE 리스너) : " + ex.getMessage());
			ex.printStackTrace();
		}
		if(real_kcal>goal_kcal && currentDate.equals(new SimpleDateFormat("YYYY-MM-dd").format(new Date()))){
			feedback = 2;
			mainpare();
		}
	}
	// 리스트 박스에 액션이 발생하면 처리하는 리스너
	public class foodListListener implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent lse) {	
			foodUnitField.setText("1");
			if (!lse.getValueIsAdjusting() && !foodNames.isSelectionEmpty()) {  // 현재 선택이 다 끝난 경우에 처리
				try {
					Statement stmt = conn.createStatement();				// SQL 문장 만들기 위한 Statement 객체
					ResultSet rs = stmt.executeQuery("SELECT * FROM food WHERE name = '" +
							(String)foodNames.getSelectedValue() + "'");
					rs.next();// 여러개가 리턴되어도 첫번째 것으로 사용
					foodUnitLabel.setText(rs.getString("unit"));
					foodGField.setText(rs.getString("g"));
					foodKcalField.setText(rs.getString("kcal"));
					stmt.close();
					foodCnt = foodNames.getSelectedIndex();
					foodNames.ensureIndexIsVisible(foodCnt);
				} catch (SQLException sqlex) {
					System.out.println("SQL 에러 : " + sqlex.getMessage());
					sqlex.printStackTrace();
				} catch (Exception ex) {
					System.out.println("DB Handling 에러(리스트 리스너) : " + ex.getMessage());
					ex.printStackTrace();
				}
			}
		}
	}
	public void fitpare(){
		String currentDate = new SimpleDateFormat("YYYY-MM-dd").format(currentTime2);
		int fitModelRow = fitModel.getRowCount();
		for(int i=0; i<fitModelRow; i++){
			fitModel.removeRow(0);
		}
		try {
			Statement stmt = conn.createStatement();				// SQL 문을 작성을 위한  Statement 객체 생성
			ResultSet rs = stmt.executeQuery("SELECT goal_fit FROM member_info WHERE mi_id = " + member);
			rs.next();
			goal_fit = rs.getInt("goal_fit");
			ResultSet rs2 = stmt.executeQuery("SELECT SUM((SELECT kcal/minute FROM exercise WHERE exercise_id = mi_exercise.exercise_id)*mi_exercise.exercise_minute*level) exercise_kcal FROM mi_exercise WHERE mi_id = " + member + " AND date = '" + currentDate +"';");
			if(rs2.next()) real_fit = rs2.getInt("exercise_kcal");
			else real_fit = 0;
			fitKaclLabel.setText(real_fit + "/" + goal_fit);

			ResultSet rs3 = stmt.executeQuery("SELECT e.name name, exercise_minute, TRUNCATE(e.kcal*(exercise_minute/e.minute)*level,0) kcal, level FROM mi_exercise NATURAL JOIN exercise e WHERE mi_id = " + member + " AND date = '" + currentDate +"';");
			while(rs3.next()){
				Object data[] = new Object[4];
				String level;
				if(rs3.getDouble("level") == 1) level = "가볍게";
				else if(rs3.getDouble("level") == 1.4) level = "보통";
				else level = "빠르게";
				data[0] = rs3.getString("name");
				data[1] = rs3.getInt("exercise_minute");
				data[2] = rs3.getInt("kcal");
				data[3] = level;
				fitModel.addRow(data);
			}
			ResultSet rs4 = stmt.executeQuery("SELECT * FROM exercise ORDER BY name");
			Vector<String> list = new Vector<String>();
			while (rs4.next()) {
				list.add(rs4.getString("name"));		
			}
			stmt.close();										// statement는 사용후 닫는 습관
			fitNames.setListData(list);							// names의 각종 속성은 그대로 두고 내용물만 바꾼다
			if (!list.isEmpty())								// 리스트가 바뀌고 나면 항상 첫번째 아이텀을 가리키게 
				fitNames.setSelectedIndex(fitCnt);  
		} catch (SQLException sqlex) {
			System.out.println("SQL 에러 : " + sqlex.getMessage());
			sqlex.printStackTrace();
		} catch (Exception ex) {
			System.out.println("DB Handling 에러(SAVE 리스너) : " + ex.getMessage());
			ex.printStackTrace();
		}
		if(real_fit>goal_fit && currentDate.equals(new SimpleDateFormat("YYYY-MM-dd").format(new Date()))){
			feedback = 3;
			mainpare();
		}
	}
	public void traningpare(){
		try {
			Statement stmt = conn.createStatement();				// SQL 문을 작성을 위한  Statement 객체 생성
			ResultSet rs4 = stmt.executeQuery("SELECT * FROM traning ORDER BY name");
			while (rs4.next()) {
				traningComboBox.addItem(rs4.getString("name"));	
			}
			stmt.close();										// statement는 사용후 닫는 습관
		} catch (SQLException sqlex) {
			System.out.println("SQL 에러 : " + sqlex.getMessage());
			sqlex.printStackTrace();
		} catch (Exception ex) {
			System.out.println("DB Handling 에러(SAVE 리스너) : " + ex.getMessage());
			ex.printStackTrace();
		}
	}
	public class fitListListener implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent lse) {	
			if (!lse.getValueIsAdjusting() && !fitNames.isSelectionEmpty()) {  // 현재 선택이 다 끝난 경우에 처리
				try {
					Statement stmt = conn.createStatement();				// SQL 문장 만들기 위한 Statement 객체
					ResultSet rs = stmt.executeQuery("SELECT * FROM exercise WHERE name = '" +
							(String)fitNames.getSelectedValue() + "'");
					rs.next();// 여러개가 리턴되어도 첫번째 것으로 사용
					fitMinuteField.setText(rs.getString("minute"));
					fitKcalField.setText(rs.getString("kcal"));
					fitEffectArea.setText(rs.getString("effect"));
					stmt.close();
					fitCnt = fitNames.getSelectedIndex();
				} catch (SQLException sqlex) {
					System.out.println("SQL 에러 : " + sqlex.getMessage());
					sqlex.printStackTrace();
				} catch (Exception ex) {
					System.out.println("DB Handling 에러(리스트 리스너) : " + ex.getMessage());
					ex.printStackTrace();
				}
			}
		}
	}
	public class traningComboBoxListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			traningName = (String)(traningComboBox.getSelectedItem());
			gifCnt = 1;
			try {
				Statement stmt = conn.createStatement();				// SQL 문을 작성을 위한  Statement 객체 생성
				ResultSet rs4 = stmt.executeQuery("SELECT * FROM traning WHERE name = '" + traningName + "'");
				rs4.next();
				gitLastCnt = rs4.getInt("traning_cnt");
				fitExpainLabel.setText(rs4.getString("explanation"));
				stmt.close();										// statement는 사용후 닫는 습관
			} catch (SQLException sqlex) {
				System.out.println("SQL 에러 : " + sqlex.getMessage());
				sqlex.printStackTrace();
			} catch (Exception ex) {
				System.out.println("DB Handling 에러(SAVE 리스너) : " + ex.getMessage());
				ex.printStackTrace();
			}
		}
	}
	public class DisplayButtonListener implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			// DB에서 가져오는 데이터를 rowObjects의 형태로 저장하고 이들의 리스트를 Printer 또는 Preview로 보내 줌
			ArrayList<RowObjects> rowList = new ArrayList<RowObjects>();	// 행들의 리스트
			RowObjects line;												// 하나의 행
			PrintObject word;												// 하나의 단어
			try {
	    		Statement stmt = conn.createStatement();					// SQL 문장 만들기 위한 Statement 객체
	    		ResultSet rs = stmt.executeQuery("SELECT * FROM meal NATURAL JOIN food WHERE mi_id = "+member+" ORDER BY date");
	    		while(rs.next()) {
	    			line = new RowObjects();								// 5개의 단어가 1줄
	    			line.add(new PrintObject(rs.getString("date"), 20));
	    			line.add(new PrintObject(rs.getString("name"), 20));
	    			line.add(new PrintObject(rs.getString("count"), 20));
	    			line.add(new PrintObject(rs.getString("day"), 5));
	    			rowList.add(line);										// 출력해야 될 전체 리스트를 만듬									
	    		}
	    		stmt.close();	
	    		// 각 페이지의 칼럼 헤더를 위해 한 줄 만들음
	    		line = new RowObjects();									// 5개의 단어가 1줄
    			line.add(new PrintObject("날짜", 20));
    			line.add(new PrintObject("음식이름", 20));
    			line.add(new PrintObject("인분", 20));
    			line.add(new PrintObject("때", 5));
    			if (e.getSource() == bPrint) {
	    			Printer prt = new Printer(new PrintObject("섭취 리스트", 20), line, rowList, true);
	    			prt.print();
    			}
    			else {
	    			Preview prv = new Preview(new PrintObject("섭취 리스트", 20), line, rowList, true);
	    			prv.preview();
    			}
    				
			} catch (SQLException sqlex) {
	    		System.out.println("SQL 에러 : " + sqlex.getMessage());
	    		sqlex.printStackTrace();
	    	} catch (Exception ex) {
	    		System.out.println("DB Handling 에러(리스트 리스너) : " + ex.getMessage());
	    		ex.printStackTrace();
	    	}
	    	
		}
	}
	public class DisplayButtonListener2 implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			// DB에서 가져오는 데이터를 rowObjects의 형태로 저장하고 이들의 리스트를 Printer 또는 Preview로 보내 줌
			ArrayList<RowObjects> rowList = new ArrayList<RowObjects>();	// 행들의 리스트
			RowObjects line;												// 하나의 행
			PrintObject word;												// 하나의 단어
			try {
	    		Statement stmt = conn.createStatement();					// SQL 문장 만들기 위한 Statement 객체
	    		ResultSet rs = stmt.executeQuery("SELECT * FROM mi_exercise NATURAL JOIN exercise WHERE mi_id = "+member+" ORDER BY date");
	    		while(rs.next()) {
	    			line = new RowObjects();								// 5개의 단어가 1줄
	    			line.add(new PrintObject(rs.getString("date"), 20));
	    			line.add(new PrintObject(rs.getString("name"), 20));
	    			line.add(new PrintObject(rs.getString("exercise_minute"), 20));
	    			if(rs.getDouble("level") == 1.0)line.add(new PrintObject("가볍게", 20));
	    			else if(rs.getDouble("level") == 1.4)line.add(new PrintObject("보통", 20));
	    			else line.add(new PrintObject("빠르게", 20));
	    			rowList.add(line);										// 출력해야 될 전체 리스트를 만듬									
	    		}
	    		stmt.close();	
	    		// 각 페이지의 칼럼 헤더를 위해 한 줄 만들음
	    		line = new RowObjects();									// 5개의 단어가 1줄
    			line.add(new PrintObject("날짜", 20));
    			line.add(new PrintObject("운동이름", 20));
    			line.add(new PrintObject("운동시간", 20));
    			line.add(new PrintObject("레벨", 5));
    			if (e.getSource() == aPrint) {
	    			Printer prt = new Printer(new PrintObject("운동 리스트", 20), line, rowList, true);
	    			prt.print();
    			}
    			else {
	    			Preview prv = new Preview(new PrintObject("운동 리스트", 20), line, rowList, true);
	    			prv.preview();
    			}
    				
			} catch (SQLException sqlex) {
	    		System.out.println("SQL 에러 : " + sqlex.getMessage());
	    		sqlex.printStackTrace();
	    	} catch (Exception ex) {
	    		System.out.println("DB Handling 에러(리스트 리스너) : " + ex.getMessage());
	    		ex.printStackTrace();
	    	}
	    	
		}
	}
}

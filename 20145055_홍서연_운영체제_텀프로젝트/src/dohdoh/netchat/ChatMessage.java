package dohdoh.netchat;
import java.io.Serializable;


public class ChatMessage implements Serializable {
	// �޽��� Ÿ�� ����
	// 1���� �޽��� ���� �ʵ�� 3���� String�� �ʵ�.
	// NO_ACT�� ������ �� �ִ� Dummy �޽���. ������ ������ ����ϱ� ���� ����� ����
	// (1) Ŭ���̾�Ʈ�� ������ �޽��� ����
	//	- LOGIN  : CLIENT �α���.
	//		�޽��� ���� : LOGIN, "�۽���", "", ""
	//	- LOGOUT : CLIENT �α׾ƿ�.
	//		�޽��� ���� : LOGOUT, "�۽���", "", ""
	// 	- CLIENT_MSG : �������� ������  ��ȭ .
	// 		�޽�������  : CLIENT_MSG, "�۽���", "������", "����"
	// (2) ������ ������ �޽��� ����
	// 	- LOGIN_FAILURE  : �α��� ����
	//		�޽��� ���� : LOGIN_FAILURE, "", "", "�α��� ���� ����"
	// 	- SERVER_MSG : Ŭ���̾�Ʈ���� �������� ������ ��ȭ 
	//		�޽�������  : SERVER_MSG, "�۽���", "", "����" 
	// 	- LOGIN_LIST : ���� �α����� ����� ����Ʈ.
	//		�޽��� ���� : LOGIN_LIST, "", "", "/�� ���е� ����� ����Ʈ"
	// (3) ����
	//  - GAME_START : ������ �����ϰų� ���� ���� �Ҷ�, ĳ���͵� ����
	//      �޼��� ���� : GAME_START,"userCheck", "�۽��� ĳ����", "�۽���"
	//  - GAME_DRAW : �׸��� �׸� �� �ʿ��� ��ǥ, ũ��, ����
	//      �޼��� ���� : GAME_DRAW,"x","y","h","w","c"
	//  - GAME_CURRECT : ������ ���߸� ����ٰ� ��� ������� �˸�
	//      �޼��� ���� : GAME_CURRECT,"���� ���"
	//  - GAME_TIME : ���� ȭ���� �ð��� ���� �����ϹǷ� ������ ���� ���� �ð��� ����
	//      �޼��� ���� : GAME_TIME,"���� �ð�"
	public enum MsgType {NO_ACT, LOGIN, LOGOUT, CLIENT_MSG, LOGIN_FAILURE, SERVER_MSG, LOGIN_LIST,
		GAME_START,GAME_DRAW,GAME_CURRECT,GAME_TIME};
	public static final String ALL = "��ü";	 // ����� �� �� �ڽ��� ������ ��� �α��εǾ� �ִ�
											 // ����ڸ� ��Ÿ���� �ĺ���
	private MsgType type;
	private String sender;
	private String receiver;
	private String contents;
	public boolean userCheck;
	String Currecter;
	int x,y,height,width,color;
	long time;
	int character;
	String startSender;
	public ChatMessage() {
		this(MsgType.NO_ACT, "", "", "");
	}
	public ChatMessage(MsgType t, String sID, String rID, String mesg) {
		type = t;
		sender = sID;
		receiver = rID;
		contents = mesg;
	}
	public ChatMessage(MsgType t, boolean uk,int character,String startSender){ // ���� ��ŸƮ
		type = t;
		userCheck = uk;
		this.character=character;
		this.startSender=startSender;
	}
	public ChatMessage(MsgType t, int i, int j,int h,int w,int c){ //���� ��ο�
		type = t;
		x=i;
		y=j;
		height=h;
		width=w;
		color=c;
	}
	public ChatMessage(MsgType t, String currecter){ //���� Ŀ��Ʈ
		type = t;
		this.Currecter = currecter;
	}
	
	public ChatMessage(MsgType t, long time){ //���� Ÿ��
		type = t;
		this.time = time;
	}
	public void setType (MsgType t) {
		type = t;
	}
	public MsgType getType() {
		return type;
	}

	public void setSender (String id) {
		sender = id;
	}
	public String getSender() {
		return sender;
	}
	
	public void setReceiver (String id) {
		receiver = id;
	}
	public String getReceiver() {
		return receiver;
	}
	
	public void setContents (String mesg) {
		contents = mesg;
	}
	public String getContents() {
		return contents;
	}
	public boolean isUserCheck() {
		return userCheck;
	}
	public void setUserCheck(boolean userCheck) {
		this.userCheck = userCheck;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getColor() {
		return color;
	}
	public void setColor(int color) {
		this.color = color;
	}
	public String toString() {
		return ("�޽��� ���� : " + type + "\n" +
				"�۽���         : " + sender + "\n" +
				"������         : " + receiver + "\n" +
				"�޽��� ���� : " + contents + "\n");
	}
}

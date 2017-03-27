package dohdoh.netchat;
import java.io.Serializable;


public class ChatMessage implements Serializable {
	// 메시지 타입 정의
	// 1개의 메시지 종류 필드와 3개의 String형 필드.
	// NO_ACT는 무시할 수 있는 Dummy 메시지. 디버깅용 등으로 사용하기 위해 만들어 놓음
	// (1) 클라이언트가 보내는 메시지 형식
	//	- LOGIN  : CLIENT 로그인.
	//		메시지 포맷 : LOGIN, "송신자", "", ""
	//	- LOGOUT : CLIENT 로그아웃.
	//		메시지 포맷 : LOGOUT, "송신자", "", ""
	// 	- CLIENT_MSG : 서버에게 보내는  대화 .
	// 		메시지포맷  : CLIENT_MSG, "송신자", "수신자", "내용"
	// (2) 서버가 보내는 메시지 형식
	// 	- LOGIN_FAILURE  : 로그인 실패
	//		메시지 포맷 : LOGIN_FAILURE, "", "", "로그인 실패 원인"
	// 	- SERVER_MSG : 클라이언트에게 원격으로 보내는 대화 
	//		메시지포맷  : SERVER_MSG, "송신자", "", "내용" 
	// 	- LOGIN_LIST : 현재 로그인한 사용자 리스트.
	//		메시지 포맷 : LOGIN_LIST, "", "", "/로 구분된 사용자 리스트"
	// (3) 게임
	//  - GAME_START : 게임을 시작하거나 새로 시작 할때, 캐릭터도 받음
	//      메세지 포맷 : GAME_START,"userCheck", "송신자 캐릭터", "송신자"
	//  - GAME_DRAW : 그림을 그릴 때 필요한 좌표, 크기, 색깔
	//      메세지 포맷 : GAME_DRAW,"x","y","h","w","c"
	//  - GAME_CURRECT : 정답을 맞추면 맞췄다고 모든 사람한테 알림
	//      메세지 포맷 : GAME_CURRECT,"맞춘 사람"
	//  - GAME_TIME : 양쪽 화면의 시간이 같이 가야하므로 문제를 내는 측의 시간을 공유
	//      메세지 포맷 : GAME_TIME,"남은 시간"
	public enum MsgType {NO_ACT, LOGIN, LOGOUT, CLIENT_MSG, LOGIN_FAILURE, SERVER_MSG, LOGIN_LIST,
		GAME_START,GAME_DRAW,GAME_CURRECT,GAME_TIME};
	public static final String ALL = "전체";	 // 사용자 명 중 자신을 제외한 모든 로그인되어 있는
											 // 사용자를 나타내는 식별문
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
	public ChatMessage(MsgType t, boolean uk,int character,String startSender){ // 게임 스타트
		type = t;
		userCheck = uk;
		this.character=character;
		this.startSender=startSender;
	}
	public ChatMessage(MsgType t, int i, int j,int h,int w,int c){ //게임 드로우
		type = t;
		x=i;
		y=j;
		height=h;
		width=w;
		color=c;
	}
	public ChatMessage(MsgType t, String currecter){ //게임 커렉트
		type = t;
		this.Currecter = currecter;
	}
	
	public ChatMessage(MsgType t, long time){ //게임 타임
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
		return ("메시지 종류 : " + type + "\n" +
				"송신자         : " + sender + "\n" +
				"수신자         : " + receiver + "\n" +
				"메시지 내용 : " + contents + "\n");
	}
}

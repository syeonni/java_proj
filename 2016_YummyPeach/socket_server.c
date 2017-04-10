#include <sys/socket.h>
#include <sys/stat.h>
#include <arpa/inet.h>
#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <errno.h>
#include <stdlib.h>
#include <termios.h>
#include <wiringPi.h>
#include <softTone.h>
#include "pitches.h"

#define PORT	8081
#define MAXBUF 80				/* 클라이언트와 길이를 맞추어준다. */

#define l	0

#define START	2
#define EXIT	6
#define STARTGAME	10
#define STARTMUSIC	7
#define RESTART		11

#define	CDS	7	/* GPIO17 */	
#define	SW		0	/* GPIO24 */
#define	SW2	15
#define	SPKR	2	/* GPIO25 */
#define	LEDR	8	/* GPIO21 */
#define	LEDG	9	/* GPIO21 */
#define TRIG 3
#define ECHOPIN 12

int isEnd = 1;
int gpiopins[4] = {4,1,16,15};    /* A, B, C, D : 23 20 15 14 */    	// FND 출력을 위한 핀 설정
int number[10][4] = { {0,0,0,0},      /* 0 */						// FND 번호 출력을 위한 배열
	{0,0,0,1},      /* 1 */
	{0,0,1,0},      /* 2 */
	{0,0,1,1},      /* 3 */
	{0,1,0,0},      /* 4 */
	{0,1,0,1},      /* 5 */
	{0,1,1,0},      /* 6 */
	{0,1,1,1},      /* 7 */
	{1,0,0,0},      /* 8 */
	{1,0,0,1} };    /* 9 */

#define STARTTOTAL	3
#define GAMETOTAL	75
#define EATTOTAL	4
#define ENDTOTAL	7

int gamenotes[] = {		/* 게임을 연주하기 위한 계이름 */
//75

    NOTE_E7, NOTE_E7, 0, NOTE_E7, 0, NOTE_C7, NOTE_E7, 0, NOTE_G7, 0, 0,  0, NOTE_G6, 0, 0, 0, 
  NOTE_C7, 0, 0, NOTE_G6, 0, 0, NOTE_E6, 0, 0, NOTE_A6, 0, NOTE_B6, 0, NOTE_AS6, NOTE_A6, 0, 
  NOTE_G6, NOTE_E7, NOTE_G7, NOTE_A7, 0, NOTE_F7, NOTE_G7, 0, NOTE_E7, 0,NOTE_C7, NOTE_D7, NOTE_B6, 0, 0,
  NOTE_C7, 0, 0, NOTE_G6, 0, 0, NOTE_E6, 0, 0, NOTE_A6, 0, NOTE_B6, 0, NOTE_AS6, NOTE_A6, 0, 
  NOTE_G6, NOTE_E7, NOTE_G7, NOTE_A7, 0, NOTE_F7, NOTE_G7, 0, NOTE_E7, 0,NOTE_C7, NOTE_D7, NOTE_B6, 0, 0
/*
293,391,466, 587,587,587,587,523,466,440,466,466,466,466,466,466,
391,466,587,783,783,783,783,783,880,698,622,698,698,698,698,698,698,
440,587,698,880,880,783,698,659,698,783,783,698,659,659,587,
523,466,523,587,523,391,440,440,440,440,440,440,440,440,440,440,
293,391,466,587,587,587,523,466,440,466,466,466,
391,466,587,783,783,783,783,698,622,698,783,783,698,659,659,587,
523,466,523,587,523,391,440,440,440,
293,391,466,587,587,587,523,466,440,466,466,466,
391,466,587,783,783,783,783,880,698,622,698,698,698,
440,587,698,880,880,783,698,659,622,659,783,783,698,659,587,277,587*/
};

int eatnotes[] = {		/* 게임을 연주하기 위한 계이름 */
    440,440,523,0
};

int endnotes[] = {		/* 게임을 연주하기 위한 계이름 */
    880, 880, 932.33, 1107.73, 1174.66, 1174.66,0
};

pthread_mutex_t startmusic_lock;
pthread_mutex_t gamemusic_lock;
pthread_mutex_t eatmusic_lock;
pthread_mutex_t endmusic_lock;
pthread_t ptgameMusic, ptstartMusic, pteatMusic, ptendMusic;

int client_sockfd; 	// 소켓
char buf[MAXBUF], sendBuf[MAXBUF], peach[MAXBUF];
int read_len;
int peachFlag = 1;


void *startmusicfunction(void *arg) 							// 임계구역이 보호된 음악연주
{
	if (pthread_mutex_trylock(&startmusic_lock) != EBUSY) {	// 된 상황에서는 브로킹 안되고 return
		startmusicPlay();
		pthread_mutex_unlock(&startmusic_lock);
	}
	return NULL;
}

void *eatmusicfunction(void *arg) 							// 임계구역이 보호된 음악연주
{
	if (pthread_mutex_trylock(&eatmusic_lock) != EBUSY) {	// 된 상황에서는 브로킹 안되고 return
		eatmusicPlay();
		pthread_mutex_unlock(&eatmusic_lock);
	}
	return NULL;
}
void *endmusicfunction(void *arg) 							// 임계구역이 보호된 음악연주
{
	if (pthread_mutex_trylock(&endmusic_lock) != EBUSY) {	// 된 상황에서는 브로킹 안되고 return
		endmusicPlay();
		pthread_mutex_unlock(&endmusic_lock);
	}
	return NULL;
}

int startmusicPlay()
{
    int i ;

    softToneCreate(SPKR) ; 			/* 톤 출력을 위한 GPIO 설정 */
    for (i = 0; i < GAMETOTAL; ++i) {
        softToneWrite(SPKR, gamenotes[i]);	/* 톤 출력 : 시작 연주 */
        delay(200); 					/* 음의 전체 길이만큼 출력되도록 대기 */
	if(isEnd == 0){
		softToneWrite(SPKR, 0);
		break;
	}
    }
	if(isEnd == 1){
		pthread_mutex_init(&startmusic_lock, NULL);
		pthread_create(&ptstartMusic, NULL, startmusicfunction, NULL);	
	}
    return 0;
}

int eatmusicPlay()
{
	int i;
	softToneCreate(SPKR) ; 			/* 톤 출력을 위한 GPIO 설정 */
	    for (i = 0; i < EATTOTAL; ++i) {
		softToneWrite(SPKR, eatnotes[i]);	/* 톤 출력 : 학교종 연주 */
		delay(280); 					/* 음의 전체 길이만큼 출력되도록 대기 */
	    }
	 return 0;
}
int endmusicPlay()
{
	int i;
	softToneCreate(SPKR) ; 			/* 톤 출력을 위한 GPIO 설정 */
	    for (i = 0; i < ENDTOTAL; ++i) {
		softToneWrite(SPKR, endnotes[i]);	/* 톤 출력 : 학교종 연주 */
		delay(280); 					/* 음의 전체 길이만큼 출력되도록 대기 */
	    }
	 return 0;
}

int ledControl(int gpio, int onoff)
{
    pinMode(gpio, OUTPUT) ; 			/* 핀(Pin)의 모드 설정 */
    digitalWrite(gpio, (onoff)?HIGH:LOW); 	/* LED 켜고 끄기 */

    return 0;
}

int tenFndControl(int num)
{
	   int gpiopins[4] = {29,28,27,26};     /* A, B, C, D : 23 18 15 14 */
	   int  i,j;
	   int number[10][4] = { {0,0,0,0},      /* 0 */
		                 {0,0,0,1},      /* 1 */
		                 {0,0,1,0},      /* 2 */
		                 {0,0,1,1},      /* 3 */
		                 {0,1,0,0},      /* 4 */
		                 {0,1,0,1},      /* 5 */
		                 {0,1,1,0},      /* 6 */
		                 {0,1,1,1},      /* 7 */
		                 {1,0,0,0},      /* 8 */
		                 {1,0,0,1} };    /* 9 */

	   for (i = 0; i < 4; i++) {
	       pinMode(gpiopins[i], OUTPUT) ;                /* 모든 Pin의 출력 설정 */
	   }

	   for (i = 0; i < 4; i++) {
	      digitalWrite(gpiopins[i], number[num][i]?HIGH:LOW);         /* HIGH(1) 값을 출력 : LED 켜기 */
	   };

	   for (i = 0; i < 4; i++) {
	     digitalWrite(number[num][i], LOW);           /* LOW (0) 값을 출력 : LED끄기*/
	   };


	   return 0;
}

int oneFndControl(int num)
{
	   int gpiopins[4] = {25,24,23,22};     /* A, B, C, D : 23 18 15 14 */
	   int  i,j;
	   int number[10][4] = { {0,0,0,0},      /* 0 */
		                 {0,0,0,1},      /* 1 */
		                 {0,0,1,0},      /* 2 */
		                 {0,0,1,1},      /* 3 */
		                 {0,1,0,0},      /* 4 */
		                 {0,1,0,1},      /* 5 */
		                 {0,1,1,0},      /* 6 */
		                 {0,1,1,1},      /* 7 */
		                 {1,0,0,0},      /* 8 */
		                 {1,0,0,1} };    /* 9 */

	   for (i = 0; i < 4; i++) {
	       pinMode(gpiopins[i], OUTPUT) ;                /* 모든 Pin의 출력 설정 */
	   }

	   for (i = 0; i < 4; i++) {
	      digitalWrite(gpiopins[i], number[num][i]?HIGH:LOW);         /* HIGH(1) 값을 출력 : LED 켜기 */
	   };

	   for (i = 0; i < 4; i++) {
	     digitalWrite(number[num][i], LOW);           /* LOW (0) 값을 출력 : LED끄기*/
	   };

	   return 0;
}



int main(int argc, char **argv) {
	int server_sockfd; 	// 소켓
	int choice;
	char dummy[MAXBUF];
	struct sockaddr_in serveraddr, clientaddr;  // 서버와 클라이언트의 소켓 정보
	int client_len;						// 클라이언트 소켓의 바이트 크기
	int chk_bind; 					// 연결 확인 변수
	int start_time, end_time;
	int distance;

	int i = 0, sFlag1 = 0, sFlag2 = 0;
	pthread_t ptSwitch, ptCds, ptMusic, ptFnd, ptstartMusic, pteatMusic, ptendMusic;

	pthread_mutex_init(&startmusic_lock, NULL);
	pthread_mutex_init(&eatmusic_lock, NULL);
	pthread_mutex_init(&endmusic_lock, NULL);

	wiringPiSetup();

	client_len = sizeof(clientaddr);

	/* 서버 socket() 생성  */
	server_sockfd = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP);
	if(server_sockfd == -1) {
		perror("socket error : ");
		exit(0);
	}
	/* bind() */
	bzero(&serveraddr, sizeof(serveraddr)); 	// 해당 메모리 영역 초기화
	serveraddr.sin_family = AF_INET;
	serveraddr.sin_addr.s_addr = htonl(INADDR_ANY);
	serveraddr.sin_port = htons(PORT);		// 포트 번호 설정
	chk_bind = bind(server_sockfd, (struct sockaddr *)&serveraddr, sizeof(serveraddr));
	if(chk_bind > 0) {
		perror("bind error : ");
		exit(0);
	}

	/* listen() */
	if(listen(server_sockfd, 5)) {
		perror("listen error : ");
	}

	while(1){
		/* 클라이언트 연결 대기 */
		/* accept() */
		client_sockfd = accept(server_sockfd, (struct sockaddr *)&clientaddr, &client_len);
		printf("New Client Connect: %s\n", inet_ntoa(clientaddr.sin_addr));
		tenFndControl(0); //점수를 00으로 세트
		oneFndControl(0);
		ledControl(LEDR, 1); //게임 시작 전 빨간불만 켜짐
		ledControl(LEDG, 0);
		for(i = 0; ;i++) {
			memset(buf, 0x00, MAXBUF);
			read_len = read(client_sockfd, buf, MAXBUF);			
			printf("클라이언트로부터 받은 값: %s\n", buf);

			choice =atoi(buf);

			switch(choice) {
				case START:				//START케이스일 때 게임 시작 SET
					printf("게임 ON\n");

					pinMode(SW, INPUT); 			/* 핀의 모드를 입력으로 설정 */
					pinMode(SW2, INPUT); 
					 pinMode(LEDR, OUTPUT);
					pinMode(LEDG, OUTPUT);
					pinMode(CDS, INPUT);

					    for (i = 0; ; i++) {				// 무한 루프
						if(digitalRead(SW) == HIGH) sFlag1 = 0; 
						if(digitalRead(SW) == LOW && sFlag1 == 0) { 	//'a'버튼
							printf("'a'눌림 \n");
							sFlag1 = 1;
							sendBuf[0] = 'a';
							sendBuf[1] = '\n';
							 if(write(client_sockfd, sendBuf, 2) <= 0)
								perror("write( )");
						}
						if(digitalRead(SW2) == HIGH) sFlag2 = 0; 		//채터링
						if(digitalRead(SW2) == LOW && sFlag2 == 0) { 	//'b'버튼
							printf("'b'눌림 \n");
							sFlag2 = 1;
							sendBuf[0] = 'b';
							sendBuf[1] = '\n';
  							 if(write(client_sockfd, sendBuf, 2) <= 0)
  					   		  	perror("write( )");
						}
						if(digitalRead(CDS) == LOW) { 	//빛이 없으면 게임 시작
						    printf("게임 시작\n");
							sendBuf[0] = '2';
							sendBuf[1] = '\n';
							ledControl(LEDR, 0);
							ledControl(LEDG, 1);
							/* 클라이언트로 buf에 있는 문자열 전송*/
  						   if(write(client_sockfd, sendBuf,2) <= 0)
  					   		  perror("write( )");
						    break;						// 한번 스위치 작동하면 끝냄
						}
					    };
					break;
				case STARTGAME: 				/* 읽은 문자가 f이면 별도의 스레드를 사용하여 FND에 표시한다. */
					printf("초음파\n");
					pinMode(TRIG, OUTPUT);
					pinMode(ECHO, INPUT);

					while(1){
						digitalWrite(TRIG, LOW);
						delay(500);
						digitalWrite(TRIG, HIGH);
						delayMicroseconds(10);
						digitalWrite(TRIG, LOW);
						while(digitalRead(ECHOPIN)==0);
						start_time = micros();
						while(digitalRead(ECHOPIN)==1);
						end_time = micros();
						distance = (end_time - start_time) / 29. / 2. ;
						
						buf[0] = (distance/10) + '0';
						buf[1] = (distance%10) + '0';
						if(distance < 100) {
							printf("distance %d cm\n \n", distance);
							if(write(client_sockfd, buf, read_len) <= 0)
  					   		  	perror("write( )");
							 read(client_sockfd, buf, MAXBUF);
							printf("받은 값%s\n",buf);	
							if(peachFlag == 1){
								peachFlag = 0;
								strcpy(peach,buf);
							}
							if(atoi(buf) == 99){
								printf("끝\n");	
								isEnd = 0;
								ledControl(LEDR, 1);
								ledControl(LEDG, 0);
								pthread_create(&ptendMusic, NULL, endmusicfunction, NULL);
								break;
							}
							else{
								tenFndControl(atoi(buf)/10);
								oneFndControl(atoi(buf)%10);
							}
							if(strcmp(buf, peach) != 0){
								printf("먹음\n");
								pthread_create(&pteatMusic, NULL, eatmusicfunction, NULL);
								strcpy(peach,buf);
							}
							
						}
					}
					break;
				case STARTMUSIC: 			//배경음악 스레드로 재생
					printf("시작노래\n");
					pthread_create(&ptstartMusic, NULL, startmusicfunction, NULL);
					break;	
				case RESTART: 			//배경음악 스레드로 재생
					ledControl(LEDR, 1); //게임 시작 전 빨간불만 켜짐
					ledControl(LEDG, 0);
					 for (i = 0; ; i++) {				// 무한 루프
						if(digitalRead(SW) == HIGH) sFlag1 = 0; 
						if(digitalRead(SW) == LOW && sFlag1 == 0) { 	//'a'버튼
							printf("'재시작 \n");
							pthread_create(&ptstartMusic, NULL, startmusicfunction, NULL);
							
							ledControl(LEDR, 0);
							ledControl(LEDG, 1);
							
							isEnd=1;
							peachFlag = 1;
							sFlag1 = 0;
							sFlag2 = 0;
							
							sFlag1 = 1;
							sendBuf[0] = 'a';
							sendBuf[1] = '\n';
							 if(write(client_sockfd, sendBuf, 2) <= 0)
								perror("write( )");
							break;
						}
						if(digitalRead(SW2) == HIGH) sFlag2 = 0; 		//채터링

						if(digitalRead(SW2) == LOW && sFlag2 == 0) { 	//'b'버튼
							printf("'종료\n");
							choice = EXIT;
							sendBuf[0] = 'b';
							sendBuf[1] = '\n';
							 if(write(client_sockfd, sendBuf, 2) <= 0)
								perror("write( )");
							delay(100);
							
							break;
						}
					}

					break;			
				
			}
			if(choice == EXIT){
				return;
			}
			
		}
		printf("새 클라이언트 접속을 기다림\n");
	}
}


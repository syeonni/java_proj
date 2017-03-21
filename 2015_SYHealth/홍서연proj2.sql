CREATE TABLE member_info(
	mi_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	id VARCHAR(30),
	password VARCHAR(30),
	name VARCHAR(10),
	gender CHAR(1),
	birthday DATE,
	height DEC(5,2),
	weight DEC(5,2),
	goal_kcal INT,
	goal_fit INT
);

CREATE TABLE feedback(
	feedback_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	feedback VARCHAR(200)
);
INSERT INTO feedback(feedback)VALUES('님 안녕하세요!!');
INSERT INTO feedback(feedback)VALUES('님 목표 칼로리를 초과하셨어요!');
INSERT INTO feedback(feedback)VALUES('님 목표 운동시간을 다 하셨어요 대단해요 ><');
INSERT INTO feedback(feedback)VALUES('님 하루 권장 물을 다 마셨어요!');
INSERT INTO feedback(feedback)VALUES('님 많은 양의 카페인은 몸에 좋지않아요 ㅜㅜ');
INSERT INTO feedback(feedback)VALUES('님 밥먹기 전후 30분 물을 마시면 소화에 좋지 않대요!');
INSERT INTO feedback(feedback)VALUES('님 밀가루 음식은 소화를 방해하고 얼굴에 트러블을 일으킨데요');
INSERT INTO feedback(feedback)VALUES('님 술은 적당히 !');
INSERT INTO feedback(feedback)VALUES('님 음주 전에 마시는 갈아 만든 배는 숙취에 좋아요 *.*');
INSERT INTO feedback(feedback)VALUES('님 운동 후 수분보충이 아주 중요하답니다 !');
INSERT INTO feedback(feedback)VALUES('님 겨울에 숙취가 더 오래간데여 ! 술은 조금만 ~.~');
INSERT INTO feedback(feedback)VALUES('님 레몬은 노폐물 배출에 최고 !');
INSERT INTO feedback(feedback)VALUES('님 신발이 한쪽만 닳는다면 척추에 이상이 있을 수도 있데요 ㅜ');

CREATE TABLE water(
	mi_id INT,
	date DATE,
	water_count INT
);
ALTER TABLE water ADD FOREIGN KEY(mi_id) REFERENCES member_info(mi_id);
ALTER TABLE water ADD PRIMARY KEY(mi_id,date);

CREATE TABLE caffeine(
	mi_id INT,
	date DATE,
	caffeine_count INT
);
ALTER TABLE caffeine ADD FOREIGN KEY(mi_id) REFERENCES member_info(mi_id);
ALTER TABLE caffeine ADD PRIMARY KEY(mi_id,date);

CREATE TABLE food(
	food_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(100),
	kcal INT,
	g INT,
	unit VARCHAR(20)
);

CREATE TABLE meal(
	meal_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	mi_id INT,
	date DATE,
	food_id INT,
	count DEC(4,2),
	day VARCHAR(20)
);
ALTER TABLE meal ADD FOREIGN KEY(mi_id) REFERENCES member_info(mi_id);
ALTER TABLE meal ADD FOREIGN KEY(food_id) REFERENCES food(food_id);

INSERT INTO food(name,kcal,g,unit)VALUES('콩나물해장국',223,700,'인분');
INSERT INTO food(name,kcal,g,unit)VALUES('소머리국밥',631,800,'인분');
INSERT INTO food(name,kcal,g,unit)VALUES('김밥',317,250,'줄');
INSERT INTO food(name,kcal,g,unit)VALUES('김치김밥',344,250,'줄');
INSERT INTO food(name,kcal,g,unit)VALUES('샐러드김밥',405,250,'줄');
INSERT INTO food(name,kcal,g,unit)VALUES('소고기김밥',400,250,'줄');
INSERT INTO food(name,kcal,g,unit)VALUES('참치김밥',424,250,'줄');
INSERT INTO food(name,kcal,g,unit)VALUES('치즈김밥',425,250,'줄');
INSERT INTO food(name,kcal,g,unit)VALUES('충무김밥',583,400,'인분');
INSERT INTO food(name,kcal,g,unit)VALUES('고추장불고기삼각김밥',165,100,'개');
INSERT INTO food(name,kcal,g,unit)VALUES('참치마요네즈삼각김밥',161,100,'개');
INSERT INTO food(name,kcal,g,unit)VALUES('숯불갈비삼각김밥',172,100,'개');
INSERT INTO food(name,kcal,g,unit)VALUES('제육덮밥',782,500,'인분');
INSERT INTO food(name,kcal,g,unit)VALUES('불고기덮밥',669,500,'인분');
INSERT INTO food(name,kcal,g,unit)VALUES('오징어덮밥',679,500,'인분');
INSERT INTO food(name,kcal,g,unit)VALUES('장어덮밥',716,400,'인분');
INSERT INTO food(name,kcal,g,unit)VALUES('해물덮밥',771,700,'인분');
INSERT INTO food(name,kcal,g,unit)VALUES('회덮밥',683,500,'인분');
INSERT INTO food(name,kcal,g,unit)VALUES('참치덮밥',679,500,'인분');
INSERT INTO food(name,kcal,g,unit)VALUES('류산슬덮밥',574,500,'인분');
INSERT INTO food(name,kcal,g,unit)VALUES('볶음밥',400,400,'인분');
INSERT INTO food(name,kcal,g,unit)VALUES('김치볶음밥',754,500,'인분');
INSERT INTO food(name,kcal,g,unit)VALUES('새우볶음밥',700,400,'인분');
INSERT INTO food(name,kcal,g,unit)VALUES('해물볶음밥',705,400,'인분');
INSERT INTO food(name,kcal,g,unit)VALUES('오므라이스',729,450,'인분');
INSERT INTO food(name,kcal,g,unit)VALUES('삼선볶음밥',705,400,'인분');
INSERT INTO food(name,kcal,g,unit)VALUES('비빔밥',706,500,'인분');
INSERT INTO food(name,kcal,g,unit)VALUES('육회비빔밥',679,450,'인분');
INSERT INTO food(name,kcal,g,unit)VALUES('생선초밥(광어)',453,300,'인분');
INSERT INTO food(name,kcal,g,unit)VALUES('생선초밥(모둠)',461,300,'인분');
INSERT INTO food(name,kcal,g,unit)VALUES('농어초밥',396,250,'인분');
INSERT INTO food(name,kcal,g,unit)VALUES('문어초밥',392,250,'인분');
INSERT INTO food(name,kcal,g,unit)VALUES('연어초밥',447,250,'인분');
INSERT INTO food(name,kcal,g,unit)VALUES('새우초밥',387,250,'인분');
INSERT INTO food(name,kcal,g,unit)VALUES('장어초밥',486,250,'인분');
INSERT INTO food(name,kcal,g,unit)VALUES('한치초밥',374,250,'인분');
INSERT INTO food(name,kcal,g,unit)VALUES('유부초밥',446,250,'인분');
INSERT INTO food(name,kcal,g,unit)VALUES('잡채밥',884,650,'인분');
INSERT INTO food(name,kcal,g,unit)VALUES('잡탕밥',884,640,'인분');
INSERT INTO food(name,kcal,g,unit)VALUES('자장밥',741,500,'인분');
INSERT INTO food(name,kcal,g,unit)VALUES('카레라이스',672,500,'인분');
INSERT INTO food(name,kcal,g,unit)VALUES('알밥',618,400,'인분');
INSERT INTO food(name,kcal,g,unit)VALUES('짬뽕밥',661,900,'인분');
INSERT INTO food(name,kcal,g,unit)VALUES('오곡밥',390,250,'인분');
INSERT INTO food(name,kcal,g,unit)VALUES('새우튀김롤',606,300,'인분');
INSERT INTO food(name,kcal,g,unit)VALUES('연어롤',510,300,'인분');
INSERT INTO food(name,kcal,g,unit)VALUES('캘리포니아롤',487,300,'인분');
INSERT INTO food(name,kcal,g,unit)VALUES('꽈배기',262,65,'개');
INSERT INTO food(name,kcal,g,unit)VALUES('찹쌀도우넛',207,70,'개');
INSERT INTO food(name,kcal,g,unit)VALUES('만주',222,70,'개');
INSERT INTO food(name,kcal,g,unit)VALUES('머핀',560,130,'개');
INSERT INTO food(name,kcal,g,unit)VALUES('베이글',335,120,'개');
INSERT INTO food(name,kcal,g,unit)VALUES('곰보빵',227,70,'개');
INSERT INTO food(name,kcal,g,unit)VALUES('마늘빵',296,70,'개');
INSERT INTO food(name,kcal,g,unit)VALUES('모닝빵',231,70,'개');
INSERT INTO food(name,kcal,g,unit)VALUES('버터크림빵',229,60,'개');
INSERT INTO food(name,kcal,g,unit)VALUES('페이스트리빵',319,70,'개');
INSERT INTO food(name,kcal,g,unit)VALUES('카스텔라',235,70,'개');
INSERT INTO food(name,kcal,g,unit)VALUES('햄치즈샌드위치',436,200,'개');
INSERT INTO food(name,kcal,g,unit)VALUES('생크림케이크',278,100,'조각');
INSERT INTO food(name,kcal,g,unit)VALUES('초콜릿케이크',420,100,'조각');
INSERT INTO food(name,kcal,g,unit)VALUES('치즈케이크',329,100,'조각');
INSERT INTO food(name,kcal,g,unit)VALUES('채소고로케',300,100,'개');
INSERT INTO food(name,kcal,g,unit)VALUES('한과,산자',109,30,'개');
INSERT INTO food(name,kcal,g,unit)VALUES('한과,약과',119,30,'개');
INSERT INTO food(name,kcal,g,unit)VALUES('한과,매작과',123,30,'개');
INSERT INTO food(name,kcal,g,unit)VALUES('한과,송화다식',110,30,'개');
INSERT INTO food(name,kcal,g,unit)VALUES('한과,유과',127,30,'개');
INSERT INTO food(name,kcal,g,unit)VALUES('자장면',796,650,'그릇');
INSERT INTO food(name,kcal,g,unit)VALUES('간자장',824,650,'그릇');
INSERT INTO food(name,kcal,g,unit)VALUES('삼선자장면',803,700,'그릇');
INSERT INTO food(name,kcal,g,unit)VALUES('막국수',599,550,'인분');
INSERT INTO food(name,kcal,g,unit)VALUES('비빔국수',617,550,'인분');
INSERT INTO food(name,kcal,g,unit)VALUES('잔치국수',599,700,'인분');
INSERT INTO food(name,kcal,g,unit)VALUES('쫄면',601,450,'인분');
INSERT INTO food(name,kcal,g,unit)VALUES('물냉면',551,800,'인분');
INSERT INTO food(name,kcal,g,unit)VALUES('비빔냉면',622,550,'인분');
INSERT INTO food(name,kcal,g,unit)VALUES('열무냉면',525,800,'인분');
INSERT INTO food(name,kcal,g,unit)VALUES('회냉면',629,550,'인분');
INSERT INTO food(name,kcal,g,unit)VALUES('떡국',711,800,'인분');
INSERT INTO food(name,kcal,g,unit)VALUES('떡만둣국',624,700,'인분');
INSERT INTO food(name,kcal,g,unit)VALUES('고기만두',452,250,'인분');
INSERT INTO food(name,kcal,g,unit)VALUES('군만두',685,250,'인분');
INSERT INTO food(name,kcal,g,unit)VALUES('김치만두',420,250,'인분');
INSERT INTO food(name,kcal,g,unit)VALUES('물만두',156,120,'인분');
INSERT INTO food(name,kcal,g,unit)VALUES('만둣국',434,700,'인분');
INSERT INTO food(name,kcal,g,unit)VALUES('수제비',646,800,'인분');
INSERT INTO food(name,kcal,g,unit)VALUES('오일소스스파게티',646,400,'인분');
INSERT INTO food(name,kcal,g,unit)VALUES('크림소스스파게티',837,400,'인분');
INSERT INTO food(name,kcal,g,unit)VALUES('토마토소스스파게티',642,500,'인분');
INSERT INTO food(name,kcal,g,unit)VALUES('해물크림소스스파게티',917,500,'인분');
INSERT INTO food(name,kcal,g,unit)VALUES('우동(일식)',421,700,'인분');
INSERT INTO food(name,kcal,g,unit)VALUES('우동(중식)',648,1000,'인분');
INSERT INTO food(name,kcal,g,unit)VALUES('김치우동',500,800,'인분');
INSERT INTO food(name,kcal,g,unit)VALUES('삼선우동',691,1000,'인분');
INSERT INTO food(name,kcal,g,unit)VALUES('짬뽕',687,1000,'인분');
INSERT INTO food(name,kcal,g,unit)VALUES('굴짬뽕',680,900,'인분');
INSERT INTO food(name,kcal,g,unit)VALUES('삼성짬뽕',662,900,'인분');
INSERT INTO food(name,kcal,g,unit)VALUES('해물칼국수',628,900,'인분');

CREATE TABLE exercise(
	exercise_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(100),
	minute INT,
	kcal INT,
	effect VARCHAR(100)
);
CREATE TABLE mi_exercise(
	me_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	mi_id INT,
	date DATE,
	exercise_id INT,
	exercise_minute INT,
	level DEC(2,1)
);
ALTER TABLE mi_exercise ADD FOREIGN KEY(mi_id) REFERENCES member_info(mi_id);
ALTER TABLE mi_exercise ADD FOREIGN KEY(exercise_id) REFERENCES exercise(exercise_id);

INSERT INTO exercise(name,minute,kcal,effect) VALUES('걷기',60,156,'일상생활의 소모 칼로리에요.');
INSERT INTO exercise(name,minute,kcal,effect) VALUES('싸이클실내자전거',60,300,'하체 근육 강화 유산소 운동이에요.');
INSERT INTO exercise(name,minute,kcal,effect) VALUES('스트레칭',60,240,'운동 전,후에 해주면 좋아요.');
INSERT INTO exercise(name,minute,kcal,effect) VALUES('런닝머신걷기',60,160,'체중 조절에 효과적이에요.');
INSERT INTO exercise(name,minute,kcal,effect) VALUES('L자다리운동',60,360,'엉덩이를 벽에 딱 ! 붙힌후 다리를 올려주세요.');
INSERT INTO exercise(name,minute,kcal,effect) VALUES('스쿼트',60,420,'허벅지 근육 강화와 힙업을 시켜준답니다.');
INSERT INTO exercise(name,minute,kcal,effect) VALUES('서있기',60,86,'운동효과가거의 없습니다.');
INSERT INTO exercise(name,minute,kcal,effect) VALUES('산책 걷기',60,120,'동맥경화 예방에 좋아요.');
INSERT INTO exercise(name,minute,kcal,effect) VALUES('줄넘기',60,600,'전신 유산소 운동으로 에너지 소모가 커요.');
INSERT INTO exercise(name,minute,kcal,effect) VALUES('플랭크',60,360,'전신 근육 발달에 좋아요');
INSERT INTO exercise(name,minute,kcal,effect) VALUES('훌라후프',60,240,'숙변 해소와 체내 노폐물을 방출해주는 효과가 있어요');
INSERT INTO exercise(name,minute,kcal,effect) VALUES('런닝머신 달리기',60,345,'체중 조절에 효과적이에요');
INSERT INTO exercise(name,minute,kcal,effect) VALUES('앉아있기',60,60,'장시간 앉아있기는 혈액순환에 좋지 않답니다.');
INSERT INTO exercise(name,minute,kcal,effect) VALUES('샤워',60,240,'스트레스 해소와 피로회복에 좋지만 운동효과는 없어요.');
INSERT INTO exercise(name,minute,kcal,effect) VALUES('박봄다리운동',60,480,'옆으로 누워 팔로 머리를 괴고 다리를 60도 이상 올려주세요.');
INSERT INTO exercise(name,minute,kcal,effect) VALUES('레그레이즈',60,480,'복부 근육 강화에 좋아요.');
INSERT INTO exercise(name,minute,kcal,effect) VALUES('하늘자전거',60,360,'다리의 군살과 뱃살 제거에 효과적이에요.');
INSERT INTO exercise(name,minute,kcal,effect) VALUES('줌바댄스',60,1002,'라틴댄스+휘트니스의 결합된 운동이에요.');
INSERT INTO exercise(name,minute,kcal,effect) VALUES('계단오르기 걷기',60,420,'유산소운동과 무산소운동을 동시에 할 수 있어요.');
INSERT INTO exercise(name,minute,kcal,effect) VALUES('고정자전거',60,420,'비만 또는 관절 질환이 있는 사람들에게 좋은 운동이에요.');

CREATE TABLE traning(
	traning_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(50),
	traning_cnt INT,
	explanation VARCHAR(100)
);
INSERT INTO traning (name, traning_cnt, explanation) VALUES('5분허벅지',9,'10회 씩 반복해주세요.');
INSERT INTO traning (name, traning_cnt, explanation) VALUES('팔뚝살',7,'화면의 시간동안 따라해주세요');
INSERT INTO traning (name, traning_cnt, explanation) VALUES('복근',8,'화면의 시간동안 따라해주세요 헛둘헛둘');
INSERT INTO traning (name, traning_cnt, explanation) VALUES('골반교정',7,'15초씩 쭉쭉 따라해주세요 ~');
INSERT INTO traning (name, traning_cnt, explanation) VALUES('날씬한다리',10,'화면의 시간동안 따라해주세요 헛둘헛둘');
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
INSERT INTO feedback(feedback)VALUES('�� �ȳ��ϼ���!!');
INSERT INTO feedback(feedback)VALUES('�� ��ǥ Į�θ��� �ʰ��ϼ̾��!');
INSERT INTO feedback(feedback)VALUES('�� ��ǥ ��ð��� �� �ϼ̾�� ����ؿ� ><');
INSERT INTO feedback(feedback)VALUES('�� �Ϸ� ���� ���� �� ���̾��!');
INSERT INTO feedback(feedback)VALUES('�� ���� ���� ī������ ���� �����ʾƿ� �̤�');
INSERT INTO feedback(feedback)VALUES('�� ��Ա� ���� 30�� ���� ���ø� ��ȭ�� ���� �ʴ��!');
INSERT INTO feedback(feedback)VALUES('�� �а��� ������ ��ȭ�� �����ϰ� �󱼿� Ʈ������ ����Ų����');
INSERT INTO feedback(feedback)VALUES('�� ���� ������ !');
INSERT INTO feedback(feedback)VALUES('�� ���� ���� ���ô� ���� ���� ��� ���뿡 ���ƿ� *.*');
INSERT INTO feedback(feedback)VALUES('�� � �� ���к����� ���� �߿��ϴ�ϴ� !');
INSERT INTO feedback(feedback)VALUES('�� �ܿ￡ ���밡 �� ���������� ! ���� ���ݸ� ~.~');
INSERT INTO feedback(feedback)VALUES('�� ������ ���� ���⿡ �ְ� !');
INSERT INTO feedback(feedback)VALUES('�� �Ź��� ���ʸ� ��´ٸ� ô�߿� �̻��� ���� ���� �ֵ��� ��');

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

INSERT INTO food(name,kcal,g,unit)VALUES('�ᳪ�����屹',223,700,'�κ�');
INSERT INTO food(name,kcal,g,unit)VALUES('�ҸӸ�����',631,800,'�κ�');
INSERT INTO food(name,kcal,g,unit)VALUES('���',317,250,'��');
INSERT INTO food(name,kcal,g,unit)VALUES('��ġ���',344,250,'��');
INSERT INTO food(name,kcal,g,unit)VALUES('��������',405,250,'��');
INSERT INTO food(name,kcal,g,unit)VALUES('�Ұ����',400,250,'��');
INSERT INTO food(name,kcal,g,unit)VALUES('��ġ���',424,250,'��');
INSERT INTO food(name,kcal,g,unit)VALUES('ġ����',425,250,'��');
INSERT INTO food(name,kcal,g,unit)VALUES('�湫���',583,400,'�κ�');
INSERT INTO food(name,kcal,g,unit)VALUES('������Ұ��ﰢ���',165,100,'��');
INSERT INTO food(name,kcal,g,unit)VALUES('��ġ�������ﰢ���',161,100,'��');
INSERT INTO food(name,kcal,g,unit)VALUES('���Ұ���ﰢ���',172,100,'��');
INSERT INTO food(name,kcal,g,unit)VALUES('��������',782,500,'�κ�');
INSERT INTO food(name,kcal,g,unit)VALUES('�Ұ�ⵤ��',669,500,'�κ�');
INSERT INTO food(name,kcal,g,unit)VALUES('��¡���',679,500,'�κ�');
INSERT INTO food(name,kcal,g,unit)VALUES('����',716,400,'�κ�');
INSERT INTO food(name,kcal,g,unit)VALUES('�ع�����',771,700,'�κ�');
INSERT INTO food(name,kcal,g,unit)VALUES('ȸ����',683,500,'�κ�');
INSERT INTO food(name,kcal,g,unit)VALUES('��ġ����',679,500,'�κ�');
INSERT INTO food(name,kcal,g,unit)VALUES('���꽽����',574,500,'�κ�');
INSERT INTO food(name,kcal,g,unit)VALUES('������',400,400,'�κ�');
INSERT INTO food(name,kcal,g,unit)VALUES('��ġ������',754,500,'�κ�');
INSERT INTO food(name,kcal,g,unit)VALUES('���캺����',700,400,'�κ�');
INSERT INTO food(name,kcal,g,unit)VALUES('�ع�������',705,400,'�κ�');
INSERT INTO food(name,kcal,g,unit)VALUES('���Ƕ��̽�',729,450,'�κ�');
INSERT INTO food(name,kcal,g,unit)VALUES('�Ｑ������',705,400,'�κ�');
INSERT INTO food(name,kcal,g,unit)VALUES('�����',706,500,'�κ�');
INSERT INTO food(name,kcal,g,unit)VALUES('��ȸ�����',679,450,'�κ�');
INSERT INTO food(name,kcal,g,unit)VALUES('�����ʹ�(����)',453,300,'�κ�');
INSERT INTO food(name,kcal,g,unit)VALUES('�����ʹ�(���)',461,300,'�κ�');
INSERT INTO food(name,kcal,g,unit)VALUES('����ʹ�',396,250,'�κ�');
INSERT INTO food(name,kcal,g,unit)VALUES('�����ʹ�',392,250,'�κ�');
INSERT INTO food(name,kcal,g,unit)VALUES('�����ʹ�',447,250,'�κ�');
INSERT INTO food(name,kcal,g,unit)VALUES('�����ʹ�',387,250,'�κ�');
INSERT INTO food(name,kcal,g,unit)VALUES('����ʹ�',486,250,'�κ�');
INSERT INTO food(name,kcal,g,unit)VALUES('��ġ�ʹ�',374,250,'�κ�');
INSERT INTO food(name,kcal,g,unit)VALUES('�����ʹ�',446,250,'�κ�');
INSERT INTO food(name,kcal,g,unit)VALUES('��ä��',884,650,'�κ�');
INSERT INTO food(name,kcal,g,unit)VALUES('������',884,640,'�κ�');
INSERT INTO food(name,kcal,g,unit)VALUES('�����',741,500,'�κ�');
INSERT INTO food(name,kcal,g,unit)VALUES('ī�����̽�',672,500,'�κ�');
INSERT INTO food(name,kcal,g,unit)VALUES('�˹�',618,400,'�κ�');
INSERT INTO food(name,kcal,g,unit)VALUES('«�͹�',661,900,'�κ�');
INSERT INTO food(name,kcal,g,unit)VALUES('�����',390,250,'�κ�');
INSERT INTO food(name,kcal,g,unit)VALUES('����Ƣ���',606,300,'�κ�');
INSERT INTO food(name,kcal,g,unit)VALUES('�����',510,300,'�κ�');
INSERT INTO food(name,kcal,g,unit)VALUES('Ķ�����ϾƷ�',487,300,'�κ�');
INSERT INTO food(name,kcal,g,unit)VALUES('�ʹ��',262,65,'��');
INSERT INTO food(name,kcal,g,unit)VALUES('���ҵ����',207,70,'��');
INSERT INTO food(name,kcal,g,unit)VALUES('����',222,70,'��');
INSERT INTO food(name,kcal,g,unit)VALUES('����',560,130,'��');
INSERT INTO food(name,kcal,g,unit)VALUES('���̱�',335,120,'��');
INSERT INTO food(name,kcal,g,unit)VALUES('������',227,70,'��');
INSERT INTO food(name,kcal,g,unit)VALUES('���û�',296,70,'��');
INSERT INTO food(name,kcal,g,unit)VALUES('��׻�',231,70,'��');
INSERT INTO food(name,kcal,g,unit)VALUES('����ũ����',229,60,'��');
INSERT INTO food(name,kcal,g,unit)VALUES('���̽�Ʈ����',319,70,'��');
INSERT INTO food(name,kcal,g,unit)VALUES('ī���ڶ�',235,70,'��');
INSERT INTO food(name,kcal,g,unit)VALUES('��ġ�������ġ',436,200,'��');
INSERT INTO food(name,kcal,g,unit)VALUES('��ũ������ũ',278,100,'����');
INSERT INTO food(name,kcal,g,unit)VALUES('���ݸ�����ũ',420,100,'����');
INSERT INTO food(name,kcal,g,unit)VALUES('ġ������ũ',329,100,'����');
INSERT INTO food(name,kcal,g,unit)VALUES('ä�Ұ����',300,100,'��');
INSERT INTO food(name,kcal,g,unit)VALUES('�Ѱ�,����',109,30,'��');
INSERT INTO food(name,kcal,g,unit)VALUES('�Ѱ�,���',119,30,'��');
INSERT INTO food(name,kcal,g,unit)VALUES('�Ѱ�,���۰�',123,30,'��');
INSERT INTO food(name,kcal,g,unit)VALUES('�Ѱ�,��ȭ�ٽ�',110,30,'��');
INSERT INTO food(name,kcal,g,unit)VALUES('�Ѱ�,����',127,30,'��');
INSERT INTO food(name,kcal,g,unit)VALUES('�����',796,650,'�׸�');
INSERT INTO food(name,kcal,g,unit)VALUES('������',824,650,'�׸�');
INSERT INTO food(name,kcal,g,unit)VALUES('�Ｑ�����',803,700,'�׸�');
INSERT INTO food(name,kcal,g,unit)VALUES('������',599,550,'�κ�');
INSERT INTO food(name,kcal,g,unit)VALUES('�������',617,550,'�κ�');
INSERT INTO food(name,kcal,g,unit)VALUES('��ġ����',599,700,'�κ�');
INSERT INTO food(name,kcal,g,unit)VALUES('�̸�',601,450,'�κ�');
INSERT INTO food(name,kcal,g,unit)VALUES('���ø�',551,800,'�κ�');
INSERT INTO food(name,kcal,g,unit)VALUES('����ø�',622,550,'�κ�');
INSERT INTO food(name,kcal,g,unit)VALUES('�����ø�',525,800,'�κ�');
INSERT INTO food(name,kcal,g,unit)VALUES('ȸ�ø�',629,550,'�κ�');
INSERT INTO food(name,kcal,g,unit)VALUES('����',711,800,'�κ�');
INSERT INTO food(name,kcal,g,unit)VALUES('�����Ա�',624,700,'�κ�');
INSERT INTO food(name,kcal,g,unit)VALUES('��⸸��',452,250,'�κ�');
INSERT INTO food(name,kcal,g,unit)VALUES('������',685,250,'�κ�');
INSERT INTO food(name,kcal,g,unit)VALUES('��ġ����',420,250,'�κ�');
INSERT INTO food(name,kcal,g,unit)VALUES('������',156,120,'�κ�');
INSERT INTO food(name,kcal,g,unit)VALUES('���Ա�',434,700,'�κ�');
INSERT INTO food(name,kcal,g,unit)VALUES('������',646,800,'�κ�');
INSERT INTO food(name,kcal,g,unit)VALUES('���ϼҽ����İ�Ƽ',646,400,'�κ�');
INSERT INTO food(name,kcal,g,unit)VALUES('ũ���ҽ����İ�Ƽ',837,400,'�κ�');
INSERT INTO food(name,kcal,g,unit)VALUES('�丶��ҽ����İ�Ƽ',642,500,'�κ�');
INSERT INTO food(name,kcal,g,unit)VALUES('�ع�ũ���ҽ����İ�Ƽ',917,500,'�κ�');
INSERT INTO food(name,kcal,g,unit)VALUES('�쵿(�Ͻ�)',421,700,'�κ�');
INSERT INTO food(name,kcal,g,unit)VALUES('�쵿(�߽�)',648,1000,'�κ�');
INSERT INTO food(name,kcal,g,unit)VALUES('��ġ�쵿',500,800,'�κ�');
INSERT INTO food(name,kcal,g,unit)VALUES('�Ｑ�쵿',691,1000,'�κ�');
INSERT INTO food(name,kcal,g,unit)VALUES('«��',687,1000,'�κ�');
INSERT INTO food(name,kcal,g,unit)VALUES('��«��',680,900,'�κ�');
INSERT INTO food(name,kcal,g,unit)VALUES('�Ｚ«��',662,900,'�κ�');
INSERT INTO food(name,kcal,g,unit)VALUES('�ع�Į����',628,900,'�κ�');

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

INSERT INTO exercise(name,minute,kcal,effect) VALUES('�ȱ�',60,156,'�ϻ��Ȱ�� �Ҹ� Į�θ�����.');
INSERT INTO exercise(name,minute,kcal,effect) VALUES('����Ŭ�ǳ�������',60,300,'��ü ���� ��ȭ ����� ��̿���.');
INSERT INTO exercise(name,minute,kcal,effect) VALUES('��Ʈ��Ī',60,240,'� ��,�Ŀ� ���ָ� ���ƿ�.');
INSERT INTO exercise(name,minute,kcal,effect) VALUES('���׸ӽŰȱ�',60,160,'ü�� ������ ȿ�����̿���.');
INSERT INTO exercise(name,minute,kcal,effect) VALUES('L�ڴٸ��',60,360,'�����̸� ���� �� ! ������ �ٸ��� �÷��ּ���.');
INSERT INTO exercise(name,minute,kcal,effect) VALUES('����Ʈ',60,420,'����� ���� ��ȭ�� ������ �����ش�ϴ�.');
INSERT INTO exercise(name,minute,kcal,effect) VALUES('���ֱ�',60,86,'�ȿ�������� �����ϴ�.');
INSERT INTO exercise(name,minute,kcal,effect) VALUES('��å �ȱ�',60,120,'���ư�ȭ ���濡 ���ƿ�.');
INSERT INTO exercise(name,minute,kcal,effect) VALUES('�ٳѱ�',60,600,'���� ����� ����� ������ �Ҹ� Ŀ��.');
INSERT INTO exercise(name,minute,kcal,effect) VALUES('�÷�ũ',60,360,'���� ���� �ߴ޿� ���ƿ�');
INSERT INTO exercise(name,minute,kcal,effect) VALUES('�Ƕ�����',60,240,'���� �ؼҿ� ü�� ������ �������ִ� ȿ���� �־��');
INSERT INTO exercise(name,minute,kcal,effect) VALUES('���׸ӽ� �޸���',60,345,'ü�� ������ ȿ�����̿���');
INSERT INTO exercise(name,minute,kcal,effect) VALUES('�ɾ��ֱ�',60,60,'��ð� �ɾ��ֱ�� ���׼�ȯ�� ���� �ʴ�ϴ�.');
INSERT INTO exercise(name,minute,kcal,effect) VALUES('����',60,240,'��Ʈ���� �ؼҿ� �Ƿ�ȸ���� ������ �ȿ���� �����.');
INSERT INTO exercise(name,minute,kcal,effect) VALUES('�ں��ٸ��',60,480,'������ ���� �ȷ� �Ӹ��� ���� �ٸ��� 60�� �̻� �÷��ּ���.');
INSERT INTO exercise(name,minute,kcal,effect) VALUES('���׷�����',60,480,'���� ���� ��ȭ�� ���ƿ�.');
INSERT INTO exercise(name,minute,kcal,effect) VALUES('�ϴ�������',60,360,'�ٸ��� ����� ��� ���ſ� ȿ�����̿���.');
INSERT INTO exercise(name,minute,kcal,effect) VALUES('�ܹٴ�',60,1002,'��ƾ��+��Ʈ�Ͻ��� ���յ� ��̿���.');
INSERT INTO exercise(name,minute,kcal,effect) VALUES('��ܿ����� �ȱ�',60,420,'����ҿ�� ����ҿ�� ���ÿ� �� �� �־��.');
INSERT INTO exercise(name,minute,kcal,effect) VALUES('����������',60,420,'�� �Ǵ� ���� ��ȯ�� �ִ� ����鿡�� ���� ��̿���.');

CREATE TABLE traning(
	traning_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(50),
	traning_cnt INT,
	explanation VARCHAR(100)
);
INSERT INTO traning (name, traning_cnt, explanation) VALUES('5�������',9,'10ȸ �� �ݺ����ּ���.');
INSERT INTO traning (name, traning_cnt, explanation) VALUES('�ȶһ�',7,'ȭ���� �ð����� �������ּ���');
INSERT INTO traning (name, traning_cnt, explanation) VALUES('����',8,'ȭ���� �ð����� �������ּ��� ������');
INSERT INTO traning (name, traning_cnt, explanation) VALUES('��ݱ���',7,'15�ʾ� ���� �������ּ��� ~');
INSERT INTO traning (name, traning_cnt, explanation) VALUES('�����Ѵٸ�',10,'ȭ���� �ð����� �������ּ��� ������');
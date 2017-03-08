
class Record implements Comparable<Record>{
	String name = "무명";
	int score;

	public Record(String name, int score) {
		this.name = name;
		this.score = score;
	}

	public int compareTo(Record r) {	
		return r.score-score;
	}
	public String toString(){
		return "이름 : " + name + " 점수 : " + score;
	}
}

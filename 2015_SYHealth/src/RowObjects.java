import java.util.*;

// ��µ� �� ���� ������ �ǹ��ϴ� Ŭ����
public class RowObjects implements Iterator<PrintObject> {
	private ArrayList<PrintObject> row;
	private int totalLength;				// ������ ������ ��ü ���ڿ��� ����		
	private int pos;						
	
	RowObjects() {
		row = new ArrayList<PrintObject>();
		this.totalLength = 0;
		pos = -1;
	}
	
	public void reset() {
		pos = -1;
	}
	public void add(PrintObject po) {
		row.add(po);
		totalLength += po.getMaxWidth();
	}

	public boolean hasNext() {
		return pos < row.size()-1;
	}

	@Override
	public PrintObject next() {
		pos++;
		return row.get(pos);
	}

	@Override
	public void remove() {
	}
	
	public int getTotalLength() {
		return totalLength;
	}
}

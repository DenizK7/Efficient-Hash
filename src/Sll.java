
public class Sll {
	Node head;
	public void add(Object data1, Object data2) {
		if(head==null) {
			Node newNode = new Node (data1, data2);
			head = newNode;
		}
		else {
			Node temp = head;
			while(temp.getLink()!= null)
				temp = temp.getLink();
			Node newNode = new Node(data1, data2);
			temp.setLink(newNode);
		}
		
	}
	
	public boolean search(String txt) {
		Node temp = head;
		while(temp!= null) {
			if(txt.equals(temp.getData1())) {
				temp.setData2((int)temp.getData2()+ 1);
				return true;
			}
			temp= temp.getLink();
		}
		return false;
	}
	public int size() {
		int count = 0;
		Node temp = head;
		while(temp!= null) {
			temp = temp.getLink();
			count++;
		}
		return count;
	}
	public void display() {
		Node temp = head;
		while(temp!=null) {
			System.out.println(temp.getData1() + "  " + temp.getData2());
			temp = temp.getLink();
		}
	}
	

}

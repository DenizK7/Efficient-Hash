
public class Node {
	private Object data1;
	private Object data2;
	private Node link;
	public Object getData1() {
		return data1;
	}
	public void setData1(Object data1) {
		this.data1 = data1;
	}
	public Object getData2() {
		return data2;
	}
	public void setData2(Object data2) {
		this.data2 = data2;
	}
	public Node(Object dataToAdd, Object dataToAdd2) {
		data1 = dataToAdd;
		data2 = dataToAdd2;
		link = null;
	}
	   public Node getLink() { return link;  }
	   public void setLink(Node link) { this.link = link;   }   

}

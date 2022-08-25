
/*
 * messages communicate sending messages to each other
 */
public class Message {
	private int value;
	private int mythread;
	private String type ;
	// a message should include information.
	// you are required to add corresponding fields and constructor parameters
	// in order to pass on that information
	public Message(int mythread, int value, String type) {
		this.mythread = mythread;
		this.type = type;
		this.value = value;
	}
	
	public int getMessage() {
		
		return value;
	}
	public String getType() {
		
		return type;
	}
		public int getSender() {
		
		return mythread;
	}
}

import java.util.concurrent.ThreadLocalRandom;

// nextInt is normally exclusive of the top value,
// so add 1 to make it inclusive
import java.util.HashMap;
import java.util.Map.Entry;

public class Agent implements Runnable {

	private int id;
	private Mailer mailer;
	private HashMap<VarTuple, ConsTable> constraints;
	private Message collectedMessage;
	private int success = 0;	//sucessful constraint checks
	private int numberOfMsgRead = 0;	//keep count of the number of messages read
	private int numberOfConstRead = 0;	//keep count of the number of constraint messages read
	private int n ;	//keep count of the number of constraint messages read
	private int d ;	//keep count of the number of constraint messages read

	
	/*
	 * constructor parameters -
	 * agent's id
	 * a reference to mailer
	 * a reference to csp
	 */
	public Agent(int id, Mailer mailer, HashMap<VarTuple, ConsTable> constraints, int n, int d) {
		this.id = id;
		this.mailer = mailer;
		this.constraints = constraints;
		this.n = n;
		this.d = d; 
	}
	
	public void printPrivate() {
		for (Entry<VarTuple, ConsTable> entry : constraints.entrySet()) {
			System.out.println("id "+ this.id +" private: " + entry.getKey().getI() + " and " + entry.getKey().getJ() + ":");
			//entry.getValue().print(domainSize);
			//System.out.println();
		}
	}

	@Override
	public void run() {
		// CODE...
		// CODE...
		// CODE...
			int total = 0;

			int assign = ThreadLocalRandom.current().nextInt(0, (d-1) + 1);
		while(true) {
		//System.out.println(assign);
		Message msg = new Message(id,assign, "assign");
		for(Entry<VarTuple, ConsTable> entry : constraints.entrySet()) {	//send to neighbours
				mailer.send(entry.getKey().getJ(), msg);
		}
			collectedMessage = mailer.readOne(id);	//collect message 
			if(constraints.size() == 0) break;
		   else if(collectedMessage == null) {
	//if no message received or need to be received, keep reading 
				continue;
			}else {
				if(collectedMessage.getType()=="assign" ) {
				    int otherAssign = collectedMessage.getMessage(); //get assign of neighbor
					int otherId = collectedMessage.getSender(); //get id of neighbor 'sender'
				for(Entry<VarTuple, ConsTable> entry : constraints.entrySet()) {	
					//send to neighbours
			        if(entry.getKey().getJ() == otherId){  // 
                       			ConsTable cte = entry.getValue();
								success+=cte.get_constraint(d,assign,otherAssign);
					}
	         	}
					//success += 1;	//increment successful constraint checks
					numberOfMsgRead += 1;	//increment number of messages read
					/*if(id==4){///////////
						total++;
					}*/
				}
			    /*if( collectedMessage.getType()=="constraint" ) {	/////////////////
					int myconst = collectedMessage.getMessage();
					System.out.println("my cost = "+myconst);
					numberOfMsgRead += 1;	//increment number of messages read
				//	total++ ; 
				}*/
				if(numberOfMsgRead == constraints.size()) {	
					//check if all messaged received from all neighbours
					break;
				}else continue;	//if there are still some messages not read from neighbours, loop
				
			}
		}
		if(id != (n-1)){
				/*try {
            	Thread.sleep(2000); // ms
			}catch (InterruptedException e) {   return; }	*/
            Message msg1 = new Message(id, success, "constraint");
	        mailer.send(n-1, msg1);
			//System.out.println("i am "+ id+" Sent = "+success);
		}			
		System.out.println("ID: "+id+", assignment: "+assign+",successful constraint checks: "+success);
		try {
			
            if(id == (n-1) ) {
            	System.out.println("id "+id+" gonna sleep");
            	Thread.sleep(2000); // ms
            	 System.out.println("id "+id+" out sleep");
            }
			}catch (InterruptedException e) {   return; }
	    if(id == (n-1) ) {
	    	total += success;	//add its assignment/const to the total
			while(true) {
			collectedMessage = mailer.readOne(id);	//collect message
		    if(collectedMessage == null) {
		    	System.out.println("msg vide waiting");
	//if no message received or need to be received, keep reading 
				break;
			}else {
			    if( collectedMessage.getType()=="constraint" ) {
					int myconst = collectedMessage.getMessage();
					//System.out.println("my cost = "+myconst);
				    total += myconst ;	//increment total consts by received const
				    numberOfConstRead += 1;
				}
				if(numberOfConstRead == id) {	
					//check if all messaged received from all neighbours
					break;
				}else continue;	//if there are still some messages not read from neighbours, loop
			}
		}
			System.out.println("i am "+id+" Total constraints = "+total);
		}
	}

}

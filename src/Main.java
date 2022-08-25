import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class Main {

	public static void main(String[] args) throws InterruptedException {

		// extract parameters
		int n = Integer.valueOf(args[0]).intValue();
		int d = Integer.valueOf(args[1]).intValue();
		double p1 = Double.valueOf(args[2]).doubleValue();
		double p2 = Double.valueOf(args[3]).doubleValue();

		// generate and print CSP
		Generator gen = new Generator(n, d, p1, p2);		
		CSP csp = gen.generateDCSP();
		csp.print();
		//HashMap<VarTuple, ConsTable> private_information1 = csp.get_private_info(3);
		//CSP p3 = new CSP(private_information1, 3);
		//p3.print();
		// initialize mailer
		Mailer mailer = new Mailer();
		for (int i = 0; i < n; i++) {
			mailer.put(i);
		}		

		// create agents
		ArrayList<Thread> threads = new ArrayList<Thread>();
		for (int i = 0; i < n; i++) {
			HashMap<VarTuple, ConsTable> private_information = csp.get_private_info(i);
			//private_information.print();
			// use the csp to extract the private information of each agent
			Agent a = new Agent(i, mailer, private_information, n, d);
			//a.printPrivate();
			Thread t = new Thread(a);
			threads.add(t);
		}
        //threads.get(4).setPriority(3);
		// run agents as threads
		for (Thread t : threads) {
			t.start();
		}

		// wait for all agents to terminate
		for (Thread t : threads) {
			t.join();
		}
	}

}


import java.util.HashMap;
import java.util.Map.Entry;

/*
 * the csp class wraps the constraint tables (HashMap<VarTuple, ConsTable> cons_tables).
 * this class is queried when constructing the agents and their private information.
 * you are required to add the necessary fields and methods to this class,
 * in order to enable extracting this information
 */
public class CSP {

	// required fields - the constraints tables and the domain size
	private HashMap<VarTuple, ConsTable> cons_tables;
	private int domainSize;
	
	/*
	 * constructor
	 */
	public CSP(HashMap<VarTuple, ConsTable> cons_tables, int domainSize) {
		this.cons_tables = cons_tables;
		this.domainSize = domainSize;
	}
	
	// print a csp
	public void print() {
		for (Entry<VarTuple, ConsTable> entry : cons_tables.entrySet()) {
			System.out.println("table of " + entry.getKey().getI() + " and " + entry.getKey().getJ() + ":");
			entry.getValue().print(domainSize);
			System.out.println();
		}
	}

		public HashMap<VarTuple, ConsTable> get_private_info(int id) {
		HashMap<VarTuple, ConsTable> mytable = new HashMap<VarTuple, ConsTable>();
		for (Entry<VarTuple, ConsTable> entry : cons_tables.entrySet()) {
			if(entry.getKey().getI() == id) {
				VarTuple at = new VarTuple(id, entry.getKey().getJ());
				mytable.put(at, entry.getValue());
			}
			else if (entry.getKey().getJ() == id) {
				VarTuple at = new VarTuple(id, entry.getKey().getI());
				mytable.put(at, entry.getValue());	
			}
		}
		return mytable;
	}
	
	//extract
	public HashMap<VarTuple, ConsTable> extract() {
		
		return cons_tables;
	}

}

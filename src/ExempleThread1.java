public class ExempleThread1 extends Thread {
	
    private int nombre_max;
    ExempleThread1 (int nombre_max) {
        this.nombre_max=nombre_max;    }

    public void run() {
        for (int nombre=1; nombre <nombre_max;nombre++) {
            System.out.println("le compteur est à " + nombre + ".");
            try {
                Thread.sleep(6000); // milliseconds
            } catch (InterruptedException e) { return;   }}}// fin run

   public static void main(String args[]) {
        ExempleThread1 lecompteur = new ExempleThread1(10);
        lecompteur.start();
        int n = 0;
        while (lecompteur.isAlive ()) {
            System.out.println("en train de compter (n=" + n + ")" );
            n = n + 1;
            try {
               Thread.sleep(3000); // ms
            } catch(InterruptedException e) {} 
              } 
           } 
    } 
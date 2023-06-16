public class Main {
    public static void main(String[] args) {
        Harcos harcos = new Harcos("Harcos", 100);
        Varazslo varazslo = new Varazslo("Varázsló", 80);

        /* a harcot elindul külön szálon*/
        Thread harcThread = new Thread(() -> {
            while (harcos.isEletbenVan() && varazslo.isEletbenVan()) {
                harcos.tamad(varazslo);
                varazslo.tamad(harcos);
            }
        });
        harcThread.start();

        /* a harc befejeződését várjuk*/
        try {
            harcThread.join();
        } catch (InterruptedException e) {
        }

        /* a győztes kiírása*/
        if (harcos.isEletbenVan()) {
            System.out.println(harcos.getNev() + " győzött!");
        } else {
            System.out.println(varazslo.getNev() + " győzött!");
        }
    }
}

class Harcos {
    private final String nev;
    private int egeszseg;

    public Harcos(String nev, int egeszseg) {
        this.nev = nev;
        this.egeszseg = egeszseg;
    }

    public String getNev() {
        return nev;
    }

    public boolean isEletbenVan() {
        return egeszseg > 0;
    }

    public void tamad(Varazslo varazslo) {
        /* egy támadás*/
        varazslo.megSerul(20);
        System.out.println(nev + " megtámadta " + varazslo.getNev() + "t.");
    }

    public synchronized void megSerul(int serules) {
        /* egy sebzés*/
        egeszseg -= serules;
        if (egeszseg < 0) {
            egeszseg = 0;
        }
        System.out.println(nev + " kapott " + serules + " sebzést.");
    }
}

class Varazslo {
    private final String nev;
    private int egeszseg;

    public Varazslo(String nev, int egeszseg) {
        this.nev = nev;
        this.egeszseg = egeszseg;
    }

    public String getNev() {
        return nev;
    }

    public boolean isEletbenVan() {
        return egeszseg > 0;
    }

    public void tamad(Harcos harcos) {
        /*egy támadás megvalosul*/
        harcos.megSerul(15);
        System.out.println(nev + " megtámadta " + harcos.getNev() + "t.");
    }

    public synchronized void megSerul(int serules) {
        /*egy  sebzés megvalosul*/
        egeszseg -= serules;
        if (egeszseg < 0) {
            egeszseg = 0;
        }
        System.out.println(nev + " kapott " + serules + " sebzést.");
    }
}

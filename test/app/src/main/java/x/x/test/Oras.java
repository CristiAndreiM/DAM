package x.x.test;

import java.io.Serializable;

import java.io.Serializable;

public class Oras implements Serializable {
    private String nume;
    private double populatie;
    private String regiune;
    private boolean resedinta;

    private String telefonPrimarie;
    public Oras(String nume, double populatie, String regiune, boolean resedinta) {
        this.nume = nume;
        this.populatie = populatie;
        this.regiune = regiune;
        this.resedinta = resedinta;
        this.telefonPrimarie = "000000000";
    }
    public Oras(String nume, double populatie, String regiune, boolean resedinta, String telefonPrimarie) {
        this.nume = nume;
        this.populatie = populatie;
        this.regiune = regiune;
        this.resedinta = resedinta;
        this.telefonPrimarie = telefonPrimarie;
    }

    public String getNume() { return nume; }
    public double getPopulatie() { return populatie; }
    public String getRegiune() { return regiune; }
    public boolean isResedinta() { return resedinta; }

    public String getTelefonPrimarie(){return telefonPrimarie;}
    @Override
    public String toString() {
        return nume + " (" + regiune + ") - " + (long) populatie + " loc."
                + (resedinta ? " - resedinta" : "");
    }
}

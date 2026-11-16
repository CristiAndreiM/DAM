package x.x.angajati;

import java.io.Serializable;

public class Angajat implements Serializable{
    private String nume;
    private String departament;
    private String telefon;
    private int vechi;
    private boolean lucreazaRemote;

    public Angajat(String nume, String departament, String telefon, int vechi, boolean lucreazaRemote) {
        this.nume = nume;
        this.departament = departament;
        this.telefon = telefon;
        this.vechi = vechi;
        this.lucreazaRemote = lucreazaRemote;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Angajat{");
        sb.append("nume='").append(nume).append('\'');
        sb.append(", departament='").append(departament).append('\'');
        sb.append(", telefon='").append(telefon).append('\'');
        sb.append(", vechi=").append(vechi);
        sb.append(", lucreazaRemote=").append(lucreazaRemote);
        sb.append('}');
        return sb.toString();
    }

    public String getNume() {
        return nume;
    }

    public String getDepartament() {
        return departament;
    }

    public String getTelefon() {
        return telefon;
    }

    public int getVechi() {
        return vechi;
    }

    public boolean isLucreazaRemote() {
        return lucreazaRemote;
    }
}

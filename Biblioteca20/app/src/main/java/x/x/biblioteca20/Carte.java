package x.x.biblioteca20;

import java.io.Serializable;

public class Carte implements Serializable {
    private String titlu;
    private String autor;
    private String gen;
    private  int an;
    private float pret;
    private boolean citita;

    public Carte(String titlu, String autor, String gen, int an, float pret, boolean citita) {
        this.titlu = titlu;
        this.autor = autor;
        this.gen = gen;
        this.an = an;
        this.pret = pret;
        this.citita = citita;
    }

    @Override
    public String toString(){
        return titlu + " - " + autor + " / " + an;
    }

    public String getTitlu() {
        return titlu;
    }

    public String getAutor() {
        return autor;
    }

    public String getGen() {
        return gen;
    }

    public int getAn() {
        return an;
    }

    public float getPret() {
        return pret;
    }

    public boolean isCitita() {
        return citita;
    }
}

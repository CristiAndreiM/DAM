package x.x.biblioteca;

import androidx.annotation.NonNull;
import java.io.Serializable;

public class Carte implements Serializable {

    private String titlu;
    private String autor;
    private String gen;
    private int an_aparitie;
    private boolean disponibila;

    public Carte(String titlu, String autor, String gen, int an_aparitie, boolean disponibila) {
        this.titlu = titlu;
        this.autor = autor;
        this.gen = gen;
        this.an_aparitie = an_aparitie;
        this.disponibila = disponibila;
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

    public int getAn_aparitie() {
        return an_aparitie;
    }

    public boolean isDisponibila() {
        return disponibila;
    }

    @NonNull
    @Override
    public String toString() {
        return "Carte{" +
                "titlu='" + titlu + '\'' +
                ", autor='" + autor + '\'' +
                ", gen='" + gen + '\'' +
                ", an_aparitie=" + an_aparitie +
                ", disponibila=" + disponibila +
                '}';
    }
}
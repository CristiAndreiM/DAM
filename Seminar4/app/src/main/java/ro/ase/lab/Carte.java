package ro.ase.lab;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Carte implements Serializable {
    private final String titlu;
    private final String gen;
    private final int an_aparatiei;

    public Carte(String titlu, String gen, int an_aparatiei) {
        this.titlu = titlu;
        this.gen = gen;
        this.an_aparatiei = an_aparatiei;
    }

    @NonNull
    @Override
    public String toString() {
        return titlu + " - " + gen + " (" + an_aparatiei + ")";
    }
}

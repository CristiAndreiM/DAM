package ro.ase.lab;

import androidx.annotation.NonNull;

public class Informatie {
    private String name;
    private double weight;
    private double height;
    private String gen;
    private double calc;
    Informatie(String name, double weight, double height,  String gen, double calc){
        this.name = name;
        weight = weight;
        this.height = height;
        this.gen = gen;
        this.calc = calc;
    }

    @NonNull
    @Override
    public String toString() {
        return "Name: " + name + "Greutate: " + height +
                "Gen: " + gen + "calc: " + calc;
    }
}

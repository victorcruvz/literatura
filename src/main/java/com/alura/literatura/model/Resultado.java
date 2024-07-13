package com.alura.literatura.model;
import jakarta.persistence.Entity;

import java.util.List;

public class Resultado {

    private List<DatosLibro> results;
    private Integer count;
    private String previous;
    private String next;

    public Resultado(){}

    public List<DatosLibro> getResults() {
        return results;
    }
    public void setResults(List<DatosLibro> resultado) {
        this.results = resultado;
    }

    public Integer getCount() {
        return count;
    }
    public void setCount(Integer count) {
        this.count = count;
    }

    public String getPrevious() {
        return previous;
    }
    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public String getNext() {
        return next;
    }
    public void setNext(String next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "Resultado{" +
                "results=" + results +
                '}';
    }
}

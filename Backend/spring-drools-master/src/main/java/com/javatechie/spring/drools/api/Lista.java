package com.javatechie.spring.drools.api;

import java.util.ArrayList;
import java.util.List;

public class Lista {
    List<String> elementi = new ArrayList<>();

    public Lista(List<String> elementi) {
        this.elementi = elementi;
    }

    public Lista() {
        this.elementi = new ArrayList<>();
    }

    public List<String> getElementi() {
        return elementi;
    }

    public void setElementi(List<String> elementi) {
        this.elementi = elementi;
    }
}

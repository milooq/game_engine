package com.company;

public class Main {
    public static void main(String[] args) {
        try{
            new Window().play();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

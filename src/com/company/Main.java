package com.company;

public class Main {
    public static void main(String[] args) {
        while (true) {
            Window w = new Window();
            try {
                w.play();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                w.dispose();
            }
        }
    }
}

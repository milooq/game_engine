package com.company;

public class Main {
    public static void main(String[] args) {
            Window w = new Window();
            while(true){
                while(Window.isPlaying){}
                w.menu.show();
            }
    }
}

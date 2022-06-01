package com.company;

public class Main {
    static volatile char c = 'A';
    static Object monitor = new Object();

    public static void main(String[] args) {
        new Thread(new WaitNotifyClass('A', 'B')).start();
        new Thread(new WaitNotifyClass('B', 'C')).start();
        new Thread(new WaitNotifyClass('C', 'A')).start();

    }

    static class WaitNotifyClass implements Runnable {
        private final char currentLetter;
        private final char nextLetter;

        public WaitNotifyClass(char currentLetter, char nextLetter) {
            this.currentLetter = currentLetter;
            this.nextLetter = nextLetter;
        }

        @Override
        public void run() {

            try {
                for (int i = 0; i < 5; i++) {
                    synchronized (monitor) {
                        while (c != currentLetter)
                            monitor.wait();
                        System.out.print(currentLetter);
                        c = nextLetter;
                        monitor.notifyAll();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}



package com.example.android_begin;

final class Presenter {
    private static Presenter instance = null;
    private static final Object sync = new Object();
    private int indexSpin;

    private Presenter() {
        this.indexSpin = 0;
    }

    int getIndexSpin() {
        return indexSpin;
    }

    void setIndexSpin(int indexSpin) {
        this.indexSpin = indexSpin;
    }

    static Presenter getInstance() {
        synchronized (sync) {
            if (instance == null) {
                instance = new Presenter();
            }
            return instance;
        }
    }

}

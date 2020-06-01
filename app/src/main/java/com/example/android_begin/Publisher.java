package com.example.android_begin;

import java.util.ArrayList;
import java.util.List;

public class Publisher {

    private List<Observer> observers;   // Все обозреватели

    public Publisher() {
        observers = new ArrayList<>();
    }

    // Подписать
    public void subscribe(Observer observer) {
        observers.add(observer);
    }

    // Отписать
    public void unsubscribe(Observer observer) {
        observers.remove(observer);
    }

    // Разослать событие
    public void notifyHum(boolean val) {
        for (Observer observer : observers) {
            observer.updateHumidity(val);
        }
    }

    public void notifyWind(boolean val) {
        for (Observer observer : observers) {
            observer.updateWind(val);
        }
    }
}
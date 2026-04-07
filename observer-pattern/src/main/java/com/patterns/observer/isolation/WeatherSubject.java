package com.patterns.observer.isolation;

/**
 * Subject interface for weather data.
 * Defines methods for managing observer subscriptions.
 */
public interface WeatherSubject {
    
    /**
     * Registers an observer to receive updates.
     *
     * @param observer the observer to register
     */
    void registerObserver(WeatherObserver observer);
    
    /**
     * Removes an observer from receiving updates.
     *
     * @param observer the observer to remove
     */
    void removeObserver(WeatherObserver observer);
    
    /**
     * Notifies all registered observers of a state change.
     */
    void notifyObservers();
}

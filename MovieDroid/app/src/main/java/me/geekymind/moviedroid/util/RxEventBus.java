package me.geekymind.moviedroid.util;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * A simple event bus built with RxJava
 */
public class RxEventBus {

  private final PublishSubject<Object> mBusSubject;
  private static RxEventBus instance;

  private RxEventBus() {
    mBusSubject = PublishSubject.create();
  }

  public static RxEventBus getInstance() {
    if (instance == null) {
      instance = new RxEventBus();
    }
    return instance;
  }

  /**
   * Posts an object (usually an Event) to the bus
   */
  public void post(Object event) {
    mBusSubject.onNext(event);
  }

  /**
   * Observable that will emmit everything posted to the event bus.
   */
  public Observable<Object> observable() {
    return mBusSubject;
  }

  /**
   * Observable that only emits events of a specific class.
   * Use this if you only want to subscribe to one type of events.
   */
  public <T> Observable<T> filteredObservable(final Class<T> eventClass) {
    return mBusSubject.ofType(eventClass);
  }
}
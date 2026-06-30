package com.training.alokdemoapplication.util;

import androidx.annotation.MainThread;
import androidx.lifecycle.MutableLiveData;

import java.util.concurrent.atomic.AtomicBoolean;

/** A LiveData that emits an event exactly once (e.g. one-off error toasts). */
public class SingleLiveEvent<T> extends MutableLiveData<T> {

    private final AtomicBoolean pending = new AtomicBoolean(false);

    @MainThread
    public void call() {
        setValue(null);
    }

    @Override
    @MainThread
    public void setValue(T value) {
        pending.set(true);
        super.setValue(value);
    }

    public boolean consumePending() {
        return pending.compareAndSet(true, false);
    }
}

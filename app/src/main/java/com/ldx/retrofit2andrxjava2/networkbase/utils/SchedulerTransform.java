package com.ldx.retrofit2andrxjava2.networkbase.utils;

import android.support.annotation.NonNull;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.Nullable;
import io.reactivex.schedulers.Schedulers;

/**
 * 线程切换
 * 使用compose()
 */
public class SchedulerTransform {

    @Nullable
    private static SchedulerTransform INSTANCE;

    // Prevent direct instantiation.
    private SchedulerTransform() {
    }

    public static synchronized SchedulerTransform getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SchedulerTransform();
        }
        return INSTANCE;
    }

    @NonNull
    public Scheduler computation() {
        return Schedulers.computation();
    }

    @NonNull
    public Scheduler io() {
        return Schedulers.io();
    }

    @NonNull
    public Scheduler ui() {
        return AndroidSchedulers.mainThread();
    }

    @NonNull
    public <T> ObservableTransformer<T, T> applySchedulers() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(io())
                        .observeOn(ui());
            }
        } ;
    }

    @NonNull
    public <T> FlowableTransformer<T, T> flowApplySchedulers() {
        return new FlowableTransformer<T,T>(){
            @Override
            public Publisher<T> apply(Flowable<T> upstream) {
                return upstream.subscribeOn(io())
                        .observeOn(ui());
            }
        };
    }
}

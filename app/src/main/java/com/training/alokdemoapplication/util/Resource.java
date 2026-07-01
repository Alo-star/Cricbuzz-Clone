package com.training.alokdemoapplication.util;

/**
 * Generic wrapper for data that is being loaded from a repository so the UI
 * layer can react to loading / success / error / offline-cache states.
 */
public class Resource<T> {

    public enum Status { LOADING, SUCCESS, ERROR }

    public final Status status;
    public final T data;
    public final String message;
    /** True when {@link #data} came from the local Room cache rather than a fresh network call. */
    public final boolean fromCache;

    private Resource(Status status, T data, String message, boolean fromCache) {
        this.status = status;
        this.data = data;
        this.message = message;
        this.fromCache = fromCache;
    }

    public static <T> Resource<T> loading(T cachedDataOrNull) {
        return new Resource<>(Status.LOADING, cachedDataOrNull, null, cachedDataOrNull != null);
    }

    public static <T> Resource<T> success(T data) {
        return new Resource<>(Status.SUCCESS, data, null, false);
    }

    public static <T> Resource<T> successFromCache(T data) {
        return new Resource<>(Status.SUCCESS, data, null, true);
    }

    public static <T> Resource<T> error(String message, T cachedDataOrNull) {
        return new Resource<>(Status.ERROR, cachedDataOrNull, message, cachedDataOrNull != null);
    }
}

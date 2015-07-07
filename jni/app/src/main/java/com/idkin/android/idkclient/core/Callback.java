package com.idkin.android.idkclient.core;

/**
 * @param <T> expected response type
 */
public interface Callback<T> {

    /**
     * Successful HTTP response.
     *
     * @param t
     */
    void success(T t);

    /**
     * Unsuccessful HTTP response due to network failure, non-2XX status code, or unexpected
     * exception.
     */
    void failure(Object error);
}

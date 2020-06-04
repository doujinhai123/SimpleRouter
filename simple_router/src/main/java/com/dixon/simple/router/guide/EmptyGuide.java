package com.dixon.simple.router.guide;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.SparseArray;

import java.io.Serializable;
import java.util.ArrayList;

public class EmptyGuide implements IGuide {

    private RouterCallback callback;

    @Override
    public IGuide addParams(String key, String value) {
        return this;
    }

    @Override
    public IGuide addParams(String key, String[] value) {
        return this;
    }

    @Override
    public IGuide addStringListParams(String key, ArrayList<String> value) {
        return this;
    }

    @Override
    public IGuide addParams(String key, Bundle value) {
        return this;
    }

    @Override
    public IGuide addParams(String key, byte value) {
        return this;
    }

    @Override
    public IGuide addParams(String key, byte[] value) {
        return this;
    }

    @Override
    public IGuide addParams(String key, char value) {
        return this;
    }

    @Override
    public IGuide addParams(String key, char[] value) {
        return this;
    }

    @Override
    public IGuide addParams(String key, CharSequence value) {
        return this;
    }

    @Override
    public IGuide addParams(String key, CharSequence[] value) {
        return this;
    }

    @Override
    public IGuide addCharSequenceListParams(String key, ArrayList<CharSequence> value) {
        return this;
    }

    @Override
    public IGuide addParams(String key, float value) {
        return this;
    }

    @Override
    public IGuide addParams(String key, float[] value) {
        return this;
    }

    @Override
    public IGuide addParams(String key, int value) {
        return this;
    }

    @Override
    public IGuide addParams(String key, int[] value) {
        return this;
    }

    @Override
    public IGuide addIntListParams(String key, ArrayList<Integer> value) {
        return this;
    }

    @Override
    public IGuide addParams(String key, Parcelable value) {
        return this;
    }

    @Override
    public IGuide addParams(String key, Parcelable[] value) {
        return this;
    }

    @Override
    public IGuide addParcelableListParams(String key, ArrayList<Parcelable> value) {
        return this;
    }

    @Override
    public IGuide addParams(String key, short value) {
        return this;
    }

    @Override
    public IGuide addParams(String key, short[] value) {
        return this;
    }

    @Override
    public IGuide addParams(String key, Serializable value) {
        return this;
    }

    @Override
    public IGuide addParams(String key, boolean value) {
        return this;
    }

    @Override
    public IGuide addParams(String key, boolean[] value) {
        return this;
    }

    @Override
    public IGuide addParams(String key, double value) {
        return this;
    }

    @Override
    public IGuide addParams(String key, double[] value) {
        return this;
    }

    @Override
    public IGuide addParams(String key, long value) {
        return this;
    }

    @Override
    public IGuide addParams(String key, long[] value) {
        return this;
    }

    @Override
    public IGuide addParams(String key, SparseArray<? extends Parcelable> value) {
        return this;
    }

    @Override
    public IGuide interceptor(Interceptor interceptor) {
        return this;
    }

    @Override
    public IGuide callback(RouterCallback callback) {
        this.callback = callback;
        return this;
    }

    @Override
    public void execute() {
        if (callback != null) {
            callback.onLost();
        }
    }

    @Override
    public void execute(int requestCode) {
        if (callback != null) {
            callback.onLost();
        }
    }
}

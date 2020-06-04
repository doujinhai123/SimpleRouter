package com.dixon.simple.router.guide;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.SparseArray;

import java.io.Serializable;
import java.util.ArrayList;

public interface IGuide {

    IGuide addParams(String key, String value);

    IGuide addParams(String key, String[] value);

    IGuide addStringListParams(String key, ArrayList<String> value);

    IGuide addParams(String key, Bundle value);

    IGuide addParams(String key, byte value);

    IGuide addParams(String key, byte[] value);

    IGuide addParams(String key, char value);

    IGuide addParams(String key, char[] value);

    IGuide addParams(String key, CharSequence value);

    IGuide addParams(String key, CharSequence[] value);

    IGuide addCharSequenceListParams(String key, ArrayList<CharSequence> value);

    IGuide addParams(String key, float value);

    IGuide addParams(String key, float[] value);

    IGuide addParams(String key, int value);

    IGuide addParams(String key, int[] value);

    IGuide addIntListParams(String key, ArrayList<Integer> value);

    IGuide addParams(String key, Parcelable value);

    IGuide addParams(String key, Parcelable[] value);

    IGuide addParcelableListParams(String key, ArrayList<Parcelable> value);

    IGuide addParams(String key, short value);

    IGuide addParams(String key, short[] value);

    IGuide addParams(String key, Serializable value);

    IGuide addParams(String key, boolean value);

    IGuide addParams(String key, boolean[] value);

    IGuide addParams(String key, double value);

    IGuide addParams(String key, double[] value);

    IGuide addParams(String key, long value);

    IGuide addParams(String key, long[] value);

    IGuide addParams(String key, SparseArray<? extends Parcelable> value);

    IGuide interceptor(Interceptor interceptor);

    IGuide callback(RouterCallback callback);

    void execute();

    void execute(int requestCode);
}

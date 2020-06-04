package com.dixon.simple.router.guide;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.SparseArray;

import com.dixon.simple.router.guide.execute.Executor;
import com.dixon.simple.router.guide.execute.RouterExecutor;

import java.io.Serializable;
import java.util.ArrayList;

public class SRouterGuide implements IGuide {

    private Context context;
    private String router;
    private Bundle bundle; // 参数合集
    private RouterCallback callback; // 路由监控
    private Interceptor interceptor; // 临时跳转拦截器

    public SRouterGuide(Context context, String router) {
        this.context = context;
        this.router = router;
    }

    @Override
    public IGuide addParams(String key, String value) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putString(key, value);
        return this;
    }

    @Override
    public IGuide addParams(String key, String[] value) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putStringArray(key, value);
        return this;
    }

    @Override
    public IGuide addStringListParams(String key, ArrayList<String> value) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putStringArrayList(key, value);
        return this;
    }

    @Override
    public IGuide addParams(String key, Bundle value) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putBundle(key, bundle);
        return this;
    }

    @Override
    public IGuide addParams(String key, byte value) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putByte(key, value);
        return this;
    }

    @Override
    public IGuide addParams(String key, byte[] value) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putByteArray(key, value);
        return this;
    }

    @Override
    public IGuide addParams(String key, char value) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putChar(key, value);
        return this;
    }

    @Override
    public IGuide addParams(String key, char[] value) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putCharArray(key, value);
        return this;
    }

    @Override
    public IGuide addParams(String key, CharSequence value) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putCharSequence(key, value);
        return this;
    }

    @Override
    public IGuide addParams(String key, CharSequence[] value) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putCharSequenceArray(key, value);
        return this;
    }

    @Override
    public IGuide addCharSequenceListParams(String key, ArrayList<CharSequence> value) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putCharSequenceArrayList(key, value);
        return this;
    }

    @Override
    public IGuide addParams(String key, float value) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putFloat(key, value);
        return this;
    }

    @Override
    public IGuide addParams(String key, float[] value) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putFloatArray(key, value);
        return this;
    }

    @Override
    public IGuide addParams(String key, int value) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putInt(key, value);
        return this;
    }

    @Override
    public IGuide addParams(String key, int[] value) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putIntArray(key, value);
        return this;
    }

    @Override
    public IGuide addIntListParams(String key, ArrayList<Integer> value) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putIntegerArrayList(key, value);
        return this;
    }

    @Override
    public IGuide addParams(String key, Parcelable value) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putParcelable(key, value);
        return this;
    }

    @Override
    public IGuide addParams(String key, Parcelable[] value) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putParcelableArray(key, value);
        return this;
    }

    @Override
    public IGuide addParcelableListParams(String key, ArrayList<Parcelable> value) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putParcelableArrayList(key, value);
        return this;
    }

    @Override
    public IGuide addParams(String key, short value) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putShort(key, value);
        return this;
    }

    @Override
    public IGuide addParams(String key, short[] value) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putShortArray(key, value);
        return this;
    }

    @Override
    public IGuide addParams(String key, Serializable value) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putSerializable(key, value);
        return this;
    }

    @Override
    public IGuide addParams(String key, boolean value) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putBoolean(key, value);
        return this;
    }

    @Override
    public IGuide addParams(String key, boolean[] value) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putBooleanArray(key, value);
        return this;
    }

    @Override
    public IGuide addParams(String key, double value) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putDouble(key, value);
        return this;
    }

    @Override
    public IGuide addParams(String key, double[] value) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putDoubleArray(key, value);
        return this;
    }

    @Override
    public IGuide addParams(String key, long value) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putLong(key, value);
        return this;
    }

    @Override
    public IGuide addParams(String key, long[] value) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putLongArray(key, value);
        return this;
    }

    @Override
    public IGuide addParams(String key, SparseArray<? extends Parcelable> value) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putSparseParcelableArray(key, value);
        return this;
    }

    @Override
    public IGuide interceptor(Interceptor interceptor) {
        this.interceptor = interceptor;
        return this;
    }

    @Override
    public IGuide callback(RouterCallback callback) {
        this.callback = callback;
        return this;
    }

    @Override
    public void execute() {
        new RouterExecutor(context, router, bundle, callback, interceptor).execute();
    }

    @Override
    public void execute(int requestCode) {
        new RouterExecutor(context, router, bundle, callback, interceptor, requestCode).execute();
    }
}

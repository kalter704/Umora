package ru.comp.vas.umora;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;
import java.util.List;

public class ContentLoader extends AsyncTaskLoader<List<Content>> {

    private Source mSource;
    private List<Content> items = new ArrayList<>();

    public ContentLoader(Context context, Source source) {
        super(context);
        mSource = source;
    }

    @Override
    protected void onStartLoading() {
        if (items.isEmpty()) {
            onForceLoad();
        }
        if (!items.isEmpty()) {
            deliverResult(items);
        }
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    public void onCanceled(List<Content> data) {
        if (!items.isEmpty()) {
            items.clear();
        }
    }

    @Override
    public void deliverResult(List<Content> data) {
        if (isReset()) {
            if (!items.isEmpty()) {
                items.clear();
            }
            return;
        }
        if (isStarted()) {
            super.deliverResult(data);
        }
    }

    @Override
    protected void onReset() {
        super.onReset();

        onStopLoading();

        if (!items.isEmpty()) {
            items.clear();
        }
    }

    @Override
    public List<Content> loadInBackground() {
        if (items.isEmpty()) {
            try {
                return UmoriApiClass.fetch(mSource);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        } else {
            return items;
        }
    }
}

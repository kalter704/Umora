package ru.comp.vas.umora;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ViewAnimator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContentActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Content>> {

    private static final String BUNDLE_EXTRA = "bundle_extra";

    private final int VIEWANIMATOR_LOADING_TEXT = 0;
    private final int VIEWANIMATOR_RECYCLEVIEW = 1;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.view_animator)
    ViewAnimator mViewAnimator;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private Source mSource;

    public static Intent newIntent(Context context, Source source) {
        Intent i = new Intent(context, ContentActivity.class);
        i.putExtra(BUNDLE_EXTRA, source.getBundle());
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        mSource = new Source(getIntent().getBundleExtra(BUNDLE_EXTRA));
        getSupportActionBar().setTitle(mSource.getDesc());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mSwipeRefreshLayout.setColorSchemeColors(
                    getResources().getColor(R.color.colorPrimary, getTheme()),
                    getResources().getColor(R.color.colorAccent, getTheme())
            );
        } else {
            mSwipeRefreshLayout.setColorSchemeColors(
                    getResources().getColor(R.color.colorPrimary),
                    getResources().getColor(R.color.colorAccent)
            );
        }
        mSwipeRefreshLayout.setRefreshing(true);
        mSwipeRefreshLayout.setOnRefreshListener(() -> getSupportLoaderManager().restartLoader(0, Bundle.EMPTY, this));

        mViewAnimator.setDisplayedChild(VIEWANIMATOR_LOADING_TEXT);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);

        getSupportLoaderManager().initLoader(0, Bundle.EMPTY, this);

    }

    @Override
    public Loader<List<Content>> onCreateLoader(int id, Bundle args) {
        try {
            return new ContentLoader(this, mSource);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<List<Content>> loader, List<Content> data) {
        if (data == null) {
            mSwipeRefreshLayout.setRefreshing(false);
            mViewAnimator.setDisplayedChild(VIEWANIMATOR_RECYCLEVIEW);
            return;
        }
        if (mRecyclerView.getAdapter() != null) {
            if (!data.get(0).getElementPureHtml().equals(((ContentAdapter) mRecyclerView.getAdapter()).getContents().get(0).getElementPureHtml())) {
                mRecyclerView.setAdapter(new ContentAdapter(this, data));
            }
        } else {
            mRecyclerView.setAdapter(new ContentAdapter(this, data));
        }
        if (mViewAnimator.getDisplayedChild() != VIEWANIMATOR_RECYCLEVIEW) {
            mViewAnimator.setDisplayedChild(VIEWANIMATOR_RECYCLEVIEW);
        }
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoaderReset(Loader<List<Content>> loader) {
        mRecyclerView.setAdapter(new ContentAdapter(this, new ArrayList<>()));
    }
}

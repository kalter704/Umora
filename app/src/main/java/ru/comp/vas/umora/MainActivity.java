package ru.comp.vas.umora;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private List<Source> mSources = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        String[] sites = getResources().getStringArray(R.array.sites);
        String[] names = getResources().getStringArray(R.array.names);
        String[] desc = getResources().getStringArray(R.array.desc);

        for(int i = 0; i < sites.length; ++i) {
            mSources.add(new Source(sites[i], names[i], desc[i]));
        }

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(new SourceAdapter(this, mSources));
    }
}

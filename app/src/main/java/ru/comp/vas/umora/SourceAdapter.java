package ru.comp.vas.umora;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SourceAdapter extends RecyclerView.Adapter<SourceAdapter.SourceHolder> {

    private Context mContext;
    private List<Source> mSourceList;

    public SourceAdapter(Context context, List<Source> sourceList) {
        mContext = context;
        mSourceList = sourceList;
    }

    @Override
    public SourceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SourceHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.source_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(SourceHolder holder, int position) {
        holder.bind(mSourceList.get(position));
    }

    @Override
    public int getItemCount() {
        return mSourceList.size();
    }

    public void setSourceList(List<Source> sourceList) {
        mSourceList = sourceList;
    }


    public class SourceHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_text)
        TextView mTextView;

        private Source mSource;

        public SourceHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener((v) -> {
                mContext.startActivity(ContentActivity.newIntent(mContext, mSource));
            });
        }

        public void bind(Source source) {
            mSource = source;
            mTextView.setText(source.getDesc());
        }

    }
}

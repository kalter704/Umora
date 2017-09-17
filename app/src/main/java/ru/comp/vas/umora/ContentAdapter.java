package ru.comp.vas.umora;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ContentHolder> {

    private Context mContext;
    private List<Content> mContents;

    public ContentAdapter(Context context, List<Content> contents) {
        mContext = context;
        mContents = contents;
    }

    @Override
    public ContentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContentHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.content_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ContentHolder holder, int position) {
        holder.bind(mContents.get(position));
    }

    @Override
    public int getItemCount() {
        return mContents.size();
    }

    public List<Content> getContents() {
        return mContents;
    }

    class ContentHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_text)
        TextView mTextView;

        @BindView(R.id.btn_share)
        Button mButton;

        private Content mContent;

        public ContentHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            mButton.setOnClickListener((v) -> {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_SEND);
                i.putExtra(Intent.EXTRA_TEXT, mContext.getString(R.string.share_text, mTextView.getText()));
                i.setType("text/plain");
                mContext.startActivity(Intent.createChooser(i,"Поделиться"));
            });
        }

        public void bind(Content content) {
            mContent = content;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mTextView.setText(Html.fromHtml(mContent.getElementPureHtml(), Html.FROM_HTML_MODE_LEGACY));
            } else {
                mTextView.setText(Html.fromHtml(mContent.getElementPureHtml()));
            }
        }

    }

}

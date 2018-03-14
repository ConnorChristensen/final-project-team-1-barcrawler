package cs492.barcrawler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import cs492.barcrawler.Utils.YelpAPIUtils;

/**
 * Created by Will on 3/14/18.
 */

public class BarAdapter extends RecyclerView.Adapter<BarAdapter.BarViewHolder> {
    private ArrayList<YelpAPIUtils.YelpItem> mBarList;
    private OnBarClickListener mBarClickListener;
    private Context mContext;

    public interface OnBarClickListener {
        void onBarItemClick(YelpAPIUtils.YelpItem barItem);
    }

    public BarAdapter(Context context, OnBarClickListener clickListener) {
        mContext = context;
        mBarClickListener = clickListener;
    }

    public void updateBarItems(ArrayList<YelpAPIUtils.YelpItem> barList) {
        mBarList = barList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mBarList != null) {
            return mBarList.size();
        } else {
            return 0;
        }
    }

    @Override
    public BarAdapter.BarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.bar_item, parent, false);
        return new BarAdapter.BarViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BarAdapter.BarViewHolder holder, int position) {
        holder.bind(mBarList.get(position));
    }

    class BarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mBarTV;

        public BarViewHolder(View itemView) {
            super(itemView);
            mBarTV = itemView.findViewById(R.id.tv_bar_item);
            itemView.setOnClickListener(this);
        }

        public void bind(YelpAPIUtils.YelpItem barItem) {
            mBarTV.setText(barItem.barName);
        }

        @Override
        public void onClick(View v) {
            YelpAPIUtils.YelpItem barItem = mBarList.get(getAdapterPosition());
            mBarClickListener.onBarItemClick(barItem);
        }
    }
}

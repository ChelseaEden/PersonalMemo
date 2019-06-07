package com.example.g.personalmemo.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.g.personalmemo.R;
import com.example.g.personalmemo.util.Memo;

import java.util.List;

public class MemoAdapter extends RecyclerView.Adapter<MemoAdapter.ViewHolder>{

    private OnItemLongClickListener mlongClickListener;
    private OnItemClickListener mClickListener;
    private static Context mContext;
    private static List<Memo> memoList;
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private OnItemLongClickListener mlongListener;
        private OnItemClickListener mListener;
        ImageView memoimg;
        TextView memotitle;
        TextView memotime;
        TextView itemDelete;

        public ViewHolder(View view, OnItemClickListener listener,OnItemLongClickListener longlistener){
            super(view);

            itemDelete = (TextView)view.findViewById(R.id.tv_delete);
            mListener = (OnItemClickListener) listener;
            mlongListener = (OnItemLongClickListener) longlistener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            itemDelete.setOnClickListener(this);
            memoimg = (ImageView)view.findViewById(R.id.img_memo);
            memotitle = (TextView)view.findViewById(R.id.title_memo);
            memotime = (TextView)view.findViewById(R.id.date_memo);

           /* view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(v,getPosition());
                    //Toast.makeText(mContext, "当前点击 "+ mfileList.get(getLayoutPosition()).getName(), Toast.LENGTH_SHORT).show();
                }
            }); */
        }

        @Override
        public boolean onLongClick(View v) {
            mlongListener.onItemLongClick(v,getPosition());
            return true;
        }

        @Override
        public void onClick(View v) {
            mListener.onItemClick(v,getPosition());
        }

    }
    public MemoAdapter(Context mContext, List<Memo> tmemoList){
        this.mContext = mContext;
        memoList = tmemoList;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int postion);
    }
    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int postion);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mClickListener = listener;
    }
    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.mlongClickListener = listener;
    }

    @Override
    public MemoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item,parent,false);
         ViewHolder holder = new ViewHolder(view,mClickListener,mlongClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(MemoAdapter.ViewHolder holder, int position) {
        Memo mm = memoList.get(position);
        if (mm.getKindId()== 0){
            holder.memoimg.setImageResource(R.drawable.password_img);
        }else if (mm.getKindId() == 1){
            holder.memoimg.setImageResource(R.drawable.date_img);
        }else if(mm.getKindId() == 2){
            holder.memoimg.setImageResource(R.drawable.ic_time);
        }else {
            holder.memoimg.setImageResource(R.drawable.ic_hreat);
        }
        holder.memotitle.setText(mm.getTitle());
        holder.memotime.setText(mm.getTime());
    }


    @Override
    public int getItemCount() {
        return memoList.size();
    }
    @Override
    public int getItemViewType(int position){
        return 1;
    }

}

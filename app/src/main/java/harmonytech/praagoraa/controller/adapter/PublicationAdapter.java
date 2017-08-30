package harmonytech.praagoraa.controller.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import harmonytech.praagoraa.R;
import harmonytech.praagoraa.controller.domain.Provider;
import harmonytech.praagoraa.controller.interfaces.RecyclerViewOnClickListenerHack;

public class PublicationAdapter extends RecyclerView.Adapter<PublicationAdapter.MyViewHolder>{

    private List<Provider> mList;
    private LayoutInflater mLayoutInflater;
    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;
    private Context c;

    public PublicationAdapter(Context c, List<Provider> l){
        this.c = c;
        mList = l;
        mLayoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public PublicationAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View v = mLayoutInflater.inflate(R.layout.item_publication, viewGroup, false);
        return new PublicationAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PublicationAdapter.MyViewHolder myViewHolder, int position) {

        myViewHolder.publicationCategory.setText(mList.get(position).getCategoryScreen());
        myViewHolder.publicationSubcategory.setText(mList.get(position).getSubcategoryScreen());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack r){
        mRecyclerViewOnClickListenerHack = r;
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener /*View.OnCreateContextMenuListener*/{
        TextView publicationCategory, publicationSubcategory;

        MyViewHolder(View itemView) {
            super(itemView);
            publicationCategory = (TextView) itemView.findViewById(R.id.publication_category);
            publicationSubcategory = (TextView) itemView.findViewById(R.id.publication_subcategory);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mRecyclerViewOnClickListenerHack != null){
                mRecyclerViewOnClickListenerHack.onClickListener(v, getAdapterPosition());
            }
        }
    }
}

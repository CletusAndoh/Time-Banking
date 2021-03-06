package com.timebanking.firebase;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    Context context;
    ArrayList<String> emaillist;
    ArrayList<String> namelist;

    class SearchViewHolder extends RecyclerView.ViewHolder{
        TextView name, email;


        public SearchViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            email = itemView.findViewById(R.id.email);


        }
    }

    public SearchAdapter(Context context, ArrayList<String> emaillist, ArrayList<String> namelist) {
        this.context = context;
        this.emaillist = emaillist;
        this.namelist = namelist;
    }



    @Override
    public SearchAdapter.SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_list_items, parent, false);
        return new SearchAdapter.SearchViewHolder(view);

    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, int position) {
        holder.name.setText(namelist.get(position));
        holder.email.setText(emaillist.get(position));


    }


    @Override
    public int getItemCount() {
        return namelist.size();
    }
}

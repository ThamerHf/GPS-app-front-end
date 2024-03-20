package com.akatsuki.gps_app_front;

import static androidx.recyclerview.widget.RecyclerView.*;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CollectionListItemAdapter extends ArrayAdapter<String> implements Filterable {

    private Context context;
    private LayoutInflater inflater;

    private List<String> filteredCollection;

    private List<String> originalCollection;

    public CollectionListItemAdapter(@NonNull Context context, int resource,
                           @NonNull List<String> collections) {
        super(context, resource, collections);
        inflater = LayoutInflater.from(context);
        filteredCollection = new ArrayList<>(collections);
        originalCollection = new ArrayList<>(collections);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder viewHolder;

        if(convertView==null){
        convertView=inflater.inflate(R.layout.fragment_collection_list_item,parent,false);
        viewHolder=new ViewHolder(convertView);
        convertView.setTag(viewHolder);
        }else{
        viewHolder=(ViewHolder)convertView.getTag();
        }

        String tag=getItem(position);

        // Remplir la vue avec les données de l'élément
        viewHolder.collectionName.setText(tag);
        // Modifiez l'image ici si nécessaire (par exemple, à partir de ressources, d'URL, etc.)

        return convertView;
    }

    private static class ViewHolder {
        TextView collectionName;


        ViewHolder(View view) {
            collectionName = view.findViewById(R.id.collectionName);
        }
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString().toLowerCase().trim();
                List<String> filteredList = new ArrayList<>();
                if (charString.isEmpty()) {
                    filteredList.addAll(originalCollection);
                } else {
                    for (String tag : originalCollection) {
                        if (tag.toLowerCase().contains(charString)) {
                            filteredList.add(tag);
                        }
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredCollection = (List<String>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public int getCount() {
        return filteredCollection.size();
    }

    @Override
    public String getItem(int position) {
        return filteredCollection.get(position);
    }
}
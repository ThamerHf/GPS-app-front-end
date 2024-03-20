package com.akatsuki.gps_app_front;

import android.content.Context;

import androidx.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.akatsuki.gps_app_front.data.model.entity.Collection;

import java.util.ArrayList;
import java.util.List;

public class CollectionListItemAdapter extends ArrayAdapter<Collection> implements Filterable {

    private Context context;
    private LayoutInflater inflater;

    private List<Collection> filteredCollection;

    private List<Collection> originalCollection;

    public CollectionListItemAdapter(@NonNull Context context, int resource,
                           @NonNull List<Collection> collections) {
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

        Collection collection = getItem(position);

        // Remplir la vue avec les données de l'élément
        viewHolder.collectionName.setText(collection.getTag());
        viewHolder.occurenceNumber.setText(Math.toIntExact(collection.getNumberOfLocations()));
        // Modifiez l'image ici si nécessaire (par exemple, à partir de ressources, d'URL, etc.)

        return convertView;
    }

    private static class ViewHolder {
        TextView collectionName;
        TextView occurenceNumber;

        ViewHolder(View view) {

            collectionName = view.findViewById(R.id.collectionName);
            occurenceNumber = view.findViewById(R.id.collectionNumberElem);

        }
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString().toLowerCase().trim();
                List<Collection> filteredList = new ArrayList<>();
                if (charString.isEmpty()) {
                    filteredList.addAll(originalCollection);
                } else {
                    for (Collection collection : originalCollection) {
                        if (collection.getTag().toLowerCase().contains(charString)) {
                            filteredList.add(collection);
                        }
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredCollection = (List<Collection>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public int getCount() {
        return filteredCollection.size();
    }

    @Override
    public Collection getItem(int position) {
        return filteredCollection.get(position);
    }
}
package com.akatsuki.gps_app_front;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.akatsuki.gps_app_front.data.model.entity.Collection;
import com.akatsuki.gps_app_front.ui.collection.CollectionFragment;
import com.akatsuki.gps_app_front.ui.location.LocationFragment;
import com.akatsuki.gps_app_front.ui.locations.LocationsFragment;

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
        if (collection.getNumberOfLocations() != null) {
            viewHolder.occurenceNumber.setText(""+collection.getNumberOfLocations());
        }
        // Modifiez l'image ici si nécessaire (par exemple, à partir de ressources, d'URL, etc.)

        convertView.findViewById(R.id.cardCollection).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Effectuer la transition vers le nouveau fragment
                // Créer le nouveau fragment à afficher
                Fragment newFragment = new LocationsFragment();

                // Commencer la transaction pour remplacer le fragment actuel par le nouveau fragment
                FragmentTransaction transaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.main_container, newFragment);
                transaction.addToBackStack(null); // Ajouter à la pile de retour arrière, si nécessaire
                transaction.commit();
            }
        });

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
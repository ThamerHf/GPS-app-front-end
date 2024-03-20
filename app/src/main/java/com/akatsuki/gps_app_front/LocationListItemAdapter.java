package com.akatsuki.gps_app_front;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.akatsuki.gps_app_front.data.model.entity.Location;
import com.akatsuki.gps_app_front.ui.location.LocationFragment;
import com.akatsuki.gps_app_front.ui.location.LocationsFragment;

import java.util.ArrayList;
import java.util.List;

public class LocationListItemAdapter extends ArrayAdapter<Location> implements Filterable {

    private Context context;

    private LayoutInflater inflater;

    private List<Location> filteredCollection;

    private List<Location> originalCollection;

    public LocationListItemAdapter(@NonNull Context context, int resource,
                                     @NonNull List<Location> locations) {
        super(context, resource, locations);
        inflater = LayoutInflater.from(context);
        filteredCollection = new ArrayList<>(locations);
        originalCollection = new ArrayList<>(locations);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LocationListItemAdapter.ViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.location_list_item, parent, false);
            viewHolder = new LocationListItemAdapter.ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (LocationListItemAdapter.ViewHolder) convertView.getTag();
        }

        Location location = getItem(position);

        viewHolder.locationName.setText(location.getTitle());
        String tags = "";
        int i;
        for(i = 0; i <= 4; i++) {
            try {
                tags = tags + location.getTags().get(i) + ", ";
            } catch (Exception e) {
                // Gérer l'exception si nécessaire
            }
        }
        tags = tags + "...";
        viewHolder.tags.setText(tags);
        if(location.getImage() == null){
            viewHolder.image.setImageResource(R.drawable.collection_default_image);
        }

        convertView.findViewById(R.id.cardLocation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Effectuer la transition vers le nouveau fragment
                // Créer le nouveau fragment à afficher
                Fragment newFragment = new LocationFragment(location);

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
        TextView locationName;
        TextView tags;
        ImageView image;
        ViewHolder(View view) {

            locationName = view.findViewById(R.id.LocationName);
            tags = view.findViewById(R.id.LocationSupportingText);
            image = view.findViewById(R.id.LocationImage);
        }
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString().toLowerCase().trim();
                List<Location> filteredList = new ArrayList<>();
                if (charString.isEmpty()) {
                    filteredList.addAll(originalCollection);
                } else {
                    for (Location location : originalCollection) {
                        if (location.getTitle().toLowerCase().contains(charString)) {
                            filteredList.add(location);
                        }
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredCollection = (List<Location>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public int getCount() {
        return filteredCollection.size();
    }

    @Override
    public Location getItem(int position) {
        return filteredCollection.get(position);
    }
}

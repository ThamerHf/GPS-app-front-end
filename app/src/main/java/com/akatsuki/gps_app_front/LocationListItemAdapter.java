package com.akatsuki.gps_app_front;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.akatsuki.gps_app_front.data.model.entity.Location;

import java.util.ArrayList;
import java.util.List;

public class LocationListItemAdapter extends ArrayAdapter<Location> implements Filterable {

    private Context context;

    private LayoutInflater inflater;

    private List<Location> filteredCollection;

    private List<Location> originalCollection;

    public LocationListItemAdapter(@NonNull Context context, int resource,
                                     @NonNull List<Location> collections) {
        super(context, resource, collections);
        inflater = LayoutInflater.from(context);
        filteredCollection = new ArrayList<>(collections);
        originalCollection = new ArrayList<>(collections);
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

        // Remplir la vue avec les données de l'élément

        viewHolder.locationName.setText(location.getTitle());
        viewHolder.description.setText(location.getDescription());
        if(location.getImage() == null){
            viewHolder.image.setImageResource(R.drawable.collection_default_image);
        }
        else {
            Bitmap bitmap = BitmapFactory
                    .decodeByteArray(location.getImage(),0, location.getImage().length);
            viewHolder.image.setImageBitmap(bitmap);
        }

        return convertView;
    }

    private static class ViewHolder {
        TextView locationName;
        TextView description;
        ImageView image;
        ViewHolder(View view) {

            locationName = view.findViewById(R.id.LocationName);
            description  = view.findViewById(R.id.LocationSupportingText);
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

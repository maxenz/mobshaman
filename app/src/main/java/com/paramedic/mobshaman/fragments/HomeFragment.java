package com.paramedic.mobshaman.fragments;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.paramedic.mobshaman.R;

/**
 * Provides UI for the view with Cards.
 */
public class HomeFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.recycler_view, container, false);
        ContentAdapter adapter = new ContentAdapter(recyclerView.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return recyclerView;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView serviceNumber, serviceAddress;
        public ImageView ambulancePicture;
        public View cardBorderTop;
        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_card, parent, false));
            serviceNumber = (TextView) itemView.findViewById(R.id.card_service_number);
            serviceAddress = (TextView) itemView.findViewById(R.id.card_service_address);
            ambulancePicture = (ImageView) itemView.findViewById(R.id.card_ambulance_image);
            cardBorderTop = itemView.findViewById(R.id.card_service_border_top_color);
        }
    }
    /**
     * Adapter to display recycler view.
     */
    public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {
        // Set numbers of List in RecyclerView.
        private static final int LENGTH = 18;
        private final Drawable[] mAmbulancePictures;
        public ContentAdapter(Context context) {
            Resources resources = context.getResources();
            TypedArray a = resources.obtainTypedArray(R.array.ambulances);
            mAmbulancePictures = new Drawable[a.length()];
            for (int i = 0; i < mAmbulancePictures.length; i++) {
                mAmbulancePictures[i] = a.getDrawable(i);
            }
            a.recycle();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.serviceNumber.setText("Incidente AB0");
            holder.serviceAddress.setText("Av. Alvarez Thomas 1235, Capital Federal");
            holder.ambulancePicture.setImageDrawable(mAmbulancePictures[position % mAmbulancePictures.length]);
            holder.cardBorderTop.setBackgroundColor(ContextCompat.getColor(holder.cardBorderTop.getContext(), R.color.ambulance_green));
//            holder.name.setText(mPlaces[position % mPlaces.length]);
//            holder.description.setText(mPlaceDesc[position % mPlaceDesc.length]);
        }

        @Override
        public int getItemCount() {
            return LENGTH;
        }
    }
}

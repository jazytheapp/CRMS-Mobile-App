package ca.gbc.comp3074.crms;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import ca.gbc.comp3074.crms.models.Restaurant;

public class ListAdapter extends ArrayAdapter implements Filterable {

    private Activity mContext;
    List<Restaurant> restaurantList;

    public ListAdapter(Activity mContext, List<Restaurant> restaurantList){
        super(mContext, R.layout.list_item, restaurantList );
        this.mContext = mContext;
        this.restaurantList = restaurantList;


    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = mContext.getLayoutInflater();
        View listItemView = inflater.inflate(R.layout.list_item,null,true);

        TextView resName = listItemView.findViewById(R.id.restaurantName);
        TextView resAddress = listItemView.findViewById(R.id.restaurantAddress);
        ImageView resImage = listItemView.findViewById(R.id.rImageView);
        RatingBar resRating = listItemView.findViewById(R.id.restaurantRating);
        Button btnBooking = listItemView.findViewById(R.id.booking);

        Restaurant restaurant = restaurantList.get(position);

        resName.setText(restaurant.getName());
        resAddress.setText(restaurant.getAddress());
        Picasso.get().load(restaurant.getImage()). into(resImage);
        resRating.setRating(restaurant.getRating().floatValue());

        btnBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mContext.startActivity(new Intent(mContext,MakeReservation.class));
                Log.d("click","clicked");
                Intent intent = new Intent(mContext, MakeReservation.class);
                intent.putExtra("Restaurant", restaurantList.get(position).getName());
                mContext.startActivity(intent);
            }
        });

        return listItemView;
    }
}

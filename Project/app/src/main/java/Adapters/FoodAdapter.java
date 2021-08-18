package Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.iraqoz.project.R;

import java.util.ArrayList;

import Models.Food;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.StartersViewHolder> {

    private ArrayList<Food>starters;
    public FoodAdapter(ArrayList<Food> starters){
        this.starters = starters;
    }

    @Override
    public int getItemCount()
    {
        return starters.size();
    }

    @NonNull
    @Override
    public StartersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_food_item_template,parent,false);
        return new StartersViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull StartersViewHolder holder, int position) {
        holder.bind(starters.get(position));
    }


    static class StartersViewHolder extends RecyclerView.ViewHolder{

        ImageView Icon;
        TextView Title;
        TextView Description;
        TextView Price;
        public StartersViewHolder(@NonNull View itemView) {
            super(itemView);
            Icon= itemView.findViewById(R.id.image_view_cart_view_img);
            Title= itemView.findViewById(R.id.text_view_title_food_item_template);
            //Description=itemView.findViewById(R.id.);
            Price=itemView.findViewById(R.id.text_view_price_food_item_template);

        }
        public void bind(Food food){
            Glide.with(itemView).load(food.imageUrl).into(Icon);
            Title.setText(food.foodname);
            Description.setText(food.description);
            Price.setText(food.price +" KSH");
        }
    }


}

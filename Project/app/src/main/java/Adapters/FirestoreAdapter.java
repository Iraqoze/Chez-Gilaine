package Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.iraqoz.project.R;

import Models.Food;

public class FirestoreAdapter extends FirestoreRecyclerAdapter<Food,FirestoreAdapter.FoodViewHolder> {
private OnListItemClick onListItemClick;

    public FirestoreAdapter(@NonNull FirestoreRecyclerOptions<Food> options, OnListItemClick onListItemClick) {
        super(options);
        this.onListItemClick=onListItemClick;
    }

    @Override
    protected void onBindViewHolder(@NonNull FoodViewHolder holder, int position, @NonNull Food food) {
        Glide.with(holder.itemView).load(food.imageUrl).into(holder.Icon);
        holder.Title.setText(food.getFoodname());
        //holder.Description.setText(food.getDescription());
        holder.Price.setText("@ "+food.getPrice()+" KSH");
        System.out.println("FOOD NAME: "+ food.getFoodname());

    }
    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_food_item_template,parent,false);

        return new FoodViewHolder(view);
    }
    public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView Icon;
        TextView Title;
        //TextView Description;
        TextView Price;
        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            Icon= itemView.findViewById(R.id.image_view_cart_view_img);
            Title= itemView.findViewById(R.id.text_view_title_food_item_template);
            //Description=itemView.findViewById(R.id.text_view_description_prod_detail);
            Price=itemView.findViewById(R.id.text_view_price_food_item_template);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onListItemClick.onItemclick(getItem(getAdapterPosition()), getAdapterPosition());
        }
    }
    public interface OnListItemClick{
        void onItemclick(Food foodItem, int position);
    }
}

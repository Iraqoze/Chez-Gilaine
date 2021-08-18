package Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iraqoz.project.R;

import java.util.ArrayList;

import Models.Order;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrderViewHolder> {
    private OnCategoryFoodItemClickListener onCategoryFoodItemClickListener;
    private ArrayList<Order>categories;
    public OrdersAdapter(ArrayList<Order> categories, OnCategoryFoodItemClickListener onCategoryFoodItemClickListener){
        this.categories = categories;
        this.onCategoryFoodItemClickListener=onCategoryFoodItemClickListener;
    }

    @Override
    public int getItemCount()
    {
        return categories.size();
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_order_history_item_template,parent,false);
        return new OrderViewHolder(view,onCategoryFoodItemClickListener);

    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        holder.bind(categories.get(position));
    }


    public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        OnCategoryFoodItemClickListener onCategoryFoodItemClickListener;

        ImageView Icon;
        TextView Date, Amount;
        public OrderViewHolder(@NonNull View itemView, OnCategoryFoodItemClickListener onCategoryFoodItemClickListener) {
            super(itemView);
           // Icon= itemView.findViewById(R.id.image_view_category);
            Date = itemView.findViewById(R.id.textView_order_history_date);
            Amount=itemView.findViewById(R.id.textView_order_history_total_price);
            this.onCategoryFoodItemClickListener=onCategoryFoodItemClickListener;

        }
        public void bind(Order category){
            //Glide.with(itemView).load(category.imageurl).into(Icon);
            Date.setText(category.getTime());
            Amount.setText("@ "+category.getTotalAmount()+" KSH");
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onCategoryFoodItemClickListener.onCategoryFoodItemClick(categories.get(getAdapterPosition()));
        }
    }
    public interface OnCategoryFoodItemClickListener {
        void onCategoryFoodItemClick(Order order);
    }
}

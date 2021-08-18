package Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.iraqoz.project.R;

import java.util.ArrayList;

import Models.CartItem;
import Models.Category;
import Services.DatabaseHelper;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.CartItemViewHolder> {

    private  OnCartRemoveItemClickListener onCartRemoveItemClickListener;
    private ArrayList<CartItem>cartItems;
    public CartItemAdapter(ArrayList<CartItem> cartItems, OnCartRemoveItemClickListener onCartRemoveItemClickListener){
        this.cartItems = cartItems;
        this.onCartRemoveItemClickListener=onCartRemoveItemClickListener;
    }

    @Override
    public int getItemCount()
    {
        return cartItems.size();
    }

    @NonNull
    @Override
    public CartItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_cart_view_item_template,parent,false);
        return new CartItemViewHolder(view, onCartRemoveItemClickListener);

    }

    @Override
    public void onBindViewHolder(@NonNull CartItemViewHolder holder, int position) {
        holder.bind(cartItems.get(position));
    }


    public class CartItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        OnCartRemoveItemClickListener onCartRemoveItemClickListener;
        DatabaseHelper _dbHelper;
        ImageView Url;
        TextView Name, Quantity, Amount;
        ImageButton removeItemBTN;
        public CartItemViewHolder(@NonNull View itemView, OnCartRemoveItemClickListener onCartRemoveItemClickListener) {
            super(itemView);
            _dbHelper=new DatabaseHelper(itemView.getContext());
            Name=itemView.findViewById(R.id.textView_cart_view_name);
            Url = itemView.findViewById(R.id.image_view_cart_view_img);
            Quantity=itemView.findViewById(R.id.textView_cart_view_quantity);
            Amount=itemView.findViewById(R.id.textView_cartview_total_amount);
            removeItemBTN=itemView.findViewById(R.id.imageButton_cart_view_remove);
            this.onCartRemoveItemClickListener=onCartRemoveItemClickListener;

        }
        public void bind(CartItem cartItem){
            Name.setText(""+cartItem.getName());
           Glide.with(itemView).load(cartItem.getUrl()).into(Url);
            Quantity.setText("Quantity: "+cartItem.getQuantity());
            Amount.setText("Total Amount: "+cartItem.getAmount()+" KSH");
            removeItemBTN.setOnClickListener(this);
            /*new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("Remove Button Has Been Clicked");
                    _dbHelper.deleteCartItem(cartItem.getUrl());
                    Toast.makeText(itemView.getContext(), cartItem.getName()+" is removed from cart",Toast.LENGTH_SHORT).show();
                }
            }*/
        }

        @Override
        public void onClick(View v) {
            onCartRemoveItemClickListener.onCartRemoveItemClick(cartItems.get(getAdapterPosition()));
        }
    }
    public interface OnCartRemoveItemClickListener {
        void onCartRemoveItemClick(CartItem cartItem);
    }

}

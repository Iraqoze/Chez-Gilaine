package Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.iraqoz.project.CategoryFoodItemTemplateActivity;
import com.iraqoz.project.R;

import java.util.ArrayList;

import Models.Category;
import Models.Food;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private OnCategoryFoodItemClickListener onCategoryFoodItemClickListener;
    private ArrayList<Category>categories;
    public CategoryAdapter(ArrayList<Category> categories, OnCategoryFoodItemClickListener onCategoryFoodItemClickListener){
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
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_category_item_template,parent,false);
        return new CategoryViewHolder(view,onCategoryFoodItemClickListener);

    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.bind(categories.get(position));
    }


    public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        OnCategoryFoodItemClickListener onCategoryFoodItemClickListener;

        ImageView Icon;
        TextView Title;
        public CategoryViewHolder(@NonNull View itemView, OnCategoryFoodItemClickListener onCategoryFoodItemClickListener) {
            super(itemView);
            Icon= itemView.findViewById(R.id.image_view_category);
            Title= itemView.findViewById(R.id.text_view_category_name);
            this.onCategoryFoodItemClickListener=onCategoryFoodItemClickListener;

        }
        public void bind(Category category){
           Glide.with(itemView).load(category.imageurl).into(Icon);
            Title.setText(category.name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
        onCategoryFoodItemClickListener.onCategoryFoodItemClick(categories.get(getAdapterPosition()));
        }
    }
    public interface OnCategoryFoodItemClickListener {
        void onCategoryFoodItemClick(Category category);
    }
}

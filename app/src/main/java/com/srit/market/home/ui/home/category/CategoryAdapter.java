package com.srit.market.home.ui.home.category;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.srit.market.R;
import com.srit.market.databinding.ItemHomeBinding;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private List<CategoryModel> modelList;
    private Context context;
    ItemListener lestiner;

    public interface ItemListener {
        public void onItemClick(CategoryModel categoryModel);
    }

    public void setListener(ItemListener listener) {
        this.lestiner = listener;
    }

    public CategoryAdapter(Context context, List<CategoryModel> list) {
        this.context = context;
        this.modelList = list;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ItemHomeBinding binding;

        ViewHolder(@NonNull ItemHomeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(final CategoryModel dayModel) {
            binding.setItem(dayModel);
            binding.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lestiner.onItemClick(dayModel);
                }
            });
            binding.hasPendingBindings();
        }
    }


    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemHomeBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                        R.layout.item_home, viewGroup, false);
        return new CategoryAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, final int position) {
        holder.bind(modelList.get(position));
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }
}

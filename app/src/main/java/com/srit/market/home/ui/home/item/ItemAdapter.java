package com.srit.market.home.ui.home.item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.srit.market.R;
import com.srit.market.databinding.ItemHomeBinding;
import com.srit.market.databinding.ItemNewOrderBinding;
import com.srit.market.home.ui.home.category.CategoryModel;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private List<ItemModel> modelList;
    private Context context;


    public ItemAdapter(Context context, List<ItemModel> list) {
        this.context = context;
        this.modelList = list;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ItemNewOrderBinding binding;

        ViewHolder(@NonNull ItemNewOrderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(ItemModel dayModel) {
            binding.setItem(dayModel);
            binding.hasPendingBindings();
        }
    }


    @NonNull
    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemNewOrderBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                        R.layout.item_new_order, viewGroup, false);
        return new ItemAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ViewHolder holder, final int position) {
        holder.bind(modelList.get(position));
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }
}

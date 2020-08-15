package com.srit.market.home.ui.home.item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.srit.market.R;
import com.srit.market.databinding.ItemCategoryItemBinding;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private List<ItemModel> modelList;
    private Context context;

    ItemListener lestiner;

    public interface ItemListener {
        public void onItemClick(ItemModel itemModel);
    }

    public void setListener(ItemListener listener) {
        this.lestiner = listener;
    }

    public ItemAdapter(Context context, List<ItemModel> list) {
        this.context = context;
        this.modelList = list;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ItemCategoryItemBinding binding;

        ViewHolder(@NonNull ItemCategoryItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(final ItemModel dayModel) {
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
    public ItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemCategoryItemBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                        R.layout.item_category_item, viewGroup, false);
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

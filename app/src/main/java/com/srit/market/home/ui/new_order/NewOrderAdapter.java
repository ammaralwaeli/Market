package com.srit.market.home.ui.new_order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.srit.market.R;
import com.srit.market.databinding.ItemCategoryItemBinding;
import com.srit.market.databinding.ItemNewOrderBinding;
import com.srit.market.db.OrderItem;
import com.srit.market.helpers.SharedPrefHelper;
import com.srit.market.home.ui.home.item.ItemModel;

import java.util.List;

public class NewOrderAdapter extends RecyclerView.Adapter<NewOrderAdapter.ViewHolder> {

    private List<OrderItem> modelList;
    private Context context;


    public NewOrderAdapter(Context context, List<OrderItem> list) {
        this.context = context;
        this.modelList = list;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ItemNewOrderBinding binding;

        ViewHolder(@NonNull ItemNewOrderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(final OrderItem dayModel) {
            binding.setItem(dayModel);
            binding.hasPendingBindings();
        }
    }


    @NonNull
    @Override
    public NewOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemNewOrderBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                        R.layout.item_new_order, viewGroup, false);
        if (SharedPrefHelper.getInstance().getLanguage()){
            binding.units.setText(R.string.ar_units);
            binding.price.setText(R.string.ar_price);
        }else{
            binding.units.setText(R.string.en_units);
            binding.price.setText(R.string.en_price);
        }
        return new NewOrderAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NewOrderAdapter.ViewHolder holder, final int position) {
        holder.bind(modelList.get(position));
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }
}

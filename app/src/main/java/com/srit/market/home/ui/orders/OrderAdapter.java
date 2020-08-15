package com.srit.market.home.ui.orders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.srit.market.R;
import com.srit.market.databinding.ItemOrderBinding;
import com.srit.market.home.ui.home.category.CategoryAdapter;
import com.srit.market.home.ui.home.category.CategoryModel;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private List<OrderModel> modelList;
    private Context context;


    ItemListener lestiner;

    public interface ItemListener {
        public void onItemClick(int orderId);
    }

    public void setListener(ItemListener listener) {
        this.lestiner = listener;
    }


    public OrderAdapter(Context context, List<OrderModel> list) {
        this.context = context;
        this.modelList = list;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ItemOrderBinding binding;

        ViewHolder(@NonNull ItemOrderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(final OrderModel dayModel) {
            binding.setItem(dayModel);
            binding.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lestiner.onItemClick(dayModel.getOrderID());
                }
            });
            binding.hasPendingBindings();
        }
    }


    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemOrderBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                        R.layout.item_order, viewGroup, false);
        return new OrderAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, final int position) {
        holder.bind(modelList.get(position));
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }
}

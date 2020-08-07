package com.example.ApkPKL;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OrderRecyclerAdapter extends RecyclerView.Adapter<OrderRecyclerAdapter.OrderViewHolder> {

    private List<User> listOrder;

    public OrderRecyclerAdapter(List<User> listOrder) {
        this.listOrder = listOrder;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_order_recycler, parent, false);

        return new OrderViewHolder(itemView);
        }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {
        holder.textViewTanggal.setText(listOrder.get(position).getTanggal());
        holder.textViewNama.setText(listOrder.get(position).getNama());
        holder.textViewKontak.setText(listOrder.get(position).getKontak());
        holder.textViewAlamat.setText(listOrder.get(position).getAlamat());
    }

    @Override
    public int getItemCount() {
        Log.v(OrderRecyclerAdapter.class.getSimpleName(),""+listOrder.size());
        return listOrder.size();
    }


    /**
     * ViewHolder class
     */
    public class OrderViewHolder extends RecyclerView.ViewHolder {

        public AppCompatTextView textViewTanggal;
        public AppCompatTextView textViewNama;
        public AppCompatTextView textViewKontak;
        public AppCompatTextView textViewAlamat;

        public OrderViewHolder(View view) {
            super(view);
            textViewTanggal = (AppCompatTextView) view.findViewById(R.id.textViewTanggal);
            textViewNama = (AppCompatTextView) view.findViewById(R.id.textViewNama);
            textViewKontak = (AppCompatTextView) view.findViewById(R.id.textViewKontak);
            textViewAlamat = (AppCompatTextView) view.findViewById(R.id.textViewAlamat);
        }
    }
}

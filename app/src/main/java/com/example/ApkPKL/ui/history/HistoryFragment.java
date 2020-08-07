package com.example.ApkPKL.ui.history;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ApkPKL.DatabaseHelper;
import com.example.ApkPKL.OrderRecyclerAdapter;
import com.example.ApkPKL.R;
import com.example.ApkPKL.User;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {

    private RecyclerView recyclerViewOrder;
    private List<User> listOrder;
    private OrderRecyclerAdapter orderRecyclerAdapter;
    private DatabaseHelper myDb;
    private HistoryViewModel historyViewModel;
    private View root;
    private String email;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        historyViewModel =
                ViewModelProviders.of(this).get(HistoryViewModel.class);
        root = inflater.inflate(R.layout.fragment_history, container, false);

        initViews();
        initObjects();

        return root;
    }

    private void initViews() {
        recyclerViewOrder = root.findViewById(R.id.recyclerViewOrder);
        myDb = new DatabaseHelper(getActivity());
    }

    private void initObjects() {

        listOrder = new ArrayList<>();
        orderRecyclerAdapter = new OrderRecyclerAdapter(listOrder);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerViewOrder.setLayoutManager(mLayoutManager);
        recyclerViewOrder.setItemAnimator(new DefaultItemAnimator());
        recyclerViewOrder.setHasFixedSize(true);
        recyclerViewOrder.setAdapter(orderRecyclerAdapter);

        String emailFromIntent = getActivity().getIntent().getStringExtra("USERNAME");
        email = emailFromIntent;
        getDataFromSQLite();

        System.out.println("Tanggal : " + myDb.getTanggal(emailFromIntent));
        System.out.println("Nama : " + myDb.getNama(emailFromIntent));
        System.out.println("Asal : " + myDb.getKontak(emailFromIntent));
        System.out.println("Tujuan : " + myDb.getAlamat(emailFromIntent));
    }

    private void getDataFromSQLite() {
        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                listOrder.clear();
                listOrder.addAll(myDb.getAllRiwayat(email));

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                orderRecyclerAdapter.notifyDataSetChanged();
            }
        }.execute();
    }
}
package com.example.client_in_time.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.client_in_time.R;

public class OrdersFragment extends Fragment {


    public OrdersFragment(){

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Toast.makeText(getContext(), "from orders", Toast.LENGTH_SHORT).show();
        return inflater.inflate(R.layout.fragment_orders, container, false);
    }
}

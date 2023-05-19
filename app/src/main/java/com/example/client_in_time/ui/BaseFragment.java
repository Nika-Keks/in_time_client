package com.example.client_in_time.ui;

import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

public class BaseFragment extends Fragment {

    protected void goneAll(ViewBinding binding){
        ViewGroup vg = (ViewGroup) binding.getRoot();
        for (int i = 0; i < vg.getChildCount(); i++)
            vg.getChildAt(i).setVisibility(View.GONE);
    }
}

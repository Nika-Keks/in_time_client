package com.example.client_in_time.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.client_in_time.MainActivity;
import com.example.client_in_time.databinding.FragmentSigninBinding;
import com.example.client_in_time.models.SigninViewModel;


public class SigninFragment extends BaseFragment{
    FragmentSigninBinding binding;
    SigninViewModel signinViewModel;

    SigninViewModel.SigninListener listener = rc -> {
        switch (rc){
            case SUCCESS:
                Intent sendIntent = new Intent(getContext(), MainActivity.class);
                startActivity(sendIntent);
                break;
            case ERROR:
                Toast.makeText(getContext(), "User exists", Toast.LENGTH_LONG).show();
                break;
            default:
                Toast.makeText(getContext(), "Indalid name or email", Toast.LENGTH_LONG).show();
        }
    };
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSigninBinding.inflate(inflater, container, false);
        signinViewModel = new ViewModelProvider(this).get(SigninViewModel.class);

        binding.btnSignIn.setOnClickListener(view -> {
            signinViewModel.signin(
                    binding.editName.getText().toString(),
                    binding.editLogin.getText().toString(),
                    binding.editPassword.getText().toString(),
                    listener);
        });

        return binding.getRoot();
    }
}

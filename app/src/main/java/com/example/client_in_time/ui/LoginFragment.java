package com.example.client_in_time.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.client_in_time.MainActivity;
import com.example.client_in_time.R;
import com.example.client_in_time.databinding.FragmentLoginBinding;
import com.example.client_in_time.models.LoginViewModel;

public class LoginFragment extends Fragment {

    FragmentLoginBinding binding;
    LoginViewModel loginViewModel;

    final LoginViewModel.LoginListener listener = rc -> {
        switch (rc) {
            case SUCCESS:
                goToMain();
                break;
            case ERROR:
            case INVALID:
                setAllButtons(true);
                if (binding == null)
                    break;
                String msg;
                if (rc == LoginViewModel.LoginRC.ERROR)
                    msg = getResources().getString(R.string.error_login);
                else
                    msg = getResources().getString(R.string.invalid_login);
                Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
                break;
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        binding = FragmentLoginBinding.inflate(getLayoutInflater());

        binding.btnLogin.setOnClickListener(view -> {
            setAllButtons(false);
            loginViewModel.login(
                    binding.editLogin.getText().toString(),
                    binding.editPassword.getText().toString(),
                    listener
            );
        });

        Toast.makeText(getContext(), "login", Toast.LENGTH_LONG).show();

        return binding.getRoot();
    }

    private void setAllButtons(boolean activated){
        if (binding == null)
            return;
        binding.btnLogin.setActivated(activated);
        binding.btnSignIn.setActivated(activated);
    }


    protected void goToMain() {
        Intent sendIntent = new Intent(getContext(), MainActivity.class);
        startActivity(sendIntent);
    }
}

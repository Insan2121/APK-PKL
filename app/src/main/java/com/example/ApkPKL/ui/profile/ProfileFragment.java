package com.example.ApkPKL.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.ApkPKL.DatabaseHelper;
import com.example.ApkPKL.R;
import com.example.ApkPKL.login;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private TextView nama;
    private TextView email;
    private TextView jenis;
    private Button btnLogout;
    private ImageView profile;
    DatabaseHelper myDb;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        nama = root.findViewById(R.id.username_pro);
        email = root.findViewById(R.id.email_pro);
        jenis = root.findViewById(R.id.genderpro);
        btnLogout = root.findViewById(R.id.button_logout);
        profile =  root.findViewById(R.id.imageView);

        initObjects();

        String emailFromIntent = getActivity().getIntent().getStringExtra("USERNAME");
        nama.setText(emailFromIntent);

        email.setText(myDb.getEmail(emailFromIntent));

        jenis.setText(myDb.getJenisKelamin(emailFromIntent));

        if(myDb.getJenisKelamin(emailFromIntent).equals("Laki - Laki")){
            profile.setImageResource(R.drawable.male);
        }else{
            profile.setImageResource(R.drawable.female);
        }

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLogin();
            }
        });

        return root;
    }

    public void goToLogin(){
        Intent i = new Intent(getActivity(),login.class);
        getActivity().finish();
        startActivity(i);
    }

    private void initObjects() {
        myDb = new DatabaseHelper(getActivity());
    }
}
package java;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

import com.example.carwashdriver_android.R;
import com.example.carwashdriver_android.ui.login.LoginFragment;
import com.google.android.material.textfield.TextInputEditText;

public class RegisterFragment extends Fragment {

    private TextInputEditText etRegName, etRegEmail, etRegPassword;
    private Button btnRegister;
    private TextView tvLogin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        etRegName = view.findViewById(R.id.etRegName);
        etRegEmail = view.findViewById(R.id.etRegEmail);
        etRegPassword = view.findViewById(R.id.etRegPassword);
        btnRegister = view.findViewById(R.id.btnRegister);
        tvLogin = view.findViewById(R.id.tvLogin);

        btnRegister.setOnClickListener(v -> {
            String name = etRegName.getText().toString().trim();
            String email = etRegEmail.getText().toString().trim();
            String password = etRegPassword.getText().toString().trim();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(getActivity(), "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            } else {
                // Aquí iría la lógica de registro
                Toast.makeText(getActivity(), "Registro exitoso", Toast.LENGTH_SHORT).show();
                if (getActivity() != null) {
                    ((AuthActivity) getActivity()).replaceFragment(new LoginFragment());
                }
            }
        });

        tvLogin.setOnClickListener(v -> {
            if (getActivity() != null) {
                ((AuthActivity) getActivity()).replaceFragment(new LoginFragment());
            }
        });

        return view;
    }
}
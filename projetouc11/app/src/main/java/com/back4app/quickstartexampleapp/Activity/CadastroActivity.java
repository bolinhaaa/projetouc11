package com.back4app.quickstartexampleapp.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.back4app.quickstartexampleapp.R;
import com.back4app.quickstartexampleapp.Util.ParseErros;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class CadastroActivity extends AppCompatActivity {

    private EditText textoUsuario, textoEmail, textoSenha;
    private Button btnCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        textoUsuario = (EditText) findViewById(R.id.txtCadUsuario);
        textoEmail = (EditText) findViewById(R.id.txtCadEmail);
        textoSenha = (EditText) findViewById(R.id.txtCadSenha);

        btnCadastrar = (Button) findViewById(R.id.btnCadastrar);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastrarUsuario();
            }
        });
    }

    private void cadastrarUsuario() {
        ParseUser usuario = new ParseUser();
        usuario.setUsername(textoUsuario.getText().toString());
        usuario.setEmail(textoEmail.getText().toString());
        usuario.setPassword(textoSenha.getText().toString());

        usuario.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null ) {
                    Toast.makeText(CadastroActivity.this, "Cadastro Ok!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(CadastroActivity.this, LoginActivity.class));
                }
                else {
                    ParseErros parseErros = new ParseErros();

                    // string para receber o erro
                    String erro = parseErros.getErro(e.getCode());
                    Toast.makeText(CadastroActivity.this, "Erro ao Cadastrar! Erro: " + erro , Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void abrirTelaLogin(View view) {
        Intent intent = new Intent(CadastroActivity.this,LoginActivity.class);
        startActivity(intent);
    }
}


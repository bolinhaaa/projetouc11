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
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText textoLogin, textoSenha;
    private Button  btnLogar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textoLogin = (EditText) findViewById(R.id.txtUser);
        textoSenha = (EditText) findViewById(R.id.txtPasswd);
        btnLogar = (Button) findViewById(R.id.btnLogin);

        //ParseUser.logOut();

        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            // recuperar dados do usuario
                String usuario = textoLogin.getText().toString();
                String senha = textoSenha.getText().toString();

                verificarLogin(usuario, senha);
            }
        });

        // verificar se o usuario já está logado
        verificarUsuarioLogado();
    }

    private void verificarLogin(String user, String passwd) {
        ParseUser.logInInBackground(user, passwd, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e == null ) {// ok
                    Toast.makeText(LoginActivity.this, "Login Ok!", Toast.LENGTH_SHORT).show();
                    abrirTelaPrincipal();
                }
                else {
                    ParseErros parseErros = new ParseErros();
                    // string recebe o codigo do erro
                    String erro = parseErros.getErro(e.getCode());
                    Toast.makeText(LoginActivity.this, "Erro ao Fazer Login! Erro: " + erro, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void verificarUsuarioLogado() {
        if (ParseUser.getCurrentUser() != null ) {
            // abrir a tela principal
            abrirTelaPrincipal();
        }
    }

    public void abrirTelaPrincipal() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void abrirCadastroUsuario(View view) {
        Intent intent  = new Intent(LoginActivity.this, CadastroActivity.class);
        startActivity(intent);
    }
}

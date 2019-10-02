package com.back4app.quickstartexampleapp.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.back4app.quickstartexampleapp.Adapter.HomeAdapter;
import com.back4app.quickstartexampleapp.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class FeedUsuariosActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView listView;
    private String userName;
    private ArrayAdapter<ParseObject> adapter;
    private ArrayList<ParseObject> postagens;

    // referencia com o textoPreto
    @SuppressLint("ResourceAsColor")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_usuarios);

        toolbar = (Toolbar)findViewById(R.id.toolbar_feed_usuario);

        // recuperar dados enviados pela intent
        Intent intent = getIntent();
        userName = intent.getStringExtra("username");

        // configurar a Toolbar
        toolbar = (Toolbar)findViewById(R.id.toolbar_feed_usuario);
        toolbar.setTitle(userName);
        toolbar.setTitleTextColor(R.color.textoPreto);
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left);
        setSupportActionBar(toolbar);

        // configurar a ListViw e adapter
        postagens = new ArrayList<>();
        listView = (ListView)findViewById(R.id.list_feed_usuarios);

        //podemos utilizar o mesmo adapter do home
        adapter = new HomeAdapter(getApplicationContext(),postagens);
        listView.setAdapter(adapter);

        // recuperar postagens
        getPostagens();
    }

    private void getPostagens() {
        // recuperar imagem postadas
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Imagem");
        query.whereEqualTo("username", userName);
        query.orderByAscending("createdAt");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null ) {
                    if (objects.size() > 0 ) {
                        postagens.clear();
                        for (ParseObject parseObject:objects) {
                            postagens.add(parseObject);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
                else {
                    Toast.makeText(FeedUsuariosActivity.this, "Erro ao recuperar o Feed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

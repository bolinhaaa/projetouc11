package com.back4app.quickstartexampleapp.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
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

public class FeedUsuariosActivity_ extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView listView;
    private String userName;
    private ArrayAdapter<ParseObject> adapter;
    private ArrayList<ParseObject> postagens;

    @SuppressLint("ResourceAsColor")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_usuarios_);

        toolbar = (Toolbar)findViewById(R.id.toolbar_feed_usuarios);

        // recuperar dados eviados pela intent
        Intent intent = getIntent();
        userName = intent.getStringExtra("username");

        // configurar toolbar
        toolbar = (Toolbar)findViewById(R.id.toolbar_feed_usuarios);
        toolbar.setTitle(userName);
        toolbar.setTitleTextColor(R.color.textoPreto);
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left);
        setSupportActionBar(toolbar);

        // configurar listView e Adapter
        postagens = new ArrayList<>();
        listView=(ListView)findViewById(R.id.list_feed_usuarios);
        adapter = new HomeAdapter(getApplicationContext(),postagens);
        listView.setAdapter(adapter);

        // recuperar postagens
        getPostagens();
    }

    private void getPostagens() {
        // recuperar imagens das postagens
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Imagem");
        query.whereEqualTo("username",userName);
        query.orderByAscending("createdAt");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    if (objects.size() > 0 ) {
                        postagens.clear();
                        for (ParseObject parseObject:objects) {
                            postagens.add(parseObject);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
                else {
                    Toast.makeText(FeedUsuariosActivity_.this, "Erro ao Recuperar o Feed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}

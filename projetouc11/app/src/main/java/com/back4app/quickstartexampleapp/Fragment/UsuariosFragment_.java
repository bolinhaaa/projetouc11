package com.back4app.quickstartexampleapp.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.back4app.quickstartexampleapp.Activity.FeedUsuariosActivity_;
import com.back4app.quickstartexampleapp.Adapter.UsuariosAdapter;
import com.back4app.quickstartexampleapp.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class UsuariosFragment_ extends Fragment {
    private ListView listView;
    private ArrayAdapter<ParseUser> adapter;
    private ArrayList<ParseUser> usuarios;
    private ParseQuery<ParseUser> query;


    public UsuariosFragment_() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_usuarios_fragment_, container, false);

        // mostrar a listView e adapter
        usuarios = new ArrayList<>();

        listView = (ListView)view.findViewById(R.id.list_usuarios);
        adapter = new UsuariosAdapter(getActivity(),usuarios);
        listView.setAdapter(adapter);

        // recuperar usuarios
        getUsuarios();

        // criar evento de click na lista
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // recuperar dados a serem passados
                ParseUser parseUser = usuarios.get(position);

                /// enviar dados para o feed usuario
                Intent intent = new Intent(getActivity(), FeedUsuariosActivity_.class);
                intent.putExtra("username", parseUser.getUsername());
                startActivity(intent);
            }
        });

        return view;
    }

    private void getUsuarios() {
        // recuperar lista de usuarios do parse
        query = ParseUser.getQuery();
        query.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());
        query.orderByAscending("username");

        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null ) {
                    if (objects.size() > 0 ) {
                        usuarios.clear();
                        for (ParseUser parseUser:objects) {
                            usuarios.add(parseUser);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
                else {
                    e.printStackTrace();
                    Log.i("ErroUsuario","Erro: " + e.getMessage());
                }
            }
        });
    }

}

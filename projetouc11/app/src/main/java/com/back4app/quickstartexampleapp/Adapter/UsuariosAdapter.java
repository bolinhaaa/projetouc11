package com.back4app.quickstartexampleapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.back4app.quickstartexampleapp.R;
import com.parse.ParseUser;

import java.util.ArrayList;

public class UsuariosAdapter extends ArrayAdapter<ParseUser> {

    private Context context;
    private ArrayList<ParseUser> usuarios;

    public UsuariosAdapter(@NonNull Context c,@NonNull ArrayList<ParseUser> objects) {
        super(c,0, objects);

        // referencia do c
        this.context = c;

        // referencia usuarios
        this.usuarios = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null ) {
            // inicializar objeto
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            // montar a view a partir do xml
            view = inflater.inflate(R.layout.lista_usuarios,parent,false);
        }

        // recuperar elementos para exibir
        TextView username = (TextView)view.findViewById(R.id.text_username);

        // configurar textview para exibir o usuarios
        ParseUser parseUser = usuarios.get(position);
        username.setText(parseUser.getUsername());

        return  view;
    }
}

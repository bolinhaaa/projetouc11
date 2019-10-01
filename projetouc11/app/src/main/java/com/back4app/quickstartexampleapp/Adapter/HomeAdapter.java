package com.back4app.quickstartexampleapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.back4app.quickstartexampleapp.Fragment.HomeFragment;
import com.back4app.quickstartexampleapp.R;
import com.parse.ParseObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HomeAdapter extends ArrayAdapter<ParseObject> {
    // variavel
    private Context context;
    private ArrayList<ParseObject> postagens;

    public HomeAdapter(@NonNull Context c, @NonNull ArrayList<ParseObject> objects) {
        super(c, 0, objects);
        this.context = c;
        this.postagens = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       View view = convertView;

       // vai verificar se existe o objeto view criado, a view é armazenada no cache do android
        if (view == null ) {
            // inicializar objeto para montagem do layout
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(
                    context.LAYOUT_INFLATER_SERVICE
            );

            // montar a view a partir do xml
            view = inflater.inflate(R.layout.lista_postagem, parent, false);
        }

        //verificar se há postagem
        if (postagens.size() > 0 ) {
            // recuperar componentes da tela
            ImageView imagemPostada = (ImageView) view.findViewById(R.id.image_lista_ponstagem);
            ParseObject parseObject = postagens.get(position);

            //Recusro do Picasso
            Picasso.with(context)
                    .load(parseObject.getParseFile("imagem").getUrl())
                    .fit()
                    .into(imagemPostada);
        }
        return  view;
    }
}

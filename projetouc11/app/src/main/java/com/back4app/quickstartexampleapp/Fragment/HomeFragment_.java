package com.back4app.quickstartexampleapp.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.back4app.quickstartexampleapp.Adapter.HomeAdapter;
import com.back4app.quickstartexampleapp.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment_ extends Fragment {
    // recuperar o id da listView
    private ListView listView;
    private ArrayList<ParseObject> postagens;
    private ArrayAdapter<ParseObject> adapter;

    private ParseQuery<ParseObject> query;


    public HomeFragment_() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_fragment_, container, false);

        // montar listview e o adapter
        postagens = new ArrayList<>();
        listView = (ListView)view.findViewById(R.id.list_postagens_home);

        // criar adapter e associar a uma list view
        adapter = new HomeAdapter(getActivity(),postagens);
        listView.setAdapter(adapter);

        // recuperar as postagens
        getPostagens();

        return view;
    }

    public void atualizarPostagens() {
        getPostagens();
    }

    private void getPostagens() {
        // recuperar imagem postada
        query = ParseQuery.getQuery("Imagem");

        // recuperar as imagens referente ao user em questão
        query.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
        query.orderByDescending("createdAt");

        // realiza busca
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null ) {
                    if (objects.size() > 0 ) {
                        postagens.clear();
                        for (ParseObject parseObject: objects) {
                            postagens.add(parseObject);
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
                else {
                    e.printStackTrace();
                }
            }
        });
    }

}

package com.back4app.quickstartexampleapp.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.ViewGroup;

import com.back4app.quickstartexampleapp.R;

import java.util.HashMap;

public class TabsAdapter extends FragmentStatePagerAdapter {
    private Context context;

    private int[] icones = new int[]{R.drawable.ic_action_home_24dp,
    R.drawable.ic_people_black_24dp};

    private int tamanhoIcone;

    private HashMap<Integer, Fragment> fragmentosUtilizados;

    public TabsAdapter(FragmentManager fm, Context c) {
        super(fm);
        this.context = c ;

        //Metrics
        double escala=this.context.getResources().getDisplayMetrics().density;
        tamanhoIcone=(int)(36*escala);

        //inicializar
        this.fragmentosUtilizados = new HashMap<>();
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        // position = 0 Home ou =1 Usuarios
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                fragmentosUtilizados.put(position, fragment);
                break;
            case 1:
                fragment = new UsuariosFragment();
                break;
        }
        return fragment;
    }

    public Fragment getFragment(Integer indice) {
        return fragmentosUtilizados.get(indice);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        fragmentosUtilizados.remove(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        //recuperar o icone de acordo com o position
        Drawable drawable=ContextCompat.getDrawable(this.context,icones[position]);

        // passar o tamanho para o drawble
        drawable.setBounds(0,0,tamanhoIcone,tamanhoIcone);

        //permissão para colocar uma imagem dentro de um texto
        ImageSpan imageSpan = new ImageSpan(drawable);

        // retornar um charsequence
        SpannableString spannableString = new SpannableString(" ");
        spannableString.setSpan(imageSpan,0,spannableString.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }

    @Override
    public int getCount() {
        return icones.length;
    }
}

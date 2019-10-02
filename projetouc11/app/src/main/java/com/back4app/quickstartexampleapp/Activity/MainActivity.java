package com.back4app.quickstartexampleapp.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;


import com.back4app.quickstartexampleapp.Adapter.TabsAdapter;
import com.back4app.quickstartexampleapp.Fragment.HomeFragment;
import com.back4app.quickstartexampleapp.R;
import com.back4app.quickstartexampleapp.Util.SlidingTabLayout;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbarPrincipal;
    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Save the current Installation to Back4App
        //ParseInstallation.getCurrentInstallation().saveInBackground();

        toolbarPrincipal = (Toolbar) findViewById(R.id.toolbar_principal);
        toolbarPrincipal.setTitle("Let's Run");
        setSupportActionBar(toolbarPrincipal);

        //configurar abas
        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tab_main);
        viewPager = (ViewPager) findViewById(R.id.view_pager_main);

        // configurar adapter para devolver fragmentos da tela do usuario e tela de imagens
        TabsAdapter tabsAdapter = new TabsAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(tabsAdapter);
        slidingTabLayout.setCustomTabView(R.layout.tab_view,R.id.text_item_tab);
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setViewPager(viewPager);
    }

    // inserir itens no menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sair:
                deslogarUsuario();
                return true;

            case R.id.action_settings:
                return true;

            case R.id.action_compartilhar:
                compartilharFoto();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // compartilhar fotos
    private void compartilharFoto() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
    }

    // deslogar usuario
    private void deslogarUsuario() {
        ParseUser.logOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent    );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //vamos tratar o retorno da imagem escolhida

        // 1 - testar processo de retorno de dados
        if (requestCode == 1 && resultCode == RESULT_OK && data != null ) {
            // 2 - recuperar o local do recurso
            Uri localImagemSelecionada = data.getData();

            // 3 - Recuperar a imagem do local que foi selecionada
            try {
                Bitmap imagem = MediaStore.Images.Media.getBitmap(
                        getContentResolver(),localImagemSelecionada
                );

                // 4 - comprimir no formato png
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                imagem.compress(Bitmap.CompressFormat.PNG, 75, stream);

                // 5 - Criar um Array de bytes da imagem
                byte[] byteArray = stream.toByteArray();

                // 6 - criar um arquivo com formato proprio do Parse
                ParseFile arquivoParse = new ParseFile("imagem.png",byteArray);

                // 6.1 - add uma classe simpledataFormat
                SimpleDateFormat dateFormat = new SimpleDateFormat("ddmmaaahhmmss");
                String nomeImagem = dateFormat.format(new Date());
                ParseFile arquivoParse2 = new ParseFile(nomeImagem + " -imagem.png ", byteArray);

                // 7 - Montar o objeto para salvar no Parse
                ParseObject parseObject = new ParseObject("Imagem");
                parseObject.put("username",ParseUser.getCurrentUser().getUsername());
                parseObject.put("imagem",arquivoParse2);

                // 8 - salvar dados
                parseObject.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null ) {
                            Toast.makeText(MainActivity.this, "Post Ok!", Toast.LENGTH_SHORT).show();

                            TabsAdapter adapterNovo = (TabsAdapter) viewPager.getAdapter();
                            HomeFragment homeFragmentNovo = (HomeFragment) adapterNovo.getFragment(0);
                            homeFragmentNovo.atualizarPostagens();
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Error Post!", Toast.LENGTH_SHORT).show();
                          }
                    }
                });
            }
            catch (Exception e) {
                Log.i("ErroRecuperaImg","Erro: - " + e.getMessage());
            }

        }
    }
}

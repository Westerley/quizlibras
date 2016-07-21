package com.example.westerley.quizlibras.activities;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.westerley.quizlibras.R;
import com.example.westerley.quizlibras.classes.Categoria;
import com.example.westerley.quizlibras.fragments.CategoriaFragment;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.interfaces.OnCheckedChangeListener;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.SwitchDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private Drawer navegationDrawer;
    private AccountHeader headerNavigation;
    private boolean habilitaSom;

    private OnCheckedChangeListener mOnCheckedChangeListener = new OnCheckedChangeListener(){
        @Override
        public void onCheckedChanged(IDrawerItem drawerItem, CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                habilitaSom = true;
                Toast.makeText(MainActivity.this, "Som Habilitado", Toast.LENGTH_SHORT).show();
            } else {
                habilitaSom = false;
                Toast.makeText(MainActivity.this, "Som Desabilitado", Toast.LENGTH_SHORT).show();
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.tb_main);
        mToolbar.setTitle(R.string.app_name);
        setSupportActionBar(mToolbar);

        //Habilita o som dos botões
        habilitaSom = true;

        CategoriaFragment frag = (CategoriaFragment) getSupportFragmentManager().findFragmentByTag("mainFrag");
        if (frag == null) {
            frag = new CategoriaFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.rl_fragment_container, frag, "mainFrag");
            ft.commit();
        }

        //Navigation Drawer
        headerNavigation = new AccountHeaderBuilder()
                .withActivity(this)
                .withCompactStyle(false)
                .withSavedInstance(savedInstanceState)
                .withThreeSmallProfileImages(false)
                .withHeaderBackground(R.drawable.libras)
                .build();

        navegationDrawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(mToolbar)
                .withDisplayBelowStatusBar(true)
                .withActionBarDrawerToggleAnimated(true)
                .withDrawerGravity(Gravity.LEFT)
                .withSavedInstance(savedInstanceState)
                .withSelectedItem(-1)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                        Bundle params = new Bundle();

                        if (drawerItem.isSelected() && position == 1) {
                            Intent intent = new Intent(MainActivity.this, InfoActivity.class);
                            startActivity(intent);
                        } else if (drawerItem.isSelected() && position == 2) {
                            Intent intent = new Intent(MainActivity.this, ExtrasActivity.class);
                            startActivity(intent);
                        } else if (drawerItem.isSelected() && position == 3) {
                            Intent intent = new Intent(MainActivity.this, DesenvolvedorActivity.class);
                            startActivity(intent);
                        }
                        return false;
                    }
                })
                .withAccountHeader(headerNavigation)
                .build();
        navegationDrawer.addItem(new PrimaryDrawerItem().withName("Sobre o Jogo").withIcon(getResources().getDrawable(R.drawable.ic_help)));
        navegationDrawer.addItem(new PrimaryDrawerItem().withName("Extras").withIcon(getResources().getDrawable(R.drawable.ic_grade)));
        navegationDrawer.addItem(new PrimaryDrawerItem().withName("Desenvolvedor").withIcon(getResources().getDrawable(R.drawable.ic_face)));
        navegationDrawer.addItem(new SectionDrawerItem().withName("Configurações"));
        navegationDrawer.addItem(new SwitchDrawerItem().withName("Som").withChecked(true).withOnCheckedChangeListener(mOnCheckedChangeListener).withIcon(getResources().getDrawable(R.drawable.ic_settings)));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public List<Categoria> getSetCategoriaList(int qtd) {
        String[] nome = new String[] {"Animais", "Frutas"};
        String[] info = new String[] {"Domésticos, Répteis, Anfíbios ... ", "Tropicais, Subtropicais, Mediterrânio ... "};
        int[] imagens = new int[] {R.drawable.categoria_animais, R.drawable.categoria_frutas};
        List<Categoria> listAux = new ArrayList<>();

        for (int i = 0; i < qtd; i++) {
            Categoria categoria = new Categoria( nome[i % nome.length], info[i % info.length], imagens[i % nome.length] );
            listAux.add(categoria);
        }

        return listAux;
    }

    public boolean getHabilitaSom() {
        return this.habilitaSom;
    }

}

package br.usjt.ucsist.cadaluno.ui;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.w3c.dom.Text;

import br.usjt.ucsist.cadaluno.R;
import br.usjt.ucsist.cadaluno.model.Usuario;
import br.usjt.ucsist.cadaluno.model.UsuarioViewModel;

public class MainActivity extends AppCompatActivity {

    private UsuarioViewModel usuarioViewModel;
    private Usuario usuarioCorrente;
    private TextView textViewNomeCard;
    private TextView textViewCPFCard;
    private TextView textViewEmailCard;
    private CardView cardView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        replaceFragment(R.id.frameLayout,
                HomeFragment.newInstance("",""),
                "HOMEFRAGMENT",
                "HOME");


        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.home:
                        replaceFragment(R.id.frameLayout,
                                HomeFragment.newInstance("",""),
                                "HOMEFRAGMENT",
                                "HOME");
                        return true;

                    case R.id.contato:
                        replaceFragment(R.id.frameLayout,
                                ContatoFragment.newInstance("",""),
                                "CONTATOFRAGMENT",
                                "CONTATO");
                        return true;

                    case R.id.perfil:
                        replaceFragment(R.id.frameLayout,
                                PerfilFragment.newInstance("",""),
                                "PERFILFRAGMENT",
                                "PERFIL");
                        return true;

                    case R.id.config:
                        replaceFragment(R.id.frameLayout,
                                ConfigFragment.newInstance("",""),
                                "CONFIGFRAGMENT",
                                "CONFIG");
                        return true;


                }


                return false;
            }
        });

        textViewNomeCard = findViewById(R.id.textViewNomeCard);
        textViewCPFCard = findViewById(R.id.textViewCPFCard);
        textViewEmailCard = findViewById(R.id.textViewEmailCard);
        cardView = findViewById(R.id.cardView);

        usuarioCorrente = new Usuario();

        usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);

        usuarioViewModel.getUsuario().observe(this, new Observer<Usuario>() {
            @Override
            public void onChanged(@Nullable final Usuario usuario) {
                updateView(usuario);
            }
        });

    }

    private void updateView(Usuario usuario){
        if(usuario != null && usuario.getId() > 0){
            usuarioCorrente = usuario;
            textViewNomeCard.setText(usuario.getNome());
            textViewCPFCard.setText(usuario.getCpf());
            textViewEmailCard.setText(usuario.getEmail());
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {

            case R.id.cardOpenClose:
                if (cardView.getVisibility()==View.GONE){
                    cardView.setVisibility(View.VISIBLE);
                }else {
                    cardView.setVisibility(View.GONE);
                }
                return (true);

            case R.id.editar:
                Intent intent = new Intent(this, CadastroActivity.class);
                startActivity(intent);
                return(true);
            case R.id.sair:
                finish();
                return(true);
        }
        return(super.onOptionsItemSelected(item));
    }
    protected void replaceFragment(@IdRes int containerViewId,
                                   @NonNull Fragment fragment,
                                   @NonNull String fragmentTag,
                                   @Nullable String backStackStateName) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(containerViewId, fragment, fragmentTag)
                .addToBackStack(backStackStateName)
                .commit();
    }
}

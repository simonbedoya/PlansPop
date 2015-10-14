package simon.unicauca.edu.co.planpop;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import simon.unicauca.edu.co.planpop.Fragments.Register_cypFragment;

public class RegisterActivity extends AppCompatActivity implements DialogInterface.OnClickListener {

    Register_cypFragment reg;
    AlertDialog close;
    public static String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        reg = new Register_cypFragment();

        putFragment(R.id.container,reg);

        Confirmation_close();

    }
    public void Confirmation_close(){
        close = new android.app.AlertDialog.Builder(this)
                .setTitle("Cancelar cuenta")
                .setMessage("¿Seguro que quieres cancelar la creación de la cuenta? Se descartará toda informacion.")
                .setPositiveButton("Sí", this)
                .setNegativeButton("No", this)
                .create();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            close.show();
        }
        return super.onOptionsItemSelected(item);
    }
    public void putFragment(int idContainer, Fragment fragment){
        FragmentTransaction fT = getSupportFragmentManager()
                .beginTransaction();
        fT.replace(idContainer, fragment);
        fT.commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            close.show();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if(which == DialogInterface.BUTTON_POSITIVE){
            Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}

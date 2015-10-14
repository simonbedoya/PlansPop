package simon.unicauca.edu.co.planpop;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.Timer;

public class LoginActivity extends AppCompatActivity {

    TextView txt_register, txt_rpass;
    EditText user,pass;
    Button login;
    String muser, mpass;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Iniciando sesión.....");

        user = (EditText) findViewById(R.id.edt_user);
        pass = (EditText) findViewById(R.id.edt_pass);
        login = (Button) findViewById(R.id.btn_login);
        txt_register = (TextView) findViewById(R.id.txt_register);
        txt_rpass = (TextView) findViewById(R.id.txt_rpass);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(pass.getWindowToken(), 0);
                dialog.show();
                muser = user.getText().toString();
                mpass = pass.getText().toString();
                if (muser.matches("") || mpass.matches("")){
                    dialog.hide();
                    Toast toast = Toast.makeText(getApplicationContext(), "Informacion: Por favor llene los campos.", Toast.LENGTH_SHORT);
                    toast.show();
                }else{

                    ParseUser.logInInBackground(muser, mpass, new LogInCallback() {

                        @Override
                        public void done(ParseUser u, ParseException e) {


                            if (u != null) {

                                dialog.hide();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {

                                    boolean net = isOnline();
                                    if (net == true) {

                                        user.setText("");
                                        pass.setText("");
                                        Toast toast = Toast.makeText(getApplicationContext(), "Error: Usuario o Contraseña incorrectos.", Toast.LENGTH_SHORT);
                                        toast.show();
                                    }else{

                                        user.setText("");
                                        pass.setText("");
                                        Toast toast = Toast.makeText(getApplicationContext(), "Error: La conexión se está tardando, verifique su conexion a internet", Toast.LENGTH_SHORT);
                                        toast.show();
                                    }
                            }
                        }
                    });
            }}
        });

        txt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
        txt_rpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RemPassActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }

        return false;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

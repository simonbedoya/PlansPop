package simon.unicauca.edu.co.planpop;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class PerfilActivity extends AppCompatActivity {

    ImageView img_perfil;
    TextView txt_nombre;

    Button edit_img, edit_nombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        img_perfil = (ImageView) findViewById(R.id.img_perfil);
        txt_nombre = (TextView) findViewById(R.id.nombre_perfil);

        edit_img = (Button) findViewById(R.id.btn_cambiar_foto);
        edit_nombre = (Button) findViewById(R.id.btn_cambiar_nombre);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_perfil, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if(item.getItemId() == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}

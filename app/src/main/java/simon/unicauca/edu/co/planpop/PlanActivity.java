package simon.unicauca.edu.co.planpop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.CountCallback;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.List;

import simon.unicauca.edu.co.planpop.AppUtil.AppUtil;
import simon.unicauca.edu.co.planpop.Fragments.ListaFragment;
import simon.unicauca.edu.co.planpop.parse.PlanParse;
import simon.unicauca.edu.co.planpop.parse.UpdateParse;

public class PlanActivity extends AppCompatActivity implements View.OnClickListener {

    TextView nombre, descripcion,fecha, hora, plancreador, asistentes, estado;
    Button ver_mapa, ir;
    String id_plan = AppUtil.data.get(ListaFragment.POSITION).getId();

    MainActivity main = new MainActivity();


    ListaFragment plan = new ListaFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);



        nombre = (TextView) findViewById(R.id.plan_nombre);
        descripcion = (TextView) findViewById(R.id.plan_descripcion);
        fecha = (TextView) findViewById(R.id.plan_fecha);
        hora = (TextView) findViewById(R.id.plan_Hora);
        plancreador = (TextView) findViewById(R.id.plan_creador);
        asistentes = (TextView) findViewById(R.id.plan_numero_participantes);
        ver_mapa = (Button) findViewById(R.id.btn_ver_lugar);
        ir = (Button) findViewById(R.id.btn_asistir);
        estado = (TextView) findViewById(R.id.estado);
        Log.d("Posicion", String.valueOf(ListaFragment.POSITION));

        Load_data();
        Count_assitant();
        Ver_asist();
        main.getDialog().hide();
        ir.setOnClickListener(this);

    }
    public void Load_data(){
        if (ListaFragment.POSITION!=-1){
            nombre.setText(AppUtil.data.get(ListaFragment.POSITION).getNombre());
            descripcion.setText(AppUtil.data.get(ListaFragment.POSITION).getDescripcion());
            String[] separar;
            separar = AppUtil.data.get(ListaFragment.POSITION).getFecha().split(" ");
            fecha.setText(separar[0]);
            hora.setText(separar[1]);
            ParseObject user = AppUtil.data.get(ListaFragment.POSITION).getUser();
            try {
                plancreador.setText(user.fetchIfNeeded().getString("name"));

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public void Count_assitant(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Plan");
        query.getInBackground(id_plan, new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    ParseRelation relation = object.getRelation("Asistentes");
                    ParseQuery query = relation.getQuery();
                    query.countInBackground(new CountCallback() {
                        @Override
                        public void done(int count, ParseException e) {
                            if (e == null) {
                                Log.d("score", "Sean has played " + count + " games");
                                asistentes.setText(Integer.toString(count));

                            } else {

                            }

                        }
                    });

                }
            }
        });
    }

    public void Ver_asist(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Plan");
        query.getInBackground(id_plan, new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    ParseRelation relation = object.getRelation("Asistentes");
                    ParseQuery<ParseObject> query = relation.getQuery();
                    query.whereEqualTo("objectId", ParseUser.getCurrentUser().getObjectId());
                    query.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {
                            if (objects.size() == 1) {
                                Log.d("Asis", "SI");
                                
                                ir.setVisibility(View.VISIBLE);
                                ir.setText("NO ASISTIR");
                                ir.setBackgroundColor(Color.RED);
                                estado.setText("ASISTIRE");
                            } else {

                                ir.setVisibility(View.VISIBLE);
                                ir.setText("ASISTIR");
                                ir.setBackgroundColor(Color.GREEN);
                                Log.d("Asis", "NO");
                            }
                        }
                    });
                }
            }
        });
    }

                @Override
                public boolean onCreateOptionsMenu (Menu menu){
                    // Inflate the menu; this adds items to the action bar if it is present.
                    getMenuInflater().inflate(R.menu.menu_plan, menu);
                    return true;
                }

                @Override
                public boolean onOptionsItemSelected (MenuItem item){
                    // Handle action bar item clicks here. The action bar will
                    // automatically handle clicks on the Home/Up button, so long
                    // as you specify a parent activity in AndroidManifest.xml.
                    int id = item.getItemId();

                    //noinspection SimplifiableIfStatement
                    if (id == R.id.action_settings) {
                        return true;
                    }
                    if (id == android.R.id.home) {
                        ListaFragment list = new ListaFragment();
                        list.Reload();

                    }

                    return super.onOptionsItemSelected(item);
                }
                @Override
                public boolean onKeyDown ( int keyCode, KeyEvent event){
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        ListaFragment list = new ListaFragment();
                        list.Reload();
                    }
                    return super.onKeyDown(keyCode, event);
                }

                @Override
                public void onClick (View v){


                    final ParseUser user = ParseUser.getCurrentUser();
                    String id_plan = AppUtil.data.get(ListaFragment.POSITION).getId();
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("Plan");
                    query.getInBackground(id_plan, new GetCallback<ParseObject>() {
                        public void done(ParseObject object, ParseException e) {
                            if (e == null) {
                                // Now let's update it with some new data. In this case, only cheatMode and score
                                // will get sent to the Parse Cloud. playerName hasn't changed.
                                ParseRelation<ParseObject> relation = object.getRelation("Asistentes");

                                if (AppUtil.aux_asistant == 0) {
                                    relation.add(user);
                                    ir.setText("NO ASISTIR");
                                    estado.setText("ASISTIRE");
                                    ir.setBackgroundColor(Color.RED);
                                    AppUtil.aux_asistant = 1;

                                    Log.d("Asistir", "SI");
                                } else {
                                    relation.remove(user);
                                    ir.setText("ASISTIR");
                                    ir.setBackgroundColor(Color.GREEN);
                                    estado.setText("---");
                                    Log.d("Asistir", "NO");
                                    AppUtil.aux_asistant = 0;
                                }

                                object.saveInBackground();
                                Count_assitant();

                            }
                        }
                    });


// Retrieve the object by id

                }
            }

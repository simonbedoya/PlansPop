package simon.unicauca.edu.co.planpop;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.parse.ParseUser;

import simon.unicauca.edu.co.planpop.Fragments.MisPLanesFragment;
import simon.unicauca.edu.co.planpop.adapters.PagerAdapter;
import simon.unicauca.edu.co.planpop.Fragments.ListaFragment;
import simon.unicauca.edu.co.planpop.Fragments.MapsFragment;
import simon.unicauca.edu.co.planpop.Fragments.TitleFragment;
import simon.unicauca.edu.co.planpop.AppUtil.AppUtil;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements ListaFragment.OnItemSelectedList, MapsFragment.OnLugarSelected, SearchView.OnQueryTextListener, MenuItemCompat.OnActionExpandListener {

    ViewPager pager;
    List<TitleFragment> data;
    PagerAdapter adapter;
    public static ProgressDialog dialog;

    ListaFragment list = new ListaFragment();


    public static ProgressDialog getDialog() {
        return dialog;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Cargando informacion....");

        pager = (ViewPager) findViewById(R.id.pager);
        data = new ArrayList<>();

        MisPLanesFragment misPLanesFragment = new MisPLanesFragment();
        data.add(misPLanesFragment);

        ListaFragment list = new ListaFragment();
        data.add(list);

        MapsFragment mapa = new MapsFragment();
        data.add(mapa);

        adapter = new PagerAdapter(getSupportFragmentManager(), data);
        pager.setAdapter(adapter);



       // ParseQuery<ParseUser> parseQuery = ParseQuery.getQuery("User");
       // parseQuery.whereEqualTo("nombre","franklin");

    }

    @Override
    protected void onResume() {


        super.onResume();
    }

    /*@Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onRestart() {
        adapter.notifyDataSetChanged();
        super.onRestart();
    }*/

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        // LISTENER PARA EL EDIT TEXT
        searchView.setOnQueryTextListener(this);

        // LISTENER PARA LA APERTURA Y CIERRE DEL WIDGET
        MenuItemCompat.setOnActionExpandListener(searchItem, this);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.action_add:

                Intent intent = new Intent(this, AddActivity.class);
                startActivity(intent);

                break;

            case R.id.action_search:

                break;
            case R.id.action_logout:
                ParseUser.logOut();
                ParseUser currentUser = ParseUser.getCurrentUser(); // this will now be null
                Intent intent2 = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent2);
                finish();

                break;
            case R.id.home:
                Log.d("Presiono"," Presiones atras");
                list.Reload();
                break;
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
    public void onItemSelectedList(int position) {
        dialog.show();
        AppUtil.positionSelected = position;
        Intent intent = new Intent(this,PlanActivity.class);
        startActivity(intent);

    }

    // metodo todavia no implementado.
    @Override
    public void onLugarSelected(double latitud, double longitud) {
        Toast.makeText(this,latitud+" "+longitud,Toast.LENGTH_LONG);
    }


    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public boolean onQueryTextSubmit(String query) {
        View search = findViewById(R.id.action_search);
        InputMethodManager imm = (InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(search.getWindowToken(), 0);


        AppUtil.searching = query.toString();

        list.search();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {


        return true;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        list.Reload();
        return true;
    }
}
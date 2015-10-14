package simon.unicauca.edu.co.planpop.Fragments;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import simon.unicauca.edu.co.planpop.R;
import simon.unicauca.edu.co.planpop.models.Lugar;
import simon.unicauca.edu.co.planpop.parse.LugarParse;

import java.io.IOException;
import java.util.List;

/**
 * Created by Frank on 3/10/2015.
 */
public class MapsFragment extends TitleFragment implements View.OnClickListener, GoogleMap.OnMapLongClickListener, LugarParse.LugarParseInterface {


    public interface OnLugarSelected{
        void onLugarSelected(double latitud, double longitud);
    }

    public static int CAMBIAR_MARKER=-1;


    OnLugarSelected onLugarSelected;

    GoogleMap mMap;
    Context context;

    EditText edt_buscar;
    Button btn_buscar_mapa;

    private Marker marker = null;
    private MarkerOptions markerOptions = null;
    private LatLng latLng = null;
    String direccion;


    public MapsFragment() {
    }

    @Override
    public void onAttach(Context context) {
        this.context = context;
        onLugarSelected = (OnLugarSelected) context;
        super.onAttach(context);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_maps, container, false);

        edt_buscar = (EditText) v.findViewById(R.id.edit_buscar);
        btn_buscar_mapa = (Button) v.findViewById(R.id.btn_buscar_mapa);

        btn_buscar_mapa.setOnClickListener(this);

        setUpMapIfNeeded();
        LugarParse lugarParse = new LugarParse(this);
        lugarParse.getAllLugares();
        mMap.setOnMapLongClickListener(this);


        return v;
    }

    @Override
    public void resultListLugares(Boolean exito, List<Lugar> lugares) {

        if(exito == true) {

            MarkerOptions markerLugaresOption = new MarkerOptions();
            LatLng latitudLongitud;

            String nameLugar, direccionLugar;
            double latitud, longitud;


            for (int i = 0; i < lugares.size(); i++) {

                nameLugar = lugares.get(i).getNombre();
                direccionLugar = lugares.get(i).getDireccion();
                latitud = lugares.get(i).getUbicacion().getLatitude();
                longitud = lugares.get(i).getUbicacion().getLongitude();

                latitudLongitud = new LatLng(latitud, longitud);
                markerLugaresOption.position(latitudLongitud);
                markerLugaresOption.title(nameLugar + " " + direccionLugar);

                mMap.addMarker(markerLugaresOption);

            }
        }
        else {
            Toast.makeText(context, "ERROR CARGANDO LUGARES", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapa)).getMap();


            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(2.4448143 ,-76.6147395),13.0f));
        }
    }

    private void setUpMap() {
        //mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
        mMap.setMyLocationEnabled(true);

    }

    @Override
    public String getTitle() {
        return "Mapa";
    }

    @Override
    public void onClick(View v) {

        String localizacion = edt_buscar.getText().toString();

        List<Address> addressList = null;

        if(!localizacion.equals("")){
            Geocoder geocoder = new Geocoder(context);
            try {
                addressList = geocoder.getFromLocationName(localizacion, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Address address = addressList.get(0);
            LatLng bus_latLng = new LatLng(address.getLatitude(),address.getLongitude());
            mMap.addMarker(new MarkerOptions().position(bus_latLng).title(address.getLatitude()+"  "+address.getLongitude()));


            mMap.animateCamera(CameraUpdateFactory.newLatLng(bus_latLng));
        }
        else{
            Toast.makeText(context, "No se ingreso ninguna palabra clave", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onMapLongClick(LatLng latLng) { // marcar un lugar

        Geocoder geocoderDireccion = new Geocoder(context);
        List<Address> addresses = null;
        try {
            addresses = geocoderDireccion.getFromLocation(latLng.latitude,latLng.longitude,1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Address address = addresses.get(0);
        direccion = address.getAddressLine(0);

        markerOptions = new MarkerOptions().position(latLng).title(direccion);

        if(marker == null) {
            this.latLng = latLng;
            marker = mMap.addMarker(markerOptions);
        }
        else {
            marker.remove();
            marker = null;
        }

    }


}


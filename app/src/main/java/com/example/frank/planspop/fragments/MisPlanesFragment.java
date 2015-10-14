package com.example.frank.planspop.fragments;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.frank.planspop.AppUtil.AppUtil;
import com.example.frank.planspop.R;
import com.example.frank.planspop.adapters.PlanAdapter;
import com.example.frank.planspop.models.Plan;
import com.example.frank.planspop.parse.PlanParse;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MisPlanesFragment extends TitleFragment implements PlanParse.PlanParseInterface, AdapterView.OnItemClickListener {

    public interface ActionMisPlanesFragment{
        void ItemSeleccionado(int position);
    }

    ActionMisPlanesFragment actionMisPlanesFragment;

    ListView list_misPlanes;


    Context context;

    PlanParse planParse = new PlanParse(this);

    public MisPlanesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        actionMisPlanesFragment = (ActionMisPlanesFragment) context;
        this.context = context;
        AppUtil.positionSelectedMisPlanes = -1;
        AppUtil.data_misPlanes = new ArrayList<>();
        AppUtil.tab = 1;

        AppUtil.adapter_list = new PlanAdapter(context, AppUtil.data_misPlanes);
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_mis_planes, container, false);

        list_misPlanes = (ListView) v.findViewById(R.id.mis_planes_list);
        list_misPlanes.setAdapter(AppUtil.adapter_list);
        list_misPlanes.setOnItemClickListener(this);
        AppUtil.adapter_list.notifyDataSetChanged();


        planParse.getPlanUser();


        return v;
    }


    @Override
    public void onResume() {
        AppUtil.adapter_list.notifyDataSetChanged();
        super.onResume();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        actionMisPlanesFragment.ItemSeleccionado(position);
    }

    @Override
    public void done(boolean exito) {

    }

    @Override
    public void resultPlan(boolean exito, Plan plan) {

    }
    public void  search (){
        Log.d("Prueba", "LLego" + AppUtil.searching);
        //PlanParse planp = new PlanParse(this);
        planParse.getPlanByName(AppUtil.searching);

    }
    public void Reload(){
        planParse.getPlanUser();
    }

    @Override
    public void resultListPlans(boolean exito, List<Plan> planes) {
        if(exito){
            AppUtil.data_misPlanes.clear();
            for(int i=0; i<planes.size(); i++){
                AppUtil.data_misPlanes.add(planes.get(i));
            }
            AppUtil.adapter_list.notifyDataSetChanged();
        }
        else
            Log.i("resultPLan exito:", "es falso");
    }

    @Override
    public String getTitle() {
        return "Mis Planes";
    }
}

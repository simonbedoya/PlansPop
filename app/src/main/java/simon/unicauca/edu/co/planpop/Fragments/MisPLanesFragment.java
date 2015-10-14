package simon.unicauca.edu.co.planpop.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import simon.unicauca.edu.co.planpop.AppUtil.AppUtil;
import simon.unicauca.edu.co.planpop.R;
import simon.unicauca.edu.co.planpop.adapters.PlanAdapter;
import simon.unicauca.edu.co.planpop.models.Plan;
import simon.unicauca.edu.co.planpop.parse.PlanParse;

/**
 * A simple {@link Fragment} subclass.
 */
public class MisPLanesFragment extends TitleFragment implements AdapterView.OnItemClickListener, PlanParse.PlanParseInterface {


    ListView list_misPlanes;
    PlanAdapter adapter;

    Context context;

    PlanParse planParse;

    public MisPLanesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        this.context = context;
        AppUtil.data_misPlanes = new ArrayList<>();

        adapter = new PlanAdapter(context, AppUtil.data_misPlanes);
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_mis_planes, container, false);

        list_misPlanes = (ListView) v.findViewById(R.id.mis_planes_list);
        list_misPlanes.setAdapter(adapter);
        list_misPlanes.setOnItemClickListener(this);
        adapter.notifyDataSetChanged();

        PlanParse planParse = new PlanParse(this);
        planParse.getPlanUser();

        return v;
    }


    @Override
    public void onResume() {
        adapter.notifyDataSetChanged();
        super.onResume();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void done(boolean exito) {

    }

    @Override
    public void resultPlan(boolean exito, Plan plan) {

    }

    @Override
    public void resultListPlans(boolean exito, List<Plan> planes) {
        if(exito){
            for(int i=0; i<planes.size(); i++){
                AppUtil.data_misPlanes.add(planes.get(i));
            }
            adapter.notifyDataSetChanged();
        }
        else
            Log.i("resultPLan exito:", "es falso");
    }

    @Override
    public String getTitle() {
        return "Mis Planes";
    }



}

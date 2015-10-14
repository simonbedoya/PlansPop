package simon.unicauca.edu.co.planpop.Fragments;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import simon.unicauca.edu.co.planpop.R;
import simon.unicauca.edu.co.planpop.models.Plan;
import simon.unicauca.edu.co.planpop.parse.PlanParse;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddFragment extends Fragment implements View.OnClickListener {


    public  interface ActionListenerAddFragment{
        void siguiente(String nombre, String descripcion, String fechaHora);

    }

    private int mYear, mMonth, mDay, mHora,mMinuto;
    private String Sfecha,Shora;

    ActionListenerAddFragment actionListenerAddFragment;

    EditText edit_nombre,edit_descripcion;
    TextView txt_fecha, txt_hora;
    Button btn_fecha,btn_hora,btn_siguiente;

    Context context;

    public AddFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        actionListenerAddFragment = (ActionListenerAddFragment) context;
        this.context = context;
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v;
        v = inflater.inflate(R.layout.fragment_add, container, false);

        edit_nombre = (EditText) v.findViewById(R.id.nombre);
        edit_descripcion = (EditText) v.findViewById(R.id.descripcion);
        txt_fecha = (TextView) v.findViewById(R.id.fecha);
        txt_hora = (TextView) v.findViewById(R.id.hora);
        btn_fecha = (Button) v.findViewById(R.id.btn_fecha);
        btn_hora = (Button) v.findViewById(R.id.btn_hora);


        btn_siguiente = (Button) v.findViewById(R.id.btn_siguienteAddFragment);

        //<editor-fold desc="Botton fecha">
        btn_fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePicker =new DatePickerDialog(v.getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                Sfecha = dayOfMonth+"/"+monthOfYear+"/"+year;
                                txt_fecha.setText(Sfecha);

                            }
                        },mYear,mMonth,mDay);
                datePicker.show();
            }
        });
        //</editor-fold>

        //<editor-fold desc="Hora">
        btn_hora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                mHora = calendar.get(calendar.HOUR_OF_DAY);
                mMinuto  = calendar.get(calendar.MINUTE);

                TimePickerDialog timePicker = new TimePickerDialog(v.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String am_pm;
                        if(hourOfDay < 12) {
                            am_pm = "AM";
                        } else {
                            am_pm = "PM";
                        }
                        Shora = hourOfDay+":"+minute+" "+am_pm;
                        txt_hora.setText(Shora);
                    }
                },mHora,mMinuto,false);
                timePicker.show();
            }
        });
        //</editor-fold>


        btn_siguiente.setOnClickListener(this);

        //    btn_lugar.setOnClickListener(this);
        //   btn_lugar = (Button) v.findViewById(R.id.btn_marcar_lugar);
        return v;
    }

    @Override
    public void onClick(View v) {
        String name = edit_nombre.getText().toString();
        String description = edit_descripcion.getText().toString();
        Sfecha = txt_fecha.getText().toString();
        Shora = txt_hora.getText().toString();

        if(name!= " " && description != " " && Sfecha != "Fecha" && Shora != "Hora") {
            actionListenerAddFragment.siguiente(name, description, Sfecha + " " + Shora);
        }
        else {
            Toast.makeText(context,"Todos los campos son requeridos", Toast.LENGTH_LONG);
        }
            /*case R.id.btn_add:
                Plan plan = new Plan();
                plan.setNombre(edit_nombre.getText().toString());
                plan.setDescripcion(edit_descripcion.getText().toString());
                plan.setFecha(txt_fecha.getText().toString());
                plan.setImgPath(ImgF.getPath());

                PlanParse planParse = new PlanParse(this);

                try {
                    planParse.insertPlan(plan);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                actionListenerAddFragment.terminarFragment(true);

                break;*/

    }

}

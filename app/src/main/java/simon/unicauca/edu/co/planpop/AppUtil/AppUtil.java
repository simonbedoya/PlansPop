package simon.unicauca.edu.co.planpop.AppUtil;

import android.content.Context;

import java.util.List;

import simon.unicauca.edu.co.planpop.adapters.PlanAdapter;
import simon.unicauca.edu.co.planpop.models.Plan;

/**
 * Created by sbv23 on 08/10/2015.
 */
public class AppUtil {
    public static String email;
    public static String c_name;
    public static String b_date;
    public static String sex = "Hombre";
    public static String username;
    public static String pass;
    public static List<Plan> data;
    public static List<Plan> search;
    public static int positionSelected;
    public static String searching;
    public static int flag=0;
    public static PlanAdapter adapter;
    public static int aux_asistant=0;
    public static List<Plan> data_misPlanes;
}

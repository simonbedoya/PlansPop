package simon.unicauca.edu.co.planpop.models;

/**
 * Created by Frank on 6/10/2015.
 */
public class Asistente {

    private String id,id_user,id_plan;

    public Asistente() {
    }

    public Asistente(String id, String id_user, String id_plan) {
        this.id = id;
        this.id_user = id_user;
        this.id_plan = id_plan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getId_plan() {
        return id_plan;
    }

    public void setId_plan(String id_plan) {
        this.id_plan = id_plan;
    }
}

package entities;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.Date;

public class Task {

    private int idtask;
    private StringProperty nomcour;
    private ObjectProperty<Date> date;
    private StringProperty etat;
    private int idplan;
    private StringProperty planName; // New field to store the name of the plan

    public Task() {
        this.nomcour = new SimpleStringProperty();
        this.date = new SimpleObjectProperty<>();
        this.etat = new SimpleStringProperty();
        this.planName = new SimpleStringProperty();
    }

    public Task(int idtask, String nomcour, Date date, String etat, int idplan, String planName) {
        this.idtask = idtask;
        this.nomcour = new SimpleStringProperty(nomcour);
        this.date = new SimpleObjectProperty<>(date);
        this.etat = new SimpleStringProperty(etat);
        this.idplan = idplan;
        this.planName = new SimpleStringProperty(planName);
    }

    public int getIdtask() {
        return idtask;
    }

    public void setIdtask(int idtask) {
        this.idtask = idtask;
    }

    public StringProperty nomcourProperty() {
        return nomcour;
    }

    public String getNomcour() {
        return nomcour.get();
    }

    public void setNomcour(String nomcour) {
        this.nomcour.set(nomcour);
    }

    public ObjectProperty<Date> dateProperty() {
        return date;
    }

    public Date getDate() {
        return date.get();
    }

    public void setDate(Date date) {
        this.date.set(date);
    }

    public StringProperty etatProperty() {
        return etat;
    }

    public String getEtat() {
        return etat.get();
    }

    public void setEtat(String etat) {
        this.etat.set(etat);
    }

    public int getIdplan() {
        return idplan;
    }

    public void setIdplan(int idplan) {
        this.idplan = idplan;
    }

    public StringProperty planNameProperty() {
        return planName;
    }

    public String getPlanName() {
        return planName.get();
    }

    public void setPlanName(String planName) {
        this.planName.set(planName);
    }

    @Override
    public String toString() {
        return "Task{" +
                "idtask=" + idtask +
                ", nomcour='" + nomcour + '\'' +
                ", date=" + date +
                ", etat='" + etat + '\'' +
                ", idplan=" + idplan +
                ", planName='" + planName + '\'' +
                '}';
    }
}

package org.techtown.mission10;

public class routine {
    String routineName;
    String date;
    int detailShown;

    public routine(String routineName, String date) {
        this.routineName = routineName;
        this.date = date;
    }

    public int getDetailShown() {
        return detailShown;
    }

    public void setDetailShown(int detailShown) {
        this.detailShown = detailShown;
    }

    public String getRoutineName() {
        return routineName;
    }

    public void setRoutineName(String routineName) {
        this.routineName = routineName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

package dfk.example.com.iworker.Model;

public class Logs {
    private int empId, id;
    private String empMac, location, logTime;

    public Logs() {
        this.empId = empId;
        this.id = id;
        this.empMac = empMac;
        this.location = location;
        this.logTime = logTime;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmpMac() {
        return empMac;
    }

    public void setEmpMac(String empMac) {
        this.empMac = empMac;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLogTime() {
        return logTime;
    }

    public void setLogTime(String logTime) {
        this.logTime = logTime;
    }
}

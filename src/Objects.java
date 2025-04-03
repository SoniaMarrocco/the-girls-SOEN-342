import java.util.ArrayList;

public class Objects {
    private String description;
    private Boolean available;
    private ArrayList<Object> objects;

    public Objects(int obj_Id, String description, Boolean available) {
        this.description = description;
        this.available = available;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public ArrayList<Object> getObjects() {
        return objects;
    }

    public void setObjects(ArrayList<Object> objects) {
        this.objects = objects;
    }


    public void makeObjectAvailable(int obj_Id){}

    //public Objects selectObject(){}

    public void searchObject()  {
        if (objects.size() != 0) {
            for (int i = 0; i < objects.size(); i++) {
                System.out.print(i + 1 + objects.get(i).toString());
            }
        }
        else {
            System.out.println("No objects exist in the system");
        }
    }

    public void selectObject(int i) {
        objects.get(i).toString();
    }

    public String toString() {
        return this.description;    
    }

    

}

public class Expert {
    private String name;
    private String contact;
    private int licenceNum;
    private int[] specialty;
    public Objects[] objs;

    public Expert(String name, String contact, int licenceNum, int[] specialty, Objects[] objs) {
        this.name = name;
        this.contact = contact;
        this.licenceNum = licenceNum;
        this.specialty = specialty;
        this.objs = objs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public int getLicenceNum() {
        return licenceNum;
    }

    public void setLicenceNum(int licenceNum) {
        this.licenceNum = licenceNum;
    }

    public int[] getSpecialty() {
        return specialty;
    }

    public void setSpecialty(int[] specialty) {
        this.specialty = specialty;
    }
}

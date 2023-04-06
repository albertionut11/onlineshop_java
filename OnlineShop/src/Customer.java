public class Customer {
    private static int next_id = 1;
    private String customer_name, gender, phone, mail;
    private int customer_id, age, points;

    public Customer(String customer_name, int age, String gender, String phone, String mail) {
        this.customer_name = customer_name;
        this.customer_id = next_id;
        this.age = age;
        this.gender = gender;
        this.phone = phone;
        this.mail = mail;
        this.points = 0;
        next_id++;
    }

    public String getCustomerName() {
        return customer_name;
    }

    public void setCustomerName(String customer_name) {
        this.customer_name = customer_name;
    }

    public int getCustomerId() {
        return customer_id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getPoints() {
        return points;
    }

    public void addPoints(double price) {
        this.points += (price / 100);
    }
}

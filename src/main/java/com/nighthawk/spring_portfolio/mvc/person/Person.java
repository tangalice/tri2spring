package com.nighthawk.spring_portfolio.mvc.person;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.format.annotation.DateTimeFormat;
import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
/*
Person is a POJO, Plain Old Java Object.
First set of annotations add functionality to POJO
--- @Setter @Getter @ToString @NoArgsConstructor @RequiredArgsConstructor
The last annotation connect to database
--- @Entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@TypeDef(name="json", typeClass = JsonType.class)
public class Person {
    // automatic unique identifier for Person record
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    // email, password, roles are key attributes to login and authentication
    @NotEmpty
    @Size(min=5)
    @Column(unique=true)
    @Email
    private String email;
    @NotEmpty
    private String password;
    // @NonNull, etc placed in params of constructor: "@NonNull @Size(min = 2, max = 30, message = "Name (2 to 30 chars)") String name"
    @NonNull
    @Size(min = 2, max = 30, message = "Name (2 to 30 chars)")
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dob;
    @Column(unique=false)
    private int height;
    @Column(unique=false)
    private int weight;
    @NotEmpty
    private String gender;
    /* HashMap is used to store JSON for daily "stats"
    "stats": {
        "2022-11-13": {
            "calories": 2200,
            "steps": 8000
        }
    }
    */
    @Type(type="json")
    @Column(columnDefinition = "jsonb")
    private Map<String,Map<String, Object>> stats = new HashMap<>();
    // Constructor used when building object from an API
    public Person(String email, String password, String name, Date dob, int height, int weight, String gender) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.dob = dob;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
    }
    // A custom getter to return age from dob attribute
    public int getAge() {
        if (this.dob != null) {
            LocalDate birthDay = this.dob.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            return Period.between(birthDay, LocalDate.now()).getYears(); }
        return -1;
    }
    public double getBmi() {
            double bmi = (703 * this.weight / Math.pow(this.height,2));
            return bmi;
    }

    public double bmrCalculator() {
        if (getGender().equals("male")) {
            return 88.362 + (13.397 * getWeight() / 2.205) + (4.799 * getHeight() * 2.54) - (5.677 * getAge());
        } else if (getGender().equals("female")) {
            return 447.593 + (9.247 * getWeight() / 2.205) + (3.098 * getHeight() * 2.54) - (4.330 * getAge());
        } else {
            return -1;
        }
    }
    public double amrCalculator() {
        return bmrCalculator() * 1.55;
    }
    // Calories required to burn per day to maintain current weight (Uses Active &
    // Basal Metabolic Rate)
    public int getStep() {
        if (getDob() != null && getWeight() != 0 && getHeight() != 0) {
            // assumes moderate exercise each day, indicates calories needed to burn
            return (int) (Math.round(amrCalculator() - bmrCalculator() - 500) / 0.04);
        }
        return -1;
    }
    public String getBmiToString() {
        return("{ \"bmi\": " + this.getBmi() +"}");
    }

    public String getStepToString() {
        return("{ \"step\": " + this.getStep() +"}");
    }

    public String toString(){
        return ("{\"email\": " + this.email + ", " + "\"password\": " + this.password + ", " + "\"name\": " + this.name + ", " + "\"dob\": " + this.dob + " \"height\": " + this.height + ", \"weight\": " + this.weight + "}" );
    } 
    public static void main(String[] args){
        LocalDate dob = LocalDate.of(2006,07,19);
        Date date = Date.from(dob.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Person noArgsPerson = new Person();
        Person allArgsPerson = new Person("tang.alice.000@gmail.com","pineapple", "Alice Tang", date, 66, 107, "female");
        System.out.println(noArgsPerson);
        System.out.println(allArgsPerson);
    }
}
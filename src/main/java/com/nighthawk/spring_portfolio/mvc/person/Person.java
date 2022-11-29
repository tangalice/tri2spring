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
            double bmi = (703*this.weight / Math.pow(this.height,2));
            return bmi;
    }

    // Max heart rate
    public double mhrCalculator() {
        return 220 - getAge();
    }

    // Target Heart Rate
    // recommended that you exercise within 55 to 85 percent of your maximum heart rate for at least 20 to 30 minutes to get the best results from aerobic exercise.
    public int getRate1() {
        return (int)(mhrCalculator() * 0.55);
    }

    public int getRate2() {
        return (int)(mhrCalculator() * 0.85);
    }

    public String rateToString() {
        return("{ \"rate\": " + this.getRate1() +"-" + this.getRate2() +"}");
    }

    public String bmiToString() {
        return("{ \"bmi\": " + this.getBmi() +"}");
    }

    public String toString(){
        return ("{\"email\": " + this.email + ", " + "\"password\": " + this.password + ", " + "\"name\": " + this.name + ", " + "\"dob\": " + this.dob + " \"height\": " + this.height + ", \"weight\": " + this.weight + "}" );
    } 
    public static void main(String[] args){
        LocalDate dob = LocalDate.of(2005,01,30);
        Date date = Date.from(dob.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Person noArgsPerson = new Person();
        Person allArgsPerson = new Person("alicetang05@gmail.com","pineapple", "Alice Tang", date, 66, 107, "female");
        System.out.println(noArgsPerson);
        System.out.println(allArgsPerson);
    }
}
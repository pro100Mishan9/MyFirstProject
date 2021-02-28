package Project1.Entitys;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class BuildingsTools {
    @Id
    @GeneratedValue
    private int id;

    @Column(name = "Name", nullable = false, length = 50)
    private String name;

    @Column(name = "Date_added")
    private LocalDateTime dateAdded;

    @Column(name = "Date_of_change")
    private LocalDateTime dateOfChange;

    @Column(name = "Status", nullable = false)
    private String status;

    @Column(name = "Attention")
    private String attention;

    public void setDateAdded(LocalDateTime dateAdded) {
        this.dateAdded = dateAdded;
    }

    public void setDateOfChange(LocalDateTime dateOfChange) {
        this.dateOfChange = dateOfChange;
    }
}

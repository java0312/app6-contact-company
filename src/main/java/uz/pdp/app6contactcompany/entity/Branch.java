package uz.pdp.app6contactcompany.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name", "region_id"}))
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(optional = false)
    private Region region;

    @OneToOne(optional = false)
    private User leader;

    @ManyToOne(optional = false)
    private User manager;

    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL)
    private Set<User> employees;

}

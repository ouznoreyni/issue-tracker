package sn.noreyni.issuetrackerbackend.role;


import jakarta.persistence.*;
import lombok.*;
import sn.noreyni.issuetrackerbackend.shared.entity.BaseEntity;

/**
 * class for roles by User
 */

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table( name = "issue_tracker_role",uniqueConstraints = {
        @UniqueConstraint(columnNames = "name")
})
public class Role extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;

    @Column(name="name", unique = true)
    private String name;

    @Column(name="description")
    private String description;

    @Column(columnDefinition = "bit not null default 1")
    private boolean active=true;
}
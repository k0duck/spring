package hellojpa;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(name = "user_name")
    private String username;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "team_id")
    private Team team;

    @Embedded
    private Address address;

    @ElementCollection
    @CollectionTable(name = "favorite_foods", joinColumns =
    @JoinColumn(name = "member_id ")
    )
    @Column(name = "food_name")
    private Set<String> favoriteFoods = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "address_history", joinColumns =
    @JoinColumn(name = "member_id")
    )
    private List<Address> addressHistory = new ArrayList<>();
}

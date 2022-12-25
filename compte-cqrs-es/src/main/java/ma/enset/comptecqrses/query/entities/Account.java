package ma.enset.comptecqrses.query.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.enset.comptecqrses.commonapi.enums.AccountStatus;

import javax.persistence.*;
import java.util.Collection;


@Entity
@Data @AllArgsConstructor @NoArgsConstructor
@Builder
public class Account {
    @Id
    private String id;
    private String currency;
    private double balance;
    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "account")
    private Collection<Operation> operations;
}
package goldfish.express.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;


@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "operator" , indexes = @Index(columnList = "id"))
public class OperatorTelegramEntity extends BaseEntity {
    private String firstName;
    private String lastName;
}

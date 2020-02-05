package proit.test.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jooq.util.maven.example.tables.pojos.Employee;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmplList extends Employee {
    String nameOrg;
    String nameHeadempl;
}

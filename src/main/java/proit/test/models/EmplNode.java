package proit.test.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jooq.util.maven.example.tables.pojos.Employee;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmplNode {
    Employee value;
    List<EmplNode> subItems;

    public EmplNode(List<EmplNode> subItems) {
        this.subItems = subItems;
    }
}

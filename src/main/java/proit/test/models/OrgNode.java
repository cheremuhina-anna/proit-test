package proit.test.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jooq.util.maven.example.tables.pojos.Organization;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrgNode {
    Organization value;
    boolean haveSubOrg;

    public OrgNode(Organization value) {
        this.value = value;
    }
}

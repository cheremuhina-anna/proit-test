package proit.test.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jooq.util.maven.example.tables.pojos.Organization;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrgList extends Organization {
    String nameHeadorg;
    Integer countEmpl;

    public OrgList(UUID idOrg, String name, UUID idHeadorg, String nameHeadorg, Integer countEmpl){
        super(idOrg, name, idHeadorg);
        this.nameHeadorg = nameHeadorg;
        this.countEmpl = countEmpl;
    }
}

package proit.test.services;

import org.jooq.util.maven.example.tables.pojos.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proit.test.models.OrgList;
import proit.test.repositories.OrgRepo;

import java.util.List;
import java.util.UUID;

@Service
public class OrgService {

    @Autowired
    private OrgRepo repo;

    public List<OrgList> listOrgAndEmpl(int offset, int limit){
        return repo.selectAll(offset, limit);
    }

    public List<Organization> list() { return repo.select();}

    public int getCountOrg(){ return repo.countOrg(); }

    public Organization insert(Organization org){
        return repo.insert(org);
//        return new Organization(id, org.getName(), org.getIdHeadorg());
    }

    public Organization update(Organization org){
        int res = repo.update(org);
//        if(res==1)
            return org;
//        else
//            return new Organization();
    }

    public boolean delete(UUID id_org){
        if (repo.canDelete(id_org).size()>0)
            return false;
        else {
            repo.delete(id_org);
            return true;
        }
    }
}

package proit.test.services;

import org.jooq.util.maven.example.tables.pojos.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proit.test.models.OrgList;
import proit.test.models.OrgNode;
import proit.test.repositories.OrgRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrgService {

    @Autowired
    private OrgRepo repo;

    public List<OrgList> pageListOrgAndEmpl(int offset, int limit){
        return repo.selectPage(offset, limit);
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

    public List<OrgNode> getNodes(){
        List<OrgNode> list = new ArrayList<>();
        for (Organization item: repo.orgDontHaveHead()) {
            if (repo.countSubOrg(item.getId()) > 0) list.add(new OrgNode(item,true));
            else list.add(new OrgNode(item,false));
        }
//        repo.orgDontHaveHead().forEach(x-> repo.countSubOrg(x.getId()) > 0 ? list.add(new OrgNode(x,true)) : list.add(new OrgNode(x,false)));
        return list;
    }

    public List<Organization> getOrgWithoutHeadOrg() {
        return repo.orgDontHaveHead();
    }

    public List<Organization> getSubOrg(UUID id_org) {
        return repo.subOrg(id_org);
    }
}

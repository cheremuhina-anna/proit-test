package proit.test.services;

import org.jooq.util.maven.example.tables.pojos.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proit.test.models.OrgList;
import proit.test.models.OrgNode;
import proit.test.repositories.OrgRepo;

import java.util.ArrayList;
import java.util.Collections;
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
        return org;
    }

    public boolean delete(UUID id_org){
        if (repo.countSubOrg(id_org)>0)
            return false;
        else {
            repo.delete(id_org);
            return true;
        }
    }

    public List<OrgNode> getAllTree(){
        List<OrgNode> list = new ArrayList<>();
        for (Organization item: repo.orgDontHaveHead()) {
            list.add(new OrgNode(item, getChildren(item.getId())));
        }
        return list;
    }

    public List<OrgNode> getChildren(UUID id){
        List <Organization> subOrgs = repo.subOrg(id);
        if (subOrgs.size() == 0) return Collections.emptyList();
        else {
            List<OrgNode> list = new ArrayList<>();
            for (Organization item : subOrgs) {
                list.add(new OrgNode(item, getChildren(item.getId())));
            }
            return list;
        }
    }

    public List<Organization> getHeadOrgs(UUID id_headOrg) {
        List<Organization> headOrgs = new ArrayList<>();
        Organization headOrg = repo.headOrg(id_headOrg);
        headOrgs.add(headOrg);
        System.out.println(headOrg);
        while (headOrg.getIdHeadorg() != null) {
            headOrg = repo.headOrg(headOrg.getIdHeadorg());
            headOrgs.add(headOrg);
            System.out.println(headOrg);
        }
        return headOrgs;
    }

    public List<Organization> listWithoutSubOrgs(UUID id_org) {
        List<Organization> list = list();
        list.removeAll(listAllSubOrgs(id_org));
        return list;
    }


    public List<Organization> listAllSubOrgs(UUID id_org) {
        OrgNode temp = new OrgNode(getChildren(id_org));
        List<Organization> list = new ArrayList<>();
        list.add(repo.selectOrg(id_org));
        for (OrgNode node: temp.getSubItems()) {
            list.add(node.getValue());
            list.addAll(orgFromNodes(node));
        }
        return list;
    }

    public List<Organization> orgFromNodes(OrgNode node){
        if(node.getSubItems().isEmpty()) return Collections.emptyList();
        else {
            List<Organization> list = new ArrayList<>();
            for (OrgNode item : node.getSubItems()) {
                list.add(item.getValue());
                list.addAll(orgFromNodes(item));
            }
            return list;
        }
    }

    public List<Organization> getOrgWithoutHeadOrg() {
        return repo.orgDontHaveHead();
    }

    public List<Organization> getSubOrg(UUID id_org) {
        return repo.subOrg(id_org);
    }

    public List<OrgList> getFilterOrgList(String str, int offset, int limit) {
        return repo.filterOrgList(str, offset, limit);
    }
}

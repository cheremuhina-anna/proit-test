package proit.test.services;

import org.jooq.util.maven.example.tables.pojos.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proit.test.models.EmplList;
import proit.test.models.EmplNode;
import proit.test.repositories.EmplRepo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class EmplService {

    @Autowired
    private EmplRepo repo;

    public List<EmplList> selectPage(int offset, int limit){
        return repo.selectPage(offset, limit);
    }

    public int countEmpl(){return repo.countEmpl();}

    public List<Employee> list() { return repo.select();}

    public List<Employee> selectEmplOrg(UUID id_org) { return  repo.selectEmplOrg(id_org); }

    public Employee insert(Employee empl){
        UUID id = repo.insert(empl);
        return new Employee(id, empl.getName(), empl.getIdOrg(), empl.getIdHeadempl());
    }

    public Employee update(Employee newEmpl){
        Employee targetEmpl = repo.selectEmpl(newEmpl.getId());
        if (targetEmpl.getIdOrg() != newEmpl.getIdOrg()) {
            List<Employee> subEmpl = repo.subEmpl(newEmpl.getId());
            if (!subEmpl.isEmpty())
                subEmpl.stream().peek(sub->sub.setIdHeadempl(null)).forEach(sub->repo.update(sub));
        }
        int res = repo.update(newEmpl);
        return newEmpl;
    }

    public boolean delete(UUID id_empl){
        if (repo.canDelete(id_empl).size()>0)
            return false;
        else {
            repo.delete(id_empl);
            return true;
        }
    }

    public List<EmplNode> getAllTree(){
        List<EmplNode> list = new ArrayList<>();
        for (Employee item: repo.emplDontHaveHead()) {
            list.add(new EmplNode(item, getChildren(item.getId())));
        }
        return list;
    }

    public List<EmplNode> getChildren(UUID id){
        List <Employee> subEmpl = repo.subEmpl(id);
        if (subEmpl.size() == 0) return Collections.emptyList();
        else {
            List<EmplNode> list = new ArrayList<>();
            for (Employee item : subEmpl) {
                list.add(new EmplNode(item, getChildren(item.getId())));
            }
            return list;
        }
    }

    public List<Employee> listWithoutSubEmpls(UUID id_empl, UUID id_org) {
        List<Employee> list = selectEmplOrg(id_org);
        list.removeAll(listAllSubEmpls(id_empl));
        return list;
    }


    public List<Employee> listAllSubEmpls(UUID id_empl) {
        if(id_empl == null) return Collections.emptyList();
        else {
            EmplNode temp = new EmplNode(getChildren(id_empl));
            List<Employee> list = new ArrayList<>();
            list.add(repo.selectEmpl(id_empl));
            for (EmplNode node : temp.getSubItems()) {
                list.add(node.getValue());
                list.addAll(emplFromNodes(node));
            }
            return list;
        }
    }

    public List<Employee> emplFromNodes(EmplNode node){
        if(node.getSubItems().isEmpty()) return Collections.emptyList();
        else {
            List<Employee> list = new ArrayList<>();
            for (EmplNode item : node.getSubItems()) {
                list.add(item.getValue());
                list.addAll(emplFromNodes(item));
            }
            return list;
        }
    }

    public List<EmplList> getFilterEmplList(String type, String str, int offset, int limit) {
        if (str.isEmpty())
            return repo.selectPage(offset, limit);
        else {
            if (type.equals("name"))
                return repo.namefilterEmplList(str, offset, limit);
            else
                return repo.orgfilterEmplList(str, offset, limit);
        }
    }
}

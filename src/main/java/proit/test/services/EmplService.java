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

    public Employee update(Employee empl){
        int res = repo.update(empl);
        return empl;
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
}

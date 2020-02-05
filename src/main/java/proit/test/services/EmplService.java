package proit.test.services;

import org.jooq.util.maven.example.tables.pojos.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proit.test.models.EmplList;
import proit.test.repositories.EmplRepo;

import java.util.List;
import java.util.UUID;

@Service
public class EmplService {

    @Autowired
    private EmplRepo repo;

    public List<EmplList> selectAll(){
        return repo.selectAll();
    }

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
}
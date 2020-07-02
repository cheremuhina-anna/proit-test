package proit.test.controllers;

import org.jooq.util.maven.example.tables.pojos.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import proit.test.models.OrgList;
import proit.test.models.OrgListOnPage;
import proit.test.models.OrgNode;
import proit.test.services.OrgService;

import java.util.List;
import java.util.UUID;

@RestController
public class OrgController {

    @Autowired
    private OrgService service;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/organization/")
    public OrgListOnPage showPageOrg(@RequestParam("offset") int offset, @RequestParam("limit") int limit) {
        return new OrgListOnPage(service.getCountOrg(), service.pageListOrgAndEmpl(offset, limit));
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/organization/{filter}")
    public OrgListOnPage showPageOrg(@PathVariable("filter") String filter, @RequestParam("offset") int offset, @RequestParam("limit") int limit) {
        List<OrgList> listOrg = service.getFilterOrgList(filter, offset, limit);
        return new OrgListOnPage(listOrg.size(), listOrg);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/organization/create")
    public List<Organization> list() {
        return service.list();
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/organization/create")
    public Organization insertOrg(@RequestBody Organization org) {
        return service.insert(org);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/organization/update")
    public Organization updateOrg(@RequestBody Organization org) { ;
        return service.update(org);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("/organization/{id_org}")
    public boolean deleteOrg(@PathVariable("id_org") UUID id_org) { ;
        return service.delete(id_org);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/organization/tree")
    public List<OrgNode> allTree() {
        return service.getAllTree();
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/organization/update")
    public List<Organization> getOrganizations(@RequestParam("id_org") UUID id_org) {
        return service.listWithoutSubOrgs(id_org);
    }
}

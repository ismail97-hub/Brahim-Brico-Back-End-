package ib.develop.matstore.controllers;

import ib.develop.matstore.common.controllers.BaseController;
import ib.develop.matstore.entities.Settlement;
import ib.develop.matstore.services.SettlementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/settlement")
public class SettlementController extends BaseController<Settlement,Long> {

    @Autowired
    private SettlementService service;

    @PostMapping(value="/new",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Settlement newSettlement(
           @RequestParam("clientPhone") String clientPhone,
           @RequestParam("amountPaid") double amountPaid){
        return service.newSettlement(clientPhone,amountPaid);
    }

    @GetMapping(value = "/search/{query}")
    public List<Settlement> search(@PathVariable String query, Pageable pageable){
        return service.search(query, pageable);
    }
}

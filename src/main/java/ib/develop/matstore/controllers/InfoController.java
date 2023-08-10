package ib.develop.matstore.controllers;

import ib.develop.matstore.common.controllers.BaseController;
import ib.develop.matstore.entities.Info;
import ib.develop.matstore.services.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/info")
public class InfoController extends BaseController<Info,Long> {

    @Autowired
    private InfoService infoService;

    @GetMapping("/first")
    private Info getInfo(){
        return infoService.getInfo();
    }

}

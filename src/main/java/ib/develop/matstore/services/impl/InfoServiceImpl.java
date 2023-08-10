package ib.develop.matstore.services.impl;

import ib.develop.matstore.common.services.BaseServiceImpl;
import ib.develop.matstore.entities.Info;
import ib.develop.matstore.repositories.InfoRepository;
import ib.develop.matstore.services.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InfoServiceImpl extends BaseServiceImpl<Info,Long> implements InfoService{

    @Autowired
    private InfoRepository repository;


    @Override
    public Info getInfo() {
        return findAll().get(0);
    }
}

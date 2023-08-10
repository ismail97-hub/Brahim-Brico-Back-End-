package ib.develop.matstore.services;

import ib.develop.matstore.common.services.BaseService;
import ib.develop.matstore.entities.Info;

public interface InfoService extends BaseService<Info,Long> {

    Info getInfo();
}

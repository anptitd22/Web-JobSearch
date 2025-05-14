package com.project.webIT.services.IService;

import java.util.List;

public interface BaseService <T,Res,ID>{
    Res create(T data) throws Exception;
    Res update(T data) throws Exception;
    Res getAll();
    Res getById(ID id) throws Exception;
    String deleteById(ID id) throws Exception;
    String deleteByListId(List<ID> listId) throws Exception;
}

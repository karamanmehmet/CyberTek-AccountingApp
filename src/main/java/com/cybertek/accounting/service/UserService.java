package com.cybertek.accounting.service;

import com.cybertek.accounting.entity.Company;
import com.cybertek.accounting.entity.User;

import java.util.List;

public interface UserService {

    User create(User user);

    List<User> readAll();

    User update(User user);

    void delete(User user);

    List<User> findByCompanyAndState(Company company, Boolean enabled);
}

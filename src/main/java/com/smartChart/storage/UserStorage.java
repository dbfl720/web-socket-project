package com.smartChart.storage;

import java.util.HashSet;
import java.util.Set;

// db에 관계형 데이터 만들어서 사용하기 권장. // 이 부분은 단순 에시.
public class UserStorage {

    private static UserStorage instance;
    private Set<String> users;

    private UserStorage() {
        users = new HashSet<>();
    }

    public static synchronized UserStorage getInstance() {
        if (instance == null) {
            instance = new UserStorage();
        }
        return instance;
    }

    public Set<String> getUsers() {
        return users;
    }

    public void setUser(String userName) throws Exception {
        if (users.contains(userName)) {
            throw new Exception("User aready exists with userName: " + userName);
        }
        users.add(userName);
    }
}
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

    public static synchronized UserStorage getInstance() {  // synchronized 키워드를 제공해 스레드간 동기화를 시켜 data의 thread-safe를 가능 // 현재 데이터를 사용하고 있는 해당 스레드를 제외하고 나머지 스레드들은 데이터에 접근 할 수 없도록 막는 개념
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
            throw new Exception("User already exists with userName: " + userName);
        }

        users.add(userName);  // 지금은 연습용이기 때문에 실제 데이터에 저장해라.
    }
}

package com.techacademy.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import com.techacademy.entity.User;
import com.techacademy.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository repository) {
        this.userRepository = repository;
    }

    //全件を検索して返す
    public List<User> getUserList() {
        // リポジトリのfindAllメソッドを呼び出す
        return userRepository.findAll();
    }
    
 // 追記
    //Userを1件検索して返す
    public User getUser(Integer id) {
        return userRepository.findById(id).get();
    }
    
    //追記
    //Userの登録を行う
    @Transactional
    public User saveUser(User user) {
        return userRepository.save(user);
    }
    
    //追記
    //Userの削除を行う
    @Transactional
    public void deleteUser(Set<Integer> idck) {
        for(Integer id : idck) {
            userRepository.deleteById(id);
        }
    }
}
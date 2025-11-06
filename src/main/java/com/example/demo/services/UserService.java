package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    
    public List  getAllUsers(){
      
        List<User> users=userRepository.findAll();
        // for(User user:users){
        //     System.out.println(user.getName());   
        // }
        return users;
     }
     public void createUser(User user){
        userRepository.save(user);
     }
     public void softDeleteUser(Long id){
          User user=userRepository.findById(id).orElseThrow(()->new IllegalArgumentException("User not found with id: "+id));
            user.setIsActive(false);
          
         System.out.println("Soft Deleted user with id: "+id);
      }
      public User getUsrById(Long id){
        User user=userRepository.findById(id).orElseThrow(()->new IllegalArgumentException("User not found with id: "+id));
        return user;
     }
     public User getUsrByEMail(String Email){
     Optional <User> user=userRepository.findUserByEmail(Email);
        if(!user.isPresent()){
        throw new IllegalArgumentException("User not found with email: "+Email);
        }
        return user.get();
     }
     
     public void modifyUser(Long id ,String name,String email){
         User user=userRepository.findById(id).orElseThrow(()->new IllegalArgumentException("User not found with id: "+id));
         if(name!=null  && name.length()>0 && !user.getName().equals(name)){
           
            user.setName(name);
         }
         if(email!=null  && email.length()>0 && !user.getEmail().equals(email)){
           
            user.setEmail(email);
         }
         userRepository.save(user);
        }
     
    
}

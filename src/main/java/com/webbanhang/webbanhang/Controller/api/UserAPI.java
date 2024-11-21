package com.webbanhang.webbanhang.Controller.api;

import com.webbanhang.webbanhang.DTO.request.User.SignUpRequestDTO;
import com.webbanhang.webbanhang.DTO.request.User.UpdateUserRequestDTO;
import com.webbanhang.webbanhang.DTO.request.User.UserRequestDTO;
import com.webbanhang.webbanhang.DTO.response.ResponseData;
import com.webbanhang.webbanhang.DTO.response.ResponseError;
import com.webbanhang.webbanhang.Exception.CustomException;
import com.webbanhang.webbanhang.Model.UserModel;
import com.webbanhang.webbanhang.Service.ICouponService;
import com.webbanhang.webbanhang.Service.IUserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;



@RestController
@RequestMapping("/api/user")
@Validated
@Slf4j
@RequiredArgsConstructor
public class UserAPI {

    private final IUserService userService;
    private final  ICouponService couponService;
    private  final PasswordEncoder passwordEncoder;

    @PostMapping("/add")
    public ResponseData<String>  addUser(@Valid @RequestBody UserRequestDTO userDTO)
    {
        try{
            log.info("Request add user: {}",userDTO.getUserName());
            String userId = userService.saveUser(userDTO,1);
            return new ResponseData<>(HttpStatus.CREATED.value(),"Success",userId);
        }
        catch (Exception e){
            log.error("errorMessage={}",e.getMessage(),e.getCause());
            if(e instanceof CustomException)
                return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Save failed");
        }
    }
    @PostMapping("/signup")
    public ResponseData<String> signup(@Valid @RequestBody SignUpRequestDTO signUpRequestDTO){
        try{
            log.info("Request add user: {}",signUpRequestDTO.getUserName());
            String s ="U" + (userService.getAllUser().size()+1);
            UserRequestDTO userDTO = UserRequestDTO.builder()
                    .userID(s)
                    .userName(signUpRequestDTO.getUserName())
                    .type(2)
                    .phone(signUpRequestDTO.getPhone())
                    .password(signUpRequestDTO.getPassword())
                    .email(signUpRequestDTO.getEmail())
                    .gender("")
                    .build();
            String userId = userService.saveUser(userDTO,1);
            return new ResponseData<>(HttpStatus.CREATED.value(),"Success",userId);
        }
        catch (Exception e){
            log.error("errorMessage={}",e.getMessage(),e.getCause());
            if(e instanceof CustomException)
                return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Sign up failed");
        }
    }
    @PutMapping("/update")
    public ResponseData<String>  updateUser(@Valid @RequestBody UserRequestDTO userDTO)
    {
        try{
            log.info("Request update user: {}",userDTO.getUserName());
            String userId = userService.saveUser(userDTO,2);
            return new ResponseData<>(HttpStatus.CREATED.value(),"Success",userId);
        }
        catch (Exception e){
            log.error("errorMessage={}",e.getMessage(),e.getCause());
            if(e instanceof CustomException)
                return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Update failed");
        }
    }
    @PutMapping("/updateInfo")
    public ResponseData<String>  updateInfoUser(@Valid @RequestBody UpdateUserRequestDTO updateUserRequestDTO)
    {
        try{
            log.info("Request update user: {}",updateUserRequestDTO.getUserName());
            UserModel userModel = userService.findUserByID(updateUserRequestDTO.getUserID());
            UserRequestDTO userDTO = UserRequestDTO.builder()
                    .userID(updateUserRequestDTO.getUserID())
                    .userName(updateUserRequestDTO.getUserName())
                    .type(userModel.getRole().getType())
                    .phone(updateUserRequestDTO.getPhone())
                    .password(userModel.getPassword())
                    .email(updateUserRequestDTO.getEmail())
                    .gender(updateUserRequestDTO.getGender())
                    .address(updateUserRequestDTO.getAddress())
                    .build();
            String userId = userService.saveUser(userDTO,2);
            return new ResponseData<>(HttpStatus.CREATED.value(),"Success",userId);
        }
        catch (Exception e){
            log.error("errorMessage={}",e.getMessage(),e.getCause());
            if(e instanceof CustomException)
                return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Update failed");
        }
    }
    @DeleteMapping("/delete/{userID}")
    public ResponseData<String>  deleteUser(@PathVariable("userID")  String id)
    {
        try{
            String userId = userService.deleteUser(id);
            return new ResponseData<>(HttpStatus.CREATED.value(),"Success",userId);
        }
        catch (Exception e){
            log.error("errorMessage={}",e.getMessage(),e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Delete failed");
        }
    }
    @PutMapping("/changePassword")
    public ResponseData<String> changePassword(@RequestBody Map<String, String> allParams, HttpSession session) {
        try{
            UserModel a = (UserModel) session.getAttribute("UserLogin");
            String userId = a.getUserID();
            String oldPassword = allParams.get("oldPassword");
            String newPassword = allParams.get("newPassword");
            String confirmPassword = allParams.get("confirmPassword");
            UserModel user = userService.findUserByID(userId);
            if (user == null) {
                return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Người dùng không tồn tại.");
            }
            if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
                return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Mật khẩu cũ không đúng.");
            }
            if (!newPassword.equals(confirmPassword)) {
                return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Mật khẩu mới và mật khẩu xác nhận không khớp.");
            }
            user.setPassword(passwordEncoder.encode(newPassword));

            userId = userService.changePassword(userId,user.getPassword());
            return new ResponseData<>(HttpStatus.CREATED.value(),"Success",userId);
        }
        catch (Exception e){
            log.error("errorMessage={}",e.getMessage(),e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Change failed");
        }
    }
   @GetMapping("/checkCoupon/{id}")
   public ResponseEntity<?> checkCoupon(@PathVariable("id")  String id) {
         return ResponseEntity.ok(couponService.findCouponByID(id));
   }
   
    
}

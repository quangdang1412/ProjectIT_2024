package com.webbanhang.webbanhang.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roleuser")
public class RoleModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer Type;

    @Column
    private String RoleName;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "Role", cascade = CascadeType.ALL)
    @JsonManagedReference
    List<UserModel> users;

}

package ksp.group3.miraiSugoroku.security;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserForm {
    String password;
    String name;
}

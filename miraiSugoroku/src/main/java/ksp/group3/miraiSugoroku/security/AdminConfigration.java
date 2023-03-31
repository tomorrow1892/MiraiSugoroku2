package ksp.group3.miraiSugoroku.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix="app.admin")
public class AdminConfigration {
    //プロパティは外部ファイルからDIするが，ない場合は下記のデフォルト値を使う
    private String username="admin";
    private String password="kouminmiraisanda";
}
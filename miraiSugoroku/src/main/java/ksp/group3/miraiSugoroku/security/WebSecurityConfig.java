package ksp.group3.miraiSugoroku.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;



@Configuration
@EnableWebSecurity //(1) Spring Securityを使うための設定
@Order(1000)  
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserService userService;         //予約アプリのユーザサービス
    @Autowired
    private PasswordEncoder passwordEncoder; //アプリ共通のパスワードエンコーダ
    @Autowired
    private AdminConfigration adminConfig;   //管理者の設定
    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;
    @Override
    public void configure(WebSecurity web) throws Exception {
        //  (2) 全体に対するセキュリティ設定を行う
        //  特定のパスへのアクセスを認証の対象から外す
        web.ignoring().antMatchers("/img/**", "/css/**", "/js/**");
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
         //  (3) URLごとに異なるセキュリティ設定を行う
        //認可の設定
        http.authorizeRequests(authorize -> authorize
        .antMatchers("/admin").permitAll()//ログインページは誰でも許可
        .antMatchers("/subadmin").permitAll()
        .antMatchers("/admin/**").hasRole("ADMIN")   //管理者画面は管理者のみ許可     
        .antMatchers("/subadmin/**").hasRole("SUB_ADMIN"));     //サブ管理者画面は管理者・サブ管理者のみ許可     
        
              
        //ログインの設定(管理者)
        http.formLogin(login -> login.loginPage("/admin")                       // ログインページ
        .loginProcessingUrl("/authenticate")       // フォームのPOST先URL．認証処理を実行する
        .usernameParameter("name")                  // ユーザ名に該当するリクエストパラメタ
        .passwordParameter("password")             // パスワードに該当するリクエストパラメタ
        .successHandler(authenticationSuccessHandler)
        .failureUrl("/admin"));               // 失敗時のページ

        // // //ログインの設定(サブ管理者)
        // http.formLogin(login -> login.loginPage("/subadmin")                       // ログインページ
        // .loginProcessingUrl("/authenticate/sub")       // フォームのPOST先URL．認証処理を実行する
        // .usernameParameter("name")                  // ユーザ名に該当するリクエストパラメタ
        // .passwordParameter("password")             // パスワードに該当するリクエストパラメタ
        // .defaultSuccessUrl("/subadmin/login", true)  // 成功時のページ (trueは以前どこにアクセスしてもここに遷移する設定)
        // .failureUrl("/loginerror"));  
            
        //ログアウトの設定
        http.logout(logout -> logout.logoutUrl("/logout")                      //ログアウトのURL
        .logoutSuccessUrl("/admin")         //ログアウト完了したらこのページへ
        .deleteCookies("JSESSIONID")               //クッキー削除
        .invalidateHttpSession(true)               //セッション情報消去
        .permitAll());                              //ログアウトはいつでもアクセスできる
    }
    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        //  (4) 認証方法の実装の設定を行う
        auth.userDetailsService(userService)   //認証は予約アプリのユーザサービスを使う
            .passwordEncoder(passwordEncoder); //パスワードはアプリ共通のものを使う
        // ついでにここで管理者ユーザを登録しておく
        userService.registerAdmin(adminConfig.getUsername(), adminConfig.getPassword());
    }
}
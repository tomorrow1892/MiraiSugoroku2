package ksp.group3.miraiSugoroku.security;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ksp.group3.miraiSugoroku.exception.MiraiSugorokuException;
import ksp.group3.miraiSugoroku.security.User.Role;
import lombok.AllArgsConstructor;

/**
 * ユーザサービス．管理者ユーザ，マス承認権限ユーザを生成する．
 * Spring securityの UserDetailsServiceを実走している
 */
@AllArgsConstructor
@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository users; // ユーザリポジトリ
    @Autowired
    PasswordEncoder passwordEncoder; // システム共通のパスワードエンコーダ（ユーザ作成・更新時に利用）

    /**
     * ユーザを新規作成
     * 
     * @param user
     * @return
     */
    public User createUser(UserForm form) {
        if (users.existsByName(form.getName())) {
            throw new MiraiSugorokuException(MiraiSugorokuException.USER_ALREADY_EXIST, "User already exists");
        }
        // パスワードを暗号化
        String pass = passwordEncoder.encode(form.getPassword());
        return users.save(new User(null, pass, form.getName(), new Date(), Role.SUB_ADMIN));
    }

    /**
     * 全ユーザ取得
     * 
     * @return
     */
    public List<User> getAllUsers() {
        ArrayList<User> list = new ArrayList<>();
        Iterable<User> all = users.findAll();
        all.forEach(list::add);
        return list;
    }

    /**
     * ユーザをユーザ名で取得
     * 
     * @param uid
     * @return
     */
    public User getUser(String name) {
        User user = users.findByName(name)
                .orElseThrow(() -> new MiraiSugorokuException(MiraiSugorokuException.USER_NOT_FOUND,
                        name + ": No such user"));
        return user;
    }

    /**
     * ユーザをIDで取得
     * 
     * @param uid
     * @return
     */
    public User getUserById(Long uid) {
        User user = users.findByUid(uid)
                .orElseThrow(() -> new MiraiSugorokuException(MiraiSugorokuException.USER_NOT_FOUND,
                        uid + ": No such user"));
        return user;
    }

    /**
     * ユーザを更新
     * 
     * @param name
     * @param user
     * @return
     */
    public User updateUser(String name, User user) {
        User orig = getUser(name);
        if (!name.equals(user.getName())) {
            throw new MiraiSugorokuException(MiraiSugorokuException.INVALID_USER_UPDATE,
                    name + ": uid cannot be updated");
        }
        // パスワードを暗号化
        String pass = passwordEncoder.encode(user.getPassword());
        user.setPassword(pass);
        user.setCreatedAt(orig.getCreatedAt());
        return users.save(user);
    }

    /**
     * ユーザを消去する
     * 
     * @param name
     */
    public void deleteUser(String name) {
        User user = getUser(name);
        users.delete(user);
    }

    /**
     * ユーザIDで検索して，ユーザ詳細を返すサービス．Spring Securityから呼ばれる
     */
    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        // レポジトリからユーザを検索する
        User user = users.findByName(name)
                .orElseThrow(() -> new MiraiSugorokuException(MiraiSugorokuException.USER_NOT_FOUND,
                        name + ": No such user"));

        // ユーザのロールに応じて，権限を追加していく
        List<GrantedAuthority> authorities = new ArrayList<>(); // 権限リスト
        User.Role role = user.getRole(); // ユーザの権限
        switch (role) {
            // 教員の時は，教員権限を追加
            case SUB_ADMIN:
                // 権限文字列には，ROLE_を付けないといけない．
                authorities.add(new SimpleGrantedAuthority("ROLE_SUB_ADMIN"));
                break;

            // 管理者の時は，教員権限と管理者権限を両方追加
            case ADMIN:
                // 権限文字列には，ROLE_を付けないといけない．
                authorities.add(new SimpleGrantedAuthority("ROLE_SUB_ADMIN"));
                authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                break;

            // それ以外はエラー
            default:
                throw new MiraiSugorokuException(MiraiSugorokuException.INVALID_USER_ROLE,
                        role + ": Invalid user role");
        }

        // userからユーザセッション(UserDetailsの実装)を作成して返す
        UserSession userSession = new UserSession(user, authorities);

        return userSession;
    }

    /**
     * 管理者を登録する
     * 
     * @param name
     * @param password
     */
    @Transactional
    public void registerAdmin(String name, String password) {
        if (!users.existsByName(name)) {
            User user = new User(null, passwordEncoder.encode(password), name, new Date(), Role.ADMIN);
            users.save(user);
        }

    }
}
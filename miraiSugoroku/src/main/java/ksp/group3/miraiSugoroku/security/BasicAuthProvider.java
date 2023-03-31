// package ksp.group3.miraiSugoroku.security;

// import org.springframework.security.authentication.AuthenticationProvider;
// import org.springframework.security.authentication.BadCredentialsException;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.AuthenticationException;

// /**
//  * BASIC 認証の簡易実装クラス。
//  */
// public class BasicAuthProvider implements AuthenticationProvider {

//   /**
//    * 認証を実行する。
//    *
//    * @param authentication 認証リクエスト情報
//    * @return クレデンシャル情報を含む認証済みの情報
//    * @throws AuthenticationException 認証に失敗した場合
//    */
//   @Override
//   public Authentication authenticate(Authentication authentication) throws AuthenticationException {

//     System.out.println("DemoBasicAuthProvider#authenticate");
//     System.out.println(authentication.getClass().getName());

//     // 入力されたユーザー名とパスワードを取得
//     String inputName = authentication.getName();
//     String inputPass = authentication.getCredentials().toString();

//     String name = "john-doe"; // 正しいユーザー名
//     String pass = "12345678"; // 正しいパスワード

//     // ユーザー名とパスワードが正しいかチェック
//     if (inputName.equals(name) && inputPass.equals(pass)) {
//       System.out.println("ユーザー名とパスワードが正しい");
//       // ユーザー名とパスワードを表現する認証済みオブジェクトを返す
//       return new UsernamePasswordAuthenticationToken(inputName, inputPass, authentication.getAuthorities());
//     } else {
//       System.out.println("ユーザー名やパスワードが正しくない");
//       throw new BadCredentialsException("ユーザー名やパスワードが正しくない");
//     }
//   }

//   /**
//    * このクラスが引数に指定された認証リクエスト情報をサポートするときは true を返す。
//    *
//    * @param authentication Authentication 型のクラスオブジェクト
//    * @return 引数に指定された認証リクエスト情報をサポートするか
//    */
//   @Override
//   public boolean supports(Class<?> authentication) {

//     System.out.println("DemoBasicAuthProvider#supports");
//     System.out.println(authentication.getName());

//     // UsernamePasswordAuthenticationToken として扱える認証リクエストであれば true を返す
//     return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
//   }
// }

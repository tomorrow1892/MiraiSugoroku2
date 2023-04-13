package ksp.group3.miraiSugoroku.controller;

import ksp.group3.miraiSugoroku.exception.MiraiSugorokuException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class MiraiSugorokuErrorHandler {
    /**
     * アプリケーション例外をハンドルし，エラーページを表示する
     */
    @ExceptionHandler(MiraiSugorokuException.class)
    public String handleToDoException(MiraiSugorokuException ex, Model model) {
        // model.addAttribute("advice", "アプリケーション例外が発生しました．メッセージを確認してください");
        // model.addAttribute("exception", ex);
        String message;

        switch (ex.getCode()) {
            case MiraiSugorokuException.NO_SUCH_USER:
                message = "参加イベントかユーザIDが間違っています。戻るボタンを押して、もういちど入力してください。";
                break;
            case MiraiSugorokuException.ADMIN_PASSWORD_WRONG:
                message = "ログインIDが間違っています。戻るボタンを押して、もういちど入力してください。";
                break;
            case MiraiSugorokuException.USED_LOGINID:
                message = "このイベント内で既に使用されているログインIDです。別のログインIDを使用してください。";
                break;
            case MiraiSugorokuException.PASSWORD_ERROR:
                message = "マス承認管理者のIDとパスワードは半角英数16文字以下にしてください。";
                break;
            case MiraiSugorokuException.CREATE_SQUARE_NOT_PERMITTED:
                message = "イベント期間が終了しているのでマスを作ることは出来ません．";
                break;
                case MiraiSugorokuException.USER_ALREADY_EXIST:
                message = "マス承認管理者のIDが既に使われています。";
                break;
            case 11:
                message = "パスワードが間違っています。戻るボタンを押して、再度入力してください。";
                break;
            default:
                message = "エラーが発生しました．";
                model.addAttribute("exception", ex);
        }

        model.addAttribute("advice", message);
        log.error(ex.getMessage());
        return "error";
    }

    /**
     * その他の例外をハンドルし，エラーページを表示する
     */
    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {
        model.addAttribute("advice", "エラーが発生しました．戻るボタンを押して、前のページに戻ってください");
        model.addAttribute("exception", ex);
        log.error(ex.getMessage(), ex.getCause());
        return "error";
    }
}
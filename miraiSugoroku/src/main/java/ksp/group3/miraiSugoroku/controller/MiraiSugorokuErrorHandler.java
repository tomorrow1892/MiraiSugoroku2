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
//        model.addAttribute("advice", "アプリケーション例外が発生しました．メッセージを確認してください");
//        model.addAttribute("exception", ex);
        String message;

        switch (ex.getCode()) {
            case MiraiSugorokuException.NO_SUCH_USER:
                message = "参加イベントかユーザIDが間違っています。ブラウザバックし、再度入力してください。"; break;
            case 11:
                message = "パスワードが間違っています。ブラウザバックし、再度入力してください。"; break;
            default:
                message = "アプリケーション例外が発生しました．メッセージを確認してください";
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
        model.addAttribute("advice", "エラーが発生しました．前のページに戻ってください");
        model.addAttribute("exception", ex);
        log.error(ex.getMessage(), ex.getCause());
        return "error";
    }
}
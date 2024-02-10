package com.techacademy.controller;

import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult; // 追加
import org.springframework.validation.annotation.Validated; // 追加
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.techacademy.entity.User;
import com.techacademy.service.UserService;

@Controller
@RequestMapping("user")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    // 一覧画面を表示
    @GetMapping("/list")
    public String getList(Model model) {
        // 全件検索結果をModelに登録
        model.addAttribute("userlist", service.getUserList());
        // user/list.htmlに画面遷移
        return "user/list";
    }

    // User登録画面を表示
    @GetMapping("/register")
    public String getRegister(@ModelAttribute User user) {
        // User登録画面に遷移
        return "user/register";
    }

    // User登録処理
    @PostMapping("/register")
    public String postRegister(@Validated User user, BindingResult res, Model model) {
        if (res.hasErrors()) {
            // エラーあり
            return getRegister(user);
        }
        // User登録
        service.saveUser(user);
        // 一覧画面にリダイレクト
        return "redirect:/user/list";
    }

    // User更新画面を表示
    @GetMapping("/update/{id}/")
    public String getUser(@PathVariable("id") Integer id, Model model, User user) {

        // idがnullかどうか判断
        if (id != null) {
            // idがnullではない場合、一覧画面から遷移し、Modelに登録する
            model.addAttribute("user", service.getUser(id));
        } else {
            // idがnullの場合、postUser()から遷移し、ModelにはpostUser()から渡された引数のuserをセットする
            model.addAttribute("user", user);
        }
        // User更新画面に遷移
        return "user/update";
    }

    // User更新処理
    @PostMapping("/update/{id}/")
    public String postUser(@PathVariable("id") Integer id, @Validated User user, BindingResult res, Model model) {

        // エラーがあるかどうか判断
        if (res.hasErrors()) {
            // エラーありの場合、getUserメソッドを呼び出す。引数にnullを設定する
            return getUser(null, model, user);
        }
        // エラー無しの場合

        // User登録
        service.saveUser(user);
        // 一覧画面にリダイレクト
        return "redirect:/user/list";
    }

    // User削除処理
    @PostMapping(path = "list", params = "deleteRun")
    public String deleteRun(@RequestParam(name = "idck") Set<Integer> idck, Model model) {
        // Userを一括削除
        service.deleteUser(idck);
        // 一覧画面にリダイレクト
        return "redirect:/user/list";
    }
}
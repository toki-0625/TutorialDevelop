package com.techacademy.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.techacademy.entity.User;


@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class UserControllerTest2 {
    private MockMvc mockMvc;

    private final WebApplicationContext webApplicationContext;
   
     UserControllerTest2(WebApplicationContext context) {
        this.webApplicationContext = context;
    }

    @BeforeEach
    void beforeEach() {
        // Spring Securityを有効にする
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity()).build();
    }    
    
    @Test
    @DisplayName("User更新画面")
    @WithMockUser
    void testGetList() throws Exception {
        // HTTPリクエストに対するレスポンスの検証
        MvcResult result = mockMvc.perform(get("/user/list")) // URLにアクセス
            .andExpect(status().isOk()) // ステータスを確認
            .andExpect(model().attributeExists("userlist")) // Modelの内容を確認
            .andExpect(model().hasNoErrors()) // Modelのエラー有無の確認
            .andExpect(view().name("user/list")) // viewの確認
            .andReturn(); // 内容の取得

        // userの検証
        // Modelからuserを取り出す。リスト化
        List<User> userlist = (List<User>)result.getModelAndView().getModel().get("userlist");
        //userlistの1番目 (0)から参照する
        User user = userlist.get(0);
        //リスト化 (要素数３)
        assertEquals(userlist.size(), 3);
        //ユーザーIDの取得
        assertEquals(user.getId(), 1);
        //ユーザー名の取得
        assertEquals(user.getName(), "キラメキ太郎");
        
    }
}
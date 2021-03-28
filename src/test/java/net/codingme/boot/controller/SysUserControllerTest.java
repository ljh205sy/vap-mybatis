//package net.codingme.boot.controller;
//
//import net.codingme.boot.domain.SysUser;
//import net.codingme.boot.service.SysUserService;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.context.WebApplicationContext;
//
//import java.util.Date;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
///**
// * @author liujinhui
// * date 2021/3/26 11:50
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class SysUserControllerTest {
//
//    @Autowired
//    private SysUserService sysUserService;
//
//    @Autowired
//    private WebApplicationContext wac;
//
//    private MockMvc mockMvc;
//
//    @Before
//    public void setup() {
//        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
//    }
//
//    @Test
//    @Transactional
//    @Rollback(false)
//    public void whenCreateUser() {
//        SysUser user = new SysUser();
//        user.setName("liujinhui");
//        user.setPassword("123456");
//        sysUserService.insert(user);
//    }
//
//    @Test
//    @Transactional
//    @Rollback(false)
//    public void whenCreateUserSuccess() throws Exception {
//        Date date = new Date();
//        System.out.println(date.getTime());
//        String content = "{\"username\":\"test123qwe\",\"name\":\"那么部门2222\",\"password\":\"password1121\"}";
//        String reuslt = mockMvc.perform(post("/user").contentType("application/json;charset=UTF-8")
//                .content(content))
//                .andExpect(status().isOk())
////                .andExpect(jsonPath("$.id").value("1"))
//                .andReturn().getResponse().getContentAsString();
//        System.out.println(reuslt);
//    }
//}
//package com.github.cache.controller;
//
//import com.alibaba.fastjson.JSON;
//import lombok.SneakyThrows;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import javax.annotation.Resource;
//
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
///**
// * <p>
// * 创建时间为 10:42 2019-07-10
// * 项目名称 spring-boot-cache
// * </p>
// *
// * @author 石少东
// * @version 0.0.1
// * @since 0.0.1
// */
//@DirtiesContext
//@AutoConfigureMockMvc
//@ActiveProfiles("junit")
//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class UserSimpleControllerTest {
//
//    /**
//     * 模拟mvc测试对象
//     */
//    @Resource
//    private MockMvc mockMvc;
//
//    @Resource
//    private IUserSimpleRepository repository;
//
//
//    @Test
//    @SneakyThrows(Exception.class)
//    public void getUserSimpleByName() {
//        String result = mockMvc.perform(MockMvcRequestBuilders.get("/simple/1111"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andReturn()
//                .getResponse()
//                .getContentAsString();
//        UserSimpleDO userSimpleDO = JSON.parseObject(result, UserSimpleDO.class);
//        Assert.assertNull("用户名为 null", userSimpleDO.getName());
//    }
//
//    @Test
//    @SneakyThrows(Exception.class)
//    public void createUserSimple() {
//        String result = mockMvc.perform(MockMvcRequestBuilders.post("/simple")
//                .contentType(MediaType.APPLICATION_JSON_UTF8)
//                .content(getUserSimpleDO().toString()))
//                .andExpect(status().isOk())
//                .andReturn()
//                .getResponse()
//                .getContentAsString();
//        UserSimpleDO userSimpleDO = JSON.parseObject(result, UserSimpleDO.class);
//        Assert.assertEquals("用户名相同", getUserSimpleDO().getName(), userSimpleDO.getName());
//
//        repository.deleteUserSimpleDOByNameEquals("name1");
//    }
//
//    @Test
//    @SneakyThrows(Exception.class)
//    public void updateUserSimple() {
//        UserSimpleDO userSimpleDO1 = repository.save(getUserSimpleDO());
//        userSimpleDO1.setName("name2");
//
//        String result2 = mockMvc.perform(MockMvcRequestBuilders.put("/simple")
//                .contentType(MediaType.APPLICATION_JSON_UTF8)
//                .content(userSimpleDO1.toString()))
//                .andExpect(status().isOk())
//                .andReturn()
//                .getResponse()
//                .getContentAsString();
//
//        UserSimpleDO userSimpleDO2 = JSON.parseObject(result2, UserSimpleDO.class);
//        Assert.assertEquals("用户名相同", userSimpleDO2.getName(), "name2");
//        repository.deleteUserSimpleDOByNameEquals("name2");
//    }
//
//    @Test
//    @SneakyThrows(Exception.class)
//    public void deleteUserSimpleByName() {
//        String result = mockMvc.perform(MockMvcRequestBuilders.delete("/simple/name1")
//                .contentType(MediaType.APPLICATION_JSON_UTF8))
//                .andExpect(status().isNotFound())
//                .andReturn()
//                .getResponse()
//                .getContentAsString();
//        Assert.assertEquals("返回内容为空", "", result);
//    }
//
//
//    private UserSimpleDO getUserSimpleDO() {
//        return UserSimpleDO.builder()
//                .name("name1")
//                .build();
//    }
//
//}
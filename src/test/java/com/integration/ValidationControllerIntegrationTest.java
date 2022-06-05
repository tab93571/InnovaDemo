package com.integration;

import com.DemoApplication;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.request.ValidateRequest;
import com.response.Response;
import com.util.MockMvcUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.constant.MappingConstant.VALIDATE;
import static com.enums.ResponseEnum.Fail;
import static com.enums.ResponseEnum.Success;
import static com.enums.ResponseEnum.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
@AutoConfigureMockMvc
public class ValidationControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private MockMvcUtil mockMvcUtil;

    //------------------ validate------------------------------


    @Test
    public void testValidateSuccess() throws Exception {
        ValidateRequest request = new ValidateRequest("aa123");

        String returnString = mockMvcUtil.postMethod(VALIDATE,request);

        Response response = mapper.readValue(returnString,Response.class);

        Assert.assertEquals(Success.getCode(),response.getCode());
    }

    @Test
    public void testValidateFailNullOrEmpty() throws Exception {
        ValidateRequest request = new ValidateRequest();

        String returnString = mockMvcUtil.postMethod(VALIDATE,request);

        TypeReference reference = new TypeReference<Response<List<Response>>>() {};
        Response<List<Response>> response = mapper.readValue(returnString,reference);

        Assert.assertEquals(Fail.getCode(),response.getCode());
        Assert.assertEquals(1,response.getData().size());
        Assert.assertEquals(ValidationPasswordNullOrEmptyError.getCode(),response.getData().get(0).getCode());
    }

    /**
     * ValidationPasswordLengthError
     * ValidationPasswordNoLowerCaseLetters
     * ValidationPasswordNoNumericalDigits
     * ValidationPasswordCharacterNotAllowed
     * ValidationSequenceOfCharactersFollowedByTheSameSequence
     * @throws Exception
     */

    @Test
    public void testValidateFailLengthErrorAndCharacterNotMatchedAndNoLowercaseCharacterNoNumberDigits() throws Exception {
        ValidateRequest request = new ValidateRequest("ABAB");

        String returnString = mockMvcUtil.postMethod(VALIDATE,request);

        TypeReference reference = new TypeReference<Response<List<Response>>>() {};
        Response<List<Response>> response = mapper.readValue(returnString,reference);

        Assert.assertEquals(Fail.getCode(),response.getCode());
        Assert.assertEquals(1,response.getData().size());
        Assert.assertEquals(ValidationPasswordNullOrEmptyError.getCode(),response.getData().get(0).getCode());
    }





    //------------------ get------------------------------




    //------------------ add------------------------------



    //------------------ delete------------------------------
}

package com.unittest.service;

import com.DemoApplication;
import static com.enums.ResponseEnum.*;

import com.request.AddValidateServiceRequest;
import com.request.DeleteValidateServiceRequest;
import com.request.ValidateRequest;
import com.response.Response;
import com.service.GeneralValidateService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
@AutoConfigureMockMvc
public class GeneralValidateServiceImplUnitTest {

    @Autowired
    GeneralValidateService generalValidateService;

    @Test
    public void testValidatePassword_Null(){
        ValidateRequest request = new ValidateRequest();
        List<Response> responseList = generalValidateService.validate(request);

        List<Integer> actualResponseListCode = responseList.stream().map(response ->  response.getCode()).collect(Collectors.toList());
        List<Integer> expectResponseListCode = Arrays.asList(ValidationPasswordNullOrEmptyError.getCode());

        Assert.assertEquals(expectResponseListCode.size(),actualResponseListCode.size());
        Assert.assertTrue(actualResponseListCode.containsAll(expectResponseListCode));
    }
    @Test
    public void testValidatePasswordEmpty(){
        ValidateRequest request = new ValidateRequest("");
        List<Response> responseList = generalValidateService.validate(request);

        List<Integer> actualResponseListCode = responseList.stream().map(response ->  response.getCode()).collect(Collectors.toList());
        List<Integer> expectResponseListCode = Arrays.asList(ValidationPasswordNullOrEmptyError.getCode());

        Assert.assertEquals(expectResponseListCode.size(),actualResponseListCode.size());
        Assert.assertTrue(actualResponseListCode.containsAll(expectResponseListCode));
    }
    /**
     * bean exists in the validationSet
     */
    @Test
    public void testDeleteValidateServiceInUseSuccess(){
        Response responseGet = generalValidateService.getValidateServiceNameInUse();
        List<String> serviceNames = (List<String>)(responseGet.getData());

        Response responseDelete = generalValidateService.deleteValidateServiceInUse(new DeleteValidateServiceRequest(serviceNames.get(0)));
        Assert.assertEquals(Success.getCode(),responseDelete.getCode());

    }

    /**
     * bean doesn't exist in the validationSet
     */
    @Test
    public void testDeleteValidateServiceInUseNotFound(){

        Response responseDelete = generalValidateService.deleteValidateServiceInUse(new DeleteValidateServiceRequest("aaa"));
        Assert.assertEquals(ValidationCanNotFindService.getCode(),responseDelete.getCode());
    }

    /**
     * bean doesn't exist in the spring container
     */
    @Test
    public void testAddValidateServiceInUseNotExistInSpringContainer(){

        Response responseAdd = generalValidateService.addValidateService(new AddValidateServiceRequest("aaa"));
        Assert.assertEquals(ValidationCanNotFindService.getCode(),responseAdd.getCode());
    }

    /**
     * the type of bean isn't matched
     */
    @Test
    public void testAddValidateServiceBeanTypeNotMatched(){

        Response responseAdd = generalValidateService.addValidateService(new AddValidateServiceRequest("validationController"));
        Assert.assertEquals(ValidationServiceTypeNotMatch.getCode(),responseAdd.getCode());
    }
}

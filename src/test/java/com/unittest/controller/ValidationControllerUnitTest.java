package com.unittest.controller;

import com.DemoApplication;
import static com.enums.ResponseEnum.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.request.AddValidateServiceRequest;
import com.request.DeleteValidateServiceRequest;
import com.request.ValidateRequest;
import com.response.Response;
import com.service.GeneralValidateService;
import com.util.MockMvcUtil;
import com.util.ResponseUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import static org.mockito.ArgumentMatchers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import static com.constant.MappingConstant.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
@AutoConfigureMockMvc
public class ValidationControllerUnitTest {


    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private GeneralValidateService service ;

    @Autowired
    private MockMvcUtil mockMvcUtil;


    @Test
    public void testValidateSuccess() throws Exception {

        ValidateRequest request = new ValidateRequest();
        //if without any() we need to implements hashcode() and equals()
        Mockito.when(service.validate(any(ValidateRequest.class))).thenReturn(new ArrayList());


        String returnString = mockMvcUtil.postMethod(VALIDATE,request);

        Response response = mapper.readValue(returnString,Response.class);

        Assert.assertEquals(Success.getCode(),response.getCode());
    }

    @Test
    public void testValidateFail() throws Exception {

        ValidateRequest request = new ValidateRequest();
        List<Response> responseListExpect = Arrays.asList(ResponseUtil.error(ValidationPasswordCharacterNotAllowed),ResponseUtil.error(ValidationPasswordNoLowerCaseLetters));
        Mockito.when(service.validate(any(ValidateRequest.class))).thenReturn(responseListExpect);

        String returnString = mockMvcUtil.postMethod(VALIDATE,request);

        TypeReference reference = new TypeReference<Response<List<Response>>>() {};
        Response<List<Response>> response = mapper.readValue(returnString,reference);


        Assert.assertEquals(Fail.getCode(),response.getCode());

        List<Integer> actualResponseListCode = response.getData().stream().map(res ->  res.getCode()).collect(Collectors.toList());
        List<Integer> expectResponseListCode = responseListExpect.stream().map(res ->  res.getCode()).collect(Collectors.toList());

        Assert.assertEquals(expectResponseListCode.size(),actualResponseListCode.size());
        Assert.assertTrue(actualResponseListCode.containsAll(expectResponseListCode));
    }
    @Test
    public void testGetValidateServiceNameInUse() throws Exception {

        List<String> data = Arrays.asList("a","b","c");
        Mockito.when(service.getValidateServiceNameInUse()).thenReturn(ResponseUtil.success(data));

        String returnString = mockMvcUtil.getMethod(GETVALIDATESERVICESINUSE);


        Response response = mapper.readValue(returnString,Response.class);
        List<String>responseData = (List<String>)response.getData();
        Assert.assertEquals(Success.getCode(),response.getCode());
        for(int i = 0 ; i < data.size() ; i ++){
            Assert.assertEquals(data.get(i),responseData.get(i));
        }
    }

    /**
     * bean exists in the validationSet
     */
    @Test
    public void testDeleteValidateServiceInUseSuccess() throws Exception {

        DeleteValidateServiceRequest request = new DeleteValidateServiceRequest();

        Mockito.when(service.deleteValidateServiceInUse(any(DeleteValidateServiceRequest.class))).thenReturn(ResponseUtil.success());

        String returnString = mockMvcUtil.deleteMethod(DELETEVALIDATESERVICEINUSE,request);


        Response response = mapper.readValue(returnString,Response.class);

        Assert.assertEquals(Success.getCode(),response.getCode());
    }

    /**
     * bean doesn't exist in the validationSet
     */
    @Test
    public void testDeleteValidateServiceInUseNotFound() throws Exception {

        DeleteValidateServiceRequest request = new DeleteValidateServiceRequest();

        Mockito.when(service.deleteValidateServiceInUse(any(DeleteValidateServiceRequest.class))).thenReturn(ResponseUtil.error(ValidationCanNotFindService));

        String returnString = mockMvcUtil.deleteMethod(DELETEVALIDATESERVICEINUSE,request);

        Response response = mapper.readValue(returnString,Response.class);

        Assert.assertEquals(ValidationCanNotFindService.getCode(),response.getCode());
    }

    /**
     * bean doesn't exist in the spring container
     */
    @Test
    public void testAddValidateServiceInUseNotExistInSpringContainer() throws Exception {

        AddValidateServiceRequest request = new AddValidateServiceRequest();

        Mockito.when(service.addValidateService(any(AddValidateServiceRequest.class))).thenReturn(ResponseUtil.error(ValidationCanNotFindService));

        String returnString = mockMvcUtil.postMethod(ADDVALIDATESERVICE,request);

        Response response = mapper.readValue(returnString,Response.class);
        Assert.assertEquals(ValidationCanNotFindService.getCode(),response.getCode());

    }

    /**
     * the type of bean isn't matched
     */
    @Test
    public void testAddValidateServiceBeanTypeNotMatched() throws Exception {

        AddValidateServiceRequest request = new AddValidateServiceRequest();

        Mockito.when(service.addValidateService(any(AddValidateServiceRequest.class))).thenReturn(ResponseUtil.error(ValidationServiceTypeNotMatch));

        String returnString = mockMvcUtil.postMethod(ADDVALIDATESERVICE,request);

        Response response = mapper.readValue(returnString,Response.class);
        Assert.assertEquals(ValidationServiceTypeNotMatch.getCode(),response.getCode());
    }

}

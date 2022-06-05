package com.unittest.service;

import com.DemoApplication;
import com.constant.ValidationConstant;
import static com.enums.ResponseEnum.*;
import com.request.ValidateRequest;
import com.response.Response;
import com.service.impl.ValidatePasswordLengthService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
@AutoConfigureMockMvc
public class ValidatePasswordLengthServiceUnitTest {
    @Autowired
    ValidatePasswordLengthService validatePasswordLengthService;

    /**
     * Password length is under the lower bound
     * the length of testString will be LENGTHLOWERBOUND - 1
     */

    @Test
    public void testValidatePasswordLengthUnderLowerBound(){
        StringBuilder testStringBuilder = new StringBuilder("");

        for(int i = 1 ; i < ValidationConstant.LENGTHLOWERBOUND ; i ++){
            testStringBuilder.append("a");
        }
        ValidateRequest request = new ValidateRequest(testStringBuilder.toString());
        List<Response> responseList = new ArrayList<>();
        validatePasswordLengthService.validate(request,responseList);

        List<Integer> actualResponseListCode = responseList.stream().map(response ->  response.getCode()).collect(Collectors.toList());
        List<Integer> expectResponseListCode = Arrays.asList(ValidationPasswordLengthError.getCode());

        Assert.assertEquals(expectResponseListCode.size(),actualResponseListCode.size());
        Assert.assertTrue(actualResponseListCode.containsAll(expectResponseListCode));

    }

    /**
     * Password length is over the upper bound
     * the length of testString will be LENGTHUPPERBOUND + 1
     */

    @Test
    public void testValidatePasswordLengthOverUpperBound(){

        StringBuilder testStringBuilder = new StringBuilder("");

        for(int i = 0 ; i <= ValidationConstant.LENGTHUPPERBOUND ; i ++){
            testStringBuilder.append("a");
        }
        ValidateRequest request = new ValidateRequest(testStringBuilder.toString());
        List<Response> responseList = new ArrayList<>();
        validatePasswordLengthService.validate(request,responseList);

        List<Integer> actualResponseListCode = responseList.stream().map(response ->  response.getCode()).collect(Collectors.toList());
        List<Integer> expectResponseListCode = Arrays.asList(ValidationPasswordLengthError.getCode());

        Assert.assertEquals(expectResponseListCode.size(),actualResponseListCode.size());
        Assert.assertTrue(actualResponseListCode.containsAll(expectResponseListCode));

    }

    /**
     * the length of testString is LENGTHLOWERBOUND
     */

    @Test
    public void testValidatePassLengthLowerBound(){

        StringBuilder testStringBuilder = new StringBuilder("");

        for(int i = 0 ; i < ValidationConstant.LENGTHLOWERBOUND ; i ++){
            testStringBuilder.append("a");
        }

        ValidateRequest request = new ValidateRequest(testStringBuilder.toString());
        List<Response> responseList = new ArrayList<>();
        validatePasswordLengthService.validate(request,responseList);
        Assert.assertEquals(0,responseList.size());
    }

    /**
     * the length of testString is LENGTHUPPERBOUND
     */

    @Test
    public void testValidatePassLengthUpperBound(){

        StringBuilder testStringBuilder = new StringBuilder("");

        for(int i = 0 ; i < ValidationConstant.LENGTHUPPERBOUND ; i ++){
            testStringBuilder.append("a");
        }

        ValidateRequest request = new ValidateRequest(testStringBuilder.toString());
        List<Response> responseList = new ArrayList<>();
        validatePasswordLengthService.validate(request,responseList);
        Assert.assertEquals(0,responseList.size());
    }
}

package com.unittest.service;

import com.DemoApplication;

import static com.enums.ResponseEnum.*;
import com.request.ValidateRequest;
import com.response.Response;
import com.service.impl.ValidatePasswordCheckRepeatedSequenceService;
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
public class ValidatePasswordCheckRepeatedSequenceServiceUnitTest {

    @Autowired
    private ValidatePasswordCheckRepeatedSequenceService validatePasswordCheckRepeatedSequenceService;

    @Test
    public void testValidateSequenceOfCharactersFollowedByTheSameSequence(){
        ValidateRequest request = new ValidateRequest("1abcabc");
        List<Response> responseList = new ArrayList<>();
        validatePasswordCheckRepeatedSequenceService.validate(request,responseList);

        List<Integer> actualResponseListCode = responseList.stream().map(response ->  response.getCode()).collect(Collectors.toList());
        List<Integer> expectResponseListCode = Arrays.asList(ValidationSequenceOfCharactersFollowedByTheSameSequence.getCode());

        Assert.assertEquals(expectResponseListCode.size(),actualResponseListCode.size());
        Assert.assertTrue(actualResponseListCode.containsAll(expectResponseListCode));

    }

    @Test
    public void testValidatePass(){

        ValidateRequest request = new ValidateRequest("abcab");
        List<Response> responseList = new ArrayList<>();
        validatePasswordCheckRepeatedSequenceService.validate(request,responseList);
        Assert.assertEquals(0,responseList.size());
    }

    /**
     * aa1bb   "a" equals "a" but  the length is 1,
     * base on the conversion with manager, I define it not a sequence of characters
     * so in this case it will pass
     */

    @Test
    public void testValidatePassOneCharacter(){

        ValidateRequest request = new ValidateRequest("aa1bbcc");
        List<Response> responseList = new ArrayList<>();
        validatePasswordCheckRepeatedSequenceService.validate(request,responseList);
        Assert.assertEquals(0,responseList.size());
    }
}

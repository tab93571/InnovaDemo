package com.unittest.service;

import com.DemoApplication;
import com.request.ValidateRequest;
import com.response.Response;
import com.service.impl.ValidatePasswordCharacterService;
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
import static com.enums.ResponseEnum.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
@AutoConfigureMockMvc
public class ValidatePasswordCharacterServiceUnitTest {
    @Autowired
    private ValidatePasswordCharacterService validatePasswordCharacterService;

    /**
     * the password is without numerical digits
     *                 with  lowercase characters
     *                 without neither  numerical or lowercase
     */

    @Test
    public void testValidateNoNumericalDigits(){
        ValidateRequest request = new ValidateRequest("a");
        List<Response> responseList = new ArrayList<>();
        validatePasswordCharacterService.validate(request,responseList);

        List<Integer> actualResponseListCode = responseList.stream().map(response ->  response.getCode()).collect(Collectors.toList());
        List<Integer> expectResponseListCode = Arrays.asList(ValidationPasswordNoNumericalDigits.getCode());

        Assert.assertEquals(expectResponseListCode.size(),actualResponseListCode.size());
        Assert.assertTrue(actualResponseListCode.containsAll(expectResponseListCode));

    }

    /**
     * the password is with numerical digits
     *                 without  lowercase characters
     *                 without neither  numerical or lowercase
     */

    @Test
    public void testValidateNoLowercaseCharacters(){
        ValidateRequest request = new ValidateRequest("1");
        List<Response> responseList = new ArrayList<>();
        validatePasswordCharacterService.validate(request,responseList);

        List<Integer> actualResponseListCode = responseList.stream().map(response ->  response.getCode()).collect(Collectors.toList());
        List<Integer> expectResponseListCode = Arrays.asList(ValidationPasswordNoLowerCaseLetters.getCode());

        Assert.assertEquals(expectResponseListCode.size(),actualResponseListCode.size());
        Assert.assertTrue(actualResponseListCode.containsAll(expectResponseListCode));

    }

    /**
     * the password is with numerical digits
     *                 with  lowercase characters
     *                 with neither  numerical or lowercase
     */

    @Test
    public void testValidateCharactersNotAllowed(){
        ValidateRequest request = new ValidateRequest("aA1");
        List<Response> responseList = new ArrayList<>();
        validatePasswordCharacterService.validate(request,responseList);

        List<Integer> actualResponseListCode = responseList.stream().map(response ->  response.getCode()).collect(Collectors.toList());
        List<Integer> expectResponseListCode = Arrays.asList(ValidationPasswordCharacterNotAllowed.getCode());

        Assert.assertEquals(expectResponseListCode.size(),actualResponseListCode.size());
        Assert.assertTrue(actualResponseListCode.containsAll(expectResponseListCode));

    }

    /**
     * the password is without numerical digits
     *                 without  lowercase characters
     *                 with neither  numerical or lowercase
     */

    @Test
    public void testValidateWithoutNumericalDigitsAndLowercase(){
        ValidateRequest request = new ValidateRequest("A");
        List<Response> responseList = new ArrayList<>();
        validatePasswordCharacterService.validate(request,responseList);

        List<Integer> actualResponseListCode = responseList.stream().map(response ->  response.getCode()).collect(Collectors.toList());
        List<Integer> expectResponseListCode = Arrays.asList(ValidationPasswordNoLowerCaseLetters.getCode(),ValidationPasswordNoNumericalDigits.getCode(),ValidationPasswordCharacterNotAllowed.getCode());

        Assert.assertEquals(expectResponseListCode.size(),actualResponseListCode.size());
        Assert.assertTrue(actualResponseListCode.containsAll(expectResponseListCode));

    }

    /**
     * the password is without numerical digits
     *                   with  lowercase characters
     *                   with neither  numerical or lowercase
     */

    @Test
    public void testValidateWithoutNumericalWithLowercaseWithNotAllowedCharacters(){
        ValidateRequest request = new ValidateRequest("aA");
        List<Response> responseList = new ArrayList<>();
        validatePasswordCharacterService.validate(request,responseList);

        List<Integer> actualResponseListCode = responseList.stream().map(response ->  response.getCode()).collect(Collectors.toList());
        List<Integer> expectResponseListCode = Arrays.asList(ValidationPasswordNoNumericalDigits.getCode(),ValidationPasswordCharacterNotAllowed.getCode());

        Assert.assertEquals(expectResponseListCode.size(),actualResponseListCode.size());
        Assert.assertTrue(expectResponseListCode.containsAll(actualResponseListCode));
    }

    /**
     * the password is with numerical digits
     *                 without  lowercase characters
     *                 with neither  numerical or lowercase
     */

    @Test
    public void testValidateWithNumericalWithoutLowercaseWithNotAllowedCharacters(){
        ValidateRequest request = new ValidateRequest("1A");
        List<Response> responseList = new ArrayList<>();
        validatePasswordCharacterService.validate(request,responseList);

        List<Integer> actualResponseListCode = responseList.stream().map(response ->  response.getCode()).collect(Collectors.toList());
        List<Integer> expectResponseListCode = Arrays.asList(ValidationPasswordNoLowerCaseLetters.getCode(),ValidationPasswordCharacterNotAllowed.getCode());

        Assert.assertEquals(expectResponseListCode.size(),actualResponseListCode.size());
        Assert.assertTrue(expectResponseListCode.containsAll(actualResponseListCode));
    }

    /**
     *  the password is with either numerical digits or lowercase characters
     *  and at least one numerical digits and at least one lowercase characters
     *
     */

    @Test
    public void testValidatePass(){
        ValidateRequest request = new ValidateRequest("a2");
        List<Response> responseList = new ArrayList<>();
        validatePasswordCharacterService.validate(request,responseList);
        Assert.assertEquals(0,responseList.size());

    }
}

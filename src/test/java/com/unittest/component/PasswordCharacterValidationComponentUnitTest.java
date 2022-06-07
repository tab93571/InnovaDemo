package com.unittest.component;

import com.bo.CommonResult;
import com.bo.PasswordValidationResult;
import com.component.PasswordValidationComponent;
import com.request.ValidationRequest;
import com.component.impl.PasswordCharacterValidationComponent;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import static com.enums.ResponseEnum.*;
import static org.mockito.Mockito.mock;


public class PasswordCharacterValidationComponentUnitTest {

    private PasswordValidationComponent passwordValidationComponent = new PasswordCharacterValidationComponent();

    /**
     * the password is without numerical digits
     *                 with  lowercase characters
     *                 without neither  numerical digits nor lowercase characters
     */

    @Test
    public void testValidateNoNumericalDigits(){

        ValidationRequest request = new ValidationRequest("a");

        PasswordValidationResult result = passwordValidationComponent.validate(request);
        List<CommonResult> resultDetails = result.getDetails();

        List<Integer> actualResultDetailsCode = resultDetails.stream().map(detail ->  detail.getCode()).collect(Collectors.toList());
        List<Integer> expectResultDetailsCode = Arrays.asList(ValidationPasswordNoNumericalDigits.getCode());

        Assert.assertEquals(false,result.isSuccess());

        Assert.assertTrue(actualResultDetailsCode.containsAll(expectResultDetailsCode));
        Assert.assertTrue(expectResultDetailsCode.containsAll(actualResultDetailsCode));

    }

    /**
     * the password is with numerical digits
     *                 without  lowercase characters
     *                 without neither  numerical nor lowercase
     */

    @Test
    public void testValidateNoLowercaseCharacters(){

        ValidationRequest request = new ValidationRequest("1");

        PasswordValidationResult result = passwordValidationComponent.validate(request);
        List<CommonResult> resultDetails = result.getDetails();

        List<Integer> actualResultDetailsCode = resultDetails.stream().map(detail ->  detail.getCode()).collect(Collectors.toList());
        List<Integer> expectResultDetailsCode = Arrays.asList(ValidationPasswordNoLowerCaseLetters.getCode());

        Assert.assertEquals(false,result.isSuccess());

        Assert.assertTrue(actualResultDetailsCode.containsAll(expectResultDetailsCode));
        Assert.assertTrue(expectResultDetailsCode.containsAll(actualResultDetailsCode));

    }

    /**
     * the password is with numerical digits
     *                 with  lowercase characters
     *                 with neither  numerical nor lowercase
     */

    @Test
    public void testValidateCharactersNotAllowed(){

        ValidationRequest request = new ValidationRequest("aA1");

        PasswordValidationResult result = passwordValidationComponent.validate(request);
        List<CommonResult> resultDetails = result.getDetails();

        List<Integer> actualResultDetailsCode = resultDetails.stream().map(detail ->  detail.getCode()).collect(Collectors.toList());
        List<Integer> expectResultDetailsCode = Arrays.asList(ValidationPasswordCharacterNotAllowed.getCode());

        Assert.assertEquals(false,result.isSuccess());

        Assert.assertTrue(actualResultDetailsCode.containsAll(expectResultDetailsCode));
        Assert.assertTrue(expectResultDetailsCode.containsAll(actualResultDetailsCode));

    }

    /**
     * the password is without numerical digits
     *                 without  lowercase characters
     *                 with neither  numerical nor lowercase
     */

    @Test
    public void testValidateWithoutNumericalDigitsAndLowercase(){

        ValidationRequest request = new ValidationRequest("A");

        PasswordValidationResult result = passwordValidationComponent.validate(request);
        List<CommonResult> resultDetails = result.getDetails();

        List<Integer> actualResponseListCode = resultDetails.stream().map(detail ->  detail.getCode()).collect(Collectors.toList());
        List<Integer> expectResponseListCode = Arrays.asList(ValidationPasswordNoLowerCaseLetters.getCode(),ValidationPasswordNoNumericalDigits.getCode(),ValidationPasswordCharacterNotAllowed.getCode());


        Assert.assertEquals(false,result.isSuccess());

        Assert.assertEquals(expectResponseListCode.size(),actualResponseListCode.size());
        Assert.assertTrue(actualResponseListCode.containsAll(expectResponseListCode));

    }

    /**
     * the password is without numerical digits
     *                   with  lowercase characters
     *                   with neither numerical nor lowercase
     */

    @Test
    public void testValidateWithoutNumericalWithLowercaseWithNotAllowedCharacters(){

        ValidationRequest request = new ValidationRequest("aA");

        PasswordValidationResult result = passwordValidationComponent.validate(request);
        List<CommonResult> resultDetails = result.getDetails();

        List<Integer> actualResultDetailsCode = resultDetails.stream().map(detail ->  detail.getCode()).collect(Collectors.toList());
        List<Integer> expectResultDetailsCode = Arrays.asList(ValidationPasswordNoNumericalDigits.getCode(),ValidationPasswordCharacterNotAllowed.getCode());

        Assert.assertEquals(false,result.isSuccess());

        Assert.assertTrue(actualResultDetailsCode.containsAll(expectResultDetailsCode));
        Assert.assertTrue(expectResultDetailsCode.containsAll(actualResultDetailsCode));
    }

    /**
     * the password is with numerical digits
     *                 without  lowercase characters
     *                 with neither  numerical nor lowercase
     */

    @Test
    public void testValidateWithNumericalWithoutLowercaseWithNotAllowedCharacters(){

        ValidationRequest request = new ValidationRequest("1A");

        PasswordValidationResult result = passwordValidationComponent.validate(request);
        List<CommonResult> resultDetails = result.getDetails();

        List<Integer> actualResultDetailsCode = resultDetails.stream().map(detail ->  detail.getCode()).collect(Collectors.toList());
        List<Integer> expectResultDetailsCode = Arrays.asList(ValidationPasswordNoLowerCaseLetters.getCode(),ValidationPasswordCharacterNotAllowed.getCode());

        Assert.assertEquals(false,result.isSuccess());

        Assert.assertTrue(actualResultDetailsCode.containsAll(expectResultDetailsCode));
        Assert.assertTrue(expectResultDetailsCode.containsAll(actualResultDetailsCode));
    }

    /**
     *  the password is with either numerical digits or lowercase characters
     *  and at least one numerical digits and at least one lowercase characters
     *
     */

    @Test
    public void testValidatePass(){

        ValidationRequest request = new ValidationRequest("a2");

        PasswordValidationResult result = passwordValidationComponent.validate(request);
        List<CommonResult> resultDetails = result.getDetails();

        Assert.assertEquals(true,result.isSuccess());

        Assert.assertEquals(0,resultDetails.size());


    }
}

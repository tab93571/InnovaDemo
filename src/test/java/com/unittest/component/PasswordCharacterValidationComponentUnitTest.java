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


public class PasswordCharacterValidationComponentUnitTest {

    private PasswordValidationComponent passwordValidationComponent = new PasswordCharacterValidationComponent();


    @Test
    public void testValidateEmpty(){

        ValidationRequest request = new ValidationRequest("");

        PasswordValidationResult result = passwordValidationComponent.validate(request);
        List<CommonResult> resultDetails = result.getDetails();

        List<Integer> actualResultDetailCodes = resultDetails.stream().map(detail ->  detail.getCode()).collect(Collectors.toList());
        List<Integer> expectResultDetailCodes = Arrays.asList(ValidationPasswordNullOrEmptyError.getCode());

        Assert.assertEquals(false,result.isSuccess());

        Assert.assertTrue(actualResultDetailCodes.containsAll(expectResultDetailCodes));
        Assert.assertTrue(expectResultDetailCodes.containsAll(actualResultDetailCodes));

    }

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

        List<Integer> actualResultDetailCodes = resultDetails.stream().map(detail ->  detail.getCode()).collect(Collectors.toList());
        List<Integer> expectResultDetailCodes = Arrays.asList(ValidationPasswordNoNumericalDigits.getCode());

        Assert.assertEquals(false,result.isSuccess());

        Assert.assertTrue(actualResultDetailCodes.containsAll(expectResultDetailCodes));
        Assert.assertTrue(expectResultDetailCodes.containsAll(actualResultDetailCodes));

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

        List<Integer> actualResultDetailCodes = resultDetails.stream().map(detail ->  detail.getCode()).collect(Collectors.toList());
        List<Integer> expectResultDetailCodes = Arrays.asList(ValidationPasswordNoLowerCaseLetters.getCode());

        Assert.assertEquals(false,result.isSuccess());

        Assert.assertTrue(actualResultDetailCodes.containsAll(expectResultDetailCodes));
        Assert.assertTrue(expectResultDetailCodes.containsAll(actualResultDetailCodes));

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

        List<Integer> actualResultDetailCodes = resultDetails.stream().map(detail ->  detail.getCode()).collect(Collectors.toList());
        List<Integer> expectResultDetailCodes = Arrays.asList(ValidationPasswordCharacterNotAllowed.getCode());

        Assert.assertEquals(false,result.isSuccess());

        Assert.assertTrue(actualResultDetailCodes.containsAll(expectResultDetailCodes));
        Assert.assertTrue(expectResultDetailCodes.containsAll(actualResultDetailCodes));

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

        List<Integer> actualResultDetailCodes = resultDetails.stream().map(detail ->  detail.getCode()).collect(Collectors.toList());
        List<Integer> expectResultDetailCodes = Arrays.asList(ValidationPasswordNoLowerCaseLetters.getCode(),ValidationPasswordNoNumericalDigits.getCode(),ValidationPasswordCharacterNotAllowed.getCode());

        Assert.assertEquals(false,result.isSuccess());

        Assert.assertTrue(actualResultDetailCodes.containsAll(expectResultDetailCodes));
        Assert.assertTrue(expectResultDetailCodes.containsAll(actualResultDetailCodes));

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

        List<Integer> actualResultDetailCodes = resultDetails.stream().map(detail ->  detail.getCode()).collect(Collectors.toList());
        List<Integer> expectResultDetailCodes = Arrays.asList(ValidationPasswordNoNumericalDigits.getCode(),ValidationPasswordCharacterNotAllowed.getCode());

        Assert.assertEquals(false,result.isSuccess());

        Assert.assertTrue(actualResultDetailCodes.containsAll(expectResultDetailCodes));
        Assert.assertTrue(expectResultDetailCodes.containsAll(actualResultDetailCodes));
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

        List<Integer> actualResultDetailCodes = resultDetails.stream().map(detail ->  detail.getCode()).collect(Collectors.toList());
        List<Integer> expectResultDetailCodes = Arrays.asList(ValidationPasswordNoLowerCaseLetters.getCode(),ValidationPasswordCharacterNotAllowed.getCode());

        Assert.assertEquals(false,result.isSuccess());

        Assert.assertTrue(actualResultDetailCodes.containsAll(expectResultDetailCodes));
        Assert.assertTrue(expectResultDetailCodes.containsAll(actualResultDetailCodes));
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

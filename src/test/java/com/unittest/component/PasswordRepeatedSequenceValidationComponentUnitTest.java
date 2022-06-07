package com.unittest.component;


import static com.enums.ResponseEnum.*;
import com.bo.PasswordValidationResult;
import com.component.PasswordValidationComponent;
import com.component.impl.PasswordRepeatedSequenceValidationComponent;
import com.request.ValidationRequest;
import com.bo.CommonResult;
import org.junit.Assert;
import org.junit.Test;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class PasswordRepeatedSequenceValidationComponentUnitTest {

    private  PasswordValidationComponent passwordValidationComponent = new PasswordRepeatedSequenceValidationComponent();


    @Test
    public void testValidateSequenceOfCharactersFollowedByTheSameSequence(){

        ValidationRequest request = new ValidationRequest("1abcabc");

        PasswordValidationResult result = passwordValidationComponent.validate(request);
        List<CommonResult> resultDetails = result.getDetails();

        List<Integer> actualResultDetailsCode = resultDetails.stream().map(detail ->  detail.getCode()).collect(Collectors.toList());
        List<Integer> expectResultDetailsCode = Arrays.asList(ValidationSequenceOfCharactersFollowedByTheSameSequence.getCode());

        Assert.assertEquals(false,result.isSuccess());

        Assert.assertTrue(actualResultDetailsCode.containsAll(expectResultDetailsCode));
        Assert.assertTrue(expectResultDetailsCode.containsAll(actualResultDetailsCode));

    }

    @Test
    public void testValidatePass(){

        ValidationRequest request = new ValidationRequest("abcab");

        PasswordValidationResult result = passwordValidationComponent.validate(request);
        List<CommonResult> resultDetails = result.getDetails();

        Assert.assertEquals(true,result.isSuccess());
        Assert.assertEquals(0,resultDetails.size());
    }

    /**
     * aa1bb   "a" equals "a" but  the length is 1,
     * base on the conversion with manager, I define it not a sequence of characters
     * so in this case it will pass
     */

    @Test
    public void testValidatePassOneCharacter(){

        ValidationRequest request = new ValidationRequest("aabbcc");

        PasswordValidationResult result = passwordValidationComponent.validate(request);
        List<CommonResult> resultDetails = result.getDetails();

        Assert.assertEquals(true,result.isSuccess());
        Assert.assertEquals(0,resultDetails.size());
    }
}

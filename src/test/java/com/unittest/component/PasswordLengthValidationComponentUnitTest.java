package com.unittest.component;

import com.bo.CommonResult;
import com.bo.PasswordValidationResult;
import com.component.PasswordValidationComponent;
import com.constant.ValidationConstant;
import static com.enums.ResponseEnum.*;
import com.request.ValidationRequest;
import com.component.impl.PasswordLengthValidationComponent;
import org.junit.Assert;
import org.junit.Test;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PasswordLengthValidationComponentUnitTest {


    private PasswordValidationComponent passwordValidationComponent = new PasswordLengthValidationComponent();

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
        ValidationRequest request = new ValidationRequest(testStringBuilder.toString());

        PasswordValidationResult result = passwordValidationComponent.validate(request);
        List<CommonResult> resultDetails = result.getDetails();

        List<Integer> actualResultDetailsCode = resultDetails.stream().map(detail ->  detail.getCode()).collect(Collectors.toList());
        List<Integer> expectResultDetailsCode = Arrays.asList(ValidationPasswordLengthError.getCode());

        Assert.assertEquals(false,result.isSuccess());

        Assert.assertTrue(actualResultDetailsCode.containsAll(expectResultDetailsCode));
        Assert.assertTrue(expectResultDetailsCode.containsAll(actualResultDetailsCode));

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
        ValidationRequest request = new ValidationRequest(testStringBuilder.toString());

        PasswordValidationResult result = passwordValidationComponent.validate(request);
        List<CommonResult> resultDetails = result.getDetails();

        List<Integer> actualResultDetailsCode = resultDetails.stream().map(detail ->  detail.getCode()).collect(Collectors.toList());
        List<Integer> expectResultDetailsCode = Arrays.asList(ValidationPasswordLengthError.getCode());

        Assert.assertEquals(false,result.isSuccess());

        Assert.assertTrue(actualResultDetailsCode.containsAll(expectResultDetailsCode));
        Assert.assertTrue(expectResultDetailsCode.containsAll(actualResultDetailsCode));
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
        ValidationRequest request = new ValidationRequest(testStringBuilder.toString());

        PasswordValidationResult result = passwordValidationComponent.validate(request);
        List<CommonResult> resultDetails = result.getDetails();

        Assert.assertEquals(true,result.isSuccess());
        Assert.assertEquals(0,resultDetails.size());
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
        ValidationRequest request = new ValidationRequest(testStringBuilder.toString());

        PasswordValidationResult result = passwordValidationComponent.validate(request);
        List<CommonResult> resultDetails = result.getDetails();

        Assert.assertEquals(true,result.isSuccess());
        Assert.assertEquals(0,resultDetails.size());
    }
}

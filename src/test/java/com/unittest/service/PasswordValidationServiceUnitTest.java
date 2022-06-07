package com.unittest.service;


import com.bo.CommonResult;
import com.bo.PasswordValidationResult;
import com.component.PasswordValidationComponent;
import com.component.impl.PasswordCharacterValidationComponent;
import com.component.impl.PasswordLengthValidationComponent;
import com.component.impl.PasswordRepeatedSequenceValidationComponent;
import com.constant.ValidationConstant;
import com.enums.ResponseEnum;
import com.request.ValidationRequest;
import com.service.impl.PasswordValidationServiceImpl;
import com.util.CommonResultUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static com.enums.ResponseEnum.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class PasswordValidationServiceUnitTest {

    @Mock
    private PasswordValidationComponent passwordCharacterValidationComponent;
    @Mock
    private PasswordValidationComponent passwordLengthValidationComponent;
    @Mock
    private PasswordValidationComponent passwordRepeatedSequenceValidationComponent;



    @Before
    public void setUp() throws Exception {
        passwordCharacterValidationComponent = mock(PasswordCharacterValidationComponent.class);
        passwordLengthValidationComponent = mock(PasswordLengthValidationComponent.class);
        passwordRepeatedSequenceValidationComponent = mock(PasswordRepeatedSequenceValidationComponent.class);
    }

    @Test
    public void testValidateSuccess(){
        Mockito.when(passwordCharacterValidationComponent.validate(any(ValidationRequest.class))).
                thenReturn(new PasswordValidationResult(true,new ArrayList<>()));

        Mockito.when(passwordLengthValidationComponent.validate(any(ValidationRequest.class))).
                thenReturn(new PasswordValidationResult(true,new ArrayList<>()));

        Mockito.when(passwordRepeatedSequenceValidationComponent.validate(any(ValidationRequest.class))).
                thenReturn(new PasswordValidationResult(true,new ArrayList<>()));

        PasswordValidationServiceImpl service = new PasswordValidationServiceImpl(Arrays.asList(passwordCharacterValidationComponent,passwordLengthValidationComponent,passwordRepeatedSequenceValidationComponent));
        CommonResult<List<CommonResult>> result = service.validate(new ValidationRequest());
        Assert.assertEquals(Success.getCode(),result.getCode());
    }

    @Test
    public void testValidateFail(){

        CommonResult validationPasswordNoLowerCaseLetters = CommonResultUtil.error(ResponseEnum.ValidationPasswordNoLowerCaseLetters);
        CommonResult validationPasswordLengthError = CommonResultUtil.error(ResponseEnum.ValidationPasswordLengthError);
        CommonResult validationSequenceOfCharactersFollowedByTheSameSequence = CommonResultUtil.error(ResponseEnum.ValidationSequenceOfCharactersFollowedByTheSameSequence);


        Mockito.when(passwordCharacterValidationComponent.validate(any(ValidationRequest.class))).
                thenReturn(new PasswordValidationResult(false,Arrays.asList(validationPasswordNoLowerCaseLetters)));

        Mockito.when(passwordLengthValidationComponent.validate(any(ValidationRequest.class))).
                thenReturn(new PasswordValidationResult(false,Arrays.asList(validationPasswordLengthError)));

        Mockito.when(passwordRepeatedSequenceValidationComponent.validate(any(ValidationRequest.class))).
                thenReturn(new PasswordValidationResult(false,Arrays.asList(validationSequenceOfCharactersFollowedByTheSameSequence)));

        PasswordValidationServiceImpl service = new PasswordValidationServiceImpl(Arrays.asList(passwordCharacterValidationComponent,passwordLengthValidationComponent,passwordRepeatedSequenceValidationComponent));
        CommonResult<List<CommonResult>> result = service.validate(new ValidationRequest());
        Assert.assertEquals(Fail.getCode(),result.getCode());
        Assert.assertEquals(3,result.getData().size());

        List<CommonResult> subResultList = result.getData();
        Assert.assertTrue(subResultList.contains(validationPasswordNoLowerCaseLetters));
        Assert.assertTrue(subResultList.contains(validationPasswordLengthError));
        Assert.assertTrue(subResultList.contains(validationSequenceOfCharactersFollowedByTheSameSequence));


    }
}

package com.integration.service;

import com.DemoApplication;
import com.bo.CommonResult;
import com.component.PasswordValidationComponent;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.request.ValidationRequest;
import com.service.PasswordValidationService;
import com.util.MockMvcUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.constant.MappingConstant.VALIDATE;
import static com.enums.ResponseEnum.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
@AutoConfigureMockMvc
public class PasswordValidationServiceIntegrationTest {


    @Autowired
    private PasswordValidationService service;

    @Test
    public void testValidateSuccess() throws Exception {

        ValidationRequest request = new ValidationRequest("aa123");

        CommonResult commonResult = service.validate(request);

        Assert.assertEquals(Success.getCode(), commonResult.getCode());
    }

    /**
     * ValidationPasswordLengthError
     * ValidationPasswordNoLowerCaseLetters
     * ValidationPasswordNoNumericalDigits
     * ValidationPasswordCharacterNotAllowed
     * ValidationSequenceOfCharactersFollowedByTheSameSequence
     * @throws Exception
     */

    @Test
    public void testValidateFailLengthErrorAndCharacterNotMatchedAndNoLowercaseCharacterNoNumberDigits()  {
        ValidationRequest request = new ValidationRequest("ABAB");

        CommonResult<List<CommonResult>> commonResult = service.validate(request);

        List<Integer> actualSubResultCodes = commonResult.getData().stream().map(res ->  res.getCode()).collect(Collectors.toList());
        List<Integer> expectSubResultCodes = Arrays.asList(ValidationPasswordLengthError.getCode(),ValidationPasswordNoLowerCaseLetters.getCode(),ValidationPasswordNoNumericalDigits.getCode(),
                ValidationPasswordCharacterNotAllowed.getCode(),ValidationPasswordSequenceOfCharactersFollowedByTheSameSequence.getCode());

        Assert.assertTrue(actualSubResultCodes.containsAll(expectSubResultCodes));
        Assert.assertTrue(expectSubResultCodes.containsAll(actualSubResultCodes));
    }


}

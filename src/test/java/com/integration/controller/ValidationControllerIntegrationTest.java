package com.integration.controller;

import com.DemoApplication;

import com.bo.CommonResult;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.request.ValidationRequest;
import com.component.PasswordValidationComponent;
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
import static com.constant.MappingConstant.*;
import static com.enums.ResponseEnum.Fail;
import static com.enums.ResponseEnum.Success;
import static com.enums.ResponseEnum.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
@AutoConfigureMockMvc
public class ValidationControllerIntegrationTest {

    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private MockMvcUtil mockMvcUtil;
    @Autowired
    private Set<PasswordValidationComponent> validationSet;



    @Test
    public void testValidateSuccess() throws Exception {
        ValidationRequest request = new ValidationRequest("aa123");

        String returnString = mockMvcUtil.postMethod(VALIDATE,request);

        CommonResult commonResult = mapper.readValue(returnString, CommonResult.class);

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
    public void testValidateFailLengthErrorAndCharacterNotMatchedAndNoLowercaseCharacterNoNumberDigits() throws Exception {
        ValidationRequest request = new ValidationRequest("ABAB");

        String returnString = mockMvcUtil.postMethod(VALIDATE,request);

        TypeReference reference = new TypeReference<CommonResult<List<CommonResult>>>() {};
        CommonResult<List<CommonResult>> commonResult = mapper.readValue(returnString,reference);

        Assert.assertEquals(Fail.getCode(), commonResult.getCode());

        List<Integer> actualSubResultCodes = commonResult.getData().stream().map(res ->  res.getCode()).collect(Collectors.toList());
        List<Integer> expectSubResultCodes = Arrays.asList(ValidationPasswordLengthError.getCode(),ValidationPasswordNoLowerCaseLetters.getCode(),ValidationPasswordNoNumericalDigits.getCode(),
                ValidationPasswordCharacterNotAllowed.getCode(),ValidationPasswordSequenceOfCharactersFollowedByTheSameSequence.getCode());

        Assert.assertTrue(actualSubResultCodes.containsAll(expectSubResultCodes));
        Assert.assertTrue(expectSubResultCodes.containsAll(actualSubResultCodes));
    }


}

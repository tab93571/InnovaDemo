//package com.integration;
//
//import com.DemoApplication;
//
//import com.bo.CommonResult;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.request.ValidationRequest;
//import com.component.PasswordValidationComponent;
//import com.util.MockMvcUtil;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//import static com.constant.MappingConstant.*;
//import static com.constant.MappingConstant.ADDVALIDATESERVICE;
//import static com.enums.ResponseEnum.Fail;
//import static com.enums.ResponseEnum.Success;
//import static com.enums.ResponseEnum.*;
//
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = DemoApplication.class)
//@AutoConfigureMockMvc
//public class ValidationControllerIntegrationTest {
//    @Autowired
//    private MockMvc mockMvc;
//    @Autowired
//    private ObjectMapper mapper;
//    @Autowired
//    private MockMvcUtil mockMvcUtil;
//    @Autowired
//    private Set<PasswordValidationComponent> validationSet;
//
//    //------------------ validate------------------------------
//
//
//    @Test
//    public void testValidateSuccess() throws Exception {
//        ValidationRequest request = new ValidationRequest("aa123");
//
//        String returnString = mockMvcUtil.postMethod(VALIDATE,request);
//
//        CommonResult commonResult = mapper.readValue(returnString, CommonResult.class);
//
//        Assert.assertEquals(Success.getCode(), commonResult.getCode());
//    }
//
//    @Test
//    public void testValidateFailNullOrEmpty() throws Exception {
//        ValidationRequest request = new ValidationRequest();
//
//        String returnString = mockMvcUtil.postMethod(VALIDATE,request);
//
//        TypeReference reference = new TypeReference<CommonResult<List<CommonResult>>>() {};
//        CommonResult<List<CommonResult>> commonResult = mapper.readValue(returnString,reference);
//
//        Assert.assertEquals(Fail.getCode(), commonResult.getCode());
//
//        List<Integer> actualResponseListCode = commonResult.getData().stream().map(res ->  res.getCode()).collect(Collectors.toList());
//        List<Integer> expectResponseListCode = Arrays.asList(ValidationPasswordNullOrEmptyError.getCode());
//
//        Assert.assertEquals(expectResponseListCode.size(),actualResponseListCode.size());
//        Assert.assertTrue(actualResponseListCode.containsAll(expectResponseListCode));
//    }
//
//    /**
//     * ValidationPasswordLengthError
//     * ValidationPasswordNoLowerCaseLetters
//     * ValidationPasswordNoNumericalDigits
//     * ValidationPasswordCharacterNotAllowed
//     * ValidationSequenceOfCharactersFollowedByTheSameSequence
//     * @throws Exception
//     */
//
//    @Test
//    public void testValidateFailLengthErrorAndCharacterNotMatchedAndNoLowercaseCharacterNoNumberDigits() throws Exception {
//        ValidationRequest request = new ValidationRequest("ABAB");
//
//        String returnString = mockMvcUtil.postMethod(VALIDATE,request);
//
//        TypeReference reference = new TypeReference<CommonResult<List<CommonResult>>>() {};
//        CommonResult<List<CommonResult>> commonResult = mapper.readValue(returnString,reference);
//
//        Assert.assertEquals(Fail.getCode(), commonResult.getCode());
//
//        List<Integer> actualResponseListCode = commonResult.getData().stream().map(res ->  res.getCode()).collect(Collectors.toList());
//        List<Integer> expectResponseListCode = Arrays.asList(ValidationPasswordLengthError.getCode(),ValidationPasswordNoLowerCaseLetters.getCode(),ValidationPasswordNoNumericalDigits.getCode(),
//                ValidationPasswordCharacterNotAllowed.getCode(),ValidationSequenceOfCharactersFollowedByTheSameSequence.getCode());
//
//        Assert.assertEquals(expectResponseListCode.size(),actualResponseListCode.size());
//        Assert.assertTrue(actualResponseListCode.containsAll(expectResponseListCode));
//    }
//
//    //------------------ get service------------------------------
//
//    @Test
//    public void testGetValidateServiceNameInUse() throws Exception {
//
//        String returnString = mockMvcUtil.getMethod(GETVALIDATESERVICESINUSE);
//
//
//        CommonResult commonResult = mapper.readValue(returnString, CommonResult.class);
//
//        List<String>responseDataActual = (List<String>) commonResult.getData();
//        Set<String>responseDataExpected = validationSet.stream().map(service -> service.getName()).collect(Collectors.toSet());
//
//        Assert.assertEquals(Success.getCode(), commonResult.getCode());
//
//        Assert.assertEquals(responseDataExpected.size(),responseDataActual.size());
//        Assert.assertTrue(responseDataActual.containsAll(responseDataExpected));
//
//    }
//
//    //------------------ delete service------------------------------
//
//    /**
//     * bean exists in the validationSet
//     */
//    @Test
//    public void testDeleteValidateServiceInUseSuccess() throws Exception {
//
//        DeleteValidateServiceRequest request = new DeleteValidateServiceRequest("validatePasswordCheckRepeatedSequenceService");
//
//        TypeReference reference = new TypeReference<CommonResult<List<String>>>() {};
//
//        String returnStringGet = mockMvcUtil.getMethod(GETVALIDATESERVICESINUSE);
//
//        CommonResult<List<String>> commonResultGet = mapper.readValue(returnStringGet,reference);
//
//        int originServiceSize = commonResultGet.getData().size();
//
//        String returnString = mockMvcUtil.deleteMethod(DELETEVALIDATESERVICEINUSE,request);
//
//        CommonResult commonResult = mapper.readValue(returnString, CommonResult.class);
//
//        Assert.assertEquals(Success.getCode(), commonResult.getCode());
//
//        String returnStringGetAfterDelete = mockMvcUtil.getMethod(GETVALIDATESERVICESINUSE);
//
//        CommonResult<List<String>> commonResultGetAfterDelete = mapper.readValue(returnStringGetAfterDelete,reference);
//
//        int afterDeleteServiceSize = commonResultGetAfterDelete.getData().size();
//
//        Assert.assertTrue(afterDeleteServiceSize == originServiceSize - 1);
//    }
//
//    /**
//     * bean doesn't exist in the validationSet
//     */
//    @Test
//    public void testDeleteValidateServiceInUseNotFound() throws Exception {
//
//        DeleteValidateServiceRequest request = new DeleteValidateServiceRequest("");
//
//        TypeReference reference = new TypeReference<CommonResult<List<String>>>() {};
//
//        String returnStringGet = mockMvcUtil.getMethod(GETVALIDATESERVICESINUSE);
//
//        CommonResult<List<String>> commonResultGet = mapper.readValue(returnStringGet,reference);
//
//        int originServiceSize = commonResultGet.getData().size();
//
//        String returnString = mockMvcUtil.deleteMethod(DELETEVALIDATESERVICEINUSE,request);
//
//        CommonResult commonResult = mapper.readValue(returnString, CommonResult.class);
//
//        Assert.assertEquals(ValidationCanNotFindService.getCode(), commonResult.getCode());
//
//        String returnStringGetAfterDelete = mockMvcUtil.getMethod(GETVALIDATESERVICESINUSE);
//
//        CommonResult<List<String>> commonResultGetAfterDelete = mapper.readValue(returnStringGetAfterDelete,reference);
//
//        int afterDeleteServiceSize = commonResultGetAfterDelete.getData().size();
//
//        Assert.assertTrue(afterDeleteServiceSize == originServiceSize );
//    }
//
//    //------------------ add service------------------------------
//
//    /**
//     * bean doesn't exist in the spring container
//     */
//    @Test
//    public void testAddValidateServiceInUseNotExistInSpringContainer() throws Exception {
//
//        AddValidateServiceRequest request = new AddValidateServiceRequest("");
//
//        String returnString = mockMvcUtil.postMethod(ADDVALIDATESERVICE,request);
//
//        CommonResult commonResult = mapper.readValue(returnString, CommonResult.class);
//        Assert.assertEquals(ValidationCanNotFindService.getCode(), commonResult.getCode());
//
//    }
//
//    /**
//     * the type of bean isn't matched
//     */
//    @Test
//    public void testAddValidateServiceBeanTypeNotMatched() throws Exception {
//
//        AddValidateServiceRequest request = new AddValidateServiceRequest("generalValidateServiceImpl");
//
//        String returnString = mockMvcUtil.postMethod(ADDVALIDATESERVICE,request);
//
//        CommonResult commonResult = mapper.readValue(returnString, CommonResult.class);
//        Assert.assertEquals(ValidationServiceTypeNotMatch.getCode(), commonResult.getCode());
//    }
//}

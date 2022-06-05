package com.enums;
import com.constant.ValidationConstant;

public enum ResponseEnum {

    Success(0000,"success"),
    Fail(0001,"fail and check the details from data"),
    ValidationPasswordLengthError(1000,"password length is not between" + ValidationConstant.LENGTHLOWERBOUND+ " and " + ValidationConstant.LENGTHUPPERBOUND),
    ValidationPasswordNullOrEmptyError(1001,"password can not be null or empty"),
    ValidationPasswordNoLowerCaseLetters(1002,"amounts of lowercase letters can not be zero"),
    ValidationPasswordNoNumericalDigits(1003,"amounts of numericalDigits can not be zero"),
    ValidationPasswordCharacterNotAllowed(1004,"doesn't match the required character format"),
    ValidationSequenceOfCharactersFollowedByTheSameSequence(1005,"sequence of  characters can not  be followed by the same sequence"),

    ValidationCanNotFindService(2001,"the validation service you require is not in use"),
    ValidationServiceTypeNotMatch(2002,"service found is not validationService");

    private Integer code;
    private String message;

    ResponseEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}

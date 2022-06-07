package com.component.impl;

import com.bo.CommonResult;
import com.bo.PasswordValidationResult;
import com.enums.ResponseEnum;
import com.request.ValidationRequest;
import com.component.PasswordValidationComponent;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;
import com.util.CommonResultUtil;

import java.util.ArrayList;
import java.util.List;

import static com.enums.ResponseEnum.ValidationPasswordNullOrEmptyError;

@Component
public class PasswordRepeatedSequenceValidationComponent implements PasswordValidationComponent {


    @Override
    public PasswordValidationResult validate(ValidationRequest request) {

        List<CommonResult> passwordValidationDetails = new ArrayList<>();
        PasswordValidationResult passwordValidationResult = new PasswordValidationResult(passwordValidationDetails);

        if(request == null || Strings.isEmpty(request.getPassword())){
            passwordValidationDetails.add(CommonResultUtil.error(ValidationPasswordNullOrEmptyError));
            return passwordValidationResult;
        }

        String password = request.getPassword();
        int maxSequenceLength = password.length()/2;

        List<String> repeatedCharacterList = new ArrayList<>();

        for(int sequenceLength = 2 ; sequenceLength <= maxSequenceLength ; sequenceLength ++){
            for(int j = 1 ; j < password.length() ; j ++){
                int lowerBound = j - sequenceLength;
                int upperBound = j + sequenceLength;
                if(lowerBound >= 0 && upperBound <= password.length() ){
                  String sequenceFirst =  password.substring(lowerBound,j);
                  String sequenceSecond = password.substring(j,upperBound);
                  if(sequenceFirst.equals(sequenceSecond)){
                      repeatedCharacterList.add(sequenceFirst);
                  }
                }
            }
        }
        if(repeatedCharacterList.size() != 0){
            passwordValidationDetails.add(CommonResultUtil.error(ResponseEnum.ValidationPasswordSequenceOfCharactersFollowedByTheSameSequence,repeatedCharacterList));
        }
        if(0 == passwordValidationDetails.size()){
            passwordValidationResult.setSuccess(true);
        }
        return passwordValidationResult;
    }
}

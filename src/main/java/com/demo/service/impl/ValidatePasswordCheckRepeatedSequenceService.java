package com.demo.service.impl;

import com.demo.enums.ResponseEnum;
import com.demo.request.ValidateRequest;
import com.demo.response.Response;
import com.demo.service.ValidateService;
import org.springframework.stereotype.Service;
import com.demo.util.ResponseUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Service
public class ValidatePasswordCheckRepeatedSequenceService implements ValidateService {

    private final String name = "validatePasswordCheckRepeatedSequenceService";


    @Override
    public void validate(ValidateRequest request, List<Response> responseList) {
        String password = request.getPassword();
        int maxSequenceLength = password.length()/2;

        List<List<String>> repeatedCharacterList = new ArrayList<>();

        for(int sequenceLength = 2 ; sequenceLength <= maxSequenceLength ; sequenceLength ++){
            for(int j = 1 ; j < password.length() ; j ++){
                int lowerBound = j - sequenceLength;
                int upperBound = j + sequenceLength;
                if(lowerBound >= 0 && upperBound <= password.length() ){
                  String sequenceFirst =  password.substring(lowerBound,j);
                  String sequenceSecond = password.substring(j,upperBound);
                  if(sequenceFirst.equals(sequenceSecond)){
                      repeatedCharacterList.add(Arrays.asList(sequenceFirst,sequenceSecond));
                  }
                }
            }
        }
        if(repeatedCharacterList.size() != 0){
            responseList.add(ResponseUtil.error(ResponseEnum.ValidationSequenceOfCharactersFollowedByTheSameSequence,repeatedCharacterList));
        }

    }

    @Override
    public String getName() {
        return name;
    }
}

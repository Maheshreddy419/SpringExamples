package com.codingshuttle.springbootwebtutorial.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PrimeNumberValidator implements ConstraintValidator<PrimeNumberAnnotation,Integer> {

    @Override
    public boolean isValid(Integer inputNumber, ConstraintValidatorContext constraintValidatorContext) {
          int number = inputNumber;
          boolean isPrime= false;
          if(number ==0 || number == 1){
              return false;
          }else {
              for(int i = 2; i <= number/2; i++){
                  if(number % i == 0){
                       isPrime =false;
                      break;
                  }
                  isPrime = true;
              }
          }
        return isPrime;
    }
}

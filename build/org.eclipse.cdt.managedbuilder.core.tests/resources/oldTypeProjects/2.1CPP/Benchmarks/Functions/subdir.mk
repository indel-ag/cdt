################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
CPP_SRCS += \
../Functions/Func1.cpp 

CPP_DEPS += \
./Functions/Func1.d 

OBJS += \
./Functions/Func1.o 


# Each subdirectory must supply rules for building sources it contributes
include Functions/subdir.rule.mk

clean: clean-Functions

clean-Functions:
	-$(RM) ./Functions/Func1.d ./Functions/Func1.o

.PHONY: clean-Functions

